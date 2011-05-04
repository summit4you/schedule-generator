package database;

import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.util.List;
import java.util.Vector;

/**
 * Extracts the {@link Databasable} information of objects and classes, for reading and writing to the database.
 * @author Zjef
 * @version 2.4
 */
public class Extract
{
	/**
	 * Generates a table name for each {@link Databasable} class
	 */
	protected static String getTableName(Class<? extends Databasable> cl)
	{
		return cl.getSimpleName();
	}
	
	/**
	 * Generates a table name
	 */
	protected static String getTableName(Databasable object)
	{
		return getTableName(object.getClass());
	}
	
	protected static CreateTable getNewTable(Class<? extends Databasable> cl)
	{
		Vector<Method> getters=getGetters(cl);
		Vector<Field> fields=new Vector<Field>();
		
		for (Method i:getters)
		{
			fields.add(new Field(methodToString(i), FieldType.getType(i.getReturnType()),FieldDefault.NULL));
		}
		fields.add(new Field("ID",FieldType.INT,FieldDefault.AUTO_INCREMENT));
		return new CreateTable(getTableName(cl), fields, TableType.MyISAM,"ID");
	}
	
	protected static void readObject(Class cl,Object ob,ResultSet result,Database database)
	{
		try
		{
			for (Method i:getSetters(cl))
			{			
				if (result.getObject(methodToString(i))==null)
				{
					continue;
				}
				
				Class<?> inputType=i.getParameterTypes()[0];
				if (inputType==Vector.class)
				{
					Vector vec=new Vector();
					Class c=i.getAnnotation(OutDatabase.class).value();
					String data=result.getString(methodToString(i));
					if (!data.equals(""))
					{
						String[] elements=data.split(Search.classSeparator);
						
						for (String s:elements)
						{
							vec.add(strToObject(s,c,database));
						}
						i.invoke(ob,vec);
					}
				}
				else if (implementsDatabasable(inputType))
				{
					i.invoke(ob,strToObject(Integer.toString(result.getInt(methodToString(i))),inputType,database));
				}
				else if ((inputType==Boolean.TYPE)||(inputType==Boolean.class))
				{
					i.invoke(ob,result.getBoolean(methodToString(i)));
				}
				else if (inputType==ID.class)
				{
					i.invoke(ob,new ID(result.getInt("ID")));
				}
				else if (implementsSomething(inputType,DatabasableAsValue.class))
				{
					i.invoke(ob,strToObject(result.getObject(methodToString(i)).toString(),inputType,database));
				}
				else
				{
					i.invoke(ob,result.getObject(methodToString(i)));
				}
			}
			database.addCache((Databasable) ob);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	protected static Vector<?> readResult(ResultSet result,Class<? extends Databasable> cl,Database database,List<Class<? extends Databasable>> classes)
	{
		Vector objects=new Vector();
		try
		{
			while (result.next())
			{
				Databasable cached=database.getFromCache(cl,new ID(result.getString("ID")));
				if (cached!=null)
				{
					objects.add(cached);
				}
				else
				{
					Databasable ob=cl.newInstance();
					readObject(cl, ob, result, database);
					objects.add(ob);
					while (database.getLoadList().size()>0)
					{
						for (Databasable i:database.getLoadList())
						{
							if (classes==null || classes.contains(i.getClass()))
							{
								ResultSet result2=database.query(new Search(i.getClass(),i.getID()).getText());
								result2.first();
								readObject(i.getClass(),i,result2,database);
							}
							else
							{
								database.removeFromLoadList(i);
							}
						}
					}
				}
			}
			result.close();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return objects;
	}
	
	/**
	 * @return an {@link Insertion} which holds the string for an INSERT MySQL instruction.
	 */
	protected static Insertion getInsertion(Databasable databasable)
	{
		Insertion insertion=new Insertion(getTableName(databasable));
		Vector<Method> getters=getGetters(databasable.getClass());
		for (Method i:getters)
		{
			try
			{
				insertion.add(methodToString(i),i.invoke(databasable));
			} catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		return insertion;
	}
	
	/**
	 * @return an {@link Update} which holds the string for an UPDATE MySQL instruction.
	 */
	protected static Update getUpdate(Databasable databasable)
	{
		Update update=new Update(getInsertion(databasable));
		update.addConstraint("ID",databasable.getID().toString());
		return update;
	}
	
	/**
	 * @return all setters (annotated with {@link OutDatabase}) from a {@link Databasable} class
	 */
	protected static Vector<Method> getSetters(Class<? extends Databasable> cl)
	{
		Vector<Method> methods=new Vector<Method>();
		for (Method i:cl.getDeclaredMethods())
		{
			if (i.isAnnotationPresent(OutDatabase.class))
			{
				methods.add(i);
			}
		}
		try
		{
			methods.add(cl.getMethod("setID",ID.class));
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return methods;
	}

	/**
	 * @return all setters (annotated with {@link InDatabase}) from a {@link Databasable} class
	 */
	protected static Vector<Method> getGetters(Class<? extends Databasable> cl)
	{
		Vector<Method> methods=new Vector<Method>();
		for (Method i:cl.getDeclaredMethods())
		{
			if (i.isAnnotationPresent(InDatabase.class))
			{
				methods.add(i);
			}
		}
		return methods;
	}
	
	/**
	 * @return the object returned by the getter
	 */
	protected static Object getValue(Object source,Method getter)
	{
		try
		{
			return getter.invoke(source);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * @return String with '' around the value for MySQL insertions etc
	 */
	protected static String valueToString(Object object)
	{
		return (object instanceof Integer)||(object instanceof Double)?Extract.objectToStr(object):'\''+Extract.objectToStr(object)+'\'';
	}
	
	/**
	 * Converts an object, returned from a getter, to a string for the database
	 */
	protected static String objectToStr(Object obj)
	{
		if (obj instanceof DatabasableAsValue)
		{
			return objectToStr(((DatabasableAsValue) obj).toValue());
		}
		if (obj instanceof Databasable)
		{
			return ((Databasable) obj).getID().toString();
		}
		else if (obj instanceof Vector)
		{
			if (((Vector) obj).size()==0)
			{
				return "";
			}
			String res="";
			for (Object i:(Vector<?>)obj)
			{
				res+=objectToStr(i)+";";
			}
			return res.substring(0,res.length()-1);
		}
		else if (obj instanceof Boolean)
		{
			return (Boolean)obj==true?"1":"0";
		}
		else if (obj==null)
		{
			return "";
		}
		else
		{
			return obj.toString();
		}
	}
	
	protected static boolean implementsDatabasable(Class<?> cl)
	{
		return implementsSomething(cl,Databasable.class);
	}
	
	protected static boolean implementsSomething(Class<?> underTest,Class<?> something)
	{
		for (Class<?> i:underTest.getInterfaces())
		{
			if ((i==something)||(implementsSomething(i,something)))
			{
				return true;
			}
		}
		return false;
	}
	
	/**
	 * @return an object that is stored in the string.
	 */
	private static Object strToObject(String s,Class<?> cl,Database database)
	{
		if ((cl==(Boolean.TYPE))||(cl==Boolean.class))
		{
			return s.equals("1")||s.equals("true")||s.equals("TRUE")?true:false;
		}
		else if ((cl==Integer.TYPE)||(cl==Integer.class))
		{
			return Integer.parseInt(s);
		}
		else if (cl==String.class)
		{
			return s;
		}
		else if ((cl==Double.TYPE)||(cl==Double.class))
		{
			return Double.parseDouble(s);
		}
		else if (implementsSomething(cl,DatabasableAsValue.class))
		{
			try
			{
				DatabasableAsValue obj=(DatabasableAsValue) cl.newInstance();
				obj.loadFromValue(strToObject(s,cl.getDeclaredMethod(DatabasableAsValue.toValueName).getReturnType(),database));
				return obj;
			} catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		else if (implementsDatabasable(cl))
		{
			Databasable d=database.getFromCache((Class<? extends Databasable>) cl,new ID(s));
			if (d==null)
			{
				try
				{
					Databasable res=(Databasable) cl.newInstance();
					res.setID(new ID(s));
					database.addToLoad(res);
					return res;
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
			else
			{
				return d;
			}
		}
		return null;
	}
	
	/**
	 * @return the setter method mapping to the String
	 */
	protected Method stringToMethod(Class<? extends Databasable> cl,String name)
	{
		for (Method i:cl.getDeclaredMethods())
		{
			if (i.getName().equals("set"+name))
			{
				return i;
			}
		}
		new DatabasableException("No setter method for the parameter "+name+" in the class "+cl.getSimpleName());
		return null;
	}
	
	/**
	 * @return a String representing the getter/setter
	 */
	protected static String methodToString(Method method)
	{
		String name=method.getName();
		if (name.startsWith("get"))
		{
			return name.substring(3);
		}
		else if (name.startsWith("set"))
		{
			return name.substring(3);
		}
		else if (name.startsWith("is"))
		{
			return name.substring(2);
		}
		
		if (method.isAnnotationPresent(InDatabase.class))
		{
			new DatabasableException("The method "+method.getName()+" of class "+method.getDeclaringClass().getSimpleName()+" is annotated with InDatabase, but its name does not start with 'get' or 'is'");
		}
		else
		{
			new DatabasableException("The method "+method.getName()+" of class "+method.getDeclaringClass().getSimpleName()+" is annotated with OutDatabase, but its name does not start with 'set'");
		}
		
		return null;
	}
}
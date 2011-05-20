package planner;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Iterator;
import java.util.Vector;

import dataStructure.Room;
import dataStructure.Subcourse;


/**
 * dankuwel alex voor raad!
 * @author matthiascaenepeel 
 * @version1.1
 */
public class MatsiesVectorToolbox
{
//	public static Vector ordenVector(Vector vec,Method met)
//	{
//		int i=0;
//		int j=0;
//		
//		while(i<=vec.size())
//		{
//			j = i+1;
//			Object obj1=null;
//			try
//			{
//				obj1 = met.invoke(vec.elementAt(i));
//			} 
//			catch (Exception e)
//			{
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} 
//			while(j<=vec.size())
//			{
//				Object obj2=null;
//				try
//				{
//					obj2 = met.invoke(vec.elementAt(j));
//				} 
//				catch (Exception e)
//				{
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				} 
//				if(obj1.equals(obj2))
//				{
//					Object obj = vec.elementAt(j);
//					vec.setElementAt(vec.elementAt(i+1),j);
//					vec.setElementAt(obj,i+1);
//					i = i+1;
//				}
//				j = j+1;
//			}
//		}
//		
//		return vec;
//	}
	
//	public static Vector ordenVector(Vector vec,Method met)
//	{
//		
//		int k=0;
//		for(int i=0;i<vec.size();i++)
//		{
//			
//			Object obj1=null;
//			try
//			{
//				obj1 = met.invoke(vec.elementAt(i));
//			} 
//			catch (Exception e)
//			{
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} 
//			
//			k=i+1;
//			for(int j=i+1;j<vec.size();j++)
//			{
//				Object obj2=null;
//				try
//				{
//					obj2 = met.invoke(vec.elementAt(j));
//				} 
//				catch (Exception e)
//				{
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				} 
//				if(obj1.equals(obj2))
//				{
//					Object obj = vec.elementAt(j);
//					vec.setElementAt(vec.elementAt(k),j);
//					vec.setElementAt(obj,k);
//					k = k+1;
//				}
//				
//			}
//		}
//		
//		return vec;
//	}
	
	public static Object giveWishedField(Vector<Method> mets,Object obj)
	{
		
		for(Method m:mets)
		{
			try
			{
				obj = m.invoke(obj);
			} 
			catch (Exception e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.exit(0);
			} 
		}
		
		return obj;
	}
	

	
//	//Dit is een goede versie
//	public static Vector ordenVector(Vector vec,Vector<Method> mets) //volgorde is belangrijk bij het meegeven!
//	{
//		
//		
//		for(int i=0;i<vec.size();i++)
//		{
//			
//			Object obj1=giveWishedField(mets,vec.elementAt(i));
//			
//			int k=i+1;
//			for(int j=i+1;j<vec.size();j++)
//			{
//				Object obj2=giveWishedField(mets,vec.elementAt(j));;
//			
//				if(obj1.equals(obj2))
//				{
//					Object obj = vec.elementAt(j);
//					vec.setElementAt(vec.elementAt(k),j);
//					vec.setElementAt(obj,k);
//					k = k+1;
//				}
//				
//			}
//		}
//		
//		return vec;
//	}
	
	//Dit is een goede versie
	public static Vector ordenVector(Vector vec,Vector<Method> mets) //volgorde is belangrijk bij het meegeven!
	{
		
		for(int i=0;i<vec.size();i++)
		{
			
			Object obj1=giveWishedField(mets,vec.elementAt(i));
			
			int k=i+1;
			for(int j=i+1;j<vec.size();j++)
			{
				Object obj2=giveWishedField(mets,vec.elementAt(j));;
			
				if(obj1.equals(obj2))
				{
					Object obj = vec.elementAt(j);
					vec.setElementAt(vec.elementAt(k),j);
					vec.setElementAt(obj,k);
					k = k+1;
				}
				
			}
		}
		
		return vec;
	}
	
	
//	public static Vector<Bounds> mapper(Vector vec,Method met)
//	{
//		Vector<Bounds> mappedvec = new Vector<Bounds>();
//		vec = ordenVector(vec,met);
//		
//		Object obj=null;
//		
//		try
//		{
//			obj = met.invoke(vec.elementAt(1));
//		} 
//		catch (Exception e)
//		{
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} 
//		
//		Bounds bounds = new Bounds(1,0,obj);
//		mappedvec.setElementAt(bounds,1);
//		
//		int i=2;
//		while(i<=vec.size())
//		{
//			Object obj1 = null;
//			
//			try
//			{
//				obj1 = met.invoke(vec.elementAt(i));
//			} 
//			catch (Exception e)
//			{
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} 
//			
//			Object obj2 = null;
//			
//			try
//			{
//				obj2 = met.invoke(vec.elementAt(i-1));
//			} 
//			catch (Exception e)
//			{
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} 
//			
//			if(obj1.equals(obj2))
//			{
//				i = i+1;
//			}
//			else
//			{
//				mappedvec.lastElement().setUpperBound(i-1);
//				Bounds bounds1 = new Bounds(i,0,obj1);
//				mappedvec.add(bounds1);
//				i = i+1;
//			}
//		}
//		
//		mappedvec.lastElement().setUpperBound(vec.size());
//		
//		return mappedvec;
//	}
	
	public static Vector<Bounds> mapper(Vector vec,Vector<Method> mets)
	{
		Vector<Bounds> mappedvec = new Vector<Bounds>();
		
		vec = ordenVector(vec,mets);
		Object obj= giveWishedField(mets,vec.elementAt(1));
		Bounds bounds = new Bounds(1,0,obj);
		mappedvec.add(bounds);
		
		for(int i=0;i<vec.size()-1;i++)
		{
			Object obj1 = giveWishedField(mets,vec.elementAt(i));;
			Object obj2 = giveWishedField(mets,vec.elementAt(i+1));
			
			if(obj1.equals(obj2))
			{
				
			}
			else
			{
				mappedvec.lastElement().setUpperBound(i);
				Bounds bounds1 = new Bounds(i+1,0,obj2);
				mappedvec.add(bounds1);
			}
		}
		
		mappedvec.lastElement().setUpperBound(vec.size());
		
		return mappedvec;
	}
	
	public static int giveIndex(Vector<Bounds> vec,int i)
	{
		
		int j=1;
		
		for(int k=0;k<=vec.size()-1;k++)
		{
			Bounds bound = vec.elementAt(k);
			int u = bound.getUpperBound();
			int l = bound.getLowerBound();
			
			if((i<=u)&&(i>=l))
			{
				j=k;
				k=vec.size();
			}
		
			
		}
		return j;
	}
	
	public static int giveRoomIndex(int cap,Vector<Room> rooms)
	{
		int index=0;
		
		for(Room r:rooms)
		{
			if(cap>r.getcapacity())
			{
				index=rooms.indexOf(r)-1;
			}
		}
		return index;
	}
}

	


package database;

import java.util.Vector;

/**
 * Syntax for inserting an element in an existing table
 * @author Zjef
 * @version 2.1
 */
class Insertion implements Syntaxable
{
	private String tableName;
	private Vector<String> fieldNames;
	private Vector<Object> fieldValues;
	
	private Vector<Databasable> references;
	
	protected Insertion(String tableName)
	{
		this.tableName=tableName;
		this.fieldNames=new Vector<String>();
		this.fieldValues=new Vector<Object>();
		references=new Vector<Databasable>();
	}
	
	protected Insertion(String tableName,Vector<String> fieldNames,Vector<Object> fieldValues)
	{
		this(tableName);
		for (int i=0;i<fieldNames.size();i++)
		{
			add(fieldNames.get(i),fieldValues.get(i));
		}
	}
	
	protected String getTableName()
	{
		return tableName;
	}
	
	protected Vector<String> getFieldNames()
	{
		return fieldNames;
	}
	
	protected Vector<Object> getFieldValues()
	{
		return fieldValues;
	}

	protected void extractDatabasables(Object value)
	{
		if (value==null)
		{
			return;
		}
		if (value instanceof Databasable)
		{
			references.add((Databasable)value);
		}
		else if (value instanceof Vector)
		{
			for (Object i:(Vector)value)
			{
				extractDatabasables(i);
			}
		}
		else if (Extract.implementsSomething(value.getClass(),DatabasableAsValue.class))
		{
			extractDatabasables(((DatabasableAsValue)value).toValue());
		}
	}
	
	protected void add(String fieldName,Object fieldValue)
	{
		if (fieldValue!=null)
		{
			this.fieldNames.add(fieldName);
			this.fieldValues.add(fieldValue);
			extractDatabasables(fieldValue);
		}
	}
	
	protected Vector<Databasable> getReferences()
	{
		return references;
	}
	
	protected void removeReference(Databasable databasable)
	{
		this.references.remove(databasable);
	}
	
	@Override
	public String getText()
	{
		String res="INSERT INTO "+tableName+" (";
		for (String i:fieldNames)
		{
			res+=i+", ";
		}
		res=res.substring(0,res.length()-2)+") VALUES (";
		for (Object i:fieldValues)
		{
			res+=Extract.valueToString(i)+", ";
		}
		return res.substring(0,res.length()-2)+')';
	}
}
package database;

import java.util.Vector;

/**
 * Syntax for inserting an element in an existing table
 * @author Zjef
 * @version 1.0
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

	
	protected void add(String fieldName,Object fieldValue)
	{
		if (fieldValue!=null)
		{
			this.fieldNames.add(fieldName);
			this.fieldValues.add(fieldValue);
			
			if (fieldValue instanceof Databasable)
			{
				references.add((Databasable)fieldValue);
			}
			else if (fieldValue instanceof Vector)
			{
				if ((((Vector)fieldValue).size()>0)&&(((Vector)fieldValue).get(0) instanceof Databasable))
				{
					references.addAll((Vector)fieldValue);		
				}
			}
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
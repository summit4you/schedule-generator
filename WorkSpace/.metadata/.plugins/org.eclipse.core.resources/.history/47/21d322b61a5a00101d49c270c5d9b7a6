package database;

import java.util.Vector;

/**
 * Syntax for updating an element in an existing table
 * @author Zjef
 * @version 1.0
 */
public class Update extends Insertion implements Syntaxable
{
	private Vector<String> whereNames;
	private Vector<Object> whereValues;
	
	protected Update(String tableName)
	{
		super(tableName);
		this.whereNames=new Vector<String>();
		this.whereValues=new Vector<Object>();
	}
	
	protected Update(Insertion insertion)
	{
		super(insertion.getTableName(),insertion.getFieldNames(),insertion.getFieldValues());
		this.whereNames=new Vector<String>();
		this.whereValues=new Vector<Object>();
	}
	
	protected void addConstraint(String name,Object value)
	{
		this.whereNames.add(name);
		this.whereValues.add(value);
	}
	
	@Override
	public String getText()
	{
		String res="UPDATE "+getTableName()+" SET ";
		for (int i=0;i<getFieldNames().size();i++)
		{
			res+=getFieldNames().get(i)+"="+Extract.valueToString(getFieldValues().get(i))+",";
		}
		res=res.substring(0,res.length()-1)+" WHERE ";
		for (int i=0;i<whereNames.size();i++)
		{
			res+=whereNames.get(i)+"="+Extract.valueToString(whereValues.get(i))+" AND ";
		}
		return res.substring(0,res.length()-5);
	}
}
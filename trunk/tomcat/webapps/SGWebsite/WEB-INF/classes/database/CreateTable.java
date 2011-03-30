package database;

import java.util.Vector;

/**
 * Class for constructing a MySQL instruction for creating a new table
 * @author Zjef
 * @version 1.0
 */
class CreateTable implements Syntaxable
{
	private String name;
	private Vector<Field> fields;
	private TableType tableType;
	private String primaryKey;
	
	public CreateTable(String name,Vector<Field> fields,TableType tableType,String primaryKey)
	{
		this.name=name;
		this.fields=(Vector<Field>) fields.clone();
		this.tableType=tableType;
		this.primaryKey=primaryKey;
	}

	public void addField(Field field)
	{
		this.fields.add(field);
	}

	@Override
	public String getText()
	{
		String res="CREATE TABLE "+name+" (\n";
		for (Field i:fields)
		{
			res+=i.getText()+",\n";
		}
		if (primaryKey!=null)
		{
			res+="PRIMARY KEY ("+primaryKey+")\n";
		}
		else
		{
			res=res.substring(0,res.length()-2);
		}
		return res+") "+tableType.getText()+";";
	}
}
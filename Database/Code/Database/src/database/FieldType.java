package database;

import java.util.Vector;

/**
 * Different types for fields in a table
 * @author Zjef
 * @version 1.0
 */
enum FieldType implements Syntaxable
{
	INT,
	REAL,
	BOOL,
	VARCHAR,
	TEXT;

	@Override
	public String getText()
	{
		switch (this)
		{	
			case VARCHAR: return "VARCHAR(255)";
			default:return this.toString();
		}
	}
	
	public static FieldType getType(Class<?> cl)
	{
		if ((cl==(Boolean.TYPE))||(cl==Boolean.class))
		{
			return BOOL;
		}
		else if ((cl==Integer.TYPE)||(cl==Integer.class))
		{
			return INT;
		}
		else if (cl==String.class)
		{
			return VARCHAR;
		}
		else if ((cl==Double.TYPE)||(cl==Double.class))
		{
			return REAL;
		}
		else if (cl==Vector.class)
		{
			return TEXT;
		}
		else if (Extract.implementsDatabasable(cl))
		{
			return INT;
		}
		return null;
	}
}
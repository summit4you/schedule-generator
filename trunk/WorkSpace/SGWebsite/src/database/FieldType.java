package database;

import java.util.Vector;

/**
 * Different types for fields in a table
 * @author Zjef
 * @version 2.1
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
			case VARCHAR: return "VARCHAR(255) BINARY";
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
		else if (Extract.implementsSomething(cl,DatabasableAsValue.class))
		{
			try
			{
				return getType(cl.getDeclaredMethod(DatabasableAsValue.toValueName).getReturnType());
			} catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		else if (Extract.implementsDatabasable(cl))
		{
			return INT;
		}
		
		new DatabasableException("If you see this error, please contact ZVdP with the entire error message you get from eclipse");
		return null;
	}
}
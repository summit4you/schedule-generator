package database;

/**
 * Default values for fields in tables
 * @author Zjef
 * @version 1.0
 */
enum FieldDefault implements Syntaxable
{
	NULL,
	AUTO_INCREMENT;

	@Override
	public String toString() 
	{
		switch (this)
		{
			case AUTO_INCREMENT:return "AUTO_INCREMENT";
//			case NULL:return "NULL DEFAULT NULL";
			default:return super.toString();
		}
	}

	@Override
	public String getText()
	{
		return this.toString();
	}
}
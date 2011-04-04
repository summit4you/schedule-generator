package database;

/**
 * @author Zjef
 * @version 1.0
 */
enum TableType implements Syntaxable
{
	MyISAM;
	
	@Override
	public String getText()
	{
		return "TYPE="+this.toString();
	}
}
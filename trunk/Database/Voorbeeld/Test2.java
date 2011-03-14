package database;

/**
 * Voorbeeld klasse om Databasable v1.0 te implementeren
 * @author Zjef
 * @version 1.0
 */
public class Test2 implements Databasable
{
	private ID id;
	private int val;
	private String name;
	/**
	 * Deze variabele zorgt voor een oneindige lus: De klasse Test1 heeft variabelen van het type Test2 
	 * en in Test2 zit opnieuw dezelfde variabele Test1.
	 * Dit mag! Tof hé.
	 */
	private Test1 parent;
	
	public Test2(int val)
	{
		this.val=val;
	}
	
	/**
	 *constructor zonder parameters
	 */
	public Test2()
	{
	}
	
	/**
	 * Getter = @InDatabase
	 */
	@InDatabase
	public Test1 getParent()
	{
		return parent;
	}
	
	/**
	 * setter = @OutDatabase
	 */
	@OutDatabase
	public void setParent(Test1 parent)
	{
		this.parent=parent;
	}
	
	@Override
	public ID getId()
	{
		return id;
	}

	@Override
	public void setID(ID id)
	{
		this.id=id;
	}
}
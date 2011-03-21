package classesForTesting;

import database.Databasable;
import database.ID;
import database.InDatabase;
import database.OutDatabase;

/**
 * Class for testing the database
 * @author Zjef
 * @version 1.0
 */
public class TestClass2 implements Databasable
{
	private ID id;
	private String name;
	private TestClass parent;

	public TestClass2(String name)
	{
		this.setName(name);
	}
	
	public TestClass2()
	{
	
	}
	
	@OutDatabase
	public void setName(String name)
	{
		this.name = name;
	}

	@InDatabase
	public String getName()
	{
		return name;
	}

	@OutDatabase
	public void setParent(TestClass parent)
	{
		this.parent = parent;
	}

	@InDatabase
	public TestClass getParent()
	{
		return parent;
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
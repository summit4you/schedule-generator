package classesForTesting;

import java.util.Vector;

import database.Databasable;
import database.ID;
import database.InDatabase;
import database.OutDatabase;

/**
 * Class for testing the database
 * @author Zjef
 * @version 1.0
 */
public class TestClass implements Databasable
{
	private ID id;
	private String name;
	private Vector<Boolean> vBool;
	private Vector<TestClass2> children;
	
	public TestClass(String name)
	{
		setName(name);
		vBool=new Vector<Boolean>();
		setChildren(new Vector<TestClass2>());
	}
	
	public TestClass()
	{
		vBool=new Vector<Boolean>();
		setChildren(new Vector<TestClass2>());
	}
	
	public void addBool(boolean bool)
	{
		vBool.add(bool);
	}
	
	public void addChild(TestClass2 child)
	{
		children.add(child);
		child.setParent(this);
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
	
	@OutDatabase(Boolean.class)
	public void setvBool(Vector<Boolean> vBool)
	{
		this.vBool = (Vector<Boolean>) vBool.clone();
	}

	@InDatabase
	public Vector<Boolean> getvBool()
	{
		return (Vector<Boolean>) vBool.clone();
	}

	@OutDatabase(TestClass2.class)
	public void setChildren(Vector<TestClass2> children)
	{
		this.children = (Vector<TestClass2>) children.clone();
	}
	
	@InDatabase
	public Vector<TestClass2> getChildren()
	{
		return (Vector<TestClass2>) children.clone();
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
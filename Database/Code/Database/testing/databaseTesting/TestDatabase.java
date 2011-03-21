package databaseTesting;

import visibilityModifiers.DatabaseModifier;
import classesForTesting.TestClass;
import classesForTesting.TestClass2;
import database.Search;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * @author Zjef
 * @version 1.0
 */
public class TestDatabase extends TestCase
{
	private DatabaseModifier db;
	
	public static Test suite()
	{
		TestSuite suite=new TestSuite(TestDatabase.class);
		return suite;
	}
	
	@Override
	protected void setUp() throws Exception
	{
		super.setUp();
		db=new DatabaseModifier("wilma.vub.ac.be/se5_1011","se5_1011","nieveGroep");	
		db.connect();
		
		TestClass test=new TestClass("ZVdP");
		TestClass2 test2=new TestClass2("ZVdP");
		db.write(test);
		db.write(test2);
	}
	
	@Override
	protected void tearDown() throws Exception
	{
		super.tearDown();
		db.deleteTable(TestClass.class);
		db.deleteTable(TestClass2.class);
		db.disconnect();	
	}
	
	public void testConnect()
	{
		assertTrue(db.isConnected());
	}
	
	public void testIDCreation()
	{
		TestClass test=new TestClass("test");
		TestClass test2=new TestClass("test1");
		db.write(test);
		db.write(test2);
		
		assertEquals(2,test.getId().getValue());
		assertEquals(3,test2.getId().getValue());
	}
	
	public void testRead()
	{
		Search search=new Search(TestClass.class,"getName","ZVdP");
		TestClass result=db.read(search);
		
		assertNotNull(result);
		assertEquals("ZVdP",result.getName());
	}
	
	public void testReadRecursion()
	{
		TestClass parent=new TestClass("parent");
		TestClass2 child1=new TestClass2("child1");
		TestClass2 child2=new TestClass2("child2");
	
		parent.addChild(child1);
		parent.addChild(child2);
		db.write(parent);
		db.write(child1);
		db.write(child2);
		TestClass read=db.read(new Search(TestClass.class,"getName","parent"));
	
		assertEquals(2,read.getChildren().size());
		assertSame(read,read.getChildren().get(0).getParent());
		assertSame(read,read.getChildren().get(1).getParent());
	}
	
	public void testGetFromCache()
	{
		Search search=new Search(TestClass.class,"getName","ZVdP");
		TestClass testRead=db.read(search);
		
		assertNotNull(db.getFromCache(TestClass.class,testRead.getId()));
	}
}
package databaseTesting;

import java.lang.reflect.Method;
import java.util.Vector;

import classesForTesting.TestClass;
import classesForTesting.TestClass2;
import database.ID;
import visibilityModifiers.ExtractModifier;
import visibilityModifiers.IDModifier;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class TestExtraction extends TestCase
{
	public static Test suite()
	{
		TestSuite suite=new TestSuite(TestExtraction.class);
		return suite;
	}
	
	public void testTableName()
	{
		assertEquals("TestClass2",ExtractModifier.getTableName(TestClass2.class));
	}
	
	public void testGetSetters()
	{
		Vector<Method> setters=ExtractModifier.getSetters(TestClass.class);
		try
		{
			assertEquals(4,setters.size());
			assertTrue(setters.contains(TestClass.class.getDeclaredMethod("setName",String.class)));
			assertTrue(setters.contains(TestClass.class.getDeclaredMethod("setvBool",Vector.class)));
			assertTrue(setters.contains(TestClass.class.getDeclaredMethod("setChildren",Vector.class)));
			assertTrue(setters.contains(TestClass.class.getDeclaredMethod("setID",ID.class)));
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void testGetGetters()
	{
		Vector<Method> getters=ExtractModifier.getGetters(TestClass.class);
		try
		{
			assertEquals(3,getters.size());
			assertTrue(getters.contains(TestClass.class.getDeclaredMethod("getName")));
			assertTrue(getters.contains(TestClass.class.getDeclaredMethod("getvBool")));
			assertTrue(getters.contains(TestClass.class.getDeclaredMethod("getChildren")));
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void testGetValue()
	{
		try
		{
			assertEquals("ZVdP",ExtractModifier.getValue(new TestClass2("ZVdP"),TestClass2.class.getDeclaredMethod("getName")));
		} catch (Exception e)
		{
			assertTrue("Error in test method; getDeclaredMethod",false);
			e.printStackTrace();
		}
	}
	
	public void testObjectToStrBoolean()
	{
		assertEquals("1",ExtractModifier.objectToString(true));
		assertEquals("0",ExtractModifier.objectToString(false));
	}
	
	public void testObjectToStrDatabasable()
	{
		TestClass test=new TestClass("");
		test.setID(new IDModifier(10));
		assertEquals("10",ExtractModifier.objectToString(test));
	}
	
	public void testObjectToStringVector()
	{
		Vector<Integer> test=new Vector<Integer>();
		assertEquals("",ExtractModifier.objectToString(test));
		test.add(10);test.add(25621);test.add(0);test.add(4);
		assertEquals("10;25621;0;4",ExtractModifier.objectToString(test));
	}
	
	public void testMethodToString()
	{
		try
		{
			assertEquals("Name",ExtractModifier.methodToString(TestClass.class.getDeclaredMethod("getName")));
			assertEquals("vBool",ExtractModifier.methodToString(TestClass.class.getDeclaredMethod("setvBool",Vector.class)));
		} catch (Exception e)
		{
			assertTrue("Error in test method; getDeclaredMethod",false);
			e.printStackTrace();
		}
	}
}
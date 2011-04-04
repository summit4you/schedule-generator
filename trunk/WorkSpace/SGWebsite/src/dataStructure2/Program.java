package dataStructure2;

/**
 * 
 * @author matthiascaenepeel
 * @version2.0
 */

public class Program 
{
	private String name;
	private int level;
	
	public String getName() 
	{
		return name;
	}

	public void setName(String name) 
	{
		this.name = name;
	}

	
	
	public Program(int newlevel, String newname)
	{
		level = newlevel;
		name = newname;
	}
	
	public int getlevel()
	{
		return level;
	}
	
	public void setlevel(int newlevel)
	{
		level = newlevel;
	}
	
}

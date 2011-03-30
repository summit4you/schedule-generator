package dataStructure2;



/**
 * 
 * @author matthiascaenepeel
 * @version2.0
 */

public class Educator extends Person
{
	private int employeeNumber;
	
	public Educator(int newemployeeNumber,String newfirstName, String newsurName)
	{
		super(newfirstName,newsurName);
		setemployeeNumber(newemployeeNumber);
	}
	
	public Educator()
	{
		super(null,null);
	}
	
	public int getemployeeNumber()
	{
		return employeeNumber;
	}
	
	public void setemployeeNumber(int newemployeeNumber)
	{
		employeeNumber = newemployeeNumber;
	}
}

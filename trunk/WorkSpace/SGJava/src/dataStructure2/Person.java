package dataStructure2;
/**
 * 
 * @author matthiascaenepeel
 * @version2.0
 */
public class Person 
{
public Person(String firstName, String surName) 
{
		this.firstName = firstName;
		this.surName = surName;
}

private String firstName;
private String surName;

public Person()
{
	
}

public String getFirstName() 
{
	return firstName;
}
public void setFirstName(String firstName) 
{
	this.firstName = firstName;
}
public String getSurName() 
{
	return surName;
}
public void setSurName(String surName) 
{
	this.surName = surName;
}


		
}


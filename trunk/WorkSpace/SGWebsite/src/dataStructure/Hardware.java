package dataStructure;

import database.*;


/**
 * Deze klasse een XML bestand bevatten met daarin alle mogelijke hardware in, in de klasse Hardware
 * zit er dan eentje en elke {@link Subcourse} en {@link Room} bevat dan een vector met daarin de
 * objecten hardware.
 * @author matthiascaenepeel
 * @version2.0
 */
public class Hardware implements Databasable
{
	private String materiaaltype;
	
	public Hardware(String materiaaltype)
	{
		this.materiaaltype = materiaaltype;
	}
	
	private ID id;
	
	public void setID(ID id)
	{
		this.id=id;
	}
	
	public ID getId()
	{
		return id;
	}
}

package dataStructure;

import database.*;


/**
 * Deze klasse een XML bestand bevatten met daarin alle mogelijke hardware in, in de klasse Hardware
 * zit er dan eentje en elke {@link Subcourse} en {@link Room} bevat dan een vector met daarin de
 * objecten hardware.
 * @author matthiascaenepeel
 * @version2.0
 */
public class Hardware implements DatabasableAsString
{
	private String materiaaltype;
	
	public Hardware(String materiaaltype)
	{
		this.materiaaltype = materiaaltype;
	}
	
	@Override
	public boolean equals(Object obj) 
	{
		if (obj instanceof Hardware)
		{
			if (((Hardware) obj).materiaaltype.equals(this.materiaaltype))
			{
				return true;
			}
		}
		return false;
	}
	
	@Override
	public String toString() 
	{
		return materiaaltype;
	}

	@Override
	public String toValue() 
	{
		return materiaaltype;
	}

	@Override
	public void loadFromValue(String value) 
	{
		this.materiaaltype=value;
	}
}

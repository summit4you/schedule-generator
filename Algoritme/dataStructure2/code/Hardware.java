package dataStructure2;

import DataStructure.Room;
import DataStructure.Subcourse;

/**
 * Deze klasse een XML bestand bevatten met daarin alle mogelijke hardware in, in de klasse Hardware
 * zit er dan eentje en elke {@link Subcourse} en {@link Room} bevat dan een vector met daarin de
 * objecten hardware.
 * @author matthiascaenepeel
 * @version2.0
 */
public class Hardware
{
	private String materiaaltype;
	
	public Hardware(String materiaaltype)
	{
		this.materiaaltype = materiaaltype;
	}
}

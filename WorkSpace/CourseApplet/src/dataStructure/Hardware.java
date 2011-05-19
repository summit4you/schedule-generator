package dataStructure;

import java.io.Serializable;

/**
 * Deze klasse een XML bestand bevatten met daarin alle mogelijke hardware in, in de klasse Hardware
 * zit er dan eentje en elke {@link Subcourse} en {@link Room} bevat dan een vector met daarin de
 * objecten hardware.
 * @author matthiascaenepeel
 * @version2.0
 */
public class Hardware implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private String materiaaltype;
	
	public Hardware() 
	{
		
	}
}
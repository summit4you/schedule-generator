package dataStructure;

import database.*;
import htmlInterfaces.HTMLTablable;
import htmlInterfaces.HTMLTablable.*;

/**
 * Deze klasse een XML bestand bevatten met daarin alle mogelijke types van lessen (e.g. HOC, WPO, presentatie,...)
 * 
 * 
 * @author matthiascaenepeel
 * @version2.0
 */

public class TypeOfCourse implements DatabasableAsString
{
	private String coursetype;
	
	public TypeOfCourse(String coursetype)
	{
		this.coursetype = coursetype;
	}
	
	public TypeOfCourse() 
	{
		
	}

	@Override
	public String toValue() 
	{
		return coursetype;
	}

	@Override
	public void loadFromValue(String value) 
	{
		this.coursetype=value;
	}
	
	@Override
	public boolean equals(Object obj) 
	{
		if (obj instanceof TypeOfCourse)
		{
			if (((TypeOfCourse) obj).coursetype.equals(this.coursetype))
			{
				return true;
			}
		}
		return false;
	}
	
	@Override
	public String toString() 
	{
		return coursetype;
	}
}
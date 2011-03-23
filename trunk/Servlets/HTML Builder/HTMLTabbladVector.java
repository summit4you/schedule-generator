package HTMLBuilder;

import java.util.Vector;

public class HTMLTabbladVector extends Vector<HTMLTabblad>
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public HTMLTabblad getTab(String arg0)
	{		
		for (HTMLTabblad tab:this)
		{
			if (tab.getName().equals(arg0))
			{
				return tab;
			}
		}
		return null;
	}
	
	public void printNames()
	{
		System.out.println("*** De namen zijn de volgende: ***");
		for (int i = 0; i < super.size(); i++)
		{
			System.out.println(super.get(i).getName());
		}
	}
	
	}
	


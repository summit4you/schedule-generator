package dataStructure;

import java.io.Serializable;
import java.util.Vector;

import database.*;

/**
 * @author matthiascaenepeel
 * @version 2.0
 */
public class Hardware implements DatabasableAsString,Serializable
{
	private static final long serialVersionUID = 1L;
	
	private static Vector<Hardware> allHardware=loadHardwares();
	
	private String materiaaltype;
	
	public Hardware(String materiaaltype)
	{
		this.materiaaltype = materiaaltype;
	}
	
	public Hardware() 
	{
		
	}
	
	private static Vector<Hardware> loadHardwares()
	{
		//TODO read from file
		Vector<Hardware> hardware=new Vector<Hardware>();
		hardware.add(new Hardware("bord"));
		hardware.add(new Hardware("Spectrum Analyzer"));
		hardware.add(new Hardware("wulpse vrouwen"));
		hardware.add(new Hardware("groot bord"));
		hardware.add(new Hardware("groter bord"));
		hardware.add(new Hardware("grootste bord ter wereld"));
		return hardware;
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

	public static void setAllHardware(Vector<Hardware> allHardware)
	{
		Hardware.allHardware = allHardware;
	}

	public static Vector<Hardware> getAllHardware()
	{
		return allHardware;
	}
}

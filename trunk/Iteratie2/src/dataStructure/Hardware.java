package dataStructure;

import java.io.Serializable;
import java.util.Vector;

import net.fortuna.ical4j.model.parameter.Value;

import other.Globals;
import xml.ElementWithChildren;
import xml.ElementWithValue;
import xml.XMLDocument;
import xml.XMLElement;

import database.*;

/**
 * @author matthiascaenepeel
 * @version 2.0
 */
public class Hardware implements DatabasableAsString,Serializable
{
	private static final long serialVersionUID = 1L;
	
	private static final String mainLabel="Hardware";
	
	private static Vector<Hardware> allHardware=loadHardwares();
	
	private static final String fileName="Hardware.xml";
	
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
		XMLDocument hardDoc = new XMLDocument(Globals.configPath+fileName);
		Vector<Hardware> hardware=new Vector<Hardware>();
		if (hardDoc.load())
		{
			try
			{
				for(XMLElement e:ElementWithChildren.class.cast(hardDoc.getElement(mainLabel)).getElements())
				{
					hardware.add(new Hardware(ElementWithValue.class.cast(e).getValue()));
				}
			}
			catch(Exception exc)
			{
				exc.printStackTrace();	
			}
		}
		
		return hardware;
	}
	
	public static void setAllHardware(Vector<Hardware> allHardware)
	{
		Hardware.allHardware = allHardware;
	}

	public static Vector<Hardware> getAllHardware()
	{
		return allHardware;
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
		if (allHardware.contains(new Hardware(value)))
		{
			this.materiaaltype=value;
		}
	}
}

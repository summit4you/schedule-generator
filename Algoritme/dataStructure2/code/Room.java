package dataStructure2;

import java.util.Vector;

public class Room 
{
	private String location;
	private int capacity;
	private Vector<Hardware> presentHardware;
	
	public Room(String newlocation,int newcapacity)
	{
		location = newlocation;
		capacity = newcapacity;
		presentHardware = new Vector<Hardware>();
	}
	
	public Vector<Hardware> getPresentHardware()
	{
		return presentHardware;
	}

	public void setPresentHardware(Vector<Hardware> presentHardware)
	{
		this.presentHardware = presentHardware;
	}
	
	public int getcapacity()
	{
		return capacity;
	}

	public void setcapacity(int newcapacity)
	{
		capacity = newcapacity;
	}
	
	public String getLocation()
	{
		return location;
	}

	public void setLocation(String location)
	{
		this.location = location;
	}
}

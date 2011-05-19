package dataStructure;

import java.io.Serializable;
import java.util.Vector;

import database.ID;

public class Room implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private String location;
	private int capacity;
	private Vector<Hardware> presentHardware;
	
	public Room(String newlocation,int newcapacity)
	{
		location = newlocation;
		capacity = newcapacity;
		presentHardware = new Vector<Hardware>();
	}
	
	public Room()
	{
		presentHardware = new Vector<Hardware>();
	}
	
	public Vector<Hardware> getPresentHardware()
	{
		return  (Vector<Hardware>) presentHardware.clone();
	}

	public void setPresentHardware(Vector<Hardware> presentHardware)
	{
		this.presentHardware = (Vector<Hardware>)  presentHardware.clone();
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
	
	private ID id;
	
	public void setID(ID id)
	{
		this.id=id;
	}
	
	public ID getID()
	{
		return id;
	}	
	
	public String getCalendarfile()
	{
		return id.toString();
	}
	
	@Override
	public String toString()
	{
		return getLocation();
	}
}
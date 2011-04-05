package dataStructure;

import java.util.Vector;

import database.*;

public class Room implements Databasable
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
	
	public Room()
	{
		presentHardware = new Vector<Hardware>();
	}
	
	@InDatabase
	public Vector<Hardware> getPresentHardware()
	{
		return  (Vector<Hardware>) presentHardware.clone();
	}

	@OutDatabase(Hardware.class)
	public void setPresentHardware(Vector<Hardware> presentHardware)
	{
		this.presentHardware = (Vector<Hardware>)  presentHardware.clone();
	}
	
	@InDatabase
	public int getcapacity()
	{
		return capacity;
	}

	@OutDatabase
	public void setcapacity(int newcapacity)
	{
		capacity = newcapacity;
	}
	
	@InDatabase
	public String getLocation()
	{
		return location;
	}

	@OutDatabase
	public void setLocation(String location)
	{
		this.location = location;
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

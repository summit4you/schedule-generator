package dataStructure;

import htmlInterfaces.HTMLTablable;

import java.util.Vector;

import database.*;

public class Room implements Databasable,HTMLTablable
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
	
	@Override
	public boolean equals(Object obj)
	{
		return (obj!= null && obj.getClass()==this.getClass()?((this.getID()!=null && this.getID().equals(((Databasable) obj).getID()))):false);
	}

	@OutDatabase(Hardware.class)
	public void setPresentHardware(Vector<Hardware> presentHardware)
	{
		this.presentHardware = (Vector<Hardware>)  presentHardware.clone();
	}
	
	@InDatabase
	@TableInput(order=2,text="##Capacity_Room_Table##")
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
	@TableInput(order=1,text="##Location_Room_Table##")
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
	
	@Override
	public void setID(ID id)
	{
		this.id=id;
	}
	
	@Override
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
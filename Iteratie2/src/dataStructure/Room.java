package dataStructure;

import htmlInterfaces.HTMLTablable;

import java.io.Serializable;
import java.util.Vector;

import calendar.CalendarKeeper;
import calendar.IcsCalendar;

import database.*;

public class Room implements Databasable,HTMLTablable,CalendarKeeper,Serializable
{
	private static final long serialVersionUID = 1L;
	
	private String location;
	private int capacity;
	private Vector<Hardware> presentHardware;
	private Building building;
	
	public Room(String newlocation,int newcapacity)
	{
		this();
		location = newlocation;
		capacity = newcapacity;
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
	
	public void addHardware(Hardware hardware)
	{
		presentHardware.add(hardware);
	}
	
	public void removeHardware(Hardware hardware)
	{
		presentHardware.remove(hardware);
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
	
	@Override
	public String toString()
	{
		return getLocation();
	}
	
	@Override
	public IcsCalendar getCalendar()
	{
		return calendar.Translator.loadRoomCalendar(id.toString());
	}
	
	public Building getBuilding()
	{
		return this.building;
	}
	
	public void setBuilding(Building building)
	{
		this.building=building;
	}
	
}
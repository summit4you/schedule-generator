package dataStructure;

import htmlInterfaces.HTMLTablable;
import htmlInterfaces.HTMLTablable.*;

import java.io.Serializable;
import java.util.Vector;
import database.*;

/**
 * 
 * @author matthiascaenepeel
 * @version2.0
 */

public class Building implements Databasable,HTMLTablable,Serializable
{
	private static final long serialVersionUID = 1L;
	
	private String name;
	private Vector<Room> rooms;
	
	public Building(String newname)
	{
		name = newname;
		rooms = new Vector<Room>();
	}
	
	public Building()
	{
		rooms = new Vector<Room>();
	}
	
	@Override
	public boolean equals(Object obj)
	{
		return super.equals(obj)||(obj!= null && obj.getClass()==this.getClass()?((this.getID()!=null && this.getID().equals(((Databasable) obj).getID()))):false);
	}
	
	@InDatabase
	@TableInput(order=1,text="##Name_Building_Table##")
	public String getName()
	{
		return name;
	}

	@OutDatabase
	public void setName(String name)
	{
		this.name = name;
	}
	
	@InDatabase
	public Vector<Room> getRooms()
	{
		return (Vector<Room>) rooms.clone();
	}
	
	@OutDatabase(Room.class)
	public void setRooms(Vector<Room> rooms)
	{
		this.rooms = (Vector<Room>) rooms.clone();
	}
	
	public void addRooms(Room room)
	{
		rooms.add(room);
	}
	
	public void removeRooms(Room room)
	{
		rooms.remove(room);
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
		return getName();
	}
}
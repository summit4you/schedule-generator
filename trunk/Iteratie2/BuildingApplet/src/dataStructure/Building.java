package dataStructure;

import java.io.Serializable;
import java.util.Vector;

import database.ID;

/**
 * 
 * @author matthiascaenepeel
 * @version2.0
 */

public class Building implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private String name;
	private Vector<Room> rooms;
	
	public Building(String newname)
	{
		name = newname;
		rooms = new Vector<Room>();
		//neighbours =new Vector<Building>();
	}
	
	public Building()
	{
		rooms = new Vector<Room>();
	}
	
	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}
	
	public Vector<Room> getRooms()
	{
		return (Vector<Room>) rooms.clone();
	}
	
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
	
	public void removeRooms()
	{
		this.rooms.removeAllElements();
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
	
	@Override
	public String toString() 
	{
		return getName();
	}
}

package dataStructure2;

import java.util.Vector;

import DataStructure.Room;

/**
 * 
 * @author matthiascaenepeel
 * @version2.0
 */

public class Building
{
	private String name;
	private Vector<Room> rooms;
	
	public Building(String newname)
	{
		name = newname;
		rooms = new Vector<Room>();
	}
	
	public Building()
	{
		
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
		return rooms;
	}

	public void setRooms(Vector<Room> rooms)
	{
		this.rooms = rooms;
	}
	
	public void addRooms(Room room)
	{
		rooms.add(room);
	}
	
	public void removeRooms(Room room)
	{
		rooms.remove(room);
	}
}

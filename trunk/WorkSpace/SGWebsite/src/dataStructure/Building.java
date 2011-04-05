package dataStructure;

import java.util.Vector;
import database.*;






/**
 * 
 * @author matthiascaenepeel
 * @version2.0
 */

public class Building implements Databasable
{
	private String name;
	private Vector<Room> rooms;
	private Vector<Building> neighbours;
	
	public Building(String newname)
	{
		name = newname;
		rooms = new Vector<Room>();
		neighbours =new Vector<Building>();
	}
	
	public Building()
	{
		rooms = new Vector<Room>();
		neighbours =new Vector<Building>();
	}
	
	@InDatabase
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
	
	//Hier komen de methodes en parameters voor de database.
	
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

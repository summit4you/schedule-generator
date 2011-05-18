package calendar;

import other.Globals;
import dataStructure.Building;
import dataStructure.Educator;
import dataStructure.Room;
import dataStructure.Subcourse;

/**
 * @author Zjef
 * @version 1.0
 */
public class Translator
{
	final public static String roomPrefix="R";
	final public static String educatorPrefix="E";
	final public static String subcoursePrefix="";
	
	public static String brToLocation(Building building,Room room)
	{
		return brToLocation(building.getName(), room.getLocation(), building.getID().toString(),room.getID().toString());
	}
	
	public static String brToLocation(String building,String room,String buildingID,String roomID)
	{
		return building+" "+room+"%"+buildingID+"%"+roomID;
	}
	
	public static String getCalendarFileName(Subcourse subcourse)
	{
		return getCalendarFileNameSubcourse(subcourse.getID().toString());
	}
	
	public static String getCalendarFileName(Room room)
	{
		return getCalendarFileNameRoom(room.getID().toString());
	}
	
	public static String getCalendarFileName(Educator educator)
	{
		return getCalendarFileNameEducator(educator.getID().toString());
	}
	
	public static String getCalendarFileNameRoom(String id)
	{
		return Globals.calendarFolder+"/"+roomPrefix+id+IcsCalendar.fileExtension;
	}
	
	public static String getCalendarFileNameEducator(String id)
	{
		return Globals.calendarFolder+"/"+educatorPrefix+id+IcsCalendar.fileExtension;
	}
	
	public static String getCalendarFileNameSubcourse(String id)
	{
		return Globals.calendarFolder+"/"+subcoursePrefix+id+IcsCalendar.fileExtension;
	}
	
	public static IcsCalendar loadSubCourseCalendar(Subcourse subcourse)
	{
		return IcsCalendar.load(getCalendarFileName(subcourse));
	}
	
	public static IcsCalendar loadSubcourseCalendar(String id)
	{
		return IcsCalendar.load(getCalendarFileNameSubcourse(id));
	}
	
	public static IcsCalendar loadRoomCalendar(Room room)
	{
		return IcsCalendar.load(getCalendarFileName(room));
	}
	
	public static IcsCalendar loadRoomCalendar(String id)
	{
		return IcsCalendar.load(getCalendarFileNameRoom(id));
	}
	
	public static IcsCalendar loadEducatorCalendar(String id)
	{
		return IcsCalendar.load(getCalendarFileNameEducator(id));
	}
	
	public static IcsCalendar loadEducatorCalendar(Educator educator)
	{
		return IcsCalendar.load(getCalendarFileName(educator));
	}

}
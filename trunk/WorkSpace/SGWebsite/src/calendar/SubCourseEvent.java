package calendar;

import java.text.ParseException;
import java.util.Calendar;
import dataStructure.Building;
import dataStructure.Room;
import net.fortuna.ical4j.model.Date;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.PropertyFactoryImpl;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.property.DtStart;

/**
 * VEvent for subcourses.
 * @author Zjef
 * @version 1.0
 */
public class SubCourseEvent
{
	private VEvent event;
	
	/**
	 * The specified building and room must have ID's
	 */
	public SubCourseEvent(String descr,Calendar start,Calendar end,String educator,Building building,Room room)
	{
		this.event=new VEvent(Transformation.calendarToDate(start),Transformation.calendarToDate(end),descr);
		setLocation(building, room);
		setEducator(educator);
	}
	
	protected SubCourseEvent(VEvent event)
	{
		this.event=event;
	}
	
	public VEvent getEvent()
	{
		return event;
	}
	
	public void setProperty(String property,String value)
	{
		Property p;
		if ((p=event.getProperty(property))!=null)
		{
			try
			{
				p.setValue(value);
			} catch (Exception e)
			{
			}
		}
		else
		{
			PropertyFactoryImpl f=PropertyFactoryImpl.getInstance();
			p=f.createProperty(property);
			try
			{
				p.setValue(value);
			} catch (Exception e)
			{
			}
			event.getProperties().add(p);
		}
	}
	
	/**
	 * The specified building and room must have ID's
	 */
	public void setLocation(Building building,Room room)
	{
		setProperty(Property.LOCATION,Translator.brToLocation(building, room));
	}
	
	public void setLocation(String buildingName,String roomName,String buildingID,String roomID)
	{
		setProperty(Property.LOCATION,Translator.brToLocation(buildingName,roomName, buildingID, roomID));
	}
	
	public void setEducator(String name)
	{
		setProperty(Property.ORGANIZER,name.replaceAll(" ","_"));
	}
	
	public Calendar getStart()
	{
		return Transformation.dateToCalendar(event.getStartDate().getDate());
	}
	
	public Calendar getEnd()
	{
		return Transformation.dateToCalendar(event.getEndDate().getDate());
	}
	
	public void setTime(String date,String start,String end,String dateFormat)
	{
		event.getStartDate().getDate().setTime(Transformation.strToDate(date, dateFormat, start).getTime());
		event.getEndDate().getDate().setTime(Transformation.strToDate(date,dateFormat,end).getTime());
	}
}
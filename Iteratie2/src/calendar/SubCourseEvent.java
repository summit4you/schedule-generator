package calendar;

import java.net.SocketException;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Iterator;

import dataStructure.Building;
import dataStructure.Room;
import net.fortuna.ical4j.model.Date;
import net.fortuna.ical4j.model.DateTime;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.PropertyFactoryImpl;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.property.DtEnd;
import net.fortuna.ical4j.model.property.DtStart;
import net.fortuna.ical4j.model.property.Uid;
import net.fortuna.ical4j.util.UidGenerator;

/**
 * VEvent for subcourses.
 * @author Zjef
 * @version 1.0
 */
public class SubCourseEvent extends SGEvent
{
	
	/**
	 * The specified building and room must have ID's
	 */
	public SubCourseEvent(String descr,Calendar start,Calendar end,String educator,Building building,Room room)
	{
		this.event=new VEvent(Transformation.calendarToDate(start),Transformation.calendarToDate(end),descr);
		setUID();
		setLocation(building, room);
		setEducator(educator);
	}
	
	public SubCourseEvent()
	{
		event=new VEvent();
		setUID();
	}
	
	private void setUID()
	{
		try
		{
			Uid newUid=new UidGenerator("1").generateUid();
			Uid uid=(Uid) event.getProperty(Property.UID);
			if (uid!=null)
			{
				event.getProperties().remove(uid);
			}
			event.getProperties().add(newUid);
		} catch (SocketException e)
		{
			e.printStackTrace();
		}
	}
	
	protected SubCourseEvent(VEvent event)
	{
		this.event=event;
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
	
	public void setTime(Calendar start,Calendar end)
	{
		if (event.getStartDate()==null)
		{
			event.getProperties().add(new DtStart(new DateTime(start.getTime())));
		}
		else
		{
			event.getStartDate().getDate().setTime(start.getTime().getTime());
		}
		if (event.getEndDate()==null)
		{
			event.getProperties().add(new DtEnd(new DateTime(end.getTime())));
		}
		else
		{
			event.getEndDate().getDate().setTime(end.getTime().getTime());
		}
	}
	
	public void setTime(String date,String start,String end,String dateFormat)
	{
		if (event.getStartDate()==null)
		{
			event.getProperties().add(new DtStart(new DateTime(Transformation.strToDate(date,dateFormat,start).getTime())));
		}
		else
		{
			event.getStartDate().getDate().setTime(Transformation.strToDate(date, dateFormat,start).getTime());
		}
		if (event.getEndDate()==null)
		{
			event.getProperties().add(new DtEnd(new DateTime(Transformation.strToDate(date,dateFormat,end).getTime())));
		}
		else
		{
			event.getEndDate().getDate().setTime(Transformation.strToDate(date, dateFormat,end).getTime());
		}
	}
	
	public void setSummary(String summary)
	{
		setProperty(Property.SUMMARY,summary);
	}
	
	@Override
	public SubCourseEvent clone()
	{
		SubCourseEvent v=new SubCourseEvent();
		v.event=new VEvent();
		Iterator it=this.event.getProperties().iterator();
		while (it.hasNext())
		{
			Object o=it.next();
			try
			{
				if (((Property)o).getName()!=Property.DTSTAMP)
				{
					v.event.getProperties().add(((Property)o).copy());
				}
			} catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		v.setUID();
		return v;
	}
}
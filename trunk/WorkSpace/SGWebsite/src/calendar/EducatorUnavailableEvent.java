package calendar;

import java.net.SocketException;
import java.util.Calendar;

import dataStructure.Building;
import dataStructure.Room;

import net.fortuna.ical4j.model.Date;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.util.UidGenerator;

public class EducatorUnavailableEvent
{
	private VEvent event;
	private String descr="EducatorUnavailable";
	
	public EducatorUnavailableEvent(Calendar start,Calendar end)
	{
		this.event=new VEvent(Transformation.calendarToDate(start),Transformation.calendarToDate(end),descr);
	}
	
	public EducatorUnavailableEvent(Date start,Date end)
	{
		this.event=new VEvent(start,end,descr);
	}
	
	public EducatorUnavailableEvent()
	{
		event=new VEvent();
		try
		{
			event.getProperties().add(new UidGenerator("1").generateUid());
		} catch (SocketException e)
		{
			e.printStackTrace();
		}
	}
}

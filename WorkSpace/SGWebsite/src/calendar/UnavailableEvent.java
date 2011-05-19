package calendar;

import java.net.SocketException;
import java.util.Calendar;

import net.fortuna.ical4j.model.Component;
import net.fortuna.ical4j.model.Date;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.property.DtEnd;
import net.fortuna.ical4j.model.property.DtStart;
import net.fortuna.ical4j.model.property.RRule;
import net.fortuna.ical4j.util.UidGenerator;

public class UnavailableEvent
{
	private VEvent event;
	private String descr="Unavailable";
	
	public UnavailableEvent(Calendar start,Calendar end)
	{
		super();
		this.event=new VEvent(Transformation.calendarToDate(start),Transformation.calendarToDate(end),descr);
	}
	
	public  UnavailableEvent(Calendar start,Calendar end,RRule rrule)
	{
		super(); 
		event.getProperties().add(rrule); 
		event.getProperties().add(descr); 
		event.getProperties().add(new DtStart(Transformation.calendarToDate(start))); 
		event.getProperties().add(new DtEnd(Transformation.calendarToDate(end))); 
	}
	
	public UnavailableEvent(Date start,Date end)
	{
		this.event=new VEvent(start,end,descr);
	}
	
	public UnavailableEvent()
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

	public Component getEvent()
	{
		return event;
	}
}

package calendar;

import java.net.SocketException;
import java.util.Calendar;

import net.fortuna.ical4j.model.Component;
import net.fortuna.ical4j.model.Date;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.util.UidGenerator;

public class UnavailableEvent
{
	private VEvent event;
	private String descr="Unavailable";
	
	public UnavailableEvent(Calendar start,Calendar end)
	{
		this.event=new VEvent(Transformation.calendarToDate(start),Transformation.calendarToDate(end),descr);
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

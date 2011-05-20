package calendar;

import java.net.SocketException;
import java.util.Calendar;

import dataStructure.Building;
import dataStructure.Room;

import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.PropertyFactoryImpl;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.property.Uid;
import net.fortuna.ical4j.util.UidGenerator;

public class SGEvent 
{
	protected VEvent event;
	
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
	
	public SGEvent()
	{
		
	}
	
	public SGEvent(String descr,Calendar start,Calendar end)
	{
		this.event=new VEvent(Transformation.calendarToDate(start),Transformation.calendarToDate(end),descr);
		setUID();
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
}

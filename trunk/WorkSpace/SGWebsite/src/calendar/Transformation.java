package calendar;

import java.text.ParseException;
import java.util.Calendar;
import java.util.GregorianCalendar;

import net.fortuna.ical4j.model.Date;
import net.fortuna.ical4j.model.DateTime;

/**
 * @author Zjef
 * @version 1.0
 */
public class Transformation
{
	public static Calendar dateToCalendar(Date date)
	{
		Calendar c=Calendar.getInstance();
		c.setTime(date);
		return c;
	}

	public static Date calendarToDate(Calendar calendar)
	{
		return new DateTime(calendar.getTime());
	}

	public static Date strToDate(String date,String format,String hour)
	{
		try
		{
			Date formatted=new Date(date,format);
			String[] hhmm=hour.split(":");
			formatted.setHours(Integer.parseInt(hhmm[0]));
			formatted.setMinutes(Integer.parseInt(hhmm[1]));
			return formatted;
		} catch (ParseException e)
		{
			e.printStackTrace();
			return null;
		}
	}
	
	public static Calendar strToCal(String date,String format,String hour)
	{
		return dateToCalendar(strToDate(date,format,hour));
	}
}

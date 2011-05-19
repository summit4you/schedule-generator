package pseudoServlets;



import java.text.ParseException;

import javax.servlet.http.HttpServletRequest;

import pseudoServlets.tools.CalendarTools;

import net.fortuna.ical4j.model.Date;
import net.fortuna.ical4j.model.Recur;
import net.fortuna.ical4j.model.WeekDay;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.property.CalScale;
import net.fortuna.ical4j.model.property.RRule;
import net.fortuna.ical4j.model.property.Uid;

import calendar.Transformation;
import calendar.UnavailableEvent;
import calendar.IcsCalendar;

import dataStructure.Educator;

import sessionTracking.Session;

public class ConstraintsEducator extends PseudoServlet
{
	private final static String dateSeperator="/";
	private final static String hourSeperator=":";
	private String popup;
	
	/**
	 *  <b>Parse method that parses text of the following format DD/MM/YYYY/HH:MM </b></br>
	 *  If an error occurs the successfully parsed info is returned. 
	 * @param date format DD/MM/YYYY/HH:MM
	 * @return Calendar with corresponding date
	 */
	private static java.util.Calendar parseDate(String date)
	{	
		int index = date.indexOf(dateSeperator);
		java.util.Calendar cal = java.util.Calendar.getInstance();
		try
		{
			String day = date.substring(0,index);
			date=date.substring(index+1);
			cal.set(java.util.Calendar.DAY_OF_MONTH,Integer.parseInt(day));
			
			
			index = date.indexOf(dateSeperator);
			String month = date.substring(0,index);
			date=date.substring(index+1);	
			cal.set(java.util.Calendar.MONTH,Integer.parseInt(month));
			
			index = date.indexOf(dateSeperator);
			String year = date.substring(0,index);
			date=date.substring(index+1);
			cal.set(java.util.Calendar.YEAR,Integer.parseInt(year));
			
			index = date.indexOf(hourSeperator);
			String hour = date.substring(0,index);
			date=date.substring(index+1);
			cal.set(java.util.Calendar.HOUR_OF_DAY,Integer.parseInt(hour));
			
			String minute=  date;
			cal.set(java.util.Calendar.MINUTE,Integer.parseInt(minute));
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}	
		return cal;
	}
	
	@Override
	//TODO check generated calendars
	public String processRequest(RequestType type, HttpServletRequest request,Session session)
	{
		if (type==RequestType.GET)
		{
			if (request.getParameter("delete")==null)
			{	
				// een gewone get, stuur de pagina door met de form en huidige kalender
				String response = replaceTags(template, "MASTERSERVLET", createLink(session));
				return replaceTags(response, "CALENDARLINK", CalendarTools.generateEditablePHPiCalendarLink(session.getAccount().getLanguage(), createLink(session)+"&delete=true"));
			}
			else
			{
				// een delete request, vraag de gebruiker bevestiging
				String response = replaceTags(popup, "MASTERSERVLET", createlink(session));
				return replaceTags(response, "UID", request.getParameter("uid"));
			}
		}
		else if (type==RequestType.POST)
		{
			String uid = request.getParameter("uid");
			Educator educator = Educator.class.cast(session.getAccount().getData());
			IcsCalendar calendar = educator.getCalendar();
			if (uid==null)
			{
				if (request.getParameter("change").equals("single"))
				{
					String date = request.getParameter("inputDate");	  
					String starthour = request.getParameter("startHour"); 
					String stophour = request.getParameter("stopHour");   
					calendar.addUnavailableEvent(new UnavailableEvent(parseDate(date+dateSeperator+starthour),parseDate(date+dateSeperator+stophour)));	
				}
				else if (request.getParameter("change").equals("week"))
				{
					//TODO change day selection to "MO" "TU" "WE" ...
					String day = request.getParameter("day"); 
					String startDate = request.getParameter("startDate"); 
					String stopDate = request.getParameter("stopDate"); 
					String startHour = request.getParameter("startHour"); 
					String stopHour = request.getParameter("stopHour"); 
					
					Recur recur = new Recur(Recur.WEEKLY, Transformation.calendarToDate(parseDate(startDate+dateSeperator+"00:00")));
					recur.getDayList().add(new WeekDay(day));
					recur.setInterval(1);
					recur.setWeekStartDay(WeekDay.MO.getDay()); 
					RRule rrule = new RRule(recur); 
	
					calendar.addUnavailableEvent(new UnavailableEvent(Transformation.calendarToDate(parseDate(startDate+dateSeperator+startHour)),Transformation.calendarToDate(parseDate(startDate+dateSeperator+stopHour))));
				}
				else if (request.getParameter("change").equals("long"))
				{
					String startDate = request.getParameter("startDate");
					String stopDate = request.getParameter("stopDate");
					calendar.addUnavailableEvent(new UnavailableEvent(parseDate(startDate+dateSeperator+"00:00"),parseDate(startDate+dateSeperator+"24:00")));
				}
				else
				{
					return "ERROR, change type not known";
				}
			}
			else
			{
				// a delete confirmation has been sent.
				Uid ui = new Uid(uid);
				calendar.removeEvent(calendar.getEvent(ui));
				calendar.write();
				return "<html><head></head><body><script>parent.tb_remove(); parent.location.reload(1);</script></body></html>";
			}
		}
	}

	@Override
	public TabName getTabName()
	{
		return PseudoServlet.TabName.ConstraintsEducator;
	}
}

package pseudoServlets;



import javax.servlet.http.HttpServletRequest;

import net.fortuna.ical4j.model.property.Uid;

import calendar.UnavailableEvent;
import calendar.IcsCalendar;

import dataStructure.Educator;

import pseudoServlets.PseudoServlet.RequestType;
import pseudoServlets.tools.CalendarTools;

import sessionTracking.Session;
import sun.util.resources.CalendarData;

public class ConstraintsEducator extends PseudoServlet
{
	private final String dateSeperator="/";
	private final String hourSeperator=":";
	private String popup;
	
	// DD/MM/YYYY/HH:MM
	private java.util.Calendar parseDate(String date)
	{	
		String day = date.substring(0,date.indexOf(dateSeperator));
		date=date.substring(date.indexOf("dateSeperator"));
		
		String month = date.substring(0,date.indexOf(dateSeperator));
		date=date.substring(date.indexOf("dateSeperator"));	
		
		String year = date.substring(0,date.indexOf(dateSeperator));
		date=date.substring(date.indexOf("dateSeperator"));
		
		String hour = date.substring(0,date.indexOf(hourSeperator));
		
		java.util.Calendar cal = java.util.Calendar.getInstance();
		cal.set(java.util.Calendar.YEAR,Integer.parseInt(year));
		cal.set(java.util.Calendar.MONTH,Integer.parseInt(month));
		cal.set(java.util.Calendar.DAY_OF_MONTH,Integer.parseInt(day));
		cal.set(java.util.Calendar.HOUR_OF_DAY,Integer.parseInt(hour));

		return cal;
	}
	
	@Override
	protected void init()
	{
		super.init();		
		popup=loadTemplate("deleteConstraint.tpl");
	}
	
	@Override
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
			Educator educator = Educator.class.cast(session.getAccount().getData());
			IcsCalendar calendar = educator.getCalendar();
			String uid = request.getParameter("uid");
			if (uid==null)
			{
			if (request.getParameter("change").equals("single"))
			{
				//TODO 
				String date = request.getParameter("inputDate");	  // DD/MM/YYYY
				String starthour = request.getParameter("startHour"); // starting hour UU:MM
				String stophour = request.getParameter("stopHour");   // stop hour
				calendar.addUnavailableEvent(new UnavailableEvent(parseDate(date+dateSeperator+starthour),parseDate(date+dateSeperator+stophour)));
				
			}
			else if (request.getParameter("change").equals("week"))
			{
				//TODO aan kalender toevoegen
				String day = request.getParameter("day"); // day of the week which is not possible 1->7
				String startweek = request.getParameter("startWeek"); // week at which the unavailability starts, maybe change to date  DD/MM/YYYY
				String stopweek = request.getParameter("stopWeek"); // week at which the unavailability stops, maybe change to date DD/MM/YYYY
				String starthour = request.getParameter("starthour"); // hour at which the weekly unavailability starts
				String stophour = request.getParameter("stophour"); // hour at which the weekly unavailability stops
			}
			else if (request.getParameter("change").equals("long"))
			{
				String startDate = request.getParameter("startDate");
				String stopDate = request.getParameter("stopDate");
				calendar.addUnavailableEvent(new UnavailableEvent(parseDate(startDate+dateSeperator+"00:00"),parseDate(startDate+dateSeperator+"00:00")));
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

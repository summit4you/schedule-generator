package pseudoServlets;



import java.text.ParseException;

import javax.servlet.http.HttpServletRequest;

import other.Globals;

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
	private String popup;
	
	public ConstraintsEducator()
	{
		templateFile="ConstraintsEducator.tpl";
	}
	
	@Override
	protected void init()
	{
		super.init();		
		popup=loadTemplate("deleteConstraint.tpl");
	}
	
	@Override
	//TODO check generated calendars
	public String processRequest(RequestType type, HttpServletRequest request,Session session)
	{
		System.out.println(type);
		
		if (type==RequestType.GET)
		{
			if (request.getParameter("delete")==null)
			{	
				// een gewone get, stuur de pagina door met de form en huidige kalender
				String response = replaceTags(template, "MASTERSERVLET", createLink(session));
				if (session.getAccount().getData() instanceof Educator)
				{
				return replaceTags(response, "CALENDARLINK", CalendarTools.generateEditablePHPiCalendarLink((Educator) session.getAccount().getData(),session.getAccount().getLanguage(), Globals.MainSiteLink+createLink(session)+"&delete=true"));
				}
				else
				{
					return "ERROR, user is not an educator";
				}
			}
			else
			{
				// een delete request, vraag de gebruiker bevestiging
				String response = replaceTags(popup, "MASTERSERVLET", createLink(session));
				return replaceTags(response, "UID", request.getParameter("uid"));
			}
		}
		else 
		{
			String uid = request.getParameter("UID");
			Educator educator = Educator.class.cast(session.getAccount().getData());
			IcsCalendar calendar = educator.getCalendar();
			if (uid==null)
			{
				String dateFormat=request.getParameter("dateFormat");	
				if (request.getParameter("change").equals("single"))
				{
					String date = request.getParameter("inputDate");	  
					String starthour = request.getParameter("startHour"); 
					String stophour = request.getParameter("stopHour");  
					System.out.println(date +" "+starthour+" "+stophour);
					calendar.addUnavailableEvent(new UnavailableEvent(Transformation.strToCal(date,dateFormat,starthour),Transformation.strToCal(date,dateFormat,stophour)));	
				}
				else if (request.getParameter("change").equals("week"))
				{
					//TODO change day selection to "MO" "TU" "WE" ...
					String day = request.getParameter("day"); 
					String startDate = request.getParameter("startDate"); 
					String stopDate = request.getParameter("stopDate"); 
					String startHour = request.getParameter("startHour"); 
					String stopHour = request.getParameter("stopHour"); 
					
					System.out.println(day+startDate+stopDate+startHour+stopHour);
					
					Recur recur = new Recur(Recur.WEEKLY, Transformation.strToDate(stopDate,dateFormat,stopHour));
					recur.getDayList().add(new WeekDay(day));
					recur.setInterval(1);
					recur.setWeekStartDay(WeekDay.MO.getDay()); 
					RRule rrule = new RRule(recur); 
	
					calendar.addUnavailableEvent(new UnavailableEvent(Transformation.strToCal(startDate,dateFormat,startHour),Transformation.strToCal(startDate,dateFormat,stopHour)));
				}
				else if (request.getParameter("change").equals("long"))
				{
					String startDate = request.getParameter("startDate");
					String stopDate = request.getParameter("stopDate");
					calendar.addUnavailableEvent(new UnavailableEvent(Transformation.strToCal(startDate,dateFormat,"00:00"),Transformation.strToCal(stopDate,dateFormat,"23:00")));	
				}
				else
				{
					return "ERROR, change type not known";
				}
				calendar.write();
				String response = replaceTags(template, "MASTERSERVLET", createLink(session));
				return replaceTags(response, "CALENDARLINK", CalendarTools.generateEditablePHPiCalendarLink((Educator) session.getAccount().getData(),session.getAccount().getLanguage(), Globals.MainSiteLink+createLink(session)+"&delete=true"));
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

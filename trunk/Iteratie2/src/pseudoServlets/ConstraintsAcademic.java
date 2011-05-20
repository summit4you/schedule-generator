package pseudoServlets;

import java.text.ParseException;
import java.util.Calendar;

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

import calendar.SemesterEndEvent;
import calendar.SemesterStartEvent;
import calendar.Transformation;
import calendar.Translator;
import calendar.UnavailableEvent;
import calendar.IcsCalendar;

import dataStructure.Educator;

import sessionTracking.Session;

public class ConstraintsAcademic extends PseudoServlet
{

		private String popup;
		
		public ConstraintsAcademic()
		{
			templateFile="ConstraintsAcademic.tpl";
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
					return replaceTags(response, "CALENDARLINK",CalendarTools.generateEditablePHPiCalendarLink(session.getAccount().getLanguage(), 
							createLink(session)+"&delete=true")+Translator.semesterCalendar+","+Translator.holidayCalendar);
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
				if (uid==null)
				{
					String dateFormat=request.getParameter("dateFormat");	
					if (request.getParameter("change").equals("single"))
					{
						IcsCalendar holCalendar = Translator.loadHolidayCalendar();
						String date = request.getParameter("inputDate");	  
						String starthour = request.getParameter("startHour"); 
						String stophour = request.getParameter("stopHour");  
						System.out.println(date +" "+starthour+" "+stophour);
						holCalendar.addUnavailableEvent(new UnavailableEvent(Transformation.strToCal(date,dateFormat,starthour),Transformation.strToCal(date,dateFormat,stophour)));	
						holCalendar.write();
					}
					else if (request.getParameter("change").equals("week"))
					{
						IcsCalendar holCalendar = Translator.loadHolidayCalendar();
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
						holCalendar.addUnavailableEvent(new UnavailableEvent(Transformation.strToCal(startDate,dateFormat,startHour),Transformation.strToCal(startDate,dateFormat,stopHour)));
						holCalendar.write();
					}
					else if (request.getParameter("change").equals("long"))
					{
						IcsCalendar holCalendar = Translator.loadHolidayCalendar();
						String startDate = request.getParameter("startDate");
						String stopDate = request.getParameter("stopDate");
						holCalendar.addUnavailableEvent(new UnavailableEvent(Transformation.strToCal(startDate,dateFormat,"00:00"),Transformation.strToCal(stopDate,dateFormat,"23:00")));	
						holCalendar.write();
					}
					else if (request.getParameter("change").equals("SemChange"))
					{
						IcsCalendar semCalendar = Translator.loadSemesterCalendar();
						String startDate = request.getParameter("startDate");
						String stopDate = request.getParameter("stopDate");
						semCalendar.addSGEvent(new SemesterStartEvent(Transformation.strToCal(startDate,dateFormat,"00:00"),Transformation.strToCal(startDate,dateFormat,"23:00")));
						semCalendar.addSGEvent(new SemesterEndEvent(Transformation.strToCal(stopDate,dateFormat,"00:00"),Transformation.strToCal(stopDate,dateFormat,"23:00")));
						semCalendar.write();
					}
					else
					{
						return "ERROR, change type not known";
					}
					String response = replaceTags(template, "MASTERSERVLET", createLink(session));
					return replaceTags(response, "CALENDARLINK",CalendarTools.generateEditablePHPiCalendarLink(session.getAccount().getLanguage(), 
							createLink(session)+"&delete=true")+Translator.semesterCalendar+","+Translator.holidayCalendar);
				}
				else
				{
					// a delete confirmation has been sent.
					Uid ui = new Uid(uid);
					IcsCalendar holCalendar = Translator.loadHolidayCalendar();
					holCalendar.removeEvent(holCalendar.getEvent(ui));
					holCalendar.write();
					return "<html><head></head><body><script>parent.tb_remove(); parent.location.reload(1);</script>##Chrome_Error##</body></html>";
				}
			}
		}

		@Override
		public TabName getTabName()
		{
			return PseudoServlet.TabName.ConstraintsAcademic;
		}
	}



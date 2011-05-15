package pseudoServlets;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Vector;
import htmlBuilder.Site;
import javax.servlet.http.HttpServletRequest;

import net.fortuna.ical4j.model.property.Uid;

import calendar.IcsCalendar;
import calendar.SubCourseEvent;
import calendar.Translator;
import pseudoServlets.tools.CalendarTools;
import pseudoServlets.tools.PSTools;
import dataStructure.Building;
import dataStructure.Faculty;
import dataStructure.Program;
import dataStructure.Room;
import database.Database;
import database.ID;
import database.Search;
import sessionTracking.Session;

public class EditCalendar extends PseudoServlet
{
	private static final String programTag="programs";
	private static final String editPopupTag="editPopup";
	private static final String roomSelectorTag="roomSelector";
	private static final String editTag="edit";
	private static final String newEventTag="newEvent";
	private static final String templateFilePopup="editCalendarPopup.tpl";
	private static String templatePopup;
	
	public EditCalendar()
	{
		super();
		templateFile="editCalendar.tpl";
	}
	
	@Override
	protected void init()
	{
		super.init();
		templatePopup=loadTemplate(templateFilePopup);
	}
	
	@Override
	public String processRequest(RequestType type, HttpServletRequest request, Session session)
	{
		if (request.getParameter(editTag)!=null && request.getParameter(editTag).equals("true"))
		{
			return editEvent(request);
		}
		else if (request.getParameter(editPopupTag)!=null && request.getParameter(editPopupTag).equals("true"))
		{
			return generatePopup(request,session);
		}
		else
		{
			if (request.getParameter(programTag)==null)
			{
				return generateMainPage(session);
			}
			else
			{
				return generateCalendarFramePage(request.getParameter(programTag),session);
			}
		}
	}
	
	private String editEvent(HttpServletRequest request)
	{
		//TODO process edit
		if (request.getParameter(newEventTag)!=null && request.getParameter(newEventTag).equals("true"))
		{
			
		}
		else
		{
			IcsCalendar cal=Translator.loadSubcourseCalendar(request.getParameter("cal"));
			SubCourseEvent v=cal.getSubCourseEvent(new Uid(request.getParameter("uid")));
			v.setTime(request.getParameter("inputDate"),request.getParameter("startTime"),request.getParameter("endTime"),request.getParameter("dateFormat"));
			Database db=getDB();
			db.connect();
			Building b=db.read(new Search(Building.class,request.getParameter("building")));
			Room r=db.read(new Search(Room.class,request.getParameter("room")));
			db.disconnect();
			v.setLocation(b.getName(),r.getLocation(),request.getParameter("building"),request.getParameter("room"));
			cal.write();
			//TODO update room calendar
		}
		return "<html><head></head><body><script>parent.tb_remove(); parent.location.reload(1);</script></body></html>";
	}
	
	private String generatePopup(HttpServletRequest request,Session session)
	{
		if (request.getParameter(roomSelectorTag)!=null && request.getParameter(roomSelectorTag).equals("true"))
		{
			String res="<select name='room' id='room'>";
			Database db=getDB();
			db.connect();
			Building b=db.read(new Search(Building.class,"getID",request.getParameter("building")));
			db.disconnect();
			res+=PSTools.createSelectOptions(b.getRooms());	//TODO get rooms which are available on the specified hour
			res+="</select>";
			return res;
		}
		else
		{
			String page=replaceTags(templatePopup,"REQUEST_LINK",createLink(session)+"&"+roomSelectorTag+"=true"+"&"+editPopupTag+"=true");
			page=replaceTags(page,"SUBMIT_LINK",createLink(session)+"&"+editTag+"=true");
			page=replaceTags(page,"BUILDINGS",PSTools.createSelectOptions(PSTools.loadObjects(Building.class)));
			page=replaceTags(page,"DATEFORMAT","dd/MM/yyyy");
			page=replaceTags(page,"DATE",request.getParameter("date")==null?"":request.getParameter("date"));
			page=replaceTags(page,"START",request.getParameter("start")==null?"":request.getParameter("start"));
			page=replaceTags(page,"END",request.getParameter("end")==null?"":request.getParameter("end"));
			page=replaceTags(page,"INFO",request.getParameter("info")==null?"":request.getParameter("info"));
			page=replaceTags(page,"CAL",request.getParameter("cal")==null?"":request.getParameter("cal"));
			page=replaceTags(page,"UID",request.getParameter("uid")==null?"":request.getParameter("uid"));
			page=replaceTags(page,"INIT_BUILDING",request.getParameter("building")==null?"":request.getParameter("building"));
			page=replaceTags(page,"INIT_ROOM",request.getParameter("room")==null?"":request.getParameter("room"));
			return page;
		}
	}
	
	private String generateCalendarFramePage(String programs,Session session)
	{
		return "<html><head></head><body><script>parent.document.getElementById('calendarFrame').src='"+CalendarTools.generateEditablePHPiCalendarLink(getPrograms(programs),session.getAccount().getLanguage(),createLink(session)+"&"+editPopupTag+"=true")+"';</script></body></html>";
	}
	
	private Vector<Program> getPrograms(String idString)
	{
		if (idString.length()==0)
		{
			return new Vector<Program>();
		}
		Database db=getDB();
		db.connect();
		String[] ids=idString.replaceAll("_","").split(";");
		Vector<Program> progs=db.readAll(new database.Search(Program.class,Search.copyMethodString("getID",ids.length),(Object[])ids).setAndOr(false));
		db.disconnect();
		return progs;
	}
	
	private String generateMainPage(Session session)
	{
		Vector<Faculty> facs=PSTools.loadObjects(Faculty.class);
		String page=replaceTags(template,"LINK",createLink(session));
		page=replaceTags(page,"FACULTY_LIST",PSTools.createSelectOptions(facs));
		page=replaceTags(page,"TEXTS",getTexts(facs,true));
		page=replaceTags(page,"VALUES",getTexts(facs,false));
		return page;
	}
	
	/**
	 * @param texts - true for text, false for value
	 */
	private String getTexts(Vector<Faculty> facs,boolean texts)
	{
		String res="[";
		for (Faculty f:facs)
		{
			res+="[";
			for (Program p:f.getPrograms())
			{
				if (texts)
				{
					res+="'"+p.getName()+"',";
				}
				else
				{
					res+="'"+p.getID().toString()+"',";
				}
			}
			res=res.substring(0,res.length()-1)+"],";
		}
		return res.substring(0,res.length()-1)+"]";
	}
	
	@Override
	public String getTabName()
	{
		return PseudoServlet.TabName.EditCalendar.toLanguageTag();
	}
}
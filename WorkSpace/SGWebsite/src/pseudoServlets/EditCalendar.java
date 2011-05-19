package pseudoServlets;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import net.fortuna.ical4j.model.property.Uid;
import calendar.IcsCalendar;
import calendar.SubCourseEvent;
import calendar.Translator;
import pseudoServlets.tools.CalendarTools;
import pseudoServlets.tools.PSTools;
import dataStructure.Building;
import dataStructure.Course;
import dataStructure.Educator;
import dataStructure.Faculty;
import dataStructure.Program;
import dataStructure.Room;
import dataStructure.Subcourse;
import database.Databasable;
import database.Database;
import database.Search;
import sessionTracking.Session;

public class EditCalendar extends PseudoServlet
{
	private static final String programTag="programs";
	private static final String editPopupTag="editPopup";
	private static final String createPopupTag="createPopup";
	private static final String roomSelectorTag="roomSelector";
	private static final String deleteTag="deleteEvent";
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
		if (request.getParameter(roomSelectorTag)!=null && request.getParameter(roomSelectorTag).equals("true"))
		{
			return generateRoomSelector(request);
		}
		if (request.getParameter(editTag)!=null && request.getParameter(editTag).equals("true"))
		{
			return processEdit(request);
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
	
	private static void repeatWeekly(SubCourseEvent event,IcsCalendar cal,int repTimes)
	{
		for (int i=0;i<repTimes-1;i++)
		{
			SubCourseEvent v=event.clone();
			Calendar start=v.getStart();
			Calendar end=v.getEnd();
			start.add(Calendar.DAY_OF_YEAR,(i+1)*7);
			end.add(Calendar.DAY_OF_YEAR,(i+1)*7);
			v.setTime(start,end);
			cal.addSubCourseEvent(v);
		}
	}
	
	private void editEvent(IcsCalendar cal,HttpServletRequest request)
	{
		boolean newEvent=request.getParameter(newEventTag)!=null && request.getParameter(newEventTag).equals("true");
		boolean repeat=request.getParameter("weekRep")!=null && request.getParameter("weekRep").equals("on");
		int repTimes;
		try
		{
			repTimes=Integer.parseInt(request.getParameter("repSpinner"));
		}
		catch(Exception e)
		{
			repTimes=0;
			repeat=false;
		}	
		
		SubCourseEvent event=newEvent?new SubCourseEvent():cal.getSubCourseEvent(new Uid(request.getParameter("uid")));
		
		if (event==null)
		{
			return;
		}
		
		if (request.getParameter(deleteTag)!=null && request.getParameter(deleteTag).equals("true"))
		{
			cal.removeEvent(event.getEvent());	
		}
		else
		{
			Database db=getDB();
			db.connect();
			Building b=db.readSingle(new Search(Building.class,request.getParameter("building")));
			Room r=db.readSingle(new Search(Room.class,request.getParameter("room")));
			
			event.setLocation(b.getName(),r.getLocation(),request.getParameter("building"),request.getParameter("room"));
			event.setTime(request.getParameter("inputDate"),request.getParameter("startTime"),request.getParameter("endTime"),request.getParameter("dateFormat"));
			
			if (newEvent)
			{
				event.setEducator(PSTools.implodeVector(db.readAll(new Search(request.getParameter("subcourse"),Educator.class,"getSubcourses"),Subcourse.class)));
				event.setSummary(db.readSingle(new Search(Subcourse.class,request.getParameter("subcourse"))).toString());
				cal.addSubCourseEvent(event);
				if (repeat && repTimes>1)
				{
					repeatWeekly(event, cal,repTimes);
				}
			}
			db.disconnect();
		}
	
		cal.write();
	}
	
	private String processEdit(HttpServletRequest request)
	{
		boolean newEvent=request.getParameter(newEventTag)!=null && request.getParameter(newEventTag).equals("true");
		
		IcsCalendar subcourseCal=Translator.loadSubcourseCalendar(request.getParameter(newEvent?"subcourse":"cal"));
		IcsCalendar roomCal=Translator.loadRoomCalendar(request.getParameter("room"));
		
		editEvent(subcourseCal,request);
		editEvent(roomCal,request);
		
		if (newEvent)
		{
			return "<html><head></head><body><script>parent.tb_remove(); parent.document.getElementById('calendarFrame').src=parent.document.getElementById('calendarFrame').src;##Chrome_Error##</script></body></html>";
		}
		else
		{
			return "<html><head></head><body><script>parent.location.reload(1);parent.tb_remove();##Chrome_Error##</script></body></html>";
		}
	}
	
	private String generateRoomSelector(HttpServletRequest request)
	{
		String res="<select name='room' id='room'>";
		Database db=getDB();
		db.connect();
		Building b=db.read(new Search(Building.class,"getID",request.getParameter("building")),Room.class);
		db.disconnect();
		res+=PSTools.createSelectOptions(b.getRooms());
		res+="</select>";
		return res;
	}
	
	private String generatePopup(HttpServletRequest request,Session session)
	{
		boolean create=request.getParameter(createPopupTag)!=null && request.getParameter(createPopupTag).equals("true");
		
		String page=replaceTags(templatePopup,"REQUEST_LINK",createLink(session)+"&"+roomSelectorTag+"=true");
		page=replaceTags(page,"SUBMIT_LINK",createLink(session)+"&"+editTag+"=true"+(create?"&"+newEventTag+"=true":""));
		page=replaceTags(page,"DELETE_LINK",createLink(session)+"&"+editTag+"=true&"+deleteTag+"=true");
		page=replaceTags(page,"BUILDINGS",PSTools.createSelectOptions(PSTools.loadObjects(Building.class,true)));
		page=replaceTags(page,"DATEFORMAT","##DateFormat##");
		page=replaceTags(page,"INFO",request.getParameter("info")==null?"":request.getParameter("info"));
		page=replaceTags(page,"INIT_BUILDING",request.getParameter("building")==null?"1":request.getParameter("building"));
		page=replaceTags(page,"INIT_ROOM",request.getParameter("room")==null?"":request.getParameter("room"));
		page=replaceTags(page,"CAL",request.getParameter("cal")==null?"":request.getParameter("cal"));
		if (create)
		{
			page=replaceTags(page,"DATE",new SimpleDateFormat(request.getParameter("DateFormat")).format(Calendar.getInstance().getTime()));
			page=replaceTags(page,"START","08:00");
			page=replaceTags(page,"END","10:00");
			page=replaceTags(page,"HIDDEN","");
			page=replaceTags(page,"INVHIDDEN","style='display:none'");
			Database db=getDB();
			db.connect();
			Program p=db.read(new Search(Program.class,request.getParameter("program")),Course.class,Subcourse.class);
			db.disconnect();
			page=replaceTags(page,"COURSES",PSTools.createSelectOptions(p.getCourses()));
			page=replaceTags(page,"INITLISTS","initCoupledLists('course','subcourse',"+getTexts(p.getCourses(),"getSubcourses",true)+","+getTexts(p.getCourses(),"getSubcourses",false)+")");
		}
		else
		{			
			page=replaceTags(page,"COURSES","");
			page=replaceTags(page,"HIDDEN","style='display:none'");
			page=replaceTags(page,"INVHIDDEN","");
			page=replaceTags(page,"INITLISTS","");
			page=replaceTags(page,"DATE",request.getParameter("date")==null?"":request.getParameter("date"));
			page=replaceTags(page,"START",request.getParameter("start")==null?"":request.getParameter("start"));
			page=replaceTags(page,"END",request.getParameter("end")==null?"":request.getParameter("end"));
			page=replaceTags(page,"UID",request.getParameter("uid")==null?"":request.getParameter("uid"));
		}
		return page;
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
		page=replaceTags(page,"TEXTS",getTexts(facs,"getPrograms",true));
		page=replaceTags(page,"VALUES",getTexts(facs,"getPrograms",false));
		page=replaceTags(page,"CREATELINK",createLink(session)+"&"+editPopupTag+"=true&"+createPopupTag+"=true&DateFormat=##DateFormat##");
		return page;
	}
	
	/**
	 * @param texts - true for text, false for value
	 */
	private static String getTexts(Vector<? extends Databasable> facs,String submethod,boolean texts)
	{
		String res="[";
		for (Databasable f:facs)
		{
			res+="[";
			try
			{
				Vector<? extends Databasable> vec=(Vector<? extends Databasable>)f.getClass().getMethod(submethod).invoke(f);
				if (vec.size()==0)
				{
					res+=",";	//dummy character
				}
				for (Databasable p:vec)
				{
					if (texts)
					{
						res+="'"+p.toString()+"',";
					}
					else
					{
						res+="'"+p.getID().toString()+"',";
					}
				}
			} catch (Exception e)
			{
				e.printStackTrace();
			}
			res=res.substring(0,res.length()-1)+"],";
		}
		return res.substring(0,res.length()-1)+"]";
	}

	@Override
	public TabName getTabName()
	{
		return TabName.EditCalendar;
	}
}
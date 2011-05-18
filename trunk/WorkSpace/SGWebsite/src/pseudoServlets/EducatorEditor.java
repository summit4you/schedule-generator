package pseudoServlets;

import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dataStructure.Course;
import dataStructure.Educator;
import dataStructure.Faculty;
import dataStructure.Program;
import dataStructure.Student;
import dataStructure.Subcourse;
import database.Database;
import database.Search;
import pseudoServlets.tools.PSTools;
import sessionTracking.Session;

/**
 * @author Zjef
 * @version 1.0
 */
public class EducatorEditor extends PseudoServletForApplet
{
	final private static String educatorTag="educators";
	final private static String facultiesTag="faculties";
	final private static String facultyTag="faculty";
	
	public EducatorEditor()
	{
		super("EducatorApplet");
	}
	
	@Override
	public String processRequest(RequestType type, HttpServletRequest request, Session session)
	{
		String res=replaceTags(template,"JNLP","EducatorApplet");
		res=replaceTags(res,"URL",createLink(session));
		res=replaceTags(res,"PARAMS","");
		return res;
	}

	@Override
	public TabName getTabName()
	{
		return TabName.EditEducators;
	}
	
	@Override
	public void processAppletRequest(HttpServletRequest request, HttpServletResponse response, Session session)
	{
		if ("true".equals(request.getParameter(educatorTag)))
		{
			readObjectFromApplet(request);
			String faculty=request.getParameter(facultyTag);
			Database db=getDB();
			db.connect();
			Faculty f=db.read(new Search(Faculty.class,faculty),Educator.class,Course.class,Subcourse.class);
			db.disconnect();
			sendObjectToApplet(response,f.getEducators());
		}
		else if("true".equals(request.getParameter(facultiesTag)))
		{
			readObjectFromApplet(request);
			sendObjectToApplet(response,PSTools.loadObjects(Faculty.class,Program.class));
		}
		else
		{
			receive(request,response);
		}
	}
}
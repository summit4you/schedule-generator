package pseudoServlets;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import dataStructure.Faculty;
import dataStructure.Program;
import database.Database;
import database.Search;
import pseudoServlets.tools.PSTools;
import sessionTracking.Session;

/**
 * @author Zjef
 * @version 1.0
 */
public class CourseEditor extends PseudoServletForApplet
{
	final private static String facultiesTag="faculties";
	final private static String zeroProgramTag="zeroProgram";
	
	public CourseEditor()
	{
		super("CourseApplet");
	}
	
	@Override
	public String processRequest(RequestType type, HttpServletRequest request, Session session)
	{
		String res=replaceTags(template,"JNLP","CourseApplet");
		res=replaceTags(res,"URL",createLink(session));
		res=replaceTags(res,"PARAMS","");
		return res;
	}

	@Override
	public TabName getTabName()
	{
		return TabName.EditCourses;
	}
	
	@Override
	public void processAppletRequest(HttpServletRequest request, HttpServletResponse response, Session session)
	{
		if ("true".equals(request.getParameter(zeroProgramTag)))
		{
			readObjectFromApplet(request);
			Database db=Database.getDB();
			db.connect();
			Program p=db.read(new Search(Program.class,"0"));
			db.disconnect();
			sendObjectToApplet(response,p);
		}
		else if("true".equals(request.getParameter(facultiesTag)))
		{
			readObjectFromApplet(request);
			sendObjectToApplet(response,PSTools.loadObjects(Faculty.class));
		}
		else
		{
			receive(request,response);
		}
	}
}
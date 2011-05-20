package pseudoServlets;

import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dataStructure.Course;
import dataStructure.Faculty;
import dataStructure.Program;
import dataStructure.Student;
import database.Database;
import database.Search;

import pseudoServlets.tools.PSTools;

import sessionTracking.Session;

/**
 * @author Zjef
 * @version 1.0
 */
public class StudentEditor extends PseudoServletForApplet
{
	final private static String studentTag="students";
	final private static String facultyTag="faculties";
	final private static String programTag="program";
	
	public StudentEditor()
	{
		super("StudentApplet");
	}
	
	@Override
	public String processRequest(RequestType type, HttpServletRequest request, Session session)
	{
		String res=replaceTags(template,"JNLP","StudentApplet");
		res=replaceTags(res,"URL",createLink(session));
		res=replaceTags(res,"PARAMS","");
		return res;
	}

	@Override
	public TabName getTabName()
	{
		return TabName.EditStudents;
	}
	
	@Override
	public void processAppletRequest(HttpServletRequest request, HttpServletResponse response, Session session)
	{
		if ("true".equals(request.getParameter(studentTag)))
		{
			readObjectFromApplet(request);
			String program=request.getParameter(programTag);
			Database db=getDB();
			db.connect();
			Vector<Student> students=db.readAll(new Search(program,Student.class,"getPrograms"),Program.class,Course.class);
			db.disconnect();
			sendObjectToApplet(response,students);
		}
		else if("true".equals(request.getParameter(facultyTag)))
		{
			readObjectFromApplet(request);
			sendObjectToApplet(response,PSTools.loadObjects(Faculty.class,Program.class));
		}
		else
		{
			receive(request, response);
		}
	}
}
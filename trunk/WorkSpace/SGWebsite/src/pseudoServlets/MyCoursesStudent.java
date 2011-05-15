package pseudoServlets;

import java.util.Vector;

import htmlBuilder.Site;
import htmlInterfaces.HTMLInterfaceTool;

import javax.servlet.http.HttpServletRequest;

import com.hp.gagawa.java.elements.*;

import dataStructure.Course;
import dataStructure.Educator;
import dataStructure.Faculty;
import dataStructure.Program;
import dataStructure.Student;
import database.Databasable;
import database.Database;
import database.Search;

import pseudoServlets.PseudoServlet.RequestType;
import sessionTracking.Session;

/**
 * <b>PseudoServlet that shows the user its courses</b> </br> </br> 
 * 
 * @version 1.0
 * @author Adam
 *
 */
public class MyCoursesStudent extends PseudoServlet
{
	private String popuptemplate;

	@Override
	protected void init()
	{
		super.init();
		popuptemplate=loadTemplate("EditCourse.tpl");
	}
	
	public MyCoursesStudent()
	{
		templateFile="MyCoursesStudent.tpl";
	}
	
	private String ListFaculties(Vector<Faculty> facs)
	{
		String result = new String();
		for (Faculty faculty : facs)
		{
			Option op = new Option();
			op.setValue(faculty.getID().toString()).appendText(faculty.getName());
			result+=op.write();
		}
		return result;
	}
	
	private String makeSelect(Vector<? extends Databasable> list,String name)
	{
		// <select name="program" onChange="getCourses()"></select>
		Select sel = new Select();
		sel.setName(name);
		if (name.equals("program"))
		{
		sel.setAttribute("onChange", "getCourses()");
		}
		for (Databasable i : list)
		{
			Option op = new Option();
			op.setValue(i.getID().toString());
			op.appendText(i.toString());
			sel.appendChild(op);
		}
		return sel.write();
	}
	

	private String ShowCoursesFromPrograms(Student student)
	{
		Table table = new Table();
		table.setBorder("0").setStyle("border-collapse:collapse");
		for (Program i : student.getPrograms())
		{
			Td col1 = new Td();
			col1.appendText(i.getName());
			Tr row1 = new Tr();
			row1.appendChild(col1);
			table.appendChild(row1);
			Td col2 = new Td();
			col2.appendText(HTMLInterfaceTool.changeToDataTable("courses", i.getCourses()));
			Tr row2 = new Tr();
			row2.appendChild(col2);
			table.appendChild(row2);
		}
		return table.write();
	}
	
	@Override
	public String processRequest(RequestType type, HttpServletRequest request, Session session)
	{
		if (type==RequestType.POST)
		{
			String course = request.getParameter("course");
			if (course!=null)
			{
				// a course has been sent, add the course to the student
				Database db = getDB();
				db.connect();
				database.Search s = new database.Search(Course.class,"getId",course);
				Course res = db.read(s);
				db.disconnect();
				if (res!=null)
				{
					Databasable data = session.getAccount().getData();
					if (data instanceof Student)
					{
						((Student) data).addCourse(res);
						db.connect();
						db.write(data);
						// send the add popup
						String response = replaceTags(popuptemplate,"MASTERSERVLET", createLink(session));
						s = new Search(Faculty.class);
						response = replaceTags(response, "FACULTIES", ListFaculties(db.<Faculty>readAll(s)));
						db.disconnect();
						return response;
					}
					else
					{
						return "ERROR, only students can add courses";
					}
					
				}
				else
				{
					// the id sent doesn't correspond to a course in the database
					return "ERROR, course not found";
				}
				
			}
			else
			{	
				String faculty = request.getParameter("faculty");
				String program = request.getParameter("program");
				if (faculty==null)
				{
					if (program==null)
					{
						// there was no program, nor a faculty nor a course present in the message
						return "ERROR, no data sent";
					}
					else
					{
						// program has been sent, return the select with the correct options (courses from program)
						Database db = getDB();
						db.connect();
						database.Search s = new database.Search(Faculty.class,"getId",faculty);
						Faculty searchresult = db.read(s); 
						db.disconnect();
						if (searchresult!=null)
						{
							return makeSelect(searchresult.getPrograms(),"program");
						}
						else
						{
							// the id that has been sent doen't exist in the database
							return "ERROR, faculty not found";
						}
					}
				}
				else
				{
					// a faculty has been sent, return the select with the correct options (programs from faculty)
					Database db = getDB();
					db.connect();
					database.Search s = new database.Search(Faculty.class,"getId",faculty);
					Faculty searchresult = db.read(s); 
					db.disconnect();
					if (searchresult!=null)
					{
						return makeSelect(searchresult.getPrograms(),"program");
					}
					else
					{
						// the id that has been sent doen't exist in the database
						return "ERROR, program not found";
					}
				}
				}
			}
		else
		{
			String course = request.getParameter("courseid");
			if (course==null)
			{
				String add = request.getParameter("add");
				if (add==null)
				{
					// a normal request has been sent, send the page back
					String response = replaceTags(template,"MASTERSERVLET", createLink(session)); 
					Databasable data = session.getAccount().getData();
					if (data instanceof Student)
					{
						response = replaceTags(response, "PROGRAMCOURSES",ShowCoursesFromPrograms((Student) data));
						return replaceTags(response, "FREE COURSES", HTMLInterfaceTool.changeToDataTable("freecourses", ((Student) data).getCourses()));
					}
					else
					{
						return "you are not a student";
					}
				}
				else
				{
					// the add button has been clicked, send the contents of the popup
					String response = replaceTags(popuptemplate,"MASTERSERVLET", createLink(session));
					Database db = getDB();
					db.connect();
					database.Search s = new Search(Faculty.class);
					response = replaceTags(response, "FACULTIES", ListFaculties(db.<Faculty>readAll(s)));
					db.disconnect();
					return response;
				}
			}
			else
			{
				// a delete request has been sent, delete the course (no need to send page back, datatables edits the page for us)
				Database db = getDB();
				db.connect();
				database.Search s = new database.Search(Course.class,course);
				Course res = db.read(s);
				db.disconnect();
				if (res!=null)
				{
					Databasable data = session.getAccount().getData();
					if (data instanceof Student)
					{
						((Student) data).removeCourse(res);
						db.connect();
						db.write(data);
						db.disconnect();
						return " ";
					}
					else
					{
						// user who tries to delete a course is not a student
						return "ERROR, only students can delete courses";
					}
				}
				else
				{
					// the sent id does not correspond with a course in the database
					return "ERROR, course has not been found";
				}
			}
		}
	}
	
	@Override
	public String getTabName()
	{
		return PseudoServlet.TabName.MyCoursesStudent.toLanguageTag();
	}
}

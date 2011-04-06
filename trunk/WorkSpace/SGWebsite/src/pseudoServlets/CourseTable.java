package pseudoServlets;

import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import dataStructure.Course;
import dataStructure.Faculty;
import dataStructure.Program;

import pseudoServlets.tools.TableTools;

import sessionTracking.Session;

/**
 * @author Zjef
 * @version 0.1
 */
public class CourseTable extends PseudoServlet
{	
	public CourseTable() 
	{
		super();
		templateFile="courseTable.tpl";
	}
	
	@Override
	public String processRequest(RequestType type, HttpServletRequest request,Session session) 
	{
		if (request.getParameter("")==null)
		{
			return generateMainPage(session);
		}
		else
		{
			return generateTable(request.getParameter("fac"),request.getParameter("option"));
		}
	}
	
	private String generateTable(String Faculty,String Program)
	{
		//TODO
		return null;
	}
	
	private String generateMainPage(Session session)
	{
		Vector<Faculty> faculties=TableTools.loadObjects(Faculty.class);
		String res=replaceTags(template,"TABS",TableTools.createTabHeader(faculties));
		res=replaceTags(res,"LINK",createLink(session));
		res=replaceTags(res,"TABCONTENT",createFacultyTabContent(faculties));
		return res;
	}

	@Override
	protected String getTabName() 
	{
		return "Courses";
	}
	
	private String createIFrameID(int counter)
	{
		return "iframe"+counter;
	}
	
	private String createSelectorID(int counter)
	{
		return "selector"+counter;
	}
	
	private String createFacultyTabContent(Vector<Faculty> tabs)
	{
		String res="";
		int counter=1;
		for (Faculty i:tabs)
		{
			res+="<div id='tabs-'"+counter+">";
			res+=createDropListWJs(i.getPrograms(),5,"setIFrameContent('"+createSelectorID(counter)+"','"+createIFrameID(counter)+"')",createSelectorID(counter));
			res+="<iframe id='"+createIFrameID(counter)+"'></iframe>";
			res+="</div>";
			counter++;
		}
		res+="<iframe>";
		return res;
	}
	
	private String createDropListWJs(Vector<?> options,int size,String js,String id)
	{
		String res="<select size='"+size+"' onchange='"+js+"' id='"+id+"'>";
		for (Object i:options)
		{
			res+="<option>"+i.toString()+"</option>";
		}
		res+="</select>";
		return res;
	}

}

package pseudoServlets;

import htmlInterfaces.HTMLInterfaceTool;
import htmlInterfaces.HTMLTablable;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import pseudoServlets.tools.PSTools;
import sessionTracking.Session;
import dataStructure.Faculty;

/**
 * @author Zjef
 * @version 0.5 only for faculty as tab and program as list 
 */

abstract public class TabAndList<T extends HTMLTablable> extends PseudoServlet 
{
	final private static String frameTemplateFile="courseFrameTable.tpl";
	final private static String tabTag="tab";
	final private static String selectTag="sel";
	private String frameTemplate;
	
	public TabAndList() 
	{
		super();
		templateFile="courseTable.tpl";
		frameTemplate=loadTemplate(frameTemplateFile);
	}
	
	@Override
	public String processRequest(RequestType type, HttpServletRequest request,Session session) 
	{
		if (request.getParameter(tabTag)==null)
		{
			return generateMainPage(session);
		}
		else
		{
			return generateTablePage(request.getParameter(tabTag),request.getParameter(selectTag));
		}
	}
	
	abstract protected String createExpandScript(Vector<T> objects);
	
	protected String createInitDatatable()
	{
		return "\\$('#expandTable').dataTable();";
	}
	
	private String generateTablePage(String faculty,String program)
	{
		Vector<T> objects=getTableObjects(faculty,program);
		String res=replaceTags(frameTemplate,"TABLE",HTMLInterfaceTool.changeToDataTable("maintable",objects));
		res=replaceTags(res,"EXPAND_SCRIPT",createExpandScript(objects));
		res=replaceTags(res,"INIT_DATATABLE",createInitDatatable());
		
		return res;
	}
	
	protected abstract Vector<T> getTableObjects(String faculty,String program);
	
	private String generateMainPage(Session session)
	{
		Vector<Faculty> faculties=PSTools.loadObjects(Faculty.class);
		String res=replaceTags(template,"TABS",PSTools.createTabHeader(faculties));
		res=replaceTags(res,"LINK",createLink(session));
		res=replaceTags(res,"TABCONTENT",createFacultyTabContent(faculties));
		res=replaceTags(res,"SIZE",createSizeScript(faculties));
		return res;
	}
	
	private String createSelectorID(int counter)
	{
		return "selector"+counter;
	}
	
	private String createSizeScript(Vector<Faculty> tabs)
	{
		String res="";
		for (Faculty i:tabs)
		{
			res+="calcHeight('"+i.toString()+"');";
		}
		return res;
	}
	
	private String createFacultyTabContent(Vector<Faculty> tabs)
	{
		String res="";
		int counter=1;
		for (Faculty i:tabs)
		{
			res+="<div id='tabs-"+counter+"'>";
			res+=createDropListWJs(i.getPrograms(),5,"setIFrameContent('"+createSelectorID(counter)+"','"+i.toString()+"')",createSelectorID(counter));
			res+="<iframe scrollable='yes' id='"+i.toString()+"'></iframe>";
			res+="</div>";
			counter++;
		}
		return res;
	}
	
	private String createDropListWJs(Vector<?> options,int size,String js,String id)
	{
		String res="<select size='"+size+"' onchange=\""+js+"\" id='"+id+"'>";
		for (Object i:options)
		{
			res+="<option value='"+i.toString()+"'>"+i.toString()+"</option>";
		}
		res+="</select>";
		return res;
	}
}

package pseudoServlets;

import javax.servlet.http.HttpServletRequest;

import sessionTracking.Session;

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
		String res="";
		if (request.getParameter("")==null)
		{
			return generateSelectorPage();
		}
		return res;
	}
	
	private String generateSelectorPage()
	{
		return null;
	}

	@Override
	protected String getTabName() 
	{
		return "Courses";
	}

}

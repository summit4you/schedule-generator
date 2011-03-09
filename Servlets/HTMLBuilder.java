package login;

import java.util.Vector;

import xml.*;

public class HTMLBuilder
{
	public HTMLBuilder()
	{
		LoadXML();
		setTop(((ElementWithValue) pages.getElement("top")).getValue());
    	setBottom(((ElementWithValue) pages.getElement("bottom")).getValue());
	}
	
	private XMLDocument pages;	
	private String top;
	private String bottom;
	private Vector<HTMLTabblad> standardtabs;
	private Vector<HTMLFunction> standardfunctions;
	
	private void LoadXML() 
    {
    	pages = new XMLDocument("Login.xml");
    	pages.load();
    }
	
	public HTMLPage createNewHTMLPage(User user)
	{
		HTMLPage out = new HTMLPage(top, bottom);
		for (int i = 0; i < user.getPrivileges().size(); i++)
		{
			out.addTabblad(standardtabs.get(standardtabs.indexOf(user.getPrivileges().get(i))));
		}
		return out;
	}
	
	
	
	
	
	
	public void setBottom(String bottom)
	{
		this.bottom = bottom;
	}

	public String getBottom()
	{
		return bottom;
	}

	public void setTop(String top)
	{
		this.top = top;
	}

	public String getTop()
	{
		return top;
	}
	


}

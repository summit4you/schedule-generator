package HTMLBuilder;

import login.User;
import sessionTracking.Session;
import xml.*;

public class HTMLBuilder
{	
	
	private XMLDocument pages;
	private XMLDocument tabs;
	private String top;
	private String bottom;
	private HTMLTabbladVector standardtabs;
	
	private void LoadXML() 
    {
		pages = new XMLDocument("Layout.xml");
    	pages.load();    	
    	tabs = new XMLDocument("Tabs.xml");
    	tabs.load();    	
    }
	
	public HTMLBuilder()
	{
		// maak contact met de nodige XML bestanden
		LoadXML();
		// laad de standaard top en bottom strings in die de layout van de site bepalen
		setTop(((ElementWithValue) pages.getElement("top")).getValue());
    	setBottom(((ElementWithValue) pages.getElement("bottom")).getValue());
    	standardtabs = new HTMLTabbladVector();
    	// laad de standaard tabbladen in die mogelijk zijn
    	for (int i = 0; i < tabs.getElements().size(); i++)
		{
    		// haal het tabblad uit de vector van ingelezen tabbladen
    		ElementWithChildren element = (ElementWithChildren) tabs.getElements().get(i);
    		// vul een tabblad in met de waarden die uit het uitgelezen tabblad komen: naam, top en bottom
    		HTMLTabblad tab = new HTMLTabblad(((ElementWithValue) element.getChild("name")).getValue(), ((ElementWithValue) element.getChild("top")).getValue(), ((ElementWithValue) element.getChild("bottom")).getValue());
    		// voeg de functies toe die in het tabblad zitten
    		for (int j = 0; j < ((ElementWithChildren) element.getChild("functions")).getElements().size(); j++)
			{
    			HTMLFunction tempfunction = new HTMLFunction(((ElementWithValue) ((ElementWithChildren) element.getChild("functions")).getElements().get(j)).toString(), ((ElementWithValue) ((ElementWithChildren) element.getChild("functions")).getElements().get(j)).getName());
    			tab.addFunction(tempfunction);
			}
    		// voeg het uitgelezen tabblad toe aan de lijst met standaardtabbladen.
    		standardtabs.add(tab);
		}
	}
	
	public HTMLPage createNewHTMLPage(Session ses)
	{
		HTMLPage out = new HTMLPage(top, bottom);
		User user = ses.getUser();
		// voeg de tabbladen toe die in de privileges van de gebruiker staan
		for (int i = 1; i < user.getPrivileges().size(); i++)
		{
			// maak tabblad en haal het juiste tabblad uit de standaard tabbladen
			HTMLTabblad tab = new HTMLTabblad(standardtabs.get(standardtabs.indexOf(user.getPrivileges().get(i))).getName(),standardtabs.get(standardtabs.indexOf(user.getPrivileges().get(i))).getTop(), standardtabs.get(standardtabs.indexOf(user.getPrivileges().get(i))).getBottom());
			// voeg de functies toe die in de privileges onder dat tabblad staan
			for (int j = 0; j < user.getPrivileges().get(i).getFunctions().size(); j++)
			{
				
			}
			// smijt het tabblad erbij
			out.addTabblad(tab);
		}
		return out;
	}
	
	// kan een standaardpagina aanmaken zoals de login pagina
	public HTMLPage createNewHTMLPage(String name)
	{
		if (name.toLowerCase().equals("login"))
		{
			HTMLTabblad tab = new HTMLTabblad(standardtabs.getTab("login").getName(), standardtabs.getTab("login").getTop(), standardtabs.getTab("login").getBottom());
			tab.addFunction(standardtabs.getTab("login").getFunctions().getFunction("login"));
			HTMLPage out = new HTMLPage(top, bottom);
			out.addTabblad(tab);
			return out;
		}
		else if (name.toLowerCase().equals("test")) {
			HTMLTabblad tab = new HTMLTabblad(standardtabs.getTab("function2").getName(), standardtabs.getTab("function2").getTop(), standardtabs.getTab("function2").getBottom());
			tab.addFunction(standardtabs.getTab("function2").getFunctions().getFunction("function2"));
			HTMLPage out = new HTMLPage(top, bottom);
			out.addTabblad(tab);
			return out;
		}
		return null;
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

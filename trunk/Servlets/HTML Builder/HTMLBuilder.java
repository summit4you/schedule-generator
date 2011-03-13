package login;

import java.io.File;
import java.util.Vector;

import xml.*;

public class HTMLBuilder
{	
	
	private XMLDocument pages;
	private XMLDocument tabs;
	private String top;
	private String bottom;
	private Vector<HTMLTabblad> standardtabs;
	
	private void LoadXML() 
    {
		pages = new XMLDocument("Layout.xml");
    	if (pages.load())
    		{
    		System.out.println("standardlayout loaded");
    		}
    	
    	tabs = new XMLDocument("Tabs.xml");
    	if (tabs.load())
    	{
    		System.out.println("standardtabs loaded");
    	}
    	
    }
	
	public HTMLBuilder()
	{
		// maak contact met de nodige XML bestanden
		LoadXML();
		System.out.println(pages.toString());
		// laad de standaard top en bottom strings in die de layout van de site bepalen
		setTop(((ElementWithValue) pages.getElement("top")).getValue());
    	setBottom(((ElementWithValue) pages.getElement("bottom")).getValue());
    	// laad de standaard tabbladen in die mogelijk zijn
    	for (int i = 0; i < pages.getElements().size(); i++)
		{
    		// haal het tabblad uit de vector van ingelezen tabbladen
    		ElementWithChildren element = (ElementWithChildren) pages.getElements().get(i);
    		// vul een tabblad in met de waarden die uit het uitgelezen tabblad komen: naam, top en bottom
    		HTMLTabblad tab = new HTMLTabblad(((ElementWithValue) element.getChild("name")).getValue(), ((ElementWithValue) element.getChild("top")).getValue(), ((ElementWithValue) element.getChild("bottom")).getValue());
    		// voeg de functies toe die in het tabblad zitten
    		for (int j = 0; j < ((ElementWithChildren) element.getChild("functions")).getElements().size(); j++)
			{
    			tab.addFunction(new HTMLFunction(((ElementWithValue) ((ElementWithChildren) element.getChild("functions")).getElements().get(j)).getValue()));
			}
    		// voeg het uitgelezen tabblad toe aan de lijst met standaardtabbladen.
    		standardtabs.add(tab);
		}
	}
	
	public HTMLPage createNewHTMLPage(User user)
	{
		HTMLPage out = new HTMLPage(top, bottom);
		// voeg de tabbladen toe die in de privileges van de gebruiker staan
		for (int i = 0; i < user.getPrivileges().size(); i++)
		{
			// maak tabblad en haal het juiste tabblad uit de standaard tabbladen
			HTMLTabblad tab = new HTMLTabblad(standardtabs.get(standardtabs.indexOf(user.getPrivileges().get(i))).getName(),standardtabs.get(standardtabs.indexOf(user.getPrivileges().get(i))).getTop(), standardtabs.get(standardtabs.indexOf(user.getPrivileges().get(i))).getBottom());
			// voeg de functies toe die in de privileges onder dat tabblad staan
			for (int j = 0; j < user.getPrivileges().get(i).getFunctions().size(); j++)
			{
				// geen mens die aan het volgende nog aan uit geraakt, maar volgens mij is het juist
				tab.addFunction(standardtabs.get(standardtabs.indexOf(user.getPrivileges().get(i))).getFunctions().get(standardtabs.get(standardtabs.indexOf(user.getPrivileges().get(i))).getFunctions().indexOf(user.getPrivileges().get(i).getFunctions().get(j).getName())));
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
		HTMLTabblad tab = new HTMLTabblad(standardtabs.get(standardtabs.indexOf("login")).getTop(), standardtabs.get(standardtabs.indexOf("login")).getTop(), standardtabs.get(standardtabs.indexOf("login")).getTop());
		tab.addFunction(standardtabs.get(standardtabs.indexOf("login")).getFunctions().get(standardtabs.get(standardtabs.indexOf("login")).getFunctions().indexOf("login")));
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

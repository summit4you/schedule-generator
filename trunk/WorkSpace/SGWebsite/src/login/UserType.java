package login;

import java.util.Vector;

import database.DatabasableAsString;

import pseudoServlets.PseudoServlet;
import sessionTracking.Session;
import xml.ElementWithChildren;
import xml.ElementWithValue;
import xml.XMLDocument;
import xml.XMLElement;

import htmlBuilder.MainServlet;
import htmlBuilder.Site;

public class UserType implements DatabasableAsString
{
	
	static protected XMLDocument typeDoc=loadTypeDoc();
	static private final String defaultLocation="UserTypes.xml";
	protected Vector<String> pseudos;
	protected String value;
	
	public UserType()
	{
		pseudos = new Vector<String>();
		value = new String();
	}
	public UserType(String value)
	{
		this.loadFromValue(value);
	}
	
	public static XMLDocument loadTypeDoc()
	{
		XMLDocument doc=new XMLDocument(defaultLocation);
		if (doc.load()){System.out.println(">>UserType: Loading Successful");}
		return doc;
	}

	public Vector<String> getPseudos()
	{
		return pseudos;
	}
	
	public void setPseudos(Vector<String> pseudos)
	{
		this.pseudos = pseudos;
	}
		
	public boolean isAuthorized(String ps)
	{
		for(String p:pseudos)
		{
			if (p.equals(ps))
			{ 
				return true;
			}
		}
		return false;
	}
	
	@SuppressWarnings("deprecation")
	public Site buildSite(Session ses)
	{
		Site site = new Site();
		for(String p:pseudos)
		{
			site.addTabWithIFrame(PseudoServlet.getPseudoServlet(p).getTabName(), PseudoServlet.getPseudoServlet(p).createLink(ses));
		}
		return site;
	}
	
	@SuppressWarnings("deprecation")
	public Site buildSite(String path,Session ses)
	{
		Site site = new Site(path);
		for(String p:pseudos)
		{
			site.addTabWithIFrame(PseudoServlet.getPseudoServlet(p).getTabName(), PseudoServlet.getPseudoServlet(p).createLink(ses));
		}
		return site;
	}

	@Override
	public String toValue()
	{
		return value;
	}

	@Override
	public void loadFromValue(String value)
	{
		
		pseudos = new Vector<String>();
		XMLElement el = typeDoc.getElement("UserTypes."+value);
		if (el!=null)
		{
			try
			{
				for(XMLElement child:ElementWithChildren.class.cast(el).getElements())
				{
					pseudos.add(ElementWithValue.class.cast(child).getValue());
				}
				this.value=el.getName();
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}		
		
	}
	

}

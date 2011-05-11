package login;

import java.util.Vector;

import database.DatabasableAsString;

import pseudoServlets.PseudoServlet;
import sessionTracking.Session;
import xml.ElementWithChildren;
import xml.ElementWithValue;
import xml.XMLDocument;
import xml.XMLElement;
import other.Globals; 

import htmlBuilder.Site;

public class UserType implements DatabasableAsString
{	
	static private final String fileName="UserTypes.xml";
	static private final String ps="ps";
	static private final String da="da";
	
	static private final String[] psValues = {"Accounts","BuildingTable","CourseTable","EducatorTable","Search","Schedule","StudentTable"};
	static private final String[] daValues = {};
	
	static protected XMLDocument typeDoc;
	
	protected Vector<String> pseudos;
	protected Vector<String> dataAccess;
	
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
	
	public static void initUserTypes()
	{
		typeDoc=loadTypeDoc();
	}
	
	public static XMLDocument loadTypeDoc()
	{
		XMLDocument doc=new XMLDocument(Globals.configPath+"/"+fileName);
		doc.load();
		return doc;
	}
	
	public static boolean isValidPs(String ps)
	{
		for(String p:psValues)
		{
			if (p.equals(ps))
			{ 
				return true;
			}
		}
		return false;
	}
	
	public static boolean isValidDa(String da)
	{
		for(String d:daValues)
		{
			if (d.equals(da))
			{ 
				return true;
			}
		}
		return false;
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
		if (ps!=null)
		{
			for(String p:pseudos)
			{
				if (p.equals(ps))
				{ 
					return true;
				}
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

	@Override
	public String toValue()
	{
		return value;
	}

	@Override
	public void loadFromValue(String value)
	{
		
		pseudos = new Vector<String>();
		dataAccess = new Vector<String>();
		
		XMLElement el = typeDoc.getElement("UserTypes."+value);
		if (el!=null)
		{
			try
			{	
				for(XMLElement child:ElementWithChildren.class.cast(el).getElements())
				{
					if (child.getName()==ps && isValidPs(ElementWithValue.class.cast(child).getValue()))
					{
						pseudos.add(ElementWithValue.class.cast(child).getValue());
					}
					else if (child.getName()==da && isValidDa(ElementWithValue.class.cast(child).getValue()))
					{
						dataAccess.add(ElementWithValue.class.cast(child).getValue());
					}
				}
				this.value=el.getName();
			}
			catch(Exception e)
			{
				System.out.println(">>> UserType.loadFromValue: Unexpected element in file");
			}
		}		
		
	}
}

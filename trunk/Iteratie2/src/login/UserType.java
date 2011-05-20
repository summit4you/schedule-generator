package login;

import java.io.Serializable;
import java.util.Vector;
import dataStructure.Educator;
import dataStructure.Student;
import database.Databasable;
import database.DatabasableAsString;
import pseudoServlets.PseudoServlet;
import sessionTracking.Session;
import xml.ElementWithChildren;
import xml.ElementWithValue;
import xml.XMLDocument;
import xml.XMLElement;
import other.Globals; 
import htmlBuilder.Site;

public class UserType implements DatabasableAsString,Serializable
{	
	private static final long serialVersionUID = 1L;
	
	static private final String fileName="UserTypes.xml";
	
	
	final public static String separator=";";	
	final public static String mainLabel="UserTypes";
	final public static String studentType="Student";
	final public static String educatorType="Educator";
	final public static String adminType="Admin";
	final public static String guestType="Guest";
	
	static protected XMLDocument typeDoc;
	private static Vector<UserType> userTypes=loadUserTypes();
	
	protected Vector<String> pseudos;
	protected String value;
	
	public UserType()
	{
		pseudos = new Vector<String>();
		value = new String();
	}
		
	public UserType(String value)
	{
		pseudos=new Vector<String>();
		this.value=value;
	}
	
	public Class<? extends Databasable> getTypeClass()
	{
		if (this.value.equals(studentType))
		{
			return Student.class;
		}
		else if(this.value.equals(educatorType))
		{
			return Educator.class;
		}
		return null;
	}
	
	private static Vector<UserType> loadUserTypes()
	{
		XMLDocument typeDoc=new XMLDocument(Globals.configPath+fileName);
		Vector<UserType> userTypes = new Vector<UserType>();
		UserType type;
		
		if (typeDoc.load())
		{	
			for(XMLElement e: ElementWithChildren.class.cast(typeDoc.getElement(mainLabel)).getElements())
			{
				try
				{
					ElementWithChildren parent = ElementWithChildren.class.cast(e);
					type = new UserType(parent.getName()); 
					for(XMLElement ch:parent.getElements())
					{
						type.pseudos.add(ElementWithValue.class.cast(ch).getValue());
					}
					userTypes.add(type);
				}
				catch(Exception exc)
				{
					exc.printStackTrace();	
				}
			}
		}
		if (typeDoc.getElement("UserTypes."+guestType)==null)
		{
			type=new UserType(guestType);
			type.pseudos.add(PseudoServlet.TabName.Search.toString());
			userTypes.add(type);
		}
		return userTypes;	
	}
	
	public static void reloadUserTypes()
	{
		userTypes=loadUserTypes();
	}
	
	public static Vector<UserType> getUserTypes()
	{
		return userTypes;
	}
	
	public static UserType getUserType(String value)
	{
		for(UserType u:userTypes)
		{
			if (u.value.equals(value))
			{
				return u;
			}
		}
		return null;
	}
		
	public static boolean isUserType(String value)
	{
		for(UserType u:userTypes)
		{
			if (u.value.equals(value))
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
		
	public boolean isAuthorized(String ps)
	{	
		return pseudos.contains(ps);
	}
	
	@SuppressWarnings("deprecation")
	public Site buildSite(Session ses)
	{
		Site site = new Site();
		for(String p:pseudos)
		{
			if (!(PseudoServlet.getPseudoServlet(p)==null))
			{
				site.addTabWithIFrame(PseudoServlet.getPseudoServlet(p).getTabName(), PseudoServlet.getPseudoServlet(p).createLink(ses));
			}
			else
			{
				System.out.println("ERROR, pseudoservlet in usertypes not found  in pseudoservlets");
			}	
		}
		return site;
	}
	
	public String getValue()
	{
		return value;
	}
	
	@Override
	public String toString()
	{
		return value;
	}
	
	@Override
	public String toValue()
	{
		return value;
	}

	@Override
	public void loadFromValue(String value)
	{
		this.pseudos=getUserType(value).getPseudos();
		this.value=getUserType(value).value;
	}
}
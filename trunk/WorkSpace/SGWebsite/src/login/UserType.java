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
	final private static Vector<String> userTypes=loadUserTypes();
	
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
	
	private static Vector<String> loadUserTypes()
	{
		Vector<String> res=new Vector<String>();
		res.add(studentType);
		res.add(educatorType);
		res.add(guestType);
		if (typeDoc==null)
		{
			loadTypeDoc();
		}
		
		ElementWithChildren admin=(ElementWithChildren) typeDoc.getElement(mainLabel+"."+adminType);
		for (XMLElement i:admin.getElements())
		{
			res.add(adminType+"."+i.getName());
		}
		return res;
	}
	
	public static Vector<String> getUserTypes()
	{
		return userTypes;
	}
	
	private static void loadTypeDoc()
	{
		typeDoc=new XMLDocument(fileName);
		typeDoc.load();
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
		return pseudos.contains(ps);
	}
	
	@SuppressWarnings("deprecation")
	public Site buildSite(String dummy,Session ses)
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
	
		XMLElement el = typeDoc.getElement("UserTypes."+value);
		if (el!=null)
		{
			try
			{	
				for(XMLElement child:ElementWithChildren.class.cast(el).getElements())
				{
					pseudos.add(ElementWithValue.class.cast(child).getValue());
				}
			}
			catch(Exception e)
			{
				pseudos.removeAllElements();
				e.printStackTrace();
			}
			this.value=value;
		}		
	}
}
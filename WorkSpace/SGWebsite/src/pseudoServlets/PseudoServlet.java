package pseudoServlets;

import java.util.Vector;
import javax.servlet.http.HttpServletRequest;

import other.FileIO;
import sessionTracking.Session;

/**
 * @author Zjef
 * @version 1.0
 */
public abstract class PseudoServlet
{	
	public static enum RequestType {GET,POST};
	final public static String pseudoServletParamTag="ps";
	
	private static Vector<PseudoServlet> pseudoServlets;
	
	/**
	 * Link to the base Servlet, without the pseudoServlet identifier tag and session id
	 */
	protected static String baseLink;
	
	/**
	 * Vector with all the language tags to be replaced
	 */
	protected Vector<String> langTags;
	
	/**
	 * Name of the template file
	 */
	protected String templateFile;
	
	/**
	 * Content of the template file
	 */
	protected String template;
	
	
	public PseudoServlet()
	{
		langTags=new Vector<String>();
	}
	
	//**********Adding of all PseudoServlets********************************************************
	/**
	 * Add all your PseudoServlets here<br>
	 */
	private static Vector<PseudoServlet> loadPseudoServlets()
	{
		Vector<PseudoServlet> pseudos=new Vector<PseudoServlet>(); 
		//add your pseudoServlets to this vector!
		//pseudos.add(new ...);
		
		for (PseudoServlet i:pseudos)
		{
			i.init();
		}
		return pseudos;
	}
	
	//*******protected methods*******************************************************************************
	/**
	 *Loads the template of the pseudoservlet.<br>
	 */
	protected void init()
	{
		template=(templateFile==null?"":FileIO.readFile(templateFile));
	}
	
	//*******protected static methods************************************************************************	
	/**
	 * Returns a string with the tag replaced by another string<br>
	 * {} brackets are added by this method to the toBeReplaced String.
	 */
	protected static String replaceTags(String string,String toBeReplaced,String replacement)
	{
		return string.replaceAll("\\{"+toBeReplaced+"\\}",replacement);
	}
	
	//*********public methods*********************************************************************
	/**
	 * @return the identifier of this PseudoServlet to be placed in a link for processing by the security servlet. 
	 */
	public String getIdentifier()
	{
		return this.getClass().getSimpleName();
	}

	/**
	 * @return vector containing all tags to be replaced by the translator<br>
	 * The tags already contain the tags ##
	 */
	public Vector<String> getLanguageTags()
	{
		return (Vector<String>)langTags.clone();
	}

	//*******public static methods*******************************************************************
	/**
	 * Call this once before using anything else, to initialize the link to the general servlet
	 */
	public static void setBaseServletLink(String link)
	{
		baseLink=link;
	}
	
	/**
	 * Generates the base link for this pseudoServlet<br>
	 * To add extra parameters to the link, simply append them to the return value of this method (starting with a '&')
	 */
	public static String createLink(PseudoServlet pseudoServlet,Session session)
	{
		//TODO replace "id" with a final static String
		return baseLink+"?"+pseudoServletParamTag+"="+pseudoServlet.getIdentifier()+"&"+"id="+session.getSessionID();
	}
	
	/**
	 * @param link to the main servlet
	 * @returns a vector containing all PseudoServlets declared in the package "pseudoServlets"
	 */
	public static Vector<PseudoServlet> getPseudoServlets()
	{
		return (Vector<PseudoServlet>) (pseudoServlets==null?pseudoServlets=loadPseudoServlets():pseudoServlets).clone();
	}
	
	/**
	 * @param identifier - parameter from the url (after the '?') linking to a specific PseudoServlet
	 * @return the PseudoServlet linked to the identifier; <code>null</code> if none of the PseudoServlets is associated with the specified identifier.
	 */
	public static PseudoServlet getPseudoServlet(String identifier)
	{
		for (PseudoServlet i:getPseudoServlets())
		{
			if (i.getIdentifier().equals(identifier))
			{
				return i;
			}
		}
		return null;
	}
	
	/**
	 * @param pseudoServlet - class of the psudoservlet
	 * @return the PseudoServlet of the specified class; <code>null</code> if such a PseudoServlet does not exist.
	 */
	public static  PseudoServlet getPseudoServlet(Class<? extends PseudoServlet> pseudoServlet)
	{
		for (PseudoServlet i:getPseudoServlets())
		{
			if (i.getClass().equals(pseudoServlet))
			{
				return i;
			}
		}
		return null;
	}	
	
	//*********Abstract methods********************************************************************
	/**
	 *@return The String of the html page returned by this PseudoServlet
	 */
	abstract public String processRequest(RequestType type,HttpServletRequest request/*,Account account*/);
	
	/**
	 * @return name to display in the tab
	 */
	abstract protected String getTabName();
}
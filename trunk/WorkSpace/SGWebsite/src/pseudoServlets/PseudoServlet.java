package pseudoServlets;

import java.io.File;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;

import database.Database;
import other.FileIO;
import sessionTracking.Session;

/**
 * @author Zjef
 * @version 1.1
 */
public abstract class PseudoServlet
{	
	public static enum RequestType {GET,POST};
	final public static String pseudoServletParamTag="ps";
	
	private static Vector<PseudoServlet> pseudoServlets;
	
	/**
	 * URL of the base Servlet, without the pseudoServlet identifier tag and session id
	 */
	protected static String baseLink;
	/**
	 * file location of the servlet on the server
	 */
	protected static String servletPath;
	final protected static String templateFolder="SGtemplates/";
	final protected static String otherFileFolder="SGotherFiles/";
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
		pseudos.add(new SingleTable(null,"Accounts",false));
		pseudos.add(new Search());
		pseudos.add(new Schedule());
		
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
		loadTemplate();
	}
	
	private void loadTemplate()
	{
		template=(templateFile==null?"":FileIO.readFile(new File(new File(servletPath).getParentFile(),templateFolder+templateFile).getAbsolutePath()));
	}
	//*******protected static methods************************************************************************	
	/**
	 * @return a new database connection<br>
	 * The database is not connected yet
	 */
	protected static Database getDB()
	{
		//TODO replace database values with a read from an xml file
		Database db=new Database("wilma.vub.ac.be/se5_1011","se5_1011","nieveGroep");
		return db;
	}
	
	/**
	 * Returns a string with the tag replaced by another string<br>
	 * {} brackets are added by this method to the toBeReplaced String.
	 */
	protected static String replaceTags(String template,String toBeReplaced,String replacement)
	{
		return template.replaceAll("\\{"+toBeReplaced+"\\}",replacement);
	}
	
	//*********public methods*********************************************************************
	/**
	 * Generates the link for this pseudoServlet<br>
	 * This includes the base servlet's link. ( "http://serverlocation/baseServlet?sp=xxx&id=xxx" )<br>
	 * To add extra parameters to the link, simply append them to the return value of this method (starting with a '&')
	 */
	public String createLink(Session session)
	{
		//TODO replace "id" with a final static String
		return baseLink+"?"+pseudoServletParamTag+"="+getIdentifier()+"&"+"id="+session.getSessionID();
	}
	
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
	 * Inits all PseudoServlets and static variables.<br>
	 * Call this method once before using the PseudoServlets in this class.
	 * @param baseServletLink - url to the securityServlet
	 * @param servletContextPath - the contextpath of the securityServlet (to retrieve the absolute filepaths of the files on the server)
	 */
	public static void initEverything(String baseServletLink,String servletContextPath)
	{
		baseLink=baseServletLink;
		servletPath=servletContextPath;
		pseudoServlets=loadPseudoServlets();
	}
	
	/**
	 * @returns a vector containing all PseudoServlets declared in the package "pseudoServlets"
	 */
	public static Vector<PseudoServlet> getPseudoServlets()
	{		
		return (Vector<PseudoServlet>) pseudoServlets.clone();
	}
	
	/**
	 * @param identifier - parameter from the url (after the '?') linking to a specific PseudoServlet
	 * @return the PseudoServlet linked to the identifier; <code>null</code> if none of the PseudoServlets is associated with the specified identifier.
	 */
	public static PseudoServlet getPseudoServlet(String identifier)
	{
		for (PseudoServlet i:pseudoServlets)
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
		for (PseudoServlet i:pseudoServlets)
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
	abstract public String processRequest(RequestType type,HttpServletRequest request,Session session);
	
	/**
	 * @return name to display in the tab
	 */
	abstract protected String getTabName();
}
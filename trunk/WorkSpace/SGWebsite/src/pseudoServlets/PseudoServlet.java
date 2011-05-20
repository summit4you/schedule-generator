package pseudoServlets;

import htmlBuilder.Site;

import java.io.File;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import applet.Edit;
import applet.EditVector;
import applet.Wrapper;

import login.Account;

import database.Databasable;
import database.Database;
import other.FileIO;
import other.Globals;
import sessionTracking.Session;

/**
 * @author Zjef
 * @version 1.2
 */
public abstract class PseudoServlet
{	
	public static enum RequestType {GET,POST};
	public enum TabName
	{
		Account,Login,Search,Schedule,Accounts,EditAccounts,Buildings,EditBuildings,Educators,EditEducators,Students,EditStudents,Courses,EditCourses,EditCalendar,MyCoursesStudent,MyCoursesEducator,ConstraintsEducator,StartAlgorithm,ConstraintsAcademic;

		public String toLanguageTag()
		{
			return "##"+toString()+"##";
		}
	}

	final public static String pseudoServletParamTag="ps";
	
	private static Vector<PseudoServlet> pseudoServlets;	
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
	}
	
	//**********Adding of all PseudoServlets********************************************************
	/**
	 * Add all your PseudoServlets here<br>
	 */
	private static Vector<PseudoServlet> loadPseudoServlets()
	{
		Vector<PseudoServlet> pseudos=new Vector<PseudoServlet>(); 
		//add your pseudoServlets to this vector!
		pseudos.add(new pseudoServlets.Account());
		pseudos.add(new AccountEditor());
		pseudos.add(new AccountTable());
		pseudos.add(new BuildingEditor());
		pseudos.add(new BuildingTable());
		pseudos.add(new ConstraintsAcademic());
		pseudos.add(new ConstraintsEducator());
		pseudos.add(new CourseEditor());
		pseudos.add(new CourseTable());
		pseudos.add(new EditCalendar());
		pseudos.add(new EducatorEditor());
		pseudos.add(new EducatorTable());
		pseudos.add(new MyCoursesEducator());
		pseudos.add(new MyCoursesStudent());
		pseudos.add(new Schedule());
		pseudos.add(new Search());
		pseudos.add(new StudentEditor());
		pseudos.add(new StudentTable());

		
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
		template=loadTemplate(templateFile);
	}
	
	protected static String loadTemplate(String templateFile)
	{
		return (templateFile==null?"":FileIO.readFile(Globals.templatePath+"/"+templateFile));
	}
	//*******protected static methods************************************************************************	
	/**
	 * @return a new database connection<br>
	 * The database is not connected yet
	 */
	public static Database getDB()
	{
		return Database.getDB();
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
		//TODO link voor applets heeft meer nodig voor het '?'
		return "?"+pseudoServletParamTag+"="+getIdentifier()+"&"+"id="+session.getSessionID();
	}
	
	/**
	 * @return the identifier of this PseudoServlet to be placed in a link for processing by the security servlet. 
	 */
	public String getIdentifier()
	{
		return this.getTabName().toString();
	}

	//*******public static methods*******************************************************************
	/**
	 * Inits all PseudoServlets and static variables.<br>
	 * Call this method once before using the PseudoServlets in this class.
	 * @param baseServletLink - url to the securityServlet
	 * @param servletContextPath - the contextpath of the securityServlet (to retrieve the absolute filepaths of the files on the server)
	 */
	public static void initEverything()
	{
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
	abstract public TabName getTabName();
}
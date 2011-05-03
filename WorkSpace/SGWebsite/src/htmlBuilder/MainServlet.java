package htmlBuilder;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import language.Dictionary;
import login.Account;
import login.UserType;
import database.Database;
import database.Search;

import pseudoServlets.PseudoServlet;
import sessionTracking.Session;

/**
 * <b> Servlet which processes incoming requests from users </b></br>
 * This servlet receives the gets and post from users.It checks if the user is 
 * authorized to make the request. If so the appropriate pseudoservlet is contacted
 * to generate a response. Before this response is send back, the language tags are 
 * resolved by this servlet. This servlet is responsible for the initialization of the Dictionaries and 
 * Pseudoservlet. Therefore it needs the base link and language path. It also
 * uses the Site class, for which it has to locate the template.</br>
 * 
 * <center> local host version </center>
 * @author Alexander
 * @version 1.0
 */
@WebServlet("/MainServlet")
public class MainServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
    
	public static final String identifierParamTag="id";
	public static final String loginIdentifier="login";
	
	private static String baseLink="http://localhost/SGWebsite/MainServlet";//"http://wilma.vub.ac.be:8080/SGWebsite";
	private static String siteTemplatePath="C:\\java\\workspace\\SGWebsite\\src\\htmlBuilder\\site.xml";
    private static String languagePath="C:\\java\\workspace\\SGWebsite\\src\\language";
	private static String databsePath="wilma.vub.ac.be/se5_1011";
	private static String typePath="C:/java/workspace/SGWebsite/src/login/UserTypes.xml";//"UserTypes.xml";;
	
	protected static String dbUrl  = "wilma.vub.ac.be/se5_1011";
	protected static String dbName = "se5_1011";
	protected static String dbPassword = "nieveGroep";
	
	private String noMessage="";
	private String errorMessage="##WrongID_Login##";
	
	public MainServlet() 
    {
        super();
        
    }

	@Override
	public void init() throws ServletException
	{
		super.init();
		baseLink=this.getServletContext().getContextPath()+"/MainServlet";
		PseudoServlet.initEverything(baseLink,"C:/");
		PseudoServlet.setDB(dbUrl, dbName, dbPassword);
		Dictionary.initDictionaries(languagePath);
		UserType.setTypePath(typePath);
	}
    private Site makeLoginSite(String m)
    {
    	Site site=new Site(siteTemplatePath);
    	site.addTab(Site.TabName.Login, site.createLoginForm(baseLink+"?ps="+loginIdentifier, m));
		return site;
    }
        
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		response.setContentType("text/html");
	    PrintWriter out = response.getWriter();
	    
		String ps=request.getParameter(PseudoServlet.pseudoServletParamTag);
		String id=request.getParameter(identifierParamTag);
		
		Session ses = Session.getSession(id);
		
		if (id==null || ses==null) 
		{
			//TODO uit iframe gaan
			out.println(Dictionary.getDictionary(Dictionary.Language.english.toString()).translatePage(makeLoginSite(noMessage).getHtmlCode()));
		}
		else
		{
		    if (ps==null)
		    {
		    	out.println("##Pseudo_Servlet_Not_Found##");
		    } 
		    else
		    {
		    	if (ses.getAccount().getType().isAuthorized(ps))
		    	{
		    		out.println(Dictionary.getDictionary(ses.getAccount().getLanguage()).translatePage(PseudoServlet.getPseudoServlet(ps).processRequest(PseudoServlet.RequestType.GET, request,ses)));
		    	}
		    	else
		    	{
		    		out.println(Dictionary.getDictionary(ses.getAccount().getLanguage()).translatePage("#Access_Denied#"));
		    	}
		    	
		    } 
		}
		
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		response.setContentType("text/html");
	    PrintWriter out = response.getWriter();
	    
		String ps=request.getParameter(PseudoServlet.pseudoServletParamTag);
		String id=request.getParameter(identifierParamTag);
		
		if (id==null)
		{
			if (ps.equals(loginIdentifier))
			{
				// guest zit ook in de  database
				Database db = new Database(databsePath,"se5_1011","nieveGroep");
				db.connect();
				Search s = new Search(Account.class,"getUserName;getPassword",request.getParameter("username"),request.getParameter("password"));
				Account acc = db.read(s);
				db.disconnect();
				
				if (acc==null)
				{
					out.println(Dictionary.getDictionary(Dictionary.Language.english.toString()).translatePage(makeLoginSite(errorMessage).getHtmlCode()));
				}
				else
				{
					Session ses=new Session(acc);
					out.println(Dictionary.getDictionary(ses.getAccount().getLanguage()).translatePage(ses.getAccount().getType().buildSite(siteTemplatePath,ses).getHtmlCode()));
				}
			}
			else
			{
				out.write(Dictionary.getDictionary(Dictionary.Language.english.toString()).translatePage(makeLoginSite(noMessage).getHtmlCode()));
			}
		}
		else
		{
			Session ses = Session.getSession(id);
			if (ses==null)
			{
				//TODO uit iframe gaan
			}
			else
			{
			    if (ps==null)
			    {
			    	out.println("##No_PseudoServlet_MainServlet##");
			    } 
			    else
			    {
			    	out.println(Dictionary.getDictionary(ses.getAccount().getLanguage()).translatePage(PseudoServlet.getPseudoServlet(ps).processRequest(PseudoServlet.RequestType.GET, request,ses)));
			    } 
			}
		} 
	}

}

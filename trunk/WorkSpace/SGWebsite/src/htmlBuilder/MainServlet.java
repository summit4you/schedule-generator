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
import database.Database;
import database.Search;

import pseudoServlets.PseudoServlet;
import sessionTracking.Session;

/**
 * Servlet implementation class MainServlet
 */
@WebServlet("/MainServlet")
public class MainServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
    
	public static final String identifierParamTag="id";
	public static final String loginIdentifier="login";
	private static String baseLink="http://wilma.vub.ac.be:8080/ZVdP/MainServlet";
	private static final String siteTemplatePath="site.xml";//"C:\\java\\workspace\\SGWebsite\\src\\htmlBuilder\\site.xml";
    private static final String languagePath="Language";//"C:\\java\\workspace\\SGWebsite\\src\\language";
	
	private String noMessage="";
	private String errorMessage="#WrongID_Login#";
	
	public MainServlet() 
    {
        super();
        Dictionary.initDictionaries(languagePath);
    }

	@Override
	public void init() throws ServletException
	{
		super.init();
//		baseLink=this.getServletContext().getContextPath()+"/MainServlet";
		PseudoServlet.initEverything(baseLink,"C:\templates");
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
		    	out.println("#Pseudo_Servlet_Not_Found#");
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
				Database db = new Database("wilma.vub.ac.be/se5_1011","se5_1011","nieveGroep");
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
			    	out.println("error, geen pseudoservlet meegegeven");
			    } 
			    else
			    {
			    	out.println(Dictionary.getDictionary(ses.getAccount().getLanguage()).translatePage(PseudoServlet.getPseudoServlet(ps).processRequest(PseudoServlet.RequestType.GET, request,ses)));
			    } 
			}
		} 
	}

}

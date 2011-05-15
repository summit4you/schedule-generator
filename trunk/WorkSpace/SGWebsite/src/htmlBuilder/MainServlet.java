package htmlBuilder;

import java.io.IOException;
import java.io.PrintWriter;

import other.Globals;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import language.LanguageResolver;
import login.Account;
import login.UserType;
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
	public static final String logoutIdentifier="logout";
		
	private String noMessage="";
	private String errorMessage="##WrongID_Login##";
	private String sessionExpieredMesssage="##Session_Expiered##";
	
	public MainServlet() 
    {
        super();
    }

	@Override
	public void init() throws ServletException
	{
		super.init();
		UserType.initUserTypes();
		
		PseudoServlet.initEverything();
		LanguageResolver.LoadDictionaries();
	}
    private Site makeLoginSite(String m)
    {
    	Site site=new Site();
    	site.addTab(PseudoServlet.TabName.Login, site.createLoginForm("?ps="+loginIdentifier, m));
    	site.noLogoutForm();
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
		
		try
		{
		if (id==null || ses==null) 
		{
			//TODO uit iframe gaan
			out.println(LanguageResolver.translate(makeLoginSite(noMessage).getHtmlCode(), Globals.defaultLanguage ));
		}
		else
		{
		    if (ps==null) // add a check if pseudoservelt exists
		    {
		    	out.println("##Pseudo_Servlet_Not_Found##");
		    }
		    else if (ps.equals("logout")) 
		    {
				ses.end();
				out.println(LanguageResolver.translate(makeLoginSite(noMessage).getHtmlCode(), ses.getAccount().getLanguage()));
			}
		    else
		    {
		    	if (ses.getAccount().getType().isAuthorized(ps))
		    	{
		    		out.println(LanguageResolver.translate(PseudoServlet.getPseudoServlet(ps).processRequest(PseudoServlet.RequestType.GET, request,ses),ses.getAccount().getLanguage()));
		    	}
		    	else
		    	{
		    		out.println(LanguageResolver.translate("##Access_Denied##",ses.getAccount().getLanguage()));
		    	}
		    	
		    } 
		}
		}
		catch(Exception e)
		{
			out.println(LanguageResolver.translate("##Error_Occured##",ses.getAccount().getLanguage()));
			
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
				Database db = new Database(Globals.databaseAdress,Globals.databaseName,Globals.databasePassword);
				db.connect();
				Search s = new Search(Account.class,"getUserName;getPassword",request.getParameter("username"),request.getParameter("password"));
				Account acc = db.read(s);
				db.disconnect();
				
				if (acc==null)
				{
					out.println(LanguageResolver.translate(makeLoginSite(errorMessage).getHtmlCode(),Globals.defaultLanguage ));
				}
				else
				{
					Session ses=new Session(acc);
					out.println(LanguageResolver.translate(ses.getAccount().getType().buildSite(ses).getHtmlCode(),ses.getAccount().getLanguage()));
				}
			}
			else
			{
				out.println(LanguageResolver.translate(makeLoginSite(noMessage).getHtmlCode(),Globals.defaultLanguage ));
			}
		}
		else
		{
			Session ses = Session.getSession(id);
			if (ses==null)
			{
				out.println(LanguageResolver.translate(makeLoginSite(sessionExpieredMesssage).getHtmlCode(),Globals.defaultLanguage ));
			}
			else
			{
			    if (ps==null)
			    {
			    	out.println("error, geen pseudoservlet meegegeven");
			    } 
			    else
			    {
			    	out.println(LanguageResolver.translate(PseudoServlet.getPseudoServlet(ps).processRequest(PseudoServlet.RequestType.GET, request,ses),ses.getAccount().getLanguage()));
			    } 
			}
		} 
	}
}

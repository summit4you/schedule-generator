package htmlBuilder;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
	private static final String baseLink="http://localhost/SGWebsite/MainServlet";
	private static final String siteTemplatePath="C:\\java\\workspace\\SGWebsite\\src\\htmlBuilder\\site.xml";
    
	private String noMessage="";
	private String errorMessage="#WrongID_Login#";
	
	public MainServlet() 
    {
        super();
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
			out.println(makeLoginSite(noMessage).getHtmlCode());
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
		    		out.println(PseudoServlet.getPseudoServlet(ps).processRequest(PseudoServlet.RequestType.GET, request,ses));
		    	}
		    	else
		    	{
		    		out.println("#Access_Denied#");
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
				Database db = new Database("wilma.vub.ac.be/se5_1011","se5_1011","nieveGroep");
				db.connect();
				Search s = new Search(Account.class,"getUserName;getPassword",request.getParameter("username"),request.getParameter("password"));
				Account acc = db.read(s);
				db.disconnect();
				
				if (acc==null)
				{
					out.println(makeLoginSite(errorMessage).getHtmlCode());
				}
				else
				{
					Session ses=new Session(acc);
					out.println(ses.getAccount().getType().buildMainSite(baseLink, ses.getSessionID()));
				}
			}
			else
			{
				out.write(makeLoginSite(noMessage).getHtmlCode());
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
			    	out.println(PseudoServlet.getPseudoServlet(ps).processRequest(PseudoServlet.RequestType.GET, request,ses));
			    } 
			}
		} 
	}

}

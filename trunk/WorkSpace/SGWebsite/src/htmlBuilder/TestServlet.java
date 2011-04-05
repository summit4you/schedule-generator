package htmlBuilder;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class TestServlet
 */
@WebServlet("/TestServlet")
public class TestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
    private static Site mainSite;  
    	private static String contentHome=	  "<html>"+
											  "<body>"+
											  "<h1>Home Page</h1>"+
											  "<p> This a test servlet</p>"+
											  "</body>"+
											  "</html>";
    	
    	private static String contentNews= 	  "<html>"+
											  "<body>"+
											  "<h1>Hot News</h1>"+
											  "<p> A new test servlet was written</p>"+
											  "</body>"+
											  "</html>";
    	
    	private static String contentHelp =   "<html>"+
											  "<body>"+
											  "<h1>Help?</h1>"+
											  "<p> Sorry can't help you right now</p>"+
											  "</body>"+
											  "</html>";
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TestServlet() {
        super();
        mainSite=new Site("C:\\java\\workspace\\SGWebsite\\src\\htmlBuilder\\site.xml");
        
        mainSite.addTab ("Home", contentHome);
        mainSite.addTab("News", contentNews);
        mainSite.addTab("Help", contentHelp);
        
        mainSite.addTabWithIFrame("IHome", "http://localhost/SGWebsite/TestServlet?ps=contentHome");
        mainSite.addTabWithIFrame("INews", "http://localhost/SGWebsite/TestServlet?ps=contentNews");
        mainSite.addTabWithIFrame("IHelp", "http://localhost/SGWebsite/TestServlet?ps=contentHelp");
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		response.setContentType("text/html");
	    PrintWriter out = response.getWriter();
	    String psRequest=(String) request.getParameter("ps");
	    if (psRequest==null)
	    {
	    	out.print(mainSite.getHtmlCode());    
	    }
	    else if (psRequest.equals("contentHome"))
	    {
	    	out.print(contentHome);    
	    }
	    else if (psRequest.equals("contentNews"))
	    {
	    	out.print(contentNews);    
	    }
	    else if (psRequest.equals("contentHelp"))
	    {
	    	out.print(contentHelp);    
	    }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		// TODO Auto-generated method stub
	}

}

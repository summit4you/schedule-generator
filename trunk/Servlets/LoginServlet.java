package login;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.UUID;
import java.util.Vector;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sessionTracking.*;
import database.*;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/Login")
public class LoginServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
    
	private String loginPage;
	private String welcomePage;
	private String sessionPage;
	
    public LoginServlet() 
    {
        super();
        loginPage="<html>"+
        		  "<body>"+
        		  "<h1>Login Page</h1>"+
        		  "<form method=\"POST\" action=\"/SGWebsite/Login\">"+
					"<table border=\"0\">"+
					"<tr>"+
					"<td>User name:</td>"+
					"<td> <input type=\"text\" name=\"username\" /> </td>"+
					"</tr>"+
					"<tr>"+
					"<td>Password:</td>"+
					"<td><input type=\"password\" name=\"password\" /></td>"+
					"</tr>"+
					"<td><input type=\"submit\" value=\"Submit\" /></td>"+
					"</tr>"+
					"</table>"+
        		  "</form>"+
        		  "</html>"+
        		  "</body>";
        
        welcomePage="<html>"+
					"<body>"+
					"<h1>Welcome Page</h1>"+
					"<a href= #link#>Continue To Site</a>"+
					"</html>"+
					"</body>";
        
        sessionPage="<html>"+
					"<body>"+
					"<h1>Session Page</h1>"+
					"<table border=\"1\">"+
					"<tr>"+
					"<td>Session id:</td>"+
					"<td> #id# </td>"+
					"</tr>"+
					"<tr>"+
					"<td>Time lived</td>"+
					"<td>#creationtime#</td>"+
					"</tr>"+
					"<td>Life time</td>"+
					"<td>#lifetime#</td>"+
					"</tr>"+
					"</table>"+
					"</html>"+
					"</body>";
    }

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		response.setContentType("text/html");
	    PrintWriter out = response.getWriter();
	    
	    String id =request.getParameter("id");
	    System.out.println("Fetched id:");
	    System.out.println(id);
	    Session ses = Session.getSession(id);
	    
		if (id!=null && ses!=null)
		{
			//ses.refresh();
			String res=sessionPage.replace("#id#", ses.getSessionID());
			Long millis= System.currentTimeMillis()-ses.getTimeOfCreation();
			res=res.replace("#creationtime#",String.format("%d min, %d sec",TimeUnit.MILLISECONDS.toMinutes(millis),TimeUnit.MILLISECONDS.toSeconds(millis)-TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis))));
			millis= ses.getLifeTime();
			res=res.replace("#lifetime#",String.format("%d min, %d sec",TimeUnit.MILLISECONDS.toMinutes(millis),TimeUnit.MILLISECONDS.toSeconds(millis)-TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis))));
			res=res.replace("#id#",ses.getSessionID());
			out.println(res);
		}
		else
		{
			if (ses==null){System.out.println("Session invalid");}
			out.print(loginPage);
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		response.setContentType("text/html");
	    PrintWriter out = response.getWriter();
	    
//		Real code with the database	    
//----------------------------------------		
		Database db = new Database("wilma.vub.ac.be/se5_1011","se5_1011","nieveGroep");
		db.connect();
		Search s = new Search(Account.class,"getUserName;getPassword",request.getParameter("username"),request.getParameter("password"));
		Account acc = db.read(s);
		db.disconnect();
		
		if (acc==null)
		{
			out.println(loginPage);
			out.println("Wrong username or password!");
		}
		else
		{
			Session ses=new Session();
	    	out.println(welcomePage.replace("#link#","http://wilma.vub.ac.be:8080/SGWebsite/Login?id="+ses.getSessionID()));
		}
		
	    System.out.println("Recieved Data:");
	    System.out.println(request.getParameter("username"));
	    System.out.println(request.getParameter("password"));
	    
//	Dummy code without the database	    
//----------------------------------------
//		if (request.getParameter("password").equals("admin"))
//		{
//			Session ses=new Session();
//	    	out.println(welcomePage.replace("#link#","http://localhost/SGWebsite/Login?id="+ses.getSessionID()));
//		}
//		else
//		{
//			out.println(loginPage);
//			out.println("Wrong username or password!");
//		}

	}
}

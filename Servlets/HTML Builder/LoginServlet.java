package login;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.*;
import xml.*;

/**
 * 
 * 
 * @author Adam
 * @version 1.0
 */
@WebServlet("/login1")
public class LoginServlet extends HttpServlet {
private static final long serialVersionUID = 1L;
    
    private XMLDocument pages;
    private Random random;
    private Vector<LoggedInID> loggedInList; 
    private Database database;
    private String loginpage= "adam";
    private HTMLBuilder HTML;
    
    private User unknown;
    
	/**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet () 
    {
        super();
        System.out.println("super constuctor loaded");
        HTML = new HTMLBuilder();
        unknown = new User("Unknown", "Password");
        unknown.setPage(HTML.createNewHTMLPage("Login"));
    }

    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
    	String ID = request.getParameter("id");
    	System.out.println(ID);
    	response.setContentType("text/html");
	    PrintWriter out = response.getWriter();
	    if (ID==null) // gebruiker is nog niet ingelogd
		{
	    	out.println(unknown.getPage().toString());
		} else
		{
	    	// controleer of ID in de loggedinlist zit
			if(loggedInList.contains(ID))
			{
				// toon pagina voor de gebruiker die ermee overeen komt
				out.println(loggedInList.get(loggedInList.indexOf(ID)).getUser().getPage().toString());
			}
			else 
			{
				out.println(unknown.getPage().toString());
			}
		}		
	}
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		User LoggedUser = new User(request.getParameter("NAME"),request.getParameter("PASS"));
		// zoek naar usergegevens in database
		User CurrentUser = database.read(LoggedUser,"getName;getPassword");
		if (CurrentUser!=null) // de gebruiker zit in de database en wachtwoord is juist
		{
			LoggedInID pogingID=new LoggedInID(random.nextInt());
			// genereer random nummer dat nog niet gebruikt wordt
			while (loggedInList.contains(pogingID))
			{
				// genereer random nummer
				pogingID.setID(random.nextInt());
			}
			// sla nummer op in tabel en koppen de gebruikersgegevens eraan
			pogingID.setUser(CurrentUser);
			loggedInList.add(pogingID);
			// genereer pagina voor de ingelogde gebruiker
			out.println("ERROR");
		} 
		else // de gebruiker zit niet in de database
		{
			// Vertel dat de opgegeven gebruiker niet bestaat of wachtwoord mis is
			out.println(loginpage); // hier moet de zin "wachtwoord fout" aan toegevoegd worden
		}
    }
}

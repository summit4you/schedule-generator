package mypackage;

import java.io.IOException;

import java.io.PrintWriter;
import java.util.Random;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import xml.*;
import database.*;

/**
 * Servlet implementation class test
 */
public class test2 extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	/**
     * @see HttpServlet#HttpServlet()
     */
    public test2() 
    {
        super();
        // TODO Auto-generated constructor stub
    }
    
    private XMLDocument paginas;
    
    private void LaadXML() 
    {
    	paginas = new XMLDocument("Login.xml");
    	paginas.load();
    }
    	    
    private String loginpage= "adam";
    
//    <FORM METHOD="POST" ACTION="/sample/test">
//    <P>
//    <LABEL for="firstname">Account:</LABEL>
//    <INPUT TYPE="TEXT" NAME="NAME" SIZE=30> <BR>
//    <LABEL for="password">Password:</LABEL>
//    <INPUT TYPE="PASSWORD" NAME="PASS" SIZE=30> <BR>
//    <INPUT type="submit" value="Send"></P>
//    </FORM>
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    public void doGet(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException 
	{
//    	if (paginas==null)
//		{
//    		LaadXML();
//    		loginpage = ((ElementWithValue) paginas.getElement("loginpage")).getValue();
//		}
    	String ID = request.getParameter("id");
    	System.out.println(ID);
    	response.setContentType("text/html");
	    PrintWriter out = response.getWriter();
	    if (ID==null) // gebruiker is nog niet ingelogd
		{
	    	out.println(loginpage);
		} else
		{
	    	// controleer of ID in de loggedinlist zit
			if(loggedInList.contains(ID))
			{
				// toon pagina voor de gebruiker die ermee overeen komt
				// TODO methode schrijven die pagina voor gebruiker genereert ifv zijn privileges
			}
			else 
			{
				out.println(loginpage);
			}
		}		
	}
    
    private Random random;
    private Vector<LoggedInID> loggedInList; 
    private Database Database;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		User LoggedUser = new User(request.getParameter("NAME"),request.getParameter("PASS"));
		// zoek naar usergegevens in database
		User CurrentUser = Database.read(LoggedUser,"getName;getPassword");
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

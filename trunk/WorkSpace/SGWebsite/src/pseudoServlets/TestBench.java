package pseudoServlets;

import htmlInterfaces.HTMLUtils;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Vector;


import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import login.Account;

import com.hp.gagawa.java.elements.Table;

import sessionTracking.Session;

import htmlInterfaces.*;
/**
 * 
 * 
 * @author Adam
 * @version 1.0
 */
@WebServlet("/test")
public class TestBench extends HttpServlet {
    
	Session sessie; 
	
	/**
     * @see HttpServlet#HttpServlet()
     */
    public TestBench() 
    {
        super();
        
        
    }

    @Override
    public void init(ServletConfig config) throws ServletException
    {
    	// TODO Auto-generated method stub
    	super.init(config);
    	PseudoServlet.initEverything("test", "C:\templates"); // moet nog veranderd worden in de juiste link
    	Account adam = new Account("adam", "adam");
    	sessie = new Session(adam);
    }
    
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    @Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
    	response.setContentType("text/html");
	    PrintWriter out = response.getWriter();
	    String pseudo = request.getParameter("ps");
	    if (pseudo==null)
	    {
	    	out.println("error, geen pseudoservlet meegegeven");
	    } else
	    {
	    	out.println(PseudoServlet.getPseudoServlet(pseudo).processRequest(PseudoServlet.RequestType.GET, request,sessie));
	    } 
	    out.close();
	}
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
    	response.setContentType("text/html");
	    PrintWriter out = response.getWriter();
	    String pseudo = request.getParameter("ps");
	    if (pseudo==null)
	    {
	    	out.println("error, geen pseudoservlet meegegeven");
	    } else
	    {
	    	out.println(PseudoServlet.getPseudoServlet(pseudo).processRequest(PseudoServlet.RequestType.GET, request,sessie));
	    }
	    out.close();
	}
}

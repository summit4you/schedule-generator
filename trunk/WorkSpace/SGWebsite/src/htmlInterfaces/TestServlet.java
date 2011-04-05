package htmlInterfaces;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <b>This is a class for testing and will be removed later on. </b></br>
 * This class should be replaced with a JUnit TEST
 * @author Alexander
 * @version 1.3
 * @see HTMLTablable
 */
@WebServlet("/testServlet")
public class TestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	private Dummy dum;
	private Vector<HTMLTablable> dumVec = new Vector<HTMLTablable>();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TestServlet() 
    {
        super();
        dum = new Dummy("Dumdum",128);
        
	    dumVec.add(new Dummy("Dum1",3));
	    dumVec.add(new Dummy("Dum1",2));
	    dumVec.add(new Dummy("Dum1",1));
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		response.setContentType("text/html");
	    PrintWriter out = response.getWriter();
	    out.println(HTMLUtils.toHTMLTable(HTMLInterfaceTool.fetchTableContent(dum)).write());   
	    out.println();
	    out.println(HTMLInterfaceTool.changeToDataTable("1",dumVec));

	}



}

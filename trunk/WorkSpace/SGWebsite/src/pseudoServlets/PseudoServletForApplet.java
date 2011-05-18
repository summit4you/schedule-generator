package pseudoServlets;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import sessionTracking.Session;
import applet.Edit;
import applet.EditVector;
import applet.Wrapper;
import database.Databasable;
import database.Database;

/**
 * @author Zjef
 * @version 1.0
 */
public abstract class PseudoServletForApplet extends PseudoServlet
{
	protected String appletName;
	
	public PseudoServletForApplet(String appletName)
	{
		super();
		this.appletName=appletName;
		templateFile="applet.tpl";
	}
	
	/**
	 * To implement by pseudos communicating with their corresponding applet
	 */
	public abstract void processAppletRequest(HttpServletRequest request,HttpServletResponse response,Session session);
	
	@Override
	public String processRequest(RequestType type, HttpServletRequest request, Session session)
	{
		String res=replaceTags(template,"JNLP",appletName);
		res=replaceTags(res,"URL",createLink(session));
		res=replaceTags(res,"PARAMS","");
		return res;
	}
	
	protected static void sendObjectToApplet(HttpServletResponse response,Serializable toWrite)
	{
		try 
		{
			response.setContentType("application/x-java-serialized-object");
			OutputStream outstr = response.getOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(outstr);
			oos.writeObject(toWrite);
			oos.flush();
			oos.close();
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	protected static Object readObjectFromApplet(HttpServletRequest request)
	{
		try 
		{
			InputStream in = request.getInputStream();
			ObjectInputStream inputFromApplet = new ObjectInputStream(in);
			Object res=inputFromApplet.readObject();
			in.close();
			return res;
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
		return null;
	}
	
	protected void receive(HttpServletRequest request,HttpServletResponse response)
	{
		try
		{
			EditVector wrappers=(EditVector)readObjectFromApplet(request);
			Database db=getDB();
			db.connect();
			for (Wrapper i:wrappers)
			{
				if (i.getEdit()==Edit.deleted)
				{
					db.delete((Databasable) i.getObject());
				}
				else
				{
					db.write((Databasable) i.getObject(),false);
				}
			}
			db.disconnect();
			sendObjectToApplet(response,"Changes saved");
		}
		catch(Exception e)
		{
			sendObjectToApplet(response,"An error occured while uploading. Your changes might not have been saved");
		}
	}
}
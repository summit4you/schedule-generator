package pseudoServlets;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import pseudoServlets.tools.PSTools;
import dataStructure.Building;
import dataStructure.Hardware;
import sessionTracking.Session;

public class BuildingEditor extends PseudoServletForApplet
{
	final private static String buildingTag="buildings";
	
	public BuildingEditor()
	{
		super("BuildingApplet");
	}
	
	@Override
	public String processRequest(RequestType type, HttpServletRequest request, Session session)
	{
		String res=replaceTags(template,"JNLP",appletName);
		res=replaceTags(res,"URL",createLink(session));
		res=replaceTags(res,"PARAMS",",hardware:'"+PSTools.implodeVector(Hardware.getAllHardware())+"'");
		return res;
	}

	@Override
	public TabName getTabName()
	{
		return TabName.EditBuildings;
	}
	
	@Override
	public void processAppletRequest(HttpServletRequest request, HttpServletResponse response, Session session)
	{
		if ("true".equals(request.getParameter(buildingTag)))
		{
			sendBuildings(request,response);
		}
		else
		{
			receive(request,response);
		}
	}
	
	private void sendBuildings(HttpServletRequest request,HttpServletResponse response)
	{
		readObjectFromApplet(request);
		sendObjectToApplet(response,PSTools.loadObjects(Building.class));
	}
}
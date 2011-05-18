package pseudoServlets;

import htmlBuilder.Site;
import htmlInterfaces.HTMLTablable;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import applet.Edit;
import applet.EditVector;
import applet.Wrapper;

import pseudoServlets.tools.PSTools;

import sessionTracking.Session;
import dataStructure.Building;
import dataStructure.Faculty;
import dataStructure.Hardware;
import dataStructure.Room;
import database.Databasable;
import database.Database;

/**
 * @author Zjef
 * @version 1.0
 */
public class BuildingTable extends TabAndDrop<Building>
{		
	public BuildingTable() 
	{
		super(Building.class,Room.class);
	}	
	
	@Override
	protected String createExpandScript(Vector<Building> buildings)
	{
		String res="";
		int buildingCounter=1;
		for (Building b:buildings)
		{
			res+="if (oTable.id=='"+createTabObjectID(buildingCounter)+"'){";
			int roomCounter=1;
			for (Room r:b.getRooms())
			{
				res+="if (nTr.id=='"+roomCounter+"'){";
				res+=" return '<b>##Available_Hardware_BuildingTab##</b> <select size=5>";
				if (r.getPresentHardware()!=null)
				{
					for (Hardware h:r.getPresentHardware())
					{
						res+="<option>"+h.toString()+"</option>";
					}
				}
				res+="</select>';";
				res+="}";
				roomCounter++;
			}
			res+="}";
			buildingCounter++;
		}
		return res;
	}

	@Override
	protected Vector<? extends HTMLTablable> getTableObjects(Building tabObject) 
	{
		return tabObject.getRooms();
	}

	@Override
	public TabName getTabName() 
	{
		return TabName.Buildings;
	}
	
	@Override
	protected String createOnOpen() 
	{
		return "";
	}
}
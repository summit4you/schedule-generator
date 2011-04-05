package pseudoServlets;

import htmlInterfaces.HTMLInterfaceTool;

import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import dataStructure.Building;
import dataStructure.Hardware;
import dataStructure.Room;
import database.Database;
import database.Search;

import sessionTracking.Session;

public class BuildingTable extends PseudoServlet
{
	public BuildingTable() 
	{
		super();
		templateFile="buildingTable.tpl";
	}
	
	private Vector<Building> getBuildings()
	{
		Database db=getDB();
		db.connect();
		Vector<Building> buildings=db.readAll(new Search(Building.class));
		db.disconnect();
		return buildings;
	}
	
	@Override
	public String processRequest(RequestType type, HttpServletRequest request,Session session) 
	{
		Vector<Building> buildings=getBuildings();
		String res=replaceTags(template,"TABS",createTabTitles(buildings));
		res=replaceTags(template,"TABLE",createTable(buildings));
		res=replaceTags(template,"EXPAND_SCRIPT",createExpandScript(buildings));
		res=replaceTags(template,"INIT_OPEN",createInitOpen(buildings));
		return res;
	}
	
	private String createBuildingID(int index)
	{
		return "b"+index;
	}
	
	private String createInitOpen(Vector<Building> buildings)
	{
		String res="";
		int counter=1;
		for (Building b:buildings)
		{
			res+="initTables("+createBuildingID(counter)+");";
			counter++;
		}
		return res;
	}
	
	private String createExpandScript(Vector<Building> buildings)
	{
		String res="";
		int buildingCounter=1;
		for (Building b:buildings)
		{
			res+="if (oTable.id=='"+buildingCounter+"'){";
			int roomCounter=1;
			for (Room r:b.getRooms())
			{
				res+="if (nTr.id=='"+roomCounter+"'){";
				res+="available hardware <select size=5>";
				for (Hardware h:r.getPresentHardware())
				{
					res+="<option>"+h.toString()+"</option>";
				}
				res+="</select>";
				res+="}";
			}
			res+="}";
			buildingCounter++;
		}
		return res;
	}
	
	private String createTabTitles(Vector<Building> buildings)
	{
		String res="";
		int counter=1;
		for (Building i:buildings)
		{
			res+="<li><a href='#tabs-"+counter+"'>"+i.getName()+"</a></li>";
			counter++;
		}
		return res;
	}

	private String createTable(Vector<Building> buildings)
	{
		String res="";
		int counter=1;
		for (Building b:buildings)
		{
			res+="<div id='tabs-'"+counter+">";
			res+=HTMLInterfaceTool.changeToHTMLTable(b.getRooms());
			res+="</div>";
			counter++;
		}
		return res;
	}
	
	@Override
	protected String getTabName() 
	{
		return "Buildings";
	}
}
package pseudoServlets;

import htmlBuilder.Site;
import htmlInterfaces.HTMLTablable;
import java.util.Vector;
import dataStructure.Building;
import dataStructure.Hardware;
import dataStructure.Room;

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
	public String getTabName() 
	{
		return Site.TabName.Buildings.toLanguageTag();
	}

	@Override
	protected String createOnOpen() 
	{
		return "";
	}
}
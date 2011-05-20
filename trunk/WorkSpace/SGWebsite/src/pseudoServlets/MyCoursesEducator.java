package pseudoServlets;

import java.util.Vector;

import htmlBuilder.Site;
import htmlInterfaces.HTMLInterfaceTool;

import javax.servlet.http.HttpServletRequest;

import com.hp.gagawa.java.elements.*;

import dataStructure.Course;
import dataStructure.Educator;
import dataStructure.Faculty;
import dataStructure.Hardware;
import dataStructure.Program;
import dataStructure.Student;
import dataStructure.Subcourse;
import database.Databasable;
import database.Database;
import database.Search;

import pseudoServlets.PseudoServlet.RequestType;
import sessionTracking.Session;

/**
 * <b>PseudoServlet that shows the user its courses</b> </br> </br> 
 * 
 * @version 1.0
 * @author Adam
 *
 */
public class MyCoursesEducator extends PseudoServlet
{
	private String popuptemplate;

	@Override
	protected void init()
	{
		super.init();
		popuptemplate=loadTemplate("EditSubcourse.tpl");
	}
	
	public MyCoursesEducator()
	{
		templateFile="MyCoursesEducator.tpl";
	}

	private String ShowCourses(Educator educator)
	{
		Table table = new Table();
		table.setBorder("0").setStyle("border-collapse:collapse");
		for (Course i : educator.getCourses())
		{
			Td col1 = new Td();
			B bold = new B();
			bold.appendText(i.getName());
			col1.appendChild(bold);
			Tr row1 = new Tr();
			row1.appendChild(col1);
			table.appendChild(row1);
			Td col3 = new Td();
			col3.appendText(HTMLInterfaceTool.changeToDataTable("courses", i.getSubcourses()));
			Tr row2 = new Tr();
			row2.appendChild(col3);
			table.appendChild(row2);
		}
		return table.write();
	}
	
	@Override
	public String processRequest(RequestType type, HttpServletRequest request, Session session)
	{
		if (type==RequestType.POST)
		{
			String id = request.getParameter("Sid");
			if (id!=null)
			{
				Database db = getDB();
				db.connect();
				Search s = new Search(Subcourse.class, id);
				Subcourse res = db.read(s);
				db.disconnect();
				if ((session.getAccount().getData() instanceof Educator)&&(((Educator) session.getAccount().getData()).getSubcourses().contains(res)))
				{
					res.setName(request.getParameter("name"));
					res.setProperties(request.getParameter("properties"));
					res.setBlockHours(Integer.parseInt(request.getParameter("blockHours")));
					res.setTotalnumberHours(Integer.parseInt(request.getParameter("totalNumberHours")));
					Vector<Hardware> neededhardware = new Vector<Hardware>();
					for (Hardware i : Hardware.getAllHardware())
					{
						if (request.getParameter(i.toValue())!=null);
						{
							Hardware hw = new Hardware(request.getParameter(i.toValue()));
							if (!(hw.toString()==null))
							{
								neededhardware.add(hw);
							}
						}
					}
					res.setNeededHardware(neededhardware);
					db.connect();
					db.write(res);
					db.disconnect();
					// return the updated content of the popup
					String response = replaceTags(popuptemplate,"MASTERSERVLET", createLink(session));
					response = replaceTags(response, "NAME", res.getName());
					response = replaceTags(response, "PROPERTIES", res.getProperties());
					response = replaceTags(response, "TOTALNUMBERHOURS", Integer.toString(res.getTotalnumberHours()));
					response = replaceTags(response, "BLOCKHOURS", Integer.toString(res.getBlockHours()));
					response = replaceTags(response, "HARDWARETABLE", makeHardwareTable(res));
					response = replaceTags(response, "HARDWAREOPTIONS", makeHardwareOptions());
					response = replaceTags(response, "SUBCOURSEID", res.getID().toString());
					response = replaceTags(response, "NOTICE","##Edit_done##");
					return response;
				}
				else
				{
					return "ERROR, you are not authorised to change that subcourse";
				}
			}
			else
			{
				return "ERROR, no id sent";
			}
		}
		else
		{
			String Sid = request.getParameter("Sid");
			if (Sid==null)
			{
				// a normal request has been sent, send the page back
				String response = replaceTags(template,"MASTERSERVLET", createLink(session)); 
				Databasable data = session.getAccount().getData();
				if (data instanceof Educator)
				{
					response = replaceTags(response, "COURSES",ShowCourses((Educator) data));
					return replaceTags(response, "SUBCOURSEOPTIONS", ListSubCourses((Educator) data));
				}
				else
				{
					return "ERROR: you are not an educator";
				}
			}
			else
			{
				// the edit button has been clicked, send the contents of the popup
				String response = replaceTags(popuptemplate,"MASTERSERVLET", createLink(session));
				Database db = getDB();
				db.connect();
				database.Search s = new Search(Subcourse.class, Sid);
				Subcourse res = db.read(s);
				db.disconnect();
				if (res!=null)
				{
					response = replaceTags(response, "NAME", res.getName());
					response = replaceTags(response, "PROPERTIES", res.getProperties());
					response = replaceTags(response, "TOTALNUMBERHOURS", Integer.toString(res.getTotalnumberHours()));
					response = replaceTags(response, "BLOCKHOURS", Integer.toString(res.getBlockHours()));
					response = replaceTags(response, "HARDWARETABLE", makeHardwareTable(res));
					response = replaceTags(response, "HARDWAREOPTIONS", makeHardwareOptions());
					response = replaceTags(response, "SUBCOURSEID", res.getID().toString());
					response = replaceTags(response, "NOTICE"," ");
					return response;
				}
				else 
				{
					return "ERROR, subcourse corresponding to ID not found";
				}
			}
		}
	}
	
	private String makeHardwareOptions()
	{
		String result = new String();
		for (Hardware i : Hardware.getAllHardware())
		{
			Option op = new Option();
			op.appendText(i.toValue()).setValue(i.toValue());
			result+=op.write();
		}
		return result;
	}

	private String makeHardwareTable(Subcourse res)
	{
		Table table = new Table();
		table.setId("hardware");
		Thead head = new Thead();
		Tr hrow = new Tr();
		Th hcol = new Th();
		hcol.appendText("##needed_hardware##");
		hrow.appendChild(hcol);
		head.appendChild(hrow);
		table.appendChild(head);
		Tbody body = new Tbody();
		for (Hardware i : res.getNeededHardware())
		{
			Input in = new Input();
			in.setType("hidden").setValue(i.toString()).setName(i.toString());
			Td col = new Td();
			col.appendChild(in).appendText(i.toString());
			Tr row = new Tr();
			row.appendChild(col);
			body.appendChild(row);
		}
		table.appendChild(body);
		return table.write();
	}

	private String ListSubCourses(Educator data)
	{
		String result=new String();
		for (Course i : data.getCourses())
		{
			for (Subcourse j : i.getSubcourses())
			{
				Option op = new Option();
				op.setValue(j.getID().toString()).appendText(j.getName());
				result+=op.write();
			}
		}
		return result;
	}

	@Override
	public TabName getTabName()
	{
		return PseudoServlet.TabName.MyCoursesEducator;
	}
}


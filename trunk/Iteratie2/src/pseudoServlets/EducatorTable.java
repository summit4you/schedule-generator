package pseudoServlets;

import htmlBuilder.Site;
import htmlInterfaces.HTMLInterfaceTool;
import htmlInterfaces.HTMLTablable;
import java.util.Vector;

import dataStructure.Course;
import dataStructure.Educator;
import dataStructure.Faculty;
import dataStructure.Subcourse;
import database.Database;

/**
 * @author Zjef
 * @version 1.0
 */
public class EducatorTable extends TabAndDrop<Faculty>
{
	public EducatorTable() 
	{
		super(Faculty.class,Educator.class);
	}
	
	@Override
	protected String createExpandScript(Vector<Faculty> tabObjects) 
	{
		String res="";
		int facultyCounter=1;
		for (Faculty f:tabObjects)
		{
			res+="if (oTable.id=='"+createTabObjectID(facultyCounter)+"'){";
			int eduCounter=1;
			for (Educator e:f.getEducators())
			{
				res+="if (nTr.id=='"+eduCounter+"'){";
				res+="return '";
				res+="<b>##Subcourses##</b>";
				if (e.getSubcourses()!=null&&e.getSubcourses().size()>0)
				{
					res+=HTMLInterfaceTool.changeToDataTable("dropTable1",e.getSubcourses());
				}
				else
				{
					res+=HTMLInterfaceTool.makeEmptyTable("dropTable1", Subcourse.class);
				}
				res+="<b>##Courses##</b>";
				if (e.getCourses()!=null&&e.getCourses().size()>0)
				{
					res+=HTMLInterfaceTool.changeToDataTable("dropTable2",e.getCourses());
				}
				else
				{
					res+=HTMLInterfaceTool.makeEmptyTable("dropTable2", Course.class);
				}
				res+="';}";
				eduCounter++;
			}
			res+="}";
			facultyCounter++;
		}
		return res;
	}

	@Override
	protected Vector<? extends HTMLTablable> getTableObjects(Faculty tabObject) 
	{
		return tabObject.getEducators();
	}

	@Override
	public TabName getTabName() 
	{	
		return TabName.Educators;
	}

	@Override
	protected String createOnOpen() 
	{
		return "\\$('#dropTable1').dataTable();\\$('#dropTable2').dataTable();";
	}
}
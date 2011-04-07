package pseudoServlets;

import htmlBuilder.Site;
import htmlInterfaces.HTMLInterfaceTool;
import java.util.Vector;
import dataStructure.Course;
import dataStructure.Faculty;
import dataStructure.Program;
import dataStructure.Subcourse;
import database.Database;

/**
 * @author Zjef
 * @version 1.0
 */
public class CourseTable extends TabAndList<Course>
{		
	public CourseTable() 
	{
		super();
	}
	
	@Override
	protected String createExpandScript(Vector<Course> courses)
	{
		String res="";
		int courseCounter=1;
		for (Course c:courses)
		{
			res+="if (nTr.id=='"+courseCounter+"'){";
			res+="return '";
			res+="<b>##Subcourses##</b>";
			if (c.getSubcourses()!=null && c.getSubcourses().size()>0)
			{
				res+=HTMLInterfaceTool.changeToDataTable("expandTable",c.getSubcourses());
			}
			else
			{
				res+=HTMLInterfaceTool.makeEmptyTable("expandTable", Subcourse.class);
			}
			res+="';}";
			courseCounter++;
		}
		return res;
	}

	@Override
	public String getTabName() 
	{
		return Site.TabName.Courses.toLanguageTag();
	}

	@Override
	protected Vector<Course> getTableObjects(String faculty, String program) 
	{
		Database db=getDB();
		db.connect();
		Faculty f=db.read(new database.Search(Faculty.class,"getName",faculty));
		if (f!=null)
		{
			for (Program p:f.getPrograms())
			{
				if (p.getName().equals(program))
				{
					db.disconnect();
					return p.getCourses();
				}
			}
		}
		db.disconnect();
		return null;
	}
}

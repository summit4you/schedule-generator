package planner;

import java.util.Vector;
import dataStructure.*;


/**
 *
 * @author matthiascaenepeel 
 * @version1.1
 */

public class MatsiesTest1
{
	private static Vector<Educator> edus;
	private static Vector<Course> courses;
	
	public static void main(String[] args)
	{
		int i=1;
		
		for(i=1;i<=10;i++)
		{
			edus.add(new Educator("jos"+i,"dreistoot"+i,i));
			Vector<Hardware> vec1 = new Vector<Hardware>();
			Vector<Educator> vec2 = new Vector<Educator>();
			Vector<Subcourse> vec3 = new Vector<Subcourse>();
			vec2.add(edus.elementAt(i));
			Subcourse testsub = new Subcourse("kak"+i,5*i,"prot"+i,5*i,2,vec1,vec2);
			vec3.add(testsub);
			courses.add(new Course("course"+i,vec3,edus.elementAt(i)));
		}
		System.out.println(courses.toString());
	}
}

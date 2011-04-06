package planner;

import java.util.Vector;
import java.lang.reflect.Method;

import dataStructure.*;


/**
 *
 * @author matthiascaenepeel 
 * @version1.1
 */

public class MatsiesTest1
{

	
	public static void main(String[] args)
	{
		
		Vector<Educator> edus = new Vector<Educator>();
		Vector<Course> courses = new Vector<Course>();
		
		
		for(int i=0;i<=6;i++)
		{
			edus.add(new Educator("jos"+i,"dreistoot"+i,i));
			Vector<Hardware> vec1 = new Vector<Hardware>();
			Vector<Educator> vec2 = new Vector<Educator>();
			Vector<Subcourse> vec3 = new Vector<Subcourse>();
			vec2.add(edus.elementAt(i));
			if((i % 2)==0)
			{
				Subcourse testsub = new Subcourse("kak"+2,2*i,"prot"+i,2*i,2,vec1,vec2);
				vec3.add(testsub);
			}
			else
			{
				Subcourse testsub = new Subcourse("kak"+3,2*i,"prot"+i,2*i,2,vec1,vec2);
				vec3.add(testsub);
			}
			courses.add(new Course("course"+i,vec3,edus.elementAt(i)));
		}
		System.out.println(courses.toString());
		Vector<Subcourseblock> blocks = Subcourseblocksgenerator.generateBlocks(courses);
		Vector<Subcourse> subs = Subcourseblocksgenerator.giveSubcourses(blocks);
		System.out.println(subs.toString());
		System.out.println(Subcourseblocksgenerator.giveHours(blocks).toString());
		System.out.println(blocks.size());
		Vector<Method> mets = new Vector<Method> ();
		Method met1 = null;
		Method met2 = null;
		try 
		{
			met1 = Subcourseblock.class.getMethod("getSubcourse");
		} catch (SecurityException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mets.add(met1);
		
		
		
//		blocks=MatsiesVectorToolbox.ordenVector(blocks,mets);
		
//		System.out.println("Nu zou het geordend moeten zijn");
//		Vector<Subcourse> subs2 = Subcourseblocksgenerator.giveSubcourses(blocks);
//		System.out.println(subs2.toString());
//		System.out.println(Subcourseblocksgenerator.giveHours(blocks).toString());
//		System.out.println(blocks.size());
		
		Vector<Bounds> mappedBlocksOnSub = new Vector<Bounds>();
		
		mappedBlocksOnSub = MatsiesVectorToolbox.mapper(blocks,mets);
		
//		for(Bounds bound:mappedBlocksOnSub)
//		{
//			System.out.println("Fingers dimming the light");
//			System.out.println(bound.getLowerBound());
//			System.out.println(bound.getUpperBound());
//			System.out.println(bound.getObject().toString());
//			System.out.println(mappedBlocksOnSub.getClass());
//		}
//		
		Vector<Bounds> mappedSubsOnEdu = new Vector<Bounds>();
		Vector<Method> mets2 = new Vector<Method>();
		Method meta = null;
		Method metb = null;
		Method metc = null;
		
		
		try 
		{
			meta = Bounds.class.getMethod("getObject");
		} 
		catch (SecurityException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mets2.add(meta);
		
		try 
		{
			metb = Subcourse.class.getMethod("getEducators");
		} 
		catch (SecurityException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mets2.add(metb);
		
		
		try 
		{
			metc = Vector.class.getMethod("firstElement");
		} 
		catch (SecurityException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mets2.add(metc);
	
	
		System.out.println("testTest1 "+mets2.size());
		System.out.println("testTest2 "+mappedBlocksOnSub.firstElement().getObject().getClass());
		mappedSubsOnEdu = MatsiesVectorToolbox.mapper(mappedBlocksOnSub,mets2);
		System.out.println("testTest3 "+ mappedSubsOnEdu.firstElement().getClass()); 
		
		for(Bounds bound:mappedSubsOnEdu)
		{
			System.out.println("Fingers dimming the light");
			System.out.println(bound.getLowerBound());
			System.out.println(bound.getUpperBound());
			System.out.println(bound.getObject().toString());
			System.out.println(mappedSubsOnEdu.getClass());
		}
	}
}

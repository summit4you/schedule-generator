package planner;

import java.lang.reflect.Method;
import java.util.Vector;

import choco.Choco;
import choco.cp.model.CPModel;
import choco.cp.solver.CPSolver;
import choco.kernel.model.Model;
import choco.kernel.model.variables.Variable;
import choco.kernel.model.variables.integer.IntegerVariable;
import choco.kernel.solver.Solver;
import dataStructure.Course;
import dataStructure.Educator;
import dataStructure.Hardware;
import dataStructure.Room;
import dataStructure.Subcourse;

/**
 * 
 * @author matthiascaenepeel
 * @version1.1
 */

public class Model2 
{
	static int numberofdays =1;
	static int startinghour = 8;
	static int endinghour = 18;
	
	public static void main(String[] args)
	{
		Model m = new CPModel();
		
		int totalNumberofHours = numberofdays*(endinghour-startinghour);
		Vector<Room> availableRooms = new Vector<Room>();
		
		for(int i=0;i<5;i++)
		{
			int j=i+1;
			Room r = new Room("plaats"+j,j*60);
			availableRooms.add(r);
		}
		
		Vector<Educator> edus = new Vector<Educator>();
		Vector<Course> courses = new Vector<Course>();
		
		
		for(int i=0;i<10;i++)
		{
			edus.add(new Educator("jos"+i,"dreistoot"+i,i));
			Vector<Hardware> vec1 = new Vector<Hardware>();
			Vector<Educator> vec2 = new Vector<Educator>();
			Vector<Subcourse> vec3 = new Vector<Subcourse>();
			vec2.add(edus.elementAt(i));
			Subcourse testsub = new Subcourse("kak"+i,10*i,"prot"+i,10,1,vec1,vec2);
			vec3.add(testsub);
			courses.add(new Course("course"+i,vec3,edus.elementAt(i)));
		}
		
		Vector<Subcourseblock> blocks = Subcourseblocksgenerator.generateBlocks(courses);
		
		System.out.println(blocks.size());
		Vector<Method> mets1 = new Vector<Method>();
		Method met1 = null;
		
		try 
		{
			met1 = Subcourseblock.class.getMethod("getSubcourse");
		} 
		catch (SecurityException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		catch (NoSuchMethodException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mets1.add(met1);
		
		
		Vector<Bounds> subcourses = MatsiesVectorToolbox.mapper(blocks, mets1);
		
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
		
		Vector<Bounds> educators = MatsiesVectorToolbox.mapper(subcourses, mets2);
		
		//Initialiseren van de variabelen
		
		IntegerVariable[] hourroomsVar = new IntegerVariable[blocks.size()];
		
		for(int l=0;l<blocks.size();l++)
		{
			Subcourseblock b = blocks.elementAt(l);
			Subcourse sub = b.getSubcourse();
			int stuCounterSub = sub.getStudentCounter();
			Vector<Hardware> hardwareSub = sub.getNeededHardware();
			Vector<Educator> edus2 = sub.getEducators();
			Educator edu =edus2.elementAt(0);
			int subnum = MatsiesVectorToolbox.giveIndex(subcourses,l);
			int edunum = MatsiesVectorToolbox.giveIndex(educators,subnum);
			
			//Genereren van de discrete set mogelijke variabelen
			Vector<Integer> possibleRooms = new Vector<Integer>();
			
			for(int i=0;i<availableRooms.size();i++)
			{
				if((availableRooms.elementAt(i).getcapacity()>=stuCounterSub)&&(availableRooms.elementAt(i).getPresentHardware().containsAll(hardwareSub)))
				{
					possibleRooms.add(i+1);
				}
			}
			
			//Hier moet nog komen dat de educator al dan niet beschikbaar is!
			
			Vector<Integer> possibleValues = new Vector<Integer>();
			for(int uur=0;uur<(totalNumberofHours);uur++)
			{
				for(int i:possibleRooms)
				{
					possibleValues.add(uur*availableRooms.size()+i);
				}
			}
			
			int[] posVal = new int[possibleValues.size()];
			for (int i=0;i<possibleValues.size();i++) 
			{
				posVal[i]= possibleValues.get(i).intValue();
			}
			
			IntegerVariable hourroom = Choco.makeIntVar("hourroom"+l,posVal);
			hourroomsVar[l]=hourroom;
			
		}
		
		//Toevoegen constraints
		m.addConstraint(Choco.allDifferent(hourroomsVar));
		
		Solver s = new CPSolver();
		s.read(m);
		System.out.println(s.solve());
		
		for(int l=0;l<blocks.size();l++)
		{
		System.out.print(s.getVar(hourroomsVar[l]).getVal()+" ");
		System.out.println();
		}
	}
}	
	




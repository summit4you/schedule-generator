package planner;

import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.Vector;


import dataStructure.*;
import choco.Choco;
import choco.cp.model.CPModel;
import choco.kernel.model.Model;
import choco.kernel.model.constraints.Constraint;
import choco.kernel.model.variables.integer.IntegerVariable;

/**
 * 
 * @author matthiascaenepeel
 * @version0.1
 */
public class Model1 
{
	
	
	//constants of the problem
	
	static int numberofdays = 5;
	static int startinghour = 8;
	static int endinghour = 18;
	
	//variables of the problem
	// maak deze vectoren final bij het vullen
	private static Vector<Room> availableRooms;
	private static Vector<Bounds> subcourses;
	private static Vector<Bounds> educators;
	private static Vector<Subcourseblock> blocks;
	private static Vector<Method> methods;
	
	public static void main(String[] args)
	{
	
	Model m = new CPModel();
	
	try
	{
		subcourses = MatsiesVectorToolbox.mapper(blocks,Subcourseblock.class.getMethod("getSubcourse"));
	} catch (SecurityException e)
	{
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (NoSuchMethodException e)
	{
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	try
	{
		methods.add(Bounds.class.getMethod("getObject"));
	} catch (SecurityException e)
	{
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (NoSuchMethodException e)
	{
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	try
	{
		methods.add(Subcourse.class.getMethod("getEducator"));
	} catch (SecurityException e)
	{
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (NoSuchMethodException e)
	{
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	educators = MatsiesVectorToolbox.mapper(subcourses,methods);
	
	IntegerVariable[][][][][] cells = new IntegerVariable[numberofdays][endinghour-startinghour][availableRooms.size()][subcourses.size()][educators.size()];

	
	for(int i=startinghour;i<=endinghour;i++)
	{
		for(int j=1;j<=numberofdays;j++)
		{
			for(int k=1;k<=availableRooms.size();k++)
			{
				for(int l=1;l<=blocks.size();l++)
				{
					Subcourseblock b = blocks.elementAt(l);
					Subcourse sub = b.getSubcourse();
					int stuNumsub = sub.getStudentCounter();
					Vector<Hardware> hardwareSub = sub.getNeededHardware();
					Vector<Educator> edus = sub.getEducators();
					Educator edu =edus.elementAt(1);
					Room r=availableRooms.elementAt(k);
					int stuNumroom = r.getcapacity();
					Vector<Hardware> hardwareRoom = r.getPresentHardware();
					int subnum = MatsiesVectorToolbox.giveIndex(subcourses,l);
					int edunum = MatsiesVectorToolbox.giveIndex(educators,subnum);
					if(stuNumsub<=stuNumroom && hardwareRoom.containsAll(hardwareSub) )
					{
						cells[i][j][k][subnum][edunum]=Choco.makeIntVar("cell"+i+j+k+subnum,1,i*j*k*subnum*edunum);
					}
				}
			}
		}
	}
	
	
	
	}
	
	
	
}

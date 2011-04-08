package planner;

import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.Vector;


import dataStructure.*;
import choco.Choco;
import choco.cp.model.CPModel;
import choco.cp.solver.CPSolver;
import choco.kernel.model.Model;
import choco.kernel.model.constraints.Constraint;
import choco.kernel.model.variables.integer.IntegerVariable;
import choco.*;


/**
 * 
 * @author matthiascaenepeel
 * @version1.1
 */
public class Model1 
{
	
	
	//constants of the problem
	
	static int numberofdays =2;
	static int startinghour = 8;
	static int endinghour = 11;
	
	//variables of the problem
	// maak deze vectoren final bij het vullen
	private static Vector<Bounds> subcourses;
	private static Vector<Bounds> educators;
	private static Vector<Subcourseblock> blocks;
	private static Vector<Method> methods;
	
	public static void main(String[] args)
	{
		
		Model m = new CPModel();
		CPSolver s = new CPSolver();
		Vector<Room> availableRooms = new Vector<Room>();
		
		for(int i=0;i<=6;i++)
		{
			Room r = new Room("plaats"+i,i*8);
			availableRooms.add(r);
		}
		
		Vector<Educator> edus = new Vector<Educator>();
		Vector<Course> courses = new Vector<Course>();
		
		
		for(int i=0;i<=10;i++)
		{
			edus.add(new Educator("jos"+i,"dreistoot"+i,i));
			Vector<Hardware> vec1 = new Vector<Hardware>();
			Vector<Educator> vec2 = new Vector<Educator>();
			Vector<Subcourse> vec3 = new Vector<Subcourse>();
			vec2.add(edus.elementAt(i));
			Subcourse testsub = new Subcourse("kak"+i,10*i,"prot"+i,5*i,2,vec1,vec2);
			vec3.add(testsub);
			courses.add(new Course("course"+i,vec3,edus.elementAt(i)));
		}
		
		Vector<Subcourseblock> blocks = Subcourseblocksgenerator.generateBlocks(courses);
		
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
		
		IntegerVariable[][][][][] cells = new IntegerVariable[endinghour-startinghour][numberofdays+1][availableRooms.size()][subcourses.size()][educators.size()];
		Constraint[][][][][] uniqueCells = new Constraint[endinghour-startinghour][numberofdays+1][availableRooms.size()][subcourses.size()][educators.size()];
				
		IntegerVariable[] subnumrange = new IntegerVariable[subcourses.size()];
		
		for(Integer i=0;i<subcourses.size();i++)
		{  
			IntegerVariable v1 = Choco.makeIntVar("v1", i, i);
			subnumrange[i]=IntegerVariable.class.cast(v1);
		}
		
		int teller1=0;
		for(int i=startinghour;i<=endinghour-1;i++)
		{
			for(int j=1;j<=numberofdays;j++)
			{
				for(int k=0;k<=availableRooms.size()-1;k++)
				{
					for(int l=0;l<=blocks.size()-1;l++)
					{
						Subcourseblock b = blocks.elementAt(l);
						Subcourse sub = b.getSubcourse();
						int stuNumsub = sub.getStudentCounter();
						Vector<Hardware> hardwareSub = sub.getNeededHardware();
						Vector<Educator> edus2 = sub.getEducators();
						Educator edu =edus2.elementAt(0);
						Room r=availableRooms.elementAt(k);
						int stuNumroom = r.getcapacity();
						Vector<Hardware> hardwareRoom = r.getPresentHardware();
						int subnum = MatsiesVectorToolbox.giveIndex(subcourses,l);
						int edunum = MatsiesVectorToolbox.giveIndex(educators,subnum);
						if(stuNumsub<=stuNumroom && hardwareRoom.containsAll(hardwareSub) )
						{
							cells[i-startinghour][j][k][subnum][edunum]=Choco.makeIntVar("cell"+i+j+k+subnum,1,numberofdays*(endinghour-startinghour)*availableRooms.size()*subcourses.size()*educators.size());
							System.out.println("testVarmaker " +i+" "+j+" "+k+" "+subnum+" "+edunum);
							m.addVariables(cells[i-startinghour][j][k][subnum][edunum]);
							teller1 ++;
							
						}
					}
				}
			}
			}
		
		for(int i=startinghour;i<=endinghour-1;i++)
		{
			for(int j=1;j<=numberofdays;j++)
			{
				for(int k=0;k<=availableRooms.size()-1;k++)
				{
					
				}
				
			}
				
		}
		
		s.read(m);
		s.solve();
		
		int teller2=0;
		for(int i=startinghour;i<=endinghour-1;i++)
		{
			for(int j=1;j<=numberofdays;j++)
			{
				for(int k=0;k<=availableRooms.size()-1;k++)
				{
					for(int t=0;t<=subcourses.size()-1;t++)
					{
						for(int u=0;u<=educators.size()-1;u++)
						{
							if(!(cells[i-startinghour][j][k][t][u]==null))
							{
								System.out.print(s.getVar(cells[i-startinghour][j][k][t][u]).getVal()+" ");
//								System.out.println("testSolver " +i+" "+j+" "+k+" "+t+" "+u);
								teller2 ++;
							}
						}
						
					}
				}
			}
		}
		System.out.println(teller1);
//		System.out.println(teller2);
	}
}



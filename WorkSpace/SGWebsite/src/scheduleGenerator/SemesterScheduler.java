package scheduleGenerator;

import java.util.Hashtable;
import java.util.Vector;

import other.Globals;

import calendar.IcsCalendar;

import dataStructure.Course;
import dataStructure.Educator;
import dataStructure.Program;
import dataStructure.Room;
import database.Database;
import database.Search;

/**
 * Deze klasse zal toelaten om een heel schedule te maken.
 * @author matthiascaenepeel
 * @version0.1
 */
public class SemesterScheduler 
{
	int numberOfWeeks;
	
	private SpaceTimeMatrix stm;
	
	private Vector<Educator> educators;
	private Vector<Program> programs;
	private Vector<Room> rooms;
	private Vector<Course> courses;
	
	private Hashtable<Educator,Vector<Integer>> unavailableHoursForEducator;
	private Hashtable<Program,Vector<Integer>> unavailableHoursForProgram;
	
	Vector<Node> nodes;
	
	SemesterScheduler(int startingHour, int endingHour, int numberOfDays,int numberOfWeeks)
	{
		if (loadData())
		{
			stm = new SpaceTimeMatrix(startingHour,endingHour,rooms.size(),numberOfDays);
			createBlocks();
		}
	}
	
	private boolean loadData()
	{
		Database db = new Database(Globals.databaseAdress,Globals.databaseName,Globals.databasePassword);
		if (db.connect())
		{
			educators = db.readAllSingle(new Search(Educator.class));
			programs = db.readAllSingle(new Search(Program.class));
			rooms = db.readAll(new Search(Program.class));
			courses = db.readAll(new Search(Course.class));
			return true;
			
		} 
		return false;	
	}
	
	private void createBlocks()
	{
		Vector<Vector<Subcourseblock>> weeks = Subcourseblock.generateBlocksPerWeek(courses,numberOfWeeks);
	}
	
	private void initializeUnavailableHours(Vector<Subcourseblock> blocks)
	{
		for(Subcourseblock block :blocks)
		{
			Vector<Educator > educators = block.getSubcourse().getEducators();
			for(Educator educator: educators)
			{
				//TODO
				//Vector<Integer> unavailableHours = educator.getUnavailableHours();
				//IcsCalendar calendar = educator.getCalender();
				//unavailableHoursForEducator.put(educator,unavailableHours);
			}
			
			Vector<Program> programs = block.getSubcourse().getCourse().getPrograms();
			for(Program program: programs)
			{
				//TODO
				//Vector<Integer> unavailableHours2 = program.getUnavailableHours();
				//IcsCalendar calendar = program.getCalender();
				//unavailableHoursForProgram.put(program,unavailableHours2);
			}
		}
	}
	
	
	//Deze methode wordt gebruikt in MakeWeekSchedule en gaat de constraints af.
	private int calcNextNewSpace(int stmIndex,Subcourseblock block)
	{
		for(int i=stmIndex+1;i<stm.getSize();i++)
		{ 
			if(Constraint.roomAvailableAtTime(i,stm))
			{	
				Room room = rooms.get(stm.giveRoom(i));
				if(Constraint.roomSufficient(room,block))
				{	
					int hour = stm.giveHour(i);
					if(Constraint.roomAvailableForBlock(i,block,stm))
					{
						if(Constraint.hourAvailable(hour,block,unavailableHoursForEducator,unavailableHoursForProgram))
						{
							return i;
						}
					}
				}
			}
		}
		return -1;
	}
	
	private void addUnavailableBlock(int hour,Subcourseblock block,Hashtable<Educator,Vector<Integer>> unavailableHoursForEducator,Hashtable<Program,Vector<Integer>> unavailableHoursForProgram)
	{
		Vector<Educator> educators = block.getSubcourse().getEducators();
		Vector<Program> programs = block.getSubcourse().getCourse().getPrograms();
		
		for(int h = 0;h < block.getHours(); h++)
		{
			for(Educator e:educators)
			{
				unavailableHoursForEducator.get(e).add(hour+h);
			}
			
			
			for(Program p:programs)
			{
				unavailableHoursForProgram.get(p).add(hour+h);
			}
		}
		
	}
	
	private void removeUnavailableBlock(int hour,Subcourseblock block,Hashtable<Educator,Vector<Integer>> unavailableHoursForEducator,Hashtable<Program,Vector<Integer>> unavailableHoursForProgram)
	{
		Vector<Educator> educators = block.getSubcourse().getEducators();
		Vector<Program> programs = block.getSubcourse().getCourse().getPrograms();
		
		for(int h = 0;h < block.getHours(); h++)
		{
			for(Educator e:educators)
			{
				unavailableHoursForEducator.get(e).remove(hour+h);
			}
			
			for(Program p:programs)
			{
				unavailableHoursForProgram.get(p).remove(hour+h);
			}
		}
		
	}
	
	private Node makeWeekSchedule(Vector<Subcourseblock> blocks,Vector<Room> rooms,int startingHour,int endingHour,int numberOfDays)
	{
		// initialize variables for scheduling algorithm 
		Hashtable<Educator,Vector<Integer>> unavailableHoursForEducator = new Hashtable<Educator,Vector<Integer>>();
		Hashtable<Program,Vector<Integer>> unavailableHoursForProgram = new Hashtable<Program,Vector<Integer>>();
		
		initializeUnavailableHours(blocks);
		Vector<Node> nodes = new Vector<Node>();
		
		Node currentNode = null;
		Node previousNode = null;
		Node swapNode = null;
		
		Subcourseblock currentBlock = null;
		
		int stmIndex = -1;
		int blockIndex = 0;
		
		// scheduling algorithm 
		while(blockIndex < blocks.size())
		{

			currentBlock = blocks.get(blockIndex);
			stmIndex = calcNextNewSpace(stmIndex,currentBlock);
			//We hoeven niet te backtracking en gaan naar de volgende Node
			if(!(stmIndex==-1))
			{
				swapNode = currentNode;
				currentNode = new Node(blockIndex,currentBlock,stmIndex,previousNode);
				previousNode = swapNode;
				stm.changeAtBlock(stmIndex,false,currentBlock.getHours());
				int hour = stm.giveHour(stmIndex);
				addUnavailableBlock(hour,currentBlock,unavailableHoursForEducator,unavailableHoursForProgram);
				stmIndex=-1;
				blockIndex++;
			}
			else if(previousNode==null)
			{
				
				System.out.println(">>> Semester.Scheduler.makeWeekSchedule: Er bestaat geen oplossing,loser.");
				System.out.println(blockIndex);
				//TODO het gebrek aan een oplossing opvangen  
				//System.exit(0);
				//return null	
			}
			else
			{	
				System.out.println(">>> Semester.Scheduler.makeWeekSchedule: Ik moet backtracken");
				stmIndex=currentNode.getPlaceInSchedule();
				stm.changeAt(stmIndex,true);
				int hour = stm.giveHour(stmIndex);
				removeUnavailableBlock(hour,currentBlock,unavailableHoursForEducator,unavailableHoursForProgram);
				currentNode = previousNode;
				previousNode = currentNode.getParent();
				blockIndex = blockIndex-1;
			}
		}
		return currentNode;
	}
	
	public void solve()
	{
		Vector<Vector<Subcourseblock>> weeks = Subcourseblock.generateBlocksPerWeek(courses,numberOfWeeks);
		for(int i=0;i<numberOfWeeks;i++)
		{
			Node node = makeWeekSchedule(weeks.get(i),rooms,stm.getStartingHour(),stm.getEndingHour(),stm.getNumberOfDays());
			nodes.add(i,node);
		}
	}
	
	
	public static void main(String[] args)
	{
		//Constanten + variabelen
		int startingHour = 8;
		int endingHour = 18;
		int numberOfDays = 5;
		int numberOfWeeks = 13;

		 // bestaande courses en rooms uit de database halen
		 // aanmaken van Subcourseblocks per week.
		 SemesterScheduler Scheduler = new SemesterScheduler(startingHour,endingHour,numberOfDays,numberOfWeeks);	 
		 
		 // Solve the problem week per week
		 Scheduler.solve();
	}
	
}

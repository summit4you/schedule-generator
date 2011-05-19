package scheduleGenerator;

import java.util.Hashtable;
import java.util.Vector;

import calendar.IcsCalendar;

import dataStructure.Course;
import dataStructure.Educator;
import dataStructure.Program;
import dataStructure.Room;

/**
 * Deze klasse zal toelaten om een heel schedule te maken.
 * @author matthiascaenepeel
 * @version0.1
 */
public class MakeSemSchedule 
{
	//Deze methode wordt gebruikt in MakeWeekSchedule en gaat de constraints af.
	public static int calcNextNewSpace(int stmIndex,SpaceTimeMatrix stm,Subcourseblock block,Vector<Room> rooms,Hashtable<Educator,Vector<Integer>> unavailableHoursForEducator,Hashtable<Program,Vector<Integer>> unavailableHoursForProgram)
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
	
	public static void InitializeUnavailableHours(Vector<Subcourseblock> blocks,Hashtable<Educator,Vector<Integer>> unavailableHoursForEducator,Hashtable<Program,Vector<Integer>> unavailableHoursForProgram)
	{
		for(Subcourseblock block :blocks)
		{
			Vector<Educator > educators = block.getSubcourse().getEducators();
			for(Educator educator: educators)
			{
				//Vector<Integer> unavailableHours = educator.getUnavailableHours();
				IcsCalendar calendar = educator.getCalender();
				unavailableHoursForEducator.put(educator,unavailableHours);
			}
			
			Vector<Program> programs = block.getSubcourse().getCourse().getPrograms();
			for(Program program: programs)
			{
				//Vector<Integer> unavailableHours2 = program.getUnavailableHours();
				IcsCalendar calendar = program.getCalender();
				unavailableHoursForProgram.put(program,unavailableHours2);
			}
		}
	}
	
	public static void addUnavailableBlock(int hour,Subcourseblock block,Hashtable<Educator,Vector<Integer>> unavailableHoursForEducator,Hashtable<Program,Vector<Integer>> unavailableHoursForProgram)
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
	
	public static void removeUnavailableBlock(int hour,Subcourseblock block,Hashtable<Educator,Vector<Integer>> unavailableHoursForEducator,Hashtable<Program,Vector<Integer>> unavailableHoursForProgram)
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
	
	public static Node MakeWeekSchedule(Vector<Subcourseblock> blocks,Vector<Room> rooms,int startingHour,int endingHour,int numberOfDays)
	{
		SpaceTimeMatrix stm = new SpaceTimeMatrix(startingHour,endingHour,rooms.size(),numberOfDays);
		Hashtable<Educator,Vector<Integer>> unavailableHoursForEducator = new Hashtable<Educator,Vector<Integer>>();
		Hashtable<Program,Vector<Integer>> unavailableHoursForProgram = new Hashtable<Program,Vector<Integer>>();
		InitializeUnavailableHours(blocks,unavailableHoursForEducator,unavailableHoursForProgram);
		Node currentNode = null;
		Node previousNode = null;
		Node swapNode = null;
		Subcourseblock currentBlock = null;
		int stmIndex = -1;
		int blockIndex = 0;
		
		while(blockIndex < blocks.size())
		{

			currentBlock = blocks.get(blockIndex);
			stmIndex = calcNextNewSpace(stmIndex,stm,currentBlock,rooms,unavailableHoursForEducator,unavailableHoursForProgram);
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
				System.out.println("Er bestaat geen oplossing,loser.");
				System.out.println(blockIndex);
				System.exit(0);
				//return null	
			}
			else
			{	
				System.out.println("Je moet backtracken");
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
	
	public static void main(String[] args)
	{
	 //Constanten + variabelen
		
	 int startingHour = 8;
	 int endingHour = 18;
	 int numberOfDays = 5;
	 int numberOfWeeks = 13;
	 
	 //Hier moet iets komen dat de bestaande courses en rooms uit de database haalt.
	 
	 Vector<Course> courses = new Vector<Course>();
	 Vector<Room> rooms = new Vector<Room>();
	 
	 // Aanmaken van Subcourseblocks per week.
	 
	 Vector<Vector<Subcourseblock>> weeks = Subcourseblock.generateBlocksPerWeek(courses,numberOfWeeks);
	 
	 // Aanmaken van lessenroosters per week. Dus vectoren met nodes in.
	 //In de vector nodes zal voor elke week, de laatste Node zitten; deze zal altijd een verwijzing
	 //naar de vorige Node bevatten, dus men heeft genoeg aan deze informatie.
	 
	 Vector<Node> nodes = new Vector<Node>();
	 
	 for(int i=0;i<numberOfWeeks;i++)
	 {
		 Node node = MakeWeekSchedule(weeks.get(i),rooms,startingHour,endingHour,numberOfDays);
		 nodes.add(i,node);
	 }
	 
	 // Omzetten van deze vectoren naar calenderfiles.
	 
	 
	 
	 
	}
	
}

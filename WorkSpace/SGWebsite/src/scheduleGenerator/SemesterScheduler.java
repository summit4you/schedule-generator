package scheduleGenerator;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Vector;

import net.fortuna.ical4j.filter.Filter;
import net.fortuna.ical4j.filter.HasPropertyRule;
import net.fortuna.ical4j.filter.PeriodRule;
import net.fortuna.ical4j.model.Component;
import net.fortuna.ical4j.model.DateTime;
import net.fortuna.ical4j.model.Period;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.property.DtStart;
import net.fortuna.ical4j.model.property.Location;
import net.fortuna.ical4j.model.property.Summary;

import other.Globals;
import pseudoServlets.tools.PSTools;

import calendar.IcsCalendar;
import calendar.SubCourseEvent;
import calendar.Transformation;
import calendar.Translator;
import calendar.UnavailableEvent;

import dataStructure.Building;
import dataStructure.Course;
import dataStructure.Educator;
import dataStructure.Program;
import dataStructure.Room;
import dataStructure.Subcourse;
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
	int semesterIndex;
	int weekIndex;
	
	private SpaceTimeMatrix stm;
	
	private Vector<Educator> educators;
	private Vector<Program> programs;
	private Vector<Room> rooms;
	private Vector<Course> courses;
	
	private int startWeek;
	private int startMonth;
	private int startYear;
	
	private int endWeek;
	private int endMonth;
	private int endYear;
	
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
	
	
	private void intitSemesterData(int semesterIndex)
	{
		IcsCalendar semesterCalendar = Translator.loadSemesterCalendar();
		Vector<VEvent> semesterBounds = new Vector<VEvent>(new Filter(new HasPropertyRule(new Summary("START"))).filter(semesterCalendar.getEvents(Component.VEVENT)));
		startWeek=Transformation.dateToCalendar(semesterBounds.get(semesterIndex).getStartDate().getDate()).get(Calendar.WEEK_OF_YEAR);
		startMonth=Transformation.dateToCalendar(semesterBounds.get(semesterIndex).getStartDate().getDate()).get(Calendar.MONTH);
		startYear=Transformation.dateToCalendar(semesterBounds.get(semesterIndex).getStartDate().getDate()).get(Calendar.YEAR);
		semesterBounds = new Vector<VEvent>(new Filter(new HasPropertyRule(new Summary("END"))).filter(semesterCalendar.getEvents(Component.VEVENT)));
		endWeek=Transformation.dateToCalendar(semesterBounds.get(semesterIndex).getStartDate().getDate()).get(Calendar.WEEK_OF_YEAR);
		endMonth=Transformation.dateToCalendar(semesterBounds.get(semesterIndex).getStartDate().getDate()).get(Calendar.MONTH);
		endYear=Transformation.dateToCalendar(semesterBounds.get(semesterIndex).getStartDate().getDate()).get(Calendar.YEAR);
		
	}
	
	private void generateCalendars()
	{
		intitSemesterData(semesterIndex);
		
		for(Course course: courses)
		{
			Vector<Subcourse> subs = course.getSubcourses(); 
			
			for(Subcourse sub: subs)
			{
				IcsCalendar subCalendar = sub.getCalendar();
				Vector<Subcourseblock> blocks = sub.getBlocks();
				
				for(Subcourseblock block: blocks)
				{
					int week = block.getWeek();
					int blockLength = block.getHours();
					int day = block.getDay();
					int hourInDay = block.getHourInDay();
					Vector<Educator> educators = block.getSubcourse().getEducators();
					Room room = rooms.get(block.getStmIndex());
					Building building = room.getBuilding();
					GregorianCalendar start = new GregorianCalendar(startYear,startMonth,startWeek,day,hourInDay);
					start.add(Calendar.WEEK_OF_YEAR,week);
					GregorianCalendar end = new GregorianCalendar(startYear,startMonth,startWeek,day,hourInDay+blockLength);
					end.add(Calendar.WEEK_OF_YEAR,week);
					SubCourseEvent subEvent = new SubCourseEvent("block.getSubcourse().toString()",start,end,PSTools.implodeVector(educators),building,room);	
				}
				
				subCalendar.write();
					
			}
			
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
			for(Room r:rooms)
			{
				r.setBuilding((Building) db.read(new Search(r.getID().toString(),Building.class,"getRooms")));
			}
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
				unavailableHoursForEducator.put(educator,new Vector<Integer>());

			}
			
			Vector<Program> programs = block.getSubcourse().getCourse().getPrograms();
			
			for(Program program: programs)
			{
				unavailableHoursForProgram.put(program,new Vector<Integer>());
			}
		}
	}
	
	
	//Deze methode wordt gebruikt in MakeWeekSchedule en gaat de constraints af.
	private int calcNextNewSpace(int stmIndex,Subcourseblock block,int week)
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
							if(Constraint.dateAvailable(i, semesterIndex,week,block,stm,room))
							{
								return i;
							}
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
	
	private void makeWeekSchedule(Vector<Subcourseblock> blocks,Vector<Room> rooms,int startingHour,int endingHour,int numberOfDays,int weekNumber)
	{
		// initialize variables for scheduling algorithm 
		Hashtable<Educator,Vector<Integer>> unavailableHoursForEducator = new Hashtable<Educator,Vector<Integer>>();
		Hashtable<Program,Vector<Integer>> unavailableHoursForProgram = new Hashtable<Program,Vector<Integer>>();
		
		int week = weekNumber;
		initializeUnavailableHours(blocks);
		Vector<Node> nodes = new Vector<Node>();
		
		Node currentNode = null;
		Node previousNode = null;
		Node swapNode = null;
		
		Subcourseblock currentBlock = null;
		SpaceTimeMatrix stm = new SpaceTimeMatrix(startingHour,endingHour,rooms.size(),numberOfDays); 
		int stmIndex = -1;
		int blockIndex = 0;
		
		// scheduling algorithm 
		while(blockIndex < blocks.size())
		{

			currentBlock = blocks.get(blockIndex);
			stmIndex = calcNextNewSpace(stmIndex,currentBlock,week);
			//We hoeven niet te backtracking en gaan naar de volgende Node
			if(!(stmIndex==-1))
			{
				swapNode = currentNode;
				currentNode = new Node(blockIndex,currentBlock,stmIndex,previousNode);
				previousNode = swapNode;
				stm.changeAtBlock(stmIndex,false,currentBlock.getHours());
				currentBlock.setStmIndex(stmIndex);
				currentBlock.setWeek(weekNumber);
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
				currentBlock.setStmIndex(-1);
				int hour = stm.giveHour(stmIndex);
				removeUnavailableBlock(hour,currentBlock,unavailableHoursForEducator,unavailableHoursForProgram);
				currentNode = previousNode;
				previousNode = currentNode.getParent();
				blockIndex = blockIndex-1;
			}
		}
		
		while(!(currentNode.getParent()==null))
		{
			Subcourseblock block = currentNode.getBlock();
			int i = block.getStmIndex();
			block.setDay(stm.giveDay(i));
			block.setHourInDay(stm.giveHourInDay(i));
			block.setRoom(stm.giveRoom(i));
		}
		
	}
	
	public void solve(int semesterIndex,int StartintHour,int EndingHour,int NumberOfDays)
	{
		this.semesterIndex=semesterIndex;
		Vector<Vector<Subcourseblock>> weeks = Subcourseblock.generateBlocksPerWeek(courses,numberOfWeeks);
		for(int i=0;i<numberOfWeeks;i++)
		{
			makeWeekSchedule(weeks.get(i),rooms,StartingHour,EndingHour,NumberOfDays,i);
		}
	}
	
	public int CalculateNumberOfWeeks()
	{
		int numberOfWeeks;
		
		IcsCalendar semesterCalendar = Translator.loadSemesterCalendar();
		Vector<VEvent> semesterBounds = new Vector<VEvent>(new Filter(new HasPropertyRule(new Summary("START"))).filter(semesterCalendar.getEvents(Component.VEVENT)));
		startWeek=Transformation.dateToCalendar(semesterBounds.get(semesterIndex).getStartDate().getDate()).get(Calendar.WEEK_OF_YEAR);
		semesterBounds = new Vector<VEvent>(new Filter(new HasPropertyRule(new Summary("END"))).filter(semesterCalendar.getEvents(Component.VEVENT)));
		endWeek=Transformation.dateToCalendar(semesterBounds.get(semesterIndex).getStartDate().getDate()).get(Calendar.WEEK_OF_YEAR);
		numberOfWeeks = endWeek - startWeek;
		
		return numberOfWeeks;
	}
	
	
	public static void main(String[] args)
	{
		//Constanten + variabelen
		int semesterIndex = 1;
		int startingHour = 8;
		int endingHour = 18;
		int numberOfDays = 5;
		int numberOfWeeks = 13;

		 // bestaande courses en rooms uit de database halen
		 // aanmaken van Subcourseblocks per week.
		 SemesterScheduler Scheduler = new SemesterScheduler(startingHour,endingHour,numberOfDays,numberOfWeeks);	 
		 
		 // Solve the problem week per week
		 Scheduler.solve(semesterIndex);
	}
	
}

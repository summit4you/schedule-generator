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
import net.fortuna.ical4j.model.WeekDay;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.property.DtStart;
import net.fortuna.ical4j.model.property.Location;
import net.fortuna.ical4j.model.property.Summary;

import other.Globals;
import pseudoServlets.tools.PSTools;


import calendar.IcsCalendar;
import calendar.SemesterEndEvent;
import calendar.SemesterStartEvent;
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
public class SemesterScheduler implements Runnable
{
	private String status;
	private String kindOfSolution;
	private Thread thread;
	private boolean running;
	
	int numberOfWeeks;
	int semesterIndex;
	int weekIndex;
	
	private Vector<Educator> educators;
	private Vector<Program> programs;
	private Vector<Room> rooms;
	private Vector<Course> courses;
	
	private int startWeek;
	private int startMonth;
	private int startYear;
	private int startDay;

	
	private int endWeek;
	private int endMonth;
	private int endYear;
	private int endDay;
	
	private Hashtable<Educator,Vector<Integer>> unavailableHoursForEducator;
	private Hashtable<Program,Vector<Integer>> unavailableHoursForProgram;
	
	Vector<Node> nodes;
	
	public SemesterScheduler()
	{
		//System.out.println("SemesterScheduler");
		unavailableHoursForEducator = new Hashtable<Educator,Vector<Integer>>();
		unavailableHoursForProgram = new Hashtable<Program,Vector<Integer>>();

		if (loadData())
		{
			System.out.println("Load check");
		}
		else
		{
			System.out.println("Load fail");
		}
		
//		programs= new Vector<Program>();
//		educators = TestToolbox.generateEducators(15);
//		rooms = TestToolbox.generateRooms(5, 20, 100);
//		courses = TestToolbox.generateCourses(10,educators,16,2,30,2);
		
		
		
	}
	
	
	private void intitSemesterData(int semesterIndex)
	{
		//System.out.println("intitSemesterData");
		IcsCalendar semesterCalendar = Translator.loadSemesterCalendar();
		Vector<VEvent> semesterBounds = new Vector<VEvent>(new Filter(new HasPropertyRule(new Summary(SemesterStartEvent.descr))).filter(semesterCalendar.getCalendar().getComponents(Component.VEVENT)));
		startWeek=Transformation.dateToCalendar(semesterBounds.get(semesterIndex).getStartDate().getDate()).get(Calendar.WEEK_OF_YEAR);
		startMonth=Transformation.dateToCalendar(semesterBounds.get(semesterIndex).getStartDate().getDate()).get(Calendar.MONTH);
		startYear=Transformation.dateToCalendar(semesterBounds.get(semesterIndex).getStartDate().getDate()).get(Calendar.YEAR);
		startDay=Transformation.dateToCalendar(semesterBounds.get(semesterIndex).getStartDate().getDate()).get(Calendar.DAY_OF_MONTH);
		semesterBounds = new Vector<VEvent>(new Filter(new HasPropertyRule(new Summary(SemesterEndEvent.descr))).filter(semesterCalendar.getCalendar().getComponents(Component.VEVENT)));
		endWeek=Transformation.dateToCalendar(semesterBounds.get(semesterIndex).getStartDate().getDate()).get(Calendar.WEEK_OF_YEAR);
		endMonth=Transformation.dateToCalendar(semesterBounds.get(semesterIndex).getStartDate().getDate()).get(Calendar.MONTH);
		endYear=Transformation.dateToCalendar(semesterBounds.get(semesterIndex).getStartDate().getDate()).get(Calendar.YEAR);
		endDay=Transformation.dateToCalendar(semesterBounds.get(semesterIndex).getStartDate().getDate()).get(Calendar.DAY_OF_MONTH);
		
		numberOfWeeks = endWeek - startWeek;
		
	}
	
	private void generateCalendars()
	{
		//System.out.println("generateCalendars");
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
					Room room = rooms.get((block.getRoom()));
					Building building = room.getBuilding();
					
					System.out.println();
					System.out.println(block);
					System.out.println("Voor in kalenderfiles");
					System.out.println(block.getSubcourse());
					System.out.println("Week is "+week);
					System.out.println("Uur is "+hourInDay);
					System.out.println("Docenten zijn "+educators );
					System.out.println("room is "+room);
					System.out.println("building is "+building);

					GregorianCalendar start = new GregorianCalendar(startYear,startMonth,startDay,hourInDay,0);
					start.add(Calendar.WEEK_OF_YEAR,week);
					start.add(Calendar.DAY_OF_WEEK, day);
					GregorianCalendar end = new GregorianCalendar(startYear,startMonth,startDay,hourInDay+blockLength,0);
					end.add(Calendar.WEEK_OF_YEAR,week);
					end.add(Calendar.DAY_OF_WEEK, day);
					SubCourseEvent subEvent = new SubCourseEvent(block.getSubcourse().toString(),start,end,PSTools.implodeVector(educators),building,room);	
					subCalendar.addSubCourseEvent(subEvent);
					
					
				}
			
				subCalendar.write();
					
			}
			
		}
	}
	
	private boolean loadData()
	{
		//System.out.println("loadData");
		Database db = new Database(Globals.databaseAdress,Globals.databaseName,Globals.databasePassword);
		if (db.connect())
		{
			educators = db.readAllSingle(new Search(Educator.class));
			programs = db.readAllSingle(new Search(Program.class));
			rooms = db.readAll(new Search(Room.class));
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
		//System.out.println("createBlocks");
		Vector<Vector<Subcourseblock>> weeks = Subcourseblock.generateBlocksPerWeek(courses,numberOfWeeks);
		
		System.out.println();
		System.out.println("Alle blocks");
		System.out.println(weeks);
	}
	
	private void initializeUnavailableHours(Vector<Subcourseblock> blocks)
	{
		//System.out.println("initializeUnavailableHours");
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
//	private int calcNextNewSpace(int stmIndex,Subcourseblock block,int week, SpaceTimeMatrix stm)
//	{
//		//System.out.println("calcNextNewSpace");
//		for(int i=stmIndex+1;i<stm.getSize();i++)
//		{ 
//			if(Constraint.roomAvailableAtTime(i,stm))
//			{	
//				Room room = rooms.get(stm.giveRoom(i));
//				if(Constraint.roomSufficient(room,block))
//				{	
//					int hour = stm.giveHour(i);
//					if(Constraint.roomAvailableForBlock(i,block,stm))
//					{
//						if(Constraint.hourAvailable(hour,block,unavailableHoursForEducator,unavailableHoursForProgram))
//						{
////							return i;
//							if(Constraint.dateAvailable(i, semesterIndex,week,block,stm,room))
//							{
//								return i;
//							}
//						}
//					}
//				}
//			}
//		}
//		return -1;
//	}
	
	private int calcNextNewSpace(int stmIndex,Subcourseblock block,int week, SpaceTimeMatrix stm)
	{
		//System.out.println("calcNextNewSpace");
		for(int i=stmIndex+1;i<stm.getSize();i++)
		{ 
			if(Constraint.roomAvailableAtTime(i,stm))
			{	
				Room room = rooms.get(stm.giveRoom(i));
				
					int hour = stm.giveHour(i);
					if(Constraint.roomAvailableForBlock(i,block,stm))
					{
						if(Constraint.hourAvailable(hour,block,unavailableHoursForEducator,unavailableHoursForProgram))
						{
//							return i;
							if(Constraint.dateAvailable(i, semesterIndex,week,block,stm,room))
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
		//System.out.println("addUnavailableBlock");
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
		//System.out.println("removeUnavailableBlock");
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
		//System.out.println("makeWeekSchedule");
		SpaceTimeMatrix stm = new SpaceTimeMatrix(startingHour,endingHour,rooms.size(),numberOfDays); 
		
		int week = weekNumber;
		initializeUnavailableHours(blocks);
		
		Node currentNode = null;
		Node previousNode = null;
		Node swapNode = null;
		
		Subcourseblock currentBlock = null;
		
		int stmIndex = -1;
		int blockIndex = 0;
		
		// scheduling algorithm 
	  
		while(blockIndex < blocks.size())
		{
//			System.out.println("blockIndex is "+blockIndex);
			currentBlock = blocks.get(blockIndex);
			stmIndex = calcNextNewSpace(stmIndex,currentBlock,week,stm);
			//We hoeven niet te backtracking en gaan naar de volgende Node
			if(!(stmIndex==-1))
			{
				//System.out.println("makeNode");
				currentNode = new Node(blockIndex,currentBlock,stmIndex,currentNode);
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
				
				kindOfSolution="Ended: Er bestaat geen oplossing";
				this.stop();
				System.out.println(blockIndex);
				//TODO het gebrek aan een oplossing opvangen  
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
				currentNode = currentNode.getParent();
				blockIndex = blockIndex-1;
			}
		}
		
		
		while(!(currentNode==null))
		{	
			
			Subcourseblock block = currentNode.getBlock();
			Subcourse sub = block.getSubcourse();
			sub.addBlock(block);
			int i = block.getStmIndex();
			block.setDay(stm.giveDay(i));
			block.setHourInDay(stm.giveHourInDay(i));
			block.setRoom(stm.giveRoom(i));
			
			System.out.println();
			System.out.println("Juist uit algoritme");
			System.out.println("Node is is "+currentNode.number);
			System.out.println("Block is "+block);
			System.out.println("Index is "+i);
			System.out.println("Dag is "+stm.giveDay(i));
			System.out.println("Uur is "+stm.giveHourInDay(i));
			System.out.println("Uur in block is "+block.getHourInDay());
			System.out.println("Relatief Uur is "+stm.giveHour(i));
			System.out.println("Room is "+stm.giveRoom(i));
			System.out.println("Educator is"+block.getSubcourse().getEducators().get(0));
			
			currentNode=currentNode.getParent();
			
		}
		
}
	
	public void solve(int semIndex,int StartingHour,int EndingHour,int NumberOfDays)
	{
		//System.out.println("solve");
		this.semesterIndex=semIndex-1;
		intitSemesterData(semesterIndex);
		createBlocks();
		Vector<Vector<Subcourseblock>> weeks = Subcourseblock.generateBlocksPerWeek(courses,numberOfWeeks);
	
		for(int i=0;i<numberOfWeeks;i++)
		{
			makeWeekSchedule(weeks.get(i),rooms,StartingHour,EndingHour,NumberOfDays,i);
		}
		generateCalendars();
	}
	
	public int CalculateNumberOfWeeks()
	{
		//System.out.println("CalculateNumberOfWeeks");
		int numberOfWeeks;
		
		IcsCalendar semesterCalendar = Translator.loadSemesterCalendar();
		Vector<VEvent> semesterBounds = new Vector<VEvent>(new Filter(new HasPropertyRule(new Summary("START"))).filter(semesterCalendar.getEvents(Component.VEVENT)));
		startWeek=Transformation.dateToCalendar(semesterBounds.get(semesterIndex).getStartDate().getDate()).get(Calendar.WEEK_OF_YEAR);
		semesterBounds = new Vector<VEvent>(new Filter(new HasPropertyRule(new Summary("END"))).filter(semesterCalendar.getEvents(Component.VEVENT)));
		endWeek=Transformation.dateToCalendar(semesterBounds.get(semesterIndex).getStartDate().getDate()).get(Calendar.WEEK_OF_YEAR);
		numberOfWeeks = endWeek - startWeek;
		
		return numberOfWeeks;
	}
	
	
	public void start(int semester)
	{
		running = true;
		semesterIndex=semester;
		if (thread==null || !thread.isAlive())
		{
			thread = new Thread(this);
			thread.start();	
		}
	}
	
	private void stop()
	{
		thread.stop();
	}
	
	public void forceStop()
	{
		if (thread!=null)
		{
			kindOfSolution="Stopped by user";
			thread.stop();
		}
	}
	
	
	public static void main(String[] args)
	{
		//Constanten + variabelen
		int semesterIndex = 1;
		int startingHour = 8;
		int endingHour = 18;
		int numberOfDays = 5;
		//int numberOfWeeks = 2;

		IcsCalendar semesterCalendar = new IcsCalendar(Translator.getCalendarFileNameSemesterCalendar());
		semesterCalendar.addSGEvent(new SemesterStartEvent(new GregorianCalendar(2010,8,6,8,0), new GregorianCalendar(2010,8,6,18,0)));
//		semesterCalendar.addSGEvent(new SemesterEndEvent(new GregorianCalendar(2010,11,14,8,0), new GregorianCalendar(2010,11,14,18,0)));
		semesterCalendar.write();
		System.out.println(semesterCalendar);
		IcsCalendar holidayCalendar =  new IcsCalendar(Translator.getCalendarFileNameHolidayCalendar());
		holidayCalendar.addSGEvent(new UnavailableEvent(new GregorianCalendar(2010,8,6,8,0), new GregorianCalendar(2010,8,6,18,0)));
		holidayCalendar.write();
		System.out.println(holidayCalendar);
		
//		 // bestaande courses en rooms uit de database halen
//		 // aanmaken van Subcourseblocks per week.
//		 SemesterScheduler Scheduler = new SemesterScheduler(startingHour,endingHour,numberOfDays);
//		 
//		 // Solve the problem week per week
//		 Scheduler.solve(semesterIndex,startingHour,endingHour,numberOfDays);
	}


	@Override
	public void run()
	{
		status="Started: Calculating";
		this.solve(semesterIndex,8,18,5);
		status="Ended: "+kindOfSolution;
		
	}
	
	public String  getStatus()
	{
		return status;
	}
	
}

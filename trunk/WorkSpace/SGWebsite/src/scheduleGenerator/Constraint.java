package scheduleGenerator;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.Vector;

import pseudoServlets.tools.PSTools;

import net.fortuna.ical4j.filter.Filter;
import net.fortuna.ical4j.filter.HasPropertyRule;
import net.fortuna.ical4j.model.Component;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.property.Summary;

import calendar.IcsCalendar;
import calendar.SubCourseEvent;
import calendar.Transformation;
import calendar.Translator;

import dataStructure.Building;
import dataStructure.Course;
import dataStructure.Educator;
import dataStructure.Program;
import dataStructure.Room;
import dataStructure.Subcourse;

/**
 * 
 * @author matthiascaenepeel
 * @version0.1
 */

public class Constraint 
{

	/**
	 * Methode gaat na of het lessenrooster dan vrij is.
	 */
	public static boolean roomAvailableAtTime(int i, SpaceTimeMatrix stm)
	{
		if (stm!=null)
		{
			return stm.checkAt(i);
		}
		else
		{
			return false;
		}
	}
	
	/**
	* Methode gaat na of het lokaal groot genoeg is en het vereiste materiaal bevat.
	*/
	public static boolean roomSufficient(Room room, Subcourseblock block)
	{
		if (block!=null && room!=null)
		{
			Subcourse subcourse = block.getSubcourse();	
			if ((subcourse!=null)&&(room.getPresentHardware()!=null))
			{
				if (subcourse.getNeededHardware()!=null)
				{
					return (room.getcapacity()>=subcourse.getStudentCounter())&&(room.getPresentHardware().containsAll(subcourse.getNeededHardware()));
				}
				else
				{
					return (room.getcapacity()>=subcourse.getStudentCounter());
				}
			}
		}
		return false;
	}
	
	/**
	 * Methode gaat na of het lokaal de uren daarna nog vrij is.
	 */
	public static boolean roomAvailableForBlock(int i, Subcourseblock block, SpaceTimeMatrix stm)
	{
		if (stm!=null && block!=null)
		{
			int daylength = (stm.getEndingHour()-stm.getStartingHour());
			int hour = stm.giveHour(i);
			int roomNumber = stm.getNumberOfDays();
			if(((hour%daylength)+block.getHours())<=daylength) //Zodat je niet sprijdt over verschillende dagen!
			{
				for(int j=1;j==block.getHours();j++)
				{
					if(!(roomAvailableAtTime(i+j*roomNumber,stm)))
					{
						return false;
					}
				}
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Methode gaat na of de prof en studenten van die les op dat uur nog vrij zijn.
	 */
	
	public static boolean hourAvailable(int hour,Subcourseblock block,Hashtable<Educator,Vector<Integer>> unavailableHoursForEducator,Hashtable<Program,Vector<Integer>> unavailableHoursForProgram)
	{
		boolean b = true;
		
		Vector<Educator> educators = block.getSubcourse().getEducators();
		Vector<Program> programs = block.getSubcourse().getCourse().getPrograms();
		
		for(int h = 0;h < block.getHours(); h++)
		{
			for(Educator e:educators)
			{
				if(unavailableHoursForEducator.get(e).contains(hour+h))
				{
					return b = false;
				}
			}
			
			for(Program p:programs)
			{
				if(unavailableHoursForProgram.get(p).contains(hour+h))
				{
					return b = false;
				}
			}
		
		}
		
		return b;
	}
	
	public static boolean dateAvailable(int i,int semesterIndex,int week,Subcourseblock block,SpaceTimeMatrix stm,Room room)
	{
		boolean b = true;
		
		IcsCalendar semesterCalendar = Translator.loadSemesterCalendar();
		IcsCalendar holidayCalendar = Translator.loadHolidayCalendar();
		Vector<VEvent> semesterBounds = new Vector<VEvent>(new Filter(new HasPropertyRule(new Summary("START"))).filter(semesterCalendar.getEvents(Component.VEVENT)));
		int startWeek=Transformation.dateToCalendar(semesterBounds.get(semesterIndex).getStartDate().getDate()).get(Calendar.WEEK_OF_YEAR);
		int startMonth=Transformation.dateToCalendar(semesterBounds.get(semesterIndex).getStartDate().getDate()).get(Calendar.MONTH);
		int startYear=Transformation.dateToCalendar(semesterBounds.get(semesterIndex).getStartDate().getDate()).get(Calendar.YEAR);
		
		int day = stm.giveDay(i);
		int hour = stm.giveHourInDay(i);
		int blockLength = block.getHours();
		GregorianCalendar start = new GregorianCalendar(startYear,startMonth,startWeek,day,hour);
		start.add(Calendar.WEEK_OF_YEAR,week);
		GregorianCalendar end = new GregorianCalendar(startYear,startMonth,startWeek,day,hour+blockLength);
		end.add(Calendar.WEEK_OF_YEAR,week);
		
		if(holidayCalendar.overlaps(start, end))
		{
			b = false;
			return b;
		}
		
		Vector<Educator> educators = block.getSubcourse().getEducators();
		
		for(Educator e: educators)
		{
			IcsCalendar educatorCalendar = e.getCalendar();
			if(educatorCalendar.overlaps(start,end))
			{
				b = false;
				return b;
			}
		}
		
		return b;
	}
	
}

package scheduleGenerator;

import java.util.Hashtable;
import java.util.Vector;

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
		
		Educator educator = block.getSubcourse().getCourse().getEducator();
		Vector<Program> programs = block.getSubcourse().getCourse().getPrograms();
		
		for(int h = 0;h < block.getHours(); h++)
		{
			if(unavailableHoursForEducator.get(educator).contains(hour+h))
			{
				b = false;
			}
			
			for(Program p:programs)
			{
				if(unavailableHoursForProgram.get(p).contains(hour+h))
				{
					b = false;
				}
			}
		
		}
		
		return b;
	}
	
	
}

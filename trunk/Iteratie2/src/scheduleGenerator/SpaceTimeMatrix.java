package scheduleGenerator;

/**
 * 
 * @author matthiascaenepeel
 * @version0.1
 */

public class SpaceTimeMatrix 
{
	private Boolean[] matrix;
	private int startingHour;
	private int endingHour;
	private int numberOfRooms;
	private int numberOfDays;
	private int sizeOfList;

	public SpaceTimeMatrix(int startingHour, int endingHour,
			int numberOfRooms, int numberOfDays) 
	{
		super();
		this.startingHour = startingHour;
		this.endingHour = endingHour;
		this.numberOfRooms = numberOfRooms;
		this.numberOfDays = numberOfDays;

		this.sizeOfList = (endingHour - startingHour) * numberOfRooms
				* numberOfDays;

		this.matrix = new Boolean[sizeOfList];

		for (int i = 0; i < sizeOfList; i++) 
		{
			matrix[i] = true;
		}
	}

	// Het aantal mogelijkheden loopt van 1 tot sizeOfList (dus niet vanaf index
	// 0).
	// Als de plek op true staat betekent het dat de plek vrij is in het
	// rooster.
	public void initializeList() 
	{
		int sizeOfList = (endingHour - startingHour) * numberOfRooms
				* numberOfDays;
		Boolean b = true;

		for (int i = 1; i == sizeOfList; i++) 
		{
			matrix[i] = b;
		}
	}

	public boolean checkAt(int i) 
	{
		if(isWithInRange(i))
		{
			return matrix[i];
		}
		
		return false;
	}

	public void changeAt(int i, Boolean b) 
	{
		if(isWithInRange(i))
		{
			matrix[i] = b;
		}
	}
	
	public void changeAtBlock(int i, Boolean b, int blocksize)
	{
		for(int j=0;j<=blocksize;j++)
			{
				matrix[i+j*numberOfRooms] = b;
			}
		
	}
	
	public boolean checkAtBlock(int i, int blocksize)
	{
		for(int j=0;j<=blocksize;j++)
		{
			if(((i+j*numberOfRooms)>=sizeOfList))
			{
				return false;
			}
			else
			{
				if(!(matrix[i+j*numberOfRooms]))
				{
					return false;
				}
			}
		}
		
		return true;
	}

	public int getSize() 
	{
		return matrix.length;
	}
	
	public boolean isWithInRange(int i)
	{
		return !((i<=0)||(i> matrix.length));
	}
	
	public int giveHour(int i)
	{
		int hour = (i-(i%numberOfRooms))/numberOfRooms;
		return hour;
	}
	
	public int giveRoom(int i)
	{
		int room = (i%numberOfRooms);
		return room;
	}
	
	public int giveHourInDay(int i)
	{
		int hour = giveHour(i);
		int hourInDay = startingHour+(hour%(endingHour-startingHour));
		return hourInDay;
	}
	
	public int giveDay(int i)
	{
		int hour = giveHour(i);
		int day = ((hour)-(hour%(endingHour-startingHour)))/(endingHour-startingHour);
		return day;
	}

	public int getStartingHour() 
	{
		return startingHour;
	}

	public int getEndingHour() 
	{
		return endingHour;
	}

	public int getNumberOfRooms() 
	{
		return numberOfRooms;
	}

	public int getNumberOfDays() {
		return numberOfDays;
	}
	
	public static void main(String[] args)
	{
		int startingHour=8;
		int endingHour=18;
		int numberOfRooms=5;
		int numberOfDays=5;
		SpaceTimeMatrix stm =  new SpaceTimeMatrix(startingHour,endingHour,numberOfRooms, numberOfDays);
		
		System.out.println("Test Vertaler");
		for (int i = 0; i < (endingHour - startingHour) * numberOfRooms* numberOfDays; i++) 
		{
			int j = stm.giveDay(i);
			System.out.println(j);
		}
		
	}
}

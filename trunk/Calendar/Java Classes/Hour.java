package calendar;

/**
 * Class for specifying hours or durations (eg 8h30)
 * @author Zjef
 * @version 1.0
 */
public class Hour
{
	private int hour;
	private int minutes;
	
	/**
	 * @param hour - 0 to 23
	 */
	public Hour(int hour)
	{
		this.hour=hour;
		this.minutes=0;
	}
	
	/**
	 * @param hour - 0 to 23
	 * @param minutes - 0 to 59
	 */
	public Hour(int hour,int minutes)
	{
		this.hour=hour;
		this.minutes=minutes;
	}
	
	public int getHour()
	{
		return hour;
	}
	
	public int getMinutes()
	{
		return minutes;
	}
}
package calendar;

import java.util.Calendar;

public class UnavailableEvent extends SGEvent
{
	private final static String descr="Unavailable";
	
	public UnavailableEvent(Calendar start, Calendar end)
	{
		super(descr, start, end);
	}
}

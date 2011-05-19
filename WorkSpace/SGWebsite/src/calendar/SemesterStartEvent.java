package calendar;

import java.util.Calendar;

public class SemesterStartEvent extends SGEvent
{
	private final static String descr="SemesterStart";
	
	public SemesterStartEvent(Calendar start, Calendar end)
	{
		super(descr, start, end);
	}
}

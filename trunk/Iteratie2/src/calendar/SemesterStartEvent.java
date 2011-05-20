package calendar;

import java.util.Calendar;

public class SemesterStartEvent extends SGEvent
{
	public final static String descr="SemesterStart";
	
	public SemesterStartEvent(Calendar start, Calendar end)
	{
		super(descr, start, end);
	}
}

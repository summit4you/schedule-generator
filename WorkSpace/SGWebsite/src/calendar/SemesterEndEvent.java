package calendar;

import java.util.Calendar;

import net.fortuna.ical4j.model.component.VEvent;

public class SemesterEndEvent extends SGEvent
{
	private static final String descr="SemesterEnd";
	
	public SemesterEndEvent(Calendar start, Calendar end)
	{
		super(descr, start, end);
	}
}

package swingExtensions;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public abstract class ResetValueField extends RestrictedTextField
{
	public ResetValueField()
	{
		super(RestrictedTextField.RESTRICTION_INT,-1);
		addFocusListener(new FocusListener()
		{
			@Override
			public void focusLost(FocusEvent e)
			{
				setText(resetValue());
			}
			@Override
			public void focusGained(FocusEvent arg0)
			{
			}
		});
	}
	
	public abstract String resetValue();
}
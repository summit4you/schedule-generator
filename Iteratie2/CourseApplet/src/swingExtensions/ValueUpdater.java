package swingExtensions;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JList;
import javax.swing.JTextField;

import applet.Edit;
import applet.EditVector;
import applet.Wrapper;

/**
 * @author Zjef
 * @version 1.0
 */
public abstract class ValueUpdater<T> implements KeyListener
{
	private EditVector editVector;
	private JTextField field;
	
	public ValueUpdater(EditVector editVector,JTextField list)
	{
		super();
		this.editVector=editVector;
		this.field=list;
	}
	
	public abstract T doStuff(String value);
	
	@Override
	public void keyPressed(KeyEvent e)
	{
	}

	@Override
	public void keyReleased(KeyEvent e)
	{
		if ((!e.isConsumed())||(e.getKeyChar()==KeyEvent.VK_BACK_SPACE)||(e.getKeyChar()==KeyEvent.VK_DELETE))
		{
			if (!field.getText().equals(""))
			{
				editVector.add(new Wrapper(doStuff(field.getText()),Edit.edited));
			}
		}
	}

	@Override
	public void keyTyped(KeyEvent e)
	{
	}
}
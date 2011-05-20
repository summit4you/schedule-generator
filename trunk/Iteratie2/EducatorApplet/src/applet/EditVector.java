package applet;

import java.io.Serializable;
import java.util.Vector;

/**
 * @author Zjef
 * @version 1.0
 */
public class EditVector extends Vector<Wrapper<?>> implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	public EditVector()
	{
		super();
	}
	
	@Override
	public synchronized boolean add(Wrapper<?> e)
	{
		int index;
		if ((index=this.indexOf(e))==-1)
		{
			return super.add(e);
		}
		else
		{
			if (e.getEdit()==Edit.deleted && get(index).getEdit()==Edit.added)
			{
				remove(e);
				return true;
			}
			if (get(index).getEdit().getPrecedence()<e.getEdit().getPrecedence())
			{
				remove(e);
				return super.add(e);
			}
		}
		return false;
	}
}
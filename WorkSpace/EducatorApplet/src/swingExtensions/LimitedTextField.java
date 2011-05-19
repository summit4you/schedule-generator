package swingExtensions;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JTextField;

/**
 * @author Zjef
 * @version 1.0
 * Originally written for another application by Zjef
 */
public class LimitedTextField extends JTextField
{	
	private int limit;

	public LimitedTextField(int limit)
	{
		super();

		this.limit = limit;

		if (limit>=0)
		{
			addKeyListener(new KeyAdapter() {
				@Override
				public void keyTyped(KeyEvent evt) {
					if (LimitedTextField.this.getTextInEvent(evt.getKeyChar()).length() > LimitedTextField.this.limit)
						evt.consume();
				}
			});
		}
	}
	
	public void setLimit(int limit)
	{
		this.limit=limit;
	}
	
	public int getLimit()
	{
		return limit;
	}
	
	public String getTextInEvent(char keyTyped)
	{
		if (getHighlighter().getHighlights().length<=0) //no text is selected
		{
			return getText().substring(0,getCaretPosition())+keyTyped+getText().substring(getCaretPosition());
		}
		else //there are characters selected/highlighted
		{
			char[] text=new char[getText().length()+1+getHighlighter().getHighlights()[0].getStartOffset()-getHighlighter().getHighlights()[0].getEndOffset()];
			int i;
			for (i=0;i<getHighlighter().getHighlights()[0].getStartOffset();i++)//copy text before highlight
			{
				text[i]=getText().charAt(i);
			}
			text[i++]=keyTyped; //add the typed key
			for (int j=0;i<text.length;i++,j++)//copy the text from after the highlight
			{
				text[i]=getText().charAt(getHighlighter().getHighlights()[0].getEndOffset()+j);
			}
			return String.valueOf(text);
		}
	}
}
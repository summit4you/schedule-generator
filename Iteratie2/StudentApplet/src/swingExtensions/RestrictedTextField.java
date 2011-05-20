package swingExtensions;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * @author Zjef
 * @version 1.0
 * Originally written for another application by Zjef
 */
public class RestrictedTextField extends LimitedTextField
{	
	public final static int RESTRICTION_DOUBLE=0,RESTRICTION_INT=1,RESTRICTION_BYTE=2,RESTRICTION_COM=3;
	private char[] array;
	private int restriction;
	
	public RestrictedTextField(int restriction,int limit)
	{
		super(limit);
		
		this.restriction=restriction;
		array=(restriction==RESTRICTION_DOUBLE)?allowedDouble():((restriction==RESTRICTION_COM)?allowedCom():allowedInt());

		addKeyListener(new KeyAdapter() { 
			@Override
			public void keyTyped(KeyEvent evt) {

				for (int i=0;i<array.length;i++)
				{
					if (evt.getKeyChar()==array[i])
					{
						if (!checkRestrictions(RestrictedTextField.this.restriction,RestrictedTextField.this.getTextInEvent(evt.getKeyChar())))
						{
							evt.consume();
						}
						return;
					}
				}
				evt.consume();		
			}
		});	
	}
	
	private static boolean checkRestrictions(int restriction,String text)
	{
		if (restriction==RESTRICTION_BYTE)
		{
			return ((Integer.parseInt(text)>=0)&&(Integer.parseInt(text)<=255));
		}
		else if (restriction==RESTRICTION_COM)
		{
			boolean test=true;
			switch (text.length())
			{
			case 5:test=((text.charAt(4)>='0')&&(text.charAt(4)<='9')?test:false);
			case 4:test=((text.charAt(3)>='0')&&(text.charAt(3)<='9')?test:false);
			case 3:test=((text.charAt(2)=='M')?test:false);
			case 2:test=((text.charAt(1)=='O')?test:false);
			case 1:test=((text.charAt(0)=='C')?test:false);
			}
			return test;
		}
		else if (restriction==RESTRICTION_DOUBLE)
		{
			if ((text.lastIndexOf('-')>0)||((text.lastIndexOf('.')!=text.indexOf('.'))))
			{
				return false;
			}
		}
		
		return true;
	}
	
	private static char[] allowedDouble()
	{
		char[] chars={'1','2','3','4','5','6','7','8','9','0','.','-'};
		return chars;
	}
	private static char[] allowedInt()
	{
		char[] chars={'1','2','3','4','5','6','7','8','9','0'};
		return chars;
	}
	private static char[] allowedCom()
	{
		char[] chars={'C','O','M','1','2','3','4','5','6','7','8','9','0'};
		return chars;
	}	
}
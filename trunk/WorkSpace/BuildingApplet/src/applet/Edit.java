package applet;

import java.io.Serializable;

/**
 * @author Zjef
 * @version 1.0
 */
public enum Edit implements Serializable
{
	nothing(0),
	edited(1),
	added(2),
	deleted(3);
	
	private static final long serialVersionUID = 1L;
	
	private int precedence;
	
	private Edit(int precedence)
	{
		this.precedence=precedence;
	}
	
	public int getPrecedence()
	{
		return precedence;
	}
}

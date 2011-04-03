package HTMLBuilder;

import java.util.Vector;

public class HTMLFunctionVector extends Vector<HTMLFunction>
{
	
	public HTMLFunction getFunction(String arg0)
	{		
		for (HTMLFunction func:this)
		{
			if (func.getName().equals(arg0))
			{
				return func;
			}
		}
		return null;
	}

}

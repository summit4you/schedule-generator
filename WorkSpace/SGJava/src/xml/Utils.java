package xml;

import java.util.Vector;

/**
 * Just some methods
 * @author Zjef
 * @version 3.0
 */
class Utils
{
	protected static XMLElement getElement(String name,Vector<XMLElement> elements)
	{
		XMLElement res=null;
		Vector<XMLElement> els=elements;
		String local=name;
		do
		{
			if (name.charAt(0)=='.')
			{
				name=name.replaceFirst(".","");
			}
			if (res!=null)
			{
				if (res instanceof ElementWithChildren)
				{
					els=((ElementWithChildren)res).getElements();
				}
				else
				{
					return null;
				}
			}
			res=null;
			if (name.contains("."))
			{
				local=name.substring(0,name.indexOf('.'));
			}
			else
			{
				local=name;
			}
			
			for (XMLElement i:els)
			{
				if (i.getName().equals(local))
				{
					res=i;
					break;
				}
			}
			if (res==null)
			{
				return null;
			}
			name=name.replaceFirst(local,"");
		}while (name.contains("."));
		return res;
	}
}
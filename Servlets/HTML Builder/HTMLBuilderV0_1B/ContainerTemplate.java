package HTMLBuilder2;

import java.util.Vector;

import xml.ElementWithChildren;
import xml.ElementWithValue;
import xml.XMLElement;

/**
 * Generic child of template that can contain a vector of elements.</br>
 * The class of these elements has to be specified. 
 * @author Alexander
 * @param <T> Normally T=Template.
 */
public class ContainerTemplate<T> extends Template
{
	static protected final String top="Top";
	static protected final String bottom="Bottom";
	static protected final String name="Container";
	
	protected Vector<T> elements;
	
	
	public ContainerTemplate()
	{
		super();
		elements = new Vector<T>();
	}
	
	public ContainerTemplate(String fileName)
	{
		super(fileName);
		elements = new Vector<T>();
	}
	
	
	public void addTemplate(T temp)
	{
		elements.add(temp);
	}
	
	public void addTemplates(Vector<T> temps)
	{
		elements.addAll((temps));
	}
	
	public void removeTemplate(T temp)
	{
		elements.remove(temp);
	}
	
	public void removeTemplates(Vector<T> temps)
	{
		elements.removeAll(temps);
	}
	
	public void makeEmpty()
	{
		elements = new Vector<T>();
	}
	
	@Override
	protected void createFormat()
	{
		Vector<XMLElement> vec= new Vector<XMLElement>();
		vec.add(new ElementWithValue(top," "));
		vec.add(new ElementWithValue(bottom," "));
		ElementWithChildren parent = new ElementWithChildren(name);
		parent.addChildren(vec);
	}
}

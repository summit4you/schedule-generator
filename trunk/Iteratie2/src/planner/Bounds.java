package planner;

/**
 * 
 * @author matjasenalex
 * @version1.1
 */
public class Bounds<T> 
{
	private int lowerBound;
	private int upperBound;
	private T object;
	
	public Bounds(int l,int u,T o)
	{
		lowerBound=l;
		upperBound=u;
		setObject(o);
	}
	
	public void setUpperBound(int upperBound)
	{
		this.upperBound = upperBound;
	}
	public int getUpperBound()
	{
		return upperBound;
	}
	
	public void setLowerBound(int lowerBound)
	{
		this.lowerBound = lowerBound;
	}
	public int getLowerBound()
	{
		return lowerBound;
	}

	public void setObject(T object)
	{
		this.object = object;
	}

	public T getObject()
	{
		return object;
	}
	

}

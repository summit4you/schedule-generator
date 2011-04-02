package htmlInterfaces;

/**
 *<b>This is a class for testing and will be removed later on. </b></br>
 * @author Alexander
 * @version 0.1
 * @see HTMLTablable
 */
public class Dummy implements HTMLTablable
{
	private String name;
	private int age;
	
	Dummy(String n,int a)
	{
		setAge(a);
		setName(n);
	}
	
	@FormOutput
	public void setAge(int age)
	{
		this.age = age;
	}
	
	@TableInput(order=2,text= "leeftijd")	
	public int getAge()
	{
		return age;
	}
	
	@FormOutput
	public void setName(String name)
	{
		this.name =new String(name);
	}
	
	@TableInput(order=1,text= "leeftijd")
	public String getName()
	{
		return name;
	}
	
}

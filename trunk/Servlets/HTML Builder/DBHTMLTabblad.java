package HTMLBuilder;

import java.util.Vector;

import database.*;

public class DBHTMLTabblad implements Databasable
{
	private String name;
	private Vector<DBHTMLFunction> functions;
	
	
	
@Override
public ID getId()
{
	// TODO Auto-generated method stub
	return null;
}
@Override
	public void setID(ID id)
	{
		// TODO Auto-generated method stub
		
	}
public void setName(String name)
{
	this.name = name;
}
public String getName()
{
	return name;
}
public void setFunctions(Vector<DBHTMLFunction> functions)
{
	this.functions = functions;
}
public Vector<DBHTMLFunction> getFunctions()
{
	return functions;
}

}

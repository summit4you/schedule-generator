package login;

import java.util.Vector;

public class HTMLPage
{
	private String top;
	private String bottom;
	private Vector<HTMLTabblad> tabbladen;
	
	public HTMLPage(String standardtop, String standardbottom)
	{
		setTop(standardtop);
		setBottom(standardbottom);
	}
	
	public String toString()
	{
		String out = top;
		for (int i = 0; i < tabbladen.size(); i++)
		{
			out = out + tabbladen.get(i).toString();
		}
		out = out + bottom;
		return out;
	}
	
	public void addTabblad(HTMLTabblad tabblad)
	{
		tabbladen.add(tabblad);
	}
	
	public void delTabblad(HTMLTabblad tabblad)
	{
		tabbladen.remove(tabbladen.indexOf(tabblad));
	}
	
	public void editTabblad(HTMLTabblad tabblad)
	{
		tabbladen.set(tabbladen.indexOf(tabblad), tabblad);
	}
	
	public void setBottom(String bottom)
	{
		this.bottom = bottom;
	}

	public String getBottom()
	{
		return bottom;
	}

	public void setTop(String top)
	{
		this.top = top;
	}

	public String getTop()
	{
		return top;
	}

	public void setTabbladen(Vector<HTMLTabblad> tabbladen)
	{
		this.tabbladen = tabbladen;
	}

	public Vector<HTMLTabblad> getTabbladen()
	{
		return tabbladen;
	}

	
	
}

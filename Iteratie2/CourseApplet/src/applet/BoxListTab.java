package applet;

import javax.swing.JComponent;
import javax.swing.JPanel;

public class BoxListTab extends JPanel
{
	public BoxListTab()
	{
		super(null);
	}
	
	public void addBoxAndList(JComponent... components)
	{
		for (JComponent i:components)
		{
			add(i);
		}
	}
}
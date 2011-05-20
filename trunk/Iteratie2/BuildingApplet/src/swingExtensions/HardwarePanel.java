package swingExtensions;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import applet.Edit;
import applet.Wrapper;
import applet.EditVector;
import dataStructure.Hardware;
import dataStructure.Room;

/**
 * @author Zjef
 * @version 1.0
 */
public class HardwarePanel extends JPanel
{
	private String[] hardwares;
	private JCheckBox[] selections;
	private Room room;
	private ActionListener listener;
	private EditVector editVector;
	private int maxHeight;
	
	public HardwarePanel(String[] hardwares,EditVector editVector)
	{
		super(null);
		this.hardwares=hardwares;
		this.editVector=editVector;
		initPanel();
	}
	
	public void initPanel()
	{
		selections=new JCheckBox[hardwares.length];
		
		for (int i=0;i<hardwares.length;i++)
		{
			selections[i]=new JCheckBox();
			selections[i].setBounds(5,i==0?5:selections[i-1].getY()+selections[i-1].getHeight()+10,30,30);
			selections[i].addActionListener(getListener());
			JLabel label=new JLabel(hardwares[i]);
			label.setBounds(selections[i].getX()+selections[i].getWidth()+5,selections[i].getY(),900,25);
			
			add(selections[i]);
			add(label);
			maxHeight=selections[i].getY()+30;
		}
	}
	
	@Override
	public Dimension getPreferredSize()
	{
		return new Dimension(300,maxHeight);
	}
	
	private ActionListener getListener()
	{
		if (listener==null)
		{
			listener=new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent e)
				{
					room.setPresentHardware(getSelection());
					editVector.add(new Wrapper<Room>(room,Edit.edited));
				}
			};
		}
		return listener;
	}
	
	public Vector<Hardware> getSelection()
	{
		Vector<Hardware> res=new Vector<Hardware>();
		for (int i=0;i<selections.length;i++)
		{
			if (selections[i].isSelected())
			{
				res.add(new Hardware(hardwares[i]));
			}
		}
		return res;
	}
	
	public void setRoom(Room room)
	{
		this.room=room;
		setSelections(room.getPresentHardware());
	}
	
	private void setSelections(Vector<Hardware> hardware)
	{
		for (JCheckBox c:selections)
		{
			c.setSelected(false);
		}
		for (Hardware h:hardware)
		{
			for (int i=0;i<hardwares.length;i++)
			{
				if (h.toValue().equals(hardwares[i]))
				{
					selections[i].setSelected(true);
					break;
				}
			}
		}
	}
}
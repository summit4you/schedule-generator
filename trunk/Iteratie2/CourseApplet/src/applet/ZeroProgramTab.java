package applet;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import dataStructure.Program;

public class ZeroProgramTab extends JPanel
{
	private Program zero;
	private ListManager m;
	
	private JList cList;
	private JScrollPane cPane;
	private JButton delete;
	
	public ZeroProgramTab(Program zero,ListManager m)
	{
		super(null);
		this.zero=zero;
		this.m=m;
		initPanel();
	}
	
	private void initPanel()
	{
		JLabel label=new JLabel("<html>This is the list of courses that are not part of a program<br>If you delete a course from this list, it will be deleted permanently along with its subcourses");
		label.setBounds(5,5,1000,25);
		add(label);
		cList=new JList(zero.getCourses());
		cList.addListSelectionListener(new ListSelectionListener()
		{
			@Override
			public void valueChanged(ListSelectionEvent e)
			{
				delete.setEnabled(cList.getSelectedValue()!=null);
			}
		});
		cPane=new JScrollPane(cList);
		cPane.setBounds(label.getX(),label.getY()+50,200,300);
		add(cPane);
		
		delete=new JButton("delete");
		delete.setBounds(cPane.getX()+cPane.getWidth()+5,cPane.getY(),120,25);
		delete.setEnabled(false);
		delete.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				if (ButtonPanel.confirmDelete("the selected courses permanently"))
				{
					m.removeItems(cList.getSelectedValues());
					cList.setListData(zero.getCourses());
				}
			}
		});
		add(delete);
	}
	
	@Override
	public void repaint()
	{
		super.repaint();
		if (cList!=null)
		{
			cList.setListData(zero.getCourses());
		}
	}
}
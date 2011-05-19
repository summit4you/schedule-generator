package applet;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import dataStructure.Faculty;
import dataStructure.Program;

public class ProgramSelector extends JPanel
{
	private JComboBox facultyBox;
	private JComboBox programBox;
	private Vector<Faculty> faculties;
	
	public ProgramSelector(Vector<Faculty> faculties)
	{
		super(null);
		this.faculties=faculties;
		initPanel();
	}
	
	public void setSelection(Faculty f,Program p)
	{
		facultyBox.setSelectedItem(f);
		programBox.setSelectedItem(p);
	}
	
	public void addProgramListener(ActionListener listener)
	{
		programBox.addActionListener(listener);
	}
	
	public Program getProgram()
	{
		return (Program)programBox.getSelectedItem();
	}
	
	@Override
	public Dimension getPreferredSize()
	{
		return new Dimension(400,100);
	}
	
	private void initPanel()
	{
		JLabel flabel=new JLabel("Faculties");
		flabel.setBounds(5,5,1000,25);
		add(flabel);
		facultyBox=new JComboBox(faculties);
		facultyBox.setBounds(flabel.getX(),flabel.getY()+30,150,25);
		facultyBox.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				Faculty f=(Faculty) facultyBox.getSelectedItem();
				if (f!=null)
				{
					programBox.removeAllItems();
					for (Program p:f.getPrograms())
					{
						programBox.addItem(p);
					}
				}
			}
		});
		add(facultyBox);
		
		JLabel plabel=new JLabel("Programs");
		plabel.setBounds(facultyBox.getX()+facultyBox.getWidth()+20,flabel.getY(),1000,flabel.getHeight());
		add(plabel);
		programBox=new JComboBox();
		programBox.setBounds(plabel.getX(),plabel.getY()+30,facultyBox.getWidth(),facultyBox.getHeight());
		add(programBox);
	}
}
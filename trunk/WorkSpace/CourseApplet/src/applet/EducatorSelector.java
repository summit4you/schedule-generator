package applet;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import dataStructure.Educator;
import dataStructure.Faculty;
import dataStructure.Program;

/**
 * @author Zjef
 * @version 1.0
 */
public class EducatorSelector extends JPanel
{
	private Vector<Faculty> facs;
	
	private JComboBox fBox;
	private JComboBox eBox;
	
	public EducatorSelector(Vector<Faculty> facs)
	{
		super(null);
		this.facs=facs;
		initPanel();
	}
	
	@Override
	public Dimension getPreferredSize()
	{
		return new Dimension(400,60);
	}
	
	public void setSelection(Faculty f,Educator e)
	{
		fBox.setSelectedItem(f);
		if (e!=null)
		{
			eBox.setSelectedItem(e);
		}
	}
	
	public Educator getEducator()
	{
		return (Educator)eBox.getSelectedItem();
	}
	
	private void initPanel()
	{
		JLabel flabel=new JLabel("Faculties");
		flabel.setBounds(5,5,1000,25);
		add(flabel);
		fBox=new JComboBox(facs);
		fBox.setBounds(flabel.getX(),flabel.getY()+30,150,25);
		fBox.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				Faculty f=(Faculty) fBox.getSelectedItem();
				if (f!=null)
				{
					eBox.setModel(new DefaultComboBoxModel(f.getEducators()));
				}
			}
		});
		add(fBox);
		
		JLabel plabel=new JLabel("Educators");
		plabel.setBounds(fBox.getX()+fBox.getWidth()+20,flabel.getY(),1000,flabel.getHeight());
		add(plabel);
		eBox=new JComboBox();
		eBox.setBounds(plabel.getX(),plabel.getY()+30,fBox.getWidth(),fBox.getHeight());
		add(eBox);
	}
}
package applet;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import dataStructure.Educator;
import dataStructure.Subcourse;
import swingExtensions.ResetValueField;
import swingExtensions.ValueUpdater;

public class SubcoursePanel extends JPanel
{
	private ListManager m;
	private Subcourse subcourse;
	
	private JList eList;
	private JScrollPane eScroll;
	private JButton add;
	private JButton remove;
	private ResetValueField hWeek;
	private ResetValueField bWeek;
	private ResetValueField bHours;
	private ResetValueField tHours;
	private JSpinner sem;
	
	public SubcoursePanel(ListManager m)
	{
		super(null);
		this.m=m;
		initPanel();
	}
	
	public void setSubcourse(Subcourse s)
	{
		this.subcourse=s;
		if (s!=null)
		{
			tHours.setText(Integer.toString(s.getTotalnumberHours()));
			hWeek.setText(Integer.toString(s.getHoursPerWeek()));
			bHours.setText(Integer.toString(s.getBlockHours()));
			sem.setValue(s.getSemester());
			bWeek.setText(Integer.toString(s.getBeginWeek()));
			eList.setListData(s.getEducators());
		}
	}
	
	private void initPanel()
	{
		JLabel tl=new JLabel("Total Hours");
		tl.setBounds(5,5,1000,25);
		add(tl);
		tHours=new ResetValueField(){
			@Override
			public String resetValue()
			{
				return Integer.toString(subcourse.getTotalnumberHours());
			}	
		};
		tHours.setBounds(tl.getX()+70,tl.getY(),40,25);
		tHours.addKeyListener(new ValueUpdater<Subcourse>(m.getEditVector(),tHours)
		{
			@Override
			public Subcourse doStuff(String value)
			{
				subcourse.setTotalnumberHours(Integer.parseInt(value));
				return subcourse;
			}
		});
		add(tHours);
		
		JLabel hl=new JLabel("Hours/Week");
		hl.setBounds(tHours.getX()+tHours.getWidth()+15,tHours.getY(),1000,25);
		add(hl);
		hWeek=new ResetValueField(){
			@Override
			public String resetValue()
			{
				return Integer.toString(subcourse.getHoursPerWeek());
			}	
		};
		hWeek.setBounds(hl.getX()+70,hl.getY(),40,25);
		hWeek.addKeyListener(new ValueUpdater<Subcourse>(m.getEditVector(),hWeek)
				{
					@Override
					public Subcourse doStuff(String value)
					{
						subcourse.setHoursPerWeek(Integer.parseInt(value));
						return subcourse;
					}
				});
		add(hWeek);
		
		JLabel bl=new JLabel("Hours/Block");
		bl.setBounds(tl.getX(),tl.getY()+30,1000,25);
		add(bl);
		bHours=new ResetValueField(){
			@Override
			public String resetValue()
			{
				return Integer.toString(subcourse.getBlockHours());
			}
		};
		bHours.setBounds(bl.getX()+70,bl.getY(),40,25);
		bHours.addKeyListener(new ValueUpdater<Subcourse>(m.getEditVector(),bHours)
				{
					@Override
					public Subcourse doStuff(String value)
					{
						subcourse.setHoursPerWeek(Integer.parseInt(value));
						return subcourse;
					}
				});
		add(bHours);
		
		JLabel sl=new JLabel("semester");
		sl.setBounds(bl.getX(),bl.getY()+30,1000,25);
		add(sl);
		sem=new JSpinner(new SpinnerNumberModel(1,1,2,1));
		sem.setBounds(sl.getX()+70,sl.getY(),40,25);
		sem.addChangeListener(new ChangeListener()
		{
			@Override
			public void stateChanged(ChangeEvent arg0)
			{
				subcourse.setSemester((Integer) sem.getValue());
				m.getEditVector().add(new Wrapper<Subcourse>(subcourse,Edit.edited));
			}
		});
		add(sem);
		
		JLabel wl=new JLabel("Start week");
		wl.setBounds(hl.getX(),sem.getY(),1000,25);
		add(wl);
		bWeek=new ResetValueField(){
			@Override
			public String resetValue()
			{
				return Integer.toString(subcourse.getBeginWeek());
			}	
		};
		bWeek.setBounds(wl.getX()+70,sl.getY(),bHours.getWidth(),bHours.getHeight());
		bWeek.addKeyListener(new ValueUpdater<Subcourse>(m.getEditVector(),bWeek)
				{
					@Override
					public Subcourse doStuff(String value)
					{
						subcourse.setBeginWeek(Integer.parseInt(value));
						return subcourse;
					}
				});
		add(bWeek);
		
		JLabel el=new JLabel("educators");
		el.setBounds(sl.getX(),sl.getY()+35,200,25);
		add(el);
		eList=new JList();
		eScroll=new JScrollPane(eList);
		eScroll.setBounds(el.getX(),el.getY()+35,150,200);
		add(eScroll);
		
		add=new JButton("add");
		add.setBounds(eScroll.getX()+eScroll.getWidth()+5,eScroll.getY(),120,25);
		add.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				EducatorSelector sel=new EducatorSelector(m.getFaculties());
				sel.setSelection(m.getFaculty(),null);
				if (JOptionPane.showConfirmDialog(null,sel,"Select Educator",JOptionPane.OK_CANCEL_OPTION)==JOptionPane.OK_OPTION)
				{
					Educator e=sel.getEducator();
					subcourse.addEducator(e);
					m.getEditVector().add(new Wrapper<Educator>(e,Edit.edited));
					m.getEditVector().add(new Wrapper<Subcourse>(subcourse,Edit.edited));
					eList.setListData(subcourse.getEducators());
				}
			}
		});
		add(add);
	
		remove=new JButton("remove");
		remove.setBounds(add.getX(),add.getY()+30,add.getWidth(),add.getHeight());
		remove.setEnabled(false);
		remove.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent ev)
			{
				if (ButtonPanel.confirmDelete("the selected educators"))
				{
					Object[] sel=eList.getSelectedValues();
					for (Object i:sel)
					{
						subcourse.removeEducator((Educator) i);
						m.getEditVector().add(new Wrapper<Educator>((Educator) i,Edit.edited));
						m.getEditVector().add(new Wrapper<Subcourse>(subcourse,Edit.edited));
						eList.setListData(subcourse.getEducators());
					}
				}
			}
		});
		add(remove);
	}
}
package applet;

import java.awt.Dimension;
import java.util.Vector;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;

import dataStructure.Program;
import dataStructure.Student;

/**
 * @author Zjef
 * @version 1.0
 */
public class AddPanel extends JPanel
{
	final private static String separator="	"; //tab
	private JTextArea pane;
	private JScrollPane scroll;
	private Vector<Student> students;
	private JLabel errorLabel;
	private Program program;
	
	public AddPanel(Program program)
	{
		super(null);
		this.program=program;
		students=new Vector<Student>();
		initPanel();
	}
	
	private void initPanel()
	{
		JLabel label=new JLabel("<html>Enter the new accounts below<br>Format: &lt;surname&gt;&lt;tab&gt;&lt;first name&gt;"+"&lt;tab&gt;&lt;student number&gt;");
		label.setBounds(5,5,1000,25);
		add(label);
		
		errorLabel=new JLabel("Input is not valid");
		errorLabel.setBounds(label.getX(),label.getY()+30,label.getWidth(),label.getHeight());
		errorLabel.setVisible(false);
		add(errorLabel);
		
		pane=new JTextArea();
		pane.setTabSize(3);
		
		scroll=new JScrollPane(pane);
		scroll.setBounds(errorLabel.getX(),errorLabel.getY()+35,300,220);
		add(scroll);
	}
	
	@Override
	public Dimension getPreferredSize()
	{
		return new Dimension(500,280);
	}
	
	public Student createStudent(String string) throws Exception
	{
		String[] inputs=string.split(separator);
		if (inputs.length!=3)
		{
			throw new Exception("invalid input");
		}
		
		Student newOne=new Student(Integer.parseInt(inputs[2]),inputs[1],inputs[0]);
		newOne.addProgram(program);
		return newOne;
	}
	
	public boolean parseInput()
	{
		students.removeAllElements();
		try
		{
			String text=pane.getText();
			String[] lines=text.replaceAll("\r","").split("\n");
			for (String i:lines)
			{
				students.add(createStudent(i));
			}
			return true;
		}
		catch(Exception e)
		{
			students.removeAllElements();
			errorLabel.setVisible(true);
			return false;
		}
	}
	
	public boolean showInput()
	{
		int result;
		do
		{
			result=JOptionPane.showConfirmDialog(null,this,"Add Students",JOptionPane.OK_CANCEL_OPTION);
		}while(result==JOptionPane.OK_OPTION && !parseInput());
		return JOptionPane.OK_OPTION==result;
	}
	
	public Vector<Student> getStudents()
	{
		return students;
	}
}
package applet;

import java.awt.Dimension;
import java.util.Vector;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import login.Account;
import login.UserType;

/**
 * @author Zjef
 * @version 1.0
 */
public class AddPanel extends JPanel
{
	final private static String separator="	"; //tab
	private JTextArea pane;
	private JScrollPane scroll;
	private Vector<Account> accounts;
	private JLabel errorLabel;
	private String userType;
	private boolean admins;
	
	public AddPanel(String userType,boolean admins)
	{
		super(null);
		this.admins=admins;
		this.userType=userType;
		accounts=new Vector<Account>();
		initPanel();
	}
	
	private void initPanel()
	{
		JLabel label=new JLabel("<html>Enter the new accounts below<br>Format: &lt;accountname&gt;&lt;tab&gt;&lt;password&gt;"+(admins?"":"&lt;tab&gt;&lt;student/educator number&gt;"));
		label.setBounds(5,5,1000,25);
		add(label);
		
		errorLabel=new JLabel("Input is not valid");
		errorLabel.setBounds(label.getX(),label.getY()+30,label.getWidth(),label.getHeight());
		errorLabel.setVisible(false);
		add(errorLabel);
		
		pane=new JTextArea();
		pane.setTabSize(3);
		
		scroll=new JScrollPane(pane);
		scroll.setBounds(errorLabel.getX(),errorLabel.getY()+35,300,220);;
		add(scroll);
	}
	
	@Override
	public Dimension getPreferredSize()
	{
		return new Dimension(500,280);
	}
	
	public Account createAccount(String string) throws Exception
	{
		String[] inputs=string.split(separator);
		if (inputs.length!=(admins?2:3))
		{
			throw new Exception("invalid input");
		}
		
		Account newOne=new Account(inputs[0],inputs[1],new UserType(userType));
		if (!admins)
		{
			newOne.setAccountNumber(Integer.parseInt(inputs[2]));
		}
		return newOne;
	}
	
	public boolean parseInput()
	{
		accounts.removeAllElements();
		try
		{
			String text=pane.getText();
			String[] lines=text.replaceAll("\r","").split("\n");
			for (String i:lines)
			{
				accounts.add(createAccount(i));
			}
			return true;
		}
		catch(Exception e)
		{
			accounts.removeAllElements();
			errorLabel.setVisible(true);
			return false;
		}
	}
	
	public boolean showInput()
	{
		int result;
		do
		{
			result=JOptionPane.showConfirmDialog(null,this,"Add Accounts",JOptionPane.OK_CANCEL_OPTION);
		}while(result==JOptionPane.OK_OPTION && !parseInput());
		return JOptionPane.OK_OPTION==result;
	}
	
	public Vector<Account> getAccounts()
	{
		return accounts;
	}
}
package applet;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import swingExtensions.RestrictedTextField;
import login.Account;

/**
 * @author Zjef
 * @version 1.0
 */
public class AccountDetailPanel extends JPanel
{
	private EditVector editVector;
	private Account account;
	private boolean admins;
	
	private JTextField password;
	private RestrictedTextField accountNumber;
	
	public AccountDetailPanel(EditVector editVector,boolean admins)
	{
		super(null);
		this.admins=admins;
		this.editVector=editVector;
		initPanel();
	}
	
	private void initPanel()
	{
		JLabel pwLabel=new JLabel("password");
		pwLabel.setBounds(5,5,1000,25);
		add(pwLabel);
		password=new JTextField();
	
		password.setBounds(pwLabel.getX()+160,pwLabel.getY(),200,25);
		password.addKeyListener(new Listener(){
			@Override
			protected void doStuff()
			{
				account.setPassword(password.getText());
			}
		});
		add(password);
		
		JLabel anLabel=new JLabel("Educator/Student Number");
		anLabel.setBounds(pwLabel.getX(),pwLabel.getY()+35,pwLabel.getWidth(),pwLabel.getHeight());
		add(anLabel);
		accountNumber=new RestrictedTextField(RestrictedTextField.RESTRICTION_INT,-1);
		accountNumber.setBounds(password.getX(),anLabel.getY(),password.getWidth(),password.getHeight());
		accountNumber.setEnabled(!admins);
		accountNumber.addKeyListener(new Listener(){
			@Override
			protected void doStuff()
			{
				account.setAccountNumber(Integer.parseInt(accountNumber.getText()));	
			}
		});
		add(accountNumber);
	}
	
	public void setAccount(Account account)
	{
		this.account=account;
		password.setText(account.getPassword());
		accountNumber.setText(Integer.toString(account.getAccountNumber()));
	}
	
	private abstract class Listener implements KeyListener
	{
		public Listener()
		{
			super();
		}
		
		protected abstract void doStuff();
		
		@Override
		public void keyPressed(KeyEvent e)
		{
		}
		@Override
		public void keyReleased(KeyEvent e)
		{	
			if ((!e.isConsumed())||(e.getKeyChar()==KeyEvent.VK_BACK_SPACE)||(e.getKeyChar()==KeyEvent.VK_DELETE))
			{
				doStuff();
				editVector.add(new Wrapper<Account>(account,Edit.edited));
			}
		}
		@Override
		public void keyTyped(KeyEvent e)
		{
		}
	}
}
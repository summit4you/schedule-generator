package applet;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Vector;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import login.Account;

/**
 * @author Zjef
 * @version 1.0
 */
public class AccountPanel extends JPanel
{
	private String url;
	private URL codeBase;
	private String userType;
	private boolean loaded;
	private Vector<Account> accounts;
	private boolean admins;
	
	private JList accountList;
	private JScrollPane accountScroll;
	private JButton deleteAccount;
	private JButton addAccounts;
	private JButton renameAccount;
	private JButton save;
	private EditVector editVector;
	private AccountDetailPanel accountDetails;
	
	public AccountPanel(String userType,String url,URL codeBase,boolean admins)
	{
		super(null);
		this.admins=admins;
		this.url=url;
		this.codeBase=codeBase;
		editVector=new EditVector();
		loaded=false;
		this.userType=userType;
		initPanel();
	}
	
	private void initPanel()
	{
		accountList=new JList();
		accountList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		accountList.addListSelectionListener(new ListSelectionListener()
		{
			@Override
			public void valueChanged(ListSelectionEvent e)
			{
				int[] selected=accountList.getSelectedIndices();
				if (selected.length==0)
				{
					deleteAccount.setEnabled(false);
					renameAccount.setEnabled(false);
					accountDetails.setVisible(false);
				}
				else 
				{
					deleteAccount.setEnabled(true);
					if(selected.length==1)
					{
						Account a=(Account) accountList.getSelectedValue();
						renameAccount.setEnabled(true);
						accountDetails.setAccount(a);
						accountDetails.setVisible(true);
					}
					else
					{
						renameAccount.setEnabled(false);
						accountDetails.setVisible(false);
					}
				}
			}
		});
		accountScroll=new JScrollPane(accountList);
		accountScroll.setBounds(5,120,200,200);
		add(accountScroll);
		
		addAccounts=new JButton("Add");
		addAccounts.setBounds(accountScroll.getX(),accountScroll.getY()-90,accountScroll.getWidth(),25);
		addAccounts.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				AddPanel addPanel=new AddPanel(userType,admins);
				if (addPanel.showInput())
				{
					Vector<Account> accs=addPanel.getAccounts();
					for (Account i:accs)
					{
						accounts.add(i);
						editVector.add(new Wrapper<Account>(i,Edit.added));
					}
					accountList.setListData(accounts);
				}
			}
		});
		add(addAccounts);
		
		deleteAccount=new JButton("delete");
		deleteAccount.setBounds(addAccounts.getX(),addAccounts.getY()+30,addAccounts.getWidth(),addAccounts.getHeight());
		deleteAccount.setEnabled(false);
		deleteAccount.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				if (confirmDelete("all selected accounts"))
				{
					Object[] selected=accountList.getSelectedValues();
					if (selected.length>0)
					{
						for (Object i:selected)
						{
							accounts.remove(i);
							editVector.add(new Wrapper<Account>((Account) i,Edit.deleted));
						}
						accountList.setListData(accounts);
					}
				}
			}
		});
		add(deleteAccount);
		
		renameAccount=new JButton("edit");
		renameAccount.setBounds(deleteAccount.getX(),deleteAccount.getY()+30,deleteAccount.getWidth(),deleteAccount.getHeight());
		renameAccount.setEnabled(false);
		renameAccount.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				Account a=(Account) accountList.getSelectedValue();
				String name;
				if (a!=null && (name=askName(a.getUserName()))!=null)
				{
					a.setUserName(name);
					editVector.add(new Wrapper<Account>(a,Edit.edited));
					accountList.setListData(accounts);
				}
			}
		});
		add(renameAccount);
		
		accountDetails=new AccountDetailPanel(editVector,admins);
		accountDetails.setBounds(accountScroll.getX()+accountScroll.getWidth(),accountScroll.getY(),800,accountScroll.getHeight());
		accountDetails.setVisible(false);
		add(accountDetails);
		
		save=new JButton("save changes");
		save.setBounds(accountScroll.getX(),accountScroll.getY()+accountScroll.getHeight()+20,120,25);
		save.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				sendData();
				editVector.removeAllElements();
			}
		});
		add(save);
	}
	
	private String askName(String name)
	{
		return JOptionPane.showInputDialog("Enter name",name==null?"":name);
	}
	
	private boolean confirmDelete(String name)
	{
		return JOptionPane.showConfirmDialog(null,"Are you sure you want to delete "+name+"?")==JOptionPane.OK_OPTION;
	}
	
	private void loadValues()
	{
		getAccounts();
		accountList.setListData(accounts);
	}
	
	public void notifyActiveTab()
	{
		if (!loaded)
		{
			loadValues();
		}
		loaded=true;
	}
	
	/**
	 * Get a connection to the servlet.
	 */
	private URLConnection getServletConnection(String url) throws MalformedURLException, IOException 
	{
		URL urlServlet = new URL(codeBase,url+"&applet=true");
		URLConnection con = urlServlet.openConnection();
		
		con.setDoInput(true);
		con.setDoOutput(true);
		con.setUseCaches(false);
		con.setRequestProperty("Content-Type","application/x-java-serialized-object");
		
		return con;
	}
	
	private Object getObjectsToWrite()
	{
		return editVector;
	}

	/**
	 * Send the inputField data to the servlet and show the result in the outputField.
	 */
	private void sendData() 
	{
		try 
		{
			URLConnection con = getServletConnection(url+"&save=true");
			OutputStream outstream = con.getOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(outstream);
			oos.writeObject(getObjectsToWrite());
			oos.flush();
			oos.close();
			
			InputStream instr = con.getInputStream();
			ObjectInputStream inputFromServlet = new ObjectInputStream(instr);
			String res=(String) inputFromServlet.readObject();
			inputFromServlet.close();
			instr.close();
			JOptionPane.showMessageDialog(null,res);
		} catch (Exception ex) 
		{
			ex.printStackTrace();
		}
	}
	
	private void getAccounts()
	{
		try 
		{
			URLConnection con = getServletConnection(url+"&accounts=true&type="+userType);
			OutputStream outstream = con.getOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(outstream);
			oos.writeObject("hello");
			oos.flush();
			oos.close();

			InputStream instr = con.getInputStream();
			ObjectInputStream inputFromServlet = new ObjectInputStream(instr);
			accounts =(Vector<Account>) inputFromServlet.readObject();
			inputFromServlet.close();
			instr.close();
		} catch (Exception ex) 
		{
			ex.printStackTrace();
		}
	}
}
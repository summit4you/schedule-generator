package applet;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * @author Zjef
 * @version 1.0
 */
public abstract class ButtonPanel<T> extends JPanel
{
	private JButton add;
	private JButton addFromList;
	private JButton remove;
	private JButton copy;
	private JButton rename;
	private ListManager m;
	private JList list;
	
	public ButtonPanel(JList list,ListManager m,Rectangle bounds,boolean addCopy,boolean addExisting)
	{
		super(null);
		this.m=m;
		this.list=list;
		initPanel(bounds,addCopy,addExisting);
	}
	
	public abstract Object createNewObject(String name);
	
	protected abstract void renameItem(T item,String name);
	
	private void initPanel(Rectangle bounds,boolean addCopy,boolean addExisting)
	{
		this.setBounds(bounds.x+bounds.width+5,bounds.y,130,400);
		Rectangle firstButtonBounds=new Rectangle(0,0,125,25);
		if (addExisting)
		{
			addFromList=new JButton("add from list");
			addFromList.setBounds(firstButtonBounds);
			addFromList.addActionListener(new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent arg0)
				{
					m.addItem(loadFromList());
				}
			});
			add(addFromList);
		}
		
		
		add=new JButton("create new");
		add.setBounds(firstButtonBounds.x,firstButtonBounds.y+30,firstButtonBounds.width,firstButtonBounds.height);
		add.addActionListener(new ActionListener()
		{
			
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				String name=askName(null);
				if (name!=null)
				{
					m.addItem(createNewObject(name));
				}
			}
		});
		add(add);
		
		remove=new JButton("remove");
		remove.setEnabled(false);
		remove.setBounds(add.getX(),add.getY()+30,add.getWidth(),add.getHeight());
		remove.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				if (confirmDelete("the selected items"))
				{
					m.removeItems(list.getSelectedValues());
				}	
			}
		});
		add(remove);
		
		rename=new JButton("rename");
		rename.setBounds(remove.getX(),remove.getY()+30,remove.getWidth(),remove.getHeight());
		rename.setEnabled(false);
		rename.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				Object sel=list.getSelectedValue();
				String name=askName(sel.toString());
				if (name!=null)
				{
					renameItem((T)sel,name);
					m.rename(list);
				}
			}
		});
		add(rename);
		
		if (addCopy)
		{
			copy=new JButton("copy");
			copy.setEnabled(false);
			copy.setBounds(rename.getX(),rename.getY()+30,rename.getWidth(),rename.getHeight());
			copy.addActionListener(new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent e)
				{
					Object sel=list.getSelectedValue();
					String name=askName(sel.toString());
					if (name!=null)
					{
						m.addItem(createCopy((T) list.getSelectedValue(),name));
					}
				}
			});
			add(copy);
		}
		
		list.addListSelectionListener(new ListSelectionListener()
		{
			@Override
			public void valueChanged(ListSelectionEvent e)
			{
				Object[] sel=list.getSelectedValues();
				remove.setEnabled(sel.length>=1);
				rename.setEnabled(sel.length==1);
				if (copy!=null)
				{
					copy.setEnabled(sel.length==1);
				}
			}
		});
	}
	
	protected T createCopy(T object,String name)
	{
		return null;
	}
	
	protected T loadFromList()
	{
		return null;
	}
	
	public JButton getAdd()
	{
		return add;
	}
	
	public JButton getRemove()
	{
		return remove;
	}
	
	public JButton getCopy()
	{
		return copy;
	}
	
	private String askName(String name)
	{
		return JOptionPane.showInputDialog("Enter name",name==null?"":name);
	}
	
	public static boolean confirmDelete(String name)
	{
		return JOptionPane.showConfirmDialog(null,"Are you sure you want to delete "+name+"?")==JOptionPane.OK_OPTION;
	}
}
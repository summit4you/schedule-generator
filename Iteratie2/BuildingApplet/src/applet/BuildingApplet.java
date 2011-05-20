package applet;

import java.applet.Applet;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.Vector;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import swingExtensions.RoomPanel;
import dataStructure.Building;
import dataStructure.Room;

/**
 * @author Zjef
 * @version 1.0
 */
public class BuildingApplet extends Applet
{
	private static String url;
	private static String[] hardwares;
	
	private Vector<Building> buildings;
	private EditVector editVector;
	
	private JList buildingList;
	private JScrollPane buildingPane;
	private JButton buildingRename;
	private JButton buildingAdd;
	private JButton buildingRemove;
	private JList roomList;
	private JScrollPane roomPane;
	private JButton roomRename;
	private JButton roomAdd;
	private JButton roomRemove;
	private JButton save;
	private RoomPanel roomPanel;
	
	@Override
	public void init() 
	{
        url=getParameter("url");
        hardwares=getParameter("hardware").split(",");
		getBuildings();

		editVector=new EditVector();
		try 
		{
            SwingUtilities.invokeAndWait(new Runnable() 
            {
            	@Override
                public void run() 
                {
                    createGUI();
                }
            });
        } catch (Exception e) 
        {
        	e.printStackTrace();
        }
	}
	
	private String askName(String name)
	{
		return JOptionPane.showInputDialog("Enter name",name==null?"":name);
	}
	
	private boolean confirmDelete(String name)
	{
		return JOptionPane.showConfirmDialog(null,"Are you sure you want to delete "+name+"?")==JOptionPane.OK_OPTION;
	}
	
	private void createGUI()
	{
		setLayout(null);
		
		buildingList=new JList();
		buildingList.setListData(buildings);
		buildingList.addListSelectionListener(new ListSelectionListener()
		{
			@Override
			public void valueChanged(ListSelectionEvent arg0)
			{
				Building b=(Building) buildingList.getSelectedValue();
				if (b==null)
				{
					roomList.setListData(new Vector<Object>(0));
					buildingRemove.setEnabled(false);buildingRename.setEnabled(false);roomAdd.setEnabled(false);
				}
				else
				{
					buildingRemove.setEnabled(true);buildingRename.setEnabled(true);roomAdd.setEnabled(true);
					roomList.setListData(b.getRooms());
				}
			}
		});
		buildingPane=new JScrollPane(buildingList);
		buildingPane.setBounds(5,120,150,200);
		add(buildingPane);
		
		buildingAdd=new JButton("add");
		buildingAdd.setBounds(buildingPane.getX(),buildingPane.getY()-90,150,25);
		buildingAdd.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				String name=askName(null);
				if (name!=null && !name.equals(""))
				{
					Building b=new Building(name);
					buildings.add(b);
					buildingList.setListData(buildings);
					editVector.add(new Wrapper<Building>(b,Edit.added));
				}
			}
		});
		buildingRemove=new JButton("remove");
		buildingRemove.setEnabled(false);
		buildingRemove.setBounds(buildingAdd.getX(),buildingAdd.getY()+30,buildingAdd.getWidth(),buildingAdd.getHeight());
		buildingRemove.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				Building b=(Building) buildingList.getSelectedValue();
				if (b!=null && confirmDelete(b.toString()))
				{
					buildings.remove(b);
					
					buildingList.setListData(buildings);
					editVector.add(new Wrapper<Building>(b,Edit.deleted));
					for (Room r:b.getRooms())
					{
						editVector.add(new Wrapper<Room>(r,Edit.deleted));
					}
				}
			}
		});
		buildingRename=new JButton("Rename");
		buildingRename.setEnabled(false);
		buildingRename.setBounds(buildingRemove.getX(),buildingRemove.getY()+30,buildingRemove.getWidth(),buildingRemove.getHeight());
		buildingRename.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				Building b=(Building) buildingList.getSelectedValue();
				String name;
				if (b!=null && (name=askName(b.getName()))!=null)
				{
					b.setName(name);
					editVector.add(new Wrapper<Building>(b,Edit.edited));
					buildingList.setListData(buildings);
				}
			}
		});
		add(buildingAdd);
		add(buildingRemove);
		add(buildingRename);		
		
		JLabel buildingLabel=new JLabel("buildings");
		buildingLabel.setBounds(buildingAdd.getX(),buildingAdd.getY()-30,900,25);
		add(buildingLabel);
		
		roomList=new JList();
		roomList.addListSelectionListener(new ListSelectionListener()
		{
			
			@Override
			public void valueChanged(ListSelectionEvent e)
			{
				if (roomList.getSelectedValue()!=null)
				{
					roomRemove.setEnabled(true);roomRename.setEnabled(true);
				}
				else
				{
					roomRemove.setEnabled(false);roomRename.setEnabled(false);
				}
			}
		});
		roomPane=new JScrollPane(roomList);
		roomPane.setBounds(buildingPane.getX()+buildingPane.getWidth()+50,buildingPane.getY(),buildingPane.getWidth(),buildingPane.getHeight());
		add(roomPane);
		
		roomAdd=new JButton("Add");
		roomAdd.setEnabled(false);
		roomAdd.setBounds(roomPane.getX(),buildingAdd.getY(),buildingAdd.getWidth(),buildingAdd.getHeight());
		roomAdd.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				Building b=(Building) buildingList.getSelectedValue();
				String name;
				if (b!=null&&(name=askName(null))!=null)
				{
					Room r=new Room(name,1);
					b.addRooms(r);
					editVector.add(new Wrapper<Building>(b,Edit.edited));
					editVector.add(new Wrapper<Room>(r,Edit.added));
					buildingList.getListSelectionListeners()[0].valueChanged(null);
				}
			}
		});
		roomRemove=new JButton("Remove");
		roomRemove.setEnabled(false);
		roomRemove.setBounds(roomAdd.getX(),roomAdd.getY()+30,roomAdd.getWidth(),roomAdd.getHeight());
		roomRemove.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				Building b=(Building) buildingList.getSelectedValue();
				Room r=(Room) roomList.getSelectedValue();
				if (b!=null && r!=null)
				{
					b.removeRooms(r);
					editVector.add(new Wrapper<Building>(b,Edit.edited));
					editVector.add(new Wrapper<Room>(r,Edit.deleted));
					buildingList.getListSelectionListeners()[0].valueChanged(null);
				}
			}
		});
		roomRename=new JButton("Rename");
		roomRename.setEnabled(false);
		roomRename.setBounds(roomRemove.getX(),roomRemove.getY()+30,roomRemove.getWidth(),roomRemove.getHeight());
		roomRename.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				Room r=(Room) roomList.getSelectedValue();
				String name;
				if (r!=null && (name=askName(r.getLocation()))!=null)
				{
					r.setLocation(name);
					editVector.add(new Wrapper<Room>(r,Edit.deleted));
					buildingList.getListSelectionListeners()[0].valueChanged(null);
				}
			}
		});
		add(roomAdd);
		add(roomRemove);
		add(roomRename);
	
		JLabel roomLabel=new JLabel("rooms");
		roomLabel.setBounds(roomAdd.getX(),buildingLabel.getY(),900,25);
		add(roomLabel);
		
		save=new JButton("Save changes");
		save.setBounds(buildingPane.getX(),buildingPane.getY()+buildingPane.getHeight()+20,150,25);
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
		
		roomPanel=new RoomPanel(roomList, hardwares, editVector);
		roomPanel.setBounds(roomPane.getX()+roomPane.getWidth()+15,roomAdd.getY(),400,roomPane.getHeight()+roomPane.getY()-roomAdd.getY());
		add(roomPanel);
		setSize(900,400);
	}

	/**
	 * Get a connection to the servlet.
	 */
	private URLConnection getServletConnection(String url) throws MalformedURLException, IOException 
	{
		URL urlServlet = new URL(getCodeBase(),url+"&applet=true");
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
			URLConnection con = getServletConnection(url);
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
	
	private void getBuildings()
	{
		try 
		{
			URLConnection con = getServletConnection(url+"&buildings=true");
			OutputStream outstream = con.getOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(outstream);
			oos.writeObject("hello");
			oos.flush();
			oos.close();

			InputStream instr = con.getInputStream();
			ObjectInputStream inputFromServlet = new ObjectInputStream(instr);
			buildings =(Vector<Building>) inputFromServlet.readObject();
			inputFromServlet.close();
			instr.close();
		} catch (Exception ex) 
		{
			ex.printStackTrace();
		}
	}
}
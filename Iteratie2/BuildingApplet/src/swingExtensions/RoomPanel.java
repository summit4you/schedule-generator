package swingExtensions;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import applet.Edit;
import applet.Wrapper;
import applet.EditVector;
import dataStructure.Room;

/**
 * @author Zjef
 * @version 1.0
 */
public class RoomPanel extends JPanel
{
	private RestrictedTextField roomSize;
	private String[] hardwares;
	private HardwarePanel hardwarePanel;
	private EditVector editVector;
	
	private JList roomList;
	private Room room;
	
	public RoomPanel(JList roomList,String[] hardwares,EditVector editVector)
	{
		super(null);
		this.roomList=roomList;
		this.hardwares=hardwares;
		this.editVector=editVector;
		initPanel();
		initListListener();
		setVisible(false);
	}
	
	private void initListListener()
	{
		roomList.addListSelectionListener(new ListSelectionListener()
		{
			@Override
			public void valueChanged(ListSelectionEvent e)
			{
				if ((room=(Room)roomList.getSelectedValue())==null)
				{
					RoomPanel.this.setVisible(false);
				}
				else
				{
					hardwarePanel.setRoom(room);
					RoomPanel.this.setVisible(true);
					roomSize.setText(Integer.toString(room.getcapacity()));
				}
			}
		});
	}
	
	private void initPanel()
	{
		JLabel sizeLabel=new JLabel("capacity");
		sizeLabel.setBounds(5,5,900,25);
		add(sizeLabel);
		roomSize=new RestrictedTextField(RestrictedTextField.RESTRICTION_INT,-1);
		roomSize.setBounds(sizeLabel.getX(),sizeLabel.getY()+sizeLabel.getHeight()+5,150,25);
		roomSize.addKeyListener(new KeyListener()
		{
			@Override
			public void keyTyped(KeyEvent arg0)
			{
				room.setcapacity(Integer.parseInt(roomSize.getText()));
				editVector.add(new Wrapper<Room>(room,Edit.edited));
			}
			@Override
			public void keyPressed(KeyEvent e)
			{
			}
			@Override
			public void keyReleased(KeyEvent e)
			{
			}
		});
		add(roomSize);
		
		JLabel hardwareLabel=new JLabel("Harware");
		hardwareLabel.setBounds(roomSize.getX(),roomSize.getY()+20,900,25);
		add(hardwareLabel);
		hardwarePanel=new HardwarePanel(hardwares, editVector);
		JScrollPane scroll=new JScrollPane(hardwarePanel);
		scroll.setBounds(hardwareLabel.getX(), hardwareLabel.getY()+hardwareLabel.getHeight()+5,320,150);
		add(scroll);
	}
}
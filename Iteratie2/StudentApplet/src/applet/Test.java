package applet;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

import swingExtensions.RestrictedTextField;

public class Test
{
	public static void main(String[] aeg)
	{
		JFrame frame=new JFrame();
		JPanel panel=new JPanel(null);
		
		RestrictedTextField r=new RestrictedTextField(RestrictedTextField.RESTRICTION_INT, -1);
		r.setBounds(10,10,100,25);
		r.addKeyListener(new KeyListener()
		{
			@Override
			public void keyTyped(KeyEvent e)
			{
				// TODO Auto-generated method stub
				System.out.println("typed/"+(e.isConsumed()&&(e.getKeyChar()!=KeyEvent.VK_BACK_SPACE)));
			}
			
			@Override
			public void keyReleased(KeyEvent e)
			{
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyPressed(KeyEvent e)
			{
				// TODO Auto-generated method stub
				
			}
		});
		panel.add(r);
		
		frame.setSize(500,500);
		frame.setContentPane(panel);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}

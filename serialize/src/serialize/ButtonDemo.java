package serialize;

import javax.swing.*;
import java.awt.*;

import java.io.*;
import java.nio.file.*;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

class ButtonFrame extends JFrame 
{
	public static JButton bChange ; //button that appears in window
	public static JTextField bText ; //text field that appears in window

	// constructor for ButtonFrame
	ButtonFrame(String title, JButton button) 
	{
		super( title );                     // invoke the JFrame constructor
		setLayout( new FlowLayout() );      // set the layout manager

		//initialize textfield
		bText = new JTextField(40);
		bText.setText("place new button name here and click button");
		add(bText);

		//initialize button in window with text from file
		bChange = new JButton(button.getText()); 
		add( bChange );            

		//add event listener to button to change text of button in window
		bChange.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e)
			{
				bChange.setText(bText.getText());


				//save the new button text to file
				try {
					FileOutputStream fileOut =
							new FileOutputStream("button.ser");
					ObjectOutputStream out = new ObjectOutputStream(fileOut);
					out.writeObject(bChange);
					out.close();
					fileOut.close();
				} catch(Exception ex){

				}
			}
		});
		setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE ); 
	}
}

public class ButtonDemo 
{
	public static JButton jB = null;

	public static void main ( String[] args )
	{
		try
		{
			//check serialized file exists
			Path path = Paths.get("button.ser");

			if (Files.notExists(path)) {
				//create file
				FileOutputStream fileOut =
						new FileOutputStream("button.ser");
				ObjectOutputStream out = new ObjectOutputStream(fileOut);
				out.writeObject(new JButton("Click Me!"));
				out.close();
				fileOut.close();
			}

			//initailize button from file 
			FileInputStream fileIn = new FileInputStream("button.ser");
			ObjectInputStream in = new ObjectInputStream(fileIn);
			jB = (JButton) in.readObject();
			in.close();
			fileIn.close();
		}catch(Exception i)
		{
		}
		ButtonFrame frm = new ButtonFrame("Button Demo", jB);

		frm.setSize( 400, 200 );     
		frm.setVisible( true );   
	}
}

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;

public class MyButtonListener implements ActionListener
{
    //attributes, when instantiated these are passed from the frame for use 
	//change to pass in whatever things we'll need to change on the GUI
    private JFrame aFrame;
    private JTextField textField;
    private JTextArea textArea;

    
    //the frame and text spaces which need to pass information to the listener
    public MyButtonListener(JFrame myFrame,JTextField textField,JTextArea textArea)
    {
	this.aFrame = myFrame;
	this.textField = textField;
	this.textArea = textArea;	
    }


    //defines what the buttons do, try and catch for error handling/ required for code to compile
    public void actionPerformed (ActionEvent event)
    {	
	String s = event.getActionCommand();
	
	if (s.equals("0"))
		{
		aFrame.setTitle("BOONK GANG SQUAD");		
	    }
	
	else if (s.equals("5"))
	{
	aFrame.setTitle("GUCCI GANG");		
    }
	
	//not in the machine, left in case you wanna know how to do something
	else if (s.equals("clear"))
	    {
		textField.setText("");
		textArea.setText("");
		aFrame.setTitle("Clearing...");
		try {
		    Thread.sleep(1000);
		} catch(InterruptedException e) {
		    System.out.print("whoops");
		}
		aFrame.setTitle("Default title");
	    }
	
	else if (s.equals("loonie")){
		textField.setText("clicked the loonie");
		
		}
    }
}


import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;

public class EventListener implements ActionListener
{
    //attributes, when instantiated these are passed from the frame for use 
	//change to pass in whatever things we'll need to change on the GUI
    private JFrame aFrame;
    private JTextField textField;

    
    //the frame and text spaces which need to pass information to the listener
    public EventListener(JFrame myFrame,JTextField textField)
    {
	this.aFrame = myFrame;
	this.textField = textField;
    }


    //defines what the buttons do, try and catch for error handling/ required for code to compile
    public void actionPerformed (ActionEvent event)
    {	
	String frameEvent = event.getActionCommand();
	
	switch(frameEvent){
		case "Button0":
			aFrame.setTitle("button 0 was pressed");
			break;			
		case "Button1":
			aFrame.setTitle("button 1 was pressed");
			break;
		case "Button2":
			aFrame.setTitle("button 2 was pressed");
			break;
		case "Button3":
			aFrame.setTitle("button 3 was pressed");
			break;
		case "Button4":
			aFrame.setTitle("button 4 was pressed");
			break;
		case "Button5":
			aFrame.setTitle("button 5 was pressed");
			break;
	
		case "loonie":
			textField.setText(frameEvent + " was pressed");
			break;			
		case "toonie":
			textField.setText(frameEvent + " was pressed");
			break;
		case "quarter":
			textField.setText(frameEvent + " was pressed");;
			break;
		case "dime":
			textField.setText(frameEvent + " was pressed");
			break;
		case "nickel":
			textField.setText(frameEvent + " was pressed");
			break;
		case "washer":
			textField.setText(frameEvent + " was pressed");
			break;
		}
    }
    
}

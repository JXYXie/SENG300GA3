
import javax.swing.*;

import java.awt.Component;
import java.awt.event.*;
import org.lsmr.vending.hardware.*;

import ca.ucalgary.seng300.VendingMachineLogic.VendingMachineLogic;
import javafx.scene.control.Button;

import org.lsmr.vending.*;

public class EventListener implements ActionListener
{
    //attributes, when instantiated these are passed from the frame for use 
	//change to pass in whatever things we'll need to change on the GUI

    private MyFrame aFrame;
	private VendingMachine machine;
	
	// Instantiates the coins used for inserting into the vending machine when clicked by the GUI
	Coin toonie = new Coin(200);
	Coin loonie = new Coin(100);
	Coin quarter = new Coin(25);
	Coin dime = new Coin(10);
	Coin nickel = new Coin(5);

    //the frame and text spaces which need to pass information to the listener
    public EventListener(MyFrame myFrame,JTextField textField, VendingMachine vm)
    {
    	aFrame = myFrame;
		machine = vm;
    }

    //defines what the buttons do, try and catch for error handling/ required for code to compile
    public void actionPerformed (ActionEvent event)
    {	
	String frameEvent = event.getActionCommand();
	
	switch(frameEvent){
		case "Button0":
			machine.getSelectionButton(0).press();
			// aFrame.setShow(true);
			aFrame.setTitle("button 0 was pressed");
			break;			
		case "Button1":
			machine.getSelectionButton(1).press();
			aFrame.setTitle("button 1 was pressed");
			break;
		case "Button2":
			machine.getSelectionButton(2).press();
			aFrame.setTitle("button 2 was pressed");
			break;
		case "Button3":
			machine.getSelectionButton(3).press();
			aFrame.setTitle("button 3 was pressed");
			break;
		case "Button4":
			machine.getSelectionButton(4).press();
			aFrame.setTitle("button 4 was pressed");
			break;
		case "Button5":
			machine.getSelectionButton(5).press();
			aFrame.setTitle("button 5 was pressed");
			break;
			
		// Currently working on making vended cans display	
			
//		case "Vended0":
//			aFrame.deactivateButton(0);
//			break;
//			
//		case "Vended1":
//			aFrame.deactivateButton(1);
//			break;
//			
//		case "Vended2":
//			aFrame.deactivateButton(2);
//			break;
//			
//		case "Vended3":
//			aFrame.deactivateButton(3);
//			break;
//			
//		case "Vended4":
//			aFrame.deactivateButton(4);
//			break;
//			
//		case "Vended5":
//			aFrame.deactivateButton(5);
//			break;
			
		case "loonie":
			try {
				// Inserts a loonie into the vending machine
				machine.getCoinSlot().addCoin(loonie);
			} catch (DisabledException d) {
				// Currently doesn't do anything when the exception is caught, should be handled on
				// the software side??
			}
			break;			
		case "toonie":
			try {
				// Inserts a toonie into the vending machine
				machine.getCoinSlot().addCoin(toonie);
			} catch (DisabledException d) {
				
			}
			break;
		case "quarter":
			try {
				// Inserts a quarter into the vending machine
				machine.getCoinSlot().addCoin(quarter);
			} catch (DisabledException d) {
				
			}
			break;
		case "dime":
			try {
				//textField.setText(frameEvent + " was pressed");
				machine.getCoinSlot().addCoin(dime);
			} catch (DisabledException d) {
				
			}
			break;
		case "nickel":
			try {
				//textField.setText(frameEvent + " was pressed");
				machine.getCoinSlot().addCoin(nickel);
			} catch (DisabledException d) {
				
			}
			break;
		case "washer":
			//textField.setText(frameEvent + " was pressed");
			break;
		}
    }
    
}
package ca.ucalgary.seng300.VendingMachineLogic;

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
	private JTextField vendedPop;
	private JTextField[] coinsReturned;
	private JButton lock, unlock, loadCoins, loadPops, emptyCoins;
	private int[] numberOfCoins;
	
	// Instantiates the coins used for inserting into the vending machine when clicked by the GUI
	Coin toonie = new Coin(200);
	Coin loonie = new Coin(100);
	Coin quarter = new Coin(25);
	Coin dime = new Coin(10);
	Coin nickel = new Coin(5);
	Coin dummy = new Coin(42);

    //the frame and text spaces which need to pass information to the listener
    public EventListener(MyFrame myFrame,JTextField textField, JTextField vendedPop, VendingMachine vm, JTextField[] coinsReturned, int[] numberOfCoins)
    {
    	aFrame = myFrame;
		machine = vm;
		this.vendedPop = vendedPop;
		this.coinsReturned = coinsReturned;
		this.numberOfCoins = numberOfCoins;
    }
    
    public void addLockUnlock(JButton lock, JButton unlock, JButton loadCoins, JButton loadPops, JButton emptyCoins) {
    	this.lock = lock;
    	this.unlock = unlock;
    	this.loadCoins = loadCoins;
    	this.loadPops = loadPops;
    	this.emptyCoins = emptyCoins;
    }

    //defines what the buttons do, try and catch for error handling/ required for code to compile
    public void actionPerformed (ActionEvent event)
    {	
	String frameEvent = event.getActionCommand();
	
	switch(frameEvent){
		case "Button0":
			machine.getSelectionButton(0).press();
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
			
		case "Vended":
			vendedPop.setText("None");
			for(int i = 0; i < coinsReturned.length; i++) {
				numberOfCoins[i] = 0;
				coinsReturned[i].setText("Empty");
			}
			machine.getCoinReturn().unload();
			break;
			
		case "Lock":
			unlock.setVisible(true);
			loadCoins.setVisible(true);
			loadPops.setVisible(true);
			emptyCoins.setVisible(true);
			lock.setVisible(false);
			machine.enableSafety();
			break;
			
		case "Unlock":
			unlock.setVisible(false);
			loadCoins.setVisible(false);
			loadPops.setVisible(false);
			emptyCoins.setVisible(false);
			lock.setVisible(true);
			machine.disableSafety();
			break;
			
		case "LoadCoins":
			for(int i = 0; i < machine.getNumberOfCoinRacks(); i++) {
				Coin value;
				if (i == 0) 
					value = new Coin(5);
				else if (i == 1)
					value = new Coin(10);
				else if (i == 2)
					value = new Coin(25);
				else if (i == 3)
					value = new Coin(100);
				else
					value = new Coin(200);
				machine.getCoinRack(i).load(value, value, value, value);	//load 4 of every kind of coin to start with
			}
			machine.getExactChangeLight().deactivate();
			break;
			
		case "EmptyCoins":
			for(int i = 0; i < machine.getNumberOfCoinRacks(); i++) {
				machine.getCoinRack(i).unload();	//load 4 of every kind of coin to start with
			}
			break;
				
		case "configPanel":
			ConfigPanelLogic.initialize();
			
			break;
	
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
			try {
				//textField.setText(frameEvent + " was pressed");
				machine.getCoinSlot().addCoin(dummy);
			} catch (DisabledException d) {
				
			}
			break;
		}
    }
    
}

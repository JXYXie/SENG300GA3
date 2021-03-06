/************************RUN ME!******************************
 * SENG 300 Group Assignment 3 driver class
 * Main logic class
 * @author Matthew Wojcik
 * @author Preston Tran
 * @author Xin Yan (Jack) Xie
 * @author Jacky Wu
 * @author Sara Strand
 * @author Jaskaran Sidhu
 * @author Steffen Gerdes
 * @author Sheldon Birch-Lucas
 * @author Siddharth Kataria
 * This driver class is used to start up the vending machine GUI
 *and load the vending machine with pops and coins to be used by the client
 * This class also contains a GUI listener class which listens for events 
 *from the Vending Machine Logic to pass on to the GUI
 *************************************************************/
package ca.ucalgary.seng300.VendingMachineLogic;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.lsmr.vending.Coin;
import org.lsmr.vending.hardware.*;

import ca.ucalgary.seng300.VendingMachineLogic.GUIListener;
import ca.ucalgary.seng300.VendingMachineLogic.VendingMachineLogic;
//starts the GUI by making a frame
public class Driver
{
	
    public static void main(String [] args) throws IOException
    {
    	// Instantiating the Vending Machine and the Vending Machine Logic to use with the GUI
		int[] coinKinds = {5, 10, 25, 100, 200}; //Nickels, dimes, quarters, loonies, toonies (all values in cents)
		int btnCount = 6; //6 types of pop
		int coinRackCapacity = 15;
		int popRackCapacity = 10;
		int receptacleCapacity = 15;
		int deliveryChuteCapacity = 10;
		int coinReturnCapacity = 8;
		
		VendingMachine machine = new VendingMachine(coinKinds, btnCount, coinRackCapacity, popRackCapacity, receptacleCapacity, deliveryChuteCapacity, coinReturnCapacity);
		VendingMachineLogic machineLogic = new VendingMachineLogic(machine);
		List<String> popNames = new ArrayList<String>(); //List of pop names
		
		popNames.add("Water");
		popNames.add("Pepsi");
		popNames.add("Sprite");
		popNames.add("Mountain Dew");
		popNames.add("Orange Crush");
		popNames.add("Gatorade");
		
		List<Integer> costs = new ArrayList<Integer>(); //List of pop costs
		
		for (int i = 0; i < popNames.size(); i++ ) {
			costs.add(250); //everything costs 2.50
		}
		
		machineLogic.setPopNames(popNames);
		machineLogic.setCosts(costs);
		machine.configure(popNames, costs);
		machine.loadPopCans(5, 5, 5, 5, 5, 5); //Starts with 5 of each kind of pop
		
		//load the coin racks 
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
    		
        MyFrame aFrame = new MyFrame();
        aFrame.makeframe(machine, machineLogic);	// Passes the machine and machine logic to the frame for later use
        
        // Creates a listener that will update the display of the GUI to match the display in the event log
        class MyGUIListener implements GUIListener {
        	/**
        	 * Method used to change the display of the GUI to match the vending machine logic
        	 * @param message is the message to display in the vending GUI display
        	 */
        	@Override
        	public void updateDisplay(String message) {
        		aFrame.setMessage(message);
        	}
        	
        	/**
        	 * Method used to show what pop was vended by the machine
        	 * @param popName is the pop that was vended by the machine
        	 */
			@Override
			public void popVended(String popName) {
				aFrame.vendedPop(popName);
			}

        	/**
        	 * Method used to update the coins in the vending machine
        	 * @param vm is the vending machine the GUI is representing
        	 */
			@Override
			public void updateCoins(VendingMachine vm) {
				aFrame.setCoinCount(vm);
			}

        	/**
        	 * Method used to update the exact change light for the vending machine
        	 * @param set is a boolean value used to change the visibility of the light in the GUI
        	 */
			@Override
			public void updateExactChangeLight(boolean set) {
				aFrame.setExactChangeLight(set);
			}

        	/**
        	 * Method used to update the out of order light for the vending machine
        	 * @param set is a boolean value used to change the visibility of the light in the GUI
        	 */
			@Override
			public void updateOutOfOrderLight(boolean set) {
				aFrame.setOutOfOrderLight(set);
			}

			/**
			 * Method used to unload coins from vending machine
			 */
			@Override
			public void coinsUnloaded() {
				aFrame.setCoinCount(machine);
			}

			/**
			 * Method used to load coins in vending machine
			 */
			@Override
			public void coinsLoaded() {
				aFrame.setCoinCount(machine);
			}

			/**
			 * Method used to show returned coins in vending machine
			 */
			@Override
			public void coinsReturned(Coin[] coins) {
				aFrame.setCoinsReturnedCount(coins);
			}

        	
        }
        // Instantiates the gui listener and registers it to the vending machine logic
        MyGUIListener guiListen = new MyGUIListener();
    	machineLogic.registerGUI(guiListen);
    }	
}
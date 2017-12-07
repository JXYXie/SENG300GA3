package ca.ucalgary.seng300.VendingMachineLogic;

import org.lsmr.vending.Coin;
import org.lsmr.vending.hardware.VendingMachine;

/**
 * Listens for events from the Vending Machine Logic to pass on to the GUI.
 */
public interface GUIListener {
	/**
	 * Method to update the GUI display with the inputted message from the Vending
	 * Machine Logic unit
	 * @param message is the message to be displayed by the GUI
	 */
	public void updateDisplay(String message);
	
	/**
	 * A method to update the GUI to show what pop was vended
	 * @param name is the name of the pop that was vended
	 */
	public void popVended(String name);
	
	/**
	 * A method to update the coins in the GUI
	 * @param vm is the vending machine to get the coins from
	 */
	public void updateCoins(VendingMachine vm);
	
	/**
	 * A method to update the status of the exact change light in the GUI
	 * @param set is the boolean value to set the visibility of the light in the GUI
	 */
	public void updateExactChangeLight(boolean set);
	
	/**
	 * A method to update the status of the out of order light in the GUI
	 * @param set is the boolean value to set the visibility of the light in the GUI
	 */
	public void updateOutOfOrderLight(boolean set);
	
	/**
	 * A method to unload the coins from the vending machine in the GUI
	 */
	public void coinsUnloaded();
	
	/**
	 * A method to load the coins in the vending machine in the GUI
	 */
	public void coinsLoaded();
	
	/**
	 * A method to get the coins returned to the GUI
	 * @param coin is the array of coins returned to the GUI
	 */
	public void coinsReturned(Coin[] coin);
}

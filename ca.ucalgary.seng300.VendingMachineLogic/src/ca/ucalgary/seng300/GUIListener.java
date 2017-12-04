package ca.ucalgary.seng300.VendingMachineLogic;

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
	 * Method that will show what pop was vended
	 */
	public void popVended();
}

/*******************************************
 * Configuration panel logic class
 * Handles the logic to allow the access and change of vending item costs
 *through button presses
 * @author Xin Yan (Jack) Xie
 * @author Steffen Gerdes
 *******************************************/
package ca.ucalgary.seng300.VendingMachineLogic;

import java.text.NumberFormat;
import java.util.List;
import java.util.Scanner;

import org.lsmr.vending.hardware.VendingMachine;

public class ConfigPanelLogic {
	
	private VendingMachineLogic vml;
	private VendingMachine vm;
	
	private TechJFrame techFrame; //Config panel GUI JFrame object
	
	private boolean indexMode; //whether the technician is currently accessing the pop index as opposed to pop cost
	private String input = ""; //the input from the keypad
	private String event; //the internal display message
	
	private int indexToModify;
	private int costToModify;
	
	private NumberFormat formatter = NumberFormat.getCurrencyInstance(); //money format
	
	/**
	 * Constructor
	 * @param vm VendingMachine object
	 * @param vml VendingMachineLogic object
	 */
	public ConfigPanelLogic(VendingMachine vm, VendingMachineLogic vml) {
		this.vm = vm;
		this.vml = vml;
	}
	
	/**
	 * When the technician panel is first accessed/opened
	 */
	public void initialize() {
		techFrame = new TechJFrame(vm, vml);
		techFrame.setVisible(true); //Sets the config panel visible
	}
	
	/**
	 * Resets the display of the config panel and clears the current input
	 */
	public void reset() {
		indexMode = true; //Pop kind (index) is always picked before the cost
		input = ""; //Reset the input
		
		event = "Pop index to modify: "; //Automatically starts off asking for which pop index to modify
		display(event); //displays the prompt
	}
	
	/**
	 * When the enter button is pressed this method is called
	 */
	public void pressEnterButton() {
		vm.getConfigurationPanel().getEnterButton().press(); //Make sure the button is actually pressed
		if (indexMode) { //If what the technician entered was the pop rack index
			if (input != "") { //and input is not empty
				Scanner scan = new Scanner(input);
				if(!scan.hasNextInt()){//If string does not contain an int reset
					reset();
				}
				else{
					indexToModify = Integer.parseInt(input); //Gets the index to modify from the input
					indexMode = false; //No longer asking for pop rack index
					input = ""; //resets the input
					
					event = "New cost: "; //Now prompt for cost
					display(event);
				}	
			} else {
				reset(); //retries with prompt
			}
		} else { //If the technician entered in the new cost
			if (input != "") { //and the input is not empty
				costToModify = Integer.parseInt(input);
			} else { //if input is empty then
				costToModify = 200; //the new cost will default to 200 cents
			}
			changeCost(indexToModify, costToModify); //Updates the corresponding pop rack with the new cost
			indexMode = true; //Now it is back to index mode
			input = ""; //resets the input
		}
	}
	
	/**
	 * Any number button is pressed (0 - 9 for now)
	 * Gets the button index and updates it to the input
	 * @param btnIndex of the button that was pressed
	 */
	public void pressButton(int btnIndex) {
		vm.getConfigurationPanel().getButton(btnIndex).press(); //Make sure the button is actually pressed
		if (indexMode) {
			input += btnIndex; //Concatenates it to input
			event = "Pop index to modify: " + input;
			display(event);
		} else {
			input += btnIndex; //Concatenates it to input
			event = "New cost: " + input;
			display(event);
		}
	}
	
	/**
	 * Any char button (a-z) is pressed
	 * @param btnIndex of the button that was pressed
	 * @param the letter of the button that was pressed
	 */
	public void pressCharButton(int btnIndex, char letter) {
		vm.getConfigurationPanel().getButton(btnIndex).press();
		input += letter; //Concatenates the char pressed to input
		event = input;
		System.out.println(input);
		display(event);
	}
	
	/**
	 * @param index of the pop rack to be edited
	 * @param newCost of the pop
	 */
	public void changeCost(int index, int newCost) {
		List<Integer> costs = vml.getCosts(); //gets the existing costs and store it
		costs.set(index, newCost); //change the cost of the pop that was edited
		vml.setCosts(costs); //updates the new costs arrayList
		vm.configure(vml.getPopNames(), costs); //and configure the machine with the new costs
		
		double centCost = ((double)newCost / 100);
		event = ("New cost of " + vm.getPopKindName(index) + " is now " + formatter.format(centCost)); //Get the new cost
		display(event);
	}
	
	/**
	 * Access the internal display panel of the Config panel
	 * Logs display messages
	 * @param event that is displayed and logged
	 */
	public void display(String msg) {
		vm.getConfigurationPanel().getDisplay().display(msg);
		vml.log("CONFIGPANEL DISPLAY: " + msg);
	}
	
	/**
	 * Gets the current message that is on the internal display
	 * @return the event message
	 */
	public String getDisplayMessage() {
		return event;
	}
	
	/**
	 * Gets the current TechJFrame (config panel Jframe)
	 * @return techFrame
	 */
	public TechJFrame getTechFrame() {
		return techFrame;
	}
	
	
}

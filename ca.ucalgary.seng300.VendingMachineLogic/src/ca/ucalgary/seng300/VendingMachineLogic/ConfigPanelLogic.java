/*******************************************
 * Configuration panel logic class
 * Handles the logic to allow the access and change of vending item costs
 *through button presses
 *******************************************/
package ca.ucalgary.seng300.VendingMachineLogic;

import java.util.List;
import java.util.Scanner;

import org.lsmr.vending.hardware.VendingMachine;

public class ConfigPanelLogic {
	
	private VendingMachineLogic vml;
	private VendingMachine vm;
	
	private TechJFrame techFrame;
	
	private boolean indexMode;
	private String input;
	private String event;
	
	private int indexToModify;
	private int costToModify;
	
	public ConfigPanelLogic(VendingMachine vm, VendingMachineLogic vml) {
		this.vm = vm;
		this.vml = vml;
	}
	
	public void setUp() {
		techFrame = new TechJFrame(vm, vml);
		techFrame.setVisible(true);
	}
	
	/**
	 * When the technician panel is first accessed/opened
	 */
	public void initialize() {
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
				if(!scan.hasNextInt()){//If string does not contain an int initialize again
					initialize();
				}
				else{
					indexToModify = Integer.parseInt(input); //Gets the index to modify from the input
					indexMode = false; //No longer asking for pop rack index
					input = ""; //resets the input
					
					event = "New cost: "; //Now prompt for cost
					display(event);
				}	
			} else {
				initialize(); //retries with prompt
			}
		} else { //If the technician entered in the new cost
			if (input != "") { //and the input is not empty
				costToModify = Integer.parseInt(input);
			} else { //if input is empty then
				costToModify = 100; //the new cost will default to 100 cents
			}
			changeCost(indexToModify, costToModify); //Updates the corresponding pop rack with the new cost
			initialize(); //Now ask for pop index again
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
	 * @param index of the pop rack to be edited
	 * @param newCost of the pop
	 */
	public void changeCost(int index, int newCost) {
		List<Integer> costs = vml.getCosts(); //gets the existing costs and store it
		costs.set(index, newCost); //change the cost of the pop that was edited
		vml.setCosts(costs); //updates the new costs arrayList
		vm.configure(vml.getPopNames(), costs); //and configure the machine with the new costs
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
	
}

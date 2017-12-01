/*******************************************
 * Configuration panel logic class
 * Handles the logic to allow the access and change of vending item costs
 *through button presses
 *******************************************/
package ca.ucalgary.seng300.VendingMachineLogic;

import java.util.List;

import org.lsmr.vending.hardware.VendingMachine;

public class ConfigPanelLogic {
	
	private VendingMachineLogic vml;
	private VendingMachine vm;
	
	private boolean indexMode = true; //Pop kind (index) is always picked before the cost
	private String input = "";
	
	
	public ConfigPanelLogic(VendingMachine vm, VendingMachineLogic vml) {
		this.vm = vm;
		this.vml = vml;
	}
	
	public void configure() {
		if (indexMode) {
			vml.display("Pop index to modify: ");
			indexMode = false;
		} else {
			
			vml.display("Modifying New cost: ");
			indexMode = true;
		}
	}
	
	public void getInput(int btnIndex) {
		if (indexMode) {
			input += btnIndex;
			vml.display("Pop index to modify: " + input);
		} else {
			input += btnIndex;
			vml.display("New cost: " + input);
		}
	}
	
	public void changeCost(int index, int newCost) {
		List<Integer> costs = vml.getCosts();
		costs.set(index, newCost);
		vml.setCosts(costs);
		vm.configure(vml.getPopNames(), costs);
	}
	
}

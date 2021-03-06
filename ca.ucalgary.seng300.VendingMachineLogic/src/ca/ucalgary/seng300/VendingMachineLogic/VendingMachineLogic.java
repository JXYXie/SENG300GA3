/**********************************************************
 * SENG 300 Group Assignment 3
 * Main logic class
 * @author Xin Yan (Jack) Xie
 * @author Jacky Wu
 * @author Sara Strand
 * @author Jaskaran Sidhu
 * @author Siddharth Kataria
 * @author Steffen Gerdes
 * @author Sheldon Birch-Lucas
 * @author Matthew Wojcik
 * @author Preston Tran
 * This class handles all the logic operations that occur in a vending machine,
 * specifically, this class handles any transactions and vending operations: 
 * i.e. coins are inserted, buttons get pressed, pop gets vended, change is returned, etc
 * Registers all the listeners of the hardware
 **********************************************************/
package ca.ucalgary.seng300.VendingMachineLogic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

import static java.util.concurrent.TimeUnit.*;

import org.lsmr.vending.Coin;
import org.lsmr.vending.hardware.*;

public class VendingMachineLogic {

	private VendingMachine vm; //Vending machine object
	private VendingListener vlistener; //listener object
	private ConfigPanelLogic cpl; //config panel logic object
	
	//fields for the message looping
	private ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
	private ScheduledFuture<?> beeperHandle;
	private EventLogger logger;
	
	private List<PushButton> buttonList = new ArrayList<>(); //List of buttons
	
	private List<Integer> costs = new ArrayList<Integer>(); //List of pop costs
	private List<String> popNames = new ArrayList<String>(); //List of pop names
	private int userCredit;
	private String currency = "CAD"; //Current currency format, can be changed i.e. USD
	private String event; //This is used to pass to display and log file when necessary

	// Makes an array list of GUIListeners to communicate with the GUI
	private ArrayList<GUIListener> listeners = new ArrayList<>();

	// Registers listeners to the array
    public final void registerGUI(GUIListener listener) {
    	listeners.add(listener);
    }
    
	/********************
	 * Constructor
	 * instantiates and initializes all the relevant hardware
	 * and registers listeners
	 *******************/
	public VendingMachineLogic(VendingMachine vm) {
		
		this.vm = vm;
		vlistener = new VendingListener(vm, this);
		cpl = new ConfigPanelLogic(vm, this);
		logger = new EventLogger();
		
		//Iterate through all buttons
		for (int i = 0; i < vm.getNumberOfSelectionButtons(); i++) {
			PushButton sb = vm.getSelectionButton(i); //Instantiates the button
			sb.register(vlistener); //And registers the relevant listeners
			buttonList.add(sb); //Stores into an ArrayList for later use
		}
		
		//Iterate through all available pop can racks
		for (int i = 0; i < vm.getNumberOfPopCanRacks(); i++) {
			vm.getPopCanRack(i).register(vlistener); //Registers the relevant listeners
		}
		
		//Iterate through all available coin racks
		for (int i = 0; i < vm.getNumberOfCoinRacks(); i++) {
			vm.getCoinRack(i).register(vlistener); //Registers the relevant listeners
		}
		
		//Now register all the rest of the listeners
		vm.getCoinSlot().register(vlistener);
		vm.getDeliveryChute().register(vlistener);
		vm.getCoinReceptacle().register(vlistener);
		vm.getCoinReturn().register(vlistener);
		vm.getOutOfOrderLight().register(vlistener);
		vm.getExactChangeLight().register(vlistener);
		vm.getDisplay().register(vlistener);
		vm.getLock().register(vlistener);
		
		userCredit = 0;
		displayCredit(); //Display looping message at beginning since credit is 0
	}
	
	/**
	 * Buy method
	 * is called when the user attempts to buy anything from the vending machine
	 * @param btnIndex the index of the button of the pop that is pressed (bought)
	 * @throws DisabledException
	 * @throws CapacityExceededException
	 * @throws EmptyException
	 */
	public void buy(int btnIndex) throws DisabledException, CapacityExceededException, EmptyException {
		
		int cost = vm.getPopKindCost(btnIndex); //gets the cost of the pop that the user is trying to buy
		
		if (cost > userCredit) { //if the user does not have enough money
			event = "Insufficient credit: " + (cost - userCredit) + " cents short"; //then display this to show how much more money the user needs
			display(event);
		} else { //enough money
			vm.getPopCanRack(btnIndex).dispensePopCan(); //tries to dispense the pop
			vm.getCoinReceptacle().storeCoins(); //and store the coins
			userCredit -= cost; //update the new credit
			returnChange(); //return the remaining change if there is any
			displayCredit(); //display the credit or if credit is 0 display greeting message again
		}
	}
	
	/******************************** Change Functions ********************************************/
	/**
	 * Returns the change (could be exact or not exact)
	 * Algorithm will always return the highest value coin when possible (Slightly modified greedy algorithm)
	 * @throws DisabledException
	 * @throws CapacityExceededException
	 * @throws EmptyException 
	 */
	public void returnChange() throws DisabledException, CapacityExceededException, EmptyException{
		
		int coin; //the coin the vending machine will try to return
		int[] coinValues = new int[vm.getNumberOfCoinRacks()]; //array of the number of coin racks
		CoinRack returnRack;
		
		for (int i = 0; i < vm.getNumberOfCoinRacks(); i++ ) { //goes through all the coin racks
			coinValues[i] = vm.getCoinKindForCoinRack(i); //and gets all the coin values
		}
		Arrays.sort(coinValues); //then sort it in ascending order
		
		for (int i = (vm.getNumberOfCoinRacks() - 1); i >= 0 ; i-- ) { //reverse from biggest value to smallest
			coin = coinValues[i];
			returnRack = vm.getCoinRackForCoinKind(coin); //gets the rack we are returning
			
			if (userCredit == 0) //break out of the loop if the credit is 0
				break;
			
			while ((userCredit >= coin) && (returnRack.size() > 0)) { //otherwise tries to return the highest value coin
				returnRack.releaseCoin();
				addCredit(-coin); //updates the user credit to account for the returned change
			}
		}
		
		if (userCredit != 0) { //if there is still money left in the vending machine that means there is not enough change for the user
			vm.getExactChangeLight().activate(); //so exact change light is turned on
		} else if (userCredit == 0 && vm.getExactChangeLight().isActive()) { //if there is no money left and the exact change light is turned on
			vm.getExactChangeLight().deactivate(); //turn it off
		}
		
	}
	/*********************************** Machine GUI ********************************************/
	/**
	 * Method to update all listeners attached to communicate with the GUI
	 * and pass the string to the display of the GUI
	 * @param message is the string to be passed to the GUI display
	 */
	public void notifyMachineGUI(String message) {
		for(GUIListener listener : listeners) {
			listener.updateDisplay(message);
		}
	}
	
	/**
	 * A method that updates all listeners attached to communicate with the GUI
	 * and pass the name of the pop that was vended
	 * @param pop the name of the pop that was vended
	 */
	public void notifyVendedGUI(String pop) {
		for(GUIListener listener : listeners) {
			listener.popVended(pop);
		}
	}
	
	/**
	 * A method that updates all listeners attached to communicate with the GUI
	 * and update the coins in the vending machine after a transaction has successfully
	 * happened
	 * @param vm is the vending machine to get the counts of the coins from
	 */
	public void notifyCoinsGUI(VendingMachine vm) {
		for(GUIListener listener : listeners) {
			listener.updateCoins(vm);
		}
	}
	
	/**
	 * A method that updates all listeners attached to communicate with the GUI
	 * and update the exact change light in the vending machine
	 * @param set is a boolean value to set the light to on or off
	 */
	public void updateExactChangeLight(boolean set) {
		for(GUIListener listener : listeners) {
			listener.updateExactChangeLight(set);
		}
	}
	
	/**
	 * A method that updates all listeners attached to communicate with the GUI
	 * and update the out of order light in the vending machine
	 * @param set is a boolean value to set the light to on or off
	 */
	public void updateOutOfOrderLight(boolean set) {
		for(GUIListener listener : listeners) {
			listener.updateOutOfOrderLight(set);
		}
	}
	
	/**
	 * A method that updates all listeners attached to communicate with the GUI
	 * and load coins into the vending machine
	 */
	public void notifyCoinsLoadedGUI() {
		for(GUIListener listener : listeners) {
			listener.coinsLoaded();
		}
	}
	
	/**
	 * A method that updates all listeners attached to communicate with the GUI
	 * and unload coins from the vending machine
	 */
	public void notifyCoinsUnloadedGUI() {
		for(GUIListener listener : listeners) {
			listener.coinsUnloaded();
		}
	}
	
	/**
	 * A method that updates all listeners attached to communicate with the GUI
	 * how many coins were returned to the buyer
	 * @param coin
	 */
	public void notifyCoinsReturned(Coin[] coin) {
		for(GUIListener listener : listeners) {
			listener.coinsReturned(coin);
		}
	}
	/******************************** Display Functions ********************************************/
	public void display(String event) {
		if (event != null) {
			vm.getDisplay().display(event); //display the message/event to the vending machine
			notifyMachineGUI(event); //notify the GUI about the message/event
			log("DISPLAY: " + event); //and logs it to the event log file
		}
	}
	/**
	 * Displays the hi greeting prompt every second for 5 seconds if there userCredit = 0 in the vending machine
	 */
	public void displayHi() {
		
		String prompt = "Hi there!";
		final Runnable looper = new Runnable() {
			@Override
			public void run() {
				if (userCredit == 0) {
					resetTimer();
					display(prompt);
				} else
					resetTimer();
			}
		};
		
		beeperHandle = scheduler.scheduleAtFixedRate(looper, 0, 1, SECONDS);
		
		scheduler.schedule(new Runnable() {
			@Override
			public void run() { beeperHandle.cancel(true); }
		     }, 5, SECONDS);
	}
	/**
	 * Displays current credit but if it is 0 call displayHi()
	 */
	public void displayCredit() {
		
		if (userCredit > 0) {
			display("Credit: " + userCredit);
			resetTimer();
		} else {
			displayHi();
		}
	}
	/**
	 * Cancels the timer for the scheduled display loop
	 */
	public void resetTimer() {
		beeperHandle.cancel(true);
	}
	
	/**
	 * Enables the safety of the vending machine
	 */
	public void enableSafety() {
		vm.enableSafety();
		event = "Safety enabled!";
		log(event);
		vm.getLock().lock();//Locks the vending machine when safety is on
	}
	
	/**
	 * Disables the safety of the vending machine
	 */
	public void disableSafety() {
		vm.disableSafety();
		event = "Safety disabled!";
		log(event);
		vm.getLock().unlock(); //Unlocks the vending machine
	}
	
	/**
	 * @return the ConfigPanelLogic object
	 */
	public ConfigPanelLogic getConfigPanelLogic() {
		return cpl;
	}
	
	/**
	 * @return the current event
	 * Useful for printing things to a log file or do display
	 */
	public String getEvent() {
		return event;
	}
	
	/**
	 * Logs a line of string to the event log file
	 * @param line of string to be logged
	 */
	public void log(String line) {
		logger.log(line);
		this.event = line;
	}
	/**
	 * @return the currency type
	 */
	public String getCurrency() {
		return currency;
	}
	
	/**
	 * @param amount of credit to be directly added (for credit card/alternate forms of payment)
	 */
	public void addCredit(int amount) {
		userCredit += amount;
	}
	
	/**
	 * @return how much credit is in vending machine
	 */
	public int getCredit() {
		return userCredit;
	}
	
	/**
	 * @return the list of buttons
	 */
	public List<PushButton> getButtonList() {
		return buttonList;
	}
	
	/**
	 * Updates the new costs arrayList
	 * @param newCosts
	 */
	public void setCosts(List<Integer> newCosts) {
		costs = newCosts;
	}
	
	/**
	 * @return the current arrayList of pop costs
	 */
	public List<Integer> getCosts() {
		return costs;
	}
	
	/**
	 * Sets new pop names
	 * @param newPopNames
	 */
	public void setPopNames(List<String> newPopNames) {
		popNames = newPopNames;
	}
	
	/**
	 * @return the current arrayList of pop names
	 */
	public List<String> getPopNames() {
		return popNames;
	}
	
}
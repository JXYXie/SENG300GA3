/**********************************************************
 * SENG 300 Group Assignment 2
 * Main logic class
 * @author Xin Yan (Jack) Xie
 * @author Jacky Wu
 * @author Sara Strand
 * @author Jaskaran Sidhu
 * @author Siddharth Kataria
 * @author Steffen Gerdes
 * @author Sheldon Birch-Lucas
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

import org.lsmr.vending.hardware.*;

public class VendingMachineLogic {

	private VendingMachine vm; //Vending machine object
	private VendingListener vlistener; //listener objecy
	private ConfigPanelLogic cpl;
	
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
	
	public void buy(int btnIndex) throws DisabledException, CapacityExceededException, EmptyException {
		
		int cost = vm.getPopKindCost(btnIndex);
		
		if (cost > userCredit) { //not enough money
			event = "Insufficient credit: " + (cost - userCredit) + " cents short";
			display(event);
		} else { //enough money
			vm.getPopCanRack(btnIndex).dispensePopCan(); //dispenses the pop
			vm.getCoinReceptacle().storeCoins(); //store the coins
			userCredit -= cost; //update the new credit
			returnChange(); //return the remaining change if there is any
			displayCredit(); //display the credit or if credit is 0 display greeting message
		}
	}
	/******************************** Change Functions ********************************************/
	
	/**
	 * Returns the change (could be exact or not exact)
	 * Algorithm will always return the highest value coin when possible
	 * @throws DisabledException
	 * @throws CapacityExceededException
	 * @throws EmptyException 
	 */
	public void returnChange() throws DisabledException, CapacityExceededException, EmptyException{
		
		int coin;
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
			
			while ((userCredit >= coin) && (returnRack.size() > 0)) {
				returnRack.releaseCoin();
				addCredit(-coin);
			}
		}
		
		if (userCredit != 0) { //if there is still money left in the vending machine that means there is not enough change for the user
			vm.getExactChangeLight().activate();
		} else if (userCredit == 0 && vm.getExactChangeLight().isActive()) { //if the exact change light is already turned on
			vm.getExactChangeLight().deactivate(); //turn it off
		}
		
	}
	
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
	
	// Method that will be used to show what pop was vended
	public void notifyVendedGUI() {
		for(GUIListener listener : listeners) {
			listener.popVended();
		}
	}
	
	/******************************** Display Functions ********************************************/
	public void display(String event) {
		if (event != null) {
			vm.getDisplay().display(event);
			notifyMachineGUI(event); // Every time the display is changed, notify the GUI to update its display
			log("DISPLAY: " + event);
		}
	}
	/**
	 * Displays the hi greeting prompt every second for 5 seconds
	 * 
	 */
	public void displayHi() {
		
		String prompt = "Hi there!";
		final Runnable looper = new Runnable() {
			@Override
			public void run() {
				display(prompt);
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
	 * If the configuration panel is accessed
	 */
	public void configAccess() {
		cpl.initialize(); //TODO just this line for now, (will add more if needed once GUI is implemented?)
	}
	
	/**
	 * @return the current event
	 * Useful for printing things to a log file or do display
	 */
	public String getEvent() {
		return event;
	}
	
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
	 * @param amount of credit to be added
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
	 * @param newCosts
	 */
	public void setCosts(List<Integer> newCosts) {
		costs = newCosts;
	}
	
	public List<Integer> getCosts() {
		return costs;
	}
	
	public void setPopNames(List<String> newPopNames) {
		popNames = newPopNames;
	}
	
	public List<String> getPopNames() {
		return popNames;
	}
	
}
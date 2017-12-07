package ca.ucalgary.seng300.VendingMachineLogic;

import java.awt.*;
import java.awt.event.ActionListener;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.*;
import java.util.HashMap;

import org.lsmr.vending.Coin;
import org.lsmr.vending.hardware.*;

import ca.ucalgary.seng300.VendingMachineLogic.VendingMachineLogic;
import javafx.event.ActionEvent;

public class MyFrame extends JFrame {

	// attributes. ie) buttons,labels,text spaces
	private JTextField textField, vendedPop;
	private MyFrame aFrame;
	private JLabel picture1, exactChangeLight, outOfOrderLight;
	private TechJFrame configGUI;

	private JLabel label1;
	private JButton button, vendedButton, lock, unlock, loadCoins, loadPops, emptyCoins, configPanel;
	private JTextField[] coinCount, coinsReturned;
	private int[] numberOfCoins = new int[6];

	Dimension size;

	// makes a frame and instantiates/customizes all of its components/the
	// listener, self explanitory from attribute names
	// each grouping is separated for easier reading
	public void makeframe(VendingMachine vm, VendingMachineLogic vml) throws IOException {
		aFrame = new MyFrame();
		aFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		aFrame.setTitle("Clients Machine");
		configGUI = new TechJFrame(vm, vml);
		configGUI.setVisible(false);
		configGUI.setLocation(aFrame.getSize().width + 1245, aFrame.getSize().height+517);

		// display label, probably gonna remove this
		label1 = new JLabel("Display");
		aFrame.getContentPane().add(label1);
		size = label1.getPreferredSize();
		label1.setBounds(445, 175, size.width, size.height);
		aFrame.getContentPane().add(label1);

		// text box
		textField = new JTextField(10);
		size = textField.getPreferredSize();
		textField.setBounds(410, 195, size.width, size.height);
		textField.setText("Display");
		aFrame.getContentPane().add(textField);
		
		// A text field to represent what pop was vended based on the button
		// pressed
		vendedPop = new JTextField(10);
		size = vendedPop.getPreferredSize();
		vendedPop.setBounds(250, 690, size.width, size.height);
		vendedPop.setText("None");
		aFrame.getContentPane().add(vendedPop);
		
		// 5 text fields to represent what coins were returned to the buyer
		coinsReturned = new JTextField[5];
		int[] coinTypes = {200, 100, 25, 10, 5};
		for(int j = 0; j < coinTypes.length; j++) {
			coinsReturned[j] = new JTextField(15);
			size = coinsReturned[j].getPreferredSize();
			coinsReturned[j].setBounds(600, 700 + j*20, size.width, size.height);
			coinsReturned[j].setVisible(true);
			coinsReturned[j].setText("Empty");
			aFrame.getContentPane().add(coinsReturned[j]);
		}

		EventListener aListener = new EventListener(aFrame, textField, vendedPop, vm, coinsReturned, numberOfCoins);

		// A button to clear the coinReturn chute and also reset the display for
		// the next pop
		vendedButton = new JButton(new ImageIcon(ImageIO.read(getClass().getResourceAsStream("popreturnbutton.gif"))));
		vendedButton.setActionCommand("Vended");
		vendedButton.addActionListener(aListener);
		size = vendedButton.getPreferredSize();
		vendedButton.setBounds(350, 655, size.width, size.height);
		vendedButton.setBorder(BorderFactory.createEmptyBorder());
		vendedButton.setContentAreaFilled(false);
		aFrame.getContentPane().add(vendedButton);

		// A button to lock the vending machine and enable safety for the technician
		lock = new JButton(new ImageIcon(ImageIO.read(getClass().getResourceAsStream("lock.gif"))));
		lock.setActionCommand("Lock");
		lock.addActionListener(aListener);
		size = lock.getPreferredSize();
		lock.setBounds(900, 100, size.width, size.height);
		lock.setBorder(BorderFactory.createEmptyBorder());
		lock.setContentAreaFilled(false);
		aFrame.getContentPane().add(lock);
		
		// A button to unlock the vending machine and disable the safety when the technician is done
		unlock = new JButton(new ImageIcon(ImageIO.read(getClass().getResourceAsStream("unlock.gif"))));
		unlock.setActionCommand("Unlock");
		unlock.addActionListener(aListener);
		size = unlock.getPreferredSize();
		unlock.setBounds(1050, 100, size.width, size.height);
		unlock.setBorder(BorderFactory.createEmptyBorder());
		unlock.setContentAreaFilled(false);
		unlock.setVisible(false);
		aFrame.getContentPane().add(unlock);
		
		// A method to load coins into the vending machine
		loadCoins = new JButton(new ImageIcon(ImageIO.read(getClass().getResourceAsStream("coinpile.gif"))));
		loadCoins.setActionCommand("LoadCoins");
		loadCoins.addActionListener(aListener);
		size = loadCoins.getPreferredSize();
		loadCoins.setBounds(900, 300, size.width, size.height);
		loadCoins.setBorder(BorderFactory.createEmptyBorder());
		loadCoins.setContentAreaFilled(false);
		loadCoins.setVisible(false);
		aFrame.getContentPane().add(loadCoins);
		
		// A method to load pops into the vending machine
		loadPops = new JButton(new ImageIcon(ImageIO.read(getClass().getResourceAsStream("popcanpile.gif"))));
		loadPops.setActionCommand("LoadPops");
		loadPops.addActionListener(aListener);
		size = loadPops.getPreferredSize();
		loadPops.setBounds(1050, 300, size.width, size.height);
		loadPops.setBorder(BorderFactory.createEmptyBorder());
		loadPops.setContentAreaFilled(false);
		loadPops.setVisible(false);
		aFrame.getContentPane().add(loadPops);
		
		// A method to unload the coin racks from the vending machine
		emptyCoins = new JButton(new ImageIcon(ImageIO.read(getClass().getResourceAsStream("unloadracks.gif"))));
		emptyCoins.setActionCommand("EmptyCoins");
		emptyCoins.addActionListener(aListener);
		size = emptyCoins.getPreferredSize();
		emptyCoins.setBounds(900, 500, size.width, size.height);
		emptyCoins.setBorder(BorderFactory.createEmptyBorder());
		emptyCoins.setContentAreaFilled(false);
		emptyCoins.setVisible(false);
		aFrame.getContentPane().add(emptyCoins);
		
		//button for calling the config panel GUI
		/**MMMM**/
		configPanel = new JButton(new ImageIcon(ImageIO.read(getClass().getResourceAsStream("configpb.gif"))));
		configPanel.setActionCommand("configPanel");
		configPanel.addActionListener(aListener);
		size = configPanel.getPreferredSize();
		configPanel.setBounds(1050, 500, size.width, size.height);
		configPanel.setBorder(BorderFactory.createEmptyBorder());
		configPanel.setContentAreaFilled(false);
		configPanel.setVisible(false);
		aFrame.getContentPane().add(configPanel);

		
		aListener.addLockUnlock(lock, unlock, loadCoins, loadPops, emptyCoins, configPanel, configGUI);

		// buttons corresponding to pop cans
		int i;
		for (i = 0; i < 6; i++) {
			button = new JButton(new ImageIcon(ImageIO.read(getClass().getResourceAsStream("popbutton.gif"))));
			button.setActionCommand("Button" + Integer.toString(i));
			button.addActionListener(aListener);
			size = button.getPreferredSize();
			int ypos = 70;
			if (i > 2) {
				ypos = 260;
			}
			int xpos = 52 + (115 * (i % 3));
			button.setBounds(xpos, ypos, size.width, size.height);
			button.setBorder(BorderFactory.createEmptyBorder());
			button.setContentAreaFilled(false);
			aFrame.getContentPane().add(button);
		}

		// change that can be entered, along with an invalid coin(washer)
		coinCount = new JTextField[5];
		String imageStrings[] = { "toonie", "loonie", "quarter", "dime", "nickel", "washer" };
		int[] coinAmount = new int[5];
		int index = 0;
		for (String s : imageStrings) {
			button = new JButton(new ImageIcon(ImageIO.read(getClass().getResourceAsStream(s + ".gif"))));
			button.setActionCommand(s);
			button.addActionListener(aListener);
			size = button.getPreferredSize();
			button.setBounds(600, 20 + (75 * index), size.width, size.height);
			button.setBorder(BorderFactory.createEmptyBorder());
			button.setContentAreaFilled(false);
			aFrame.getContentPane().add(button);
			// Placing a text field beside each coin value to show how much of
			// each denomination is
			// currently in the machine
			if (index < 5) {
				coinCount[index] = new JTextField(10);
				size = coinCount[index].getPreferredSize();
				coinCount[index].setBounds(700, 40 + (75 * index), size.width, size.height);
				coinCount[index].setText(String.valueOf(coinAmount[index]) + " total");
				aFrame.getContentPane().add(coinCount[index]);
			}
			index++;
		}
		setCoinCount(vm);	

		// An icon that represents the exact change light
		exactChangeLight = new JLabel();
		exactChangeLight.setIcon(new ImageIcon(ImageIO.read(getClass().getResourceAsStream("light.gif"))));
		size = exactChangeLight.getPreferredSize();
		exactChangeLight.setBounds(430, 230, size.width, size.height);
		exactChangeLight.setBorder(BorderFactory.createEmptyBorder());
		exactChangeLight.setVisible(vm.getExactChangeLight().isActive());
		aFrame.getContentPane().add(exactChangeLight);

		// An icon that represents the out of order light
		outOfOrderLight = new JLabel();
		outOfOrderLight.setIcon(new ImageIcon(ImageIO.read(getClass().getResourceAsStream("light.gif"))));
		size = outOfOrderLight.getPreferredSize();
		outOfOrderLight.setBounds(430, 265, size.width, size.height);
		outOfOrderLight.setBorder(BorderFactory.createEmptyBorder());
		outOfOrderLight.setVisible(vm.getOutOfOrderLight().isActive());
		aFrame.getContentPane().add(outOfOrderLight);

		// the vending machine image
		picture1 = new JLabel();
		picture1.setIcon(new ImageIcon(ImageIO.read(getClass().getResourceAsStream("candycane.gif"))));
		picture1.setPreferredSize(new Dimension(1250, 870));

		// dont add anything after this picture, it determines the size because
		// of "pack" being used
		aFrame.getContentPane().add(picture1);

		aFrame.pack();
		aFrame.setVisible(true);
	}

	/**
	 * A method to set the display message displayed by the vending machine GUI
	 * 
	 * @param message
	 *            is the message to be displayed by the vending machine GUI
	 */
	public void setMessage(String message) {
		textField.setText(message);
	}

	/**
	 * A method to get the count of the coins currently in the vending machine
	 * 
	 * @param vm
	 *            is the vending machine to get the counts from
	 */
	public void setCoinCount(VendingMachine vm) {
		int[] coinValues = { 200, 100, 25, 10, 5 };
		int[] coinAmount = new int[5];
		for (int j = 0; j < 5; j++) {
			coinAmount[j] = vm.getCoinRackForCoinKind(coinValues[j]).size();
			coinCount[j].setText(String.valueOf(coinAmount[j]) + " total");
		}
	}

	/**
	 * A method to get the pop that was vended by the vending machine
	 * 
	 * @param popName
	 *            is the name of the pop that was vended
	 */
	public void vendedPop(String popName) {
		vendedPop.setText(popName);
	}

	/**
	 * A method to set the exact change light on the vending machine
	 * 
	 * @param set
	 *            is the boolean value that will change the visibility of the
	 *            exact change light on the vending machine GUI
	 */
	public void setExactChangeLight(boolean set) {
		exactChangeLight.setVisible(set);
	}

	/**
	 * A method to set the out of order light on the vending machine
	 * 
	 * @param set
	 *            is the boolean value that will change the visibility of the
	 *            out of order light on the vending machine GUI
	 */
	public void setOutOfOrderLight(boolean set) {
		outOfOrderLight.setVisible(set);
	}
	
	/**
	 * A method used to show what coins were returned to the buyer
	 * @param coins is the array of coins to get which coins were returned
	 */
	public void setCoinsReturnedCount(Coin[] coins) {
		String[] coinTypes = {"toonie", "loonie", "quarter", "dime", "nickel"};
		for(int i = 0; i < coins.length; i++) {
			int x = coins[i].getValue();
			System.out.println(coins[i].getValue());
			if (x == 200) {
				numberOfCoins[0] += 1;
			}
			if(x == 100) {
				System.out.println("This happened");
				numberOfCoins[1] += 1;
			}
			if(x == 25) {
				numberOfCoins[2] += 1;
			}
			if(x == 10) {
				numberOfCoins[3] += 1;
			}
			if(x == 5) {
				numberOfCoins[4] += 1;
			}
		}
		for(int j = 0; j < coinTypes.length; j++) {
			if (numberOfCoins[j] == 1) {
				coinsReturned[j].setText(numberOfCoins[j] + " " + coinTypes[j] + " returned.");
			} else {
				coinsReturned[j].setText(numberOfCoins[j] + " " + coinTypes[j] + "s returned.");
			}
		}
	}
}

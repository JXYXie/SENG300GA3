package ca.ucalgary.seng300.VendingMachineLogic;

import java.awt.*;
import java.awt.event.ActionListener;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.*;
import java.util.HashMap;

import org.lsmr.vending.hardware.*;

import ca.ucalgary.seng300.VendingMachineLogic.VendingMachineLogic;
import javafx.event.ActionEvent;

public class MyFrame extends JFrame
{

    //attributes. ie) buttons,labels,text spaces 
    private JTextField textField, vendedPop;
    private MyFrame aFrame;
    private JLabel picture1, exactChangeLight, outOfOrderLight;

    private JLabel label1;
    private JButton button, vendedButton;
    private JTextField[] coinCount;

    Dimension size;
    
    //makes a frame and instantiates/customizes all of its components/the listener, self explanitory from attribute names
    //each grouping is separated for easier reading 
    public void makeframe(VendingMachine vm) throws IOException
    {
	aFrame = new MyFrame ();
	aFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	aFrame.setTitle("Clients Machine");
	
	//display label, probably gonna remove this
	label1 = new JLabel("Display");
	aFrame.getContentPane().add(label1);
	size = label1.getPreferredSize();
    label1.setBounds(445, 175, size.width, size.height);
    aFrame.getContentPane().add(label1);

    //text box
	textField = new JTextField(10);
	size = textField.getPreferredSize();
    textField.setBounds(410, 195, size.width, size.height);
    textField.setText("Display");
    aFrame.getContentPane().add(textField);
    
    // A text field to represent what pop was vended based on the button pressed
	vendedPop = new JTextField(10);
	size = vendedPop.getPreferredSize();
	vendedPop.setBounds(250, 690, size.width, size.height);
	vendedPop.setText("None");
    aFrame.getContentPane().add(vendedPop);
    
    EventListener aListener = new EventListener(aFrame,textField, vendedPop, vm);
    
    // A button to clear the coinReturn chute and also reset the display for the next pop
    vendedButton = new JButton(new ImageIcon(ImageIO.read(getClass().getResourceAsStream("popbutton.gif"))));
    vendedButton.setActionCommand("Vended");
    vendedButton.addActionListener(aListener);
    size = vendedButton.getPreferredSize();
    vendedButton.setBounds(350, 655, size.width, size.height);
    vendedButton.setBorder(BorderFactory.createEmptyBorder());
    vendedButton.setContentAreaFilled(false);
    aFrame.getContentPane().add(vendedButton);
	
	//buttons corresponding to pop cans
	int i;
	for(i = 0;i < 6; i++){	
	button = new JButton(new ImageIcon(ImageIO.read(getClass().getResourceAsStream("popbutton.gif"))));
	button.setActionCommand("Button" + Integer.toString(i));
	button.addActionListener(aListener);
	size = button.getPreferredSize();
	int ypos = 70;
	if(i > 2){
		ypos = 260;}
	int xpos = 52+(115*(i % 3));	
	button.setBounds(xpos, ypos, size.width, size.height);
	button.setBorder(BorderFactory.createEmptyBorder());
	button.setContentAreaFilled(false);
	aFrame.getContentPane().add(button);}
	
	//change that can be entered, along with an invalid coin(washer)
	coinCount = new JTextField[5];
	String imageStrings[] = {"toonie", "loonie", "quarter", "dime", "nickel", "washer"};
	int[] coinAmount = new int[5];
	int index = 0;
	for (String s: imageStrings){ 	
		button = new JButton(new ImageIcon(ImageIO.read(getClass().getResourceAsStream(s + ".gif"))));
		button.setActionCommand(s);
		button.addActionListener(aListener);
		size = button.getPreferredSize();
		button.setBounds(600, 20+(75*index), size.width, size.height);
		button.setBorder(BorderFactory.createEmptyBorder());
		button.setContentAreaFilled(false);
		aFrame.getContentPane().add(button);
		// Placing a text field beside each coin value to show how much of each denomination is
		// currently in the machine
		if (index < 5){
			coinCount[index] = new JTextField(10);
			size = coinCount[index].getPreferredSize();
			coinCount[index].setBounds(700, 40+(75*index), size.width, size.height);
			coinCount[index].setText(String.valueOf(coinAmount[index]) +" total");
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
	
	
	//the vending machine image
	picture1 = new JLabel();
    picture1.setIcon(new ImageIcon(ImageIO.read(getClass().getResourceAsStream("candycane.gif"))));
    picture1.setBounds(0, 0, 1200, 870);
    picture1.setPreferredSize(new Dimension(1200, 870));
    
    //dont add anything after this picture, it determines the size because of "pack" being used
    aFrame.getContentPane().add(picture1);
    
    aFrame.pack();
	aFrame.setVisible(true);
    }
    
    /**
     * A method to set the display message displayed by the vending machine GUI
     * @param message is the message to be displayed by the vending machine GUI
     */
    public void setMessage(String message) {
    	textField.setText(message);
    }
    
    /**
     * A method to get the count of the coins currently in the vending machine
     * @param vm is the vending machine to get the counts from
     */
    public void setCoinCount(VendingMachine vm) {
    	int[] coinValues = {200, 100, 25, 10, 5};
    	int[] coinAmount = new int[5];
    	for(int j = 0; j < 5; j++) {
    		coinAmount[j] = vm.getCoinRackForCoinKind(coinValues[j]).size();
			coinCount[j].setText(String.valueOf(coinAmount[j]) +" total");
    	}
    }
    
    /**
     * A method to get the pop that was vended by the vending machine
     * @param popName is the name of the pop that was vended
     */
    public void vendedPop(String popName) {
    	vendedPop.setText(popName);
    }
    
    /**
     * A method to set the exact change light on the vending machine
     * @param set is the boolean value that will change the visibility of the exact change light on
     * the vending machine GUI
     */
    public void setExactChangeLight(boolean set) {
    	exactChangeLight.setVisible(set);
    }
    
    /**
     * A method to set the out of order light on the vending machine
     * @param set is the boolean value that will change the visibility of the out of order light on
     * the vending machine GUI
     */
    public void setOutOfOrderLight(boolean set) {
    	outOfOrderLight.setVisible(set);
    }
}
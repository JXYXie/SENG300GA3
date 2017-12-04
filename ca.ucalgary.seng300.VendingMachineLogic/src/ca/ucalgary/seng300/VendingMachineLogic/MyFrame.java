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
    private JLabel picture1;

    private JLabel label1;
    private HashMap<Integer, JButton> maps = new HashMap<Integer, JButton>();
    private JButton button, vendedButton;
    private JTextField[] coinCount;
    //private JButton[] vended;
    private int buttonPressed = 1;

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
    
	vendedPop = new JTextField(10);
	size = vendedPop.getPreferredSize();
	vendedPop.setBounds(250, 690, size.width, size.height);
	vendedPop.setText("None");
    aFrame.getContentPane().add(vendedPop);
    
    EventListener aListener = new EventListener(aFrame,textField, vendedPop, vm);
    
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
//	int[] coinValues = {200, 100, 25, 10, 5};
	int[] coinAmount = new int[5];
//	for(int j = 0; j < 5; j++) {
//		coinAmount[j] = vm.getCoinRackForCoinKind(coinValues[j]).size();
//	}
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
    
    public void deactivateButton(int button) {
    	maps.get(button).setVisible(false);
    	aFrame.revalidate();
    }
    
    public void setButtonPress(int button) {
    	buttonPressed = button;
    }
    
    public void setMessage(String message) {
    	textField.setText(message);
    }
    
    public void setCoinCount(VendingMachine vm) {
    	String coinStrings[] = {"toonie", "loonie", "quarter", "dime", "nickel"};
    	int[] coinValues = {200, 100, 25, 10, 5};
    	int[] coinAmount = new int[5];
    	for(int j = 0; j < 5; j++) {
    		coinAmount[j] = vm.getCoinRackForCoinKind(coinValues[j]).size();
			coinCount[j].setText(String.valueOf(coinAmount[j]) +" total");
    	}
    }
    
    public int getButtonPress() {
    	return buttonPressed;
    }
    
    public void vendedPop(String popName) {
    	vendedPop.setText(popName);
    }
}
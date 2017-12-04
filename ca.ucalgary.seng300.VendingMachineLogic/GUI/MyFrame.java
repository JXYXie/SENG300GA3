
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
    private JTextField textField;
    private MyFrame aFrame;
    private JLabel picture1;
    private JLabel canBought;

    private JLabel label1;
    private HashMap<Integer, JButton> maps = new HashMap<Integer, JButton>();
    private JButton button;
    //private JButton[] vended;
    private boolean show = false;
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
    
    EventListener aListener = new EventListener(aFrame,textField, vm);	
	
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
	
	// This method will show which can of soda was purchased, not sure how to get it to work yet
//	vended = new JButton[6];
//	
//	for(int j = 0;j < 6; j++){	
//	vended[j] = new JButton(new ImageIcon(ImageIO.read(getClass().getResourceAsStream("vendedCan" + String.valueOf(j + 1) + ".gif"))));
//	maps.put(j, vended[j]);
//	vended[j].setActionCommand("Vended" + Integer.toString(j));
//	vended[j].addActionListener(aListener);
//	size = vended[j].getPreferredSize();
//	int ypos = 615;
//	if (j > 2) {
//		ypos = 700;
//	}
//	int xpos = 70+(125*(j % 3));	
//	vended[j].setBounds(xpos, ypos, size.width, size.height);
//	vended[j].setBorder(BorderFactory.createEmptyBorder());
//	vended[j].setContentAreaFilled(false);
//	vended[j].setVisible(false);
//	aFrame.getContentPane().add(vended[j]);}
	
	
	//change that can be entered, along with an invalid coin(washer)
	String imageStrings[] = {"toonie", "loonie", "quarter", "dime", "nickel", "washer"};
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
		index++;
	}
	
	
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
    
    public int getButtonPress() {
    	return buttonPressed;
    }
    
    public void vendedPop() throws IOException {
    	// TODO: later
    }
}

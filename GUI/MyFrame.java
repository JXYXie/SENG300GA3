
import java.awt.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;

public class MyFrame extends JFrame
{

    //attributes. ie) buttons,labels,text spaces 
    private JTextField textField;
    private JFrame aFrame;
    private JLabel picture1;

    private JLabel label1;
    private JButton button;

    Dimension size;

    //makes a frame and instantiates/customizes all of its components/the listener, self explanitory from attribute names
    //each grouping is separated for easier reading 
    public void makeframe() throws IOException
    {	
	aFrame = new JFrame ();
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
    aFrame.getContentPane().add(textField);
    
    EventListener aListener = new EventListener(aFrame,textField);	
	
	//i didnt feel like making an array of buttons so someone should do that
	//obviously you can implement this to add however many buttons, i added 9 for testing
	//goodluck preston
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

	//dispensed pop can
	button = new JButton(new ImageIcon(ImageIO.read(getClass().getResourceAsStream("vendedCan.gif"))));
	button.setActionCommand("vendedCan");
	button.addActionListener(aListener);
	size = button.getPreferredSize();
	button.setBounds(600, 600, size.width, size.height);
	button.setBorder(BorderFactory.createEmptyBorder());
	button.setContentAreaFilled(false);
	button.setVisible(false);
	aFrame.getContentPane().add(button);
	
	
	String imageStrings[] = {"toonie", "loonie", "quarter", "dime", "nickel", "washer"};
	int index = 0;
	for (String s: imageStrings){ 	
		button = new JButton(new ImageIcon(ImageIO.read(getClass().getResourceAsStream(s + ".gif"))));
		button.setActionCommand(s);
		button.addActionListener(aListener);
		size = button.getPreferredSize();
		button.setBounds(600, 20+(100*index), size.width, size.height);
		button.setBorder(BorderFactory.createEmptyBorder());
		button.setContentAreaFilled(false);
		aFrame.getContentPane().add(button);
		index++;
	}
	
	
	//the vending machine image
	picture1 = new JLabel();
    picture1.setIcon(new ImageIcon(ImageIO.read(getClass().getResourceAsStream("v3.gif"))));
    picture1.setBounds(0, 0, 1200, 870);
    picture1.setPreferredSize(new Dimension(1200, 870));
    
    
    
    
    //dont add anything after this picture, it determines the size because of "pack" being used
    aFrame.getContentPane().add(picture1);
    
    aFrame.pack();
	aFrame.setVisible(true);
    }
}


import java.awt.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;

public class MyFrame extends JFrame
{

    //attributes. ie) buttons,labels,text spaces 
    private JTextField textField;
    private JTextArea textArea;
    private JFrame aFrame;
    private JLabel picture1;

    private JLabel label1;
    private JButton button1;
    private JButton button0;
    Dimension size;

    //makes a frame and instantiates/customizes all of its components/the listener, self explanitory from attribute names
    //each grouping is seperated for easier reading 
    public void makeframe() throws IOException
    {	
	aFrame = new JFrame ();
	aFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	aFrame.setTitle("Clients Machine");
	
	//display label, probably gonna remove this
	label1 = new JLabel("Display");
	//addComponent(aFrame, label1, 500, 300, 20, 5);
	aFrame.getContentPane().add(label1);
	size = label1.getPreferredSize();
    label1.setBounds(445, 175, size.width, size.height);
    aFrame.getContentPane().add(label1);

    //text box
	textField = new JTextField(10);
	size = textField.getPreferredSize();
    textField.setBounds(410, 195, size.width, size.height);
    aFrame.getContentPane().add(textField);
	
	//i didnt feel like making an array of buttons so someone should do that
	//obviously you can implement this to add however many buttons, i added 9 for testing
	//goodluck preston
	int i;
	for(i = 0;i < 9; i++){
	MyButtonListener aListener = new MyButtonListener(aFrame,textField,textArea);	
	button1 = new JButton(new ImageIcon(ImageIO.read(getClass().getResourceAsStream("bottlecap.gif"))));
	button1.setActionCommand(Integer.toString(i));
	button1.addActionListener(aListener);
	size = button1.getPreferredSize();
	int ypos = 115;
	int xpos = 50+(i*42);
	if(i >= 8){
		ypos= 230;
		xpos= 45;
	}
	
	button1.setBounds(xpos, ypos, size.width, size.height);
	button1.setBorder(BorderFactory.createEmptyBorder());
	button1.setContentAreaFilled(false);
	aFrame.getContentPane().add(button1);}

	//this is the loonie, ill automate/ add other coins and a washed when i feel like it
	MyButtonListener aListener = new MyButtonListener(aFrame,textField,textArea);	
	button0 = new JButton(new ImageIcon(ImageIO.read(getClass().getResourceAsStream("loonie.gif"))));
	button0.setActionCommand("loonie");
	button0.addActionListener(aListener);
	size = button0.getPreferredSize();
	button0.setBounds(600, 20, size.width, size.height);
	button0.setBorder(BorderFactory.createEmptyBorder());
	button0.setContentAreaFilled(false);
	aFrame.getContentPane().add(button0);
	
	
	//the vending machine image
	picture1 = new JLabel();
    picture1.setIcon(new ImageIcon(ImageIO.read(getClass().getResourceAsStream("vending.gif"))));
    picture1.setBounds(0, 0, 1200, 870);
    picture1.setPreferredSize(new Dimension(1200, 870));
    
    
    
    
    //dont add anything after this picture, it determines the size
    aFrame.getContentPane().add(picture1);
    
    aFrame.pack();
    

    
	aFrame.setVisible(true);
    }
}

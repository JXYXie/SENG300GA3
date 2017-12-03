package ca.ucalgary.seng300.TechPanelGUI;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.lsmr.vending.Coin;
import org.lsmr.vending.hardware.VendingMachine;

import ca.ucalgary.seng300.VendingMachineLogic.VendingMachineLogic;

import java.awt.GridLayout;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import java.awt.Component;
import javax.swing.JTextPane;
import javax.swing.AbstractAction;
import javax.swing.AbstractButton;
import javax.swing.Action;

public class TechJFrame extends JFrame {

	private JPanel contentPane;
	private final Action action = new SwingAction();
	private String textView = "";
	private String kpPrice = "";
	private int popIndex = -1;
	private String popName = "";
	private VendingMachineLogic vml;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VendingMachineLogic vml = setUp();
					TechJFrame frame = new TechJFrame(vml);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	

	/**
	 * Create the frame.
	 */
	public TechJFrame(VendingMachineLogic vml) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 640, 480);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		this.vml = vml;
		
		JTextPane txtpnPleaseSelectPop = new JTextPane();
		txtpnPleaseSelectPop.setEditable(false);
		
		JButton selectpop_1 = new JButton("Pop 1");
		selectpop_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
		
				textView = vml.getPopNames().get(0) + " :";
				popIndex = 0;
				txtpnPleaseSelectPop.setText(textView);
			}
		});
		
		JButton selectpop_2 = new JButton("Pop 2");
		selectpop_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textView = vml.getPopNames().get(1) + " :";
				popIndex = 1;
				txtpnPleaseSelectPop.setText(textView);
			}
		});
		
		JButton selectpop_3 = new JButton("Pop 3");
		selectpop_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textView = vml.getPopNames().get(2) + " :";
				popIndex = 2;
				txtpnPleaseSelectPop.setText(textView);;
			}
		});
		
		JButton selectpop_4 = new JButton("Pop 4");
		selectpop_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textView = vml.getPopNames().get(3) + " :";
				popIndex = 3;
				txtpnPleaseSelectPop.setText(textView);
			}
		});
		
		JButton selectpop_5 = new JButton("Pop 5");
		selectpop_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textView = vml.getPopNames().get(4) + " :";
				popIndex = 4;
				txtpnPleaseSelectPop.setText(textView);
			}
		});
		
		JButton selectpop_6 = new JButton("Pop 6");
		selectpop_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textView = vml.getPopNames().get(5) + " :";
				popIndex = 5;
				txtpnPleaseSelectPop.setText(textView);
			}
		});
		
		JButton keypad_9 = new JButton("9");
		keypad_9.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				kpPrice += "9";
				txtpnPleaseSelectPop.setText(textView + " " + kpPrice);
			}
		});
		
		JButton keypad_8 = new JButton("8");
		keypad_8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				kpPrice += "8";
				txtpnPleaseSelectPop.setText(textView + " " + kpPrice);
			}
		});
		
		JButton keypad_7 = new JButton("7");
		keypad_7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				kpPrice += "7";
				txtpnPleaseSelectPop.setText(textView + " " + kpPrice);
			}
		});
		
		JButton keypad_6 = new JButton("6");
		keypad_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				kpPrice += "6";
				txtpnPleaseSelectPop.setText(textView + " " + kpPrice);
			}
		});
		
		JButton keypad_4 = new JButton("5");
		keypad_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				kpPrice += "5";
				txtpnPleaseSelectPop.setText(textView + " " + kpPrice);
			}
		});
		
		JButton button_5 = new JButton("4");
		button_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				kpPrice += "4";
				txtpnPleaseSelectPop.setText(textView + " " + kpPrice);
			}
		});
		
		JButton keypad_3 = new JButton("3");
		keypad_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				kpPrice += "3";
				txtpnPleaseSelectPop.setText(textView + " " + kpPrice);
			}
		});
		
		JButton keypad_2 = new JButton("2");
		keypad_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				kpPrice += "2";
				txtpnPleaseSelectPop.setText(textView + " " + kpPrice);
			}
		});
		
		JButton button_8 = new JButton("1");
		button_8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				kpPrice += "1";
				txtpnPleaseSelectPop.setText(textView + " " + kpPrice);
				//txtpnPleaseSelectPop.
			}
		});
		
		JButton keypad_1 = new JButton("0");
		keypad_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				kpPrice += "0";
			}
		});
		
		JButton btnEnter = new JButton("ENTER");
		btnEnter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int newValue = Integer.parseInt(kpPrice);
				
				if((newValue % 10 == 0) | (newValue % 5 == 0))
				{
					//is divisible by either 5 or 10
					List<Integer> currentCost = vml.getCosts();
					currentCost.set(popIndex, newValue);
					vml.setCosts(currentCost);
					System.out.println(vml.getCosts().get(popIndex));
				}
				else
				{
					txtpnPleaseSelectPop.setText("Pop price has to end in either 5 or 0. Please try again.");
					//Reset everything
					kpPrice = "";
					popIndex = -1;
					textView = "";
					
				}
				
			}
		});
		
		
		txtpnPleaseSelectPop.setText("Please select pop product to change price");
		
		JButton btnReset = new JButton("RESET");
		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				kpPrice = "";
				popIndex = -1;
				textView = "";
				txtpnPleaseSelectPop.setText("Please select pop product to change price");
			}
		});
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(99)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(txtpnPleaseSelectPop, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
							.addGroup(gl_contentPane.createSequentialGroup()
								.addComponent(keypad_6)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(keypad_4)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(button_5))
							.addGroup(gl_contentPane.createSequentialGroup()
								.addComponent(keypad_9)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(keypad_8)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(keypad_7))
							.addGroup(gl_contentPane.createSequentialGroup()
								.addComponent(selectpop_1)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(selectpop_2)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(selectpop_3)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(selectpop_4)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(selectpop_5)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(selectpop_6))
							.addGroup(gl_contentPane.createSequentialGroup()
								.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
									.addGroup(gl_contentPane.createSequentialGroup()
										.addComponent(keypad_3)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(keypad_2))
									.addGroup(gl_contentPane.createSequentialGroup()
										.addComponent(keypad_1)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(btnEnter, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
								.addPreferredGap(ComponentPlacement.RELATED)
								.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
									.addComponent(btnReset, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
									.addComponent(button_8, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
								.addGap(2))))
					.addContainerGap(131, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(83)
					.addComponent(txtpnPleaseSelectPop, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(73)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(selectpop_1)
						.addComponent(selectpop_2)
						.addComponent(selectpop_3)
						.addComponent(selectpop_4)
						.addComponent(selectpop_5)
						.addComponent(selectpop_6))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(keypad_9)
						.addComponent(keypad_8)
						.addComponent(keypad_7))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(keypad_6)
						.addComponent(keypad_4)
						.addComponent(button_5))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(keypad_3)
						.addComponent(keypad_2)
						.addComponent(button_8))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(keypad_1)
						.addComponent(btnEnter)
						.addComponent(btnReset))
					.addContainerGap(111, Short.MAX_VALUE))
		);
		gl_contentPane.linkSize(SwingConstants.HORIZONTAL, new Component[] {keypad_9, keypad_8, keypad_7, keypad_6, keypad_4, button_5, keypad_3, keypad_2, button_8, keypad_1, btnEnter});
		contentPane.setLayout(gl_contentPane);
	}
	private class SwingAction extends AbstractAction {
		public SwingAction() {
			putValue(NAME, "SwingAction");
			putValue(SHORT_DESCRIPTION, "Some short description");
		}
		public void actionPerformed(ActionEvent e) {
		}
	}
	
	


/**
 * Setup for testing of gui
 */

 static VendingMachineLogic setUp()
{

	int[] coinKinds = {5, 10, 25, 100, 200}; //Nickels, dimes, quarters, loonies, toonies (all values in cents)
	int btnCount = 6; //6 types of pop
	int coinRackCapacity = 15;
	int popRackCapacity = 10;
	int receptacleCapacity = 4;
	int deliveryChuteCapacity = 10;
	int coinReturnCapacity = 8;
	
	VendingMachine vm = new VendingMachine(coinKinds, btnCount, coinRackCapacity, popRackCapacity, receptacleCapacity, deliveryChuteCapacity, coinReturnCapacity);
	VendingMachineLogic vml = new VendingMachineLogic(vm);
	
	List<String> popNames = new ArrayList<String>(); //List of pop names
	
	popNames.add("Water");
	popNames.add("Pepsi");
	popNames.add("Sprite");
	popNames.add("Mountain Dew");
	popNames.add("Orange Crush");
	popNames.add("Gatorade");
	
	List<Integer> costs = new ArrayList<Integer>(); //List of pop costs
	
	for (int i = 0; i < popNames.size(); i++ ) {
		costs.add(250); //everything costs 2.50
	}
	
	vml.setPopNames(popNames);
	vml.setCosts(costs);
	vm.configure(popNames, costs);
	vm.loadPopCans(5, 5, 5, 5, 5, 5); //Starts with 5 of each kind of pop
	
	//load the coin racks 
	for(int i = 0; i < vm.getNumberOfCoinRacks(); i++) {
		Coin value;
		if (i == 0) 
			value = new Coin(5);
		else if (i == 1)
			value = new Coin(10);
		else if (i == 2)
			value = new Coin(25);
		else if (i == 3)
			value = new Coin(100);
		else
			value = new Coin(200);
		vm.getCoinRack(i).load(value, value, value, value);	//load 4 of every kind of coin to start with
	}
	
	return vml;
}
 }

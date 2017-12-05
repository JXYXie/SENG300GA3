/*****************************************************
 * Custom JFrame class
 * Currently has a JPanel that acts as the internal display
 *and 12 buttons (num 0 to 9, enter button and reset button)
 * @author Sheldon Birch-Lucas
 * @author Jacky Wu
 * @author Xin Yan (Jack) Xie
 ****************************************************/
package ca.ucalgary.seng300.VendingMachineLogic;

import java.awt.Component;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractAction;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import org.lsmr.vending.hardware.VendingMachine;

public class TechJFrame extends JFrame {

	private JPanel contentPane;
	private ConfigPanelLogic cpl;
	private JTextPane textPane;

	/**
	 * Creates the frame.
	 */
	public TechJFrame(VendingMachine vm, VendingMachineLogic vml) {
		
		cpl = vml.getConfigPanelLogic();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 640, 480);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		textPane = new JTextPane(); //Instantiates the textpane
		textPane.setEditable(false);
		textPane.setText("Pop index to modify:"); //Default display message
		this.setTitle("Configuration Panel");
		
		JButton keypad_9 = new JButton("9");
		keypad_9.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cpl.pressButton(9);
				textPane.setText(cpl.getDisplayMessage());
			}
		});
		
		JButton keypad_8 = new JButton("8");
		keypad_8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cpl.pressButton(8);
				textPane.setText(cpl.getDisplayMessage());
			}
		});
		
		JButton keypad_7 = new JButton("7");
		keypad_7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cpl.pressButton(7);
				textPane.setText(cpl.getDisplayMessage());
			}
		});
		
		JButton keypad_6 = new JButton("6");
		keypad_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cpl.pressButton(6);
				textPane.setText(cpl.getDisplayMessage());
			}
		});
		
		JButton keypad_5 = new JButton("5");
		keypad_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cpl.pressButton(5);
				textPane.setText(cpl.getDisplayMessage());
			}
		});
		
		JButton keypad_4 = new JButton("4");
		keypad_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cpl.pressButton(4);
				textPane.setText(cpl.getDisplayMessage());
			}
		});
		
		JButton keypad_3 = new JButton("3");
		keypad_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cpl.pressButton(3);
				textPane.setText(cpl.getDisplayMessage());
			}
		});
		
		JButton keypad_2 = new JButton("2");
		keypad_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cpl.pressButton(2);
				textPane.setText(cpl.getDisplayMessage());
			}
		});
		
		JButton keypad_1 = new JButton("1");
		keypad_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cpl.pressButton(1);
				textPane.setText(cpl.getDisplayMessage());
			}
		});
		
		JButton keypad_0 = new JButton("0");
		keypad_0.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				cpl.pressButton(0);
				textPane.setText(cpl.getDisplayMessage());
			}
		});
		
		JButton btnEnter = new JButton("ENTER");
		btnEnter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cpl.pressEnterButton();
				textPane.setText(cpl.getDisplayMessage());
			}
		});
		
		JButton btnReset = new JButton("RESET");
		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cpl.reset();
				textPane.setText(cpl.getDisplayMessage());
			}
		});
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(99)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(textPane, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
							.addGroup(gl_contentPane.createSequentialGroup()
								.addComponent(keypad_1)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(keypad_2)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(keypad_3))
							.addGroup(gl_contentPane.createSequentialGroup()
								.addComponent(keypad_4)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(keypad_5)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(keypad_6))
							.addGroup(gl_contentPane.createSequentialGroup()
								.addComponent(keypad_7)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(keypad_8)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(keypad_9))
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(btnReset, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(keypad_0, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(btnEnter, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
								.addGap(2))))));
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(83)
					.addComponent(textPane, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(73)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(keypad_9)
						.addComponent(keypad_8)
						.addComponent(keypad_7))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(keypad_6)
						.addComponent(keypad_5)
						.addComponent(keypad_4))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(keypad_3)
						.addComponent(keypad_2)
						.addComponent(keypad_1))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(keypad_0)
						.addComponent(btnEnter)
						.addComponent(btnReset))
					.addContainerGap(111, Short.MAX_VALUE))
		);
		gl_contentPane.linkSize(SwingConstants.HORIZONTAL, new Component[] {keypad_9, keypad_8, keypad_7, keypad_6, keypad_5, keypad_4, keypad_3, keypad_2, keypad_1, keypad_0, btnReset, btnEnter});
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
	
}
package raven.game;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;



public class ExceptionExitPopup extends JFrame {
	private JTextField errorField = new JTextField();
	/**
	 * Instances of this class are created by
	 * exceptions that terminate the program.
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ExceptionExitPopup(String name,String error){
	super();
	
	errorField.setText(error);
	errorField.setEditable(false);
	this.getContentPane().add(errorField, BorderLayout.NORTH); 
	this.setTitle(name);
	
	///Buttons
	JPanel buttonPanel = new JPanel();
	Handler handler = new Handler(); 
	JButton okButton = new JButton("OK");
	JButton quitButton = new JButton("QUIT");
	okButton.addActionListener(handler); 		 
	quitButton.addActionListener(handler);
	buttonPanel.add(okButton, null);
	buttonPanel.add(quitButton, null);
	buttonPanel.setLayout(new GridLayout());
	this.getContentPane().add(buttonPanel, BorderLayout.SOUTH);

	////Add listener for window closing.
	addWindowListener(new WindowAdapter() {
		 public void windowClosing(WindowEvent e) {
		
			 System.out.println("Closed");
		 //System.exit(0);
		        }
		      });
	pack();
	this.setSize(250,100);
	this.setVisible(true);
	
	}
	
	
	
	
	class Handler implements ActionListener { 
		// Event handling is handled locally
		public void actionPerformed(ActionEvent e) {
			if (e.getActionCommand() == "QUIT")
			System.exit(0);
			else
				setVisible(false); //you can't see me!
				dispose(); //Destroy the JFrame object
	    } }
	
}

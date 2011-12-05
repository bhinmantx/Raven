package raven.ui;

import javax.swing.*; 

import raven.utils.Log;

import java.awt.*; 
import java.awt.event.*;
import java.util.Formatter;
import java.awt.Color;



public class Scoreboard extends JFrame {
	
	// TODO This is a hack hack hack hack!!! Fix!
	Scoreboard thisboard = this;
	
	Integer numberOfTeams = 0;
	
	

	private JTextField score0 = new JTextField(); 
	private JTextField score1 = new JTextField(); 
	
	private JLabel score0Label = new JLabel();
	private JLabel score1Label = new JLabel();

	public Scoreboard() { 
		super(); 
		score0Label.setText("Score: ");
		score1Label.setText("Score: ");
		//score0Label.setEditable(false);
		//score1Label.setEditable(false);
		score0.setText("0");
		score1.setText("0");
		
		JPanel score0Panel = new JPanel(new GridLayout(1,2));
		JPanel score1Panel = new JPanel(new GridLayout(1,2));
		
		
		score0Panel.add(score0Label);
		score0Panel.add(score0);
		score0Panel.setBackground(Color.red);
		
		score1Panel.add(score1Label);
		score1Panel.add(score1);
		score1Panel.setBackground(Color.green);
		
		
		
		this.getContentPane().add(score0Panel, BorderLayout.NORTH); 
		this.getContentPane().add(score1Panel, BorderLayout.SOUTH); 
		
		this.getContentPane().setLayout(new GridLayout(1,numberOfTeams));
	
 addWindowListener(new WindowAdapter() {
	 
public void windowClosing(WindowEvent e) {
 Log.info("Closing Scoreboard is a bad idea");

 }
      });
   
	pack();
	this.setVisible(true);

	 }
	
	
public void addTeamScorePanel(Integer TeamID, Color teamColor ){
	
}


	 

	
	// Now implement the necessary event handling code 
	public void scoreUpdate(Integer team) {
	//System.out.println("Trying to update amount in this AccountView");	
		int tempScore;
	if(team == 0){
		tempScore = Integer.parseInt((score0.getText()));
		tempScore++;
		score0.setText(tempScore+"");
	}
	else
		tempScore = Integer.parseInt((score1.getText()));
		tempScore++;
		score1.setText(tempScore+"");
	 }
	 // Inner classes for Event Handling 
	
	
	class Handler implements ActionListener { 
		// Event handling is handled locally
		public void actionPerformed(ActionEvent e) {
			//System.out.println("I am attempting to perform " + e.getActionCommand() + " of " + entryField.getText());
			
			
	    } }
	
	
	
	

}

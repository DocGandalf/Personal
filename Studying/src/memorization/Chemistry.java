package memorization;
import memorization.chemistry.*;
import java.awt.event.*;

import javax.swing.JButton;
import javax.swing.JFrame;

import java.awt.*;

public class Chemistry implements ActionListener {
	public static PeriodicTable p=new PeriodicTable();
	public JFrame GUI=new JFrame("Memorization");
	public Container contentPane=GUI.getContentPane();
	private String[][] Options={{"Balance Equations","Dimensional Analysis","Definitions","Drawing Structures","Back to Main","Exit"}};
	private int[][] grids={{2,3}};
	private int whichScreen=0;
	
	public static void main(String[] args) {
		new Chemistry();
	}

	public Chemistry(){
		contentPane.setLayout(new GridLayout(grids[whichScreen][0],grids[whichScreen][1]));
		Memorization.newButtons(Options[whichScreen], contentPane, this);
		GUI.setMinimumSize(new Dimension(350,250));
		GUI.pack();
		GUI.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		String buttonText=((JButton)ae.getSource()).getText();
		int whichButton=0;
		for(int i=0; i<Options[whichScreen].length; i++){
			if(buttonText.equals(Options[whichScreen][i])){
				whichButton=i;
			}
		}
		if(whichScreen==0){
			if(whichButton==0){
				//????????
			}else if(whichButton==4){
				Memorization.startSubject("Memorization");
				GUI.setVisible(false);
			}else if(whichButton==5){
				System.exit(0);
			}
		}
		
	}
}

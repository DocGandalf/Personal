package memorization;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class Memorization implements ActionListener{
	public JFrame GUI=new JFrame("Memorization");
	public Container contentPane=GUI.getContentPane();
	private static String[] Subjects={"Chemistry", "Spanish", "History", "Random"};//Random must be last
	
	@Override
	public void actionPerformed(ActionEvent ae) {
		JButton Source=(JButton)ae.getSource();
		for(int i=0; i<Subjects.length; i++){
			if(Source.getText().equals(Subjects[i])){
				try{
					Class.forName("memorization."+Subjects[i]).newInstance();
					GUI.setVisible(false);
				}catch(InstantiationException | IllegalAccessException | ClassNotFoundException e){
					e.printStackTrace();
				}
			}
		}
	}
	
	public static String[] getSubjects(){
		return Subjects;
	}
	
	public static void startSubject(String sub){
		try{
			Class.forName("memorization."+sub).newInstance();
		}catch(InstantiationException | IllegalAccessException | ClassNotFoundException e){
			e.printStackTrace();
		}
	}
	
	public Memorization(){
		contentPane.setLayout(new GridLayout(2,2));
		newButtons(Subjects, contentPane, this);
		GUI.setMinimumSize(new Dimension(300,200));
		GUI.pack();
		GUI.setVisible(true);
	}
	
	/**
	*@param names: an array of strings that the buttons will use as text. (It is recommended you save it for comparison later)
	*@param contentPane: the pane that the buttons will be added to. (All buttons will be default, use only for a grid layout)
	*@param l: the object listening for an action from the buttons.
	*/
	public static void newButtons(String[] names, Container contentPane, ActionListener l){
		for(int i=0; i<names.length; i++){
			JButton b=new JButton(names[i]);
			b.addActionListener(l);
			contentPane.add(b);
		}
	}
	
	/**
	*@param names: an array of strings that the buttons will use as text.
	*@param l: the object listening for an action from the buttons.
	*/
	public static ArrayList<JButton> newButtons(String[] names, ActionListener l){
		ArrayList<JButton> buttons=new ArrayList<JButton>();
		for(int i=0; i<names.length; i++){
			buttons.add(new JButton(names[i]));
			buttons.get(i).addActionListener(l);
		}
		return buttons;
	}
	
	public static void main(String[] args) {
		new Memorization();
	}

}

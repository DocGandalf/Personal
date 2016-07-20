package master;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

@SuppressWarnings("serial")
public class Popup extends JFrame {
	
	private JTextField[] parameters;
	private boolean ready=false;
	
	public Popup(String[] variableNames){
		setTitle("Parameters Needed");
		parameters=new JTextField[variableNames.length];
		setLayout(new GridLayout(variableNames.length, 2));
		for(int i=0; i<variableNames.length; i++){
			add(new JLabel(variableNames[i]));
			add(parameters[i]);
			parameters[i].addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
					ready=true;
					for(int i=0; i<parameters.length; i++){
						if(parameters[i].getText().equals("")){
							parameters[i].requestFocus();
							ready=false;
							break;
						}
					}
				}
			});
		}
	}
	
	public boolean isReady(){
		return ready;
	}
	
	public String[] getParameters(){
		String[] temp=new String[parameters.length];
		for(int i=0; i<parameters.length; i++)temp[i]=parameters[i].getText();
		return temp;
	}
	
}

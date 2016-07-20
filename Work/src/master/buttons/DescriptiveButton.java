package master.buttons;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

@SuppressWarnings("serial")
public class DescriptiveButton extends JPanel {
	
	private ArrayList<ActionListener> actionList=new ArrayList<ActionListener>();
	
	public DescriptiveButton(JButton button, String description){
		add(button, BorderLayout.NORTH);
		add(new JLabel(description), BorderLayout.WEST);
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				for(int i=0; i<actionList.size(); i++){
					actionList.get(i).actionPerformed(e);
				}
			}
		});
	}
	
	public void addActionListener(ActionListener l){
		actionList.add(l);
	}
	
	@Override
	public void setEnabled(boolean b){
		super.setEnabled(b);
		if(!b)getComponent(1).setForeground(Color.LIGHT_GRAY);else getComponent(1).setForeground(new Color(51,51,51));
	}
	
}

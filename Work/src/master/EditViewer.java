package master;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import master.buttons.BlankButton;
import java.util.*;
import java.io.*;

@SuppressWarnings("serial")
public class EditViewer extends JFrame {
	ArrayList<BlankButton> panes=new ArrayList<BlankButton>();
	private Master master;
	
	public EditViewer(Master m){
		master=m;
		setTitle("Edit Diagnosis");
		setPreferredSize(new Dimension(800,600));
		for(int i=0; i<5; i++){
			panes.add(new BlankButton());
		}
		add(panes.get(0), BorderLayout.NORTH);
		add(panes.get(1), BorderLayout.WEST);
		add(panes.get(2), BorderLayout.CENTER);
		add(panes.get(3), BorderLayout.EAST);
		add(panes.get(4), BorderLayout.SOUTH);
		
		pack();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	public EditViewer(Master m, String[] diagnoses){
		
	}
}

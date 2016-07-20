package master;

import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import java.io.*;

@SuppressWarnings("serial")
public class Master extends JFrame{
	private String[] diagnosesNames=new File("src\\master\\Diagnoses").list();
	@SuppressWarnings("rawtypes")
	private JList diagnoses;
	private JScrollPane scrollDiagnoses;
	private JButton addPatientInfo=new JButton("Go");
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Master(){
		for(int i=0; i<diagnosesNames.length; i++){
			diagnosesNames[i]=diagnosesNames[i].substring(0, diagnosesNames[i].indexOf('.'));
		}
		diagnoses=new JList(diagnosesNames);
		diagnoses.setVisibleRowCount(7);
		diagnoses.addListSelectionListener(new ListSelectionListener(){
			@Override
			public void valueChanged(ListSelectionEvent e){
				int[] indices=diagnoses.getSelectedIndices();
				addPatientInfo.setEnabled(indices.length!=0);
			}
		});
		
		scrollDiagnoses=new JScrollPane(diagnoses);
		add(scrollDiagnoses, BorderLayout.SOUTH);
		
		addPatientInfo.setEnabled(false);
		add(addPatientInfo, BorderLayout.CENTER);
		
		setPreferredSize(new Dimension(250,192));
	}
	
	public static void main(String[] args){
		Master frame = new Master();
		frame.pack();
		frame.setTitle("Master");
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}

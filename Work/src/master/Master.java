package master;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.util.*;
import java.io.*;

@SuppressWarnings("serial")
public class Master extends JFrame{
	private String[] diagnosesNames=new File("src\\master\\Diagnoses").list();
	@SuppressWarnings("rawtypes")
	private JList diagnoses;
	private JScrollPane scrollDiagnoses;
	private JButton newDiagnosis=new JButton("New");
	private JButton addPatientInfo=new JButton("Go");
	private JButton editDiagnosis=new JButton("Edit");
	private Master instance;
	private EditViewer eView;
	private Editor editor;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Master(){
		instance=this;
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
				editDiagnosis.setEnabled(indices.length==1);
			}
		});
		
		scrollDiagnoses=new JScrollPane(diagnoses);
		add(scrollDiagnoses, BorderLayout.SOUTH);
		
		addPatientInfo.setEnabled(false);
		editDiagnosis.setEnabled(false);
		
		editDiagnosis.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				instance.setVisible(false);
				eView=new EditViewer(instance, new String[]{(String)diagnoses.getSelectedValue()});
				editor=new Editor(instance);
			}
		});
		newDiagnosis.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				instance.setVisible(false);
				eView=new EditViewer(instance);
				editor=new Editor(instance);
			}
		});
		
		JPanel buttonPanel=new JPanel();
		buttonPanel.setLayout(new GridLayout(1,3));
		buttonPanel.add(addPatientInfo);
		buttonPanel.add(newDiagnosis);
		buttonPanel.add(editDiagnosis);
		add(buttonPanel, BorderLayout.CENTER);
		
		setPreferredSize(new Dimension(250,192));
	}
	
	public EditViewer getEditViewer(){
		return eView;
	}
	
	public Editor getEditor(){
		return editor;
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

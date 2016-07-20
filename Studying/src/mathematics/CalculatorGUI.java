package mathematics;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class CalculatorGUI implements ActionListener, WindowListener{
	private JFrame GUI=new JFrame("Calculator");
	private Container contentPane=GUI.getContentPane();
	private GridBagLayout gbl=new GridBagLayout();
	private GridBagConstraints gbc=new GridBagConstraints();
	private JTextField expression=new JTextField(20);
	private ArrayList<JButton> buttons=new ArrayList<JButton>();
	private boolean useSigFigs=false;
	private String LastExpression="";
	private String answer="";
	
	public CalculatorGUI(boolean basicCalc){
		GUI.addWindowListener(this);
		contentPane.setLayout(gbl);
		if(basicCalc){
			gbc.gridwidth=GridBagConstraints.REMAINDER;
			contentPane.add(expression, gbc);
			for(int i=0; i<10; i++){
				buttons.add(new JButton(i+""));
			}
			buttons.add(new JButton("C"));
			buttons.add(new JButton("LE"));
			buttons.add(new JButton("OFF"));
			buttons.add(new JButton("/"));
			buttons.add(new JButton("x"));
			buttons.add(new JButton("-"));
			buttons.add(new JButton("+"));
			buttons.add(new JButton("."));
			buttons.add(new JButton("Significant Figures: "+useSigFigs));
			buttons.get(18).setPreferredSize(new Dimension(224,25));
			buttons.add(new JButton("="));
			buttons.add(new JButton("^"));
			for(int i=0; i<buttons.size(); i++){
				buttons.get(i).addActionListener(this);
				if(i!=18){
					buttons.get(i).setPreferredSize(new Dimension(56,30));
				}
			}
			expression.addActionListener(this);
			JFrame numPad=new JFrame();
			Container numPadPane=numPad.getContentPane();
			contentPane.add(numPadPane, gbc);
			numPadPane.setLayout(new GridLayout(5,4));
			numPadPane.add(buttons.get(12));
			numPadPane.add(buttons.get(11));
			numPadPane.add(buttons.get(10));
			numPadPane.add(buttons.get(13));
			numPadPane.add(buttons.get(7));
			numPadPane.add(buttons.get(8));
			numPadPane.add(buttons.get(9));
			numPadPane.add(buttons.get(14));
			numPadPane.add(buttons.get(4));
			numPadPane.add(buttons.get(5));
			numPadPane.add(buttons.get(6));
			numPadPane.add(buttons.get(15));
			numPadPane.add(buttons.get(1));
			numPadPane.add(buttons.get(2));
			numPadPane.add(buttons.get(3));
			numPadPane.add(buttons.get(16));
			numPadPane.add(buttons.get(0));
			numPadPane.add(buttons.get(17));
			numPadPane.add(buttons.get(19));
			numPadPane.add(buttons.get(20));
			contentPane.add(buttons.get(18), gbc);
		}
		GUI.pack();
		GUI.setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent ae){
		if(answer.equals(expression.getText())){
			expression.setText("");
		}
		int which=-1;
		for(int i=0; i<buttons.size(); i++){
			if(ae.getSource()==buttons.get(i)){
				which=i;
			}
		}
		if(which==-1){
			which=19;
		}
		if(which<10){
			expression.setText(expression.getText()+which);
		}else if(which==10){
			expression.setText("");
		}else if(which==11){
			expression.setText(LastExpression);
		}else if(which==12){
			System.exit(0);
		}else if(which>=13 && which<=17){
			expression.setText(expression.getText()+buttons.get(which).getText());
		}else if(which==18){
			useSigFigs=!useSigFigs;
			buttons.get(which).setText("Significant Figures: "+useSigFigs);
		}else if(which==20){
			expression.setText(expression.getText()+"^");
		}else if(which==19){
			try{
				LastExpression=expression.getText();
				answer=calculateBasic(expression.getText(), useSigFigs);
				expression.setText(answer);
			}catch (SyntaxException e){
				expression.setText(e.getMessage());
			}
		}
	}
	
	public static String calculateBasic(String expression, boolean useSigFigs) throws SyntaxException{
		return new Expression(expression, useSigFigs).evaluate();
	}
	
	public static void main(String[] args){
		new CalculatorGUI(true);
	}

	@Override
	public void windowActivated(WindowEvent arg0) {
		
	}

	@Override
	public void windowClosed(WindowEvent arg0) {
		
	}

	@Override
	public void windowClosing(WindowEvent arg0) {
		System.exit(0);
	}

	@Override
	public void windowDeactivated(WindowEvent arg0) {
		
	}

	@Override
	public void windowDeiconified(WindowEvent arg0) {
		
	}

	@Override
	public void windowIconified(WindowEvent arg0) {
		
	}

	@Override
	public void windowOpened(WindowEvent arg0) {
		
	}
	
}
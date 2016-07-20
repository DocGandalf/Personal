package memorization;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import memorization.spanish.Translator;
import memorization.spanish.Translator.*;

public class Spanish implements ActionListener,FocusListener{
	public JFrame GUI=new JFrame("Memorization");
	public Container contentPane=GUI.getContentPane();
	public int whichScreen=0;
	public JTextField answer=new JTextField(26);
	public JTextField[] answers=new JTextField[12];
	public int chosenWord=-1;
	private JTextField lastFocused;
	private JLabel console;
	private boolean englishFirst;
	private Form form;
	private Tense tense;
	private String[] oddCharacters={"á","é","í","ó","ú","ñ","¿","¡"};
	private String[][] Options={{"Sustantivos", "Verbos", "Regresar", "Salir"},{"Definiciones", "Regresar"}, {"Definiciones", "Conjugaciones", "Regresar"},{"English to Spanish", "Español a Inglés","Regresar"},{"Tiempos", "Formas","Regresar"},{"Yo","Nosotros","Tú","Vosotros","Él/Ella/Usted","Ellos(as)/Ustedes"},{"Presente","Tener que","Ir a","Presente Progresivo","Mandatos","Pretérito","Presente Perfecto"}};//{{First Screen},{Second Screen}...}
	private int[][] grids={{1,4},{1,2},{1,3},{1,3},{1,3},{3,2},{7,1}};
	private String[] commandForm={"(Tú +)","(Tú -)","(Usted +)","(Usted -)","(Ustedes +)","(Ustedes -)"};
	private Translator Translate;
	private ArrayList<String> wordList=new ArrayList<String>();
	
	public Spanish(){
		contentPane.setLayout(new GridLayout(1,4));
		Memorization.newButtons(Options[0], contentPane, this);
		GUI.setMinimumSize(new Dimension(350,110));
		GUI.pack();
		GUI.setVisible(true);
	}
	
	public static void main(String[] args) {
		new Spanish();
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		String buttonText=((JButton)ae.getSource()).getText();
		if(whichScreen==7){
			if(buttonText.equals("Regresar")){
				whichScreen=1;
				answer.setText("");
				wordList.removeAll(wordList);
				actionPerformed(ae);
			}else if(buttonText.equals("Masculino")){
				checkDefinition(true);
			}else if(buttonText.equals("Femenino")){
				checkDefinition(false);
			}else if(buttonText.equals("Siguiente")){
				answer.setText("");
				console.setText("Translate: "+newWord(englishFirst));
			}else if(buttonText.equals("Enviar")){
				showAnswer();
			}else{
				addLetter(buttonText);
			}
			if(lastFocused!=null)lastFocused.requestFocusInWindow();
		}else{
			int whichButton=0;
			for(int i=0; i<Options[whichScreen].length; i++){
				if(buttonText.equals(Options[whichScreen][i])){
					whichButton=i;
				}
			}
			if(whichScreen==0){
				if(whichButton==0){//this determines the button
					Translate=new Translator(true);
					whichScreen=1;
				}else if(whichButton==1){
					Translate=new Translator(false);
					whichScreen=2;
				}else if(whichButton==2){
					Memorization.startSubject("Memorization");
					GUI.setVisible(false);
				}else if(whichButton==3){
					System.exit(0);
				}
			}else if(whichScreen==1){
				if(whichButton==0){
					whichScreen=3;
				}else if(whichButton==1){
					whichScreen=0;
				}
			}else if(whichScreen==2){
				if(whichButton==0){
					whichScreen=3;
				}else if(whichButton==1){
					whichScreen=4;
				}else if(whichButton==2){
					whichScreen=0;
				}
			}else if(whichScreen==3){
				if(whichButton==0){
					nounSetup(true);
					return;
				}else if(whichButton==1){
					nounSetup(false);
					return;
				}else if(whichButton==2){
					whichScreen= Translate.isNoun()? 1:2;
				}
			}else if(whichScreen==4){
				if(whichButton==0){
					whichScreen=5;
				}else if(whichButton==1){
					whichScreen=6;
				}else if(whichButton==2){
					whichScreen=2;
				}
			}else if(whichScreen==5){
				if(whichButton==0){
					verbSetup(Form.YO);
				}
				return;
			}else if(whichScreen==6){
				if(whichButton==0){
					verbSetup(Tense.PRESENT);
				}
				return;
			}
			contentPane.removeAll();
			contentPane.setLayout(new GridLayout(grids[whichScreen][0],grids[whichScreen][1]));
			Memorization.newButtons(Options[whichScreen], contentPane, this);
			GUI.pack();
		}
	}
	
	private void nounSetup(boolean english){
		whichScreen=7;
		englishFirst=english;
		for(int i=0; i<Translate.Dictionary.size(); i++){
			StringTokenizer s=new StringTokenizer(Translate.Dictionary.get(i),"="+(Translate.isNoun()? " ":""));
			wordList.add(s.nextToken());
			wordList.add(s.nextToken());
			boolean trash=Translate.isNoun()? wordList.add(s.nextToken()):false;
		}
		contentPane.removeAll();
		GridBagConstraints g=new GridBagConstraints();
		g.fill=GridBagConstraints.BOTH;
		contentPane.setLayout(new GridBagLayout());
		g.gridwidth=GridBagConstraints.REMAINDER;
		console=new JLabel("Translate: "+newWord(english));
		contentPane.add(console, g);
		contentPane.add(answer, g);
		g.weightx=0;
		g.gridwidth=1;
		ArrayList<JButton> accents=Memorization.newButtons(oddCharacters, this);
		for(int i=0; i<accents.size()-1; i++){
			contentPane.add(accents.get(i), g);
		}
		g.gridwidth=GridBagConstraints.REMAINDER;
		contentPane.add(accents.get(accents.size()-1), g);
		JButton b;
		if(Translate.isNoun()){
			g.gridwidth=4;
			b=new JButton("Masculino");
			b.addActionListener(this);
			contentPane.add(b, g);
			g.gridwidth=GridBagConstraints.REMAINDER;
			b=new JButton("Femenino");
			b.addActionListener(this);
			contentPane.add(b, g);
		}else{
			g.gridwidth=GridBagConstraints.REMAINDER;
			b=new JButton("Enviar");
			b.addActionListener(this);
			contentPane.add(b,  g);
		}
		g.gridwidth=4;
		b=new JButton("Siguiente");
		b.addActionListener(this);
		contentPane.add(b, g);
		g.gridwidth=GridBagConstraints.REMAINDER;
		b=new JButton("Regresar");
		b.addActionListener(this);
		contentPane.add(b, g);
		GUI.pack();
	}
	
	private void verbSetup(Form form){
		whichScreen=7;
		this.form=form;
		for(int i=0; i<Translate.Dictionary.size(); i++){
			StringTokenizer s=new StringTokenizer(Translate.Dictionary.get(i),"=");
			wordList.add(s.nextToken());
			wordList.add(s.nextToken());
		}
		contentPane.removeAll();
		GridBagConstraints g=new GridBagConstraints();
		g.fill=GridBagConstraints.BOTH;
		contentPane.setLayout(new GridBagLayout());
		g.gridwidth=GridBagConstraints.REMAINDER;
		console=new JLabel("Translate: "+newWord(false)+" to all tenses using ");
		if(form==Form.YO){
			 console.setText(console.getText()+"Yo");
		}else if(form==Form.TU){
			console.setText(console.getText()+"Tú");
		}else if(form==Form.EL){
			console.setText(console.getText()+"Él/Ella/Usted");
		}else if(form==Form.NOSOTROS){
			console.setText(console.getText()+"Nosotros");
		}else if(form==Form.VOSOTROS){
			console.setText(console.getText()+"Vosotros");
		}else if(form==Form.ELLOS){
			console.setText(console.getText()+"Ellos(as)/Ustedes");
		}
		contentPane.add(console, g);
		g.weightx=0;
		g.gridwidth=1;
		ArrayList<JButton> accents=Memorization.newButtons(oddCharacters, this);
		for(int i=0; i<accents.size()-1; i++){
			contentPane.add(accents.get(i), g);
		}
		g.gridwidth=GridBagConstraints.REMAINDER;
		contentPane.add(accents.get(accents.size()-1), g);
		for(int i=0; i<12; i++){
			g.gridwidth=4;
			answers[i]=new JTextField(13);
			answers[i].addFocusListener(this);
			contentPane.add(new JLabel(((i>3&&i<10)? "Mandatos "+commandForm[i-4]:((i>9)? Options[6][i-5]:Options[6][i]))+": "), g);
			g.gridwidth=GridBagConstraints.REMAINDER;
			contentPane.add(answers[i], g);
		}
		JButton b=new JButton("Enviar");
		b.addActionListener(this);
		contentPane.add(b,  g);
		g.gridwidth=4;
		b=new JButton("Siguiente");
		b.addActionListener(this);
		contentPane.add(b, g);
		g.gridwidth=GridBagConstraints.REMAINDER;
		b=new JButton("Regresar");
		b.addActionListener(this);
		contentPane.add(b, g);
		GUI.pack();
	}
	
	private void verbSetup(Tense tense){
		whichScreen=7;
		this.tense=tense;
		
	}
	
	private void addLetter(String letter){
		if(lastFocused==null)return;
		lastFocused.setText(lastFocused.getText()+letter);
	}
	
	private String newWord(boolean english){
		chosenWord=random(0,wordList.size()/nounNum()-1);
		return removeFlags(wordList.get(chosenWord*nounNum()+(english? 1:0)));
	}
	
	public static String removeFlags(String word){
		if(word.contains("(")){
			return word.substring(0, word.lastIndexOf('('));
		}
		return word;
	}
	
	private String getCurrentWord(){
		return wordList.get(chosenWord*nounNum()+(englishFirst? 1:0));
	}
	
	private int nounNum(){
		return Translate.isNoun()? 3:2;
	}
	
	public static int random(int num1, int num2){
		return (int)Math.round((Math.random()*(num2-num1)+num1));
	}
	
	private void checkDefinition(boolean masculine){
		console.setText("Answer: "+wordList.get(chosenWord*nounNum()+(englishFirst? 0:1))+", Gender: "+(wordList.get(chosenWord*nounNum()+2).equals("M")? "Masculine":"Feminine"));
		GUI.pack();
	}
	
	private void showAnswer(){
		if(form==null&&tense==null){
			console.setText("Answer: "+wordList.get(chosenWord*nounNum()+(englishFirst? 0:1)));
		}else{
			//start checking conjugations.
			if(form==null){
				Translate.conjugate(getCurrentWord(), tense, answers, wordList);
			}else{
				Translate.conjugate(getCurrentWord(), form, answers, wordList);
			}
		}
	}

	@Override
	public void focusGained(FocusEvent fe){
		lastFocused=(JTextField)fe.getSource();
	}

	@Override
	public void focusLost(FocusEvent fe){}
	
}

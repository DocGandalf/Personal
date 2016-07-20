package master;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import javax.imageio.ImageIO;
import javax.management.ImmutableDescriptor;
import javax.swing.*;
import javax.swing.event.*;
import master.buttons.DescriptiveButton;
import master.buttons.ImageButton;
import java.util.*;
import java.util.Timer;
import java.io.*;

@SuppressWarnings("serial")
public class Editor extends JFrame{
	public static final String SLASH= System.getProperty("os.name").contains("Windows")? "\\":"/";
	private DescriptiveButton BorderLay=new DescriptiveButton(new ImageButton(this.getClass().getResource("assets"+SLASH+"BorderLay.png")), "Border");
	private DescriptiveButton GridLay=new DescriptiveButton(new ImageButton(this.getClass().getResource("assets"+SLASH+"GridLay.png")), "Grid");
	private DescriptiveButton Text=new DescriptiveButton(new ImageButton(this.getClass().getResource("assets"+SLASH+"Text.png")), "Text");
	private DescriptiveButton Image=new DescriptiveButton(new ImageButton(this.getClass().getResource("assets"+SLASH+"Image.png")), "Image");
	private JButton URL=new JButton("URL");
	private JButton Delete=new JButton("Delete");
	private ImageButton Undo=new ImageButton(this.getClass().getResource("assets"+SLASH+"Undo.png"));
	private ImageButton Redo=new ImageButton(this.getClass().getResource("assets"+SLASH+"Redo.png"));
	private JButton Complete=new JButton("Done");
	private JButton Deselect=new JButton("Deselect");
	private Master master;
	private String Selected="";
	
	public Editor(Master m){
		super("Editor");
		master=m;
		JPanel pan1=new JPanel();
		pan1.setLayout(new GridLayout(2,2));
		pan1.add(BorderLay);
		pan1.add(GridLay);
		pan1.add(Text);
		pan1.add(Image);
		JPanel pan2=new JPanel();
		pan2.setLayout(new GridLayout(1,2));
		URL.setBackground(Color.WHITE);
		Delete.setBackground(Color.WHITE);
		pan2.add(URL);
		pan2.add(Delete);
		JPanel pan3=new JPanel();
		pan3.setLayout(new GridLayout(2,2));
		Undo.setBorder(URL.getBorder());
		Redo.setBorder(URL.getBorder());
		Deselect.setBackground(Color.WHITE);
		Deselect.setEnabled(false);
		Complete.setBackground(Color.WHITE);
		Complete.setEnabled(false);
		Undo.setEnabled(false);
		Redo.setEnabled(false);
		pan3.add(Undo);
		pan3.add(Redo);
		pan3.add(Complete);
		pan3.add(Deselect);
		add(pan2, BorderLayout.NORTH);
		add(pan1, BorderLayout.CENTER);
		add(pan3, BorderLayout.SOUTH);
		
		BorderLay.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Selected="BorderLay";
				Deselect.setEnabled(!getSelected().equals(""));
				BorderLay.setEnabled(getSelected().equals(""));
				Delete.setEnabled(getSelected().equals(""));
				GridLay.setEnabled(getSelected().equals(""));
				Image.setEnabled(getSelected().equals(""));
				Text.setEnabled(getSelected().equals(""));
				URL.setEnabled(getSelected().equals(""));
			}
		});
		GridLay.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Selected="GridLay";
				Deselect.setEnabled(!getSelected().equals(""));
				BorderLay.setEnabled(getSelected().equals(""));
				Delete.setEnabled(getSelected().equals(""));
				GridLay.setEnabled(getSelected().equals(""));
				Image.setEnabled(getSelected().equals(""));
				Text.setEnabled(getSelected().equals(""));
				URL.setEnabled(getSelected().equals(""));
			}
		});
		Text.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Selected="Text";
				Deselect.setEnabled(!getSelected().equals(""));
				BorderLay.setEnabled(getSelected().equals(""));
				Delete.setEnabled(getSelected().equals(""));
				GridLay.setEnabled(getSelected().equals(""));
				Image.setEnabled(getSelected().equals(""));
				Text.setEnabled(getSelected().equals(""));
				URL.setEnabled(getSelected().equals(""));
			}
		});
		Image.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Selected="Image";
				Deselect.setEnabled(!getSelected().equals(""));
				BorderLay.setEnabled(getSelected().equals(""));
				Delete.setEnabled(getSelected().equals(""));
				GridLay.setEnabled(getSelected().equals(""));
				Image.setEnabled(getSelected().equals(""));
				Text.setEnabled(getSelected().equals(""));
				URL.setEnabled(getSelected().equals(""));
			}
		});
		URL.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Selected="URL";
				Deselect.setEnabled(!getSelected().equals(""));
				BorderLay.setEnabled(getSelected().equals(""));
				Delete.setEnabled(getSelected().equals(""));
				GridLay.setEnabled(getSelected().equals(""));
				Image.setEnabled(getSelected().equals(""));
				Text.setEnabled(getSelected().equals(""));
				URL.setEnabled(getSelected().equals(""));
			}
		});
		Delete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Selected="Delete";
				Deselect.setEnabled(!getSelected().equals(""));
				BorderLay.setEnabled(getSelected().equals(""));
				Delete.setEnabled(getSelected().equals(""));
				GridLay.setEnabled(getSelected().equals(""));
				Image.setEnabled(getSelected().equals(""));
				Text.setEnabled(getSelected().equals(""));
				URL.setEnabled(getSelected().equals(""));
			}
		});
		Undo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		Redo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		Complete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		Deselect.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				Selected="";
				Deselect.setEnabled(!getSelected().equals(""));
				BorderLay.setEnabled(getSelected().equals(""));
				Delete.setEnabled(getSelected().equals(""));
				GridLay.setEnabled(getSelected().equals(""));
				Image.setEnabled(getSelected().equals(""));
				Text.setEnabled(getSelected().equals(""));
				URL.setEnabled(getSelected().equals(""));
			}
		});
		
		setPreferredSize(new Dimension(184,286));
		pack();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocation(100, 100);
		setVisible(true);
	}
	
	@Override
	public void paint(Graphics g){
		super.paint(g);
		if(getSelected().equals("")||getMousePosition()==null)return;
		try {
			g.drawImage((BufferedImage)ImageIO.read(this.getClass().getResource("assets"+SLASH+getSelected()+".png")), (int)Math.round(getMousePosition().getX()), (int)Math.round(getMousePosition().getY()), (ImageObserver)this);
		} catch (HeadlessException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NullPointerException e){
			e.printStackTrace();
		}
	}
	
	public String getSelected(){
		return Selected;
	}
}

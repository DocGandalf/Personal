package master.buttons;

import java.awt.*;
import java.net.URL;
import javax.swing.*;
import javax.swing.border.*;

@SuppressWarnings("serial")
public class ImageButton extends JButton {
	private ImageIcon ButtonImage;
	
	public ImageButton(URL url){
		ButtonImage=new ImageIcon(url);
		setIcon(ButtonImage);
		setSize(new Dimension(ButtonImage.getIconWidth(),ButtonImage.getIconHeight()));
		setOpaque(true);
		setBackground(Color.WHITE);
		setBorder(new EtchedBorder(EtchedBorder.RAISED));
	}
	
	public ImageButton(URL url, String name){
		super(name);
		ButtonImage=new ImageIcon(url);
		setIcon(ButtonImage);
		setSize(new Dimension(ButtonImage.getIconWidth(),ButtonImage.getIconHeight()));
		setOpaque(true);
		setBackground(Color.WHITE);
		setBorder(new EtchedBorder(EtchedBorder.RAISED));
	}
	
}

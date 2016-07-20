package master.buttons;

import java.awt.*;
import javax.swing.JButton;
import javax.swing.border.LineBorder;

@SuppressWarnings("serial")
public class BlankButton extends JButton {
	
	public BlankButton(){
		setOpaque(true);
		setBackground(Color.WHITE);
		setBorder(new LineBorder(Color.BLACK));
		setPreferredSize(new Dimension(175,175));
	}
	
}

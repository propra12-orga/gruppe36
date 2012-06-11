import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class SpielVerloren extends JFrame {

	private JButton okay = new JButton();
	private Image iHintergrund;
	private Icon iconhintergund;

	GameMaster game;

	public SpielVerloren(GameMaster master) {

		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		int frameWidth = 400;
		int frameHeight = 400;
		setSize(frameWidth, frameHeight);
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (d.width - getSize().width) / 2;
		int y = (d.height - getSize().height) / 2;
		setLocation(x, y);

		this.setLayout(null);
		game = master;
		this.iHintergrund = ImageLoader.getImage(Game.class,
				"IMG//game_over.jpg");
		this.iconhintergund = new ImageIcon(this.iHintergrund);

		this.okay.setIcon(iconhintergund);
		this.okay.setBounds(0, 0, 400, 400);

		this.okay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				game.setVisible(true);
				setVisible(false);
			}
		});

		this.getContentPane().add(this.okay);

		setResizable(false);
		setVisible(true);
	}

}
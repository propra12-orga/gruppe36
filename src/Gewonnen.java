import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Gewonnen extends JFrame {

	private JButton okay = new JButton();
	private Image Ihintergrund;
	private Icon iconHintergrund;

	GameMaster game;

	public Gewonnen(GameMaster master) {

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
		this.Ihintergrund = ImageLoader.getImage(Game.class,
				"IMG//gewonnen.jpg");
		this.iconHintergrund = new ImageIcon(this.Ihintergrund);

		this.okay.setIcon(iconHintergrund);
		this.okay.setBounds(0, 0, 400, 400);

		this.okay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				new Felder(game.currentLvl, game);
				setVisible(false);
			}
		});

		this.getContentPane().add(this.okay);

		setResizable(false);
		setVisible(true);
	}

}
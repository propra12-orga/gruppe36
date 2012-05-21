import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class GameMaster extends JFrame {
	private JButton start = new JButton();
	private JButton passwort = new JButton();
	private JButton multiplayer = new JButton();
	private GameMaster master;
	private Image hintergrund = ImageLoader.getImage(Game.class,
			"IMG\\hintergrund_animiert_version1.gif");
	private Icon iHintergrund = new ImageIcon(hintergrund);
	private JLabel lHintergrund = new JLabel();
	public String nextLvl = "1.Bomberman";
	public String currentLvl = nextLvl;

	public GameMaster() {
		super("Bomberman");
		master = this;
		int frameWidth = 400;
		int frameHeight = 400;
		setSize(frameWidth, frameHeight);
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (d.width - getSize().width) / 2;
		int y = (d.height - getSize().height) / 2;
		setLocation(x, y);

		this.setLayout(null);

		start.setText("Start");
		start.setBounds(150, 120, 100, 20);
		start.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent a) {
				new Felder(currentLvl, master);
				master.setVisible(false);
			}
		});

		passwort.setText("Passwort");
		passwort.setBounds(150, 160, 100, 20);
		passwort.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent a) {

			}
		});
		passwort.setEnabled(false);

		multiplayer.setText("Multiplayer");
		multiplayer.setBounds(150, 200, 100, 20);
		multiplayer.setEnabled(false);

		this.lHintergrund.setIcon(this.iHintergrund);
		this.lHintergrund.setBounds(0, -20, 400, 400);

		this.getContentPane().add(start);
		this.getContentPane().add(passwort);
		this.getContentPane().add(multiplayer);
		this.getContentPane().add(lHintergrund);

		setResizable(false);
		this.setVisible(true);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new GameMaster();

	}

}

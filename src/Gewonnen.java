/**
 * Die Klasse generiert das Fenster bei Sieg, das ebenfalls als Button zum nächsten Level dient.
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.File;

public class Gewonnen extends JFrame {

	private JButton okay = new JButton();
	private Image Ihintergrund;
	private Icon iconHintergrund;

	GameMaster game;

	/*
	 * Konstruktor, dem ein GameMaster übergeben wird.
	 */
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
				"IMG"+File.separator+"Gewonnen.jpg");
		this.iconHintergrund = new ImageIcon(this.Ihintergrund);

		this.okay.setIcon(iconHintergrund);
		this.okay.setBounds(0, 0, 400, 400);

		this.okay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				if (!Felder.game.netzwerk){
					if(!Felder.game.offlineMulti){
						new Felder(game.currentLvl, game);
					}
					else if(Felder.game.offlineMulti){
						FigurBomberman.destroy();
						new Felder(game.currentLvl, game, true);
					}
				}
				else
					game.setVisible(true);
				setVisible(false);
			}
		});

		this.getContentPane().add(this.okay);

		setResizable(false);
		setVisible(true);
	}

}
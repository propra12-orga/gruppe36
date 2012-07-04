/**
 * Die Klasse generiert das Fenster bei Niederlage, das ebenfalls als Button zum n�chsten Level dient.
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.File;

public class SpielVerloren extends JFrame {

	private JButton okay = new JButton();
        private JLabel pw = new JLabel();
        private JLabel hintergund = new JLabel();
	private Image iHintergrund;
	private Icon iconhintergund;

	GameMaster game;

	/*
	 * Konstruktor, dem ein GameMaster �bergeben wird, damit neues Spiel m�glich
	 * ist.
	 */
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
                
                this.pw.setText("Passwort: "+String.copyValueOf(game.currentLvl.toCharArray(),0,4));
                this.pw.setBounds(130, 0, 200, 100);
                this.pw.setForeground(Color.RED);
                pw.setFont(new Font("Century",Font.BOLD + Font.ROMAN_BASELINE, 15));
                pw.setVisible(!Felder.game.netzwerk&&!Felder.game.offlineMulti);

		this.iHintergrund = ImageLoader.getImage(Game.class,
				"IMG"+File.separator+"Game_Over.jpg");
		this.iconhintergund = new ImageIcon(this.iHintergrund);
                this.hintergund.setIcon(iconhintergund);
                this.hintergund.setBounds(0, 0, 400, 400);
               

		this.okay.setText("Weiter");
		this.okay.setBounds(140, 330, 100, 30);

		this.okay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
                                FigurBomberman.destroy();
				game.setVisible(true);
				setVisible(false);
			}
		});
                this.getContentPane().add(this.pw);
		this.getContentPane().add(this.okay);
                this.getContentPane().add(this.hintergund);
                
		setResizable(false);
		setVisible(true);
	}

}
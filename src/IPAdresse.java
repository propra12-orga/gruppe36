/**
 * Die Klasse generiert das Fenster, um als Client eine IP-Adresse eingeben zu koennen.
 */

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.File;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.*;

/*
 * Besitzt zwei Buttons, einen Hintergrund, vier Eingabefenster für die IP-Adresse, einen Gamemaster, einen String fuer das 
 * naechste Level, eine Variable für die IP-Adresse und ein Objekt der Klasse Felder.
 */
public class IPAdresse extends JFrame {
	private JButton connect = new JButton();
	private JButton cancel = new JButton();

	private GameMaster master;
	private Image hintergrund = ImageLoader.getImage(Game.class, "IMG"
			+ File.separator + "hintergrund_animiert_version1.gif");
	private Icon iHintergrund = new ImageIcon(hintergrund);
	private JLabel lHintergrund = new JLabel();
	public String nextLvl = "1.Bomberman";
	public String currentLvl = nextLvl;
	private JTextField t1 = new JTextField("");
	private JTextField t2 = new JTextField("");
	private JTextField t3 = new JTextField("");
	private JTextField t4 = new JTextField("");
	private InetAddress ip;
	private Felder game;

	/*
	 * Konstruktor, dem ein Gamemaster übergeben wird. Ermoeglicht das Eintippen
	 * einer IP-Adresse, mit dem sich der Server verbindet.
	 */
	public IPAdresse(GameMaster gameMaster) {
		super("Bomberman");
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		master = gameMaster;
		int frameWidth = 400;
		int frameHeight = 400;
		setSize(frameWidth, frameHeight);
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (d.width - getSize().width) / 2;
		int y = (d.height - getSize().height) / 2;
		setLocation(x, y);

		this.setLayout(null);

		connect.setText("Verbinden");
		connect.setBounds(50, 300, 100, 20);
		connect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent a) {
				boolean error = false;
				byte[] b = new byte[4];
				if (t1.getText().length() > 0 && t1.getText().length() < 4) {
					try {
						b[0] = (byte) Integer.parseInt(t1.getText());
					} catch (NumberFormatException e) {
						t1.setText("");
						error = true;
					}
					if (t2.getText().length() > 0 && t2.getText().length() < 4) {
						try {
							b[1] = (byte) Integer.parseInt(t2.getText());
						} catch (NumberFormatException e) {
							t2.setText("");
						}

						if (t3.getText().length() > 0
								&& t3.getText().length() < 4) {
							try {
								b[2] = (byte) Integer.parseInt(t3.getText());
							} catch (NumberFormatException e) {
								t3.setText("");
							}

							if (t4.getText().length() > 0
									&& t4.getText().length() < 4) {
								try {
									b[3] = (byte) Integer.parseInt(t4.getText());
								} catch (NumberFormatException e) {
									t4.setText("");
								}

								if (!error) {
									try {
										ip = InetAddress.getByAddress(b);
										Socket client = new Socket(ip, 1234);
										game = new Felder(master, client);
										setVisible(false);
									} catch (UnknownHostException e) {
										t1.setText("");
										t2.setText("");
										t3.setText("");
										t4.setText("");

									} catch (IOException e) {
										t1.setText("");
										t2.setText("");
										t3.setText("");
										t4.setText("");

									}
								}
							}
						}

					}
				}
			}
		});

		/*
		 * Button Abbrechen
		 */
		cancel.setText("Abbrechen");
		cancel.setBounds(200, 300, 100, 20);
		cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent a) {
				setVisible(false);
				master.setVisible(true);
			}
		});

		t1.setBounds(60, 50, 50, 20);
		t2.setBounds(130, 50, 50, 20);
		t3.setBounds(200, 50, 50, 20);
		t4.setBounds(270, 50, 50, 20);

		this.lHintergrund.setIcon(this.iHintergrund);
		this.lHintergrund.setBounds(0, -20, 400, 400);

		this.getContentPane().add(connect);
		this.getContentPane().add(cancel);
		this.getContentPane().add(t1);
		this.getContentPane().add(t2);
		this.getContentPane().add(t3);
		this.getContentPane().add(t4);
		this.getContentPane().add(lHintergrund);

		setResizable(false);
		this.setVisible(true);
	}
}

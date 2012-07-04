/**
 * Die Klasse die ausfuehrende und Herr ueber alle Ablaeufe.
 * An sie werden alle Veraenderungen sowohl graphische als auch die Spielelogik betreffende weitergeleitet und reagiert entsprechend (Vermittler).
 */
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.UnknownHostException;

import javax.swing.*;

/*
 * Hat Buttons der Menuefuehrung sowie den Hintergrund und liest die Level aus.
 * Buttons: Singleplayer, Multiplayer und darunter jeweils: Start, Passwort und Zurueck.
 * Unter Passwort: Textfeld zur Eingabe einer Kennnummmer, Start und Zurueck.
 */
public class GameMaster extends JFrame {

	private JButton single = new JButton();
	private JButton multiplayer = new JButton();
	private JButton online = new JButton();
	private JButton offline = new JButton();
	private JButton start = new JButton();
	private JButton passwort = new JButton();
	private JButton server = new JButton();
	private JButton client = new JButton();
	private JButton back = new JButton();
	private JTextField pw = new JTextField();
	private JButton bestetigt = new JButton();
	private JButton zuruek = new JButton();
	private GameMaster master;
	private Image hintergrund = ImageLoader.getImage(Game.class, "IMG"
			+ File.separator + "hintergrund_animiert_version1.gif");
	private Icon iHintergrund = new ImageIcon(hintergrund);
	private JLabel lHintergrund = new JLabel();
	public String nextLvl = "0001.Bomberman";
	public String currentLvl = nextLvl;

	/*
	 * Standardkonstruktor, der das Menuefenster und die dazugehoerigen Buttons
	 * erstellt.
	 */
	public GameMaster() {
		super("Bomberman");
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		master = this;
		int frameWidth = 400;
		int frameHeight = 400;
		setSize(frameWidth, frameHeight);
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (d.width - getSize().width) / 2;
		int y = (d.height - getSize().height) / 2;
		setLocation(x, y);

		this.setLayout(null);

		/*
		 * Button Singleplayer
		 */
		single.setText("Singleplayer");
		single.setBounds(150, 120, 100, 20);
		single.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent a) {
				single.setVisible(false);
				multiplayer.setVisible(false);
				start.setVisible(true);
				passwort.setVisible(true);
				online.setVisible(false);
				offline.setVisible(false);
				server.setVisible(false);
				client.setVisible(false);
				back.setVisible(true);
			}
		});

		/*
		 * Button Multiplayer
		 */
		multiplayer.setText("Multiplayer");
		multiplayer.setBounds(150, 160, 100, 20);
		multiplayer.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent a) {
				single.setVisible(false);
				multiplayer.setVisible(false);
				start.setVisible(false);
				passwort.setVisible(false);
				online.setVisible(true);
				offline.setVisible(true);
				server.setVisible(false);
				client.setVisible(false);
				back.setVisible(true);
			}
		});

		/*
		 * Button Start
		 */
		start.setText("Start");
		start.setBounds(150, 120, 100, 20);
		start.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent a) {
				// starte offline-singleplayer (mit CP)
				FigurBomberman.destroy();
				new Felder(currentLvl, master);
				master.setVisible(false);
			}
		});

		passwort.setText("Passwort");
		passwort.setBounds(150, 160, 100, 20);
		passwort.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent a) {
				single.setVisible(false);
				multiplayer.setVisible(false);
				start.setVisible(false);
				passwort.setVisible(false);
				online.setVisible(false);
				offline.setVisible(false);
				server.setVisible(false);
				client.setVisible(false);
				back.setVisible(false);
				zuruek.setVisible(true);
				pw.setVisible(true);
				bestetigt.setVisible(true);
			}
		});
		// passwort.setEnabled(false);

		/*
		 * Button Online
		 */
		online.setText("online");
		online.setBounds(150, 120, 100, 20);
		online.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent a) {
				single.setVisible(false);
				multiplayer.setVisible(false);
				start.setVisible(false);
				passwort.setVisible(false);
				online.setVisible(false);
				offline.setVisible(false);
				server.setVisible(true);
				client.setVisible(true);
				back.setVisible(true);
			}
		});

		/*
		 * Button Offline
		 */
		offline.setText("offline");
		offline.setBounds(150, 160, 100, 20);
		offline.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent a) {
				// starte offline-mulitplayer
				FigurBomberman.destroy();
				new Felder(currentLvl, master, true);
				master.setVisible(false);
			}
		});

		/*
		 * bei Onlinespielen: Button Als Server
		 */
		server.setText("Als Server");
		server.setBounds(150, 120, 100, 20);
		server.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent a) {

				try {
					FigurBomberman.destroy();
					new Felder(currentLvl, master, 1234);
					master.setVisible(false);
				} catch (IOException e) {
					e.printStackTrace();
					master.setVisible(true);
				}

			}
		});

		/*
		 * Bei Onlinespielen: Button Als Client
		 */
		client.setText("Als Client");
		client.setBounds(150, 160, 100, 20);
		client.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent a) {
				new IPAdresse(master);
				master.setVisible(false);
			}
		});

		/*
		 * Button Zurueck zu ersten Auswahl zwischen Single- und Multiplayer
		 */
		back.setText("Zur\u00FCck");
		back.setBounds(150, 200, 100, 20);
		back.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent a) {
				single.setVisible(true);
				multiplayer.setVisible(true);
				start.setVisible(true);
				passwort.setVisible(false);
				online.setVisible(false);
				offline.setVisible(false);
				server.setVisible(false);
				client.setVisible(false);
				back.setVisible(false);
			}
		});

		this.pw.setBounds(150, 120, 100, 20);

		this.bestetigt.setText("GO");
		this.bestetigt.setBounds(150, 160, 100, 20);
		bestetigt.addActionListener(new ActionListener() {

			/*
			 * Zur Eingabe des Passwortes, um Level zu laden. (non-Javadoc)
			 * 
			 * @see
			 * java.awt.event.ActionListener#actionPerformed(java.awt.event.
			 * ActionEvent)
			 */
			public void actionPerformed(ActionEvent a) {
				boolean error = false;
				try {
					BufferedReader in = new BufferedReader(new FileReader(pw
							.getText() + ".Bomberman"));
				} catch (IOException e) {
					error = true;
				}
				if (error) {
					single.setVisible(false);
					multiplayer.setVisible(false);
					start.setVisible(true);
					passwort.setVisible(true);
					online.setVisible(false);
					offline.setVisible(false);
					server.setVisible(false);
					client.setVisible(false);
					back.setVisible(true);
					zuruek.setVisible(false);
					pw.setVisible(false);
					bestetigt.setVisible(false);
					pw.setText("");
				} else {
					FigurBomberman.destroy();
					currentLvl = pw.getText() + ".Bomberman";
					new Felder(currentLvl, master);
					master.setVisible(false);
					single.setVisible(false);
					multiplayer.setVisible(false);
					start.setVisible(true);
					passwort.setVisible(true);
					online.setVisible(false);
					offline.setVisible(false);
					server.setVisible(false);
					client.setVisible(false);
					back.setVisible(true);
					zuruek.setVisible(false);
					pw.setVisible(false);
					bestetigt.setVisible(false);
					pw.setText("");
				}
			}
		});

		/*
		 * Button Zurueck zur Passworteingabe
		 */
		this.zuruek.setText("Zur\u00FCck");
		this.zuruek.setBounds(150, 200, 100, 20);
		zuruek.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent a) {
				single.setVisible(false);
				multiplayer.setVisible(false);
				start.setVisible(true);
				passwort.setVisible(true);
				online.setVisible(false);
				offline.setVisible(false);
				server.setVisible(false);
				client.setVisible(false);
				back.setVisible(true);
				zuruek.setVisible(false);
				pw.setVisible(false);
				bestetigt.setVisible(false);
			}
		});

		online.setVisible(false);
		offline.setVisible(false);
		start.setVisible(false);
		passwort.setVisible(false);
		server.setVisible(false);
		client.setVisible(false);
		back.setVisible(false);
		zuruek.setVisible(false);
		bestetigt.setVisible(false);
		pw.setVisible(false);

		this.lHintergrund.setIcon(this.iHintergrund);
		this.lHintergrund.setBounds(0, -20, 400, 400);

		this.getContentPane().add(zuruek);
		this.getContentPane().add(bestetigt);
		this.getContentPane().add(pw);
		this.getContentPane().add(single);
		this.getContentPane().add(multiplayer);
		this.getContentPane().add(start);
		this.getContentPane().add(passwort);
		this.getContentPane().add(online);
		this.getContentPane().add(offline);
		this.getContentPane().add(server);
		this.getContentPane().add(client);
		this.getContentPane().add(back);
		this.getContentPane().add(lHintergrund);

		setResizable(false);
		this.setVisible(true);
	}

	/*
	 * Mainmethode
	 */
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new GameMaster();

	}
}

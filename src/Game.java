/**
 * Die Klasse ist fuer den erstmaligen graphischen Aufbau sowie alle Veraenderungen zustaendig. 
 * Sie zeichnet das Spielfeld in einem festgelegten Zeitabstand neu.
 * Beinhaltet die eingeschraenkte Sicht des Spielers auf das Spielfeld.
 * Sie beinhaltet die Bilder.
 */
import java.awt.*;
import java.awt.event.*;
import java.io.*;

import javax.swing.*;

/*
 * Besitzt selbstentworfene Bilder, Array fuer die Sicht auf die Felder, statisches Objekt der Klasse Game, einen Timer, der das Spielfeld aktualisiert, Frame als Sicht und eine Position sowie den Schluessel und einen Zaehler. 
 */
public class Game extends JPanel implements ActionListener {

	private Image nichts = ImageLoader.getImage(Game.class, "IMG"
			+ File.separator + "nichts.jpg");
	private Image bombe = ImageLoader.getImage(Game.class, "IMG"
			+ File.separator + "bombe.gif");
	private Image bomberman = ImageLoader.getImage(Game.class, "IMG"
			+ File.separator + "bomberman.gif");
	private Image bomberman2 = ImageLoader.getImage(Game.class, "IMG"
			+ File.separator + "bomberman2.gif");
	private Image fels = ImageLoader.getImage(Game.class, "IMG"
			+ File.separator + "fels.jpg");
	private Image flammenanfang = ImageLoader.getImage(Game.class, "IMG"
			+ File.separator + "flammenanfang.gif");
	private Image flammenende_links = ImageLoader.getImage(Game.class, "IMG"
			+ File.separator + "flammenende_links.gif");
	private Image flammenende_oben = ImageLoader.getImage(Game.class, "IMG"
			+ File.separator + "flammenende_oben.gif");
	private Image flammenende_rechts = ImageLoader.getImage(Game.class, "IMG"
			+ File.separator + "flammenende_rechts.gif");
	private Image flammenende_unten = ImageLoader.getImage(Game.class, "IMG"
			+ File.separator + "flammenende_unten.gif");
	private Image flammenmitte_links = ImageLoader.getImage(Game.class, "IMG"
			+ File.separator + "flammenmitte_links.gif");
	private Image flammenmitte_oben = ImageLoader.getImage(Game.class, "IMG"
			+ File.separator + "flammenmitte_oben.gif");
	private Image flammenmitte_rechts = ImageLoader.getImage(Game.class, "IMG"
			+ File.separator + "flammenmitte_rechts.gif");
	private Image flammenmitte_unten = ImageLoader.getImage(Game.class, "IMG"
			+ File.separator + "flammenmitte_unten.gif");
	private Image fussboden = ImageLoader.getImage(Game.class, "IMG"
			+ File.separator + "Fussboden.jpg");
	private Image wand = ImageLoader.getImage(Game.class, "IMG"
			+ File.separator + "Wand.jpg");
	private Image tor_offen = ImageLoader.getImage(Game.class, "IMG"
			+ File.separator + "tor_offen.gif");
	private Image tor_zu = ImageLoader.getImage(Game.class, "IMG"
			+ File.separator + "tor_zu.jpg");
	private Image schwarz = ImageLoader.getImage(Game.class, "IMG"
			+ File.separator + "Schwarz.jpg");
	private Image item_plus = ImageLoader.getImage(Game.class, "IMG"
			+ File.separator + "item_plus.gif");
	private Image item_minus = ImageLoader.getImage(Game.class, "IMG"
			+ File.separator + "item_minus.gif");
	private Image item_groesser = ImageLoader.getImage(Game.class, "IMG"
			+ File.separator + "item_groesser.gif");
	private Image item_key = ImageLoader.getImage(Game.class, "IMG"
			+ File.separator + "item_key.gif");
	private Image item_nuke = ImageLoader.getImage(Game.class, "IMG"
			+ File.separator + "item_nuke.gif");
	private Image item_speed = ImageLoader.getImage(Game.class, "IMG"
			+ File.separator + "item_speed.gif");
	private int feldersicht[][] = new int[20][15];
	private static Game GUI;
	private Timer time;
	public JFrame frame;
	private int baseX;
	private int baseY;
	private char key;
	private char key2;
	private int zaehler;
	private int zaehler2;

	/*
	 * Standardkonstruktor
	 */
	private Game() {
		frame = new JFrame("Bomberman");
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setUndecorated(true);

		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();

		baseX = (d.width - 800) / 2;
		baseY = (d.height - 600) / 2;

		time = new Timer(200, this);
		time.start();

		this.addKeyListener(new AL());

		setFocusable(true);
		frame.add(this);
		frame.setVisible(true);
	}

	/*
	 * Methode aus .awt.event (non-Javadoc), die bei Onlinespielen die
	 * Tasteneingabe abfragt und verschickt.
	 * 
	 * @see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		if (zaehler >= FigurBomberman.getBomberman().seedteiler) {
			if (key == 'w') {
				if (!Felder.game.netzwerk) {
					FigurBomberman.getBomberman().bewegenOben();
				} else if (Felder.game.isServer) {
					FigurBomberman.getBomberman().bewegenOben();
				} else {
					try {
						OutputStream out = Felder.game.client.getOutputStream();
						out.write(2);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}

			}
			if (key == 'a') {
				if (!Felder.game.netzwerk) {
					FigurBomberman.getBomberman().bewegenLinks();
				} else if (Felder.game.isServer) {
					FigurBomberman.getBomberman().bewegenLinks();
				} else {
					try {
						OutputStream out = Felder.game.client.getOutputStream();
						out.write(0);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}
			if (key == 'd') {
				if (!Felder.game.netzwerk) {
					FigurBomberman.getBomberman().bewegenRechts();
				} else if (Felder.game.isServer) {
					FigurBomberman.getBomberman().bewegenRechts();
				} else {
					try {
						OutputStream out = Felder.game.client.getOutputStream();
						out.write(1);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}
			if (key == 's') {
				if (!Felder.game.netzwerk) {
					FigurBomberman.getBomberman().bewegenUnten();
				} else if (Felder.game.isServer) {
					FigurBomberman.getBomberman().bewegenUnten();
				} else {
					try {
						OutputStream out = Felder.game.client.getOutputStream();
						out.write(3);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}
			if (key == 'e') {
				if (!Felder.game.netzwerk) {
					FigurBomberman.getBomberman().bombeLegen();
				} else if (Felder.game.isServer) {
					FigurBomberman.getBomberman().bombeLegen();
				} else {
					try {
						OutputStream out = Felder.game.client.getOutputStream();
						out.write(4);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}
			key = ' ';
			zaehler = 0;
			FigurBomberman.getBomberman().zuenden = false;
			if (FigurBomberman.getBomberman().fernzuender) {
				if (FigurBomberman.getBomberman().verzuendertime > 150) {
					FigurBomberman.getBomberman().fernzuender = false;
					FigurBomberman.getBomberman().zuenden = true;
					FigurBomberman.getBomberman().verzuendertime = 0;
				} else {
					FigurBomberman.getBomberman().verzuendertime++;
				}
			}
			if (FigurBomberman.getBomberman().seedteiler == 0) {
				if (FigurBomberman.getBomberman().speedteileruse > 300) {
					FigurBomberman.getBomberman().seedteiler = 1;
					FigurBomberman.getBomberman().speedteileruse = 0;
				} else {
					FigurBomberman.getBomberman().speedteileruse++;
				}
			}
		} else {
			zaehler++;
		}

		if (zaehler2 >= FigurBomberman.getBomberman().seedteiler) {
			if (key2 == 'j') {
				if (!Felder.game.netzwerk && Felder.game.offlineMulti) {
					FigurBomberman.getBomberman2().bewegenLinks();
				}
			}
			if (key2 == 'i') {
				if (!Felder.game.netzwerk && Felder.game.offlineMulti) {
					FigurBomberman.getBomberman2().bewegenOben();
				}
			}
			if (key2 == 'k') {
				if (!Felder.game.netzwerk && Felder.game.offlineMulti) {
					FigurBomberman.getBomberman2().bewegenUnten();
				}
			}
			if (key2 == 'l') {
				if (!Felder.game.netzwerk && Felder.game.offlineMulti) {
					FigurBomberman.getBomberman2().bewegenRechts();
				}
			}
			if (key2 == 'o') {
				if (!Felder.game.netzwerk && Felder.game.offlineMulti) {
					FigurBomberman.getBomberman2().bombeLegen();
				}
			}
			key2 = ' ';
			zaehler2 = 0;
			FigurBomberman.getBomberman2().zuenden = false;
			if (FigurBomberman.getBomberman2().fernzuender) {
				if (FigurBomberman.getBomberman2().verzuendertime > 150) {
					FigurBomberman.getBomberman2().fernzuender = false;
					FigurBomberman.getBomberman2().zuenden = true;
					FigurBomberman.getBomberman2().verzuendertime = 0;
				} else {
					FigurBomberman.getBomberman2().verzuendertime++;
				}
			}
			if (FigurBomberman.getBomberman2().seedteiler == 0) {
				if (FigurBomberman.getBomberman2().speedteileruse > 300) {
					FigurBomberman.getBomberman2().seedteiler = 1;
					FigurBomberman.getBomberman2().speedteileruse = 0;
				} else {
					FigurBomberman.getBomberman2().speedteileruse++;
				}
			}
		} else {
			zaehler2++;
		}

	}

	/*
	 * Aktualisiert die Feldersicht.
	 */
	public void reload(int feldersicht[][]) {
		this.feldersicht = feldersicht;
		repaint();
	}

	/*
	 * Paintmethode (non-Javadoc)
	 * 
	 * @see javax.swing.JComponent#paint(java.awt.Graphics)
	 */
	public void paint(Graphics g) {
		// Konstruktor von paint vererben
		super.paint(g);
		// Erzeugen eines Graphics2D-Objektes
		g.drawImage(schwarz, 0, 0, null);
		felderReload(g);

	}

	/*
	 * Zeichnet Bilder ihrem Status entsprechend. Ein Bild ist 40x40 px gross.
	 * Status: Nichts: -1; Fussboden: 0; Bomben: 1; Bomberman: 2; Wand: 4;
	 * Zerstoerbare Wand: 8; Erhoehung der Reichweite: 16; Erhoehung der Anzahl
	 * der Bomben: 32; Ausgang: 64; Schluessel: 128; Bomberman2 bzw.
	 * Computergegner: 512; Laufgeschwindigkeit: 1024; Fernzuender: 2048.
	 * 
	 * Kombinationen: addiert
	 * 
	 * Status der Flammenbilder: Bombenexplosionanfang: 4096;
	 * Bombenexplosionsmitte rechts: 8192; Bombenexplosionsmitte links: 16384;
	 * Bombenexplosionsmitte oben: 32768; Bombenexplosionsmitte unten: 65536;
	 * Bombenexplosionsende rechts: 131072; Bombenexplosionsende links: 262144;
	 * Bombenexplosionsende oben: 524288; Bombenexplosionsende unten: 1048576;
	 */
	private void felderReload(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		int status;
		for (int y = 0; y < 15; y++) {
			for (int x = 0; x < 20; x++) {
				status = this.feldersicht[x][y];
				if (status < 4096) {
				} else if (status >= 4096 && status < 8192) {

					status = status - 4096;

				} else if (status >= 8192 && status < 16384) {

					status = status - 8192;

				} else if (status >= 16384 && status < 32768) {

					status = status - 16384;

				} else if (status >= 32768 && status < 65536) {

					status = status - 32768;

				} else if (status >= 65536 && status < 131072) {

					status = status - 65536;

				} else if (status >= 131072 && status < 262144) {

					status = status - 131072;

				} else if (status >= 262144 && status < 524288) {

					status = status - 262144;

				} else if (status >= 524288 && status < 1048576) {

					status = status - 524288;

				} else if (status >= 1048576) {

					status = status - 1048576;

				}

				if (status == -1) {

					g2d.drawImage(nichts, x * 40 + baseX, y * 40 + baseY, null);

				} else if (status == 0) {

					g2d.drawImage(fussboden, x * 40 + baseX, y * 40 + baseY,
							null);

				} else if (status == 1) {

					g2d.drawImage(fussboden, x * 40 + baseX, y * 40 + baseY,
							null);
					g2d.drawImage(bombe, x * 40 + baseX, y * 40 + baseY, null);

				} else if (status == 2) {

					g2d.drawImage(fussboden, x * 40 + baseX, y * 40 + baseY,
							null);
					g2d.drawImage(bomberman, x * 40 + baseX, y * 40 + baseY,
							null);

				} else if (status == 3) {

					g2d.drawImage(fussboden, x * 40 + baseX, y * 40 + baseY,
							null);
					g2d.drawImage(bombe, x * 40 + baseX, y * 40 + baseY, null);
					g2d.drawImage(bomberman, x * 40 + baseX, y * 40 + baseY,
							null);

				} else if (status == 4) {

					g2d.drawImage(wand, x * 40 + baseX, y * 40 + baseY, null);

				} else if (status == 8 || status == 24 || status == 40
						|| status == 1032 || status == 2056) {
					g2d.drawImage(fels, x * 40 + baseX, y * 40 + baseY, null);
				} else if (status == 16) {
					g2d.drawImage(fussboden, x * 40 + baseX, y * 40 + baseY,
							null);
					g2d.drawImage(item_groesser, x * 40 + baseX + 8, y * 40
							+ baseY + 8, null);
				} else if (status == 32) {
					g2d.drawImage(fussboden, x * 40 + baseX, y * 40 + baseY,
							null);
					g2d.drawImage(item_plus, x * 40 + baseX + 4, y * 40 + baseY
							+ 4, null);
				} else if (status == 64) {

					g2d.drawImage(fussboden, x * 40 + baseX, y * 40 + baseY,
							null);
					if (Ausgang.offen()) {
						g2d.drawImage(tor_offen, x * 40 + baseX,
								y * 40 + baseY, null);
					} else {
						g2d.drawImage(tor_zu, x * 40 + baseX, y * 40 + baseY,
								null);
					}

				} else if (status == 72) {

					g2d.drawImage(fels, x * 40 + baseX, y * 40 + baseY, null);

				} else if (status == 66) {

					g2d.drawImage(fussboden, x * 40 + baseX, y * 40 + baseY,
							null);
					if (Ausgang.offen()) {
						g2d.drawImage(tor_offen, x * 40 + baseX,
								y * 40 + baseY, null);
					} else {
						g2d.drawImage(tor_zu, x * 40 + baseX, y * 40 + baseY,
								null);
					}
					g2d.drawImage(bomberman, x * 40 + baseX, y * 40 + baseY,
							null);

				} else if (status == 128) {
					g2d.drawImage(fussboden, x * 40 + baseX, y * 40 + baseY,
							null);
					g2d.drawImage(item_key, x * 40 + baseX + 4, y * 40 + baseY
							+ 4, null);
				} else if (status == 512) {
					g2d.drawImage(fussboden, x * 40 + baseX, y * 40 + baseY,
							null);
					g2d.drawImage(bomberman2, x * 40 + baseX, y * 40 + baseY,
							null);

				} else if (status == 513) {

					g2d.drawImage(fussboden, x * 40 + baseX, y * 40 + baseY,
							null);
					g2d.drawImage(bombe, x * 40 + baseX, y * 40 + baseY, null);
					g2d.drawImage(bomberman2, x * 40 + baseX, y * 40 + baseY,
							null);

				} else if (status == 514) {
					g2d.drawImage(fussboden, x * 40 + baseX, y * 40 + baseY,
							null);
					g2d.drawImage(bomberman2, x * 40 + baseX, y * 40 + baseY,
							null);
					g2d.drawImage(bomberman, x * 40 + baseX, y * 40 + baseY,
							null);
				} else if (status == 515) {
					g2d.drawImage(fussboden, x * 40 + baseX, y * 40 + baseY,
							null);
					g2d.drawImage(bomberman, x * 40 + baseX, y * 40 + baseY,
							null);
					g2d.drawImage(bomberman2, x * 40 + baseX, y * 40 + baseY,
							null);

				} else if (status == 1024) {
					g2d.drawImage(fussboden, x * 40 + baseX, y * 40 + baseY,
							null);
					g2d.drawImage(item_speed, x * 40 + baseX + 4, y * 40
							+ baseY + 4, null);
				} else if (status == 2048) {
					g2d.drawImage(fussboden, x * 40 + baseX, y * 40 + baseY,
							null);
					g2d.drawImage(item_nuke, x * 40 + baseX + 4, y * 40 + baseY
							+ 4, null);
				}

				status = this.feldersicht[x][y];
				if (status < 4096) {
				} else if (status >= 4096 && status < 8192) {

					g2d.drawImage(flammenanfang, x * 40 + baseX,
							y * 40 + baseY, null);

				} else if (status >= 8192 && status < 16384) {

					g2d.drawImage(flammenmitte_rechts, x * 40 + baseX, y * 40
							+ baseY, null);

				} else if (status >= 16384 && status < 32768) {

					g2d.drawImage(flammenmitte_links, x * 40 + baseX, y * 40
							+ baseY, null);

				} else if (status >= 32768 && status < 65536) {

					g2d.drawImage(flammenmitte_oben, x * 40 + baseX, y * 40
							+ baseY, null);

				} else if (status >= 65536 && status < 131072) {

					g2d.drawImage(flammenmitte_unten, x * 40 + baseX, y * 40
							+ baseY, null);

				} else if (status >= 131072 && status < 262144) {

					g2d.drawImage(flammenende_rechts, x * 40 + baseX, y * 40
							+ baseY, null);

				} else if (status >= 262144 && status < 524288) {

					g2d.drawImage(flammenende_links, x * 40 + baseX, y * 40
							+ baseY, null);

				} else if (status >= 524288 && status < 1048576) {

					g2d.drawImage(flammenende_oben, x * 40 + baseX, y * 40
							+ baseY, null);

				} else if (status >= 1048576) {

					g2d.drawImage(flammenende_unten, x * 40 + baseX, y * 40
							+ baseY, null);

				}

			}
		}

	}

	/*
	 * Ueberprueft, ob es ein Objekt der Klasse Game (graphische Oberflaeche)
	 * gibt und falls nicht, erstellt dieses.
	 */
	public static Game getGUI() {
		if (GUI == null) {
			GUI = new Game();
		}
		return GUI;
	}

	/*
	 * Klasse zur Realisierung der Steuerung durch Schnittstelle Tastatur
	 */
	private class AL extends KeyAdapter {

		/*
		 * Abfrage durch Aufruf von keyRealeased, ob Tasten losgelassen sind.
		 * Bewegen mit "w", "s", "a", "d" und Bombe legen mit "e" (non-Javadoc)
		 * 
		 * @see java.awt.event.KeyAdapter#keyReleased(java.awt.event.KeyEvent)
		 */
		@Override
		public void keyTyped(KeyEvent e) {
			if (e.getKeyChar() == 'q') {
				FigurBomberman.getBomberman().zuenden = true;
			} else if (e.getKeyChar() == 'u') {
				FigurBomberman.getBomberman2().zuenden = true;
			} else if (e.getKeyChar() == 'a' || e.getKeyChar() == 's'
					|| e.getKeyChar() == 'w' || e.getKeyChar() == 'd'
					|| e.getKeyChar() == 'e') {
				key = e.getKeyChar();
			} else {
				key2 = e.getKeyChar();
			}
		}
	}
}

/*
 * Klasse, die sich um das Laden der Bilder kuemmert.
 */
final class ImageLoader {

	/*
	 * Standardkonstruktor
	 */
	private ImageLoader() {
	}

	/*
	 * Ueberprueft, ob Bild unter dem Namen vorhanden ist und laedt die Bilder
	 * aus der entsprechenden Datei.
	 */
	public static Image getImage(Class relativeClass, String filename) {
		Image returnValue = null;
		InputStream is = relativeClass.getResourceAsStream(filename);
		if (is != null) {
			BufferedInputStream bis = new BufferedInputStream(is);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			try {
				int ch;
				while ((ch = bis.read()) != -1) {
					baos.write(ch);
				}
				returnValue = Toolkit.getDefaultToolkit().createImage(
						baos.toByteArray());
			} catch (IOException exception) {
				System.err.println("Error loading: " + filename);
			}
		}
		return returnValue;
	}
}

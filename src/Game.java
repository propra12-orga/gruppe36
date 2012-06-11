import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;

import javax.swing.*;

public class Game extends JFrame implements ActionListener {
	private Container spielfeld = new Container();
	private Image nichts = ImageLoader.getImage(Game.class, "IMG//nichts.jpg");
	private Image bombe = ImageLoader.getImage(Game.class, "IMG//bombe.gif");
	private Image bomberman = ImageLoader.getImage(Game.class,
			"IMG//bomberman.gif");
	private Image bomberman2 = ImageLoader.getImage(Game.class, "IMG//bomberman2.gif");
	private Image fels = ImageLoader.getImage(Game.class, "IMG//fels.jpg");
	private Image flammenanfang = ImageLoader.getImage(Game.class,
			"IMG//flammenanfang.gif");
	private Image flammenende_links = ImageLoader.getImage(Game.class,
			"IMG//flammenende_links.gif");
	private Image flammenende_oben = ImageLoader.getImage(Game.class,
			"IMG//flammenende_oben.gif");
	private Image flammenende_rechts = ImageLoader.getImage(Game.class,
			"IMG//flammenende_rechts.gif");
	private Image flammenende_unten = ImageLoader.getImage(Game.class,
			"IMG//flammenende_unten.gif");
	private Image flammenmitte_links = ImageLoader.getImage(Game.class,
			"IMG//flammenmitte_links.gif");
	private Image flammenmitte_oben = ImageLoader.getImage(Game.class,
			"IMG//flammenmitte_oben.gif");
	private Image flammenmitte_rechts = ImageLoader.getImage(Game.class,
			"IMG//flammenmitte_rechts.gif");
	private Image flammenmitte_unten = ImageLoader.getImage(Game.class,
			"IMG//flammenmitte_unten.gif");
	private Image fussboden = ImageLoader.getImage(Game.class,
			"IMG//fussboden.jpg");
	private Image wand = ImageLoader.getImage(Game.class, "IMG//wand.jpg");
	private Image tor_offen = ImageLoader.getImage(Game.class,
			"IMG//tor_offen.gif");
	private Image schwarz = ImageLoader
			.getImage(Game.class, "IMG//schwarz.jpg");
	private Icon iTor_offen = new ImageIcon(tor_offen);
	private Icon iNichts = new ImageIcon(nichts);
	private Icon iBombe = new ImageIcon(bombe);
	private Icon iBomberman = new ImageIcon(bomberman);
	private Icon iBomberman2 = new ImageIcon(bomberman2);
	private Icon iFels = new ImageIcon(fels);
	private Icon iFlammenanfang = new ImageIcon(flammenanfang);
	private Icon iFlammenende_links = new ImageIcon(flammenende_links);
	private Icon iFlammenende_oben = new ImageIcon(flammenende_oben);
	private Icon iFlammenende_rechts = new ImageIcon(flammenende_rechts);
	private Icon iFlammenende_unten = new ImageIcon(flammenende_unten);
	private Icon iFlammenmitte_links = new ImageIcon(flammenmitte_links);
	private Icon iFlammenmitte_oben = new ImageIcon(flammenmitte_oben);
	private Icon iFlammenmitte_rechts = new ImageIcon(flammenmitte_rechts);
	private Icon iFlammenmitte_unten = new ImageIcon(flammenmitte_unten);
	private Icon iFussboden = new ImageIcon(fussboden);
	private Icon iWand = new ImageIcon(wand);
	private Icon iSchwarz = new ImageIcon(schwarz);
	private JLabel lTor_offen;
	private JLabel lNichts;
	private JLabel lBombe;
	private JLabel lBomberman;
	private JLabel lBomberman2;
	private JLabel lFels;
	private JLabel lFlammenanfang;
	private JLabel lFlammenende_links;
	private JLabel lFlammenende_oben;
	private JLabel lFlammenende_rechts;
	private JLabel lFlammenende_unten;
	private JLabel lFlammenmitte_links;
	private JLabel lFlammenmitte_oben;
	private JLabel lFlammenmitte_rechts;
	private JLabel lFlammenmitte_unten;
	private JLabel lWand;
	private JLabel lFussboden;
	private JLabel lSchwarz = new JLabel();

	private ArrayList<ArrayList<JLabel>> nichtsliste = new ArrayList<ArrayList<JLabel>>();
	private ArrayList<ArrayList<JLabel>> bombenliste = new ArrayList<ArrayList<JLabel>>();
	private ArrayList<ArrayList<JLabel>> felsliste = new ArrayList<ArrayList<JLabel>>();
	private ArrayList<ArrayList<JLabel>> bombermanliste = new ArrayList<ArrayList<JLabel>>();
	private ArrayList<ArrayList<JLabel>> bomberman2liste = new ArrayList<ArrayList<JLabel>>();
	private ArrayList<ArrayList<JLabel>> flammenanfangliste = new ArrayList<ArrayList<JLabel>>();
	private ArrayList<ArrayList<JLabel>> flammenende_linksliste = new ArrayList<ArrayList<JLabel>>();
	private ArrayList<ArrayList<JLabel>> flammenende_obenliste = new ArrayList<ArrayList<JLabel>>();
	private ArrayList<ArrayList<JLabel>> flammenende_rechtsliste = new ArrayList<ArrayList<JLabel>>();
	private ArrayList<ArrayList<JLabel>> flammenende_untenliste = new ArrayList<ArrayList<JLabel>>();
	private ArrayList<ArrayList<JLabel>> flammenmitte_linksliste = new ArrayList<ArrayList<JLabel>>();
	private ArrayList<ArrayList<JLabel>> flammenmitte_obenliste = new ArrayList<ArrayList<JLabel>>();
	private ArrayList<ArrayList<JLabel>> flammenmitte_rechtsliste = new ArrayList<ArrayList<JLabel>>();
	private ArrayList<ArrayList<JLabel>> flammenmitte_untenliste = new ArrayList<ArrayList<JLabel>>();
	private ArrayList<ArrayList<JLabel>> wandliste = new ArrayList<ArrayList<JLabel>>();
	private ArrayList<ArrayList<JLabel>> tor_offenliste = new ArrayList<ArrayList<JLabel>>();

	private int feldersicht[][] = new int[20][15];
	private static Game GUI;

	Timer time;

	private Game() {
		super("Bomberman");
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.setLayout(null);

		this.setExtendedState(MAXIMIZED_BOTH);
		this.setUndecorated(true);
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();

		int x = (d.width - 800) / 2;
		int y = (d.height - 600) / 2;

		this.spielfeld.setBounds(x, y, 800, 600);
		this.getContentPane().add(spielfeld);
		
		for (int j = 0; j < 20; j++) {
			this.nichtsliste.add(new ArrayList<JLabel>());
			this.bombenliste.add(new ArrayList<JLabel>());
			this.felsliste.add(new ArrayList<JLabel>());
			this.bombermanliste.add(new ArrayList<JLabel>());
			this.bomberman2liste.add(new ArrayList<JLabel>());
			this.flammenanfangliste.add(new ArrayList<JLabel>());
			this.flammenende_linksliste.add(new ArrayList<JLabel>());
			this.flammenende_obenliste.add(new ArrayList<JLabel>());
			this.flammenende_rechtsliste.add(new ArrayList<JLabel>());
			this.flammenende_untenliste.add(new ArrayList<JLabel>());
			this.flammenmitte_linksliste.add(new ArrayList<JLabel>());
			this.flammenmitte_obenliste.add(new ArrayList<JLabel>());
			this.flammenmitte_rechtsliste.add(new ArrayList<JLabel>());
			this.flammenmitte_untenliste.add(new ArrayList<JLabel>());
			this.wandliste.add(new ArrayList<JLabel>());
			this.tor_offenliste.add(new ArrayList<JLabel>());

			for (int i = 0; i < 15; i++) {
				this.lNichts = new JLabel();
				this.lFussboden = new JLabel();
				this.lBombe = new JLabel();
				this.lBomberman = new JLabel();
				this.lBomberman2 = new JLabel();
				this.lFels = new JLabel();
				this.lFlammenanfang = new JLabel();
				this.lFlammenende_links = new JLabel();
				this.lFlammenende_oben = new JLabel();
				this.lFlammenende_rechts = new JLabel();
				this.lFlammenende_unten = new JLabel();
				this.lFlammenmitte_links = new JLabel();
				this.lFlammenmitte_oben = new JLabel();
				this.lFlammenmitte_rechts = new JLabel();
				this.lFlammenmitte_unten = new JLabel();
				this.lWand = new JLabel();
				this.lTor_offen = new JLabel();
				this.lNichts.setIcon(iNichts);
				this.lBombe.setIcon(iBombe);
				this.lBomberman.setIcon(iBomberman);
				this.lBomberman2.setIcon(iBomberman2);
				this.lFlammenanfang.setIcon(iFlammenanfang);
				this.lFlammenende_links.setIcon(iFlammenende_links);
				this.lFlammenende_oben.setIcon(iFlammenende_oben);
				this.lFlammenende_rechts.setIcon(iFlammenende_rechts);
				this.lFlammenende_unten.setIcon(iFlammenende_unten);
				this.lFlammenmitte_links.setIcon(iFlammenmitte_links);
				this.lFlammenmitte_oben.setIcon(iFlammenmitte_oben);
				this.lFlammenmitte_rechts.setIcon(iFlammenmitte_rechts);
				this.lFlammenmitte_unten.setIcon(iFlammenmitte_unten);
				this.lWand.setIcon(iWand);
				this.lFels.setIcon(iFels);
				this.lFussboden.setIcon(iFussboden);
				this.lTor_offen.setIcon(iTor_offen);
				this.lNichts.setBounds(j * 40, i * 40, 40, 40);
				this.lBombe.setBounds(j * 40, i * 40, 40, 40);
				this.lBomberman.setBounds(j * 40, i * 40, 40, 40);
				this.lBomberman2.setBounds(j*40, i*40, 40, 40);
				this.lFlammenanfang.setBounds(j * 40, i * 40, 40, 40);
				this.lFlammenende_links.setBounds(j * 40, i * 40, 40, 40);
				this.lFlammenende_oben.setBounds(j * 40, i * 40, 40, 40);
				this.lFlammenende_rechts.setBounds(j * 40, i * 40, 40, 40);
				this.lFlammenende_unten.setBounds(j * 40, i * 40, 40, 40);
				this.lFlammenmitte_links.setBounds(j * 40, i * 40, 40, 40);
				this.lFlammenmitte_oben.setBounds(j * 40, i * 40, 40, 40);
				this.lFlammenmitte_rechts.setBounds(j * 40, i * 40, 40, 40);
				this.lFlammenmitte_unten.setBounds(j * 40, i * 40, 40, 40);
				this.lWand.setBounds(j * 40, i * 40, 40, 40);
				this.lFels.setBounds(j * 40, i * 40, 40, 40);
				this.lFussboden.setBounds(j * 40, i * 40, 40, 40);
				this.lTor_offen.setBounds(j * 40, i * 40, 40, 40);

				this.spielfeld.add(this.lFlammenanfang);
				this.spielfeld.add(this.lFlammenende_links);
				this.spielfeld.add(this.lFlammenende_oben);
				this.spielfeld.add(this.lFlammenende_rechts);
				this.spielfeld.add(this.lFlammenende_unten);
				this.spielfeld.add(this.lFlammenmitte_links);
				this.spielfeld.add(this.lFlammenmitte_oben);
				this.spielfeld.add(this.lFlammenmitte_rechts);
				this.spielfeld.add(this.lFlammenmitte_unten);
				this.spielfeld.add(this.lBomberman);
				this.spielfeld.add(this.lBomberman2);
				this.spielfeld.add(this.lBombe);
				this.spielfeld.add(this.lFels);
				this.spielfeld.add(this.lWand);
				this.spielfeld.add(this.lTor_offen);
				this.spielfeld.add(this.lNichts);
				this.spielfeld.add(this.lFussboden);
				this.lFlammenanfang.setVisible(false);
				this.lFlammenende_links.setVisible(false);
				this.lFlammenende_oben.setVisible(false);
				this.lFlammenende_rechts.setVisible(false);
				this.lFlammenende_unten.setVisible(false);
				this.lFlammenmitte_links.setVisible(false);
				this.lFlammenmitte_oben.setVisible(false);
				this.lFlammenmitte_rechts.setVisible(false);
				this.lFlammenmitte_unten.setVisible(false);
				this.lBomberman.setVisible(false);
				this.lBomberman2.setVisible(false);
				this.lBombe.setVisible(false);
				this.lFels.setVisible(false);
				this.lWand.setVisible(false);
				this.lNichts.setVisible(false);
				this.lTor_offen.setVisible(false);

				this.bombenliste.get(j).add(lBombe);
				this.felsliste.get(j).add(lFels);
				this.bombermanliste.get(j).add(lBomberman);
				this.bomberman2liste.get(j).add(lBomberman2);
				this.flammenanfangliste.get(j).add(lFlammenanfang);
				this.flammenende_linksliste.get(j).add(lFlammenende_links);
				this.flammenende_obenliste.get(j).add(lFlammenende_oben);
				this.flammenende_rechtsliste.get(j).add(lFlammenende_rechts);
				this.flammenende_untenliste.get(j).add(lFlammenende_unten);
				this.flammenmitte_linksliste.get(j).add(lFlammenmitte_links);
				this.flammenmitte_obenliste.get(j).add(lFlammenmitte_oben);
				this.flammenmitte_rechtsliste.get(j).add(lFlammenmitte_rechts);
				this.flammenmitte_untenliste.get(j).add(lFlammenmitte_unten);
				this.wandliste.get(j).add(lWand);
				this.nichtsliste.get(j).add(lNichts);
				this.tor_offenliste.get(j).add(lTor_offen);
			}
		}

		time = new Timer(10, this);
		time.start();

		this.addKeyListener(new AL());
		this.lSchwarz.setIcon(iSchwarz);
		this.lSchwarz.setBounds(0, 0, d.width, d.height);
		this.getContentPane().add(lSchwarz);
		this.setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		felderInit();
	}

	public void felderInit() {
		for(int y=0; y<15; y++){
			for(int x=0; x<20; x++){
				feldersicht[x][y]=Felder.game.felder[x][y];
			}
		}
		felderReload();
	}

	private void felderReload() {
		int status;
		for (int y = 0; y < 15; y++) {
			for (int x = 0; x < 20; x++) {
				status = this.feldersicht[x][y];
				if (status < 1024) {
					this.flammenanfangliste.get(x).get(y).setVisible(false);
					this.flammenende_linksliste.get(x).get(y).setVisible(false);
					this.flammenende_obenliste.get(x).get(y).setVisible(false);
					this.flammenende_rechtsliste.get(x).get(y)
							.setVisible(false);
					this.flammenende_untenliste.get(x).get(y).setVisible(false);
					this.flammenmitte_linksliste.get(x).get(y)
							.setVisible(false);
					this.flammenmitte_obenliste.get(x).get(y).setVisible(false);
					this.flammenmitte_rechtsliste.get(x).get(y)
							.setVisible(false);
					this.flammenmitte_untenliste.get(x).get(y)
							.setVisible(false);
				} else if (status >= 1024 && status < 2048) {
					this.flammenanfangliste.get(x).get(y).setVisible(true);
					this.flammenende_linksliste.get(x).get(y).setVisible(false);
					this.flammenende_obenliste.get(x).get(y).setVisible(false);
					this.flammenende_rechtsliste.get(x).get(y)
							.setVisible(false);
					this.flammenende_untenliste.get(x).get(y).setVisible(false);
					this.flammenmitte_linksliste.get(x).get(y)
							.setVisible(false);
					this.flammenmitte_obenliste.get(x).get(y).setVisible(false);
					this.flammenmitte_rechtsliste.get(x).get(y)
							.setVisible(false);
					this.flammenmitte_untenliste.get(x).get(y)
							.setVisible(false);
					status = status - 1024;
				} else if (status >= 2048 && status < 4096) {
					this.flammenanfangliste.get(x).get(y).setVisible(false);
					this.flammenende_linksliste.get(x).get(y).setVisible(false);
					this.flammenende_obenliste.get(x).get(y).setVisible(false);
					this.flammenende_rechtsliste.get(x).get(y)
							.setVisible(false);
					this.flammenende_untenliste.get(x).get(y).setVisible(false);
					this.flammenmitte_linksliste.get(x).get(y)
							.setVisible(false);
					this.flammenmitte_obenliste.get(x).get(y).setVisible(false);
					this.flammenmitte_rechtsliste.get(x).get(y)
							.setVisible(true);
					this.flammenmitte_untenliste.get(x).get(y)
							.setVisible(false);
					status = status - 2048;
				} else if (status >= 4096 && status < 8192) {
					this.flammenanfangliste.get(x).get(y).setVisible(false);
					this.flammenende_linksliste.get(x).get(y).setVisible(false);
					this.flammenende_obenliste.get(x).get(y).setVisible(false);
					this.flammenende_rechtsliste.get(x).get(y)
							.setVisible(false);
					this.flammenende_untenliste.get(x).get(y).setVisible(false);
					this.flammenmitte_linksliste.get(x).get(y).setVisible(true);
					this.flammenmitte_obenliste.get(x).get(y).setVisible(false);
					this.flammenmitte_rechtsliste.get(x).get(y)
							.setVisible(false);
					this.flammenmitte_untenliste.get(x).get(y)
							.setVisible(false);
					status = status - 4096;
				} else if (status >= 8192 && status < 16384) {
					this.flammenanfangliste.get(x).get(y).setVisible(false);
					this.flammenende_linksliste.get(x).get(y).setVisible(false);
					this.flammenende_obenliste.get(x).get(y).setVisible(false);
					this.flammenende_rechtsliste.get(x).get(y)
							.setVisible(false);
					this.flammenende_untenliste.get(x).get(y).setVisible(false);
					this.flammenmitte_linksliste.get(x).get(y)
							.setVisible(false);
					this.flammenmitte_obenliste.get(x).get(y).setVisible(true);
					this.flammenmitte_rechtsliste.get(x).get(y)
							.setVisible(false);
					this.flammenmitte_untenliste.get(x).get(y)
							.setVisible(false);
					status = status - 8192;
				} else if (status >= 16384 && status < 32768) {
					this.flammenanfangliste.get(x).get(y).setVisible(false);
					this.flammenende_linksliste.get(x).get(y).setVisible(false);
					this.flammenende_obenliste.get(x).get(y).setVisible(false);
					this.flammenende_rechtsliste.get(x).get(y)
							.setVisible(false);
					this.flammenende_untenliste.get(x).get(y).setVisible(false);
					this.flammenmitte_linksliste.get(x).get(y)
							.setVisible(false);
					this.flammenmitte_obenliste.get(x).get(y).setVisible(false);
					this.flammenmitte_rechtsliste.get(x).get(y)
							.setVisible(false);
					this.flammenmitte_untenliste.get(x).get(y).setVisible(true);
					status = status - 16384;
				} else if (status >= 32768 && status < 65536) {
					this.flammenanfangliste.get(x).get(y).setVisible(false);
					this.flammenende_linksliste.get(x).get(y).setVisible(false);
					this.flammenende_obenliste.get(x).get(y).setVisible(false);
					this.flammenende_rechtsliste.get(x).get(y).setVisible(true);
					this.flammenende_untenliste.get(x).get(y).setVisible(false);
					this.flammenmitte_linksliste.get(x).get(y)
							.setVisible(false);
					this.flammenmitte_obenliste.get(x).get(y).setVisible(false);
					this.flammenmitte_rechtsliste.get(x).get(y)
							.setVisible(false);
					this.flammenmitte_untenliste.get(x).get(y)
							.setVisible(false);
					status = status - 32768;
				} else if (status >= 65536 && status < 131072) {
					this.flammenanfangliste.get(x).get(y).setVisible(false);
					this.flammenende_linksliste.get(x).get(y).setVisible(true);
					this.flammenende_obenliste.get(x).get(y).setVisible(false);
					this.flammenende_rechtsliste.get(x).get(y)
							.setVisible(false);
					this.flammenende_untenliste.get(x).get(y).setVisible(false);
					this.flammenmitte_linksliste.get(x).get(y)
							.setVisible(false);
					this.flammenmitte_obenliste.get(x).get(y).setVisible(false);
					this.flammenmitte_rechtsliste.get(x).get(y)
							.setVisible(false);
					this.flammenmitte_untenliste.get(x).get(y)
							.setVisible(false);
					status = status - 65536;
				} else if (status >= 131072 && status < 262144) {
					this.flammenanfangliste.get(x).get(y).setVisible(false);
					this.flammenende_linksliste.get(x).get(y).setVisible(false);
					this.flammenende_obenliste.get(x).get(y).setVisible(true);
					this.flammenende_rechtsliste.get(x).get(y)
							.setVisible(false);
					this.flammenende_untenliste.get(x).get(y).setVisible(false);
					this.flammenmitte_linksliste.get(x).get(y)
							.setVisible(false);
					this.flammenmitte_obenliste.get(x).get(y).setVisible(false);
					this.flammenmitte_rechtsliste.get(x).get(y)
							.setVisible(false);
					this.flammenmitte_untenliste.get(x).get(y)
							.setVisible(false);
					status = status - 131072;
				} else if (status >= 262144) {
					this.flammenanfangliste.get(x).get(y).setVisible(false);
					this.flammenende_linksliste.get(x).get(y).setVisible(false);
					this.flammenende_obenliste.get(x).get(y).setVisible(false);
					this.flammenende_rechtsliste.get(x).get(y)
							.setVisible(false);
					this.flammenende_untenliste.get(x).get(y).setVisible(true);
					this.flammenmitte_linksliste.get(x).get(y)
							.setVisible(false);
					this.flammenmitte_obenliste.get(x).get(y).setVisible(false);
					this.flammenmitte_rechtsliste.get(x).get(y)
							.setVisible(false);
					this.flammenmitte_untenliste.get(x).get(y)
							.setVisible(false);
					status = status - 262144;
				}

				if (status == -1) {
					this.nichtsliste.get(x).get(y).setVisible(true);
					this.bombenliste.get(x).get(y).setVisible(false);
					this.wandliste.get(x).get(y).setVisible(false);
					this.felsliste.get(x).get(y).setVisible(false);
					this.tor_offenliste.get(x).get(y).setVisible(false);
					this.bombermanliste.get(x).get(y).setVisible(false);
					this.bomberman2liste.get(x).get(y).setVisible(false);
				} else if (status == 0) {
					this.nichtsliste.get(x).get(y).setVisible(false);
					this.bombenliste.get(x).get(y).setVisible(false);
					this.wandliste.get(x).get(y).setVisible(false);
					this.felsliste.get(x).get(y).setVisible(false);
					this.tor_offenliste.get(x).get(y).setVisible(false);
					this.bombermanliste.get(x).get(y).setVisible(false);
					this.bomberman2liste.get(x).get(y).setVisible(false);
				} else if (status == 1) {
					this.nichtsliste.get(x).get(y).setVisible(false);
					this.bombenliste.get(x).get(y).setVisible(true);
					this.wandliste.get(x).get(y).setVisible(false);
					this.felsliste.get(x).get(y).setVisible(false);
					this.tor_offenliste.get(x).get(y).setVisible(false);
					this.bombermanliste.get(x).get(y).setVisible(false);
					this.bomberman2liste.get(x).get(y).setVisible(false);
				} else if (status == 2) {
					this.nichtsliste.get(x).get(y).setVisible(false);
					this.bombenliste.get(x).get(y).setVisible(false);
					this.wandliste.get(x).get(y).setVisible(false);
					this.felsliste.get(x).get(y).setVisible(false);
					this.tor_offenliste.get(x).get(y).setVisible(false);
					this.bombermanliste.get(x).get(y).setVisible(true);
					this.bomberman2liste.get(x).get(y).setVisible(false);
				} else if (status == 3) {
					this.nichtsliste.get(x).get(y).setVisible(false);
					this.bombenliste.get(x).get(y).setVisible(true);
					this.wandliste.get(x).get(y).setVisible(false);
					this.felsliste.get(x).get(y).setVisible(false);
					this.tor_offenliste.get(x).get(y).setVisible(false);
					this.bomberman2liste.get(x).get(y).setVisible(false);
				} else if (status == 4) {
					this.nichtsliste.get(x).get(y).setVisible(false);
					this.wandliste.get(x).get(y).setVisible(true);
					this.bombenliste.get(x).get(y).setVisible(false);
					this.felsliste.get(x).get(y).setVisible(false);
					this.tor_offenliste.get(x).get(y).setVisible(false);
					this.bombermanliste.get(x).get(y).setVisible(false);
					this.bomberman2liste.get(x).get(y).setVisible(false);
				} else if (status == 8) {
					this.nichtsliste.get(x).get(y).setVisible(false);
					this.felsliste.get(x).get(y).setVisible(true);
					this.bombenliste.get(x).get(y).setVisible(false);
					this.wandliste.get(x).get(y).setVisible(false);
					this.tor_offenliste.get(x).get(y).setVisible(false);
					this.bombermanliste.get(x).get(y).setVisible(false);
					this.bomberman2liste.get(x).get(y).setVisible(false);
				} else if (status == 64) {
					this.nichtsliste.get(x).get(y).setVisible(false);
					this.felsliste.get(x).get(y).setVisible(false);
					this.bombenliste.get(x).get(y).setVisible(false);
					this.wandliste.get(x).get(y).setVisible(false);
					this.tor_offenliste.get(x).get(y).setVisible(true);
					this.bombermanliste.get(x).get(y).setVisible(false);
				} else if (status == 72) {
					this.nichtsliste.get(x).get(y).setVisible(false);
					this.felsliste.get(x).get(y).setVisible(true);
					this.bombenliste.get(x).get(y).setVisible(false);
					this.wandliste.get(x).get(y).setVisible(false);
					this.tor_offenliste.get(x).get(y).setVisible(true);
					this.bombermanliste.get(x).get(y).setVisible(false);
					this.bomberman2liste.get(x).get(y).setVisible(false);
				} else if (status == 66) {
					this.nichtsliste.get(x).get(y).setVisible(false);
					this.felsliste.get(x).get(y).setVisible(false);
					this.bombenliste.get(x).get(y).setVisible(false);
					this.wandliste.get(x).get(y).setVisible(false);
					this.tor_offenliste.get(x).get(y).setVisible(true);
					this.bomberman2liste.get(x).get(y).setVisible(false);
				}
				else if(status==512){
					this.nichtsliste.get(x).get(y).setVisible(false);
					this.felsliste.get(x).get(y).setVisible(false);
					this.bombenliste.get(x).get(y).setVisible(false);
					this.wandliste.get(x).get(y).setVisible(false);
					this.tor_offenliste.get(x).get(y).setVisible(false);
					this.bombermanliste.get(x).get(y).setVisible(false);
					this.bomberman2liste.get(x).get(y).setVisible(true);
				}
				else if (status == 513) {
					this.nichtsliste.get(x).get(y).setVisible(false);
					this.bombenliste.get(x).get(y).setVisible(true);
					this.wandliste.get(x).get(y).setVisible(false);
					this.felsliste.get(x).get(y).setVisible(false);
					this.tor_offenliste.get(x).get(y).setVisible(false);
					this.bombermanliste.get(x).get(y).setVisible(false);
					this.bomberman2liste.get(x).get(y).setVisible(true);
				}
				else if (status == 514) {
					this.nichtsliste.get(x).get(y).setVisible(false);
					this.bombenliste.get(x).get(y).setVisible(true);
					this.wandliste.get(x).get(y).setVisible(false);
					this.felsliste.get(x).get(y).setVisible(false);
					this.tor_offenliste.get(x).get(y).setVisible(false);
					this.bombermanliste.get(x).get(y).setVisible(true);
					this.bomberman2liste.get(x).get(y).setVisible(false);
				}

			}
		}

	}

	public static Game getGUI() {
		if (GUI == null) {
			GUI = new Game();
		}
		return GUI;
	}

	private class AL extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent e) {
			if (e.getKeyChar() == 'w') {
				FigurBomberman.getBomberman().bewegenOben();
			}
			if (e.getKeyChar() == 'a') {
				FigurBomberman.getBomberman().bewegenLinks();
			}
			if (e.getKeyChar() == 'd') {
				FigurBomberman.getBomberman().bewegenRechts();
			}
			if (e.getKeyChar() == 's') {
				FigurBomberman.getBomberman().bewegenUnten();
			}
			if (e.getKeyChar() == 'e') {
				FigurBomberman.getBomberman().bombeLegen();
			}
			
			if (e.getKeyChar() == 'i') {
				FigurBomberman2.getBomberman2().bewegenOben();
			}
			if (e.getKeyChar() == 'j') {
				FigurBomberman2.getBomberman2().bewegenLinks();
			}
			if (e.getKeyChar() == 'l') {
				FigurBomberman2.getBomberman2().bewegenRechts();
			}
			if (e.getKeyChar() == 'k') {
				FigurBomberman2.getBomberman2().bewegenUnten();
			}
			if (e.getKeyChar() == 'o') {
				FigurBomberman2.getBomberman2().bombeLegen();
			}

		}

	}

}

final class ImageLoader {

	private ImageLoader() {
	}

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

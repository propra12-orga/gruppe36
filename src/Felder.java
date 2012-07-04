//

/**
 * Die Klasse ist fuer das Spielfeld bzw. die einzelnen Felder verantwortlich. 
 * In einem Feld, das aus zwei Arraylisten besteht, kann sich jeweils ein Element befinden, das mehrere Subelemente haben kann.
 * Speichert in einem zweidimensionalen Array den veraenderlichen Status.
 * Enthaelt die gesamten Netzwerkfunktionen u.a. Server und Client.
 */
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.Timer;

/*
 * Besitzt ein 2D-Array fuer den jeweiligen Status, das Spielfeld, das aus einem verschachtelten Arraylisten besteht, ein statisches Objekt dieser Klasse Felder, einen Gamemaster, ein Objekt der Klasse Game, Sicht auf das Spielfeld f�r alle Spieler, einen Timer, der das Bild alle 10ms neu zeichnet.
 * Fuer Netzwerk: statisches Server und zwei Sockets sowie zwei Booleanwerte, die angeben, ob es sich generell um ein Netzwerkspiel handelt und dann, wer Server und Client ist.
 */
public class Felder implements ActionListener, Runnable {

	public int felder[][];
	public ArrayList<ArrayList<Element>> felderliste;
	public static Felder game;
	public static GameMaster spielemaster;
	public static Game GUI;
	protected int feldersicht[][] = new int[20][15];
	public Timer time;
	public static ServerSocket server;
	public Socket client;
	public Socket sServer;
	public boolean netzwerk = false;
	public boolean isServer = false;
	public boolean offlineMulti = false;

	/*
	 * Konstruktor, der das Spielfeld generiert
	 */
	public Felder(String dateiname, GameMaster master) {

		spielemaster = master;
		game = this;
		this.init(dateiname);
		GUI = Game.getGUI();
		GUI.frame.setVisible(true);
		time = new Timer(10, this);
		time.start();
	}

	public Felder(String dateiname, GameMaster master, boolean multi) {

		spielemaster = master;
		game = this;
		game.offlineMulti = multi;
		this.init(dateiname);
		GUI = Game.getGUI();
		GUI.frame.setVisible(true);
		time = new Timer(10, this);
		time.start();

	}

	/*
	 * Konstruktor, der einen Server erstellt. Wird das Level, einen Gamemaster
	 * und einen Port uebergeben.
	 */
	public Felder(String dateiname, GameMaster master, int Port)
			throws IOException { // Server
		spielemaster = master;
		game = this;
		netzwerk = true;
		isServer = true;
		this.init(dateiname);

		time = new Timer(10, this);
		server = new ServerSocket(Port);
		client = server.accept();

		GUI = Game.getGUI();
		GUI.frame.setVisible(true);

		Thread t1 = new Thread(this);
		t1.start();
		time.start();
	}

	/*
	 * Konstruktor, der einen Client erstellt. Ihm wird einen Gamemaster und die
	 * IP-Adresse des Clients uebergeben.
	 */
	public Felder(GameMaster master, Socket IPAddress)
			throws UnknownHostException, IOException { // Client
		spielemaster = master;
		game = this;
		GUI = Game.getGUI();

		time = new Timer(15, this);
		client = IPAddress;
		GUI = Game.getGUI();
		GUI.frame.setVisible(true);
		netzwerk = true;
		Thread t1 = new Thread(this);
		t1.start();
		time.start();
	}

	/*
	 * Runmethode des Interfaces Runnable. Verschickt die Pakete uebers Netzwerk.
	 * Ein Paket enthaelt die x- und y-Koordinate und den dazugehoerigen Status.
	 * Bei Sieg und Niederlage werden entsprechende Nachrichten an den Client
	 * geschickt und zuletzt die Verbindung geschlossen. Der Server fragt ab,
	 * welche Tasten der Client gedrueckt hat. (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	public void run() { // pakete verschicken
		if (!isServer) {
			while (netzwerk) {
				try {
					BufferedReader in = new BufferedReader(
							new InputStreamReader(client.getInputStream()));
					String messg;
					int start;
					char zeileC[];
					int ende;
					int j = 0;
					int x = 0;
					int y = 0;
					for (int i = 0; i < 15; i++) {
						messg = in.readLine();
						try {
							if (messg.compareTo("Spiel Sieg") != 0) {
								if (messg.compareTo("Spiel Ende") != 0) {
									zeileC = messg.toCharArray();
									start = 0;
									j = 0;

									for (int xx = 0; xx < messg.length(); xx++) {
										if (zeileC[xx] == '.') {
											ende = xx;

											if (j == 0) {
												x = (Integer.parseInt(String
														.valueOf(zeileC, start,
																ende - start)));
											}
											if (j == 1) {
												y = (Integer.parseInt(String
														.valueOf(zeileC, start,
																ende - start)));
											}
											if (j == 2) {
												feldersicht[x][y] = (Integer
														.parseInt(String
																.valueOf(
																		zeileC,
																		start,
																		ende
																				- start)));
											}
											j++;
											start = xx + 1;

										}
									}

								} else {
									FigurBomberman.getBomberman()
											.elementZerstoeren();
									netzwerk = false;
									i = 20;
								}
							} else {
								GUI.frame.setVisible(false);
								FigurBomberman.getBomberman().subelement = null;
								new Gewonnen(spielemaster);
								time.stop();
								netzwerk = false;
								i = 20;
							}
						} catch (NumberFormatException e) {
							// e.printStackTrace();
						}

					}
				} catch (IOException e1) {
					// e1.printStackTrace();
				}
			}
			OutputStream out;
			try {
				out = client.getOutputStream();
				out.write(5);
			} catch (IOException e) {
				e.printStackTrace();
			}
			netzwerk = true;

		} else {
			int input;
			while (netzwerk) {
				try {
					InputStream in = client.getInputStream();
					input = in.read();
					if (input == 0) {
						FigurBomberman.getBomberman2().bewegenLinks();
					}
					if (input == 1) {
						FigurBomberman.getBomberman2().bewegenRechts();
					}
					if (input == 2) {
						FigurBomberman.getBomberman2().bewegenOben();
					}
					if (input == 3) {
						FigurBomberman.getBomberman2().bewegenUnten();
					}
					if (input == 4) {
						FigurBomberman.getBomberman2().bombeLegen();
					}
					if (input == 5) {

						client.close();
						server.close();
						netzwerk = false;
					}

				} catch (IOException e) {
					// e.printStackTrace();
				}
			}
			netzwerk = true;
		}
	}

	/*
	 * Macht Fallunterscheidung je nachdem, ob man im Singleplayermodus ist oder
	 * eine Netzwerkanwendung hat. Bei Singleplayer: verschiebt Feldersicht. Bei
	 * Netzwerkanwendung zusaetzlich: wenn PC Server ist, schickt er neue
	 * Feldersicht an den Client; ist er Client, wartet er auf Nachrichten.
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		if (!netzwerk) {
			for (int y = 0; y < 15; y++) {
				for (int x = 0; x < 20; x++) {
					if (y >= 0 && x >= 0 && y < felderliste.get(0).size()
							&& x < felderliste.size()) {
						feldersicht[x][y] = felder[x][y];
					} else {
						feldersicht[x][y] = -1;
					}
				}
			}

			GUI.reload(feldersicht);
		} else if (isServer) {
			for (int y = 0; y < 15; y++) {
				for (int x = 0; x < 20; x++) {
					if (y >= 0 && x >= 0 && y < felderliste.get(0).size()
							&& x < felderliste.size()) {
						feldersicht[x][y] = felder[x][y];
					} else {
						feldersicht[x][y] = -1;
					}
				}
			}
			GUI.reload(feldersicht);
			try {
				PrintStream os = new PrintStream(client.getOutputStream());
				for (int y = 0; y < 15; y++) {
					for (int x = 0; x < 20; x++) {
						os.print(x + ".");
						os.print(y + ".");
						os.println(this.feldersicht[x][y] + ".");
					}

				}
			} catch (IOException e1) {
				// e1.printStackTrace();
			}

		} else if (!isServer) {
			// Auf nachricht vom Server warten und die Sicht aktualisieren
			/*
			 * try { BufferedReader in = new BufferedReader(new
			 * InputStreamReader( client.getInputStream()) ); String Messig ;
			 * Messig = in.readLine(); int x = Integer.parseInt(Messig); Messig
			 * = in.readLine(); int y = Integer.parseInt(Messig); Messig =
			 * in.readLine(); Feldersicht[x][y] = Integer.parseInt(Messig);
			 * /*int start; char zeileC []; int ende; int x; for(int i =0 ; i <
			 * 15; i++){ Messig = in.readLine(); zeileC = Messig.toCharArray();
			 * start = 0; x=0; for (int xx = 0; xx < Messig.length(); xx++) { if
			 * (zeileC[xx] == '.') { ende = xx; Feldersicht[x][i] =
			 * (Integer.parseInt(String.valueOf(zeileC, start, ende - start)));
			 * x++; start = xx + 1; } } System.out.println(Messig); }
			 * System.out.println(); } catch (IOException e1) {
			 * e1.printStackTrace(); }
			 */
			GUI.reload(feldersicht);
		}
	}

	/*
	 * Ueberprueft, ob sich ein Item auf dem von der Spielfigur zu verlassenen
	 * und dem neuem Feld befindet. Bomben und Ausgang bleiben auf dem Feld und
	 * ein Item wird als Subelement an die Figur angehaengt. Status des zu
	 * verlassenen Feldes wird neu berechnet. Elemente werden aus- und auf dem
	 * neu betretenen Feld eingetragen. Berechnet des gerade betretenen Feldes
	 * dementsprechend neu.
	 * 
	 * Startet die Ausloesung der Items.
	 */
	public synchronized boolean move(Beweglich move, int x, int y) {
		int status = felder[move.positionX + x][move.positionY + y];
		if (status != 4) {
			if (status != 8 && status != 1032 && status != 2056) {
				if (status != 24) {
					if (status != 40) {
						if (status != 72) {
							if (status != 1) {
								if (status == 2 && move.identifier == 256
										|| status != 2) {
									if (status != 3) {
										if (status != 136) {
											if ((status == 256 && move.identifier == 2)
													|| status != 512) {
												if (move.subelement != null) {
													// Wenn das subelement eine
													// Bombe oder der Ausgang
													// ist (Kommentare ueber
													// betreffender Zeile)
													if (move.subelement.identifier == 1
															|| move.subelement.identifier == 64
															|| move.subelement.identifier == 2
															|| move.subelement.identifier == 512) {
														// Wird das subelement
														// an
														// das zu verlassene
														// Feld
														// angehaengt
														felderliste
																.get(move.positionX)
																.set(move.positionY,
																		move.subelement);
														// Das subsubelement
														// wird
														// als subelement an
														// unseres Move-Element
														// angehaengt
														move.subelement = move.subelement.subelement;
														// Der Status des zu
														// verlassenen Feldes
														// wird
														// berechnet
														felder[move.positionX][move.positionY] = felderliste
																.get(move.positionX)
																.get(move.positionY)
																.getStatus();
														// Wenn wir ein
														// SubSubElement haben
													} else if (move.subelement.subelement != null) {
														// wird das subelement
														// zurueckgelassen
														felderliste
																.get(move.positionX)
																.set(move.positionY,
																		move.subelement);
														// Das Subsubelement
														// wird
														// als subelement von
														// unserem Move-Element
														// angehaengt
														move.subelement = move.subelement.subelement;
														felder[move.positionX][move.positionY] = felderliste
																.get(move.positionX)
																.get(move.positionY)
																.getStatus();
														// Wenn weder ein
														// Subsubelement
														// vorliegt
														// noch eine Bombe oder
														// ein
														// Ausgang als
														// subelement
													} else {
														// Wird das zu
														// verlassene
														// Feld null gesetzt
														felderliste
																.get(move.positionX)
																.set(move.positionY,
																		null);
														felder[move.positionX][move.positionY] = 0;
													}
												} else {
													felderliste
															.get(move.positionX)
															.set(move.positionY,
																	null);
													felder[move.positionX][move.positionY] = 0;
												}

												// auf der Zielposition liegt
												// ein
												// Element
												move.positionX = move.positionX
														+ x;
												move.positionY = move.positionY
														+ y;
												if (felderliste.get(
														move.positionX).get(
														move.positionY) != null) {

													/*
													 * if (felderliste.get(move.
													 * positionX +
													 * x).get(move.positionY +
													 * y).identifier == 128) {
													 * felderliste
													 * .get(move.positionX +
													 * x).set(move.positionY +
													 * y, null);
													 * felder[move.positionX +
													 * x][move.positionY + y] =
													 * 0; if (move ==
													 * FigurBomberman
													 * .getBomberman()) {
													 * FigurBomberman
													 * .getBomberman
													 * ().anzahlBomben++; } if
													 * (move ==
													 * FigurBomberman.getBomberman2
													 * ()) {
													 * FigurBomberman.getBomberman2
													 * ().anzahlBomben++; }
													 * felderliste
													 * .get(move.positionX +
													 * x).set(move.positionY +
													 * y, move); } else if
													 * (felderliste
													 * .get(move.positionX +
													 * x).get(move.positionY +
													 * y).identifier == 144) {
													 * System
													 * .out.println(felderliste
													 * .get(move.positionX +
													 * x).get(move.positionY +
													 * y).identifier);
													 * felderliste
													 * .get(move.positionX +
													 * x).set(move.positionY +
													 * y, null);
													 * felder[move.positionX
													 * ][move.positionY] = 0; if
													 * (move ==
													 * FigurBomberman.getBomberman
													 * ()) { if
													 * (FigurBomberman.getBomberman
													 * ().anzahlBomben > 1) {
													 * FigurBomberman
													 * .getBomberman
													 * ().anzahlBomben--; } } if
													 * (move ==
													 * FigurBomberman.getBomberman2
													 * ()) { if
													 * (FigurBomberman.getBomberman2
													 * ().anzahlBomben > 1) {
													 * FigurBomberman
													 * .getBomberman2
													 * ().anzahlBomben--; } } }
													 * else if
													 * (felderliste.get(move
													 * .positionX +
													 * x).get(move.positionY +
													 * y).identifier == 160) {
													 * felderliste
													 * .get(move.positionX +
													 * x).set(move.positionY +
													 * y, null);
													 * felder[move.positionX
													 * ][move.positionY] = 0; if
													 * (move ==
													 * FigurBomberman.getBomberman
													 * ()) {
													 * FigurBomberman.getBomberman
													 * ().bombenrange++; } if
													 * (move ==
													 * FigurBomberman.getBomberman2
													 * ()) {
													 * FigurBomberman.getBomberman2
													 * ().bombenrange++; } }
													 * else {
													 */
													felderliste
															.get(move.positionX)
															.get(move.positionY).subelement = move.subelement;
													move.subelement = felderliste
															.get(move.positionX)
															.set(move.positionY,
																	move);
													// }
												} else {
													felderliste
															.get(move.positionX)
															.set(move.positionY,
																	move);
												}

												felder[move.positionX][move.positionY] = move
														.getStatus();

												if ((move.identifier == 2 && move.subelement != null)
														|| (move.identifier == 512 && move.subelement != null)) {
													// Item/Ausgangseffekte
													// ausloesen
													if (move.subelement.identifier == 64) {
														((Ausgang) move.subelement)
																.betreten();
													} else if (move.subelement.identifier == 16) {
														((Item) move.subelement)
																.betreten((FigurBomberman) move);
													} else if (move.subelement.identifier == 32) {
														((Item) move.subelement)
																.betreten((FigurBomberman) move);
													} else if (move.subelement.identifier == 128) {
														((Item) move.subelement)
																.betreten((FigurBomberman) move);
													} else if (move.subelement.identifier == 1024) {
														((Item) move.subelement)
																.betreten((FigurBomberman) move);
													} else if (move.subelement.identifier == 2048) {
														((Item) move.subelement)
																.betreten((FigurBomberman) move);
													}

												}
												return true;
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
		return false;
	}

	/*
	 * Berechnet, wenn etwas auf dem Feld liegt, den Status und schreibt diesen
	 * in die Arrayliste. Eigene Statusfunktion fuer die Klasse Felder.
	 */
	public int getStatus(int x, int y) {
		if (x >= 0 && y >= 0) {
			if (x < felderliste.size() && x >= 0) {
				if (y < felderliste.get(x).size() && y >= 0) {
					if (felderliste.get(x) != null) {
						if (felderliste.get(x).get(y) != null) {
							return felderliste.get(x).get(y).getStatus();
						}
					}
				} else {
					return -1;
				}
			} else {
				return -1;
			}
		}
		return 0;
	}

	/*
	 * Gibt den Status der einzelnen Felder aus.
	 */
	public void printFelder() {
		for (int y = 0; y < this.felderliste.get(0).size(); y++) {
			for (int x = 0; x < this.felderliste.size(); x++) {
				System.out.print(this.getStatus(x, y));
			}
			System.out.println();
		}
		System.out.println();
	}

	/*
	 * Loescht Elemente aus der Arraylist und berechnet den Status neu.
	 */
	public boolean feldZerstoeren(int x, int y) {
		boolean temp = true;
		if (felderliste.get(x).get(y) != null) {
			temp = felderliste.get(x).get(y).elementZerstoeren();
			if (felderliste.get(x).get(y) != null) {
				felder[x][y] = felderliste.get(x).get(y).getStatus();
			} else {
				felder[x][y] = 0;
			}
		}
		return temp;
	}

	/*
	 * Fuegt Elemente in die Arraylist ein und berechnet den Status neu.
	 */
	public void addToFeld(Element add, int x, int y) {
		felderliste.get(x).remove(y);

		felderliste.get(x).add(y, add);
		if (add != null) {
			felder[x][y] = add.getStatus();
		} else {
			felder[x][y] = 0;
		}
	}

	/*
	 * Laedt Level aus einer Datei. Liest aus der ersten Zeile die Gr��e des
	 * Levels und aus der zweiten das darauffolgende Level aus. Liest Zeile fuer
	 * Zeile die Identifier aus, die durch einen "." getrennt sind und laesst
	 * das entsprechende Bild zeichnen.
	 */
	private void init(String FileName) {
		try {
			BufferedReader in = new BufferedReader(new FileReader(FileName));
			String zeile = null;
			int posX = 0, posY = 0;
			boolean Bomberman = false;
			zeile = in.readLine();
			char c[] = new char[10];
			int start = 0;
			int ende;
			int j = 0;
			c = zeile.toCharArray();
			for (int i = 0; i < zeile.length(); i++) {
				if (c[i] == '.' && j == 0) {
					ende = i;
					posX = (Integer.parseInt(String.valueOf(c, start, ende
							- start)));
					j++;
					start = i + 1;

				} else if (c[i] == '.') {
					ende = i;
					posY = (Integer.parseInt(String.valueOf(c, start, ende
							- start)));
				}
			}
			felder = new int[posX][posY];
			felderliste = new ArrayList<ArrayList<Element>>();
			for (int i = 0; i < posX; i++) {
				felderliste.add(new ArrayList<Element>());
				for (int a = 0; a < posY; a++) {
					felderliste.get(i).add(null);
					felder[i][a] = 0;
				}
			}
			char zeileC[] = new char[4 * posX];
			int zahlen[] = new int[posX];
			int y = 0;
			Element elem = null;
			zeile = in.readLine();

			Felder.spielemaster.nextLvl = zeile;

			zeile = in.readLine();

			while (empty(zeile)) {

				zeileC = zeile.toCharArray();
				start = 0;
				j = 0;
				for (int i = 0; i < zeile.length(); i++) {
					if (zeileC[i] == '.') {
						ende = i;
						zahlen[j] = (Integer.parseInt(String.valueOf(zeileC,
								start, ende - start)));
						j++;
						start = i + 1;
					}
				}
				zahlen[posX - 1] = 4;
				for (int i = 0; i < posX; i++) {
					if (zahlen[i] == 4) {
						elem = new Element(i, y, 4);

					}
					if (zahlen[i] == 8) {
						elem = new Element(i, y, 8);
						int zufall = (int) (Math.random() * 100) % 5;

						if (zufall == 0) {
							elem.subelement = new Item(i, y, 16);
						} else if (zufall == 1) {
							elem.subelement = new Item(i, y, 32);
						} else if (zufall == 2) {
							elem.subelement = new Item(i, y, 1024);
						} else if (zufall == 3) {
							elem.subelement = new Item(i, y, 2048);
						} else if (zufall == 4) {
						}
					}
					if (zahlen[i] == 72) {
						elem = new Element(i, y, 8);
						elem.subelement = new Ausgang(i, y);
					}
					if (zahlen[i] == 64) {
						elem = new Ausgang(i, y);
					}
					if (zahlen[i] == 2) {
						if (!Bomberman) {
							elem = FigurBomberman.getBomberman();
							Bomberman = true;
						} else {
							elem = FigurBomberman.getBomberman2();
						}
						elem.positionX = i;
						elem.positionY = y;

					}
					if (zahlen[i] == 512) {
						if (offlineMulti == true || netzwerk == true) {
							elem = FigurBomberman.getBomberman2();
						} else {
							elem = CP.getPlayer();
							CP.thisThread = true;
							Thread th1 = new Thread((CP) CP.getCP());
							th1.start();
						}
						elem.positionX = i;
						elem.positionY = y;
					}

					if (zahlen[i] != 0) {
						this.addToFeld(elem, i, y);
					}

				}
				zeile = in.readLine();
				y++;

			}
		} catch (IOException e) {
			e.printStackTrace();

		}
	}

	/*
	 * Methode fuer den Computergegner, die uerprueft, ob eine Bombe auf einem
	 * Feld im direkten Umkreis liegt und wie weit die Reichweite ist, damit
	 * dieser der Bombe ausweichen kann.
	 */
	public int getBombe(int x, int y) {
		if (x >= 0 && y >= 0) {
			if (x < felderliste.size() && x >= 0) {
				if (y < felderliste.get(x).size() && y >= 0) {
					if (felderliste.get(x) != null) {
						if (felderliste.get(x).get(y) != null) {
							if (felderliste.get(x).get(y).identifier == 1) {
								return ((Bombe) felderliste.get(x).get(y)).sprengkraft;
							}
							if (felderliste.get(x).get(y).identifier == 3) {
								return ((Bombe) felderliste.get(x).get(y).subelement).sprengkraft;
							}
						}
					}
				}
			}
		}
		return 0;
	}

	/*
	 * Ueberprueft, ob Zeile der Datei leer ist.
	 */
	static boolean empty(String z) {
		if (z != null) {
			if (z.length() != 0) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
}

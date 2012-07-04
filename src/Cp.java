/**
 * Klasse der Computergegner, die in einem Thread realisiert wird.
 * Dieser Gegner kann ebenfalls wie die Spieler Bomben legen.
 */
import java.util.ArrayList;

/*
 * Besitzt einen Spieler, eine Instanz dieser Klasse als Gegner, zwei Booleanwerte: einen fuer das Laufen und einen fuer das Beenden des Threads, einen String fuer die Bewegungsrichtung. 
 */
public class CP implements Runnable {
	private static FigurBomberman player;
	private static CP computer;
	public static boolean thisThread = true;
	public boolean done = false;
	public String richtung;

	/*
	 * Standardkonstruktor
	 */
	CP() {
		thisThread = true;
		player = FigurBomberman.getBomberman2();
	}

	/*
	 * Getmethode, die, falls vorhanden, den Spieler zurueckliefert.
	 */
	public static FigurBomberman getPlayer() {
		if (computer == null) {
			computer = new CP();
		}
		return player;
	}

	/*
	 * Getmethode, die, falls nicht vorhanden, den Spieler erstellt und
	 * zurueckliefert.
	 */
	public static CP getCP() {
		if (computer == null) {
			computer = new CP();
		}
		return computer;
	}

	/*
	 * Runmethode für den Thread. (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	public void run() {
		while (thisThread) {
			try {
				Thread.sleep(200 + 200 * player.seedteiler);
				if (thisThread) {
					doMove();
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	/*
	 * Bewegungsfunktionen und -methode. Wenn auf ein Hindernis gestossen wird,
	 * wird die Richtung neu entschieden. Ist in Prioritaeten aufgeteilt: 1.
	 * Bomben ausweichen; 2. Bomben legen; 3. laufen. Hat die Position des
	 * Spielers und einen Booleanwert für die Beendiung des Laufens.
	 */
	public boolean doMove() {
		int x = player.positionX;
		int y = player.positionY;
		// erste prioritaet: ueberleben
		done = false;
		if (Felder.game.getStatus(x, y) == 513) {
			richtung = richtungEntscheiden();
		} else if (Felder.game.getStatus(x - 1, y) == 1
				|| Felder.game.getStatus(x - 1, y) == 3
				|| (Felder.game.getStatus(x - 2, y) == 1 && Felder.game
						.getBombe(x - 2, y) > 1)
				|| (Felder.game.getStatus(x - 2, y) == 3 && Felder.game
						.getBombe(x - 2, y) > 1)
				|| (Felder.game.getStatus(x - 3, y) == 1 && Felder.game
						.getBombe(x - 3, y) > 2)
				|| (Felder.game.getStatus(x - 3, y) == 3 && Felder.game
						.getBombe(x - 3, y) > 2)
				|| (Felder.game.getStatus(x - 4, y) == 1 && Felder.game
						.getBombe(x - 4, y) > 3)
				|| (Felder.game.getStatus(x - 4, y) == 3 && Felder.game
						.getBombe(x - 4, y) > 3)
				|| (Felder.game.getStatus(x - 5, y) == 1 && Felder.game
						.getBombe(x - 5, y) > 4)
				|| (Felder.game.getStatus(x - 5, y) == 3 && Felder.game
						.getBombe(x - 5, y) > 4)) {
			if (richtung == null || richtung.equals("links")) {
				richtung = richtungEntscheiden("links");
				done = true;
			} else {
				step(richtung);
				done = true;
			}
		} else if (Felder.game.getStatus(x + 1, y) == 1
				|| Felder.game.getStatus(x + 1, y) == 3
				|| (Felder.game.getStatus(x + 2, y) == 1 && Felder.game
						.getBombe(x + 2, y) > 1)
				|| (Felder.game.getStatus(x + 2, y) == 3 && Felder.game
						.getBombe(x + 2, y) > 1)
				|| (Felder.game.getStatus(x + 3, y) == 1 && Felder.game
						.getBombe(x + 3, y) > 2)
				|| (Felder.game.getStatus(x + 3, y) == 3 && Felder.game
						.getBombe(x + 3, y) > 2)
				|| (Felder.game.getStatus(x + 4, y) == 1 && Felder.game
						.getBombe(x + 4, y) > 3)
				|| (Felder.game.getStatus(x + 4, y) == 3 && Felder.game
						.getBombe(x + 4, y) > 3)
				|| (Felder.game.getStatus(x + 5, y) == 1 && Felder.game
						.getBombe(x + 5, y) > 4)
				|| (Felder.game.getStatus(x + 5, y) == 3 && Felder.game
						.getBombe(x + 5, y) > 4)) {
			if (richtung == null || richtung.equals("rechts")) {
				richtung = richtungEntscheiden("rechts");
				done = true;
			} else {
				step(richtung);
				done = true;
			}
		} else if (Felder.game.getStatus(x, y - 1) == 1
				|| Felder.game.getStatus(x, y - 1) == 3
				|| (Felder.game.getStatus(x, y - 2) == 1 && Felder.game
						.getBombe(x, y - 2) > 1)
				|| (Felder.game.getStatus(x, y - 2) == 3 && Felder.game
						.getBombe(x, y - 2) > 1)
				|| (Felder.game.getStatus(x, y - 3) == 1 && Felder.game
						.getBombe(x, y - 3) > 2)
				|| (Felder.game.getStatus(x, y - 3) == 3 && Felder.game
						.getBombe(x, y - 3) > 2)
				|| (Felder.game.getStatus(x, y - 4) == 1 && Felder.game
						.getBombe(x, y - 4) > 3)
				|| (Felder.game.getStatus(x, y - 4) == 3 && Felder.game
						.getBombe(x, y - 4) > 3)
				|| (Felder.game.getStatus(x, y - 5) == 1 && Felder.game
						.getBombe(x, y - 5) > 4)
				|| (Felder.game.getStatus(x, y - 5) == 3 && Felder.game
						.getBombe(x, y - 5) > 4)) {
			while (richtung == null || richtung.equals("oben")) {
				richtung = richtungEntscheiden("oben");
			}
			done = true;
		} else if (Felder.game.getStatus(x, y - 1) == 1
				|| Felder.game.getStatus(x, y - 1) == 3
				|| (Felder.game.getStatus(x, y - 2) == 1 && Felder.game
						.getBombe(x, y + 2) > 1)
				|| (Felder.game.getStatus(x, y - 2) == 3 && Felder.game
						.getBombe(x, y + 2) > 1)
				|| (Felder.game.getStatus(x, y - 3) == 1 && Felder.game
						.getBombe(x, y + 3) > 2)
				|| (Felder.game.getStatus(x, y - 3) == 3 && Felder.game
						.getBombe(x, y + 3) > 2)
				|| (Felder.game.getStatus(x, y - 4) == 1 && Felder.game
						.getBombe(x, y + 4) > 3)
				|| (Felder.game.getStatus(x, y - 4) == 3 && Felder.game
						.getBombe(x, y + 4) > 3)
				|| (Felder.game.getStatus(x, y - 5) == 1 && Felder.game
						.getBombe(x, y + 5) > 4)
				|| (Felder.game.getStatus(x, y - 5) == 3 && Felder.game
						.getBombe(x, y + 5) > 4)) {
			while (richtung == null || richtung.equals("unten")) {
				richtung = richtungEntscheiden("unten");
			}
			done = true;
		}
		else{
			if(player.fernzuender){
				player.zuenden=true;
			}
		}
		
		// zweite prioritaet: bomben legen
		if (!done) {
			// links pruefen
			if (Felder.game.felderliste.get(x - 1) != null) {
				if (Felder.game.felderliste.get(x - 1).get(y) != null) {
					int leftState = Felder.game.felderliste.get(x - 1).get(y).status;
					switch (leftState) {
					case 8:
					case 24:
					case 40:
					case 72:
					case 1032:
					case 2056:
						player.bombeLegen();
						done = true;
						break;
					default:
					}
				}
			}
			// rechts pruefen
			if (Felder.game.felderliste.get(x + 1) != null) {
				if (Felder.game.felderliste.get(x + 1).get(y) != null) {
					int rightState = Felder.game.felderliste.get(x + 1).get(y).status;
					switch (rightState) {
					case 8:
					case 24:
					case 40:
					case 72:
					case 1032:
					case 2056:
						player.bombeLegen();
						done = true;
						break;
					default:
					}
				}
			}
			// oben pruefen
			if (Felder.game.felderliste.get(x) != null) {
				if (Felder.game.felderliste.get(x).get(y - 1) != null) {
					int rightState = Felder.game.felderliste.get(x).get(y - 1).status;
					switch (rightState) {
					case 8:
					case 24:
					case 40:
					case 72:
					case 1032:
					case 2056:
						player.bombeLegen();
						done = true;
						break;
					default:
					}
				}
			}
			// unten pruefen
			if (Felder.game.felderliste.get(x) != null) {
				if (Felder.game.felderliste.get(x).get(y + 1) != null) {
					int rightState = Felder.game.felderliste.get(x).get(y + 1).status;
					switch (rightState) {
					case 8:
					case 24:
					case 40:
					case 72:
					case 1032:
					case 2056:
						player.bombeLegen();
						done = true;
						break;
					default:
					}
				}
			}
		}
		// wenn schritt noch nicht done
		if (!done) {
			// in ca. 50% der faelle bei einer kreuzung die richtung wechseln
			// gibt es mehr als zwei wege?
			int wege = 0;
			// links pruefen
			if (Felder.game.getStatus(x - 1, y) == 0) {
				wege++;
			}
			// rechts pruefen
			if (Felder.game.getStatus(x + 1, y) == 0) {
				wege++;
			}
			// oben pruefen
			if (Felder.game.getStatus(x, y - 1) == 0) {
				wege++;
			}
			// unten pruefen
			if (Felder.game.getStatus(x, y + 1) == 0) {
				wege++;
			}
			int zufall = (int) (Math.random() * 100);
			if (zufall < 50) {
				if (wege > 2) {
					richtung = richtungEntscheiden();
					done = true;
				}
			}
		}

		// schritt in richtung gehen
		if (!done) {
			boolean schritt = false;
			if (richtung == null) {
				richtung = richtungEntscheiden();
			} else if (richtung.equals("links")) {
				schritt = player.bewegenLinks();
			} else if (richtung.equals("rechts")) {
				schritt = player.bewegenRechts();
			} else if (richtung.equals("oben")) {
				schritt = player.bewegenOben();
			} else if (richtung.equals("unten")) {
				schritt = player.bewegenUnten();
			}
			if (!schritt) {
				richtung = richtungEntscheiden();
			}
			done = true;
		}
		if (Felder.game.getStatus(x + 1, y) == 2
				|| Felder.game.getStatus(x - 1, y) == 2
				|| Felder.game.getStatus(x, y + 1) == 2
				|| Felder.game.getStatus(x, y - 1) == 2) {
			player.bombeLegen();
		}
		return done;
	}

	/*
	 * Methode fuer Richtunsgwechsel (im Prinzip ueberfluessig).
	 */
	public String richtungWechseln(String richtung) {
		String neu;
		if (richtung == "rechts") {
			neu = "links";
		}
		if (richtung == "links") {
			neu = "rechts";
		}
		if (richtung == "oben") {
			neu = "unten";
		}
		if (richtung == "unten") {
			neu = "oben";
		}
		if (richtung == null) {
			neu = "links";
		} else
			return "links";
		return neu;
	}

	/*
	 * Tatsaechliche Bewegungsfunktion, die auf die Bewegungsfunktion des
	 * Spieler aus der Klasse FigurBomberman zurueckgeht.
	 */
	public boolean step(String richtung) {
		boolean schritt = false;
		if (richtung == null) {
			richtung = richtungEntscheiden();
		} else if (richtung.equals("links")) {
			schritt = player.bewegenLinks();
		} else if (richtung.equals("rechts")) {
			schritt = player.bewegenRechts();
		} else if (richtung.equals("oben")) {
			schritt = player.bewegenOben();
		} else if (richtung.equals("unten")) {
			schritt = player.bewegenUnten();
		}
		if (!schritt) {
			richtung = richtungEntscheiden();
		}
		return true;
	}

	/*
	 * Methode, die nach einer Zufallszahl zwischen 0 und 3 entscheidet, in
	 * welche Richtung der CP geht.
	 */
	public String richtungEntscheiden() {
		String neueRichtung = "";
		boolean schritt = true;
		for (int i = 0; i < 20; i++) {
			int zufall = (int) (Math.random() * 100) % 4;
			switch (zufall) {
			case 0:
				schritt = player.bewegenLinks();
				neueRichtung = "links";
				break;
			case 1:
				schritt = player.bewegenRechts();
				neueRichtung = "rechts";
				break;
			case 2:
				schritt = player.bewegenOben();
				neueRichtung = "oben";
				break;
			case 3:
				schritt = player.bewegenUnten();
				neueRichtung = "unten";
				break;
			}
			if (schritt) {
				break;
			}
		}
		return neueRichtung;
	}

	/*
	 * Methode, der ein String für die "Nichtrichtung" uebergeben wird. Im
	 * Gegensatz zu richtungEntscheiden legt sie fest, in welche Richtung der CP
	 * nicht laufen soll.
	 */
	public String richtungEntscheiden(String Not) {
		String neueRichtung = "";
		boolean schritt = true;
		for (int i = 0; i < 20; i++) {
			int zufall = (int) (Math.random() * 100) % 4;
			switch (zufall) {
			case 0:
				if (!Not.equals("links")) {
					schritt = player.bewegenLinks();
					neueRichtung = "links";
				}
				break;
			case 1:
				if (!Not.equals("rechts")) {
					schritt = player.bewegenRechts();
					neueRichtung = "rechts";
				}
				break;
			case 2:
				if (!Not.equals("links")) {
					schritt = player.bewegenOben();
					neueRichtung = "oben";
				}
				break;
			case 3:
				if (!Not.equals("links")) {
					schritt = player.bewegenUnten();
					neueRichtung = "unten";
				}
				break;
			}
			if (schritt) {
				break;
			}
		}
		return neueRichtung;
	}
}

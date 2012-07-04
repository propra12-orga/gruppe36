/**
 * Die Klasse erstellt den jeweiligen Spieler des Spiels und legt die Anzahl und Art der Bomben fest, die Bomberman hat und eventuell haben koennte.
 * Ermoeglicht das Steuern der Figur und besitzt die "Sterbenfunktion".
 */

import java.io.IOException;
import java.io.PrintStream;

public final class FigurBomberman extends Beweglich {

	/*
	 * Hat bei Singleplayer einen Spieler und bei Netzwerkspielen zwei Spieler.
	 * Reichweite der Bombe (Einheit: pro Feld), die Anzahl an Bombe, die
	 * Bomberman maximal gleichzeitig legen kann. Momentane Anzahl der Bombe,
	 * die in der Zeit zwischen Legen und Explodieren bei 0 ist. Geschwindigkeit
	 * des Laufen wird festgelegt, ebenso wie das Vorhandensein von
	 * Fernzuendern. Hierbei laeuft jeweils ein Timer bis zur Detonation ab.
	 */
	private static FigurBomberman bomberman;
	private static FigurBomberman bomberman2;
	public int bombenrange;
	public int maxBomben;
	public int anzahlBomben;
	public int seedteiler;
	public int speedteileruse;
	public boolean fernzuender;
	public boolean zuenden;
	public int verzuendertime;

	/*
	 * Standardkonstruktor: Reichweite und Anzahlen jeweils bei 1,
	 * ferngezuendete Bomben bei 0.
	 */
	private FigurBomberman() {
		super(0, 0, 2);
		bombenrange = 1;
		maxBomben = 4;
		anzahlBomben = 1;
		seedteiler = 1;
		speedteileruse = 0;
		fernzuender = false;
		zuenden = false;
		verzuendertime = 0;
	}

	/*
	 * Konstruktor, dem ein intWert uebergeben wird zur Unterscheidung zwischen
	 * Spieler1 und Spieler2.
	 */
	private FigurBomberman(int typ) {
		super(0, 0, typ);
		bombenrange = 1;
		maxBomben = 4;
		anzahlBomben = 1;
		seedteiler = 1;
		speedteileruse = 0;
		fernzuender = false;
		zuenden = false;
		verzuendertime = 0;
	}

	/*
	 * Bei Singleplayer: Konstruktor, dem die Position von Bomberman uebergeben
	 * wird, der den Konstruktor der Oberklasse Beweglich aufruft.
	 */
	private FigurBomberman(int positionX, int positionY) {
		super(positionX, positionY, 2);
		bombenrange = 1;
		maxBomben = 4;
		anzahlBomben = 1;
		seedteiler = 1;
		speedteileruse = 0;
		fernzuender = false;
		zuenden = false;
		verzuendertime = 0;
	}

	/*
	 * Konstruktor fuer den Multiplayer zur Unterscheidung zwischen Spieler1 und
	 * Spieler2.
	 */
	private FigurBomberman(int positionX, int positionY, int typ) {
		super(positionX, positionY, typ);
		bombenrange = 1;
		maxBomben = 4;
		anzahlBomben = 1;
		seedteiler = 1;
		speedteileruse = 0;
		fernzuender = false;
		zuenden = false;
		verzuendertime = 0;
	}

	/*
	 * Bei Singleplayer: ueberprueft, ob es diesen einen Bomberman gibt, der
	 * ansonsten erstellt und zurueckgegeben wird. Bomberman steht
	 * standardmaessig bei 0/0.
	 */
	public static FigurBomberman getBomberman() {
		if (bomberman == null) {
			bomberman = new FigurBomberman(0, 0);
		}
		return bomberman;
	}

	/*
	 * Bei Singleplayer: Erstellt bei nichtvorhandenem Bomberman diesen und
	 * liefert ihn zurueck.
	 */
	public static FigurBomberman createBomberman(int positionX, int positionY) {
		if (bomberman == null) {
			bomberman = new FigurBomberman(positionX, positionY);
		}
		return bomberman;
	}

	/*
	 * Laesst die Bombermans sterben.
	 */
	public static void destroy() {
		bomberman = null;
		bomberman2 = null;
	}

	/*
	 * Bei Multiplayer: ueberprueft, ob es diesen einen Bomberman gibt, der
	 * ansonsten erstellt und zurueckgegeben wird.
	 */
	public static FigurBomberman getBomberman2() {
		if (bomberman2 == null) {
			bomberman2 = new FigurBomberman(0, 0, 512);
		}
		return bomberman2;
	}

	/*
	 * Bei Multiplayer: Erstellt bei nichtvorhandenem Bomberman diesen und
	 * liefert ihn zurueck.
	 */
	public static FigurBomberman createBomberman2(int positionX, int positionY) {
		if (bomberman2 == null) {
			bomberman2 = new FigurBomberman(positionX, positionY, 512);
		}
		return bomberman2;
	}

	/*
	 * Ermoeglichen die Steuerung der Figuren.
	 */
	public boolean bewegenLinks() {
		return Felder.game.move(this, -1, 0);
	}

	public boolean bewegenRechts() {
		return Felder.game.move(this, 1, 0);
	}

	public boolean bewegenOben() {
		return Felder.game.move(this, 0, -1);
	}

	public boolean bewegenUnten() {
		return Felder.game.move(this, 0, 1);
	}

	/*
	 * Laesst Bomben legen. Ueberprueft, ob Bomben gelegt werden koennen,
	 * startet Thread pro Bombe, zaehlt momentane Bombenanzahl runter, berechnet
	 * neuen Status des Feldes.
	 */
	public boolean bombeLegen() {
		if (this.subelement == null) {
			if (this.anzahlBomben > 0) {
				this.subelement = new Bombe(this.positionX, this.positionY,
						bombenrange, this);
				Thread t1 = new Thread((Bombe) this.subelement);
				t1.start();
				this.anzahlBomben--;
				Felder.game.felder[this.positionX][this.positionY] = this
						.getStatus();
				return true;
			}
		}

		return false;

	}

	/*
	 * Sowohl bei Singe- als auch bei Multiplayer: Wenn das Spiel entweder
	 * verloren oder gewonnen wird, oeffnet sich das entsprechende Fenster. Bei
	 * Netzwerkspielen: Wird an den Client die entsprechende Sieges- oder
	 * Niederlagenachricht gesendet und die Verbindung anschliessend getrennt.
	 * (non-Javadoc)
	 * 
	 * @see Element#elementZerstoeren()
	 */
	@Override
	public boolean elementZerstoeren() {
		if (this == bomberman
				&& (Felder.game.isServer || !Felder.game.netzwerk)) { // singleplayer:
																		// verloren;
																		// multiplayer:
																		// spieler2
																		// gewonnen
			this.subelement = null;
			Felder.game.felder[this.positionX][this.positionY] = this
					.getStatus();
			new SpielVerloren(Felder.spielemaster);
			Felder.GUI.frame.setVisible(false);
			Felder.game.time.stop();
			if (Felder.game.netzwerk && Felder.game.isServer) {
				try { // spieler2 hat gewonnen
					PrintStream os = new PrintStream(
							Felder.game.client.getOutputStream());
					os.println("Spiel Sieg");
					os.println("Spiel Sieg");
					os.println("Spiel Sieg");
					os.println("Spiel Sieg");
				} catch (IOException e1) {

					// e1.printStackTrace();
				}
			}
		} else if (this == bomberman2) {
			if (Felder.game.netzwerk) {
				try { // spieler1 hat gewonnen
					PrintStream os = new PrintStream(
							Felder.game.client.getOutputStream());
					os.println("Spiel Ende");
					os.println("Spiel Ende");
					os.println("Spiel Ende");
					os.println("Spiel Ende");
					Felder.GUI.frame.setVisible(false);
					FigurBomberman.getBomberman().subelement = null;
					new Gewonnen(Felder.spielemaster);
					Felder.game.time.stop();
				} catch (IOException e1) {

					// e1.printStackTrace();

				}
			} else if (Felder.game.netzwerk) {
				this.subelement = null;
				Felder.game.felder[this.positionX][this.positionY] = this
						.getStatus();
				new SpielVerloren(Felder.spielemaster);
				Felder.GUI.frame.setVisible(false);
				Felder.game.time.stop();
			} else {
				this.subelement = null;
				Felder.game.felderliste.get(this.positionX).set(this.positionY,
						new Item(this.positionX, positionY, 128));
				Felder.game.felder[this.positionX][this.positionY] = 128;
				CP.thisThread = false;
			}
		} else if (!Felder.game.isServer && Felder.game.netzwerk) { // spieler2
																	// hat
																	// verloren
			this.subelement = null;
			new SpielVerloren(Felder.spielemaster);
			Felder.GUI.frame.setVisible(false);
			Felder.game.time.stop();
		}
		return true;
	}

}

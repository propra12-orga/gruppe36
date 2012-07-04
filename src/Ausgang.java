/**
 * Die Klasse ist eine Subklasse von Element, sodass ein Feld auf dem Spielfeld
 * den Ausgang als Subelement beinhalten kann. Ausgang kann verborgen oder offen
 * sein und fuehrt bei Betreten automatisch zum naechsten Level.
 */

/*
 * Besitzt nur einen Booleanwert, um zu pruefen, ob Ausgang offen ist.
 */
public class Ausgang extends Element {
	private static boolean offen;

	/*
	 * Konstruktor, dem lediglich seine Position uebergeben wird.
	 */
	public Ausgang(int posX, int posY) {
		super(posX, posY, 64);
		offen = false;
	}

	/*
	 * Ueberprueft, ob Ausgang geoeffnet ist.
	 */
	public static void oeffnen() {
		offen = true;
	}

	public static boolean offen() {
		return offen;
	}

	/*
	 * Bei Betreten des Ausganges oeffnet sich das Gewonnenfenster und das neue
	 * Level wird geladen. Bei Netzwerkspielen wird die Verbindung getrennt.
	 */
	public void betreten() {
		if (offen) {
			Felder.spielemaster.currentLvl = Felder.spielemaster.nextLvl;
			Felder.GUI.frame.setVisible(false);
			CP.thisThread = false;
			FigurBomberman.getBomberman().subelement = null;
			new Gewonnen(Felder.spielemaster);
			Felder.game.time.stop();
		}

	}

}

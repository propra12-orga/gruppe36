/**
 * 
 * Jedes Objekt, also jeder Spieler, unzerstoerbare und zerstoerbare Waende, ist
 * ein Element und hat einen unveraenderlichen Identifier sowie ein Subelement
 * der Klasse Element. Ein Subelement kann eine Bombe, Item oder Ausgang sein.
 */

/*
 * Element hat einen Identifier, Subelement, einen veraenderlichen Status, der in
 * einem int array gespeichert wird und eine Position in der Klasse Felder.
 */
public class Element {
	protected int identifier;
	protected Element subelement;
	protected int status;
	public int positionX;
	public int positionY;

	/*
	 * Konstruktor, dem die Position und der Identifier uebergeben wird und das
	 * Subelement standardmaessig auf null setzt.
	 */

	public Element(int posX, int posY, int identi) {
		this.positionX = posX;
		this.positionY = posY;
		this.identifier = identi;
		subelement = null;
	}

	/*
	 * Methode, um den Status des einzelnen Feldes zu berechnen.
	 */
	protected void statusBerechnen() {
		if (this.subelement != null) {
			this.status = this.identifier + this.subelement.getStatus();
		} else {
			this.status = this.identifier;
		}

	}

	/*
	 * Liefert den Status zurueck.
	 * 
	 * @return Status
	 */
	public int getStatus() {
		this.statusBerechnen();
		return this.status;
	}

	/*
	 * Zerstoerbarkeit des Elementes wird geprueft und falls moeglich, wird ELement
	 * aus der Arraylist geloescht und Status neu berechnet.
	 */
	public boolean elementZerstoeren() {
		if (this.identifier == 4) {
			return false;
		}
		if (this.identifier == 8) {
			Felder.game.addToFeld(this.subelement, this.positionX,
					this.positionY);
			return false;
		}
		return false;
	}

	/*
	 * Liefert Identifier.
	 * 
	 * @ return Identifier
	 */
	public int getIdenti() {
		return this.identifier;
	}
}

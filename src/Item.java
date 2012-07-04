/**
 * Klasse der Items, die der Spieler einsammeln kann. Darunter fallen
 * Vergroesserung der Reichweite der Bombe, die Moeglichkeit, mehrere Bomben
 * gleichzeitig zu legen, der Schluessel zum Oeffnen des Ausganges, Erhoehung
 * der Laufgeschwindigkeit und die ferngezuendete Bombe.
 */
public class Item extends Element {
	/*
	 * Konstruktor, der den der Superklasse aufruft.
	 */
	public Item(int posX, int posY, int identi) {
		super(posX, posY, identi);
	}

	/*
	 * Methode, der einen Spieler uebergeben wird und ueberprueft, ob und welche
	 * Items auf einem Feld liegen. Berechnet den Status des jeweiligen Feldes
	 * neu. Identifier: Erhoehung der Reichweite: 16; Erhoehung der Anzahl der
	 * Bomben: 32; Schluessel: 128; Laufgeschwindigkeit: 1024; Fernzuender: 2048.
	 */
	public void betreten(FigurBomberman player) {
		if (this.identifier == 16) {
			player.bombenrange++;
			if (player.bombenrange > player.maxBomben++)
				player.bombenrange = player.maxBomben++;
			player.subelement = null;
			Felder.game.felder[player.positionX][player.positionY] = player
					.getStatus();
		} else if (this.identifier == 32) {
			player.anzahlBomben++;
			if (player.anzahlBomben > player.maxBomben)
				player.anzahlBomben = player.maxBomben;
			player.subelement = null;
			Felder.game.felder[player.positionX][player.positionY] = player
					.getStatus();
		} else if (this.identifier == 128) {
			Ausgang.oeffnen();
			player.subelement = null;
			Felder.game.felder[player.positionX][player.positionY] = player
					.getStatus();
		} else if (this.identifier == 1024) {
			player.seedteiler = 0;
			player.speedteileruse = 0;
			player.subelement = null;
			Felder.game.felder[player.positionX][player.positionY] = player
					.getStatus();
		} else if (this.identifier == 2048) {
			player.fernzuender = true;
			player.verzuendertime = 0;
			player.subelement = null;
			Felder.game.felder[player.positionX][player.positionY] = player
					.getStatus();
		}
	}
}

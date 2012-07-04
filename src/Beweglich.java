/**
 * Die Klasse ist eine Subklasse von Element, um sicherzustellen, dass sich nur
 * bewegliche Elemente wie Spieler oder Gegner fortbewegen können.
 */

public class Beweglich extends Element {

	/*
	 * Konstruktor, der Konstruktor der Oberklasse aufruft.
	 */
	public Beweglich(int posX, int posY, int identi) {
		super(posX, posY, identi);
	}
}

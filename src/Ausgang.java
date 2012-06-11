public class Ausgang extends Element {
	private boolean offen;

	public Ausgang(int posX, int posY) {
		super(posX, posY, 64);
		offen = false;
	}

	public void oeffnen() {
		offen = true;
	}

	public void betreten() {
		if (offen) {
			Felder.spielemaster.currentLvl = Felder.spielemaster.nextLvl;
			Felder.GUI.setVisible(false);
			FigurBomberman.getBomberman().subelement = null;
			new Gewonnen(Felder.spielemaster);
		}

	}

}

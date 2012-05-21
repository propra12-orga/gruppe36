public final class FigurBomberman extends Beweglich {

	private static FigurBomberman bomberman;
	public int bombenrange;
	public int maxBomben;
	public int anzahlBomben;

	private FigurBomberman() {
		super(0, 0, 2);
		bombenrange = 1;
		maxBomben = 1;
		anzahlBomben = 1;
	}

	private FigurBomberman(int positionX, int positionY) {
		super(positionX, positionY, 2);
		bombenrange = 1;
		maxBomben = 1;
		anzahlBomben = 1;
	}

	public static FigurBomberman getBomberman() {
		if (bomberman == null) {
			bomberman = new FigurBomberman(0, 0);
		}
		return bomberman;
	}

	public static FigurBomberman createBomberman(int positionX, int postitionY) {
		if (bomberman == null) {
			bomberman = new FigurBomberman(positionX, postitionY);
		}
		return bomberman;
	}

	public static void destroy() {
		bomberman = null;
	}

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

	public boolean bombeLegen() {
		if (this.subelement == null) {
			if (this.anzahlBomben > 0) {
				this.subelement = new Bombe(this.positionX, this.positionY,
						bombenrange);
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

	@Override
	public boolean elementZerstoeren() {
		this.subelement = null;
		Felder.game.felder[this.positionX][this.positionY] = this.getStatus();
		new SpielVerloren(Felder.spielemaster);
		Felder.GUI.setVisible(false);

		return true;
	}

}

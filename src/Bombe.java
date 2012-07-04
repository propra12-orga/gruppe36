/**
 * Die Klasse Bombe ist von der Klasse Element abgeleitet und realisiert jeweils
 * in einem Thread die Explosion einer Bombe.
 */

/*
 * Hat eine veraenderliche Reichweite bezueglich der Sprengkraft, hat einen
 * boolean-Wert fuer eine erfolgte Explosion und einen eindeutigen Besitzer,
 * also Spieler1, Spieler2 oder den Computergegner.
 */
public class Bombe extends Element implements Runnable {
	public int sprengkraft;
	public boolean thisThread;
	private FigurBomberman besitzer;

	/*
	 * Konstruktor, dem die Position, die Reichweite und der Besitzer uebergeben
	 * wird.
	 */

	public Bombe(int posX, int posY, int sprengkraft, FigurBomberman owner) {
		super(posX, posY, 1);
		if (sprengkraft > 5) {
			this.sprengkraft = 5;
		} else
			this.sprengkraft = sprengkraft;
		this.thisThread = true;
		besitzer = owner;

	}

	/*
	 * Ermoeglicht dem Thread einer Bombe in kuerzeren Zeitintervallen einen
	 * Abbruch, damit sich mehrere Threads nicht gegenseitig behindern. Legt die
	 * Reichweite/Sprengkraft in alle vier Richtungen fest. Kuemmert sich
	 * ebenfalls um das Items der ferngezuendeten Bombe. (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */

	public void run() {
		try {
			Thread.sleep(400);
			if (this.thisThread && !besitzer.fernzuender) {
				Thread.sleep(400);
				if (this.thisThread && !besitzer.fernzuender) {
					Thread.sleep(400);
					if (this.thisThread && !besitzer.fernzuender) {
						Thread.sleep(400);
						if (this.thisThread && !besitzer.fernzuender) {
							Thread.sleep(400);
							if (this.thisThread && !besitzer.fernzuender) {
								Thread.sleep(400);
								if (this.thisThread && !besitzer.fernzuender) {
									Thread.sleep(400);
									if (this.thisThread
											&& !besitzer.fernzuender) {
										Thread.sleep(400);
										if (this.thisThread
												&& !besitzer.fernzuender) {
											this.explodieren();
											Thread.sleep(400);
										}
									}
								}
							}
						}
					}
				}
			}
			boolean notaus = false;
			while (besitzer.fernzuender && !notaus) {
				if (besitzer.zuenden) {
					this.explodieren();
					notaus = true;
					Thread.sleep(300);
				}
				Thread.sleep(100);
			}
			Felder.game.felder[positionX][positionY] = Felder.game.getStatus(
					positionX, positionY);
			for (int i = 1; i <= this.sprengkraft
					&& (positionX + i >= 0 && positionX + i < Felder.game.felder.length); i++) {
				Felder.game.felder[this.positionX + i][this.positionY] = Felder.game
						.getStatus(positionX + i, positionY);
			}
			for (int i = 1; i <= this.sprengkraft
					&& (positionX - i >= 0 && positionX - i < Felder.game.felder.length); i++) {
				Felder.game.felder[this.positionX - i][this.positionY] = Felder.game
						.getStatus(positionX - i, positionY);
			}
			for (int i = 1; i <= this.sprengkraft
					&& (positionY + i >= 0 && positionY + i < Felder.game.felderliste
							.get(positionX).size()); i++) {
				Felder.game.felder[this.positionX][this.positionY + i] = Felder.game
						.getStatus(positionX, positionY + i);
			}
			for (int i = 1; i <= this.sprengkraft
					&& (positionY - i >= 0 && positionY - i < Felder.game.felderliste
							.get(positionX).size()); i++) {
				Felder.game.felder[this.positionX][this.positionY - i] = Felder.game
						.getStatus(positionX, positionY - i);
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/*
	 * Ermoeglicht Kettenreaktion nebeneinander liegenender Bomben. Abhaengig
	 * von der Position der in Reichweite der einen Bombe liegende andere Bombe
	 * wird der Status der verschiedenen Flammen entsprechend ihrer Richtung
	 * berechnet, sodass auch bei einer Kettenreaktion die Flammen richtig
	 * angezeigt werden koennen.
	 */
	public void explodieren() {
		besitzer.anzahlBomben++;

		synchronized (FigurBomberman.getBomberman()) { // bombe wird aus
														// arraylist genommen,
														// dann erst geht andere
														// hoch
			if (Felder.game.felderliste.get(positionX).get(positionY) != this) {
				Felder.game.feldZerstoeren(this.positionX, this.positionY);
				Felder.game.felder[this.positionX][this.positionY] = Felder.game.felder[this.positionX][this.positionY] + 1024;
			} else {
				Felder.game.felderliste.get(positionX).set(positionY, null);
				Felder.game.felder[positionX][positionY] = 4096;
			}
		}
		boolean links = true;
		boolean rechts = true;
		boolean unten = true;
		boolean oben = true;

		synchronized (FigurBomberman.getBomberman()) {
			for (int i = 1; i <= this.sprengkraft && rechts; i++) {
				rechts = Felder.game.feldZerstoeren(this.positionX + i,
						this.positionY);
				if (i == this.sprengkraft || !rechts) { // maxRange erreicht
														// oder rechts = false,
														// dann ende der
														// explosion
					Felder.game.felder[this.positionX + i][this.positionY] = Felder.game.felder[this.positionX
							+ i][this.positionY] + 131072;
				} else
					// sonst mitte der explosion
					Felder.game.felder[this.positionX + i][this.positionY] = Felder.game.felder[this.positionX
							+ i][this.positionY] + 8192;

			}
		}
		synchronized (FigurBomberman.getBomberman()) {
			for (int i = 1; i <= this.sprengkraft && links; i++) {
				links = Felder.game.feldZerstoeren(this.positionX - i,
						this.positionY);
				if (i == this.sprengkraft || !links) {
					Felder.game.felder[this.positionX - i][this.positionY] = Felder.game.felder[this.positionX
							- i][this.positionY] + 262144;
				} else
					Felder.game.felder[this.positionX - i][this.positionY] = Felder.game.felder[this.positionX
							- i][this.positionY] + 16384;

			}
		}
		synchronized (FigurBomberman.getBomberman()) {
			for (int i = 1; i <= this.sprengkraft && unten; i++) {
				unten = Felder.game.feldZerstoeren(this.positionX,
						this.positionY + i);
				if (i == this.sprengkraft || !unten) {
					Felder.game.felder[this.positionX][this.positionY + i] = Felder.game.felder[this.positionX][this.positionY
							+ i] + 1048576;
				} else
					Felder.game.felder[this.positionX][this.positionY + i] = Felder.game.felder[this.positionX][this.positionY
							+ i] + 65536;

			}
		}
		synchronized (FigurBomberman.getBomberman()) {
			for (int i = 1; i <= this.sprengkraft && oben; i++) {
				oben = Felder.game.feldZerstoeren(this.positionX,
						this.positionY - i);
				if (i == this.sprengkraft || !oben) {
					Felder.game.felder[this.positionX][this.positionY - i] = Felder.game.felder[this.positionX][this.positionY
							- i] + 524288;
				} else
					Felder.game.felder[this.positionX][this.positionY - i] = Felder.game.felder[this.positionX][this.positionY
							- i] + 32768;

			}
		}

	}

	/*
	 * Ueberschriebene Methode fuer Bombe, die die Bombe explodieren laesst und
	 * den Thread stopt. (non-Javadoc)
	 * 
	 * @see Element#elementZerstoeren()
	 */
	@Override
	public boolean elementZerstoeren() {
		this.explodieren();
		this.thisThread = false;
		return false;
	}

}
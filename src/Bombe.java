public class Bombe extends Element implements Runnable {
	private int sprengkraft;
	public boolean thisThread;

	public Bombe(int posX, int posY, int sprengkraft) {
		super(posX, posY, 1);
		this.sprengkraft = sprengkraft;
		this.thisThread = true;

	}

	public void run() {
		try {
			Thread.sleep(400);
			if (this.thisThread) {
				Thread.sleep(400);
				if (this.thisThread) {
					Thread.sleep(400);
					if (this.thisThread) {
						Thread.sleep(400);
						if (this.thisThread) {
							Thread.sleep(400);
							if (this.thisThread) {
								Thread.sleep(400);
								if (this.thisThread) {
									Thread.sleep(400);
									if (this.thisThread) {
										Thread.sleep(400);
										if (this.thisThread) {
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
			Felder.game.felder[positionX][positionY] = Felder.game.getStatus(
					positionX, positionY);
			for (int i = 1; i <= this.sprengkraft; i++) {
				Felder.game.felder[this.positionX + i][this.positionY] = Felder.game
						.getStatus(positionX + i, positionY);
			}
			for (int i = 1; i <= this.sprengkraft; i++) {
				Felder.game.felder[this.positionX - i][this.positionY] = Felder.game
						.getStatus(positionX - i, positionY);
			}
			for (int i = 1; i <= this.sprengkraft; i++) {
				Felder.game.felder[this.positionX][this.positionY + i] = Felder.game
						.getStatus(positionX, positionY + i);
			}
			for (int i = 1; i <= this.sprengkraft; i++) {
				Felder.game.felder[this.positionX][this.positionY - i] = Felder.game
						.getStatus(positionX, positionY - i);
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void explodieren() {
		FigurBomberman.getBomberman().anzahlBomben++;

		synchronized (FigurBomberman.getBomberman()) {
			if (Felder.game.felderliste.get(positionX).get(positionY) != this) {
				Felder.game.feldZerstoeren(this.positionX, this.positionY);
				Felder.game.felder[this.positionX][this.positionY] = Felder.game.felder[this.positionX][this.positionY] + 1024;
			} else {
				Felder.game.felderliste.get(positionX).set(positionY, null);
				Felder.game.felder[positionX][positionY] = 1024;
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
				if (i == this.sprengkraft || !rechts) {
					Felder.game.felder[this.positionX + i][this.positionY] = Felder.game.felder[this.positionX
							+ i][this.positionY] + 32768;
				} else
					Felder.game.felder[this.positionX + i][this.positionY] = Felder.game.felder[this.positionX
							+ i][this.positionY] + 2048;
			}
		}
		synchronized (FigurBomberman.getBomberman()) {
			for (int i = 1; i <= this.sprengkraft && links; i++) {
				links = Felder.game.feldZerstoeren(this.positionX - i,
						this.positionY);
				if (i == this.sprengkraft || !links) {
					Felder.game.felder[this.positionX - i][this.positionY] = Felder.game.felder[this.positionX
							- i][this.positionY] + 65536;
				} else
					Felder.game.felder[this.positionX - i][this.positionY] = Felder.game.felder[this.positionX
							- i][this.positionY] + 4096;

			}
		}
		synchronized (FigurBomberman.getBomberman()) {
			for (int i = 1; i <= this.sprengkraft && unten; i++) {
				unten = Felder.game.feldZerstoeren(this.positionX,
						this.positionY + i);
				if (i == this.sprengkraft || !unten) {
					Felder.game.felder[this.positionX][this.positionY + i] = Felder.game.felder[this.positionX][this.positionY
							+ i] + 262144;
				} else
					Felder.game.felder[this.positionX][this.positionY + i] = Felder.game.felder[this.positionX][this.positionY
							+ i] + 16384;

			}
		}
		synchronized (FigurBomberman.getBomberman()) {
			for (int i = 1; i <= this.sprengkraft && oben; i++) {
				oben = Felder.game.feldZerstoeren(this.positionX,
						this.positionY - i);
				if (i == this.sprengkraft || !oben) {
					Felder.game.felder[this.positionX][this.positionY - i] = Felder.game.felder[this.positionX][this.positionY
							- i] + 131072;
				} else
					Felder.game.felder[this.positionX][this.positionY - i] = Felder.game.felder[this.positionX][this.positionY
							- i] + 8192;

			}
		}

	}

	@Override
	public boolean elementZerstoeren() {
		this.explodieren();
		this.thisThread = false;
		return false;
	}

}
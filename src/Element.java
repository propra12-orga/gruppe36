public class Element {
	protected int identifier;
	protected Element subelement;
	protected int status;
	public int positionX;
	public int positionY;

	public Element(int posX, int posY, int identi) {
		this.positionX = posX;
		this.positionY = posY;
		this.identifier = identi;
		subelement = null;
	}

	protected void statusBerechnen() {
		if (this.subelement != null) {
			this.status = this.identifier + this.subelement.getStatus();
		} else {
			this.status = this.identifier;
		}

	}

	public int getStatus() {
		this.statusBerechnen();
		return this.status;
	}

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

	public int getIdenti() {
		return this.identifier;
	}
}

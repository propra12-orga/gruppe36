
public class Player extends Beweglich{

	public int bombenrange;
	public int maxBomben;
	public int anzahlBomben;
	public int playernr, status;
	public boolean human=false;
	public boolean alive;

	public Player(int nr, int status) {
		super(0, 0, status);
		bombenrange = 1;
		maxBomben = 5;
		anzahlBomben = 5;
		playernr=nr;
		alive=true;
		if (playernr<10){
			human=true;
		}
	}
	public Player(int positionX, int positionY, int nr, int status) {
		super(positionX, positionY, status);
		bombenrange = 1;
		maxBomben = 1;
		anzahlBomben = 1;
		playernr=nr;
		alive=true;
		if (playernr<10){
			human=true;
		}
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
						bombenrange, playernr);
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

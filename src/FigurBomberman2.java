public final class FigurBomberman2 extends Player {

	private static FigurBomberman2 bomberman2;
	public static int playernr=2;
	public static int status=512;

	private FigurBomberman2() {
		super(playernr, status);
	}

	private FigurBomberman2(int positionX, int positionY) {
		super(positionX, positionY, playernr, status);
	}

	public static FigurBomberman2 getBomberman2() {
		if (bomberman2 == null) {
			bomberman2 = new FigurBomberman2(0, 0);
		}
		return bomberman2;
	}

	public static FigurBomberman2 createBomberman2(int positionX, int postitionY) {
		if (bomberman2 == null) {
			bomberman2 = new FigurBomberman2(positionX, postitionY);
		}
		return bomberman2;
	}

	public static void destroy() {
		bomberman2 = null;
	}
}

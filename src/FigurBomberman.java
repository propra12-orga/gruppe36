public final class FigurBomberman extends Player {

	private static FigurBomberman bomberman;
	public static int playernr=1;
	public static int status=2;

	private FigurBomberman() {
		super(playernr, status);
	}

	private FigurBomberman(int positionX, int positionY) {
		super(positionX, positionY, playernr, status);
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
}

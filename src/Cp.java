
public class Cp extends Player{
	private static Cp computer;
	public static int playernr=11;
	public static int status=512;
	
	private Cp() {
		super(playernr, status);
	}

	private Cp(int positionX, int positionY) {
		super(positionX, positionY, playernr, status);
	}
	public static Cp getCp() {
		if (computer == null) {
			
			computer = new Cp(0, 0);
		}
		return computer;
	}

	public static Cp createCp(int positionX, int postitionY) {
		if (computer == null) {
			computer = new Cp(positionX, postitionY);
		}
		return computer;
	}

	public static void destroy() {
		computer = null;
	}
	public static void action(){
//		while(bombe in der nähe){
//			weglaufen
//		}
		
	}
}

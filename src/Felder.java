import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Felder {
	public int felder[][];
	public ArrayList<ArrayList<Element>> felderliste;
	public static Felder game;
	public static GameMaster spielemaster;
	public static Game GUI;

	public Felder(String dateiname, GameMaster master) {

		spielemaster = master;
		game = this;
		this.init(dateiname);
		GUI = Game.getGUI();
		GUI.felderInit();
		GUI.setVisible(true);
	}

	public boolean move(Beweglich move, int x, int y) {
		int status = felder[move.positionX + x][move.positionY + y];
		if (status != 4) {
			if (status != 8) {
				if (status != 24) {
					if (status != 40) {
						if (status != 72) {
							if (status != 1) {
								if (status != 3) {
									if (status != 136) {
										if ((status == 256 && move.identifier == 2)
												|| status != 256) {
											if (move.subelement != null) {
												// Wenn das subelement eine
												// Bombe oder der Ausgang ist
												if (move.subelement.identifier == 1
														|| move.subelement.identifier == 64) {
													// Wird das subelement an
													// das zu verlassene Feld
													// angehaengt
													felderliste
															.get(move.positionX)
															.set(move.positionY,
																	move.subelement);
													// Das subsubelement wird
													// als subelement von
													// unserem Move-Element
													// angehaengt
													move.subelement = move.subelement.subelement;
													// Der Status des zu
													// verlassenen Feldes wird
													// berechnet
													felder[move.positionX][move.positionY] = felderliste
															.get(move.positionX)
															.get(move.positionY)
															.getStatus();
													// Wenn wir ein
													// SubSubElement haben
												}
												else if (move.subelement.subelement != null) {
													// Das subelement wird
													// zur�ckgelassen
													felderliste
															.get(move.positionX)
															.set(move.positionY,
																	move.subelement);
													// Das Subsubelement wird
													// als subelement von
													// unserem Move-Element
													// angehaengt
													move.subelement = move.subelement.subelement;
													felder[move.positionX][move.positionY] = felderliste
															.get(move.positionX)
															.get(move.positionY)
															.getStatus();
													// Wenn weder ein
													// Subsubelement vorliegt
													// noch eine Bombe oder ein
													// Ausgang als subelement
												}
												else {
													// Wird das zu verlassene
													// Feld null gesetzt
													felderliste
															.get(move.positionX)
															.set(move.positionY,
																	null);
													felder[move.positionX][move.positionY] = 0;
												}
											} else {
												felderliste.get(move.positionX)
														.set(move.positionY,
																null);
												felder[move.positionX][move.positionY] = 0;
											}

											// auf der Zielposition liegt ein
											// Element
											if (felderliste.get(
													move.positionX + x).get(
													move.positionY + y) != null) {
												if (felderliste
														.get(move.positionX + x)
														.get(move.positionY + y).identifier != 2) {
													felderliste.get(
															move.positionX + x)
															.get(move.positionY
																	+ y).subelement = move.subelement;
													move.subelement = felderliste
															.get(move.positionX
																	+ x)
															.set(move.positionY
																	+ y, move);
												}
											} else
												felderliste
														.get(move.positionX + x)
														.set(move.positionY + y,
																move);

											felder[move.positionX + x][move.positionY
													+ y] = move.getStatus();

											if (move.identifier == 2
													&& move.subelement != null) {
												// Item/Ausgangseffekte
												// ausloesen
												if (move.subelement.identifier == 64) {
													((Ausgang) move.subelement)
															.betreten();
												}
											}
											move.positionX = move.positionX + x;
											move.positionY = move.positionY + y;
											return true;
										}
									}
								}

							}
						}
					}
				}
			}
		}
		return false;
	}

	public int getStatus(int x, int y) {
		if (x >= 0 && y >= 0) {
			if (felderliste.get(x).get(y) != null) {
				return felderliste.get(x).get(y).getStatus();
			}
		}
		return 0;
	}

	public void printFelder() {
		for (int y = 0; y < this.felderliste.get(0).size(); y++) {
			for (int x = 0; x < this.felderliste.size(); x++) {
				System.out.print(this.getStatus(x, y));
			}
			System.out.println();
		}
		System.out.println();
	}

	public boolean feldZerstoeren(int x, int y) {
		boolean temp = true;
		if (felderliste.get(x).get(y) != null) {
			temp = felderliste.get(x).get(y).elementZerstoeren();
			if (felderliste.get(x).get(y) != null)
				felder[x][y] = felderliste.get(x).get(y).getStatus();
			else
				felder[x][y] = 0;
		}
		return temp;
	}

	public void addToFeld(Element add, int x, int y) {
		felderliste.get(x).remove(y);

		felderliste.get(x).add(y, add);
		if (add != null) {
			felder[x][y] = add.getStatus();
		} else {
			felder[x][y] = 0;
		}
	}

	private void init(String FileName) {
		try {
			BufferedReader in = new BufferedReader(new FileReader(FileName));
			String zeile = null;
			int posX = 0, posY = 0;
			zeile = in.readLine();
			char c[] = new char[10];
			int start = 0;
			int ende;
			int j = 0;
			c = zeile.toCharArray();
			for (int i = 0; i < zeile.length(); i++) {
				if (c[i] == '.' && j == 0) {
					ende = i;
					posX = (Integer.parseInt(String.valueOf(c, start, ende
							- start)));
					j++;
					start = i + 1;

				} else if (c[i] == '.') {
					ende = i;
					posY = (Integer.parseInt(String.valueOf(c, start, ende
							- start)));
				}
			}
			felder = new int[posX][posY];
			felderliste = new ArrayList<ArrayList<Element>>();
			for (int i = 0; i < posX; i++) {
				felderliste.add(new ArrayList<Element>());
				for (int a = 0; a < posY; a++) {
					felderliste.get(i).add(null);
					felder[i][a] = 0;
				}
			}
			char zeileC[] = new char[4 * posX];
			int zahlen[] = new int[posX];
			int y = 0;
			Element elem = null;
			zeile = in.readLine();

			Felder.spielemaster.nextLvl = zeile;

			zeile = in.readLine();

			while (empty(zeile)) {

				zeileC = zeile.toCharArray();
				start = 0;
				j = 0;
				for (int i = 0; i < zeile.length(); i++) {
					if (zeileC[i] == '.') {
						ende = i;
						zahlen[j] = (Integer.parseInt(String.valueOf(zeileC,
								start, ende - start)));
						j++;
						start = i + 1;
					}
				}
				zahlen[posX - 1] = 4;
				for (int i = 0; i < posX; i++) {
					if (zahlen[i] == 4) {
						elem = new Element(i, y, 4);
					}
					if (zahlen[i] == 8) {
						elem = new Element(i, y, 8);
					}
					if (zahlen[i] == 72) {
						elem = new Element(i, y, 8);
						elem.subelement = new Ausgang(i, y);
						((Ausgang) elem.subelement).oeffnen();
					}
					if (zahlen[i] == 64) {
						elem = new Ausgang(i, y);
						((Ausgang) elem).oeffnen();
					}
					if (zahlen[i] == 2) {
						elem = FigurBomberman.getBomberman();
						elem.positionX = i;
						elem.positionY = y;
					}
					if(zahlen[i]==512){
						elem=FigurBomberman2.getBomberman2();
						elem.positionX=i;
						elem.positionY=y;
					}

					if (zahlen[i] != 0) {
						this.addToFeld(elem, i, y);
					}

				}
				zeile = in.readLine();
				y++;

			}
		} catch (IOException e) {
			e.printStackTrace();

		}
	}

	static boolean empty(String z) {
		if (z != null)
			if (z.length() != 0)
				return true;
			else
				return false;
		else
			return false;
	}

}

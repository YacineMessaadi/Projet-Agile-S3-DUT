package projet_agile;

import java.util.Random;

public class Ennemie {
	
	private int[] max = new int[]{11,21,31,41,51};
	
	private int nbrPersonnes;
	private Random alea = new Random();
	
	public Ennemie(int age) {
		this.nbrPersonnes = alea.nextInt(max[age-1]);
	}
	
	public int getPV() {return this.nbrPersonnes;}
	
}
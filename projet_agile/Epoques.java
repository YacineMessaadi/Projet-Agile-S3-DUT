package projet_agile;

public enum Epoques {

	PREHISTOIRE(1),
	ANTIQUITE(2),
	MOYEN_AGE(3),
	TEMPS_MODERNES(4),
	EPOQUE_CONTEMPORAINE(5);
	
	private final int AGE;
	
	Epoques(int age) {this.AGE=age;}
	public int getAGE() {return this.AGE;}
	
}

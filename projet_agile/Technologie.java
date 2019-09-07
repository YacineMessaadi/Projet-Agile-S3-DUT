package projet_agile;

import java.util.ArrayList;

public enum Technologie {

	
	FEU(1),
	OUTIL(1),
	LANGAGE(1),
	AGRICULTURE(1),
	ACIER(2),
	BOUSSOLE(2),
	BATEAU(2),
	PAPIER(2),
	CIMENT(2),
	CANON(3),
	VOITURE(3),
	IMPRIMERIE(3),
	HORLOGE(3),
	VACCINS(4),
	AMPOULE(4),
	AVION(4),
	BOMBENUCLEAIRE(4),
	REFRIGERATEUR(5),
	INTERNET(5),
	NAVETTESPATIALE(5);

	
	private final int AGE;
	
	Technologie(int age) {this.AGE=age;}
	public int getAGE() {return this.AGE;}
	
	public static ArrayList<Technologie> getTechnologieEpoque(int epoque) {
		ArrayList<Technologie> technologiesDeLepoque = new ArrayList<>();
		
		for(Technologie t : Technologie.values()) {
			if(t.getAGE() == epoque) {
				technologiesDeLepoque.add(t);
			}
		}
		return technologiesDeLepoque;
	}
	
	public String getName() {
		return this.name().charAt(0)+this.name().substring(1).toLowerCase();
	}
}

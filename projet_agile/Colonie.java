package projet_agile;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Random;


public class Colonie implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected ArrayList<Humain> colonie = new ArrayList<>();
	public int Nbturn = 0;
	public String nom;
	private int qi;
	private int nourriture;
	private int rendementCulture = 10;
	public ArrayList<Technologie> technos = new ArrayList<>();
	private int epoque;
	public int mortExpe;
	public int mortFaim;
	public int mortGuerre;
	public int mortAge;
	public int babys;
	public boolean vaccinne;
	private int humainsMax = 0;
	private int tourDepart;
	private boolean enExploration;
	private boolean nouvellesHabitations;
	private int quota_bomb;

	public Colonie(String nom) {
		this.nom = nom;
		for(int i = 0; i < (int) ((Math.random()*9)+2) ; i++) {
			colonie.add(new Humain(true));
			colonie.add(new Humain(false));
		}
		this.nourriture = 10;
		this.epoque = 1 ;
		this.rendementCulture =0;
		this.vaccinne = false;
		this.tourDepart = 0;
		this.enExploration = false;
		this.nouvellesHabitations = false;
		this.quota_bomb = 400;
	}
	public boolean getExploration() {
		return this.enExploration;
	}
	public void attaquer() {
		Random alea = new Random();
		System.out.println("Tu attaques une colonie ennemie !");
		int tailleEnnemie = alea.nextInt(this.colonie.size());
		if(tailleEnnemie-1 <1) {
			System.out.println("Tu as perdu le combat ...\n");
			this.colonie.clear();
		}
		else {
			int perte = alea.nextInt(this.colonie.size()/2);
			int gagne = alea.nextInt(this.colonie.size()/2);
			for(int i=0; i<perte ; i++)
				this.colonie.remove(0);
			for(int i=0; i<gagne; i++)
				this.colonie.add(new Humain());
			System.out.println("Tu as perdu "+perte+" habitants, mais tu en as gagné "+gagne+".");
		}
	}

	public boolean getVaccinne() {return this.vaccinne;}
	public void vaccinner() {this.vaccinne = true;}

	public void passerTemps() {
		faim();
		vieillir();
		enterrerMorts();
		reproduction();
		attaqueEnnemie();
		if(!this.getVaccinne()) maladie();
		recupererCulture();
		LEMONDEVATILMOURIRDUNEBOMBENUCLEAIRE();
		testDecouverte();
		boolHabitations();
		coucou();
	} 
	
	public void coucou() {
		Random r = new Random();
		if(r.nextInt(10)<=3 && this.Nbturn >=5) {
			System.out.println("Une autre colonie est venue te rendre visite.");
			int tmp = r.nextInt(10);
			int tmpB = r.nextInt(10);
			
			for(int i =0; i<tmp; i++)
				this.colonie.add(new Humain());
			this.nourriture += tmpB;
			
			System.out.println(tmp+" personnes ont décidé de rester. Ils t'ont apporté "+tmpB+" nourritures pour te remercier.");
		}
	}
	
	public void boolHabitations() {
		if(this.nouvellesHabitations) {
			Random alea= new Random();
			int tmp = alea.nextInt(50);
			for(int i=0 ; i<tmp; i++)
				this.colonie.add(new Humain());
			this.nourriture-=25;
			System.out.println("Tes nouvelles habitations ont attiré "+tmp+" nouveaux habitants !\n");
				
		}
	}
	
	public void newHabitations() {
		this.nouvellesHabitations = !this.nouvellesHabitations;
	}
	
	public boolean getNouvellesHabitations() {return this.nouvellesHabitations;}
	
	public void testDecouverte() {
		if(this.enExploration) this.tourDepart++;
		if(this.tourDepart ==5) {
			this.tourDepart = 0;
			this.enExploration = false;
			Random alea = new Random(); 
			
			int retour = alea.nextInt(10);
			System.out.println("L'expédition est maintenant terminée ! ");
			System.out.println("Résultat : ");
			System.out.println("  - "+retour+" habitants sont revenus;");
			for(int i = 0; i<retour; i++)
				this.colonie.add(new Humain());
			int newD =0;
			if(alea.nextInt(100) < 34) {
				Technologie nouvelleTechnologie = Technologie.getTechnologieEpoque(epoque).get(alea.nextInt(Technologie.getTechnologieEpoque(epoque).size()));
				while(technos.contains(nouvelleTechnologie)) {
					nouvelleTechnologie = Technologie.getTechnologieEpoque(epoque).get(alea.nextInt(Technologie.getTechnologieEpoque(epoque).size()));
				}
				technos.add(nouvelleTechnologie);
				qi += 7;
				newD++;
				System.out.println("  - Tu as découvert "+ nouvelleTechnologie.name());
				affichageTechnologie(nouvelleTechnologie);
			}
			
			
		}
	}
	
	public void LEMONDEVATILMOURIRDUNEBOMBENUCLEAIRE() {
		if(this.technos.contains(Technologie.BOMBENUCLEAIRE)) {
			Random alea = new Random();
			if(alea.nextInt(800)<=quota_bomb) {
				System.out.println("Trump a balancé une bombe sur : " + Main.location);
				System.out.println("F I N    D U    M O N D E");
				this.colonie.clear();
			}
		}
	}
	public void lettres() {
		if(quota_bomb >= 50) {
			quota_bomb-=50;
			System.out.println("Trump t'envoie ses amitiés en retour.");
		}
	}

	public void recupererCulture() {
		this.nourriture+=this.rendementCulture;
	}
	public void cultiver() {
		this.rendementCulture++;
		System.out.println("Ton rendement est augmenté de 1 par tour !\n");
	}
	public void attaqueEnnemie() {

		Random r = new Random();
		int nbr = r.nextInt(50);
		Random randChanceAttaque = new Random();
		int chanceAttaque = randChanceAttaque.nextInt(10);

		if(Nbturn >= 10 && chanceAttaque < 3) {

			if(nbr < 10) {
				System.out.println("Ta force a intimidé les ennemis et ils ne t'ont finalement pas attaqué !");
			}
			else {
				double tauxDeMorts = (int) colonie.size() * nbr/100;
				int morts = (int) tauxDeMorts;
				
				System.out.println("Tu t'es fait attaqué par une colonie ennemie !");
				System.out.println("Tu as perdu "+ morts + " humains.");

				for(int i = 0 ; i < morts ; i++) {
					colonie.remove(0);
					mortGuerre+= 1;
				}
			}

			System.out.println("");
			if(this.colonie.size() == 0 ) System.out.println("Ta colonie a été décimée.");
		}
	}

	public void decouvrir() {
		System.out.println("Ton équipage part pour de nouvelles aventures... Il sera de retour dans 5 tours. ");
		this.tourDepart = this.Nbturn;
		if(this.colonie.size() <=10) System.out.println("Tu n'as pas assez d'habitants...\n");
		else {
			for(int i=0;i<10;i++)
				this.colonie.remove(0);
		}
		this.enExploration = true;
	}
	
	public void maladie() {
		Random alea = new Random();
		if(alea.nextInt(100)<=2 && Nbturn >= 10) {
			System.out.println("Ta colonie a été victime d'une maladie !\n");
			int tmp =  alea.nextInt((int) (this.colonie.size()*0.8));
			for(int i = 0; i< tmp; i++)
				if(colonie.size() > 0) {
					this.colonie.remove(0);
				}
		}
	}

	public int getEpoque() {return this.epoque;}



	public void changerEpoque(int epoque) {
		this.epoque = epoque +1 ;
	}

	public int getTaille() {
		return colonie.size();
	}

	public int getQi() {
		return qi;
	}

	public int getMortExpe() {
		return mortExpe;
	}
	public int getMortFaim() {
		return mortFaim;
	}
	public int getMortGuerre() {
		return mortGuerre;
	}
	public int getMortAge() {
		return mortAge;
	}

	public void setQi(int qi) {
		this.qi = qi;
	}

	public void expedition() {
		nourriture--;
		Random r = new Random();
		int nbr = r.nextInt(100);

		if(technos.contains(Technologie.VOITURE)) {
			if(nbr < 42) {
				Technologie nouvelleTechnologie = Technologie.getTechnologieEpoque(epoque).get(r.nextInt(Technologie.getTechnologieEpoque(epoque).size()));
				while(technos.contains(nouvelleTechnologie)) {
					nouvelleTechnologie = Technologie.getTechnologieEpoque(epoque).get(r.nextInt(Technologie.getTechnologieEpoque(epoque).size()));
				}
				technos.add(nouvelleTechnologie);
				qi += 7;
				
				affichageTechnologie(nouvelleTechnologie);
				System.out.println("Vous avez découvert : " + nouvelleTechnologie.name());

				if(r.nextInt(100) < 21) {
					colonie.remove(0);
					System.out.println("Un villageois est mort durant l'expédition !");
					mortExpe++;
				}
			}
		}
		else {
			if(nbr < 34) {
				Technologie nouvelleTechnologie = Technologie.getTechnologieEpoque(epoque).get(r.nextInt(Technologie.getTechnologieEpoque(epoque).size()));
				while(technos.contains(nouvelleTechnologie)) {
					nouvelleTechnologie = Technologie.getTechnologieEpoque(epoque).get(r.nextInt(Technologie.getTechnologieEpoque(epoque).size()));
				}
				technos.add(nouvelleTechnologie);
				qi += 7;
			
				affichageTechnologie(nouvelleTechnologie);
				System.out.println("Vous avez découvert : " + nouvelleTechnologie.name());

				if(r.nextInt(100) < 21) {
					colonie.remove(0);
					System.out.println("Un villageois est mort durant l'expédition !");
					mortExpe++;
				}
			}
			else {
				System.out.println("Tu n'as rien découvert durant l'exploration !");
				if(r.nextInt(100) < 21) {
					colonie.remove(0);
					System.out.println("Un villageois est mort durant l'expédition !");
					mortExpe++;
				}
			}
		}
	}

	public void chasser() {
		if(technos.contains(Technologie.FEU)) {
			nourriture += 3;
		}
		else {
			nourriture += 2;
		}
		System.out.println("tu as attrapé une bête");
	}



	public int getNourriture() {
		return nourriture;
	}

	public void faim() {
		nourriture--;
		if(nourriture<5) {
			if(colonie.size()>0) colonie.remove(0);
			System.out.println("Un villageois est mort de faim, tu dois ramener de la nourriture !");
			mortFaim++;
		}
	}

	public void reproduction() {
		if(nourriture>colonie.size()) {
			ArrayList<Humain> bebes = new ArrayList<Humain>();
			for(Humain h: colonie) {
				if(!h.isMort() && !h.isParent() && h.getAge()>13) {
					Random r = new Random();
					int idx = r.nextInt(colonie.size());
					if(colonie.get(idx).isMale()!=h.isMale()) {
						Humain b = new Humain();
						b.setQiEnfant(h, colonie.get(idx));
						bebes.add(b);
						h.setParent(true);
						colonie.get(idx).setParent(true);
					}
				}
			}
			for(Humain h: bebes) {
				colonie.add(h);
				h.setEsperanceVie(20*this.getEpoque());
				babys++;
			}
			if(bebes.size()>0) System.out.println("Ta colonie accueille " + bebes.size() + " nouveau(x) né(s) !");
			bebes.clear();
			for(Humain h: colonie) {
				h.setParent(false);
			}
		}

	}

	private void vieillir() {
		for(Humain h: colonie) {
			h.vieillir();
		}
	}

	private void enterrerMorts() {
		if(colonie.size()==0) return;
		ArrayList<Integer> morts = new ArrayList<>();
		for(Humain p : colonie) {
			if(p.isMort() == true) morts.add(colonie.indexOf(p));
		}
		for(int i : morts) {
			if(i<colonie.size()) colonie.remove(i);
		}
		if(morts.size()>0) System.out.println(morts.size() + " villageois sont morts de vieillesse !");
		mortAge += morts.size();
	}

	public int getBaby() {
		return babys;
	}

	public int getHumainsMax() {
		return humainsMax;
	}

	public void setHumainsMax(int humainsMax) {
		this.humainsMax = humainsMax;
	}

	public void affichageTechnologie(Technologie t) {
		try {
			Method m = Menu.class.getMethod("affichage"+t.getName());
		  	try {
		  		Menu instance = new Menu();
			    Method method = instance.getClass().getDeclaredMethod("affichage"+t.getName());
			    method.setAccessible(true);
			    method.invoke(instance);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		} catch (SecurityException e) {}
		  catch (NoSuchMethodException e) {}
	}

}

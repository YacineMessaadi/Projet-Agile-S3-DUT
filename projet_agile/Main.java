package projet_agile;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;


public class Main implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static Colonie col;
	static Scanner saisie = new Scanner(System.in);
	static Epoques ep;
	static int nbMax;
	static int nbExplo;
	static int nbExploVoiture;
	static int nbChasse;
	public static String lignee, location;

	private static boolean verificationSaisie(String stringTest) {
		if(stringTest.length() == 0 || stringTest.length() >15) return false;
		return true;
	}



	public static void main(String[] args) {
		Csvreader.changeColor("res");
		Menu.main(null);

		int choixMenu = -1;
		choixMenu = Menu.choix();

		switch(choixMenu) {
		case 1:
			initOuCharger();
			turn();
		case 2:
			System.out.println("=================== Règles du jeu ===================\n");
			System.out.println("Tu incarnes une lignée d'être humain.");
			System.out.println("Le principe est simple, tu dois survivre au fil du temps\n");
			System.out.println("Ce jeu tour par tour t'offre deux choix minimum par tour: ");
			System.out.println("-Chasser               -Explorer\n");
			System.out.println("Tu as une barre de faim à maintenir au dessus d'un certain seuil, attention à la famine !");
			System.out.println("Tu dois explorer pour obtenir de nouvelles technologies et pouvoir changer d'ère.");
			System.out.println("Tu vas devoir faire les meilleurs choix pour aller le plus loin possible.");
			System.out.println("");
			System.out.println("1-JOUER \n2-QUITTER ");

			int choixRegles = -1;
			choixRegles = Menu.choix();
			switch(choixRegles) {
			case 1:
				initialisationJeu();
				turn();
				break;
			case 2:
				System.exit(0);
				break;
			}
		case 3: 
			System.exit(0);
			break;
		}
	}

	public static void affichageActions() {
		System.out.println("==========================================================");
		System.out.println("| Possibilités d'action :                                |");
		System.out.println("|       - (0) AIDE MOI A CHOISIR                         |");
		System.out.println("|       - (1) Chasser                                    |");

		if(col.technos.contains(Technologie.VOITURE)) System.out.println("|       - (2) Explorer en voiture                       |");
		else System.out.println("|       - (2) Explorer                                   |");

		if(col.technos.contains(Technologie.AGRICULTURE))
			System.out.println("|       - (3) Cultiver                                   |");

		if(col.technos.contains(Technologie.BOUSSOLE)&&col.technos.contains(Technologie.BATEAU))
			System.out.println("|       - (4) Découvrir le monde                          |");

		if(col.technos.contains(Technologie.CANON))
			System.out.println("|       - (5) Attaquer                                    |");

		if(col.technos.contains(Technologie.VACCINS) && !col.getVaccinne())
			System.out.println("|       - (6) Vacciner la population                      |");
		if(col.technos.contains(Technologie.CIMENT) && !col.getNouvellesHabitations())
			System.out.println("|       - (7) Créer de nouvelles habitations              |");
		if(col.technos.contains(Technologie.CIMENT) && col.getNouvellesHabitations())
			System.out.println("|       - (7) Arrêter de créer de nouvelles habitations   |");
		if(col.technos.contains(Technologie.IMPRIMERIE))
			System.out.println("|       - (8) Envoyer des lettres d'amitié à D. TRUMP     |");
		System.out.println("==========================================================");
		System.out.println(" Technologies découvertes :                             ");
		for ( Technologie t : Technologie.getTechnologieEpoque(col.getEpoque())) {
			if(col.technos.contains(t)) System.out.println(" 		"+t+"                             ");

		} 
		System.out.println("==========================================================");
		System.out.println(" Technologies à découvrir  :                             ");
		for ( Technologie t : Technologie.getTechnologieEpoque(col.getEpoque())) {
			if(!col.technos.contains(t)) System.out.println(" 		"+t+"                             ");

		} 
		System.out.println("==========================================================");
	}
	public static void turn() {
		for(Humain h : col.colonie) {
			h.setEsperanceVie(20*col.getEpoque());
		}

		while (col.getTaille() > 0) {
			checkEpoqueChange();
			System.out.println("Tour n° " + (col.Nbturn+1) + "    |    Colonie : "+ col.getTaille() +
					" personnes    |    Nourriture : "+ col.getNourriture() + " points    |    QI : "+ col.getQi()+
					"    |    Epoque : " +Epoques.values()[col.getEpoque()-1]);
			if(col.getHumainsMax() < col.getTaille()) {
				col.setHumainsMax(col.getTaille());
			}
			affichageActions();
			System.out.print("Ton choix d'action : ");
			int choix = Menu.saisieInteger(0, 8);
			clearScreen();
			if(choix != 0) {
				if(choix == 1) {
					col.chasser();
					nbChasse++;
					col.passerTemps();
					col.Nbturn++;
				}
				else if(choix == 2 && !col.technos.contains(Technologie.VOITURE)) {
					col.expedition();
					nbExplo++;
					col.passerTemps();
					col.Nbturn++;
				}
				else if(choix == 2 && col.technos.contains(Technologie.VOITURE)) {
					col.expedition();
					nbExploVoiture++;
					col.passerTemps();
					col.Nbturn++;
				}
				else if(choix == 3 && col.technos.contains(Technologie.AGRICULTURE)) { 
					col.cultiver() ;
					col.passerTemps();
					col.Nbturn++;
				}
				else if(choix == 6 && !col.getVaccinne() && col.technos.contains(Technologie.VACCINS)) {
					col.vaccinner();
					col.passerTemps();
					col.Nbturn++;
				}
				else if(choix == 4 && col.technos.contains(Technologie.BATEAU) && col.technos.contains(Technologie.BOUSSOLE) && !col.getExploration()) {
					col.decouvrir();
					col.passerTemps();
					col.Nbturn++;
				}
				else if(choix == 5 && col.technos.contains(Technologie.CANON)) {
					col.attaquer();
					col.passerTemps();
					col.Nbturn++;
				}
				else if(choix == 7 && col.technos.contains(Technologie.CIMENT)) {
					col.newHabitations();
					col.passerTemps();
					col.Nbturn++;
				}
				else if(choix == 8 && col.technos.contains(Technologie.IMPRIMERIE)) {
					col.lettres();
					col.passerTemps();
					col.Nbturn++;
				}
			}
			else aide();
			if(col.Nbturn%5==0) quitterPartie();
			
			//clearScreen();
		}
		try {
			Method m = Menu.class.getMethod("affichageLose");
		  	try {
		  		Menu instance = new Menu();
			    Method method = instance.getClass().getDeclaredMethod("affichageLose");
			    method.setAccessible(true);
			    method.invoke(instance);
			    
			    try {
					TimeUnit.SECONDS.sleep(5);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		} catch (SecurityException e) {}
		  catch (NoSuchMethodException e) {}
		System.out.println("==============================================================================");
		System.out.println(" Tu n'as pas réussi à faire perdurer ta colonie...                          ");
		System.out.println(" "+col.getMortExpe()+" sont morts en expéditions                         	 		    ");
		System.out.println(" "+col.getMortGuerre()+" sont tombés au combat                         	 		    ");
		System.out.println(" "+col.getMortFaim()+" sont morts de faim                         		    		  ");
		System.out.println(" QI max : "+col.getQi()+"				                        	");
		System.out.println(" Nouveaux nés totaux : "+col.getBaby()+"				                       ");
		System.out.println(" L'espèce humaine s'est éteinte au : "+Epoques.values()[col.getEpoque()-1]+"				");
		System.out.println("==============================================================================");

	}


	public static void initialisationJeu() {
		System.out.println("Bonjour !\nNous avons besoin de quelques informations avant de te transmettre la lourde charge qu'est la survie de votre tribu ...\n");
		do { 
			System.out.println("Quel est le nom de votre lignée ? (15caractères max.)");
			lignee = saisie.nextLine();
		}while(!verificationSaisie(lignee));

		do {
			System.out.println("Où se situe-t-elle ? (15caractères max.)");
			location = saisie.nextLine();
		}while(!verificationSaisie(location));

		System.out.println("Félicitations !");
		System.out.println("Tu es maintenant responsable de la lignée "+lignee+", localisée à "+location+" !");
		col = new Colonie(lignee);
	}

	public static void aide() {
		System.out.println("Menu d'aide aux actions : \n");

		System.out.println("");
		System.out.println("  - CHASSER (1) : Action innée et toujours disponible, permettant de ramener de la nourriture pour la colonie.");
		System.out.println("  -               La découverte du feu te permet d'augmenter la rentabilité de la chasse par la cuisson de la viande.");
		System.out.println("");
		System.out.println("  - EXPLORER (2) : Action innée et évolutive, permettant de trouver de nouvelles technologies.");
		System.out.println("                   Cela te coûte de la nourriture et des vies humaines.");
		System.out.println("                   Cette action évolue à la découverte de la VOITURE.");
		System.out.println("");
		if(col.technos.contains(Technologie.AGRICULTURE)) {
			System.out.println("  - CULTIVER (3) : Action se débloquant avec la découverte de l'AGRICULTURE. ");
			System.out.println("                   L'action prend un tour mais ajoute un rendement de 1 nourriture par tour.");
			System.out.println("");
		}
		if(col.technos.contains(Technologie.BATEAU) && col.technos.contains(Technologie.BOUSSOLE)) {
			System.out.println("  - DECOUVRIR LE MONDE (4) : Action se débloquant à la découverte de la BOUSSOLE et du BATEAU.");
			System.out.println("                             L'action embarque 10 personnes et dure 5 tours.");
			System.out.println("                             Ramène de la nourriture et une expédition.");
			System.out.println("");
		}
		if(col.technos.contains(Technologie.CANON)) {
			System.out.println("  - ATTAQUER (5) : Action se débloquant avec le CANON. Permet d'attaquer une colonie ennemie.");
			System.out.println("                   Possibilité de gain ou de perte d'habitants.");
			System.out.println("");
		}
		if(col.technos.contains(Technologie.VACCINS)) {
			System.out.println("  - VACCINER (6) : Action se débloquant avec le VACCIN. Action unique.");
			System.out.println("                   Permets d'arrêter les maladies tombant de manière aléatoire.");
			System.out.println("");
		}
		if(col.technos.contains(Technologie.CIMENT)) {
			System.out.println("  - NOUVELLES HABITATIONS (7) : Action se débloquant avec la découverte du CIMENT.");
			System.out.println("                                Permet d'investir, tant qu'il est activé dans des maisons. Des nouveaux habitants vont débarquer.");
		}
		if(col.technos.contains(Technologie.IMPRIMERIE)) {
			System.out.println("  - LETTRES (8) : Action se débloquant avec la découverte de l'IMPRIMERIE.");
			System.out.println("                  Deviens ami avec TRUMP.");
		} 
	}

	public final static void clearScreen(){

		System.out.print("\033[H\033[2J");

	}

	public static void initOuCharger() {
		System.out.println("Veux-tu charger la dernière partie ? (Oui pour accepter / Entrée pour une nouvelle simulation)");
		String s = saisie.nextLine();
		if(s.equals("Oui") || s.equals("oui")) {
			try {
				FileInputStream fis = new FileInputStream(System.getProperty("user.dir")+"/Sauvegarde");
				System.out.println("Fichier trouvé !");
				ObjectInputStream ois = new ObjectInputStream(fis);
				col = (Colonie) ois.readObject(); 
				ois.close();
			} catch (Exception e) { 
				e.printStackTrace(); 
			}
			return;
		}
		initialisationJeu();
		System.out.println("=====================================");
	}

	public static String vac() {
		String res;
		if(col.getVaccinne()) {
			res = "Oui";
		}
		else {
			res = "Non";
		}
		return res;
	}
	public static void checkEpoqueChange() {
		boolean allTechnoDiscovered = true;
		for(Technologie t : Technologie.getTechnologieEpoque(col.getEpoque())) {
			if(!col.technos.contains(t)) {
				allTechnoDiscovered = false;
				return;
			}
		}
		if(allTechnoDiscovered) {
			col.changerEpoque(col.getEpoque());
		}

		if(col.getEpoque() == 6) {
			System.out.println("BRAVO TU AS GAGNE");
			System.out.println("=====================================");
			System.out.println(" Colonie "+col.nom+"			                        		    ");
			System.out.println(" Humains MAX: "+col.getHumainsMax()+"			                        		    ");
			System.out.println(" Nombre de tours: "+col.Nbturn+"                        	 		");

			System.out.println(" Morts en expéditions: "+col.getMortExpe()+"                        	 		");
			System.out.println(" Morts au combat: "+col.getMortGuerre()+"                         	 		");
			System.out.println(" Morts de faim: "+col.getMortFaim()+"                         		    			 ");
			System.out.println(" QI max: "+col.getQi()+"				                        		    ");
			System.out.println(" Nouveaux nés totaux: "+col.getBaby()+"				                        		    ");
			System.out.println(" Nombre d'explorations: "+nbExplo+"				                        		    ");
			System.out.println(" Nombre d'explorations en voiture: "+nbExploVoiture+"				                        		    |");
			System.out.println(" Nombre de chasses: "+nbChasse+"				                        		    ");
			System.out.println(" Population vaccinée: "+vac()+"			                        		    ");
			System.out.println("==============================================================================");

			System.exit(0);
		}

	}

	@SuppressWarnings("resource")
	public static boolean quitterPartie(){
		//System.out.println('\n' + "Voulez-vous sauvegarder la partie et quitter ? (Oui pour quitter / Entrée pour continuer)");
		//Scanner sc = new Scanner(System.in);

		//if(sc.nextLine().equals("Oui")) {
		try {
			FileOutputStream fs = new FileOutputStream(System.getProperty("user.dir")+"/Sauvegarde");
			ObjectOutputStream os = new ObjectOutputStream(fs);
			os.writeObject(col);
			os.close();
			System.out.println('\n'+"Sauvegarde automatique réussie !" + '\n');
		} catch (Exception e) { 
			e.printStackTrace(); 
		}
		return true;
		//}
		//return false;
	}

}

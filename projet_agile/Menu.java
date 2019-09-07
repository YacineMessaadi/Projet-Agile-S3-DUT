package projet_agile;

import java.lang.reflect.Method;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Menu {
	public static Scanner sc = new Scanner(System.in);
	public static int resultat;
	public static void main(String[] args) {
		
		
		
				
		Csvreader.changeColor("rouge");
		System.out.println("  _______ _             _                                 _    _           _     ____      _                          "); 
		delai(100);
		System.out.println(" |__   __| |           | |                               | |  (_)         | |   / __ \\    | |                         "); 
		delai(100);
		System.out.println("    | |  | |__   ___   | |__  _   _ _ __ ___   __ _ _ __ | | ___ _ __   __| |  | |  | | __| |_   _ ___ ___  ___ _   _ ");
		delai(100);
		Csvreader.changeColor("bleu");
		System.out.println("    | |  | '_ \\ / _ \\  | '_ \\| | | | '_ ` _ \\ / _` | '_ \\| |/ / | '_ \\ / _` |  | |  | |/ _` | | | / __/ __|/ _ \\ | | | ");
		delai(100);
		System.out.println("    | |  | | | |  __/  | | | | |_| | | | | | | (_| | | | |   <| | | | | (_| |  | |__| | (_| | |_| \\__ \\__ \\  __/ |_| |");
		Csvreader.changeColor("res");
		delai(100);
		System.out.println("    |_|  |_| |_|\\___|  |_| |_|\\__,_|_| |_| |_|\\__,_|_| |_|_|\\_\\_|_| |_|\\__,_|   \\____/ \\__,_|\\__, |___/___/\\___|\\__, |"); 
		delai(100);
		System.out.println("                                                                                              __/ |              __/ |");
		delai(100);
		System.out.println("                                                                                             |___/              |___/ ");
	
		System.out.print("======================================\n\n");
		System.out.println("Menu du jeu :\n");
		System.out.println("1- Jouer");
		System.out.println("2- But du jeu");
		System.out.print("3- Quitter le menu\n\n");
		System.out.println("======================================");
		
	}
	
	public static int choix() {
		System.out.println("Entrez votre choix: ");
		int choix = saisieInteger(1, 3);
		int resultat = -1;
		
		switch(choix) {
		case 1:
			resultat = 1;
			break;
		case 2:
			resultat = 2;
			break;
		case 3:
			System.exit(0);
			break;
		default:
			choix();
		}
		Main.clearScreen();
		return resultat;
	}
	
	public static int saisieInteger(int min, int max) {
		int num = -1;
		boolean bon = false;
		while(!bon) {
			try {
                int tmp = Integer.parseInt(sc.nextLine());
                if(tmp>=min && tmp<=max) num = tmp;
                break;
            } catch (NumberFormatException nfe) {
                System.out.print("Essayez encore : ");
            }
		}
		return num;
	}
	
	private static void delai(int i) {
		try {
			TimeUnit.MILLISECONDS.sleep(i);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public  void affichageFeu() {
		Csvreader.changeColor("rouge");
		Csvreader.affichageMultiple("Feu","Feu2",null);
		Csvreader.changeColor("res");
	}
	
	public  void affichageLose() {
		Csvreader.affichageMultiple("dead1", "dead2", "dead3", 300);
	}
	
	public  void affichageOutil() {
		Csvreader.affichageCsv("outils");
	}
	
	public  void affichageLanguage() {
		Csvreader.affichageCsv("language");
	}
	
	public  void affichageAgriculture() {
		Csvreader.affichageCsv("agriculture");
	}
	
	public  void affichageAcier() {
		Csvreader.affichageCsv("acier");
	}
	
	public  void affichageBoussole() {
		Csvreader.affichageCsv("boussole");
	}
	
	public  void affichageBateau() {
		Csvreader.affichageCsv("bateau");
	}
	
	public  void affichagePapier() {
		Csvreader.affichageCsv("papier");
	}
	
	public  void affichageCiment() {
		Csvreader.affichageCsv("mur");
	}
	
	public  void affichageCanon() {
		Csvreader.affichageCsv("canon");
	}
	
	public  void affichageVoiture() {
		Csvreader.affichageMultiple("car", "car2", "car3");
	}
	
	public  void affichageImprimerie() {
		Csvreader.affichageCsv("imp");
	}
	
	public  void affichageHorloge() {
		Csvreader.affichageCsv("horloge");
	}
	
	public  void affichageAmpoule() {
		System.out.println("\u001B[43m");
		Csvreader.affichageCsv("ampoule");
		System.out.println("\u001B[40m");
	}
	public  void affichageVaccin() {
		Csvreader.changeColor("rouge");
		Csvreader.affichageCsv("ser1gue");
		Csvreader.changeColor("res");
	}
	
	public  void affichageAvion() {
		Csvreader.affichageCsv("plane");
	}
	
	public  void affichageBombenucleaire() {
		for(int i = 0;i< 2;i++) {
		Csvreader.affichageCsv("Bomb1");
		delai(600);
		System.out.print("\033[H\033[2J");  
	    System.out.flush();
	    System.out.println("\033[0;92m");
	    delai(50);
	    System.out.print("\033[H\033[2J");  
	    System.out.flush();
	    Csvreader.affichageCsv("bomb2");
		delai(600);
		System.out.print("\033[H\033[2J");  
	    System.out.flush();
	    System.out.println("\033[0;92m");
	    delai(50);
	    System.out.print("\033[H\033[2J");  
	    System.out.flush();
	    Csvreader.affichageCsv("bomb3");
		delai(600);
		System.out.print("\033[H\033[2J");  
	    System.out.flush();
	}
		Csvreader.changeColor("res");
	}
	
	public  void affichageRefrigeateur() {
		Csvreader.affichageCsv("fridge");
	}
	
	public  void affichageInternet() {
		Csvreader.affichageCsv("computer");
	}
	
	public void affichageNavettespatiale() {
		Csvreader.affichageCsv("space");
	}
	
}

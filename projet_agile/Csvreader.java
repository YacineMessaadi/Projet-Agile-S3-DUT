package projet_agile;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Csvreader{
	
	public static void changeColor(String s) {
		switch(s) {
		case "res":
			//RESET
			System.out.print("\u001B[0m");
			break;
		case "rouge":
			//ROUGE
			System.out.print("\u001B[31m");
			break;
		case "vert":
			//VERT
			System.out.print("\u001B[32m");
			break;
		case "bleu":
			//BLEU
			System.out.print("\u001B[34m");
			break;
			default:
				System.out.println("\u001B[0m");	
		}
	}
	
	public static void affichageCsv(String fileName) {
		 //File file = new File("src/projet_agile/res/" + fileName + ".csv");
		 InputStream file = Csvreader.class.getResourceAsStream("res/" + fileName + ".csv");
		 List<List<String>> records = new ArrayList<>();
		 try (BufferedReader br = new BufferedReader(new InputStreamReader(file))) {
		     String line;
		     while ((line = br.readLine()) != null) {
		         String[] values = line.split(";");
		         records.add(Arrays.asList(values));
		     }
		 } catch (Exception e) {
			e.printStackTrace();
		 }
		 System.out.print("\033[H\033[2J");  
		 for(int op= 0;op<records.size();op++) {
			 System.out.println(records.get(op).toString().substring(1,records.get(op).toString().length()-1));
		
			
		 }
	}
	
	public static void affichageMultiple(String fileName1,String fileName2,String fileName3) {
		for(int i = 0; i<3;i++) {
			affichageCsv(fileName1);
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.print("\033[H\033[2J");  
		    System.out.flush();
			affichageCsv(fileName2);
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.print("\033[H\033[2J");  
		    System.out.flush();
			if(fileName3 != null) {
				affichageCsv(fileName3);
				try {
					TimeUnit.SECONDS.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.print("\033[H\033[2J");  
			    System.out.flush();
			}
		}
	}
	
	public static void affichageMultiple(String fileName1,String fileName2,String fileName3,int i) {
		affichageCsv(fileName1);
			try {
				TimeUnit.MILLISECONDS.sleep(i);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.print("\033[H\033[2J");  
		    System.out.flush();
			affichageCsv(fileName2);
			try {
				TimeUnit.MILLISECONDS.sleep(i);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.print("\033[H\033[2J");  
		    System.out.flush();
			if(fileName3 != null) {
				affichageCsv(fileName3);
				try {
					TimeUnit.MILLISECONDS.sleep(i);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.print("\033[H\033[2J");  
			    System.out.flush();
			}
		}
	


}
	
	
	
	  
 





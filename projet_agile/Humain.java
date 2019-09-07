package projet_agile;

import java.io.Serializable;
import java.util.Random;

public class Humain implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int age;
	private boolean male;
	private boolean mort = false;
	private boolean parent = false;
	private int qi;
	private int esperanceVie;
	
	
	public Humain() {
		this.age = 0;
		Random r = new Random();
		this.male = r.nextBoolean();
		this.qi = r.nextInt(10);
	}
	
	public Humain(boolean male) {
		this.age = 0;
		this.male = male;
	}

	public int getAge() {
		return age;
	}

	public boolean isMale() {
		return male;
	}
	
	public void vieillir() {
		age++;
		if(age>esperanceVie) {
			if(age-esperanceVie == 0) {
				Random r = new Random();
				if(r.nextInt(5) < 2) {
					this.mort=true;
				}
				this.mort=false;
			}
			if(age-esperanceVie == 1) {
				Random r = new Random();
				if(r.nextInt(5) < 4) {
					this.mort=true;
				}
				this.mort=false;
			}
			else {
				this.mort=true;
			}
		}
	}
	
	public boolean isParent() {
		return parent;
	}

	public void setParent(boolean parent) {
		this.parent = parent;
	}

	public boolean isMort() {
		return mort;
	}

	public int getQi() {
		return qi;
	}

	public void setQi(int qi) {
		this.qi = qi;
	}
	
	public void setQiEnfant(Humain h1, Humain h2) {
		this.qi = (h1.getQi() + h2.getQi()) / 2;
	}

	public int getEsperanceVie() {
		return esperanceVie;
	}

	public void setEsperanceVie(int esperanceVie) {
		this.esperanceVie = esperanceVie;
	}
	
}

package ArkaDroid;

import java.awt.geom.Rectangle2D;

public class Cegla extends Rectangle2D.Float implements Standardy {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8718549472107806162L;
	
	int lives, punkty;
	
	public Cegla (int x, int y)
	{
		this.x = x;
		this.y = y;
		this.width = 60;
		this.height = 25;
		this.lives = 1;
		this.punkty = 10;
	}
	
	
	public void ustawPunkty(int punkty)
	{
		this.punkty = punkty;
	}
	
	public void ustawLives(int zycia)
	{
		this.lives = zycia;
	}
	
	public void ustawCegle(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
	
	public Cegla zmniejszLives(Cegla c,int i, int j)
	{
		switch(c.lives)
		{
		case 3 : return new cMedium(j*60,i*25);	
		case 2 : return new cLow(j*60,i*25);	
		case 1 : return null;	
		default: return c;
		}
	}
}

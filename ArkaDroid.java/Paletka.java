package ArkaDroid;

import java.awt.geom.Rectangle2D;


public class Paletka extends Rectangle2D.Float implements Standardy{
	
	
	/**
	 * 
	 */
	
	int dx;
	
	private static final long serialVersionUID = 6238801291281987740L;

	Paletka ()
	{
		this.x = GRA_SZEROKOSC/2-PALETKA_SZEROKOSC/2;
		this.y = PALETKA_Y;
		this.height = PALETKA_WYSOKOSC;
		this.width = PALETKA_SZEROKOSC;
	}
	
	public void ruszLewo(int dx)
	{
		this.x -= dx;
		if( x < 0 ) x = 0;
	}
	
	public void ruszPrawo(int dx)
	{
		this.x += dx;
		if (x + PALETKA_SZEROKOSC > GRA_SZEROKOSC) x = GRA_SZEROKOSC - PALETKA_SZEROKOSC;
	}

	
	
	public void ustawPaletke(int x)
	{
		this.x = x;
		if(x < 0) this.x = 0;
		if(x > GRA_SZEROKOSC - PALETKA_SZEROKOSC) this.x = GRA_SZEROKOSC - PALETKA_SZEROKOSC;
	}
	
	public float pobierzPolozenie(){
	return x;
	}

}

package ArkaDroid;

import java.util.ArrayList;

public class Gracz extends Thread implements Standardy, Comparable<Gracz>{

	ArrayList<GraczListener> sluchacze;
	static int sekundy;
	Gra gra;
	PanelGracza panelGracza;
	int zbiteCegly;
	int zycia, wynik, czas;
	Integer SCORE, TIME;
	String nick;
	
	public Gracz(String nick,int SCORE,int TIME)
	{
		this.nick = nick;
		this.TIME = TIME; 		// SUMA CZASU GRACZA
		this.SCORE = SCORE;		// SUMA WYNIKOW GRACZA
		this.zycia = 3;			// POCZ¥TKOWE ¯YCIA GRACZA
		this.wynik = 0;			// POCZ¥TKOWY WYNIK
		sekundy = 0;	//???
		
		
		sluchacze = new ArrayList<GraczListener>();
		
	}
	

	public String toString()
	{
		return nick +" : " +SCORE + " : " + TIME;
	}
	
	public void watekStart()
	{
		this.start();
	}
	
	public void run()
	{
		System.out.println("czas");	
		while(OknoGlowne.graCzynna)
		{
			System.out.println("czas");	
			try{sleep(1000);}
			catch (InterruptedException e) {System.out.println("Problem z czasem gry");}
			if(OknoGlowne.pauza == false && Gra.przegrana ==false && Gra.czasStop==false)
			{
			sekundy+=1;
			zwiekszTIME();
			
			}
			fireZmienionoCzas();
			//System.out.println(sekundy);			
		}
	}
	
	public int pobierzSCORE()
	{
		return SCORE; 
	}
	
	public void dodajWynik(int wynik)
	{
		SCORE += wynik; 
	}
	
	public int pobierzTIME()
	{
		return TIME;
	}
	
	public void zwiekszTIME()
	{
		TIME++;
		
	}
	
	public int pobierzWynik()
	{
		return wynik;
	}
	
	public void resetujWynik()
	{
		wynik = 0; fireZmienionoWynik();
	}
	
	
	
	public void resetujCzas(int czas)
	{
		Gracz.sekundy = 0;
	}
	
	public int pobierzCzas()
	{
		return Gracz.sekundy;
	}
	
	public String pobierzNick()
	{
		return nick;
	}
	
	
	
	public void usunZycie()
	{
		zycia--;
		fireZmienionoZycia();
		if(zycia==0)
		{
			Gra.przegrana = true;
		}
	}
	
	public void ustawZycia(int zycia)
	{
		this.zycia = zycia;
		fireZmienionoZycia();
	}
	
	public void ustawCzas(int sekundy)
	{
		Gracz.sekundy = sekundy;
	}
	
	public void dodajPunkty(int punkty)
	{
		wynik += punkty; fireZmienionoWynik();
	}
	
	
	
	
	//
	//³apanie zdarzeñ
	//
	public void addGraczListener(GraczListener gl)  //dodaje sluchacza
	{
		sluchacze.add(gl);
	}
	
	public void removeGraczListener(GraczListener gl)  //usuwa sluchacza
	{
		sluchacze.remove(gl);
	}
	
	public void fireZmienionoCzas()  // ³apie zdarzenie zmiany czasu dla wszystkich sluchaczy z listy
	{
		for(GraczListener gl : sluchacze) gl.zmienionoCzas(sekundy, TIME); 
	}
	
	public void fireZmienionoZycia()  // ³apie zdarzenie zmiany ¿ycia dla wszystkich sluchaczy z listy
	{
		for(GraczListener gl : sluchacze) gl.zmienionoZycia(zycia); 
	}
	
	public void fireZmienionoWynik()  // ³apie zdarzenie zmiany wyniku dla wszystkich sluchaczy z listy
	{
		for(GraczListener gl : sluchacze) gl.zmienionoWynik(wynik); 
	}

	
	@Override
	public int compareTo(Gracz o) {
		// TODO Auto-generated method stub
		
		int sprWynik = SCORE.compareTo(o.SCORE);
		
		if(sprWynik == 0)
		{
			return Integer.reverse(TIME.compareTo(o.TIME));
		}
		else return sprWynik;
	}
	
	
}

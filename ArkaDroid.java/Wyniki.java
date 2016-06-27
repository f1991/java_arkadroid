package ArkaDroid;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Collections;

public class Wyniki {

	ArrayList<Gracz> Gracze;
	
	
	public Wyniki()
	{
		Gracze = new ArrayList<Gracz>();
	}
	
	public void odczytWynikow()
	{
		Gracze = new ArrayList<Gracz>();
		
		long pozycja=0, dlugoscpliku=0;
	     
	     RandomAccessFile raf = null;

	     try {
	        raf = new RandomAccessFile("wyniki.null", "rw");
	      } catch (FileNotFoundException e) {
	          System.out.println("B£¥D PRZY OTWIERANIU PLIKU!");
	          System.exit(1);
	      }

	     // SWOBODNY ODCZYT Z PLIKU
	     try { 
	    	 
	        dlugoscpliku = raf.length();
	        System.out.println("Dlugosc pliku: " + dlugoscpliku);
	        raf.seek(0); // "skok" na pocz¹tek pliku
	        while (pozycja < dlugoscpliku){
	        String[] temp = null;
	        String odkodowane = odkoduj(raf.readLine());
	         temp = odkodowane.split(" : "); 
	         pozycja = raf.getFilePointer();
	         if(temp.length==3)
	         Gracze.add(new Gracz(temp[0],Integer.parseInt(temp[1]), Integer.parseInt(temp[2])));
	         
	         }
	      } catch (IOException e1) {
	            System.out.println("B£¥D ODCZYTU Z PLIKU!");
	            System.exit(2);
	       }
	     
	   //ZAMYKANIE PLIKU: 
	      try {
	          raf.close();
	       } catch (IOException e) {
	             System.out.println("B£¥D PRZY ZAMYKANIU PLIKU!");
	             System.exit(3); 
	       }
	       System.out.println("polozenie wskaznika :" + pozycja);
	     
	     //
	     
	     System.out.println(Gracze.size() + " przed przetwarzaniem");
	     przetwarzanieWynikow();
	     
	}
	
	public void zapiszWynik(Gracz gracz)
	{
		int t = sprawdzGracza(gracz);
		// sprawdz gracza sprawdza czy istnieje gracz o tym samym imieniu jesli isnieje to zwraca pozycje w tablicy jesli nie to -1
		if(t==-1)  //jesli gracz taki nie istnieje czyli nie ma takiego w pliku to //
		{	
			System.out.println("nie Istanieje!");
			Gracze.add(0, gracz);						// 0, gracz
			przetwarzanieWynikow();
			PrintWriter pw;
			try{
				pw = new PrintWriter("wyniki.null");   
				for(Gracz g : Gracze)
					pw.println(zakoduj(g.toString()));
				pw.close();
				System.out.println("Zapis powiodl sie!");}
			catch(IOException e){
					System.out.println("Nie powiodl sie zapis!");} 		 
	   	}
		else { //jesli taki gosc istnieje to szukamy i usuwamy go z tabeli
			System.out.println("Istanieje!");
			Gracze.remove(t);
				
			Gracze.add(0, gracz);
			przetwarzanieWynikow();
			PrintWriter pw;
			try{
				pw = new PrintWriter("wyniki.null");   
				for(Gracz g : Gracze)
					pw.println(zakoduj(g.toString()));
				pw.close();
				System.out.println("Zapis powiodl sie!");}
			catch(IOException e){
				System.out.println("Nie powiodl sie zapis!");}
			}
		
	}
	
	public Gracz sprawdzGracza(String nick)
	{
		odczytWynikow();
		
		for(Gracz g : Gracze)
			if(g.nick.equals(nick)) return g;
		
		
		
		
		return null;
	}
	
	public int sprawdzGracza(Gracz gr)
	{
		odczytWynikow();
		
		for(int i = 0; i < Gracze.size(); i++)
			if(Gracze.get(i).nick.equals(gr.nick)) return i;
		
		return -1;
	}

	
	
	public void przetwarzanieWynikow()
	{
		Collections.sort(Gracze);
		Collections.reverse(Gracze);
			
		while(Gracze.size()>10)
			Gracze.remove(10);		
	}
	
	public String odkoduj(String zakodowane)
	{
		String odkodowany;
		char[] tablica = zakodowane.toCharArray();
		
		for(int a = 0; a < tablica.length; a++) tablica[a] = (char) (tablica[a] - ((a)%30));
		
		odkodowany = new String(tablica);
		return odkodowany;
	}
	
	public String zakoduj(String odkodowane)
	{
		String zakodowane;
		char[] tablica = odkodowane.toCharArray();
		
		for(int a = 0; a < tablica.length; a++) tablica[a] = (char) (tablica[a] + ((a)%30));
		
		zakodowane = new String(tablica);
		
		return zakodowane;
		
	}
	
}

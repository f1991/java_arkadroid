package ArkaDroid;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


public class Poziom implements Standardy{
	
	
	static int ilePustych;  //dla kazdego poziomu obliczana jest ilosc pustych pól oraz dodana iloœæ ska³
	boolean czyIstnieja;
	
	public Poziom()
	{
		System.out.print(liczbaPoziomow);
		//wczytajPoziom(2);
	}
	
	public final Cegla[][] wydajCegly()
	{
		return wczytajPoziom(1);
	}
	
	
	FileFilter FF = new FileFilter()
	{
		@Override
		public boolean accept(File f) {
			// TODO Auto-generated method stub
			
			if(f.getAbsolutePath().endsWith(ROZSZERZENIE)) return true;
			
			return false;
		}
		
	};
	int liczbaPoziomow = (new File("Poziomy")).listFiles(FF).length;
	
	
	
	
	
	public Cegla[][] wczytajPoziom(int i)
	{
//		Cegla[][] ceglyy = new Cegla[POLE_WIERSZE][POLE_KOLUMNY];
//		File[] poziom = new File[liczbaPoziomow];
//		ilePustych = new int[liczbaPoziomow];
//		BufferedReader br = null;
//		
//		
//		try{
//			for(int a = 0; a<liczbaPoziomow; a++)
//			{
//				poziom[a] = new File("Poziomy//poziom" +(a+1)+ ".level");
//				br = new BufferedReader(new FileReader(poziom[a]));
//				ceglyy = pobierzCegly(br, a);
//			
//			}
//			
//		}
//		catch(NullPointerException e)
//		{
//		e.printStackTrace();		
//		}
//		catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} 
//		
//		return ceglyy;
		
		Cegla[][] ceglyy = new Cegla[POLE_WIERSZE][POLE_KOLUMNY];
		File poziom;
		BufferedReader br = null;
		
		
		try{
//			for(int a = 0; a<liczbaPoziomow; a++)
//			{
				poziom = new File("Poziomy//poziom" +i+ ".level");
				System.out.println("Tu jestem"+poziom.getPath());
				br = new BufferedReader(new FileReader(poziom));
				ceglyy = pobierzCegly(br);
			
//			}
			
		}
		catch(NullPointerException e)
		{
		e.printStackTrace();		
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		} 
		
		return ceglyy;
		
	}
	
	public Cegla[][] pobierzCegly(BufferedReader br)
	{
		Cegla [][] cegly = new Cegla [POLE_WIERSZE][POLE_KOLUMNY];
		ilePustych = 0;
		
		for (int i = 0; i < POLE_WIERSZE; i++)
		{
			String[] wiersz = null;
			try{
				wiersz = br.readLine().split(" ");
			} catch(IOException e){
				System.out.println("B³¹d czytania pliku poziomu: "+e);
			}
			
//			for (int j = 0; j < POLE_KOLUMNY; j++)
//			{
//				if(wiersz[j].trim().equals("1")) { cegly[i][j] = new Cegla(j*CEGLA_SZEROKOSC,i*CEGLA_WYSOKOSC);}
//				else { cegly[i][j] = null; }
//			}
			
			
//			for (int j = 0; j < POLE_KOLUMNY; j++)
//			{
//				System.out.print("A");
//				switch(wiersz[j].trim())
//				{
//				case "0" : cegly[i][j] = null;															break;
//				case "1" : cegly[i][j] = new Cegla(j*CEGLA_SZEROKOSC,i*CEGLA_WYSOKOSC, Rodzaj.LOW);		break;
//				case "2" : cegly[i][j] = new Cegla(j*CEGLA_SZEROKOSC,i*CEGLA_WYSOKOSC, Rodzaj.MEDIUM);	break;
//				case "3" : cegly[i][j] = new Cegla(j*CEGLA_SZEROKOSC,i*CEGLA_WYSOKOSC, Rodzaj.HIGH);	break;
//				case "S" : cegly[i][j] = new Cegla(j*CEGLA_SZEROKOSC,i*CEGLA_WYSOKOSC, Rodzaj.SKALA);	break;
//				default  : cegly[i][j] = null;															break;
//				}
//			}
			
			for (int j = 0; j < POLE_KOLUMNY; j++)
			{
				switch(wiersz[j].trim())
				{
				case "0" : cegly[i][j] = null; ilePustych++;	break;
				case "1" : cegly[i][j] = new cLow(j*CEGLA_SZEROKOSC,i*CEGLA_WYSOKOSC); break;
				case "2" : cegly[i][j] = new cMedium(j*CEGLA_SZEROKOSC,i*CEGLA_WYSOKOSC); break;
				case "3" : cegly[i][j] = new cHigh(j*CEGLA_SZEROKOSC,i*CEGLA_WYSOKOSC); break;
				case "/" : cegly[i][j] = new cSkala(j*CEGLA_SZEROKOSC,i*CEGLA_WYSOKOSC);	ilePustych++; break;
				default  : cegly[i][j] = null;	ilePustych++; break;
				}
			}
		}
		return cegly;
	}
	
	public int[] pilka_wCegle ( double x, double y)
	{
		float xx = (float) POLE_KOLUMNY / (float) POLE_SZEROKOSC ;
		float yy =(float) POLE_WIERSZE / (float)POLE_WYSOKOSC;
		
		
		int index[] = new int[8];
		
			
		
			index[0] = (int) (xx * x);
			index[1] = (int) (xx * (x+PILKA_SREDNICA));
			index[2] = (int) (xx * x);
			index[3] = (int) (xx * (x+PILKA_SREDNICA));
			
			index[4] = (int) (yy * y);
			index[5] = (int) (yy * y);
			index[6] = (int) (yy * (y+PILKA_SREDNICA));
			index[7] = (int) (yy * (y+PILKA_SREDNICA));
			
			
			int sprawdz = 0;
			
			for(int a = 0 ; a < 4 ; a++){
				//System.out.print(index[a] + " " + index[a+4]);
				if(index[a]>= POLE_KOLUMNY || index[a+4]>=POLE_WIERSZE)
				{
					index[a] = -1;
					index[a+4] = -1;
					sprawdz++;
				}
			}
		
		if (sprawdz==4) return null;
		else return index;
	}
	

}

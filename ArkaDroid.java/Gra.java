package ArkaDroid;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.awt.image.MemoryImageSource;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


public class Gra extends JPanel implements Runnable, MouseMotionListener,MouseListener, Standardy{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1069694185573089636L;
	
	Thread praca;  												// w¹tek gry
	ArrayList<GraListener> sluchacze;
	Cursor przezroczysty;										// tworzenie przezroczystego kursora
	private BufferedImage[] obraz = new BufferedImage[5]; // obraz dla cegly
	
	Paletka paletka = new Paletka();							//nowa paletka
	Pilka pilka = new Pilka(360-PILKA_PROMIEN/2,PALETKA_Y-PILKA_PROMIEN,0,-2,this,paletka);			//nowa pilka od paletki
	Wyniki ow = new Wyniki();

	Cegla[][] cegly = new Cegla[POLE_WIERSZE][POLE_KOLUMNY];	//tablica cegiel
	Poziom poziom = new Poziom();
	Gracz player;//klasa poziom ktora bedzie wczytywala gre i sprawdzala w jakim obszarze jest pilka
	
	static volatile boolean uruchomiona=true;
	OknoGlowne og;
	
	static boolean uwolniona;											//czy pilka zostala uwolniona spod paletki (MouseListener)										//czy gracz stracil wszystkie zycia
	static int poziomGry;												//numer poziomu do wczytania
	static int poziomCegly;
	static boolean przegrana;
	static boolean czasStop;
	int zbiteCegly;
	static boolean rozrusznik = false;
	
	public Gra(OknoGlowne og)
	{
		this.og = og;
		addKeyListener(new KAdapter());
		wczytajObrazy();
		repaint();
		sluchacze = new ArrayList<GraListener>();
		uwolniona = false;
		poziomGry = 1;
		przegrana = false;		
		setFocusable(true);	
		setVisible(true);	
		addMouseMotionListener(this);
		addMouseListener(this);	
		cegly = poziom.wczytajPoziom(1);
		poziomCegly = CEGLA_MAX - Poziom.ilePustych;
		zbiteCegly = 0;
		czasStop=true;
		fireZmienionoPoziom();		
	}
	
	
	
	
	public void wczytajObrazy()  // wczytuje obrazy z folderu w ktorym znajduje sie projekt
	{
			try{
				for(int i = 0; i < 4; i++) obraz[i]=ImageIO.read(new File ("bloczek"+i+".jpg"));
				obraz[4] = ImageIO.read(new File("wygrana.jpg"));
			}
			catch(IOException e){
				JOptionPane.showMessageDialog(getParent(), "Wyst¹pi³ nieoczekiwany b³¹d braku plików: bloczek0-bloczek3");
				System.exit(0);
			}	
	}
		
		
	public Gracz pobierzGracza()
	{
		return player;
	}
	
	public void addNotify()
	{
		super.addNotify();
		praca = new Thread(this);
		praca.start();
	}
	
	protected void paintComponent(Graphics g){
		
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;

		if(OknoGlowne.graCzynna==false){
			g2d.drawImage(obraz[4], 0, 0, this);
			System.out.println("Pracuje");
		}
		else{
		
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setColor(new Color(0xb0c4de));
		g2d.fillRect(0, 0, GRA_SZEROKOSC, GRA_WYSOKOSC);
		
		for (int i = 0; i< cegly.length; i++)
		{
			
			for (int j = 0; j< cegly[i].length; j++)	
				if(cegly[i][j]!=null)
					switch(cegly[i][j].lives){
					case 1 : 	g2d.drawImage(obraz[1], j*60, i*25, this);	break;
					case 2 : 	g2d.drawImage(obraz[2], j*60, i*25, this);	break;
					case 3 : 	g2d.drawImage(obraz[3], j*60, i*25, this);	break;
					case -1 : 	g2d.drawImage(obraz[0], j*60, i*25, this);	break;
					default: 	break;
				}
		}
		g2d.setColor(Color.DARK_GRAY);
		g2d.fill(paletka);
		g2d.setColor(Color.BLACK);
		g2d.fill(pilka);
		
	
		final int[] pixels = new int [16*16];
		final Image image = Toolkit.getDefaultToolkit().createImage(new MemoryImageSource(16,16,pixels,0,16));
		przezroczysty = Toolkit.getDefaultToolkit().createCustomCursor(image, new Point(0,0), "invisibleCursor");
		setCursor(przezroczysty);

		if(OknoGlowne.pauza == true)
		{
			g.setColor(Color.GRAY);
			g.setFont(new Font("TimesRoman", Font.BOLD,200));
			g.drawString("PAUZA", 10, 150);
			}
		else if(przegrana == true) {
			g.setColor(Color.GRAY);
			g.setFont(new Font("TimesRoman", Font.BOLD,100));
			g.drawString("PRZEGRANA", 50, 100);
			} 
		}
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		long czasPrzed, czasRoznica, uspienie;
		
		czasPrzed = System.currentTimeMillis();
		
		while(OknoGlowne.graCzynna)
		{
			if(OknoGlowne.pauza==false && rozrusznik==true && przegrana==false)
			{
				if(uwolniona)
				{	
					pilka.pracuj();
					odbijaj();
					sprawdzPrzegrana();	
				}
				repaint();
			}
				
			czasRoznica = System.currentTimeMillis() - czasPrzed;
			uspienie = OPOZNIENIE - czasRoznica;
			
			if(uspienie<0) uspienie = 2;
			
			try {
				Thread.sleep(uspienie);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			czasPrzed = System.currentTimeMillis();
		}
		
	}

	
	public void rozpocznijGre()
	{
		rozrusznik = true;
		
		if((player=ow.sprawdzGracza(OknoGlowne.nick))!=null) { System.out.println("Udalo sie wskrzesic gracza!");}
		else player= new Gracz(OknoGlowne.nick, 0, 0); 
		player.watekStart();
	}
	
	public void sprawdzPrzegrana()
	{
		if(przegrana==true) poPrzegranej();
	}
	
	public void poPrzegranej()
	{
		repaint();
		if(JOptionPane.showOptionDialog(getParent(), "Przegrales na poziomie "
				 + poziomGry+"\n"+"\n"+"Chcesz graæ jeszcze raz?", "Uwaga!",
				 JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null,null)==0){
					System.out.println("Zostaje!");
					
						if(poziom.liczbaPoziomow >= poziomGry)
						{
							przegrana=true;
							uwolniona=false;
							pilka.zresetowana=false;
							repaint();	
							poziomPoPrzegranej(poziomGry);
							fireZmienionoPoziom();
							nastepnyPoziom(poziomGry);
							player.ustawZycia(3);
							przegrana=false;
							ruszGdyReset();
							repaint();
						} 

						}
						else {
							zatrzymanieSilowe();
							og.zakonczGre();
				
							return;
						}
	}
	
	public void poziomPoPrzegranej(int aktualnyPoziom)
	{
		if(aktualnyPoziom==1)
		{
			poziomGry = aktualnyPoziom;
		}
		else poziomGry = (aktualnyPoziom/2)+1;
	}
	

	public void odbijaj()
	{	
		int[] indexCegly = new int[8];
		indexCegly = poziom.pilka_wCegle(pilka.getX(), pilka.getY());
		
		if(indexCegly!=null)
			for (int i = 0; i < 4; i++)
			{
				int	b=indexCegly[i];
				int	a=indexCegly[i+4];
	
				if(b!=-1 || a!=-1)
					if(cegly[a][b]!=null)	
						if(pilka.aaa(cegly[a][b],a,b)==true)
						{
							player.dodajPunkty(cegly[a][b].punkty);
							if(cegly[a][b] instanceof cLow) dodajZbitaCegle();
							cegly[a][b] = cegly[a][b].zmniejszLives(cegly[a][b], a, b);
							if(sprawdzKoniecPoziomu()) KoniecPoziomu();
							return;
						}
			}
	}
	
	public void wygrana() throws IOException{
		
		player.dodajWynik(player.wynik);
		ow.zapiszWynik(player);	
		zatrzymanieSilowe();
		
		removeMouseMotionListener(this);
		removeMouseListener(this);
		sluchacze = null;
		player = null;
		sluchacze = null;

		repaint();
	}
	
	   public void zatrzymanieSilowe() {
	       OknoGlowne.graCzynna = false;
	   }
	
	
	public boolean sprawdzKoniecPoziomu()
	{
		if(zbiteCegly == poziomCegly)
		{
			uwolniona = false;
			pilka.zresetowana = false;
			czasStop = true;
			return true;
		}
		else return false;
	}

	public boolean sprawdzPauze()
	{
		if(OknoGlowne.pauza == true) {OknoGlowne.pauza = true; System.out.println("powinno dzialac a nie dziala"); return true;}
		return false;
	}
	
	
	public void KoniecPoziomu()
	{
		
		if(JOptionPane.showOptionDialog(getParent(), "Zakoñczy³eœ z powodzeniem gre na poziomie: "
													 + poziomGry+"\n"+"Twoj wynik to: "+player.wynik+"\n"+"Chcesz graæ nastêpny poziom?", "Uwaga!",
													 JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null,null)==0){
			System.out.println("Zostaje!");
			if(poziom.liczbaPoziomow > poziomGry)
			{
				player.dodajWynik(player.wynik);
				zwiekszPoziom();
			
			ow.zapiszWynik(player);	
				
			nastepnyPoziom(poziomGry);
			
			}
			else
				try {
					wygrana();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
				
		}
		else {
			zatrzymanieSilowe();
			og.zakonczGre();
			return;
		}
	}
	
	public void nastepnyPoziom(int level)
	{
		cegly = poziom.wczytajPoziom(level);
		
		poziomCegly = CEGLA_MAX - Poziom.ilePustych;
		zbiteCegly = 0;
		player.resetujWynik();
		player.resetujCzas(player.czas);
		ruszGdyReset();
		fireZbitoCegle();
	}
	
	public void zwiekszPoziom()
	{
		poziomGry++;
		fireZmienionoPoziom();
	}
	
	public void dodajZbitaCegle()
	{
		zbiteCegly++;
		fireZbitoCegle();
	}

	//zdarzenia myszy
	
	public void mousePressed(MouseEvent e)
	{
		
	}
	
	
	public void spacjaKlik()
	{
		if(OknoGlowne.pauza==false)
		{
		czasStop = false;
		pilka.resetujPilke();
		uwolniona = true;
		}
	}
	
	public void mouseClicked(MouseEvent e)
	{
			spacjaKlik();
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
		this.requestFocus();
		
		if(!uwolniona && !pilka.zresetowana && OknoGlowne.pauza==false)
		{
		paletka.ustawPaletke(e.getX()- PALETKA_SZEROKOSC/2);
		pilka.y = PALETKA_Y-PILKA_PROMIEN;
		pilka.x = (float) paletka.getCenterX()-PILKA_PROMIEN/2;
		}
		else paletka.ustawPaletke(e.getX()- PALETKA_SZEROKOSC/2);
	}



	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub	
	}	
	
	public void ruszGdyReset()
	{
		if(!uwolniona && !pilka.zresetowana && OknoGlowne.pauza==false)
		{
		pilka.y = PALETKA_Y-PILKA_PROMIEN;
		pilka.x = (float) paletka.getCenterX()-PILKA_PROMIEN/2;
		repaint();
		}
	}
	
	private class KAdapter extends KeyAdapter {

        public void keyPressed(KeyEvent e) {

            int key = e.getKeyCode();

            
            if ((key == KeyEvent.VK_LEFT)) {
            	
            	paletka.ruszLewo(20);
            	ruszGdyReset();
                System.out.println("Nossa");
            }
            if ((key == KeyEvent.VK_RIGHT)) {
            	paletka.ruszPrawo(20);
            	ruszGdyReset();
                System.out.println("Nossa");
            }
            if(key == KeyEvent.VK_SPACE)
            {
            	spacjaKlik();
            }
            if(key == KeyEvent.VK_ESCAPE)
            {
            	if(OknoGlowne.pauza) OknoGlowne.pauza = false;
    			else OknoGlowne.pauza = true;
    			repaint();
            }
        }
    }
	
	
	// GraListener
	
	public void addGraListener(GraListener gl)  //dodaje sluchacza
	{
		sluchacze.add(gl);
	}
	
	public void removeGraListener(GraListener gl)  //usuwa sluchacza
	{
		sluchacze.remove(gl);
	}
	
	public void fireZbitoCegle()  // ³apie zdarzenie zmiany czasu dla wszystkich sluchaczy z listy
	{
		for(GraListener gl : sluchacze) gl.zmianaZbitychCegiel(zbiteCegly);
	}
	
	public void fireZmienionoPoziom()
	{
		for(GraListener gl : sluchacze) gl.zmienionoPoziom(player.pobierzSCORE());
	}


}



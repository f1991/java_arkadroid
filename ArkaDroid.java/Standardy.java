package ArkaDroid;

public interface Standardy {

	
	//FPS
	int OPOZNIENIE = 12;
	// GRA
	int GRA_SZEROKOSC = 720;
	int GRA_WYSOKOSC = 600;
	//PI£KA
	int PILKA_PROMIEN = 10;
	int PILKA_SREDNICA = 2 * PILKA_PROMIEN;
	float PILKA_PREDKOSC = 3;
	
	//PALETKA
	int PALETKA_WYSOKOSC = 20;
	int PALETKA_Y = GRA_WYSOKOSC - 50;
	int PALETKA_SZEROKOSC = 100;
	int PALETKAB_SZEROKOSC = 150;
	int PALETKAS_SZEROKOSC = 65;
	//POLE DLA CEGIE£
	int POLE_SZEROKOSC = 720;
	int POLE_WYSOKOSC = 500;
	int POLE_KOLUMNY = 12;
	int POLE_WIERSZE = 20;
	//CEG£A
	int CEGLA_SZEROKOSC = 60;
	int CEGLA_WYSOKOSC = 25;
	int CEGLA_MAX = 240;
	int CEGLA_PUNKTY = 10;
	//POZIOM
	String ROZSZERZENIE = ".level";
}

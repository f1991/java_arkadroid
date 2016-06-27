package ArkaDroid;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class PanelGracza extends JPanel implements GraczListener, GraListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8808022783328172811L;
	String nick;
	Gra gra;
	JLabel NICK, PUNKTY, ZYCIA, CZAS, CEGLY, POZIOM; 
	JLabel TotalTime, TotalScore;
	
	
	public PanelGracza(Gra gra, String nick)
	{
		super(new GridLayout(4,2));
		this.gra = gra;
		this.nick = nick;
		
		this.setBackground(Color.lightGray);
		Font hv = new Font("Helvetica",Font.BOLD,30);
		
		POZIOM = new JLabel("Poziom: "+1);
		POZIOM.setFont(hv);
		
		this.NICK = new JLabel("Nazwa gracza: "+this.nick);
		this.NICK.setFont(hv);
		
		this.PUNKTY = new JLabel("Punkty: "+0);
		this.PUNKTY.setFont(hv);
		
		this.ZYCIA = new JLabel("Zycia: " +3);
		this.ZYCIA.setFont(hv);
		
		this.CZAS = new JLabel("Czas: ");
		this.CZAS.setFont(hv);
		
		this.CEGLY = new JLabel("Cegly: ");
		this.CEGLY.setFont(hv);
		
		this.TotalScore = new JLabel("Suma punktow: 0");
		this.TotalScore.setFont(hv);
		
		this.TotalTime = new JLabel("Suma czasow: 0");
		this.TotalTime.setFont(hv);
		
		this.setPreferredSize(new Dimension(720,200));
		
		this.add(POZIOM);
		this.add(NICK);
		this.add(PUNKTY);
		this.add(ZYCIA);
		this.add(CZAS);
		this.add(CEGLY);
		this.add(TotalScore);
		this.add(TotalTime);
		
		this.setVisible(true);
		gra.pobierzGracza().addGraczListener(this);
		gra.addGraListener(this);
		
		gra.fireZbitoCegle();
		
		this.repaint();
		
		
	}
	

	public String toString()
	{
		return POZIOM.getText() +" "+POZIOM.getName() + " "+POZIOM.isEnabled()+" "+POZIOM.isShowing();
	}
	

	@Override
	public void zmienionoCzas(int czas, int TIME) {
		// TODO Auto-generated method stub

		CZAS.setText("CZAS: " + czas);
		TotalTime.setText("Czas Gry: " + TIME);
		
		repaint();
	}

	public void zakoncz()
	{
		gra.pobierzGracza().removeGraczListener(this);
		gra.removeGraListener(this);
	}

	@Override
	public void zmienionoZycia(int zycia) {
		// TODO Auto-generated method stub
		ZYCIA.setText("Zycia: "+zycia);
		repaint();
	}



	@Override
	public void zmienionoWynik(int wynik) {
		// TODO Auto-generated method stub
		PUNKTY.setText("Wynik:"+wynik);
		repaint();
	}



	@Override
	public void zmianaZbitychCegiel(int zbiteCegly) {
		// TODO Auto-generated method stub
		CEGLY.setText("Cegly: "+zbiteCegly+"/"+Gra.poziomCegly);
		repaint();
	}



	@Override
	public void zmienionoPoziom(int SCORE) {
		// TODO Auto-generated method stub
		TotalScore.setText("Punkty Gry: " + SCORE );
		POZIOM.setText("Poziom: "+Gra.poziomGry);
		repaint();
	}

}

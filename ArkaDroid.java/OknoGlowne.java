package ArkaDroid;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;





public class OknoGlowne extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8272055094114183274L;
	/**
	 * @param args
	 */
	Image cegla;
	Gra nowaGra;
	PanelGracza panelGracza; 
	JButton start, stop, wyniki, zakoncz;
	static String nick;
	static volatile boolean graCzynna=false;
	static boolean pauza = false;
	private BufferedImage ekranStart, panelStart;
	
	
	public OknoGlowne(){
		
		super("ArkaDroid - Emil Faliñski");
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setPreferredSize(new Dimension(880,870));
		setLayout(null);
		setResizable(true);
		setLocation(new Point(240, 120));
		
		start = new JButton("Start");
		stop = new JButton("Pauza");
		wyniki = new JButton("Wyniki");
		zakoncz = new JButton("Stop");
		
		start.setBounds(10, 20, 100, 30);
		stop.setBounds(10,70 , 100, 30);
		wyniki.setBounds(10,120 , 100, 30);
		zakoncz.setBounds(10,170,100,30);
		
		start.addActionListener(this);
		stop.addActionListener(this);
		wyniki.addActionListener(this);
		zakoncz.addActionListener(this);

		
		try{
		ImageIcon ic = new ImageIcon("sciana.jpg");
		ekranStart = ImageIO.read(new File("ngAdin.jpg"));
		panelStart = ImageIO.read(new File("pgAdin.jpg"));
		cegla = ic.getImage();
		}
		catch(IOException e){
			JOptionPane.showMessageDialog(getParent(), "Wyst¹pi³ nieoczekiwany b³¹d braku plików: bloczek0-bloczek3");
		}
		
		//panelGracza = new PanelGracza();
		//panelGracza.setBounds(130,10,720,200);
		
		
		
		//add(panelGracza);
		add(start);
		add(stop);
		add(wyniki);
		add(zakoncz);
		pack();
		setVisible(true);
		
		repaint();
	}
	
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new OknoGlowne();
		
	}

	int i =0;

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		
		if(e.getSource()==stop && graCzynna)
		{
			if(pauza) pauza = false;
			else pauza = true;
			if(nowaGra!=null) nowaGra.repaint();
		}
		
		if(e.getSource()==start && !graCzynna)
		{
			pauza = false;
			if(((nick=JOptionPane.showInputDialog(getParent(), "Podaj nazwe gracza:", "Gracz", JOptionPane.DEFAULT_OPTION))!=null))
			{
			graCzynna = true;
			repaint();
			
			if(nowaGra!=null && panelGracza!=null) {
				nowaGra.removeAll(); panelGracza.removeAll();
				remove(nowaGra); remove(panelGracza);
				nowaGra = null; panelGracza = null;} 
			
			nowaGra = new Gra(this);
			nowaGra.setBounds(130, 220 , 720, 600);
			nowaGra.rozpocznijGre();
			panelGracza = new PanelGracza(nowaGra, nick);
			panelGracza.setBounds(130,10,720,200);
			
			
			add(nowaGra);
			add(panelGracza);
			pack();	
			nowaGra.fireZmienionoPoziom();
			nowaGra.requestFocus();
			
			}
		}
		
		if(e.getSource()==wyniki)
		{
			new OknoWyniki();
		}
		
		if(e.getSource()==zakoncz)
			if(nowaGra!=null && panelGracza!=null) zakonczGre();
			
		
		
	}

	public void paint(Graphics g) {
		super.paint(g);
		
		if(!graCzynna)	{
			g.drawImage(ekranStart, 138, 250, this);
			g.drawImage(panelStart, 138, 40, this);
		}
		else if(graCzynna)
		{
			for (int i = 250; i <= 840; i += 10) {
				g.drawImage(cegla, 128, i, this);
				g.drawImage(cegla, 858, i, this);
			}
		
			for (int i = 128; i <= 858; i += 10) {
				g.drawImage(cegla, i, 240, this);
				g.drawImage(cegla, i, 850, this);
			}
		}
	}
	public void zakonczGre()
	{
		nowaGra.zatrzymanieSilowe();
		nowaGra.removeAll(); panelGracza.removeAll();
		remove(nowaGra); remove(panelGracza);
		nowaGra = null; panelGracza = null; repaint();}
}

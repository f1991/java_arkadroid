package ArkaDroid;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;



public class Pilka extends Ellipse2D.Float implements Standardy{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3873364592692027295L;
	boolean zresetowana;
	int dx,dy;
	int srednica = 2*PILKA_PROMIEN;
	Paletka paletka;
	Cegla cegla;
	Gra area;
	Poziom poziom = new Poziom();
	Gracz player;
	
	
	
	
	public Pilka (int x, int y, int dx, int dy, Gra area, Paletka paletka)
	{
		this.x = x;
		this.y = y;
		this.width = PILKA_PROMIEN;
		this.height = PILKA_PROMIEN;
		this.dx = dx;
		this.dy = dy;
		this.area = area;
		this.paletka = paletka;
		this.zresetowana = false; 
	}
	
	
	public boolean aaa(Cegla c, int w, int k)
	{
		
		Point leftUp = new Point((int)c.getX(),(int)c.getY());
		Point rightUp = new Point((int)c.getMaxX(),(int)c.getY());
		Point rightDown = new Point((int)c.getMaxX(),(int)c.getMaxY());
		Point leftDown = new Point((int)c.getX(),(int)c.getMaxY());
		
		Line2D top = new Line2D.Float(leftUp, rightUp);
		Line2D bottom = new Line2D.Float(leftDown, rightDown);
		Line2D left = new Line2D.Float(leftUp, leftDown);
		Line2D right = new Line2D.Float(rightUp, rightDown);
		
		Rectangle2D pilka = new Rectangle2D.Double(this.getX(),this.getY(),this.width,this.height);
		
		if(this.intersects(c)){
			//System.out.println("tutaj");
		
			
			
		if(this.contains(leftUp))   // lewy gorny
		{
			if(k!=0&&area.cegly[w][k-1]!=null) dy=-dy;  		//od gory
			else if(w!=0&&area.cegly[w-1][k]!=null) dx=-dx;  //od lewej
			else if( dx > 0 && dy < 0) dx=-dx;
			else if( dx < 0 && dy > 0) dy=-dy;
			else{ 
				Point leftSUp = new Point((int)c.getX()+1,(int)c.getY()+1);
				if(this.contains(leftSUp))
				{
					this.x = x - dx;
					this.y = y - dy;
				}
				
				 dy = -dy; dx=-dx;
			}
			System.out.println("lewygorny");
		}
		
		else if(this.contains(rightUp)) //prawy górny
		{
			if(k!=11&&area.cegly[w][k+1]!=null) dy=-dy;  		//od gory
			else if(w!=0 && area.cegly[w-1][k]!=null) dx=-dx;  //od lewej
			else if(dx<0 && dy<0) dx=-dx;
			else if(dx>0 && dy>0) dy=-dy;
			else {
				Point rightSUp = new Point((int)c.getMaxX()-1,(int)c.getY()+1);
				if(this.contains(rightSUp))
				{
					this.x = x - dx;
					this.y = y - dy;
				}
				
				dx=-dx; dy=-dy;
				
				}
			System.out.println("prawy gorny");
		}
		else if(this.contains(rightDown)) //prawy dolny +
		{
			if(k!=11&&area.cegly[w][k+1]!=null) dy=-dy;  		//od gory
			else if(w!=19&&area.cegly[w+1][k]!=null) dx=-dx;  //od lewej
			else if(dx<0 && dy >0) dx = -dx;
			else if(dx>0 && dy<0) dy=-dy;
			else {
				Point rightSDown = new Point((int)c.getMaxX()-1,(int)c.getMaxY()-1);
				if(this.contains(rightSDown))
				{
					this.x = x - dx;
					this.y = y - dy;
				}
				dx=-dx; dy = -dy;
				}
			
			System.out.println("prawydolny");
		}
		else if(this.contains(leftDown))  //lewy dolny 
		{
			if (k!=0&&area.cegly[w][k-1]!=null) dy=-dy;  		//od gory
			else if(w!=19&&area.cegly[w+1][k]!=null) dx=-dx;  //od lewej
			else if(dx>0 && dy >0) dx=-dx;
			else if(dx<0&&dy<0) dy=-dy;
			else{
			Point leftSDown = new Point((int)c.getX()+1,(int)c.getMaxY()-1);
			if(this.contains(leftSDown))
			{
				this.x = x - dx;
				this.y = y - dy;
				area.repaint();
			}
			dx=-dx; dy = -dy;
			}
			System.out.println("lewydolny");
		}
		
		
		
		else if(top.intersects(pilka)) 
		{
			if(w!=0 && area.cegly[w-1][k]!=null) dx=-dx;
			else if( (dx >= 0 && dy > 0) || (dx <= 0 && dy > 0) ) dy = -dy;
			else {dx = -dx;} 
			System.out.println("gora");
			
		}
		
		
		else if(bottom.intersects(pilka))
		{
			if(w!=19 && area.cegly[w+1][k]!=null) dx = -dx;
			else if( (dx >= 0 && dy < 0) || (dx <= 0 && dy < 0) ) dy = -dy;
			else {dx = -dx; }
			System.out.println("dol");
		}
		else if(left.intersects(pilka)) {dx=-dx;System.out.println("lewa");}
		else if(right.intersects(pilka)) {dx=-dx; System.out.println("prawa");}
		else System.out.println(" blad!"); 
//		else if(this.intersects(c.getX()+0.5, c.getY(), CEGLA_SZEROKOSC-1, 1)) dy=-dy; 			//gora
//		else if(this.intersects(c.getX()+0.5, c.getMaxY()-0.5, CEGLA_SZEROKOSC-1, 1)) dy=-dy; 	//dol
//		else if(this.intersects(c.getX(), c.getY()+0.5, 1, CEGLA_WYSOKOSC-1)) dx=-dx;		//lewa
//		else if(this.intersects(c.getMaxX()-0.5, c.getY()+0.5, 1, CEGLA_WYSOKOSC-1)) dx=-dx;		//prawa
		
//		if(this.intersects(c.getX()+1, c.getY(), CEGLA_SZEROKOSC-2, 1)) { dy=-dy;} 			//gora
//		else if(this.intersects(c.getX()+1, c.getMaxY()-1, CEGLA_SZEROKOSC-2, 1)) { dy=-dy;} 	//dol
//		else if(this.intersects(c.getX(), c.getY()+1, 1, CEGLA_WYSOKOSC-2)) { dx=-dx;}		//lewa
//		else if(this.intersects(c.getMaxX()-1, c.getY()+1, 1, CEGLA_WYSOKOSC-2)) { dx=-dx;}		//prawa
		return true;
		}
		return false;
//		Line2D top = new Line2D.Float(leftUp, rightUp);
//		Line2D bottom = new Line2D.Float(leftDown, rightDown);
//		Line2D left = new Line2D.Float(leftUp, leftDown);
//		Line2D right = new Line2D.Float(rightUp, rightDown);
//		
//		Rectangle2D pilka = new Rectangle2D.Double(this.getX(),this.getY(),this.width,this.height);
//		
//		if(top.intersects(pilka)) dy=-dy;
//		else if(bottom.intersects(pilka)) dy=-dy;
//		else if(left.intersects(pilka)) dx=-dx;
//		else if(right.intersects(pilka)) dx=-dx;
	}
	
	
	public boolean sprawdzKolizje(Cegla cg)
	{
		// uderzenie od dolu
		if(this.getMaxX()>cg.getX() && this.getMinX()<cg.getMaxX()){   // pilka.prawo >= cegla.lewo ii pilka.lewo <= cegla.prawo
			if(this.getMinY() <= cg.getMaxY() && this.getMinY()>= cg.getMaxY()-PILKA_PROMIEN) // pilka.gora <= cegla.gora ii pilka. >= cegla.gora
			{
				dy = -dy;
			}
			if (this.getMaxY()>= cg.getY() && this.getMaxY()<=cg.getY()+PILKA_PROMIEN) // od gory
			{
				dy = - dy;
			}
		}	
		
		Point a = new Point(10,10);
		this.contains(a);
		
		
		//uderzenie z boku
		if(this.getMinY()<cg.getMaxY()&&this.getMaxY()>cg.getY()){
			if(this.getMaxX()>=cg.getX() && this.getMaxX()<=cg.getX()+PILKA_PROMIEN) //lewa
			{
				dx = -dx;
			}
			if (this.getMinX() <= cg.getMaxX() && this.getMinX()>=cg.getMaxX()-PILKA_PROMIEN) //prawa
			{
				dx = -dx;
			}
		}
		return false;
	}
	
	public void ui (Cegla c)
	{
		
            if (this.intersects(c)) {

                int ballLeft = (int)this.getMinX();
                int ballHeight = (int)this.getHeight();
                int ballWidth = (int)this.getWidth();
                int ballTop = (int)this.getMinY();

                Point pointRight =
                    new Point(ballLeft + ballWidth + 1, ballTop);
                Point pointLeft = new Point(ballLeft - 1, ballTop);
                Point pointTop = new Point(ballLeft, ballTop - 1);
                Point pointBottom =
                    new Point(ballLeft, ballTop + ballHeight + 1);

                //if (!bricks[i].isDestroyed()) {
                    if (c.contains(pointRight)) {
                        dx = -dx;
                    }

                    else if (c.contains(pointLeft)) {
                        dx=-dx;
                    }

                    if (c.contains(pointTop)) {
                        dy = -dy;
                    }

                    else if (c.contains(pointBottom)) {
                        dy = -dy;
                    }

                   // bricks[i].setDestroyed(true);
                }
	}
	
	
	public void resetujPilke()
	{
		if(!Gra.uwolniona)
		{
		this.dx = 0;
		this.dy = -3;
		this.zresetowana = true;
		}
	}
	
	public void pracuj() // ruch pi³eczki
	{
		
		
		x += dx;
		y += dy;
		
		
		
		
		// Œciany gry
		if( this.getX()<=0) //jeœli sprobuje wyjsc poza pole w osi x to sie odbije od sciany
		{
			this.x = 0;
			dx = -dx;
			
		}
		else if(this.getMaxX()>=area.getWidth()) 
		{
			this.x=GRA_SZEROKOSC-PILKA_PROMIEN;
			dx = -dx;
		}
		if ((this.getY()<=0)) 
		{
			this.y = 0;
			dy = -dy;
		}
		else if(this.getMaxY()>=area.getHeight()) 
		{
			area.pobierzGracza().usunZycie();
			zresetowana = false;
			Gra.uwolniona = false;
			area.ruszGdyReset();
	
			
			
		}
		
		
		
		
		// Paletka
		//Ellipse2D.Float.this.getx
		
		/*
		if (nowyY + PILKA_PROMIEN > area.paletka.getY() - 1)
			if (nowyY + srednica < area.paletka.getY()+srednica)
				if (nowyX + srednica >= area.paletka.getX())
					if (nowyX <= area.paletka.getX() + area.paletka.getWidth())
					{
						dy = -dy;
					}
		*/
		if (this.getMaxY()>=paletka.getMinY() && this.getMaxY() < paletka.getY()+srednica)
		{
			
			if((this.getX()+PILKA_PROMIEN)>=paletka.getMinX() && (this.getX())<=paletka.getMaxX() )
			{
				dy = -dy;
				
				float a = (float) (		( 	( this.getX() + PILKA_PROMIEN ) - ( paletka.getX() + (paletka.width/2))	) / (paletka.getWidth() / 5)	);
				
				
				
				dx = (int) a;
			}
			
		}
		
		
		
		
//		Cegla[][] cegielkii = area.pobierzCegly();
//		
//		if(this.intersects(cegielkii[10][10]))
//		{
//			System.out.print("rwrw");
//		}
//		if(this.intersects(cegielkii[0][0]))
//		{
//			System.out.print("rwrw");
//		}
	
		/*
		for (int i = 0; i < POLE_WIERSZE; i++ )
		{
			for (int j = 0; j < POLE_KOLUMNY; j++)
			{
				
				Rectangle intersection = this.getBounds().intersection(cegielkii[i][j].getBounds());
				
				if (intersection.width >= intersection.height)
				{
					dy = - dy;
				}
				
				if (intersection.height >= intersection.width)
				{
					dx = -dx;
				}
				
				
				
			}
		}
		*/
		
			
		//area.repaint();
		
	}
	
	
	public boolean collision(Cegla c)
	{
		if(this.getMaxX()+dx>=c.getX() && this.getX()+dx<=c.getMaxX() && this.getMaxY()+dy>=c.getY() && this.getY()<=c.getMaxY())
		{
			
			if(this.getMinY() <= c.getMaxY() && this.getMinY()>= c.getMaxY()-PILKA_PROMIEN) // pilka.gora <= cegla.gora ii pilka. >= cegla.gora
			{
				dy = -dy;
			}
			
			
			
			if(this.getMaxX() < c.getX() && this.getMaxX()+dx>=c.getX()) dx= -dx;
			
			if(this.getX()>c.getMaxX() && this.getX()+dx<=c.getMaxX()) dx = -dx;
			
			if(this.getMaxY()<c.getY() && this.getMaxY()+dy >= c.getY()) dy = -dy;
			
			return true;
		}
		return false;
	}
	
	public boolean kolizja(Cegla c)
	{
		Rectangle intersection = this.getBounds().intersection(c.getBounds());
		if(this.intersects(c)){ 
			
		if (intersection.width >= intersection.height)
		{
			dy = - dy; 
		}
		
		if (intersection.height >= intersection.width)
		{
			dx = -dx; 
		}
		//c.zniszczona = true;
		return true;
		}
		return false;
	}
	
	
}

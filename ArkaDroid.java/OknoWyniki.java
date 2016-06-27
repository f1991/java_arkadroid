package ArkaDroid;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;



public class OknoWyniki extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6732447232840050058L;
	Wyniki wyniki = new Wyniki();
	
	public OknoWyniki(){
		
	setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	Font hv = new Font("Helvetica",Font.BOLD,20);
	JTable tabela = new JTable();
	
	setTitle("Wyniki - 10 najwy¿szych wyników");
	setSize(400,375);
	setLayout(new BorderLayout());
    add(new JScrollPane(tabela), BorderLayout.CENTER);
	
	wyniki.odczytWynikow();
	
	DefaultTableModel model = new DefaultTableModel();
	model.setColumnIdentifiers(new String[] {"Nr" ,"Nick", "Wynik", "Czas" });
    model.setRowCount(wyniki.Gracze.size());
    
    int row = 0;  
    
    
    if(wyniki.Gracze.size()>0) {
    	for (Gracz g : wyniki.Gracze) {
        	model.setValueAt(row+1, row, 0);
            model.setValueAt(g.nick, row, 1);
            model.setValueAt(g.SCORE, row, 2);
            model.setValueAt(g.TIME, row, 3);
            row++;
        }
        tabela.setBackground(Color.GRAY);
        tabela.getTableHeader().setFont(hv);
        tabela.setRowHeight(30);
        tabela.setFont(hv);
        tabela.setModel(model);
    }
    else{
    	JLabel wynikiNull = new JLabel("Nie ma jeszcze ¿adnych wyników!" +"\n"+ "PrzejdŸ poziom!");
    	wynikiNull.setFont(new Font("Helvetica",Font.BOLD,15));
    	add(wynikiNull);
    }	
    setVisible(true);
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub	
	}
}





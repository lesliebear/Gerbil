package View;

import java.awt.EventQueue;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class SavedGames extends JFrame{

	String[] games = {"Game 1", "Game really long name", "Game 3"};
	
	public static SavedGames savedGames;
	
	private JPanel mainPanel = new JPanel();
	private JButton openGame = new JButton("Open Game");
	private JButton deleteGame = new JButton("Delete Game");
	private JButton cancel = new JButton("Cancel");
	
	static int rows;
	static int cols;
	static int hgap;
	static int vgap;
	
	
	public SavedGames(){

		savedGames = this; 
		
		createScreen(rows,cols,hgap,vgap);
	}
	
	protected void createScreen(int rows, int cols, int hgap, int vgap) {
		
		mainPanel.setLayout(new GridLayout());
		
	    for(int i=1;i<=rows;i++)
        {
            for(int j=1;j<=cols;j++)
            {
                JButton btn=new JButton(String.valueOf(i));
                mainPanel.add(btn);
            }
        }
	}
	
	public JComponent getMainComponent(){
		return mainPanel;
	}

	public static void showScreen() {	
		JFrame frame = new JFrame("Saved Games");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(savedGames.getMainComponent());
		frame.pack(); 
		frame.setMinimumSize(frame.getSize());
		frame.setVisible(true); // set it visible
	}
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {		
				try{
					showScreen();
				}catch(Exception e){
					//do something?..not sure if try catch needed
				}
			}
		});
	}
}

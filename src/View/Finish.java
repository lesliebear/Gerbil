package View;
 
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.ActionListener;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * @author Katiuska Nunez
 * This class creates a GUI for the Finish Screen.
 */
public class Finish extends Screen {

	private JFrame frame;
	private JPanel panel;
	private BufferedImage image;
	private JButton saveGame;
	private JButton newGame;
	private JButton loadGame;
	private JLabel reachedTheEnd;
	private JLabel fruitCounter;
	
	public int fruitCount;

	
	/**
	 * Constructor that creates all necessary GUI components.
	 * 
	 */
	public Finish() {
		createAndShowGUI(); 
	}
	
	
	protected void createAndShowGUI() { 
		frame = new JFrame("Finish");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		try {
			image = ImageIO.read(new File("pics/background.jpg"));
		} catch (Exception ex) {
			System.out.println("Couldn't load image");
		}
		panel = new JPanel() {
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
			}
		};
		
		createButtons();
		setPanel();
		frame.add(panel);
		
		frame.pack();
		frame.setResizable(false);
		frame.setVisible(false);
	}
	
	public void setPanel(){
		panel.setLayout(new GridBagLayout());
		panel.setOpaque(true);
		GridBagConstraints gc = new GridBagConstraints();
		
		Dimension size= panel.getPreferredSize();
		size.width =875;
		size.height = 600;
		panel.setPreferredSize(size);
		
		reachedTheEnd = new JLabel("You've reached Water!");
		reachedTheEnd.setFont(new Font(null, Font.BOLD, 48));
		reachedTheEnd.setForeground(Color.WHITE);
		
		fruitCounter = new JLabel("Fruits Eaten: "+this.fruitCount+"/"+"30"); // PUT NUMBERS HERE!
		fruitCounter.setFont(new Font(null, Font.BOLD, 32));
		fruitCounter.setForeground(Color.WHITE);
		
		//top, left, botton, right <- insets
		
		gc.ipady = 30;
		gc.ipadx = 20;
		
		gc.gridx = 0;
		gc.gridy = 0; 
		panel.add(reachedTheEnd, gc);
		

		gc.ipady = 20;
		gc.ipadx = 20;
		
		gc.insets = new Insets(30,0,0,0);
		
		gc.gridx = 0;
		gc.gridy = 1; 
		panel.add(fruitCounter, gc);
		
		gc.gridx = 0;
		gc.gridy = 2; 
		panel.add(saveGame, gc);
		
		gc.gridx = 0;
		gc.gridy = 3; 
		panel.add(newGame, gc);
		
		gc.gridx = 0;
		gc.gridy = 4; 
		panel.add(loadGame, gc);
	}
	
	public void createButtons(){
		saveGame= new JButton("Save Game"){
			public void paint(Graphics g) {
				saveGame.setFont(new Font("Serif", Font.PLAIN, 18)); 
				this.setContentAreaFilled(false);
				this.setBorderPainted(false);
				Graphics2D g2d = (Graphics2D)g;
				g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
				g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
				super.paint(g);
				g2d.setColor(Color.WHITE);
				g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 18, 18);
				g2d.setColor(Color.BLACK);
				g2d.setStroke(new BasicStroke(2));
				g2d.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 18, 18);
				FontRenderContext frc = new FontRenderContext(null, false, false);
				Rectangle2D r = getFont().getStringBounds(getText(), frc);
				float xMargin = (float)(getWidth() - r.getWidth()) / 2;
				float yMargin = (float)(getHeight() - getFont().getSize()) / 2;
				g2d.drawString(getText(), xMargin, (float)getFont().getSize() + yMargin);
			}
		};
		
		newGame= new JButton("New Game"){
			public void paint(Graphics g) {
				newGame.setFont(new Font("Serif", Font.PLAIN, 18)); 
				this.setContentAreaFilled(false);
				this.setBorderPainted(false);
				Graphics2D g2d = (Graphics2D)g;
				g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
				g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
				super.paint(g);
				g2d.setColor(Color.WHITE);
				g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 18, 18);
				g2d.setColor(Color.BLACK);
				g2d.setStroke(new BasicStroke(2));
				g2d.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 18, 18);
				FontRenderContext frc = new FontRenderContext(null, false, false);
				Rectangle2D r = getFont().getStringBounds(getText(), frc);
				float xMargin = (float)(getWidth() - r.getWidth()) / 2;
				float yMargin = (float)(getHeight() - getFont().getSize()) / 2;
				g2d.drawString(getText(), xMargin, (float)getFont().getSize() + yMargin);
			}
		};
		
		loadGame= new JButton("Load Game"){
			public void paint(Graphics g) {
				loadGame.setFont(new Font("Serif", Font.PLAIN, 18)); 
				this.setContentAreaFilled(false);
				this.setBorderPainted(false);
				Graphics2D g2d = (Graphics2D)g;
				g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
				g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
				super.paint(g);
				g2d.setColor(Color.WHITE);
				g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 18, 18);
				g2d.setColor(Color.BLACK);
				g2d.setStroke(new BasicStroke(2));
				g2d.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 18, 18);
				FontRenderContext frc = new FontRenderContext(null, false, false);
				Rectangle2D r = getFont().getStringBounds(getText(), frc);
				float xMargin = (float)(getWidth() - r.getWidth()) / 2;
				float yMargin = (float)(getHeight() - getFont().getSize()) / 2;
				g2d.drawString(getText(), xMargin, (float)getFont().getSize() + yMargin);
			}
		};
	}

	/**
	 * Creates the screen by putting the GUI components together.
	 * 
	 */
	protected void createScreen() {	
		
	}

	/**
	 * Shows the screen.
	 * 
	 */
	public void show() {
		frame.setVisible(true);
	}
	
	/**
	 * Hides the screen.
	 * 
	 */
	public void hide() {
		frame.setVisible(false);
	}
	

	@Override
	public void enable() {
		frame.setEnabled(true);
	}

	@Override
	public void disable() {
		frame.setEnabled(false);
	}
	

	public void addSaveGamesButtonListener(ActionListener listener) {
		saveGame.addActionListener(listener);
	}
	
	public void addNewGamesButtonListener(ActionListener listener) {
		// TODO Auto-generated method stub
		newGame.addActionListener(listener);
	}
	
	public void addLoadGamesButtonListener(ActionListener listener) {
		// TODO Auto-generated method stub
		loadGame.addActionListener(listener);
	}
	
	public void setFruitCount(int i){
		this.fruitCount=i;
	}
	
}
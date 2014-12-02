package View;
 
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * This class creates a GUI for the Function Screen.
 *
 */
public class UserFunction extends Screen{

	private JFrame frame;
	private JPanel panel;
	private JLabel[][] gridPanel;
	private JButton If, elseIf, Else, While, repeat, turnLeft, eat, move, ok, cancel;
	private JList list;
	private JLabel begin, end, funtion, value, condStatements, givenFunctions;
	private JTextField textField;
	private JComboBox userFunctions;
	private Image imageApple, imagePear;

	
	
	/**
	 * Constructor that creates all necessary GUI components.
	 * 
	 */
	public UserFunction() {
		 
		 try {
	            imageApple = ImageIO.read(new File("EditButton_Play.png")).getScaledInstance(67, 67, Image.SCALE_SMOOTH);
	            imagePear = ImageIO.read(new File("DeleteButton_Play.png")).getScaledInstance(67, 67, Image.SCALE_SMOOTH);;
	            
	            
	        } catch (Exception ex) {
	            ex.printStackTrace();
	        }
		frame = new JFrame();
		If = new JButton("Test");
		If.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
			
				
				 gridPanel[0][0].setIcon(new ImageIcon(imagePear));
			}
			
		});
		panel = new JPanel();
		gridPanel = new JLabel[15][15];
		createScreen();
		
	}

	/**
	 * Creates the screen by putting the GUI components together.
	 * 
	 */
	protected void createScreen() {	
		Dimension dimension = new Dimension(1005,1005);
		//panel.setSize(dimension);
		//panel.setMinimumSize(dimension);
		//panel.setPreferredSize(dimension);
		panel.setLayout(new GridLayout(15,16));
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		for(int i = 0; i < 15; i++) {
			for(int j = 0; j < 15; j++) {
				gridPanel[i][j] = new JLabel();
				gridPanel[i][j].setSize(new Dimension(67,67));
				gridPanel[i][j].setIcon(new ImageIcon(imageApple));
				panel.add(gridPanel[i][j]);
			}
		}
		panel.add(If);
		frame.add(panel);
		frame.setSize(dimension);
		frame.setMinimumSize(dimension);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	/**
	 * Shows the screen.
	 * 
	 */
	public void show() {
	
		
	}
	
	/**
	 * Hides the screen.
	 * 
	 */
	public void hide() {
	
	}

	@Override
	public void enable() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void disable() {
		// TODO Auto-generated method stub
		
	}
	
	class GridLabel extends JLabel {
		
		private BufferedImage image;

	    public GridLabel() {
	    	
	        try {
	            image = ImageIO.read(new File("DeleteButton_Play.png"));
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	    public void repaintComponent(BufferedImage image) {
	    	this.image = image;
	    	paintComponent(this.getGraphics());
	    }
	    @Override
	    protected void paintComponent(Graphics g) {
	        super.paintComponent(g);
	        g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
	    }
	}
}
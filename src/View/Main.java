package View;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * This class creates a GUI for the Gerbil Screen (Opening Screen).
 *
 */
public class Main extends Screen {

	public JButton play, instructions, exit;
	private JPanel panel;
	private BufferedImage image;
	private JFrame frame;

	/**
	 * Constructor that creates all necessary GUI components.
	 * 
	 */
	@SuppressWarnings("serial")
	public Main() {

		frame = new JFrame("Gerbil");
		play = new JButton("Play");
		instructions = new JButton("Instructions");
		exit = new JButton("Exit");
		try {
			image = ImageIO.read(new File("10619975_10204553653617548_7614187758233835955_o.jpg"));
		} catch (Exception ex) {
			System.out.println("coudln't load image");
		}
		panel = new JPanel() {
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
			}
		};
		createScreen();
	}

	/**
	 * Creates the screen by putting the GUI components together.
	 * 
	 */
	protected void createScreen() {

		play.setMinimumSize(new Dimension(50,50));
		play.setMaximumSize(new Dimension(100,100));
		play.setFont(new Font(null, Font.BOLD,20));
		instructions.setMinimumSize(new Dimension(50,50));
		instructions.setMaximumSize(new Dimension(100,100));
		instructions.setFont(new Font(null, Font.BOLD,20));
		exit.setMinimumSize(new Dimension(50,50));
		exit.setMaximumSize(new Dimension(100,100));
		exit.setFont(new Font(null, Font.BOLD,20));
		GroupLayout layout = new GroupLayout(panel);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		panel.setLayout(layout);
		Dimension dimension = new Dimension(1024, 668);
		layout.setHorizontalGroup(
			    layout.createSequentialGroup()
			        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
			            .addComponent(play)).addGap(50)
			        .addGroup(layout.createParallelGroup()
			            .addComponent(instructions)).addGap(50)
			        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
			            .addComponent(exit)));
	
		layout.setVerticalGroup(
			    layout.createParallelGroup()
			        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
			            .addComponent(play)
			            .addComponent(instructions)
			            .addComponent(exit)));
		frame.add(panel);
		frame.setSize(dimension);
		frame.setMinimumSize(dimension);
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
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

	public void addEventListeners(ActionListener listener) {
		
		play.addActionListener(listener);
		instructions.addActionListener(listener);
		exit.addActionListener(listener);
	}
}
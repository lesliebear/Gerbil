package View;
 
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.ActionListener;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.awt.Rectangle;

import javax.swing.JComponent;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.plaf.ScrollPaneUI;
import javax.swing.plaf.basic.BasicScrollBarUI;

/**
 * This class creates a GUI for the Instruction Screen (Help screen).
 *
 */
public class Instructions extends Screen{

	private JFrame frame;
	private JPanel panel;
	private JButton back;
	private BufferedImage image;
	private JTextArea gamePlayText, loadSaveText;
	private JScrollPane gpScrollPane, lsScrollPane;
	
	/**
	 * Constructor that creates all necessary GUI components.
	 * 
	 */
	@SuppressWarnings("serial")
	public Instructions() {
		 
		frame = new JFrame("Instructions");
		back = new JButton("Back") {
			public void paint(Graphics g) {
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
		gamePlayText = new JTextArea() {
			public void paint(Graphics g) {
				setOpaque(false);
				g.setColor(new Color(128, 128, 128, 110));
				Insets insets = getInsets();
				int x = insets.left;
				int y = insets.top;
				int width = getWidth() - (insets.left + insets.right);
				int height = getHeight() - (insets.top + insets.bottom);
				g.fillRect(x, y, width, height);
				super.paint(g);
			}
		};
		loadSaveText = new JTextArea() {
			public void paint(Graphics g) {
				setOpaque(false);
				g.setColor(new Color(128, 128, 128, 110));
				Insets insets = getInsets();
				int x = insets.left;
				int y = insets.top;
				int width = getWidth() - (insets.left + insets.right);
				int height = getHeight() - (insets.top + insets.bottom);
				g.fillRect(x, y, width, height);
				super.paint(g);
			}
		};
		gpScrollPane = new JScrollPane(gamePlayText);	
		lsScrollPane = new JScrollPane(loadSaveText);
		try {
			image = ImageIO.read(new File("instruct.jpg"));
		} catch (Exception ex) {
			System.out.println("Couldn't load image");
		}
		panel = new JPanel() {
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
			}
		};
		JScrollBar sb = new JScrollBar() {
			public void paintComponent(Graphics g) {
				setOpaque(false);
				g.setColor(new Color(128, 128, 128, 110));
				Insets insets = getInsets();
				int x = insets.left;
				int y = insets.top;
				int width = getWidth() - (insets.left + insets.right);
				int height = getHeight() - (insets.top + insets.bottom);
				g.fillRect(x, y, width, height);
				super.paintComponent(g);
			}
		};
		gpScrollPane.setVerticalScrollBar(sb);
		createScreen();
	}

	/**
	 * Creates the screen by putting the GUI components together.
	 * 
	 */
	protected void createScreen() {
		
		Dimension textAreaDimension = new Dimension(300,375);
		Dimension dimension = new Dimension(1024, 768);
		gamePlayText.setEditable(true);
		gamePlayText.setLineWrap(true);
		gpScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		gpScrollPane.setPreferredSize(textAreaDimension);
		gpScrollPane.setOpaque(false);
		gpScrollPane.getViewport().setOpaque(false);
	    gpScrollPane.setBorder(null);
	    loadSaveText.setEditable(true);
		loadSaveText.setLineWrap(true);
	    lsScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		lsScrollPane.setPreferredSize(textAreaDimension);
		lsScrollPane.setOpaque(false);
		lsScrollPane.getViewport().setOpaque(false);
	    lsScrollPane.setBorder(null);
		
		panel.add(gpScrollPane);
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
	
		frame.setVisible(true);;
	}
	
	/**
	 * Hides the screen.
	 * 
	 */
	public void hide() {
	
		frame.setVisible(false);
	}

	/**
	 * Enable the screen
	 */
	public void enable() {
		
		frame.setEnabled(true);
	}

	/**
	 * Disable to screen
	 */
	public void disable() {

		frame.setEnabled(false);
	}
	
	public void addBackEventHandler(ActionListener listener) {
		
		back.addActionListener(listener);
	}
}
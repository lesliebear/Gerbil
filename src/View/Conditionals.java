package View;
 
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;
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

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
 
/**
 * This screen creates a GUI for the Conditional Screen. 
 */
public class Conditionals {
	/**Type of conditional frame**/
	public static String type= "";
	
	/**LHS and RHS panels**/
	static JPanel leftPanel = new JPanel(); 
	static JPanel rightPanel = new JPanel();
	
	
	JLabel label = new JLabel();
	JComboBox<String> comboBox = new JComboBox<String>();
	/**Right side panel: labels, buttons, other**/
	JLabel conditionalsL = new JLabel("Conditional Statements"); 	
	JLabel givenFunctionsL = new JLabel("Given Functions"); 
	JLabel userDefinedL = new JLabel("User Defined Functions");
	
	JButton ifB;
	JButton elseB;
	JButton elseifB;
	JButton whileB;
	JButton repeatB;
	
	JButton moveForwardB;
	JButton turnLeftB;
	JButton eatB;
	
	static JFrame frame; 
	
	private int startLineNumber;
	private int endLineNumber;
	private DefaultListModel<String> instructions;
	
	JComboBox userDefinedFunctions; /*get from control*/ 
	
	/**Left side panel: labels, buttons, other**/
	JLabel ifL = new JLabel("If :");
	JLabel elseL= new JLabel("Else :");
	JLabel elseifL= new JLabel("Else if :");
	JLabel whileL = new JLabel("While :");
	JLabel bodyL = new JLabel("Body :");
	 
	JLabel beginL = new JLabel("Begin");
	JLabel endL = new JLabel("End");
	
	JButton okB;
	JButton cancelB; 
	
	JComboBox conditionalDropdown; /*get from control*/ 
	
	private static JList conditionalscodeList;
	private static JScrollPane scrollpane;
	
	/** Repeat stuff **/
	JLabel repeatL= new JLabel("Repeat"); 
	String[] nums = {"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15"};
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	JComboBox repeatNumTimes = new JComboBox(nums);
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {		
				createAndShowGUI();
			}
		});
	}
	
	/**
	 * Constructor that creates all necessary GUI components.
	 */
	public Conditionals(String typeIn) {
		type = typeIn; 
		createButtons();
		setRightComponents();
		setLeftComponents();
		createAndShowGUI();
	}
	
	void createButtons(){
		/**Right side panel**/
		moveForwardB= new JButton("Move Forward"){
			public void paint(Graphics g) {
				moveForwardB.setFont(new Font("Serif", Font.PLAIN, 18)); 
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
		
		turnLeftB= new JButton("Turn Left"){
			public void paint(Graphics g) {
				turnLeftB.setFont(new Font("Serif", Font.PLAIN, 18)); 
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
		
		eatB = new JButton("Eat"){
			public void paint(Graphics g) {
				eatB.setFont(new Font("Serif", Font.PLAIN, 18)); 
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

		/**Left side panel**/
		okB = new JButton("OK"){
			public void paint(Graphics g) {
				okB.setFont(new Font("Serif", Font.PLAIN, 18)); 
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
		
		cancelB = new JButton("Cancel"){
			public void paint(Graphics g) {
				cancelB.setFont(new Font("Serif", Font.PLAIN, 18)); 
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
	
	public void setRightComponents(){
		rightPanel.setLayout(new GridBagLayout());
		rightPanel.setOpaque(true);
		GridBagConstraints gc = new GridBagConstraints();
		
		Dimension size= rightPanel.getPreferredSize();
		size.width =200;
		size.height = 600;
		rightPanel.setPreferredSize(size);
		
		/*Setting the Font types*/
		conditionalsL.setFont(new Font("Serif", Font.BOLD, 18)); 
		givenFunctionsL.setFont(new Font("Serif", Font.BOLD, 18)); 
		userDefinedL.setFont(new Font("Serif", Font.BOLD, 18));
		
		String[] drop = {"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15"}; // EDIT : should come from somewhere else
		userDefinedFunctions = new JComboBox(drop);
		
		//top, left, botton, right <- insets
		gc.insets = new Insets(5,0,10,5);
	      
		//gc.anchor = GridBagConstraints.WEST;
		
		gc.gridx = 0;
		gc.gridy = 1;
		rightPanel.add(givenFunctionsL, gc);
		
		gc.gridx = 0;
		gc.gridy = 2;
		rightPanel.add(moveForwardB, gc);
		
		gc.gridx = 0;
		gc.gridy = 3;
		rightPanel.add(turnLeftB, gc);
		
		gc.gridx = 0;
		gc.gridy = 4;
		rightPanel.add(eatB, gc);
		
		gc.gridx = 0;
		gc.gridy = 5;
		rightPanel.add(userDefinedL, gc);
		
		gc.fill = GridBagConstraints.HORIZONTAL;
		gc.gridx = 0;
		gc.gridy = 6;
		rightPanel.add(userDefinedFunctions, gc); //placeholder */
	}
	
	public void setLeftComponents(){
		leftPanel.setLayout(new GridBagLayout());
		leftPanel.setOpaque(true);
		GridBagConstraints gc = new GridBagConstraints();
		
		Dimension size= leftPanel.getPreferredSize();
		size.width =675;
		size.height = 600;
		leftPanel.setPreferredSize(size);
		
		bodyL.setFont(new Font("Serif", Font.BOLD, 20));
		
		// EDIT: this should be called from somewhere else...
		String placeholder[] = {" " };
		
		conditionalscodeList = new JList(placeholder);
		scrollpane = new JScrollPane(conditionalscodeList);
		
		conditionalscodeList.setVisibleRowCount(20);
		
		String[] drop = {"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15"}; // EDIT : should come from somewhere else
		conditionalDropdown = new JComboBox(drop);
		
		gc.gridx = 0;
		gc.gridy = 0;
		
		if(type.equals("If")){
			ifL.setFont(new Font("Serif", Font.BOLD, 35));
			leftPanel.add(ifL, gc);
	
		}else if(type.equals("Else")){
			elseL.setFont(new Font("Serif", Font.BOLD, 35));
			leftPanel.add(elseL, gc);
			
		}else if(type.equals("Else if")){
			elseifL.setFont(new Font("Serif", Font.BOLD, 35));
			leftPanel.add(elseifL, gc);
			
		}else if(type.equals("While")){
			whileL.setFont(new Font("Serif", Font.BOLD, 35));
			leftPanel.add(whileL, gc);
			
		}else if(type.equals("Repeat")){
			repeatL.setFont(new Font("Serif", Font.BOLD, 35));
			leftPanel.add(repeatL, gc);
		}
		
		gc.insets = new Insets(10,0,0,20);

		gc.fill = GridBagConstraints.HORIZONTAL;
		gc.gridwidth = 2;
		gc.gridx = 1; 
		gc.gridy=0; 
		leftPanel.add(conditionalDropdown, gc);
		
		gc.insets = new Insets(0,0,0,0);
		
		gc.fill = GridBagConstraints.NONE;
		gc.weightx = .70;
		gc.gridwidth = 1;
		gc.gridx = 0; 
		gc.gridy=1; 
		leftPanel.add(bodyL, gc);
		
		gc.fill = GridBagConstraints.HORIZONTAL;
		
		gc.insets = new Insets(10,0,0,20);
		gc.gridwidth = 2;
		gc.gridx = 1; 
		gc.gridy=1; 
		leftPanel.add(scrollpane, gc);
		
		gc.fill = GridBagConstraints.NONE;
		
		//top, left, botton, right <- insets
		gc.insets = new Insets(60,0,0,0);
				
		gc.gridwidth = 1;
		gc.gridx = 1; 
		gc.gridy=2; 
		gc.ipady = 20;
		gc.ipadx = 20;
		leftPanel.add(okB, gc);
		
		gc.gridx = 2; 
		gc.gridy=2; 
		gc.ipady =20;
		gc.ipadx = 20;
		leftPanel.add(cancelB, gc);
	}

	/**
	 * Creates the screen by putting the GUI components together.
	 */
	protected static void createAndShowGUI() { 
		//conditionals = new Conditionals("Repeat");
		frame = new JFrame("apple");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.setLayout(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();
		Container c = frame.getContentPane();
	
		gc.gridx = 0;
		gc.gridy = 0; 
		
		c.add(leftPanel,gc);
		
		gc.gridx = 1; 
		gc.gridy = 0;
		
		c.add(rightPanel, gc);
		
		frame.pack();
		frame.setResizable(false);
		frame.setVisible(false);
	}

	public JComponent getLeftComponent() {
		return leftPanel;
	}
	
	public JComponent getRightComponent(){
		return rightPanel;
	}
	
	
	/**
	 * Shows the screen.
	 * 
	 */
	public void show() {
		frame.setVisible(true);
	}
	
	public void setType(String type) {
		
		if(type.equals("If")){
			label.setText("If");
			
		}
	}
	/**
	 * Hides the screen.
	 * 
	 */
	public void hide() {
		frame.setVisible(false);
	}

	public void enable() {
		frame.setEnabled(true);
	}

	public void disable() {
		frame.setEnabled(false);
	}
	
	public void resetLineNumber() {
		startLineNumber = 0;
	}

	public int getEndLineNumber() {
		endLineNumber = instructions.size() + 1;
		return endLineNumber;
	}
	
	public int getSelectedLineNumber() {
		
		return conditionalscodeList.getSelectedIndex();
	}
	public void updateInstructionsList(String[] instructions) {
		this.instructions.clear();
		for(int i = 0; i < instructions.length; i++) {
			this.instructions.addElement(instructions[i]);
		}
	}
	
	public void addCancelEventHandler(ActionListener listener) {
		cancelB.addActionListener(listener);
	}
	
	public void addOkEventHandler(ActionListener listener) {
		okB.addActionListener(listener);
	}
}
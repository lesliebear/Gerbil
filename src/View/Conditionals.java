package View;
 
import java.awt.Container;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * This screen creates a GUI for the Conditional Screen. 
 *
 */
public class Conditionals {
	public static Conditionals conditionalsScreen;
	
	JLabel conditionalsL = new JLabel("Conditionals"); 
	JLabel givenFunctionsL = new JLabel("Given Functions"); 
	JLabel userDefinedL = new JLabel("User Defined Functions");
	JLabel valueL= new JLabel("Value"); 
	
	JLabel repeatL= new JLabel("Repeat"); 
	String[] nums = {"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15"};
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	JComboBox repeatNumTimes = new JComboBox(nums);
	JComboBox userDefinedFunctions; 
	
	/*Side buttons*/
	JButton ifB= new JButton("If");
	JButton elseB= new JButton("Else");
	JButton elseifB= new JButton("Else if");
	JButton whileB = new JButton("While");
	JButton repeatB= new JButton("Repeat");
	
	JButton moveAheadB= new JButton("Move Ahead");
	JButton turnLeftB= new JButton("Turn Left");
	JButton eatB = new JButton("Eat");
	
	/*Bottom Buttons*/
	JButton okB= new JButton("OK"); 
	JButton cancelB = new JButton("Cancel"); 
	
	static String type;
	
	static JPanel leftPanel = new JPanel(); 
	static JPanel rightPanel = new JPanel();
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {		
				createAndShowGUI();
			}
		});
	}
	
	/**
	 * Constructor that creates all necessary GUI components.
	 */
	public Conditionals(String typeIn) {
		 this.type = typeIn; 
		 setRightComponents();
	}
	
	
	public void setRightComponents(){
		rightPanel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		c.gridx = 0;
		c.gridy = 1; 
		rightPanel.add(conditionalsL);
		
		c.gridx = 0;
		c.gridy = 2;
		rightPanel.add(ifB);
		
		c.gridx = 0;
		c.gridy = 3;
		rightPanel.add(elseB);
		
		c.gridx = 0;
		c.gridy = 4;
		rightPanel.add(elseifB);
		
		c.gridx = 0;
		c.gridy = 5;
		rightPanel.add(whileB);
		
		c.gridx = 0;
		c.gridy = 6;
		rightPanel.add(repeatB);
		
		c.gridx = 0;
		c.gridy = 7;
		rightPanel.add(givenFunctionsL);
		
		c.gridx = 0;
		c.gridy = 8;
		rightPanel.add(moveAheadB);
		
		c.gridx = 0;
		c.gridy = 9;
		rightPanel.add(turnLeftB);
		
		c.gridx = 0;
		c.gridy = 10;
		rightPanel.add(eatB);
		
		c.gridx = 0;
		c.gridy = 11;
		rightPanel.add(userDefinedL);
		
		c.gridx = 0;
		c.gridy = 12;
		rightPanel.add(repeatNumTimes); // this has to change to the user defined functions; placeholder
	}
	
	public void setLeftComponents(){
		
	}

	/**
	 * Creates the screen by putting the GUI components together.
	 * 
	 */
	protected static void createAndShowGUI() { 
		conditionalsScreen = new Conditionals(type);
		
		JFrame frame = new JFrame(type);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.setLayout(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();
		Container c = frame.getContentPane();
		
		gc.weightx = 1; //determines how much of the space will be occupied by it - helps with resizing screen issue
		gc.weighty=  .5;
		gc.fill = GridBagConstraints.BOTH;
		
		gc.gridx = 1;
		gc.gridy = 0; 
		
		c.add(rightPanel,gc);
		
		frame.pack();
		frame.setVisible(true);
	}

	public JComponent getLeftComponent() {
		return leftPanel;
	}
	
	public JComponent getRightComponent(){
		return rightPanel;
	}
}
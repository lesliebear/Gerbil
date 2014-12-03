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
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;

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

////eat(0),turnleft(1),move(2),if(3),elseif(4),else(5),while(6),repeat(7), function(8),
public class Conditionals {
	public static Conditionals conditionalsScreen;

	/**Type of conditional frame**/
	static String type;
	
	public static Conditionals conditionals;
	
	/**LHS and RHS panels**/
	static JPanel leftPanel = new JPanel(); 
	static JPanel rightPanel = new JPanel();
	
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
	
	JComboBox userDefinedFunctions; /*get from control*/ 
	
	/**Left side panel: labels, buttons, other**/
	JLabel ifL = new JLabel("If :");
	JLabel elseL= new JLabel("Else :");
	JLabel elseifL= new JLabel("Else if :");
	JLabel whileL = new JLabel("While :");
	JLabel bodyL = new JLabel("Body :");
	 
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
	}
	
	void createButtons(){
		/**Right side panel**/
		ifB= new JButton("If"){
			public void paint(Graphics g) {
				ifB.setFont(new Font("Serif", Font.PLAIN, 18)); 
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
		
		elseB= new JButton("Else"){
			public void paint(Graphics g) {
				elseB.setFont(new Font("Serif", Font.PLAIN, 18)); 
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
		elseifB= new JButton("Else if"){
			public void paint(Graphics g) {
				elseifB.setFont(new Font("Serif", Font.PLAIN, 18)); 
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
		whileB = new JButton("While"){
			public void paint(Graphics g) {
				whileB.setFont(new Font("Serif", Font.PLAIN, 18)); 
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
		repeatB= new JButton("Repeat"){
			public void paint(Graphics g) {
				repeatB.setFont(new Font("Serif", Font.PLAIN, 18)); 
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
		rightPanel.add(conditionalsL, gc);
		
		gc.gridx = 0;
		gc.gridy = 2;
		rightPanel.add(ifB, gc);
		
		gc.gridx = 0;
		gc.gridy = 3;
		rightPanel.add(elseB, gc);
		
		gc.gridx = 0;
		gc.gridy = 4;
		rightPanel.add(elseifB, gc);
		
		gc.gridx = 0;
		gc.gridy = 5;
		rightPanel.add(whileB, gc);
		
		gc.gridx = 0;
		gc.gridy = 6;
		rightPanel.add(repeatB, gc);
		
		gc.gridx = 0;
		gc.gridy = 7;
		rightPanel.add(givenFunctionsL, gc);
		
		gc.gridx = 0;
		gc.gridy = 8;
		rightPanel.add(moveForwardB, gc);
		
		gc.gridx = 0;
		gc.gridy = 9;
		rightPanel.add(turnLeftB, gc);
		
		gc.gridx = 0;
		gc.gridy = 10;
		rightPanel.add(eatB, gc);
		
		gc.gridx = 0;
		gc.gridy = 11;
		rightPanel.add(userDefinedL, gc);
		
		gc.fill = GridBagConstraints.HORIZONTAL;
		gc.gridx = 0;
		gc.gridy = 12;
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
		String placeholder[] = { "Begin",
		        "End",  "INSERT NEW" };
		
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
		
			gc.gridx = 0; 
			gc.gridy=1;  
			leftPanel.add(repeatNumTimes, gc);
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
		conditionals = new Conditionals("Repeat");
		JFrame frame = new JFrame("If");
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
		frame.setVisible(true);
	}

	public JComponent getLeftComponent() {
		return leftPanel;
	}
	
	public JComponent getRightComponent(){
		return rightPanel;
	}
	
	/**
	protected class FunctionListener implements ActionListener{ 
		public void actionPerformed(ActionEvent e) {
			
			int result=-100;
			int result2=-200;
			
			selectedRow = AlbumInfoPicturePanel.table.getSelectedRow();
			selectedCol = AlbumInfoPicturePanel.table.getSelectedColumn();

			if(e.getSource() == addPhoto && !OK.isVisible() && !cancel.isVisible()){
				AlbumInfoPicturePanel.table.setCellSelectionEnabled(false);
				isolateAddPhoto();
			}
			else if( e.getSource() == addTag  && !OK.isVisible() && !cancel.isVisible() ){
				AlbumInfoPicturePanel.table.setCellSelectionEnabled(false);
				isolateAddTag();
			}
			else if(e.getSource() == deleteTag  && !OK.isVisible() && !cancel.isVisible()) {
				AlbumInfoPicturePanel.table.setCellSelectionEnabled(false);
				isolateDeleteTag();
			}
			else if(e.getSource() == OK){ 
				OK.setVisible(false);
				cancel.setVisible(false);

				if(Error.isVisible()){
					Error.setVisible(false);
					cancel.setVisible(true);
					OK.setVisible(true);
				}else if(addPhoto.isEnabled()){
					
					if(textField2.getText().isEmpty()){
						cancel.setVisible(false);
						Error.setText("Invalid input, please try again.");
						Error.setVisible(true);

						OK.setVisible(true);

					}else if(!(new File(textField1.getText()).exists())){
						cancel.setVisible(false);
						Error.setText("Invalid input, please try again.");
						Error.setVisible(true);

						OK.setVisible(true);
					}else{
						String caption = textField2.getText();
						String filename = textField1.getText();

						result = LogIn.control2.addPhotos(filename, caption, currAlbum.getAlbumName());
						
						if(result == -1 || result == -2 || result == -3 || result == -4 || result == -5){
							cancel.setVisible(false);
							
							switch(result){
							case -1:Error.setText("Empty album entered, please try again.");break;
							case -2:Error.setText("Current User does not exist, please try relogin in.");break;
							case -3: Error.setText("Invalid photo name, please try again.");break;
							case -4:Error.setText("Invalid Album name, please try again. ");break;
							case -5:Error.setText("Photo "+filename+" already exists in album "+currAlbum.getAlbumName());break;
							default: Error.setText("Invalid inputer, please try again.");
							}

							Error.setVisible(true);

							OK.setVisible(true);
						}else{
							AlbumInfoPicturePanel.model.fireTableDataChanged();
							resetAllStates();
							AlbumInfoPicturePanel.loadTable(AlbumInfoPicturePanel.model, currAlbum);
						}
					}

				}else if(recaption.isEnabled() ){ //bugs with empty cell - editable textfield
					if(pCaptionText.getText().isEmpty()){
						cancel.setVisible(false);
						Error.setText("Invalid input, please try again.");
						Error.setVisible(true);

						OK.setVisible(true);

					}else{
						photo temp=null;
						if(AlbumInfoPicturePanel.selectedImage == null){
							cancel.setVisible(false);
							Error.setText("Invalid input, please try again.");
							Error.setVisible(true);
							pCaptionText.setText("");


							OK.setVisible(true);
						}else{
							temp = currAlbum.getPictureObject(AlbumInfoPicturePanel.selectedImage);
						}

						if(temp == null){ //taking care of empty spot and null : above + here
							cancel.setVisible(false);
							Error.setText("Invalid input, please try again.");
							Error.setVisible(true);

							OK.setVisible(true);
						}else{

							temp.setCaption(pCaptionText.getText());
							resetAllStates();
						}
					}

				}else if(movePhoto.isEnabled()){

					photo temp=null;
					if(AlbumInfoPicturePanel.selectedImage == null){
						cancel.setVisible(false);
						Error.setText("No image selected, please try again.");
						Error.setVisible(true);

						OK.setVisible(true);
					}else{
						temp = currAlbum.getPictureObject(AlbumInfoPicturePanel.selectedImage);
					}

					if(temp == null){ //taking care of empty spot and null : above + here
						cancel.setVisible(false);
						Error.setText("Invalid inputer, please try again.");
						Error.setVisible(true);

						OK.setVisible(true);
					}else{

						LogIn.control2.movePhoto(AlbumInfoPicturePanel.selectedImage, currAlbum.getAlbumName(), selectedAlbumName);
						AlbumInfoPicturePanel.model.fireTableDataChanged();
						resetAllStates();
						AlbumInfoPicturePanel.loadTable(AlbumInfoPicturePanel.model, currAlbum);
						resetTopTextFields();

					}

				}else if(deleteTag.isEnabled()){
					
					//deleteTag
					//-7 "Tag does not exist for "+filename+" "+type+":"+value
					
					if(textField1.getText().isEmpty() && textField2.getText().isEmpty()){
						cancel.setVisible(false);
						Error.setText("Invalid input, please try again.");
						Error.setVisible(true);

						OK.setVisible(true);

					}

					photo temp=null;
					if(AlbumInfoPicturePanel.selectedImage == null){
						cancel.setVisible(false);
						Error.setText("Invalid input, please try again.");
						Error.setVisible(true);
						cleanTextFields();

						OK.setVisible(true);
					}else{
						temp = currAlbum.getPictureObject(AlbumInfoPicturePanel.selectedImage);
					}

					if(temp == null){ //taking care of empty spot and null : above + here
						cancel.setVisible(false);
						Error.setText("Invalid input, please try again.");
						Error.setVisible(true);

						OK.setVisible(true);
					}else{
						if(!textField1.getText().isEmpty()){ // location
							result = LogIn.control2.deleteTag(temp.getFilename(), "location", textField1.getText());
						}
						if(!textField2.getText().isEmpty()){ // person
							result2 = LogIn.control2.deleteTag(temp.getFilename(), "person", textField2.getText());
						}

						if(result == -7 && result2 ==-7){
							cancel.setVisible(false);
							Error.setText("Tags do not exist, could not delete.");
							Error.setVisible(true);

							OK.setVisible(true);
						}else if(result==-7 || result2 == -7){
							cancel.setVisible(false);
							
							if(result==7){
								Error.setText("Couldn't delete location tag:"+textField1.getText());
							}else{
								Error.setText("Couldn't delete person tag:"+textField2.getText());
							}
							Error.setVisible(true);

							OK.setVisible(true);
						}else{
							AlbumInfoPicturePanel.model.fireTableDataChanged();
							resetAllStates();
							AlbumInfoPicturePanel.loadTable(AlbumInfoPicturePanel.model, currAlbum);
							resetTopTextFields();
						}
					}

				}else if(addTag.isEnabled()){
					if(textField1.getText().isEmpty() && textField2.getText().isEmpty()){
						cancel.setVisible(false);
						Error.setText("Invalid input, please try again.");
						Error.setVisible(true);

						OK.setVisible(true);

					}

					photo temp=null;
					if(AlbumInfoPicturePanel.selectedImage == null){
						cancel.setVisible(false);
						Error.setText("Invalid input, please try again.");
						Error.setVisible(true);
						cleanTextFields();


						OK.setVisible(true);
					}else{
						temp = currAlbum.getPictureObject(AlbumInfoPicturePanel.selectedImage);
					}

					if(temp == null){ //taking care of empty spot and null : above + here
						cancel.setVisible(false);
						Error.setText("Invalid input, please try again.");
						Error.setVisible(true);

						OK.setVisible(true);
					}else{
						if(!textField1.getText().isEmpty()){ // location
							LogIn.control2.addTag(temp.getFilename(), "location", textField1.getText());
						}
						if(!textField2.getText().isEmpty()){ // person
							LogIn.control2.addTag(temp.getFilename(), "person", textField2.getText());
						}

						AlbumInfoPicturePanel.model.fireTableDataChanged();
						resetAllStates();
						AlbumInfoPicturePanel.loadTable(AlbumInfoPicturePanel.model, currAlbum);
					}
				}

			}
			else if(e.getSource() == cancel){ 
				resetAllStates();
			}
			else if(e.getSource() == recaption && !OK.isVisible() && !cancel.isVisible()){ //should also delete from ArrayList and decrease count
				AlbumInfoPicturePanel.table.setCellSelectionEnabled(false);
				isolateRecaption();
			}
			else if(e.getSource() == movePhoto && !OK.isVisible() && !cancel.isVisible()){
				AlbumInfoPicturePanel.table.setCellSelectionEnabled(false);
				isolateMovePhoto();
			}
			else if(e.getSource() == deletePhoto){
				if(AlbumInfoPicturePanel.selectedImage != null){
					currAlbum.deletePhoto(AlbumInfoPicturePanel.selectedImage);
					AlbumInfoPicturePanel.model.fireTableDataChanged();
					AlbumInfoPicturePanel.loadTable(AlbumInfoPicturePanel.model, currAlbum);
					resetAllStates();
					resetTopTextFields();
				}
			}
			else if(e.getSource() == slideShow){
				
				openAlbum.setVisible(false);
				
				SlideShow s = new SlideShow(currAlbum, AlbumInfo.albumInfo);
				s.setVisible(true);
			}
			else if(e.getSource() == displayPhoto){
				
				if(AlbumInfoPicturePanel.selectedImage != null){
					
					openAlbum.setVisible(false);
					
					DisplayPhoto dp = new DisplayPhoto(currAlbum.getPictureObject(AlbumInfoPicturePanel.selectedImage));
					dp.setVisible(true);
				}
			}
			else if(e.getSource() == backToUserScreen){
				userScreen.setVisible(true); //makes suer screen visible
				try {
					LogIn.backend2.saveUsers(LogIn.backend2.getUserList());
				} catch (IOException e1) {
					
				}
				
				openAlbum.dispose(); //gets rid of the current frame = destorys
			}
			else if(e.getSource() == logout){
				logIn.setVisible(true);
				
				try {
					LogIn.backend2.saveUsers(LogIn.backend2.getUserList());
				} catch (IOException e1) {

				}
				
				openAlbum.dispose();
			}
		}
	}
	
	
	
	
	
	
	
	**/
	
	
	
	
	
}
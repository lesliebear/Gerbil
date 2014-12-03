package View;
 
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.ActionListener;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import Control.ActionListenersControl;
/**
 * This class creates a GUI for the Play screen.
 */
public class Play extends Screen{
	
	public static ArrayList<String> instructions;
	static DefaultListModel model;
	
	private static JFrame frame;
	
	/**Panels**/
	private static JPanel upperPanel = new JPanel();
	private static JPanel leftPanel = new JPanel(); 
	private static JPanel gridPanel = new JPanel();
	private static JPanel lowerPanel = new JPanel();
	
	/**Upper Panel Components**/
	private static JButton menuB;
	private static JButton playB;
	private static JButton stopB; 
	private static JButton insertB; 
	private static JButton editB; 
	private static JButton deleteB;
	private static JButton clearallB; 
	private static JButton saveB;
	
	/**Left Panel Components**/
	public static JList playcodeList;
	private static JScrollPane scrollpane;
	
	/**Grid Panel components**/
	private static JLabel[][] gridBoxes;
	
	/**Lower Panel Components**/
	private static JButton createFunctionB;
	private static JButton deleteFunctionB;
	public static JComboBox conditionalsDD;
	public static JComboBox givenFunctionsDD;
	public static JComboBox userFunctionsDD;
	public static JComboBox checksDD;
	public static JComboBox numsDD;
	
	private static JLabel conditionalStatementsL;
	private static JLabel givenFunctionsL;
	private static JLabel userDefinedFunctionsL;
	private static JLabel checksL;
	private static JLabel numsL;
	
	/** Gerbil grid representation */
	private static char[][] grid;
	
	/** Image icons of all the necessary pictures */
	private static ImageIcon imageApple, imagePear, imageGrass, imagePumpkin, imageWall, imageGerbilEast, imageGerbilWest, imageGerbilNorth, imageGerbilSouth, imageWater;
	
	/**
	 * Constructor that creates all necessary GUI components.
	 */
	public Play() {
		instructions = new ArrayList<String>();
		instructions.add(0, "Begin");
		instructions.add(1, "End");
		instructions.add(2, " ");
		
		try {
			imageApple = new ImageIcon(ImageIO.read(new File("apple icon.png")).getScaledInstance(42, 34, Image.SCALE_SMOOTH));
	        imagePear = new ImageIcon(ImageIO.read(new File("pear icon.png")).getScaledInstance(42, 34, Image.SCALE_SMOOTH));;
	        imageGrass = new ImageIcon(ImageIO.read(new File("grass icon.png")).getScaledInstance(42, 34, Image.SCALE_SMOOTH));;
	        imagePumpkin = new ImageIcon(ImageIO.read(new File("pumpkin.png")).getScaledInstance(42, 34, Image.SCALE_SMOOTH));;
	        imageWall = new ImageIcon(ImageIO.read(new File("wall icon.png")).getScaledInstance(42, 34, Image.SCALE_SMOOTH));;
	        imageGerbilEast = new ImageIcon(ImageIO.read(new File("gerbilEast.png")).getScaledInstance(42, 34, Image.SCALE_SMOOTH));;
	        imageGerbilWest = new ImageIcon(ImageIO.read(new File("gerbilWest.png")).getScaledInstance(42, 34, Image.SCALE_SMOOTH));;
	        imageGerbilNorth = new ImageIcon(ImageIO.read(new File("gerbilNorth.png")).getScaledInstance(42, 34, Image.SCALE_SMOOTH));;
	        imageGerbilSouth = new ImageIcon(ImageIO.read(new File("gerbilSouth.png")).getScaledInstance(42, 34, Image.SCALE_SMOOTH));;
	        imageWater = new ImageIcon(ImageIO.read(new File("waterBottle.jpg")).getScaledInstance(42, 34, Image.SCALE_SMOOTH));;
	            
	    } catch (Exception ex) {
	            ex.printStackTrace();
	    }
		createAndShowGUI();
	}
	
	public static void setNewGrid(char[][] newGrid) {
		grid = newGrid;
	}

	/**
	 * Creates the screen by putting the GUI components together.
	 */
	protected static void createAndShowGUI() { 
		frame = new JFrame("Play"); // EDIT: User defined game name?
	
		setUpperComponents();
		setLeftComponents();
		setGridComponents();
		setLowerComponents();
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();
		Container c = frame.getContentPane();
	
		gc.gridwidth = 2;
		gc.gridx = 0;
		gc.gridy = 0; 
		c.add(upperPanel,gc);
		
		gc.gridwidth = 1; 
		gc.gridx = 0;
		gc.gridy=1;
		c.add(leftPanel,gc);
		
		gc.gridwidth =1;
		gc.gridx = 1;
		gc.gridy=1;
		c.add(gridPanel,gc);
		
		gc.gridwidth = 2; 
		gc.gridx = 0;
		gc.gridy=2;
		c.add(lowerPanel,gc);
		
		frame.setResizable(false);
		//frame.setMinimumSize(new Dimension(875, 150));
		frame.pack();
		frame.setVisible(false);
		frame.setLocationRelativeTo(null);
	}
	
	public static void setUpperComponents(){
		upperPanel.setLayout(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();
		
		Dimension size= upperPanel.getPreferredSize();
		size.width = 875;
		size.height = 35;
		upperPanel.setPreferredSize(size);
		
		ImageIcon menuIcon = new ImageIcon("MenuButton_Play.png");
		Image  menuImg = menuIcon.getImage() ;   //width, height, type scaling
		Image  menuNewimg =  menuImg.getScaledInstance( 105, 25,  java.awt.Image.SCALE_SMOOTH ) ; 
		
		menuB = new JButton(new ImageIcon( menuNewimg));
		menuB.setBorderPainted(false);
		menuB.setFocusPainted(false);
		menuB.setContentAreaFilled(true);
		menuB.setBackground(Color.BLACK);
		menuB.setOpaque(true);
		/********/
		ImageIcon playIcon = new ImageIcon("PlayButton_Play.png");
		Image  playImg = playIcon.getImage() ;   //width, height, type scaling
		Image  playNewimg =  playImg.getScaledInstance( 105, 25,  java.awt.Image.SCALE_SMOOTH ) ; 
		
		playB = new JButton(new ImageIcon( playNewimg));
		playB.setBorderPainted(false);
		playB.setFocusPainted(false);
		playB.setContentAreaFilled(true);
		playB.setBackground(Color.BLACK);
		playB.setOpaque(true);
		/********/
		ImageIcon stopIcon = new ImageIcon("StopButton_Play.png");
		Image  stopImg = stopIcon.getImage() ;   //width, height, type scaling
		Image  stopNewimg =  stopImg.getScaledInstance(110, 25,  java.awt.Image.SCALE_SMOOTH ) ; 
		
		stopB = new JButton(new ImageIcon( stopNewimg));
		stopB.setBorderPainted(false);
		stopB.setFocusPainted(false);
		stopB.setContentAreaFilled(true);
		stopB.setBackground(Color.BLACK);
		stopB.setOpaque(true);
		/********/
		ImageIcon insertIcon = new ImageIcon("InsertButton_Play.png");
		Image  insertImg = insertIcon.getImage() ;   //width, height, type scaling
		Image  insertNewimg =  insertImg.getScaledInstance( 110, 25,  java.awt.Image.SCALE_SMOOTH ) ; 
		
		insertB = new JButton(new ImageIcon(insertNewimg));
		insertB.setBorderPainted(false);
		insertB.setFocusPainted(false);
		insertB.setContentAreaFilled(true);
		insertB.setBackground(Color.BLACK);
		insertB.setOpaque(true);
		/********/
		ImageIcon editIcon = new ImageIcon("EditButton_Play.png");
		Image  editImg = editIcon.getImage() ;   //width, height, type scaling
		Image  editNewimg =  editImg.getScaledInstance( 110, 25,  java.awt.Image.SCALE_SMOOTH ) ; 
		
		editB = new JButton(new ImageIcon( editNewimg));
		editB.setBorderPainted(false);
		editB.setFocusPainted(false);
		editB.setContentAreaFilled(true);
		editB.setBackground(Color.BLACK);
		editB.setOpaque(true);
		/********/
		ImageIcon deleteIcon = new ImageIcon("DeleteButton_Play.png");
		Image  deleteImg = deleteIcon.getImage() ;   //width, height, type scaling
		Image  deleteNewimg =  deleteImg.getScaledInstance( 110, 25,  java.awt.Image.SCALE_SMOOTH ) ; 
		
		deleteB = new JButton(new ImageIcon( deleteNewimg));
		deleteB.setBorderPainted(false);
		deleteB.setFocusPainted(false);
		deleteB.setContentAreaFilled(true);
		deleteB.setBackground(Color.BLACK);
		deleteB.setOpaque(true);
		/********/
		ImageIcon clearallIcon = new ImageIcon("ClearAllButton_Play.png");
		Image  clearallImg = clearallIcon.getImage() ;   //width, height, type scaling
		Image  clearallNewimg =  clearallImg.getScaledInstance( 110, 25,  java.awt.Image.SCALE_SMOOTH ) ; 
		
		clearallB = new JButton(new ImageIcon( clearallNewimg));
		clearallB.setBorderPainted(false);
		clearallB.setFocusPainted(false);
		clearallB.setContentAreaFilled(true);
		clearallB.setBackground(Color.BLACK);
		clearallB.setOpaque(true);
		/********/
		ImageIcon saveIcon = new ImageIcon("SaveButton_Play.png");
		Image  saveImg = saveIcon.getImage() ;   //width, height, type scaling
		Image  saveNewimg =  saveImg.getScaledInstance(110, 25,  java.awt.Image.SCALE_SMOOTH ) ; 
		
		saveB = new JButton(new ImageIcon(saveNewimg));
		saveB.setBorderPainted(false);
		saveB.setFocusPainted(false);
		saveB.setContentAreaFilled(true);
		saveB.setBackground(Color.BLACK);
		saveB.setOpaque(true);
		
		gc.weightx = 1; //determines how much of the space will be occupied by it
		gc.fill = GridBagConstraints.HORIZONTAL;

		gc.gridx=0;
		gc.gridy=0; 
		upperPanel.add(menuB, gc);
		
		gc.gridx=1;
		gc.gridy=0; 
		upperPanel.add(playB, gc);
		
		gc.gridx=2;
		gc.gridy=0; 
		upperPanel.add(stopB, gc);
		
		gc.gridx=3;
		gc.gridy=0; 
		upperPanel.add(insertB, gc);
		
		gc.gridx=4;
		gc.gridy=0; 
		upperPanel.add(editB, gc);
		
		gc.gridx=5;
		gc.gridy=0; 
		upperPanel.add(deleteB, gc);
		
		gc.gridx=6;
		gc.gridy=0; 
		upperPanel.add(clearallB, gc);
		
		gc.gridx=7;
		gc.gridy=0;
		upperPanel.add(saveB, gc);
	}
	
	/*
	 * Kat: 
	 * 
	 * Testing: Vertical Scrollbar
	 * Input: list longer than screen (Vertical)
	 * Expected Output: Vertical Scrollbar should pop up
	 * Output: Vertical Scrollbar pops up
	 * 
	 * Testing: Horizontal Scrollbar
	 * Input: Word longer than screen (Horizontal)
	 * Expected Output: Horizontal Scrollbar should pop up
	 * Output: Horizontal Scrollbar pops up
	 * 
	 * */
	public static void setLeftComponents(){
		Dimension size= leftPanel.getPreferredSize();
		size.width =250;
		size.height = 500;
		leftPanel.setPreferredSize(size);
		
		// EDIT: this should be called from somewhere else...
		String placeholder[] = { "Begin","End"," "};
		
		model=new DefaultListModel();
		
		for(int i=0; i<instructions.size();i++){
			model.addElement(instructions.get(i));
		}
		
		playcodeList = new JList(model);
		playcodeList.setSelectedIndex(playcodeList.getModel().getSize()-2);
	
		if(beforeIsConditional()){
			disableAllPlayDDButChecks();
		}
		
		playcodeList.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				// TODO Auto-generated method stub

			}
		});

		scrollpane = new JScrollPane(playcodeList);
		
		leftPanel.setLayout(new BorderLayout());
		leftPanel.add(scrollpane, BorderLayout.CENTER);
	}
	
	public static void setGridComponents(){
		gridPanel.setLayout(new GridLayout(grid.length, grid[0].length));	
		Dimension size= gridPanel.getPreferredSize();
		size.width =630;
		size.height = 510;
		gridPanel.setPreferredSize(size);
		gridBoxes = new JLabel[grid.length][grid[0].length];
		for(int i = 0; i < grid.length; i++) {
			for(int j = 0; j < grid[0].length; j++) {
				gridBoxes[i][j] = new JLabel();
				switch(grid[i][j]) {
					case'0':gridBoxes[i][j].setIcon(imageGrass);
							break;
					case'w':gridBoxes[i][j].setIcon(imageWall);
							break;
					case'a':gridBoxes[i][j].setIcon(imageApple);
							break;
					case'k':gridBoxes[i][j].setIcon(imagePumpkin);
							break;
					case'p':gridBoxes[i][j].setIcon(imagePear);
							break;
					case'g':gridBoxes[i][j].setIcon(imageGerbilNorth);
							break;
					case't':gridBoxes[i][j].setIcon(imageWater);
				}
				gridPanel.add(gridBoxes[i][j]);
			}
		}
	
	}
	
	public static void setLowerComponents(){
		lowerPanel.setLayout(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();
		
		Dimension size= lowerPanel.getPreferredSize();
		size.width =875;
		size.height =  125; 
		lowerPanel.setPreferredSize(size);
		
		createFunctionB = new JButton(){
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
		
		deleteFunctionB = new JButton(){
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
		
		
		createFunctionB.setText("Create Function");
		deleteFunctionB.setText("Delete Function");
		
		// EDIT: this should come from something else...
		String[] conditionals = { "If", "Else", "Else if", "While", "Repeat" };
		String[] functions = {"Move Forward", "Turn Left", "Eat"};
		String [] checks = {"There'sWall?", "There'sNoWall", "There'sFood","There'sNoFood"};
		String[] nums = {"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15"};
		
		
		conditionalsDD = new JComboBox(conditionals);
		givenFunctionsDD = new JComboBox(functions);
		
		//userFunctionsDD = new JComboBox();
		//userFunctionsDD.setModel(new DefaultComboBoxModel(arrayList.toArray()));
		userFunctionsDD = new JComboBox(ActionListenersControl.controller.userDefined.toArray());
		checksDD = new JComboBox(checks);
		numsDD = new JComboBox(nums);
		
		conditionalStatementsL = new JLabel("<html>Conditional </br> Statements<html>");
		givenFunctionsL = new JLabel("Given Functions");
		userDefinedFunctionsL = new JLabel("<html>User Functions</html>");
		checksL = new JLabel("Checks");
		numsL = new JLabel("Numbers");
		
		gc.fill = GridBagConstraints.BOTH;
		gc.weightx = .70;
		gc.weighty = 1;
		gc.gridheight= 2;
		//top, left, botton, right <- insets
		gc.insets = new Insets(0,30,10,80);

		gc.fill = GridBagConstraints.HORIZONTAL;
		gc.gridx=0;
		gc.gridy=0;
		lowerPanel.add(createFunctionB, gc);
		
		gc.fill = GridBagConstraints.HORIZONTAL;
		gc.gridx=0;
		gc.gridy=1;
		lowerPanel.add(deleteFunctionB, gc);

		gc.insets = new Insets(20,30,0,0);
		
		gc.gridheight= 1;
		/*Labels*/
		gc.gridx=1;
		gc.gridy=0;
		lowerPanel.add(conditionalStatementsL, gc);
		
		gc.gridx=2;
		gc.gridy=0;
		lowerPanel.add(givenFunctionsL, gc);
		
		gc.gridx=3;
		gc.gridy=0;
		lowerPanel.add(userDefinedFunctionsL, gc);
		
		gc.gridx=4;
		gc.gridy=0;
		lowerPanel.add(checksL, gc);
		
		gc.gridx=5;
		gc.gridy=0;
		lowerPanel.add(numsL, gc);
		
		//gc.insets = new Insets(0,30,0,80);
		/*Dropdowns*/
		gc.gridx=1;
		gc.gridy=1;
		lowerPanel.add(conditionalsDD, gc);
		
		gc.gridx=2;
		gc.gridy=1;
		lowerPanel.add(givenFunctionsDD, gc);
		
		gc.gridx=3;
		gc.gridy=1;
		lowerPanel.add(userFunctionsDD, gc);
		
		gc.gridx=4;
		gc.gridy=1;
		lowerPanel.add(checksDD, gc);
		
		gc.gridx=5;
		gc.gridy=1;
		lowerPanel.add(numsDD, gc);
	
	}
	
	/**
	 * Creates the screen by putting the GUI components together.
	 * 
	 */
	protected void createScreen() {	
		
	}	
	
	public void refreshUserFunctions(){
		userFunctionsDD.removeAllItems();

		for(String s: ActionListenersControl.controller.userDefined){
			userFunctionsDD.addItem(s);
		}
	}
	
	public static void refreshCodeList(){
		model.clear();
		
		for(int i=0; i<instructions.size();i++){
			model.addElement(instructions.get(i));
		}
		
		playcodeList.setSelectedIndex(playcodeList.getModel().getSize()-1);
		
		if(beforeIsConditional()){
			disableAllPlayDDButChecks();
		}
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
	
	/**Button Listeners**/
	
	public void addMenuEventHandler(ActionListener listener) {
		menuB.addActionListener(listener);
	}

	public void addPlayEventHandler(ActionListener listener) {
		playB.addActionListener(listener);
	}

	public void addStopEventHandler(ActionListener listener) {
		stopB.addActionListener(listener);
	}

	public void addInsertEventHandler(ActionListener listener) {
		insertB.addActionListener(listener);
	}

	public void addEditEventHandler(ActionListener listener) {
		editB.addActionListener(listener);
	}

	public void addDeleteEventHandler(ActionListener listener) {
		deleteB.addActionListener(listener);
	}

	public void addClearAllEventHandler(ActionListener listener) {
		clearallB.addActionListener(listener);
	}

	public void addSaveEventHandler(ActionListener listener) {
		saveB.addActionListener(listener);
	}

	public void addCreateFunctionEventHandler(ActionListener listener) {
		createFunctionB.addActionListener(listener);
	}
	
	/**Code List/
	public void addCodeListSelectionListener(ActionListener listener) {
		playcodeList.addListSelectionListener(listener);
	}**/
	
	/**JComboBoxes**/
	public void addConditionalsListSelectionListener(ActionListener listener) {
		conditionalsDD.addActionListener(listener);
	}
	
	public void addGivenFunctionsListSelectionListener(ActionListener listener) {
		givenFunctionsDD.addActionListener(listener);

	}
	
	public void addUserFunctionsListSelectionListener(ActionListener listener) {
		userFunctionsDD.addActionListener(listener);
	}
	
	public void addChecksFunctionsListSelectionListener(ActionListener listener) {
		checksDD.addActionListener(listener);
	}
	
	public void addNumsFunctionsListSelectionListener(ActionListener listener) {
		numsDD.addActionListener(listener);
	}
	
	public void showMove(int gerbilCurrX, int gerbilCurrY, int gerbilNewX, int gerbilNewY, char compass) {
		
		gridBoxes[gerbilCurrX][gerbilCurrY].setIcon(imageGrass);
		switch(compass) {
		case'n':gridBoxes[gerbilNewX][gerbilNewY].setIcon(imageGerbilNorth);
				break;
		case's':gridBoxes[gerbilNewX][gerbilNewY].setIcon(imageGerbilSouth);
				break;
		case'w':gridBoxes[gerbilNewX][gerbilNewY].setIcon(imageGerbilWest);
				break;
		case'e':gridBoxes[gerbilNewX][gerbilNewY].setIcon(imageGerbilEast);
				break;
		}
	}
	
	public void showTurnLeft(char compass, int gerbilX, int gerbilY) {
		
		switch(compass) {
			case'n':gridBoxes[gerbilX][gerbilY].setIcon(imageGerbilNorth);
					break;
			case's':gridBoxes[gerbilX][gerbilY].setIcon(imageGerbilSouth);
					break;
			case'w':gridBoxes[gerbilX][gerbilY].setIcon(imageGerbilWest);
					break;
			case'e':gridBoxes[gerbilX][gerbilY].setIcon(imageGerbilEast);
					break;
		}
	}
	
	public static boolean beforeIsConditional(){
		if(Play.instructions.get(Play.playcodeList.getSelectedIndex()-1) == "If"){
			return true;
		}else if(Play.instructions.get(Play.playcodeList.getSelectedIndex()-1) == "Else if"){
			return true;
		}else if(Play.instructions.get(Play.playcodeList.getSelectedIndex()-1) == "Else"){
			return true;
		}else if(Play.instructions.get(Play.playcodeList.getSelectedIndex()-1) == "While"){
			return true;
		}else if(Play.instructions.get(Play.playcodeList.getSelectedIndex()-1) == "Repeat"){
			return true;
		}

		return false;
	}
	
	public static boolean conditionalSelected(){ 
		if(Play.instructions.get(Play.playcodeList.getSelectedIndex()) == "If"){
			return true;
		}else if(Play.instructions.get(Play.playcodeList.getSelectedIndex()) == "Else if"){
			return true;
		}else if(Play.instructions.get(Play.playcodeList.getSelectedIndex()) == "Else"){
			return true;
		}else if(Play.instructions.get(Play.playcodeList.getSelectedIndex()) == "While"){
			return true;
		}else if(Play.instructions.get(Play.playcodeList.getSelectedIndex()) == "Repeat"){
			return true;
		}

		return false;
	}
	
	public static void disableAllPlayDDButChecks(){
		Play.conditionalsDD.setEnabled(false);
		Play.givenFunctionsDD.setEnabled(false);
		Play.userFunctionsDD.setEnabled(false);
		Play.numsDD.setEnabled(false);
	}

	public static void enableAllPlayDD(){
		Play.conditionalsDD.setEnabled(true);
		Play.givenFunctionsDD.setEnabled(true);
		Play.userFunctionsDD.setEnabled(true);
		Play.checksDD.setEnabled(true);
		Play.numsDD.setEnabled(true);
	}
	
	public static void clearAll(){
		model.clear();
		model.addElement("End");
		model.addElement("Begin");
	}
}
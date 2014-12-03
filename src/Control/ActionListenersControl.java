package Control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import View.ErrorDialog;
import View.Instructions;
import View.Main;
import View.Play;
import View.PlayOptions;
import View.UserFunction;

public class ActionListenersControl {
	
	Main main;
	Play play;
	PlayOptions playOptions;
	Instructions instructions;
	UserFunction userFunction;
	ErrorDialog errorDialog;
	public static Controller controller;
	
	int selectedIndexPlayScreen; /*Code list*/
	
	/*ComboBoxes*/
	static int selectedIndexConditionals;
	static int selectedIndexGivenFunctions;
	static int selectedIndexUserFunctions;
	
	int selectedIndexOther;
	
	public ActionListenersControl(){
		controller = new Controller();
		main = new Main();
		playOptions = new PlayOptions();
		instructions = new Instructions();
		userFunction = new UserFunction();
		errorDialog = new ErrorDialog();
		errorDialog.hide();
		
		Play.setNewGrid(controller.gamePlaying.getGrid().getGridRepresentation());
		play = new Play();
		initEventHandlers();
		main.show();
	}
	/**
	 * Sets up event handlers for each screen
	 */
	private void initEventHandlers() {
		
		addMainEventHandlers();
		addPlayOptionsEventHandlers();
		addInstructionsEventHandlers();
		addPlayEventHandlers();
		addUserFunctionEventHandlers();
		addErrorDialogEventHandlers();
	}
	
	/**
	 * Add event handlers for the Main screen
	 */
	private void addMainEventHandlers() {
		
		main.addPlayEventHandler(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				playOptions.show();
				main.hide();
			}
		});
		main.addInstructionsEventHandler(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				instructions.show();
				main.hide();
			}
		});
		main.addExitEventHandler(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
	}
	
	/**
	 * Add event handlers for the PlayOptions screen
	 */
	private void addPlayOptionsEventHandlers() {
		
		playOptions.addLoadGameEventHandler(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
			}	
		});
		playOptions.addNewGameEventHandler(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				play.show();
				playOptions.hide();
			}	
		});
		playOptions.addBackEventHandler(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				main.show();
				playOptions.hide();
			}
		});
	}
	
	private void addInstructionsEventHandlers() {
		
		instructions.addBackEventHandler(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				instructions.hide();
				main.show();
			}		
		});
	}
	
	
	/**
	 * Add event handlers for the Play screen
	 */
	private void addPlayEventHandlers() {
		/**Button Listeners**/
		play.addMenuEventHandler(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				playOptions.show();
				play.hide();
			}	
		});
		
		play.addPlayEventHandler(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			
			}	
		});

		play.addStopEventHandler(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			
			}	
		});
		
		play.addInsertEventHandler(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			
			}	
		});
		
		play.addEditEventHandler(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			
			}	
		});
		
		play.addDeleteEventHandler(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			
			}	
		});
		
		play.addClearAllEventHandler(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			
			}	
		});
		
		play.addSaveEventHandler(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			
			}	
		});
		
		play.addCreateFunctionEventHandler(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				userFunction.show();
				play.hide();
			}	
		});
		
		/**Code List
		
		play.addCodeListSelectionListener(new ListSelectionListener() {
			
		});**/
		
		/**JComboBoxes**/
		play.addConditionalsListSelectionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//Play.playcodeList
				ArrayList<String> temp = new ArrayList<String>();
				
				selectedIndexConditionals = Play.conditionalsDD.getSelectedIndex();
			}	
		});
		
		play.addGivenFunctionsListSelectionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			
				selectedIndexGivenFunctions = Play.givenFunctionsDD.getSelectedIndex();
				
				if(Play.instructions.get(0) == "Begin"){
					errorDialog.errorL.setText("Cannot insert here");
					errorDialog.show();
				}
			}	
		});
		
		play.addUserFunctionsListSelectionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				selectedIndexUserFunctions = Play.givenFunctionsDD.getSelectedIndex();
			}	
		});
	}
	
	private void addUserFunctionEventHandlers() {
		userFunction.addCancelEventHandler(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				play.show();
				userFunction.hide();
			}
		});
		
		userFunction.addOkEventHandler(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				//System.out.println(UserFunction.conditionalDropdown.getText());
				Controller.userDefined.add(UserFunction.conditionalDropdown.getText());
				play.refreshUserFunctions();
				play.show();
				userFunction.hide();
			}
		});
	}
	
	private void addErrorDialogEventHandlers() {
		errorDialog.addOkEventHandler(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				errorDialog.hide();
			}
		});
	}
}
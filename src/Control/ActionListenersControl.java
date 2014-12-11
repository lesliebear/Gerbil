package Control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import View.*;

public class ActionListenersControl {
	
	Main main; 
	Play play;
	DeleteFunction deleteFunction;
	ErrorDialog errorDialog;
	PlayOptions playOptions;
	Instructions instructions;
	UserFunction userFunction;
	Controller controller;
	
	public ActionListenersControl(){
		controller = new Controller();
		errorDialog = new ErrorDialog();
		deleteFunction = new DeleteFunction();
		main = new Main();
		playOptions = new PlayOptions();
		instructions = new Instructions();
		userFunction = new UserFunction();
		//initGrid();
		play = new Play();
		initEventHandlers();
		main.show();
	}
	
	private void initGrid() {
		Play.setNewGrid(controller.gamePlaying.getGrid().getGridRepresentation());
		Play.setGerbilLocation(controller.gamePlaying.getGerbil().getY(), controller.gamePlaying.getGerbil().getX());
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
				
				controller.createGame();
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
		
		play.addDeleteFunctionEventHandler(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				deleteFunction.show();
				play.hide();
			}	
		});
		
		play.addCreateFunctionEventHandler(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				userFunction.show();
				play.hide();
			}	
		});
		
		/**Code List**/
		
		//MISSING
		
		/**JComboBoxes**/
		
		play.addConditionalsListSelectionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			
			}	
		});
		
		play.addGivenFunctionsListSelectionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			
			}	
		});
		
		play.addUserFunctionsListSelectionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			
			}	
		});
	}
	
	private void addDeleteFunctionsEventHandlers() {
		
	}
	
	private void addUserFunctionEventHandlers() {
		
	
		userFunction.addListSelectionEventHandler(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent arg0) {
				controller.getHighlighting(userFunction.getSelectedLineNumber(), controller.gamePlaying.getBlocks());
			}
			
		});
		
		userFunction.addEatEventHandler(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		
		userFunction.addElseEventHandler(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

			}
			
		});
		
		userFunction.addElseIfEventHandler(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		
		userFunction.addIfEventHandler(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

			}
		});
		
		userFunction.addMoveAheadEventHandler(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}	
		});
		
		userFunction.addTurnLeftEventHandler(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		
		userFunction.addOkEventHandler(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		
		userFunction.addCancelEventHandler(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				play.show();
				userFunction.hide();
			}
		});
	}
}
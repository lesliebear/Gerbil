package Control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import View.Instructions;
import View.Main;
import View.Play;
import View.PlayOptions;

public class ActionListnersControl {
	Main main;
	Play play;
	PlayOptions playOptions;
	Instructions instructions;
	
	public ActionListnersControl(){
		Instructions instructions = new Instructions();
		Play play = new Play();
		//DeleteFunction df = new DeleteFunction();
		//Main main = new Main();
		//playOptions = new PlayOptions();
		//initEventHandlers();
	}
	/**
	 * Sets up event handlers for each screen
	 */
	private void initEventHandlers() {
		
		addMainEventHandlers();
		addPlayOptionsEventHandlers();
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
			
			}
		});
		main.addExitEventHandler(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
					
			}
		});
	}
	
	/**
	 * Add event handlers for the PlayOptions screen
	 */
	private void addPlayOptionsEventHandlers() {
		
		playOptions.addLoadGameEventHandler(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			
			}	
		});
		playOptions.addNewGameEventHandler(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			
			}	
		});
		playOptions.addBackEventHandler(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				main.show();
				playOptions.hide();
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
}

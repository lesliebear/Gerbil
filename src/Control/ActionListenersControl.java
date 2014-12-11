package Control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Model.*;

import View.*;
 
public class ActionListenersControl {
	static NewGame newGame;
	static DeleteFunction deleteFunction;
	static ErrorDialog errorDialog;
	static Finish finish;
	static Instructions instructionsScreen;
	static Main main; 
	static Play playScreen;
	static PlayOptions playOptions;
	static SavedGames savedGames;
	static UserFunction userFunction;
	
	int selectedIndexPlayScreen; /*Code list*/

	/*ComboBoxes*/
	static int selectedIndexConditionals;
	static int selectedIndexGivenFunctions;
	static int selectedIndexUserFunctions;

	static int selectedIndexPlayCodeList;

	int selectedIndexOther;

	boolean selectedCreateFunctionFirst;

	boolean inserting;
	boolean deleting;
	boolean editing;
	boolean stop;
	boolean play;
	int prevBegin=-1;
	
	static int parentScreen;

	public ActionListenersControl(){
		newGame = new NewGame();
		deleteFunction = new DeleteFunction();
		errorDialog = new ErrorDialog();
		finish = new Finish();
		instructionsScreen = new Instructions();
		main = new Main(); 
		
		playOptions = new PlayOptions();
		savedGames = new SavedGames();

		inserting = false;
		deleting = false;
		editing = false;

		initEventHandlers();
		main.show();
	}
	
	private void initGrid() {
		Play.setNewGrid(Start.StartGerbil.controller.gamePlaying.getGrid().getGridRepresentation());
		Play.setGerbilLocation(Start.StartGerbil.controller.gamePlaying.getGerbil().getY(), Start.StartGerbil.controller.gamePlaying.getGerbil().getX());
	}

	/**
	 * Sets up event handlers for each screen
	 */
	private void initEventHandlers() {
		addMainEventHandlers();
		addNewGameEventHandlers();
		addPlayOptionsEventHandlers();
		addInstructionsEventHandlers();
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
				parentScreen = 2; 
			}
		});
		main.addInstructionsEventHandler(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				instructionsScreen.show();
				main.hide();
				parentScreen = 2; 
			}
		});
		main.addExitEventHandler(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
	}

	/**
	 * DeleteFunction 1
	 * Main 2
	 * NewGame 3
	 * Play 4
	 * PlayOptions 5
	 * SavedGame 6
	 * UserFunction 7
	 */
	
	/**
	 * Add event handlers for the PlayOptions screen
	 */
	private void addPlayOptionsEventHandlers() {
		playOptions.addLoadGameEventHandler(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				parentScreen =5;
				playOptions.hide();
				
				savedGames.show(); 
			}	
		});
		
		playOptions.addNewGameEventHandler(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
				
				if(parentScreen == 4){ // the person clicked the menu button from an old play screen and now wants to start a new game
					playScreen.getPlayFrame().dispose();
					System.out.println("THIS RANTHISRANTHISRAN!!!!!!!!!!!!!!!!!!");
				}
				
				parentScreen = 5;
				playOptions.hide();
				
				newGame.show();
			}	
		});
		
		playOptions.addBackEventHandler(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				playOptions.hide();
				
				showParent();
				parentScreen = 5;
			}
		});
	}

	private void addInstructionsEventHandlers() {

		instructionsScreen.addBackEventHandler(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				instructionsScreen.hide();
				main.show();
			}		
		});
	}
	
	private void addNewGameEventHandlers() {
		newGame.addOkEventHandler(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
				String text = newGame.textF.getText(); 
				parentScreen = 3;
				
				if(!text.isEmpty()){
					if(Start.StartGerbil.backend.gameExists(text)){
						errorDialog.errorL.setText("Please enter a Game name that doesn't already exist.");
						newGame.hide();
						errorDialog.show();
					}else{
						Game g = new Game();
						Start.StartGerbil.backend.addGame(g); // not sure what add game does in this version - kat
						Start.StartGerbil.controller.setCurrentGame(g);
						initGrid();
						
						playScreen = new Play();
						userFunction = new UserFunction();
						
						addPlayEventHandlers();
						
						
						newGame.hide();
						playScreen.show();
					}
				}else{
					errorDialog.errorL.setText("Please enter a valid Game name");
					newGame.hide();
					errorDialog.show();
				}
			}		
		});
		
		newGame.addBackEventHandler(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				newGame.textF.setText("");
				newGame.hide();
				main.show();
			}		
		});
	}
	
	private void addErrorDialogEventHandlers() {
		errorDialog.addOkEventHandler(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				errorDialog.hide();
				showParent();
			}		
		});
	}
	

	/**
	 * Add event handlers for the Play screen
	 */

	/**
	 * Add event handlers for the Play screen
	 */
	/**
	 * Add event handlers for the Play screen
	 */
	private void addPlayEventHandlers() {
		/**Button Listeners**/
		
		playScreen.addMenuEventHandler(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				parentScreen = 4; 
				playOptions.show();
				playScreen.hide();
			}	
		});

		/*play.addPlayEventHandler(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Thread thread = new Thread() {
					public void run() {
						String[] instructions = controller.getTerminals();
						for(int i = 0; i < instructions.length; i++) {
							if(instructions[i].equals("turn left")) {
								controller.turnLeft(controller.tempgerbil);
								play.showTurnLeft(controller.tempgerbil.getCompass(), controller.tempgerbil.getY(), controller.tempgerbil.getX());
							}
							else if(instructions[i].equals("move")) {
								int currX = controller.tempgerbil.getX();
								int currY = controller.tempgerbil.getY();
								controller.moveForward(controller.tempgerbil);
								play.showMove(currY, currX, controller.tempgerbil.getY(), controller.tempgerbil.getX(), controller.tempgerbil.getCompass(), controller.tempgrid[currY][currX]);

							}
							else if(instructions[i].equals("eat")) {
								controller.eat(controller.tempgerbil.getFrontX(), controller.tempgerbil.getY());
							}
							try {
								sleep(500);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
				};
				thread.start();
			}
		}); */

		playScreen.addStopEventHandler(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				playScreen.setStopSelected();
			}	
		});

		playScreen.addInsertEventHandler(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				inserting = true;
				playScreen.setInsertSelected();
				
				
			}	
		});

		playScreen.addDoneEventHandler(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(inserting==true){
					playScreen.setDoneSelected();
					//get the info we need from the stuff to fill into the insert blocks
					Start.StartGerbil.controller.createBlocks('e', prevBegin, numLines, cond);
				}
				
			}	
		});

		playScreen.addDeleteEventHandler(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				deleting = true;
				
				playScreen.setDeleteSelected();
			}
		});

		playScreen.addClearAllEventHandler(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Play.clearAll();
				
				playScreen.setClearAllSelected();
			}	
		});

		playScreen.addSaveEventHandler(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try{
					Start.StartGerbil.backend.saveGames(Start.StartGerbil.backend.getGameList());
				}catch(Exception e){
					System.out.println("Unable to save game.");
				}
				
				playScreen.setSaveSelected();
			}	
		});

		playScreen.addCreateFunctionEventHandler(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				selectedCreateFunctionFirst=true;
				parentScreen = 4; 
				
				userFunction.show();
				playScreen.hide();
				
			}	
		});

		
		 
		/**JComboBoxes**/
		playScreen.addConditionalsListSelectionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				selectedIndexPlayCodeList = Play.playcodeList.getSelectedIndex();
				
			}	
		});

		/*playScreen.addGivenFunctionsListSelectionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				selectedIndexPlayCodeList = Play.playcodeList.getSelectedIndex();

				if(inserting == true){
					Play.enableAllPlayDD();
					Play.instructions.add(selectedIndexPlayCodeList, Play.givenFunctionsDD.getSelectedItem().toString());
					Play.refreshCodeList();
					Play.playcodeList.setSelectedIndex(Play.playcodeList.getModel().getSize()-2);

				}else if(editing == true){
					if(Play.conditionalSelected()){
						System.out.println(Play.instructions.get(selectedIndexPlayCodeList).substring(0,6));
						if(Play.instructions.get(selectedIndexPlayCodeList).substring(0,6) == "Repeat"){

						}

					}else if(){

					}
				}
			}
		});	*/

		playScreen.addUserFunctionsListSelectionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

			}	
		});
	}
	
	/**
	 * DeleteFunction 1
	 * Main 2
	 * NewGame 3
	 * Play 4
	 * PlayOptions 5
	 * SavedGame 6
	 * UserFunction 7
	 */
	
	private static void showParent(){ 
		switch(parentScreen){
		case 1:
			deleteFunction.show();
			break;
		case 2:
			main.show();
			break;
		case 3:
			newGame.show();
			break;
		case 4:
			playScreen.show();
			break;
		case 5:
			playOptions.show();
			break;
		case 6:
			savedGames.show();
			break;
		case 7:
			userFunction.show();
			break;
		}
	}
}
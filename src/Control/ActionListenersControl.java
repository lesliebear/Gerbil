package Control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Model.Game;
import Start.StartGerbil;
import View.Conditionals;
import View.DeleteFunction;
import View.ErrorDialog;
import View.Finish;
import View.Instructions;
import View.Main;
import View.NewGame;
import View.Play;
import View.PlayOptions;
import View.SavedGames;
import View.UserFunction;

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
	static Conditionals conditionals;

	int selectedIndexPlayScreen; /*Code list in Play Screen*/
	static int selectedIndexPlayCodeList;

	boolean selectedCreateFunctionFirst;

	boolean inserting;
	boolean deleting;
	boolean editing;
	boolean stop;
	boolean play;
	
	boolean deleteCurrGame;
	
	static int parentScreen;
	String backT="     ";
	
	public ActionListenersControl(){
		//Set up GAME - idk...need so screen models aren't null...
		Start.StartGerbil.controller.setCurrentGame(new Game("setUpGame")); // DO NOT remove. kthx.
		initGrid();
		
		newGame = new NewGame();
		deleteFunction = new DeleteFunction();
		errorDialog = new ErrorDialog();
		finish = new Finish();
		instructionsScreen = new Instructions();
		main = new Main(); 
		conditionals = new Conditionals(" ");
		
		playOptions = new PlayOptions();
		savedGames = new SavedGames();
		
		userFunction = new UserFunction();
		playScreen = new Play();
		playScreen.initialPlayScreen();
		
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
					newGame.textF.setText(""); //must reset the text line
					
					if(Start.StartGerbil.backend.gameExists(text)){
						errorDialog.errorL.setText("Please enter a Game name that doesn't already exist.");
						newGame.hide();
						errorDialog.show();
					}else{
						Game g = new Game(text);
						Start.StartGerbil.backend.addGame(g);
						Start.StartGerbil.controller.setCurrentGame(g);
						
						initGrid();
						Play.refreshGrid();
						
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
	
	
	private void addUserFunctionEventHandlers(){
		userFunction.addBackEventHandler(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				errorDialog.hide();
				showParent();
			}		
		});
		
		userFunction.addOkEventHandler(new ActionListener() {
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
				playScreen.enableAllPlayDD();	
				playScreen.enableAllButtons();
				playScreen.setInsertSelected();
			}	
		});

		playScreen.addEditEventHandler(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				editing = true;
				
				playScreen.setEditSelected();
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
				playScreen.clearAll();
				
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
				playScreen.setSaveUnselected();
			}	
		});

		playScreen.addCreateFunctionEventHandler(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				selectedCreateFunctionFirst=true;
				parentScreen = 4; 
				
			//First, create BLOCKS(instructions) that goes in the function
				int type; //get block type
				int begin; //get line begin
				int numLines; //get number of lines
				String cond; //get cond
				
				Start.StartGerbil.controller.createFunctionBlocks(type, begin, numLines, cond);
				
				
				userFunction.show();
				playScreen.hide();
				
			}	
		});

		
		 
		/**JComboBoxes**/
		playScreen.addConditionalsListSelectionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				selectedIndexPlayCodeList = Play.playcodeList.getSelectedIndex();
				String newType = Play.conditionalsDD.getSelectedItem().toString();
				//if(3),elseif(4),else(5),while(6),repeat(7), 
				//String[] conditionals = { "If", "Else", "Else if", "While", "Repeat" };
				if(newType.equals("If")){
					StartGerbil.controller.createBlocks(3,selectedIndexPlayCodeList, 0, null);
				}else if(newType.equals("Else")){
					StartGerbil.controller.createBlocks(5,selectedIndexPlayCodeList , 0, null);
				}else if(newType.equals("Else if")){
					StartGerbil.controller.createBlocks(4,selectedIndexPlayCodeList, 0, null);
				}else if(newType.equals("While")){
					StartGerbil.controller.createBlocks(6,selectedIndexPlayCodeList, 0, null);
				}else {//if(newType.equals("Repeat")){
					StartGerbil.controller.createBlocks(7,selectedIndexPlayCodeList, 0, null);
				}
				
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
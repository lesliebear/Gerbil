package Control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import Model.Game;
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
		
		userFunction.addWhileEventHandler(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				int uSelect = userFunction.getSelectedLineNumber();
				Start.StartGerbil.controller.createFunctionBlocks(7, uSelect,0, null);
				//GET CONDITIONALS SCREEN STUFF HERE!! WHEN THAT RETURNS BY CLICKING OK!!
				Start.StartGerbil.controller.createFunctionBlocks('e', uSelect,numberoflines here, cond here);
			}
		});
		
		userFunction.addWhileEventHandler(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				int uSelect = userFunction.getSelectedLineNumber();
				Start.StartGerbil.controller.createFunctionBlocks(6, uSelect,0, null);
				//GET CONDITIONALS SCREEN STUFF HERE!! WHEN THAT RETURNS BY CLICKING OK!!
				Start.StartGerbil.controller.createFunctionBlocks('e', uSelect,numberoflines here, cond here);
			}
		});
		
		userFunction.addElseEventHandler(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				int uSelect = userFunction.getSelectedLineNumber();
				Start.StartGerbil.controller.createFunctionBlocks(5, uSelect,0, null);
				//GET CONDITIONALS SCREEN STUFF HERE!! WHEN THAT RETURNS BY CLICKING OK!!
				Start.StartGerbil.controller.createFunctionBlocks('e', uSelect,numberoflines here, cond here);
			}
		});
		
		
		userFunction.addElseIfEventHandler(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				int uSelect = userFunction.getSelectedLineNumber();
				Start.StartGerbil.controller.createFunctionBlocks(4, uSelect,0, null);
				//GET CONDITIONALS SCREEN STUFF HERE!! WHEN THAT RETURNS BY CLICKING OK!!
				Start.StartGerbil.controller.createFunctionBlocks('e', uSelect,numberoflines here, cond here);
			}
		});
		userFunction.addIfEventHandler(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				int uSelect = userFunction.getSelectedLineNumber();
				Start.StartGerbil.controller.createFunctionBlocks(3, uSelect,0, null);
				//GET CONDITIONALS SCREEN STUFF HERE!! WHEN THAT RETURNS BY CLICKING OK!!
				Start.StartGerbil.controller.createFunctionBlocks('e', uSelect,numberoflines here, cond here);	
			}});
		
		
		userFunction.addMoveAheadEventHandler(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				Start.StartGerbil.controller.createFunctionBlocks(2, userFunction.getSelectedLineNumber(),1, null);
				Start.StartGerbil.controller.createFunctionBlocks('e', userFunction.getSelectedLineNumber(),1, null);
				
			}});
		
		userFunction.addEatEventHandler(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				Start.StartGerbil.controller.createFunctionBlocks(0, userFunction.getSelectedLineNumber(), 1, null);
				Start.StartGerbil.controller.createFunctionBlocks('e', userFunction.getSelectedLineNumber(), 1, null);
			}
		});
		
		userFunction.addTurnLeftEventHandler(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				Start.StartGerbil.controller.createBlocks(1,userFunction.getSelectedLineNumber(),1, null);
				Start.StartGerbil.controller.createBlocks('e',userFunction.getSelectedLineNumber(),1, null);
			}});
		
		
		userFunction.addOkEventHandler(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String functionName = userFunction.getFunctionName();
				
				if(!functionName.equals("")) {
					
					/* Do something here with function name*/
					errorDialog.hide();
					showParent();
				}
				else {
					/* show the error that function name isn't inputted yet */
				}
			
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

	/*	playScreen.addPlayEventHandler(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Thread thread = new Thread() {
					public void run() {
						Start.StartGerbil.controller.resetTempGrid();//just in case, resetting grid and gerbil object
						int errortype= Start.StartGerbil.controller.runBlocks();
						ArrayList<String> instructions = Start.StartGerbil.controller.getFinalBlocks();
						for(int i = 0; i < instructions.size(); i++) {
							if(instructions.get(i).equals("turn left")) {
								Start.StartGerbil.controller.turnLeft(Start.StartGerbil.controller.getTempGerbil());
								playScreen.showTurnLeft(Start.StartGerbil.controller.getTempGerbil().getCompass(), Start.StartGerbil.controller.getTempGerbil().getY(), Start.StartGerbil.controller.getTempGerbil().getX());
							}
							else if(instructions.get(i).equals("move")) {
								int currX = Start.StartGerbil.controller.getTempGerbil().getX();
								int currY = Start.StartGerbil.controller.getTempGerbil().getY();
								Start.StartGerbil.controller.moveForward(Start.StartGerbil.controller.getTempGerbil());
								playScreen.showMove(currY, currX, Start.StartGerbil.controller.getTempGerbil().getY(), Start.StartGerbil.controller.getTempGerbil().getX(), Start.StartGerbil.controller.getTempGerbil().getCompass(), Start.StartGerbil.controller.tempgrid[currY][currX]);

							}
							else if(instructions.get(i).equals("eat")) {
								Start.StartGerbil.controller.eat(Start.StartGerbil.controller.getTempGerbil().getX(), Start.StartGerbil.controller.getTempGerbil().getY(), Start.StartGerbil.controller.tempgrid);
							}
							try {
								sleep(500);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						if(errortype==1){
							//ERROR: insert Dialogue BoxCannot Eat because no food here
							errorDialog.errorL.setText("Cannot Eat: there is no food at square");
							errorDialog.show();
						}else if(errortype==2){
							//ERROR: insert Dialogue BoxCannot Move Forward bc there is WALL
							errorDialog.errorL.setText("Cannot Move Forward: there is a wall ahead");
							errorDialog.show();
						}else if(errortype==3){
							//miscellaneous error, could not compile code(this shouldn't happen)
						}else if(errortype==4){
							//ERROR: insert Dialogue BoxDid not reach water/goal
							errorDialog.errorL.setText("Did not reach water, Try Again!");
							errorDialog.show();
						}else if(errortype==-1){
							//parsing error(this shouldn't happen)
						}else if(errortype==-2){
							//ERROR: insert Dialogue BoxInfiniteLoop was created, cannot run code
							//this does not run/animate the gerbil
							errorDialog.errorL.setText("Infinite Loop was created, please edit your code");
							errorDialog.show();
						}
					}
				};
				thread.start();
			}
		});  */

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
				
				Start.StartGerbil.controller.clearBlocks();
			}	
		});

		playScreen.addSaveEventHandler(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {	
				try{
					Start.StartGerbil.controller.saveGame();
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

				//Start.StartGerbil.controller.createFunctionBlocks(type, begin, numLines, cond);


				userFunction.show();
				playScreen.hide();

			}	
		});

		/**JComboBoxes**/
		playScreen.addConditionalsListSelectionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(inserting==true){
					selectedIndexPlayCodeList = Play.playcodeList.getSelectedIndex();
					String newType = Play.conditionalsDD.getSelectedItem().toString();
					//if(3),elseif(4),else(5),while(6),repeat(7), 
					//String[] conditionals = { "If", "Else", "Else if", "While", "Repeat" };
					if(newType.equals("If")){
						Start.StartGerbil.controller.createBlocks(3,selectedIndexPlayCodeList, 0, null);
						
						conditionals.type = "If";
						
					}else if(newType.equals("Else")){
						Start.StartGerbil.controller.createBlocks(5,selectedIndexPlayCodeList , 0, null);
					}else if(newType.equals("Else if")){
						Start.StartGerbil.controller.createBlocks(4,selectedIndexPlayCodeList, 0, null);
					}else if(newType.equals("While")){
						Start.StartGerbil.controller.createBlocks(6,selectedIndexPlayCodeList, 0, null);
					}else {//if(newType.equals("Repeat")){
						Start.StartGerbil.controller.createBlocks(7,selectedIndexPlayCodeList, 0, null);
					}
				}else{
					
				}
				
				conditionals.show();

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


		playScreen.addCodeListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if(deleting == true){
					selectedIndexPlayCodeList = Play.playcodeList.getSelectedIndex();
					Start.StartGerbil.controller.deleteBlock(selectedIndexPlayCodeList);
				}
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
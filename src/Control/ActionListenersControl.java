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
import View.OkYesDialog;
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
	static OkYesDialog okNoDialog;

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
		okNoDialog = new OkYesDialog();

		userFunction = new UserFunction();
		playScreen = new Play();
		playScreen.initialPlayScreen();

		inserting = false;
		deleting = false;
		editing = false;

		initEventHandlers();
		main.show();
		//userFunction.show();
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
		addOkYesDialogEventHandlers();
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

				main.show();
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
	
	private void addOkYesDialogEventHandlers(){
		okNoDialog.addNoEventHandler(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				main.show();
			}		
		});
		
		okNoDialog.addOkEventHandler(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					Start.StartGerbil.controller.saveGame();
				}catch(Exception ex){
					System.out.println("Unable to save game.");
				}
				
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
						Start.StartGerbil.controller.createGame(text);

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
		
		userFunction.addFunctionListListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String funcName = (String) userFunction.userDefinedFunctions.getSelectedItem();
				Start.StartGerbil.controller.createFunctionBlocks(8, userFunction.getSelectedLineNumber(), 1, null);
				Start.StartGerbil.controller.createFunctionBlocks('e', userFunction.getSelectedLineNumber(), 1, funcName);
				userFunction.updateInstructionsList(Start.StartGerbil.controller.FunctionUnFin());
			}
		});
		
		
		userFunction.addBackEventHandler(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				errorDialog.hide();
				showParent();
				
			}		
		});
		
		userFunction.addReapeatEventHanderl(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				int uSelect = userFunction.getSelectedLineNumber();
				Start.StartGerbil.controller.createFunctionBlocks(7, uSelect,0, null);
				conditionals.setText("Repeat");
				int tempLine = uSelect+2; //for the current statement and begin
				conditionals.setBegin(tempLine);
				conditionals.show(); //shows the conditionals screen so when they return we get back here
				userFunction.hide();
				Start.StartGerbil.controller.createFunctionBlocks('e', uSelect,conditionals.getEndLineNumber(), conditionals.getCond());
				userFunction.updateInstructionsList(Start.StartGerbil.controller.FunctionUnFin());
			}
		});
		
		userFunction.addWhileEventHandler(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				int uSelect = userFunction.getSelectedLineNumber();
				Start.StartGerbil.controller.createFunctionBlocks(6, uSelect,0, null);
				conditionals.setText("While");
				int tempLine = uSelect+2; //for the current statement and begin
				conditionals.setBegin(tempLine);
				conditionals.show(); //shows the conditionals screen so when they return we get back here
				userFunction.hide();
				Start.StartGerbil.controller.createFunctionBlocks('e', uSelect,conditionals.getEndLineNumber(), conditionals.getCond());
				userFunction.updateInstructionsList(Start.StartGerbil.controller.FunctionUnFin());
			}
		});
		
		userFunction.addElseEventHandler(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				int uSelect = userFunction.getSelectedLineNumber();
				Start.StartGerbil.controller.createFunctionBlocks(5, uSelect,0, null);
				conditionals.setText("Else");
				int tempLine = uSelect+2; //for the current statement and begin
				conditionals.setBegin(tempLine);
				conditionals.show(); //shows the conditionals screen so when they return we get back here
				userFunction.hide();
				Start.StartGerbil.controller.createFunctionBlocks('e', uSelect,conditionals.getEndLineNumber(), conditionals.getCond());
				userFunction.updateInstructionsList(Start.StartGerbil.controller.FunctionUnFin());
			}
		});
		
		
		userFunction.addElseIfEventHandler(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				int uSelect = userFunction.getSelectedLineNumber();
				Start.StartGerbil.controller.createFunctionBlocks(4, uSelect,0, null);
				conditionals.setText("Else If");
				int tempLine = uSelect+2; //for the current statement and begin
				conditionals.setBegin(tempLine);
				conditionals.show(); //shows the conditionals screen so when they return we get back here
				userFunction.hide();
				Start.StartGerbil.controller.createFunctionBlocks('e', uSelect,conditionals.getEndLineNumber(), conditionals.getCond());
				userFunction.updateInstructionsList(Start.StartGerbil.controller.FunctionUnFin());
			}
		});
		userFunction.addIfEventHandler(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				int uSelect = userFunction.getSelectedLineNumber();
				Start.StartGerbil.controller.createFunctionBlocks(3, uSelect,0, null);
				conditionals.setText("If");
				int tempLine = uSelect+2; //for the current statement and begin
				conditionals.setBegin(tempLine);
				conditionals.show(); //shows the conditionals screen so when they return we get back here
				userFunction.hide();
				Start.StartGerbil.controller.createFunctionBlocks('e', uSelect,conditionals.getEndLineNumber(), conditionals.getCond());
				userFunction.updateInstructionsList(Start.StartGerbil.controller.FunctionUnFin());
			}});
			
		
		
		userFunction.addMoveAheadEventHandler(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				Start.StartGerbil.controller.createFunctionBlocks(2, userFunction.getSelectedLineNumber(),1, null);
				Start.StartGerbil.controller.createFunctionBlocks('e', userFunction.getSelectedLineNumber(),1, null);
				userFunction.updateInstructionsList(Start.StartGerbil.controller.FunctionUnFin());
				
			}});
		
		userFunction.addEatEventHandler(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				Start.StartGerbil.controller.createFunctionBlocks(0, userFunction.getSelectedLineNumber(), 1, null);
				Start.StartGerbil.controller.createFunctionBlocks('e', userFunction.getSelectedLineNumber(), 1, null);
				userFunction.updateInstructionsList(Start.StartGerbil.controller.FunctionUnFin());
			}
		});
		
		userFunction.addTurnLeftEventHandler(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				Start.StartGerbil.controller.createFunctionBlocks(1,userFunction.getSelectedLineNumber(),1, null);
				Start.StartGerbil.controller.createFunctionBlocks('e',userFunction.getSelectedLineNumber(),1, null);
				userFunction.updateInstructionsList(Start.StartGerbil.controller.FunctionUnFin());
			}});
		
		
		userFunction.addOkEventHandler(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String functionName = userFunction.getFunctionName();
				
				if(!functionName.equals("")) {
					
					errorDialog.hide();
					showParent();
					int error= Start.StartGerbil.controller.createFunction(functionName);
					if(error==1){
						//error: function names can only consist of letters/numbers
						errorDialog.errorL.setText("Name must consist of letters/numbers");
						errorDialog.show();
					}else if(error==2){
						//error: function name already exists, choose another
						errorDialog.errorL.setText("Name already exists, enter another name");
						errorDialog.show();
					}else{
						userFunction.hide();
					}
				}else{
					errorDialog.errorL.setText("You Must Enter a Function Name");
					errorDialog.show();
				}
			
			}		
		});
	}

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

		playScreen.addPlayEventHandler(new ActionListener() {
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
								playScreen.showMove(currX, currY, Start.StartGerbil.controller.getTempGerbil().getY(), Start.StartGerbil.controller.getTempGerbil().getX(), Start.StartGerbil.controller.getTempGerbil().getCompass());
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
		});

		playScreen.addStopEventHandler(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				playScreen.setStopSelected();
			}	
		});

		playScreen.addInsertEventHandler(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				inserting = true;
				editing = false;
				deleting = false; 
				
				playScreen.enableAllPlayDD();	
				playScreen.enableAllButtons();
				playScreen.setInsertSelected();
			}	
		});

		playScreen.addEditEventHandler(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				editing = true;
				inserting = false;
				deleting = false;

				playScreen.setEditSelected();
			}	
		});

		playScreen.addDeleteEventHandler(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				deleting = true;
				inserting = false;
				editing = false;

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
						
						conditionals.setText("If");
						int tempLine = selectedIndexPlayCodeList+2; //for the current statement and begin
						conditionals.setBegin(tempLine);
					}else if(newType.equals("Else")){
						Start.StartGerbil.controller.createBlocks(5,selectedIndexPlayCodeList , 0, null);
						
						conditionals.setText("Else");
						int tempLine = selectedIndexPlayCodeList+2; //for the current statement and begin
						conditionals.setBegin(tempLine);
					}else if(newType.equals("Else if")){
						Start.StartGerbil.controller.createBlocks(4,selectedIndexPlayCodeList, 0, null);
						
						conditionals.setText("Else if");
						int tempLine = selectedIndexPlayCodeList+2; //for the current statement and begin
						conditionals.setBegin(tempLine);
					}else if(newType.equals("While")){
						Start.StartGerbil.controller.createBlocks(6,selectedIndexPlayCodeList, 0, null);
						
						conditionals.setText("While");
						int tempLine = selectedIndexPlayCodeList+2; //for the current statement and begin
						conditionals.setBegin(tempLine);
					}else {//if(newType.equals("Repeat")){
						Start.StartGerbil.controller.createBlocks(7,selectedIndexPlayCodeList, 0, null);
						
						conditionals.setText("Repeat");
						int tempLine = selectedIndexPlayCodeList+2; //for the current statement and begin
						conditionals.setBegin(tempLine);
					}
					
					conditionals.show();
					playScreen.hide();
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
				String funcName = (String) playScreen.userFunctionsDD.getSelectedItem();
				Start.StartGerbil.controller.createBlocks(8,Play.playcodeList.getSelectedIndex(),1, null);
				Start.StartGerbil.controller.createBlocks('e',Play.playcodeList.getSelectedIndex(),1,funcName);
				playScreen.refreshCodeList();
			}	
		});


		playScreen.addCodeListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if(deleting == true){
					selectedIndexPlayCodeList = Play.playcodeList.getSelectedIndex();
					Start.StartGerbil.controller.deleteBlock(selectedIndexPlayCodeList);
					
					Play.refreshCodeList(); // refreshes the code list in Play screen
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
	
	
	public void addConditionalsEventHandlers(){
		conditionals.addOkEventHandler(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				int begin = conditionals.getBegin();
				int numLines = conditionals.getEndLineNumber();
				String cond = conditionals.getCond();

				Start.StartGerbil.controller.createBlocks('e', begin, numLines, cond);

				userFunction.show();
				playScreen.hide();
			}	
		});
		
		conditionals.addCancelEventHandler(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				Start.StartGerbil.controller.createBlocks('c', 0, 0, null);


				userFunction.show();
				playScreen.hide();
			}	
		});

		conditionals.addMoveEventHandler(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				
			}	
		});
		
		conditionals.addEatEventHandler(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				 
				
			}	
		});
		
		conditionals.addTurnLeftEventHandler(new ActionListener() {
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
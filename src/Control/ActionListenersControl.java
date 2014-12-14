package Control;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import Model.Block;
import Model.Game;
import Model.Gerbil;
import Start.StartGerbil;
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
	static ErrorDialog errorDialogRun;
	int selectedIndexPlayScreen; /*Code list in Play Screen*/
	static int selectedIndexPlayCodeList;
	static int funcListSelect;
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
		errorDialogRun = new ErrorDialog();
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
		initBooleans();
		main.show();
		//userFunction.show();
	}

	private void initGrid() {
		Play.setNewGrid(Start.StartGerbil.controller.gamePlaying.getGrid().getGridRepresentation());
		Play.setGerbilLocation(Start.StartGerbil.controller.gamePlaying.getGerbil().getY(), Start.StartGerbil.controller.gamePlaying.getGerbil().getX());
	}
	
	private void initBooleans(){
		 selectedCreateFunctionFirst = false;
		 
		 inserting = false;
		 deleting = false;
		 editing = false;
		 stop = false;
		 play = false;

		 deleteCurrGame = false;
	}

	/**
	 * Sets up event handlers for each screen
	 */
	private void initEventHandlers() {
		addMainEventHandlers();
		addNewGameEventHandlers();
		addPlayOptionsEventHandlers();
		addInstructionsEventHandlers();
		addErrorDialogRunEventHandlers();
		addErrorDialogEventHandlers();
		addPlayEventHandlers();
		addUserFunctionEventHandlers();
		addOkYesDialogEventHandlers();
		addSavedGamesEventHandlers();
		addConditionalsEventHandlers();
		addDeleteFunctionEventHandlers();
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
		
		conditionals.addFunctionsEventHandler(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if(parentScreen == 4){
					String cond = (String)conditionals.conditionalscodeList.getSelectedValue();
					int begin = conditionals.getBegin()-2;
					
					Start.StartGerbil.controller.createBlocks(8,begin,1,null);
					Start.StartGerbil.controller.createBlocks('e', begin, 1, cond);
					
					playScreen.refreshCodeList();
					
				}else if(parentScreen == 7){
					String cond = (String)conditionals.conditionalscodeList.getSelectedValue();
					int begin = conditionals.getBegin()-2;
					
					Start.StartGerbil.controller.createFunctionBlocks(8,begin,1,null);
					Start.StartGerbil.controller.createFunctionBlocks('e', begin, 1, cond);
					
					userFunction.refreshCodeList();
				}
	
			}
		});
		
		conditionals.addOkEventHandler(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				int begin = conditionals.getBegin()-2;
				int numLines = conditionals.getEndLineNumber()+2;
				String cond = conditionals.getCond();

				if(parentScreen == 4){
					Start.StartGerbil.controller.createBlocks('e', begin, numLines, cond);
					playScreen.refreshCodeList();
				}else if(parentScreen == 7){
					Start.StartGerbil.controller.createFunctionBlocks('e', begin, numLines, cond);
					userFunction.refreshCodeList();
				}
		
				conditionals.hide();	
				showParent();
			}	
		});

		conditionals.addCancelEventHandler(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(parentScreen == 4){
					Start.StartGerbil.controller.createBlocks('c', 0, 0, null);
				}else if(parentScreen == 7){
					Start.StartGerbil.controller.createFunctionBlocks('c', 0, 0, null);	
				}
				conditionals.hide();	
				showParent();
			}	
		});

		conditionals.addMoveEventHandler(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int lineSelect  = conditionals.getSelectedLineNumber()+conditionals.getBegin();
				
				if(parentScreen == 4){
					Start.StartGerbil.controller.createBlocks(2,lineSelect,1, null);
					Start.StartGerbil.controller.createBlocks('e',lineSelect,1,null);
					conditionals.refreshConditionalsJList(Start.StartGerbil.controller.getUnFinIns());					
										
				}else if(parentScreen == 7){
		
					Start.StartGerbil.controller.createFunctionBlocks(2,lineSelect,1, null);
					Start.StartGerbil.controller.createFunctionBlocks('e',lineSelect,1,null);
					conditionals.refreshConditionalsJList(Start.StartGerbil.controller.FunctionUnFin());
				}
			}	
		});

		conditionals.addEatEventHandler(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int lineSelect  = conditionals.getSelectedLineNumber()+conditionals.getBegin();
				Start.StartGerbil.controller.createBlocks(0,lineSelect,1, null);
				Start.StartGerbil.controller.createBlocks('e',lineSelect,1,null);
				conditionals.refreshConditionalsJList(Start.StartGerbil.controller.getUnFinIns());
			}	
		});

		conditionals.addTurnLeftEventHandler(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int lineSelect  = conditionals.getSelectedLineNumber()+conditionals.getBegin();
				Start.StartGerbil.controller.createBlocks(1,lineSelect,1, null);
				Start.StartGerbil.controller.createBlocks('e',lineSelect,1,null);
				conditionals.refreshConditionalsJList(Start.StartGerbil.controller.getUnFinIns());
			}	
		});
	}
	
	private void addErrorDialogRunEventHandlers() {
		errorDialogRun.addOkEventHandler(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				errorDialogRun.hide();
				Play.setGridIcons();
				Start.StartGerbil.controller.resetTempGrid();
				showParent();
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
	
	private void addInstructionsEventHandlers() {
		instructionsScreen.addBackEventHandler(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				instructionsScreen.hide();
				main.show();
			}		
		});
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
	
	
	private void addNewGameEventHandlers() {
		newGame.addOkEventHandler(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String text = newGame.textF.getText(); 

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
						playScreen.refreshUserFunctions();
						playScreen.refreshCodeList();

						newGame.hide();
						playScreen.show();
						playScreen.enableCreateFunction();
					}
				}else{
					errorDialog.errorL.setText("Please enter a valid Game name");
					newGame.hide();
					errorDialog.show();
				}
				
				parentScreen = 3;
			}
		});

		newGame.addBackEventHandler(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				newGame.textF.setText("");
				newGame.hide();
				playOptions.show(); // will always be the parent...
			}		
		});
	}
	
	private void addOkYesDialogEventHandlers(){
		okNoDialog.addNoEventHandler(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				okNoDialog.hide();
				playOptions.show();
			}		
		});

		okNoDialog.addOkEventHandler(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Start.StartGerbil.controller.saveGame();

				try{
					Start.StartGerbil.backend.saveGames(Start.StartGerbil.backend.getGameList());
				}catch(Exception es){
					System.out.println("Unable to save game.");
				}

				okNoDialog.hide();
				playOptions.show();
			}		
		});
	}


	/**
	 * Add event handlers for the PlayOptions screen
	 */
	private void addPlayOptionsEventHandlers() {
		playOptions.addLoadGameEventHandler(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				parentScreen =5;
				playOptions.hide();

				//wanna update the list b4 displaying!
				savedGames.refreshGamesList();
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
				parentScreen = 5;
				
				playOptions.hide();

				main.show();
			}
		});
	}

	
	/**
	 * Add event handlers for the Play screen
	 */
	private void addPlayEventHandlers() {
		/**Button Listeners**/

		playScreen.addDeleteFunctionEventHandler(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteFunction.updateFunctionsList(StartGerbil.controller.getFunctions());
				deleteFunction.show();
				playScreen.hide();
				parentScreen = 4;
			}
		});
		
		playScreen.addMenuEventHandler(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				parentScreen = 4; 

				okNoDialog.okNoDialogL.setText("Would you like to save your current game?");
				okNoDialog.show();

				playScreen.hide();
			}	
		});

		playScreen.addPlayEventHandler(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Thread thread = new Thread() {
					public void run() {
						parentScreen=4;	
						int errortype= Start.StartGerbil.controller.runBlocks();
						ArrayList<String> instructions = Start.StartGerbil.controller.getFinalBlocks();
						Start.StartGerbil.controller.resetTempGrid();//just in case, resetting grid and gerbil object
						for(int i = 0; i < instructions.size(); i++) {
							if(instructions.get(i).equals("Turn Left")) {
								Start.StartGerbil.controller.turnLeft(Start.StartGerbil.controller.getTempGerbil());
								playScreen.showTurnLeft(Start.StartGerbil.controller.getTempGerbil().getCompass(), Start.StartGerbil.controller.getTempGerbil().getX(), Start.StartGerbil.controller.getTempGerbil().getY());
							}
							else if(instructions.get(i).equals("Move Forward")) {
								int currX = Start.StartGerbil.controller.getTempGerbil().getX();
								int currY = Start.StartGerbil.controller.getTempGerbil().getY();
								char oldGridSpotType = Start.StartGerbil.controller.tempgrid[currY][currX];
								Start.StartGerbil.controller.moveForward(Start.StartGerbil.controller.getTempGerbil());
								playScreen.showMove(currX, currY, Start.StartGerbil.controller.getTempGerbil().getX(), Start.StartGerbil.controller.getTempGerbil().getY(), Start.StartGerbil.controller.getTempGerbil().getCompass(), oldGridSpotType);
							}
							else if(instructions.get(i).equals("Eat")) {
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
							errorDialogRun.errorL.setText("Cannot Eat: there is no food at square");
							errorDialogRun.show();
							parentScreen = 4; 
						}else if(errortype==2){
							//ERROR: insert Dialogue BoxCannot Move Forward bc there is WALL
							errorDialogRun.errorL.setText("Cannot Move Forward: there is a wall ahead");
							errorDialogRun.show();
							parentScreen = 4; 
						}else if(errortype==3){
							//miscellaneous error, could not compile code(this shouldn't happen)
						}else if(errortype==4){
							//ERROR: insert Dialogue BoxDid not reach water/goal
							errorDialogRun.errorL.setText("Did not reach water, Try Again!");
							errorDialogRun.show();
							parentScreen = 4; 
						}else if(errortype==-1){
							//parsing error(this shouldn't happen)
						}else if(errortype==-2){
							//ERROR: insert Dialogue BoxInfiniteLoop was created, cannot run code
							//this does not run/animate the gerbil
							errorDialogRun.errorL.setText("Infinite Loop was created, please edit your code");
							errorDialogRun.show();
							parentScreen = 4; 
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
				
				Play.playcodeList.setSelectedIndex(Play.playcodeList.getModel().getSize()-1);
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
				Start.StartGerbil.controller.saveGame();

				try{
					Start.StartGerbil.backend.saveGames(Start.StartGerbil.backend.getGameList());
				}catch(Exception es){
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
					int lineS = Play.playcodeList.getSelectedIndex();
					Block bTemp = Start.StartGerbil.controller.getBlockByLineMain(lineS);
					if(lineS ==Play.playcodeList.getModel().getSize()-1){ //last line => keep the insert line as last line
						selectedIndexPlayCodeList = lineS;
					}else if(bTemp==null){ //if null then nothing inside array so set the selected line to 0
						selectedIndexPlayCodeList = 0;
					}else{ //get the block's line begin
						selectedIndexPlayCodeList = bTemp.getlineBegin();
					}
					String newType = Play.conditionalsDD.getSelectedItem().toString();
					if(newType.equals("If")){
						parentScreen = 4;
						Start.StartGerbil.controller.createBlocks(3,selectedIndexPlayCodeList, 0, null);

						conditionals.setText("If");
						int tempLine = selectedIndexPlayCodeList+2; //for the current statement and begin
						conditionals.setBegin(tempLine);
						conditionals.show();
						playScreen.hide();
					}else if(newType.equals("Else")){
						int ret = Start.StartGerbil.controller.createBlocks(5,selectedIndexPlayCodeList , 0, null);
						if(ret == 4){ //DO NOT OPEN CONDITIONALS = show error dialog!!!!
							parentScreen = 4;
							errorDialog.errorL.setText("Error: 'If' has to exist in order to use 'Else If' or 'Else'");
							errorDialog.show();
						}else if(ret==5){//DO NOT OPEN CONDITIONALS = show error dialog!!!!
							parentScreen = 4;

							errorDialog.errorL.setText("Error: Need to insert 'Else If' or 'Else' after an 'If' statement");
							errorDialog.show();
							
						}else{
							parentScreen = 4;
							conditionals.setText("Else");
							int tempLine = selectedIndexPlayCodeList+2; //for the current statement and begin
							conditionals.setBegin(tempLine);
							conditionals.show();
							playScreen.hide();
						}
					}else if(newType.equals("Else if")){
						int ret = Start.StartGerbil.controller.createBlocks(4,selectedIndexPlayCodeList, 0, null);
						if(ret==4){//DO NOT OPEN CONDITIONALS = show error dialog!!!!
							parentScreen = 4;
							errorDialog.errorL.setText("Error: 'If' has to exist in order to use 'Else If' or 'Else'");
							errorDialog.show();
						}else if(ret == 5){//DO NOT OPEN CONDITIONALS = show error dialog!!!!
							parentScreen = 4;
							errorDialog.errorL.setText("Error: Need to insert 'Else If' or 'Else' after an 'If' statement");
							errorDialog.show();
						}else{
							parentScreen = 4;
							conditionals.setText("Else if");
							int tempLine = selectedIndexPlayCodeList+2; //for the current statement and begin
							conditionals.setBegin(tempLine);
							conditionals.show();
							playScreen.hide();
						}
					}else if(newType.equals("While")){
						parentScreen = 4;
						Start.StartGerbil.controller.createBlocks(6,selectedIndexPlayCodeList, 0, null);

						conditionals.setText("While");
						int tempLine = selectedIndexPlayCodeList+2; //for the current statement and begin
						conditionals.setBegin(tempLine);
						conditionals.show();
						playScreen.hide();
					}else {//if(newType.equals("Repeat")){
						parentScreen = 4;
						Start.StartGerbil.controller.createBlocks(7,selectedIndexPlayCodeList, 0, null);

						conditionals.setText("Repeat");
						int tempLine = selectedIndexPlayCodeList+2; //for the current statement and begin
						conditionals.setBegin(tempLine);
						conditionals.show();
						playScreen.hide();
					}
				}
				
			}	
		});

		playScreen.addGivenFunctionsListSelectionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				selectedIndexPlayCodeList = Play.playcodeList.getSelectedIndex();
				if(inserting == true){
					int lineS = Play.playcodeList.getSelectedIndex();
					Block bTemp = Start.StartGerbil.controller.getBlockByLineMain(lineS);
					if(lineS ==Play.playcodeList.getModel().getSize()-1){ //last line => keep the insert line as last line
						selectedIndexPlayCodeList = lineS;
					}else if(bTemp==null){ //if null then nothing inside array so set the selected line to 0
						selectedIndexPlayCodeList = 0;
					}else{ //get the block's line begin
						selectedIndexPlayCodeList = bTemp.getlineBegin();
					}
							
					String term = (String) playScreen.givenFunctionsDD.getSelectedItem();
					int type = -1;
					if(term.equals("Move Forward")){
						type = 2;
					}else if(term.equals("Turn Left")){
						type = 1;
					}else {//if(term.equals("Eat")){
						type = 0;

					}
					Start.StartGerbil.controller.createBlocks(type, selectedIndexPlayCodeList,0, null);
					Start.StartGerbil.controller.createBlocks('e', selectedIndexPlayCodeList,1, null);
					Play.refreshCodeList();
				}
			}});
		
		
		playScreen.addUserFunctionsListSelectionListener(new ActionListener() { // this doesn't work. - kat
			public void actionPerformed(ActionEvent arg0) {
				if(inserting==true){ 
					int lineS = Play.playcodeList.getSelectedIndex();
					Block bTemp = Start.StartGerbil.controller.getBlockByLineMain(lineS);
					if(lineS ==Play.playcodeList.getModel().getSize()-1){ //last line => keep the insert line as last line
						selectedIndexPlayCodeList = lineS;
					}else if(bTemp==null){ //if null then nothing inside array so set the selected line to 0
						selectedIndexPlayCodeList = 0;
					}else{ //get the block's line begin
						selectedIndexPlayCodeList = bTemp.getlineBegin();
					}
					String funcName = (String) playScreen.userFunctionsDD.getSelectedItem();
					Start.StartGerbil.controller.createBlocks(8,selectedIndexPlayCodeList,1, null); // should it be using createFunctions Block? - kat 
					Start.StartGerbil.controller.createBlocks('e',selectedIndexPlayCodeList,1,funcName);
					playScreen.refreshCodeList();
				}
			}	
		});

		playScreen.addCodeListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if(deleting == true){
					int lineS = Play.playcodeList.getSelectedIndex();
					Block bTemp = Start.StartGerbil.controller.getBlockByLine(lineS);
					if(lineS ==Play.playcodeList.getModel().getSize()-1){ //last line => keep the insert line as last line
						//selectedIndexPlayCodeList = lineS;

					}else if(bTemp==null){ //if null then nothing inside array so set the selected line to 0
						selectedIndexPlayCodeList = 0;
						Start.StartGerbil.controller.deleteBlock(bTemp);
						deleting = false;
						playScreen.deleteB.setBackground(Color.BLACK);
						Play.refreshCodeList(); // refreshes the code list in Play screen
					}else{ //get the block's line begin
						selectedIndexPlayCodeList = bTemp.getlineBegin();
						Start.StartGerbil.controller.deleteBlock(bTemp);
						deleting = false;
						playScreen.deleteB.setBackground(Color.BLACK);
						Play.refreshCodeList(); // refreshes the code list in Play screen
					}										
				}else if(inserting==true){
					playScreen.setSingleSelectionMode();
				}
			}	
		});
	}

	private void addSavedGamesEventHandlers(){
		savedGames.addOpenGameEventHandler(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String oGame= (String) savedGames.gamesList.getSelectedItem();
				if(oGame==null){
					parentScreen=6;
					errorDialog.errorL.setText("Must Select a Game");
					errorDialog.show();
				}else{
					Game temp = Start.StartGerbil.controller.loadGame(oGame);
					playScreen.refreshCodeList();
					playScreen.refreshUserFunctions();
					
					playScreen.show();
					savedGames.hide();
				}
			}		
		});

		savedGames.addDeleteGameEventHandler(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String dGame= (String) savedGames.gamesList.getSelectedItem();
				if(dGame==null){
					parentScreen=6;
					errorDialog.errorL.setText("Must Select a Game");
					errorDialog.show();
				}else{
					Start.StartGerbil.controller.deleteGame(dGame);
				}
			}		
		});

		savedGames.addCancelEventHandler(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				savedGames.hide();
				playOptions.show();
			}		
		});
	}

	private void addUserFunctionEventHandlers(){
		userFunction.addFunctionListListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(userFunction.addtomain){
					String funcName = (String) userFunction.userDefinedFunctions.getSelectedItem();
					Start.StartGerbil.controller.createFunctionBlocks(8, userFunction.getSelectedLineNumber(), 1, null);
					Start.StartGerbil.controller.createFunctionBlocks('e', userFunction.getSelectedLineNumber(), 1, funcName);
					userFunction.updateInstructionsList(Start.StartGerbil.controller.FunctionUnFin());
				}
				userFunction.dontAddToMain(false);	
			}
		});
	
		/*
		userFunction.addListSelectionEventHandler(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if(inserting==true){
					int lineS = Play.playcodeList.getSelectedIndex();
					Block bTemp = Start.StartGerbil.controller.getBlockByLineMain(lineS);
					if(lineS ==Play.playcodeList.getModel().getSize()-1){ //last line => keep the insert line as last line
						selectedIndexPlayCodeList = lineS;
					}else if(bTemp==null){ //if null then nothing inside array so set the selected line to 0
						selectedIndexPlayCodeList = 0;
					}else{ //get the block's line begin
						selectedIndexPlayCodeList = bTemp.getlineBegin();
					}
					String newType = Play.conditionalsDD.getSelectedItem().toString();
					if(newType.equals("If")){
						Start.StartGerbil.controller.createFunctionBlocks(3,selectedIndexPlayCodeList, 0, null);

						userFunction.setText("If");
						int tempLine = selectedIndexPlayCodeList+2; //for the current statement and begin
						conditionals.setBegin(tempLine);
						conditionals.show();
						playScreen.hide();
					}else if(newType.equals("Else")){
						int ret = Start.StartGerbil.controller.createFunctionBlocks(5,selectedIndexPlayCodeList , 0, null);
						if(ret == 4){ //DO NOT OPEN CONDITIONALS = show error dialog!!!!
							parentScreen = 4;
							errorDialog.errorL.setText("Error: 'If' has to exist in order to use 'Else If' or 'Else'");
							errorDialog.show();
						}else if(ret==5){//DO NOT OPEN CONDITIONALS = show error dialog!!!!
							parentScreen = 4;

							errorDialog.errorL.setText("Error: Need to insert 'Else If' or 'Else' after an 'If' statement");
							errorDialog.show();

						}else{
							conditionals.setText("Else");
							int tempLine = selectedIndexPlayCodeList+2; //for the current statement and begin
							conditionals.setBegin(tempLine);
							conditionals.show();
							playScreen.hide();
						}
					}else if(newType.equals("Else if")){
						int ret = Start.StartGerbil.controller.createFunctionBlocks(4,selectedIndexPlayCodeList, 0, null);
						if(ret==4){//DO NOT OPEN CONDITIONALS = show error dialog!!!!
							parentScreen = 4;
							errorDialog.errorL.setText("Error: 'If' has to exist in order to use 'Else If' or 'Else'");
							errorDialog.show();
						}else if(ret == 5){//DO NOT OPEN CONDITIONALS = show error dialog!!!!
							parentScreen = 4;
							errorDialog.errorL.setText("Error: Need to insert 'Else If' or 'Else' after an 'If' statement");
							errorDialog.show();
						}else{
							conditionals.setText("Else if");
							int tempLine = selectedIndexPlayCodeList+2; //for the current statement and begin
							conditionals.setBegin(tempLine);
							conditionals.show();
							playScreen.hide();
						}
					}else if(newType.equals("While")){
						Start.StartGerbil.controller.createFunctionBlocks(6,selectedIndexPlayCodeList, 0, null);

						conditionals.setText("While");
						int tempLine = selectedIndexPlayCodeList+2; //for the current statement and begin
						conditionals.setBegin(tempLine);
						conditionals.show();
						playScreen.hide();
					}else {//if(newType.equals("Repeat")){
						Start.StartGerbil.controller.createFunctionBlocks(7,selectedIndexPlayCodeList, 0, null);

						conditionals.setText("Repeat");
						int tempLine = selectedIndexPlayCodeList+2; //for the current statement and begin
						conditionals.setBegin(tempLine);
						conditionals.show();
						playScreen.hide();
					}
				}
			}
		});
		*/
		userFunction.addCancelEventHandler(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				parentScreen=4;
				Start.StartGerbil.controller.clearTempFunctionBlockInstructions();
				errorDialog.hide();
				showParent();
			}		
		});
		
		userFunction.addOkEventHandler(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				String functionName = userFunction.getFunctionName();
				if(!functionName.equals("")) {
					errorDialog.hide();
					showParent();
					int error= Start.StartGerbil.controller.createFunction(functionName);
					if(error==1){
						parentScreen=7;
						//error: function names can only consist of letters/numbers
						errorDialog.errorL.setText("Name must consist of letters/numbers");
						errorDialog.show();
					}else if(error==2){
						parentScreen=7;
						//error: function name already exists, choose another
						errorDialog.errorL.setText("Name already exists, enter another name");
						errorDialog.show();
					}else{
						parentScreen=4;
						userFunction.clearLabels();
						userFunction.userDefinedFunctions.addItem(functionName); // don't think this is needed?
						userFunction.hide();
						userFunction.dontAddToMain(true);
						inserting=false;
						playScreen.refreshUserFunctions();
						showParent();
					}
				}else{
					parentScreen=7;
					errorDialog.errorL.setText("You Must Enter a Function Name");
					errorDialog.show();
				}

			}		
		});
 

		userFunction.addUserFunctionComboBoxListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				int lineS = UserFunction.functionsCodeList.getSelectedIndex();
				Block bTemp = Start.StartGerbil.controller.getBlockByLineUserFunction(lineS);
				if(lineS ==UserFunction.functionsCodeList.getModel().getSize()-1){ //last line => keep the insert line as last line
					funcListSelect = lineS;
				}else if(bTemp==null){ //if null then nothing inside array so set the selected line to 0
					funcListSelect = 0;
				}else{ //get the block's line begin
					funcListSelect = bTemp.getlineBegin();
				}
				String funcName = (String) userFunction.userDefinedFunctions.getSelectedItem();
				Start.StartGerbil.controller.createFunctionBlocks(8,funcListSelect,1, null); // should it be using createFunctions Block? - kat 
				Start.StartGerbil.controller.createFunctionBlocks('e',funcListSelect,1,funcName);
				userFunction.refreshCodeList();
			}
		});
	
		
		userFunction.addRepeatEventHandler(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				int lineS = UserFunction.functionsCodeList.getSelectedIndex();
				Block bTemp = Start.StartGerbil.controller.getBlockByLineUserFunction(lineS);
				if(lineS ==UserFunction.functionsCodeList.getModel().getSize()-1){ //last line => keep the insert line as last line
					funcListSelect = lineS;
				}else if(bTemp==null){ //if null then nothing inside array so set the selected line to 0
					funcListSelect = 0;
				}else{ //get the block's line begin
					funcListSelect = bTemp.getlineBegin();
				}
				parentScreen = 7;
				Start.StartGerbil.controller.createFunctionBlocks(7,funcListSelect, 0, null);
				conditionals.setText("Repeat");
				int tempLine = funcListSelect+2; //for the current statement and begin
				conditionals.setBegin(tempLine);
				conditionals.show(); //shows the conditionals screen so when they return we get back here
				userFunction.hide();
			}
		});

		userFunction.addWhileEventHandler(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				int lineS = UserFunction.functionsCodeList.getSelectedIndex();
				Block bTemp = Start.StartGerbil.controller.getBlockByLineUserFunction(lineS);
				if(lineS ==UserFunction.functionsCodeList.getModel().getSize()-1){ //last line => keep the insert line as last line
					funcListSelect = lineS;
				}else if(bTemp==null){ //if null then nothing inside array so set the selected line to 0
					funcListSelect = 0;
				}else{ //get the block's line begin
					funcListSelect = bTemp.getlineBegin();
				}
				parentScreen = 7;
				Start.StartGerbil.controller.createFunctionBlocks(6,funcListSelect, 0, null);
				conditionals.setText("While");
				int tempLine = funcListSelect+2; //for the current statement and begin
				conditionals.setBegin(tempLine);
				conditionals.show(); //shows the conditionals screen so when they return we get back here
				userFunction.hide();
			}
		});

		userFunction.addElseEventHandler(new ActionListener(){
			
			public void actionPerformed(ActionEvent e) {
				int lineS = UserFunction.functionsCodeList.getSelectedIndex();
				Block bTemp = Start.StartGerbil.controller.getBlockByLineUserFunction(lineS);
				if(lineS ==UserFunction.functionsCodeList.getModel().getSize()-1){ //last line => keep the insert line as last line
					funcListSelect = lineS;
				}else if(bTemp==null){ //if null then nothing inside array so set the selected line to 0
					funcListSelect = 0;
				}else{ //get the block's line begin
					funcListSelect = bTemp.getlineBegin();
				}
				
				int fRet = Start.StartGerbil.controller.createFunctionBlocks(5,funcListSelect, 0, null);
				if(fRet == 4){ //error
					parentScreen = 7;
					errorDialog.errorL.setText("Error: 'If' has to exist in order to use 'Else If' or 'Else'");
					errorDialog.show();
				}else if(fRet ==5){
					parentScreen = 7;
					errorDialog.errorL.setText("Error: Need to insert 'Else If' or 'Else' after an 'If' statement");
					errorDialog.show();
				}else{
					parentScreen = 7;
					conditionals.setText("Else");
					int tempLine = funcListSelect+2; //for the current statement and begin
					conditionals.setBegin(tempLine);
					conditionals.show(); //shows the conditionals screen so when they return we get back here
					userFunction.hide();
				}
			}
		});

		userFunction.addElseIfEventHandler(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				int lineS = UserFunction.functionsCodeList.getSelectedIndex();
				Block bTemp = Start.StartGerbil.controller.getBlockByLineUserFunction(lineS);
				if(lineS ==UserFunction.functionsCodeList.getModel().getSize()-1){ //last line => keep the insert line as last line
					funcListSelect = lineS;
				}else if(bTemp==null){ //if null then nothing inside array so set the selected line to 0
					funcListSelect = 0;
				}else{ //get the block's line begin
					funcListSelect = bTemp.getlineBegin();
				}
				
				int fRet = Start.StartGerbil.controller.createFunctionBlocks(4,funcListSelect, 0, null);
				if(fRet == 4){ //error
					parentScreen = 7;
					errorDialog.errorL.setText("Error: 'If' has to exist in order to use 'Else If' or 'Else'");
					errorDialog.show();
				}else if(fRet ==5){
					parentScreen = 7;
					errorDialog.errorL.setText("Error: Need to insert 'Else If' or 'Else' after an 'If' statement");
					errorDialog.show();
				}else{
					parentScreen = 7;
					conditionals.setText("Else if");
					int tempLine = funcListSelect+2; //for the current statement and begin
					conditionals.setBegin(tempLine);
					conditionals.show(); //shows the conditionals screen so when they return we get back here
					userFunction.hide();
				}
			}
		});
		
		userFunction.addIfEventHandler(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				int lineS = UserFunction.functionsCodeList.getSelectedIndex();
				Block bTemp = Start.StartGerbil.controller.getBlockByLineUserFunction(lineS);
				if(lineS ==UserFunction.functionsCodeList.getModel().getSize()-1){ //last line => keep the insert line as last line
					funcListSelect = lineS;
				}else if(bTemp==null){ //if null then nothing inside array so set the selected line to 0
					funcListSelect = 0;
				}else{ //get the block's line begin
					funcListSelect = bTemp.getlineBegin();
				}
				parentScreen = 7;
				Start.StartGerbil.controller.createFunctionBlocks(3,funcListSelect, 0, null);
				conditionals.setText("If");
				int tempLine = funcListSelect+2; //for the current statement and begin
				conditionals.setBegin(tempLine);
				conditionals.show(); //shows the conditionals screen so when they return we get back here
				userFunction.hide();
			}});

		userFunction.addMoveAheadEventHandler(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				Start.StartGerbil.controller.createFunctionBlocks(2, userFunction.getSelectedLineNumber(),1, null);
				Start.StartGerbil.controller.createFunctionBlocks('e', userFunction.getSelectedLineNumber(),1, null);
				userFunction.updateInstructionsList(Start.StartGerbil.controller.FunctionUnFin());
			}});

		userFunction.addEatEventHandler(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Start.StartGerbil.controller.createFunctionBlocks(0, userFunction.getSelectedLineNumber(), 1, null);
				Start.StartGerbil.controller.createFunctionBlocks('e', userFunction.getSelectedLineNumber(), 1, null);
				userFunction.updateInstructionsList(Start.StartGerbil.controller.FunctionUnFin());
			}
		});

		userFunction.addTurnLeftEventHandler(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Start.StartGerbil.controller.createFunctionBlocks(1,userFunction.getSelectedLineNumber(),1, null);
				Start.StartGerbil.controller.createFunctionBlocks('e',userFunction.getSelectedLineNumber(),1, null);
				userFunction.updateInstructionsList(Start.StartGerbil.controller.FunctionUnFin());

		}});
	}

	private void addDeleteFunctionEventHandlers() {
		
		deleteFunction.addDeleteEventHandler(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String function = deleteFunction.getSelectedFunction();
				if(function != null) {
					StartGerbil.controller.deleteFunction(function);	
					deleteFunction.updateFunctionsList(StartGerbil.controller.getFunctions());
					deleteFunction.setNewSelection();
				}
			}
		});
		
		deleteFunction.addDoneEventHandler(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showParent();
				deleteFunction.hide();
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
	private  void showParent(){ 
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
package Control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import View.*;

public class ActionListenersControl {
	
	Main main; 
	Play play;
	PlayOptions playOptions;
	Instructions instructions;
	UserFunction userFunction;
	ErrorDialog errorDialog;
	//Finish finish;
	public static Controller controller;
	
	int selectedIndexPlayScreen; /*Code list*/

	/*ComboBoxes*/
	static int selectedIndexConditionals;
	static int selectedIndexGivenFunctions;
	static int selectedIndexUserFunctions;

	static int selectedIndexPlayCodeList;

	int selectedIndexOther;

	boolean selectedCreateFunctionFirst;

	public ActionListenersControl(){
		controller = new Controller();
		main = new Main();
		playOptions = new PlayOptions();
		instructions = new Instructions();
		userFunction = new UserFunction();
		errorDialog = new ErrorDialog();
	//	finish = new Finish();
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
				Play.clearAll();
			}	
		});

		play.addSaveEventHandler(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

			}	
		});

		play.addCreateFunctionEventHandler(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				selectedCreateFunctionFirst=true;
				userFunction.show();
				play.hide();
			}	
		});

		/**JComboBoxes**/
		play.addConditionalsListSelectionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(!selectedCreateFunctionFirst){
					selectedIndexPlayCodeList = Play.playcodeList.getSelectedIndex();

					if(Play.instructions.get(selectedIndexPlayCodeList) == "Begin"){
						errorDialog.errorL.setText("Cannot insert here");
						errorDialog.show();
					}else if(Play.beforeIsConditional()){
						Play.disableAllPlayDDButChecks();
						Play.instructions.add(selectedIndexPlayCodeList, "End");
						Play.instructions.add(selectedIndexPlayCodeList, "Begin");
						Play.instructions.add(selectedIndexPlayCodeList, Play.checksDD.getSelectedItem().toString());
						Play.refreshCodeList();
						Play.playcodeList.setSelectedIndex(Play.playcodeList.getModel().getSize()-2);
						Play.enableAllPlayDD();
					}else{
						Play.enableAllPlayDD();
						Play.instructions.add(selectedIndexPlayCodeList, "End");
						Play.instructions.add(selectedIndexPlayCodeList, "Begin");
						Play.instructions.add(selectedIndexPlayCodeList, Play.conditionalsDD.getSelectedItem().toString());
						Play.refreshCodeList();
						Play.playcodeList.setSelectedIndex(Play.playcodeList.getModel().getSize()-2);
					}
				}else{
					Play.enableAllPlayDD();
					selectedCreateFunctionFirst= false;
				}
			}	
		});

		play.addGivenFunctionsListSelectionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(!selectedCreateFunctionFirst){
					selectedIndexPlayCodeList = Play.playcodeList.getSelectedIndex();

					if(Play.instructions.get(selectedIndexPlayCodeList) == "Begin"){
						errorDialog.errorL.setText("Cannot insert here");
						errorDialog.show();
					}else if(Play.beforeIsConditional()){
						Play.disableAllPlayDDButChecks();
					}else{
						Play.enableAllPlayDD();
						Play.instructions.add(selectedIndexPlayCodeList, Play.givenFunctionsDD.getSelectedItem().toString());
						Play.refreshCodeList();
						Play.playcodeList.setSelectedIndex(Play.playcodeList.getModel().getSize()-2);
					
					}
				}else{
					Play.enableAllPlayDD();
					selectedCreateFunctionFirst= false;
				}
			}	
		});

		play.addUserFunctionsListSelectionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(!selectedCreateFunctionFirst){
					selectedIndexPlayCodeList = Play.playcodeList.getSelectedIndex();

					//selectedIndexPlayCodeList = Play.playcodeList.getSelectedIndex()

					if(Play.instructions.get(selectedIndexPlayCodeList) == "Begin"){
						errorDialog.errorL.setText("Cannot insert here");
						errorDialog.show();
					}else if(Play.beforeIsConditional()){
						Play.disableAllPlayDDButChecks();
						Play.instructions.add(selectedIndexPlayCodeList, Play.checksDD.getSelectedItem().toString());
						Play.refreshCodeList();
						Play.playcodeList.setSelectedIndex(Play.playcodeList.getModel().getSize()-2);
						Play.enableAllPlayDD();
					}else{
						Play.enableAllPlayDD();
						Play.instructions.add(selectedIndexPlayCodeList, Play.userFunctionsDD.getSelectedItem().toString());
						Play.refreshCodeList();
						Play.playcodeList.setSelectedIndex(Play.playcodeList.getModel().getSize()-2);
					}
				}else{
					Play.enableAllPlayDD();
					selectedCreateFunctionFirst= false;
				}
			}	
		});
		
		play.addChecksFunctionsListSelectionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(!selectedCreateFunctionFirst){
	
					selectedIndexPlayCodeList = Play.playcodeList.getSelectedIndex();

					//selectedIndexPlayCodeList = Play.playcodeList.getSelectedIndex()

					if(Play.instructions.get(selectedIndexPlayCodeList) == "Begin"){
						errorDialog.errorL.setText("Cannot insert here");
						errorDialog.show();
					}else if(Play.beforeIsConditional()){
						Play.disableAllPlayDDButChecks();
						Play.instructions.add(selectedIndexPlayCodeList, Play.checksDD.getSelectedItem().toString());
						Play.refreshCodeList();
						Play.playcodeList.setSelectedIndex(Play.playcodeList.getModel().getSize()-2);
						Play.enableAllPlayDD();
					}else if(Play.conditionalSelected()){
						String temp = Play.instructions.get(selectedIndexPlayCodeList);
						Play.instructions.remove(selectedIndexPlayCodeList);
						Play.instructions.add(selectedIndexPlayCodeList,temp+" "+Play.checksDD.getSelectedItem().toString());
						Play.refreshCodeList();
						Play.playcodeList.setSelectedIndex(Play.playcodeList.getModel().getSize()-2);
						Play.enableAllPlayDD();
					}else{					
						Play.enableAllPlayDD();
						Play.instructions.add(selectedIndexPlayCodeList, Play.checksDD.getSelectedItem().toString());
						Play.refreshCodeList();
						Play.playcodeList.setSelectedIndex(Play.playcodeList.getModel().getSize()-2);
					}
				}else{
					Play.enableAllPlayDD();
					selectedCreateFunctionFirst= false;
				}
			}	
		});
		
		play.addNumsFunctionsListSelectionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(!selectedCreateFunctionFirst){
					selectedIndexPlayCodeList = Play.playcodeList.getSelectedIndex();

					//selectedIndexPlayCodeList = Play.playcodeList.getSelectedIndex()

					if(Play.instructions.get(selectedIndexPlayCodeList) == "Begin"){
						errorDialog.errorL.setText("Cannot insert here");
						errorDialog.show();
					}else if(Play.beforeIsConditional()){
						Play.disableAllPlayDDButChecks();
						Play.instructions.add(selectedIndexPlayCodeList, Play.checksDD.getSelectedItem().toString());
						Play.refreshCodeList();
						Play.playcodeList.setSelectedIndex(Play.playcodeList.getModel().getSize()-2);
						Play.enableAllPlayDD();
					}else if(Play.conditionalSelected()){
						String temp = Play.instructions.get(selectedIndexPlayCodeList);
						Play.instructions.remove(selectedIndexPlayCodeList);
						Play.instructions.add(selectedIndexPlayCodeList,temp+" "+Play.numsDD.getSelectedItem().toString());
						Play.refreshCodeList();
						Play.playcodeList.setSelectedIndex(Play.playcodeList.getModel().getSize()-2);
						Play.enableAllPlayDD();
					}else{
						Play.enableAllPlayDD();
						Play.instructions.add(selectedIndexPlayCodeList, Play.numsDD.getSelectedItem().toString());
						Play.refreshCodeList();
						Play.playcodeList.setSelectedIndex(Play.playcodeList.getModel().getSize()-2);
					}
				}else{
					Play.enableAllPlayDD();
					selectedCreateFunctionFirst= false;
				}
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
		
		userFunction.addTurnLeftEventHandler(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
					UserFunction.instructions.add(UserFunction.functionsCodeList.getSelectedIndex(),"Turn Left");
					UserFunction.refreshCodeList();
					UserFunction.functionsCodeList.setSelectedIndex(UserFunction.functionsCodeList.getModel().getSize()-2);
				
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
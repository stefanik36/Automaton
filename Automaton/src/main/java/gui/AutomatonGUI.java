package gui;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import application.Automaton;
import application.Automaton2Dim;
import application.ElementaryCellAutomaton;
import application.GameOfLife;
import application.LangtonsAnt;
import application.WireWorld;
import coordinates.CellCoordinates;
import coordinates.PointPosition;
import exceptions.InvalidCellCoordinatesInstanceException;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import neighborhood.CellNeighborhood;
import neighborhood.MoorNeighborhood;
import neighborhood.VonNeumanNeighborhood;
import states.AntState;
import states.BinaryState;
import states.CellStateFactory;
import states.GeneralStateFactory;
import states.LangtonCell;
import states.UniformStateFactory;
import states.WireElectronState;
 
public class AutomatonGUI extends Application {
	//STAGE//
    public static int STAGE_WIDTH;
    public static int STAGE_HEIGHT;
    public static int STAGE_BORDER_LEFT = 0;
    public static int STAGE_BORDER_RIGHT = STAGE_BORDER_LEFT;
    public static int STAGE_BORDER_TOP = STAGE_BORDER_LEFT;
    public static int STAGE_BORDER_BOTTOM = STAGE_BORDER_LEFT;
    //END//
    
    //MENU//
    public static int MENU_WIDTH=600;
    public static int MENU_HEIGHT=50;
    //END//
    
    //AUTOMATON//
    private Pane root;
    public Pane automatonPane;
	public static int AUTOMATON_WIDTH;
    public static int AUTOMATON_HEIGHT;
    //END//
    
    //CELL//
    public static int DISTANCE_TO_NEIGHBORS = 20;
	public static int EXTERNAL_CELL_RADIUS = 9;
	public static int INTERNAL_CELL_RADIUS = 7;
	//END//
	
	//ANIMATION//
	private AutomatonAnimation animation;
//	private AutomatonAnimation gameOfLIfeAnim;
	public static double animation_speed = 50;
	//END//
	
	//GAME OF LIFE//
//	public CellNeighborhood gameOfLifeCellNeighborhood;
//	public CellStateFactory gameOfLifeStateFactory;
	//END
	
	@Override
    public void start(Stage primaryStage) {
		

//		gameOfLifeStateFactory = new GeneralStateFactory(new GameOfLife(new UniformStateFactory(BinaryState.DEAD), new MoorNeighborhood(1)));
//		gameOfLifeCellNeighborhood = new MoorNeighborhood(1);
		setRootPane();
			
		
		Pane menu = setMenu();
//		automatonPane = setGameOfLife();
//		automatonPane = setLangtonsAnt();
//		automatonPane = setWireWorld();
//		automatonPane = setElementaryCellAutomaton();
//		automatonPane = new AnchorPane();
        root.getChildren().add(menu);
//       	root.getChildren().add(automatonPane);	
        
        Scene scene = setScene();
        
        primaryStage.setTitle("Automaton");
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

	private void setRootPane() {
		root = new VBox();
		root.getStyleClass().clear();
		root.getStyleClass().add("root");
	}

	public Scene setScene() {
		Scene scene = new Scene(root, STAGE_WIDTH+400, STAGE_HEIGHT);
//		Scene scene = new Scene(root);
        try {
			File file = new File("src/main/css/style.css");
			URL url = file.toURI().toURL();
			scene.getStylesheets().add(url.toExternalForm());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return scene;
	}
	
	
	private Pane setGameOfLife() {
		
		
		BorderPane borderPane = new BorderPane();
//		ToolBar toolbar = new ToolBar();
//		HBox statusbar = new HBox();
		
		Pane automatonPane = setGameOfLifePane();
		Pane controlers = setGameOfLifeControl();
		
//		borderPane.setTop(toolbar);
		borderPane.setCenter(automatonPane);
//		borderPane.setBottom(statusbar);
		borderPane.setRight(controlers);
		return borderPane;
//		Pane mainPane = new HBox();
//		
//		Pane automatonPane = setGameOfLifePane();
//		HBox controlers = setGameOfLifeControl();
//		
//		mainPane.getChildren().add(automatonPane);
//		mainPane.getChildren().add(controlers);
//		
//		return mainPane;
	}

	private Pane setGameOfLifePane() {
		Pane automatonPane = new AnchorPane();
//		CellStateFactory gameOfLifeStateFactory = new GeneralStateFactory(new GameOfLife(new UniformStateFactory(BinaryState.DEAD), new MoorNeighborhood(1)));
		CellStateFactory gameOfLifeStateFactory = new UniformStateFactory(BinaryState.DEAD);
		CellNeighborhood gameOfLifeCellNeighborhood = new MoorNeighborhood(1);
		System.out.println("cell state factory game : "+gameOfLifeStateFactory);
		Automaton game = new GameOfLife(gameOfLifeStateFactory, gameOfLifeCellNeighborhood);
//		Automaton game = new GameOfLife(gameOfLifeStateFactory, gameOfLifeCellNeighborhood);
				
		animation = new AutomatonAnimation(automatonPane, game);

//		switchToGameOfLife(automatonPane);
		EventHandler<MouseEvent> mouseHandler = new EventHandler<MouseEvent>(){
			@Override
		     public void handle(MouseEvent me) {
				int x = (int)me.getSceneX();
		     	int y = (int)me.getSceneY();
		     	
		     	try {
					CellCoordinates cellCoords = PointPosition.setCellCoordinates(x, y);
//					inputCoords.add(cellCoords);
					animation.changePressedCellState(cellCoords);

				} catch (InvalidCellCoordinatesInstanceException e) {
					e.printStackTrace();
				}
		     	
		     }
		};
		automatonPane.addEventHandler(MouseEvent.MOUSE_PRESSED, mouseHandler);

		GameOfLife.setIsPlaying(false);
		return automatonPane;
	}
	
//	private void switchToGameOfLife(Pane automatonPane){
//		Pane gameOfLife = new FlowPane();
//		gameOfLife.setPrefSize(AUTOMATON_WIDTH, AUTOMATON_HEIGHT);
//		gameOfLife.getStyleClass().clear();
//		gameOfLife.getStyleClass().add("gameOfLifeContainer");
//		HBox controlers = setGameOfLifeControl();
//		automatonPane.getChildren().add(gameOfLife);
//		automatonPane.getChildren().add(controlers);
////		return gameOfLife;
//		
//	}


	private Pane setGameOfLifeControl(){
		FlowPane controlers = new FlowPane();
	    controlers.setPadding(new Insets(5, 0, 5, 0));
	    controlers.setVgap(4);
	    controlers.setHgap(4);
//	    controlers.setPrefWrapLength(300); // preferred width allows for two columns
	    controlers.setStyle("-fx-background-color: 4A068A;");

	   

//	    return controlers;
		
//	    Pane controlers = new HBox();
		controlers.setPadding(new Insets(15, 12, 15, 12));
//	    ((HBox) controlers).setSpacing(10);
		controlers.setMinSize(MENU_WIDTH, MENU_HEIGHT);
		Button btnStart = new Button();

		if(GameOfLife.isPlaying()){
	        btnStart.setText("PAUSE GAME OF LIFE");
		}
		else{
	        btnStart.setText("PLAY GAME OF LIFE");	
		}
        btnStart.setPrefSize(100, 20);
//        btnStart.setTranslateX(-((BOARD_SIZE_PIX)/2-6*BORDER_SIZE_X));
//        btnStart.setTranslateY(AUTOMATON_HEIGHT-50);
        btnStart.getStyleClass().clear();
		btnStart.getStyleClass().add("button");
		
		

//		File file = new File("css/Roboto/Roboto-Black.ttf");
//		URL url;
//		try {
//			url = file.toURI().toURL();
//			btnStart.setFont(Font.loadFont(url.toExternalForm(), 20));	
//		} 
//		catch (MalformedURLException e) {
//			e.printStackTrace();
//		}
        btnStart.setOnAction(new EventHandler<ActionEvent>() {
        	
            @Override
            public void handle(ActionEvent event) {
            	if(GameOfLife.isPlaying()){
            		animation.stop();
            		btnStart.setText("PLAY");
            		GameOfLife.setIsPlaying(false);
            	}
            	else{
                	btnStart.setText("PAUSE");
                	animation.start();
                	GameOfLife.setIsPlaying(true);
            	}
            }
        });
        
        controlers.getChildren().add(btnStart);
		return controlers;
 
	}
	
	
	private Pane setLangtonsAnt() {
		Pane automatonPane = new AnchorPane();
		CellStateFactory LangtonsAntStateFactory = new UniformStateFactory(new LangtonCell(BinaryState.DEAD, 0, AntState.NONE));
		CellNeighborhood LangtonsAntCellNeighborhood = new VonNeumanNeighborhood(1);
//		System.out.println("cell state factory game : "+gameOfLifeStateFactory);
		Automaton game = new LangtonsAnt(LangtonsAntStateFactory, LangtonsAntCellNeighborhood);
//		Automaton game = new GameOfLife(gameOfLifeStateFactory, gameOfLifeCellNeighborhood);
		
//		ArrayList<CellCoordinates> inputCoords = new ArrayList<CellCoordinates>();
		
		animation = new AutomatonAnimation(automatonPane, game);

		switchToLangtonsAnt(automatonPane);
		EventHandler<MouseEvent> mouseHandler = new EventHandler<MouseEvent>(){
			@Override
		     public void handle(MouseEvent me) {
				int x = (int)me.getSceneX();
		     	int y = (int)me.getSceneY();
		     	
		     	try {
					CellCoordinates cellCoords = PointPosition.setCellCoordinates(x, y);
//					inputCoords.add(cellCoords);
					animation.changePressedCellState(cellCoords);

				} catch (InvalidCellCoordinatesInstanceException e) {
					e.printStackTrace();
				}
		     	
		     }
		};
		automatonPane.addEventHandler(MouseEvent.MOUSE_PRESSED, mouseHandler);
//		animation.start();
		HBox controlers = setLangtonsAntControl();
//		automatonPane.getChildren().add(gameOfLifePane);
		automatonPane.getChildren().add(controlers);
//		Pane gameOfLife = switchToGameOfLife(automatonPane);
		LangtonsAnt.setIsPlaying(false);
		
		return automatonPane;

	}
	private void switchToLangtonsAnt(Pane automatonPane){
		Pane langtonsAntPane = new FlowPane();
		langtonsAntPane.setPrefSize(AUTOMATON_WIDTH, AUTOMATON_HEIGHT);
		langtonsAntPane.getStyleClass().clear();
		langtonsAntPane.getStyleClass().add("LangtonsAntContainer");
		HBox controlers = setLangtonsAntControl();
		automatonPane.getChildren().add(langtonsAntPane);
		automatonPane.getChildren().add(controlers);
//		return gameOfLife;
		
	}

	private HBox setLangtonsAntControl(){
		HBox controlers = new HBox();
		controlers.setMinSize(MENU_WIDTH, MENU_HEIGHT);
		Button btnStart = new Button();

		if(LangtonsAnt.isPlaying()){
	        btnStart.setText("PAUSE");
		}
		else{
	        btnStart.setText("PLAY");	
		}
        btnStart.setPrefSize(100, 20);
//        btnStart.setTranslateX(-((BOARD_SIZE_PIX)/2-6*BORDER_SIZE_X));
        btnStart.setTranslateY(AUTOMATON_HEIGHT-50);
        btnStart.getStyleClass().clear();
		btnStart.getStyleClass().add("button");
		
		

//		File file = new File("css/Roboto/Roboto-Black.ttf");
//		URL url;
//		try {
//			url = file.toURI().toURL();
//			btnStart.setFont(Font.loadFont(url.toExternalForm(), 20));	
//		} 
//		catch (MalformedURLException e) {
//			e.printStackTrace();
//		}
        btnStart.setOnAction(new EventHandler<ActionEvent>() {
        	
            @Override
            public void handle(ActionEvent event) {
            	if(LangtonsAnt.isPlaying()){
            		animation.stop();
            		btnStart.setText("PLAY LANGTON'S ANT");
            		GameOfLife.setIsPlaying(false);
            	}
            	else{
                	btnStart.setText("PAUSE LANGTON'S ANT");
                	animation.start();
                	LangtonsAnt.setIsPlaying(true);
            	}
            }
        });
        
        controlers.getChildren().add(btnStart);
		return controlers;
 
	}
	

	
	private Pane setWireWorld() {
		Pane automatonPane = new AnchorPane();
//		CellStateFactory gameOfLifeStateFactory = new GeneralStateFactory(new GameOfLife(new UniformStateFactory(BinaryState.DEAD), new MoorNeighborhood(1)));
		CellStateFactory WireWorldStateFactory = new UniformStateFactory(WireElectronState.VOID);
		CellNeighborhood WireWorldCellNeighborhood = new MoorNeighborhood(1);
//		System.out.println("cell state factory game : "+gameOfLifeStateFactory);
		Automaton game = new WireWorld(WireWorldStateFactory, WireWorldCellNeighborhood);
//		Automaton game = new GameOfLife(gameOfLifeStateFactory, gameOfLifeCellNeighborhood);
		
//		ArrayList<CellCoordinates> inputCoords = new ArrayList<CellCoordinates>();
		
		animation = new AutomatonAnimation(automatonPane, game);

		switchToWireWorld(automatonPane);
		EventHandler<MouseEvent> mouseHandler = new EventHandler<MouseEvent>(){
			@Override
		     public void handle(MouseEvent me) {
				int x = (int)me.getSceneX();
		     	int y = (int)me.getSceneY();
		     	
		     	try {
					CellCoordinates cellCoords = PointPosition.setCellCoordinates(x, y);
//					inputCoords.add(cellCoords);
					animation.changePressedCellState(cellCoords);

				} catch (InvalidCellCoordinatesInstanceException e) {
					e.printStackTrace();
				}
		     	
		     }
		};
		automatonPane.addEventHandler(MouseEvent.MOUSE_PRESSED, mouseHandler);
//		animation.start();
		HBox controlers = setWireWorldControl();
//		automatonPane.getChildren().add(gameOfLifePane);
		automatonPane.getChildren().add(controlers);
//		Pane gameOfLife = switchToGameOfLife(automatonPane);
		WireWorld.setIsPlaying(false);
		
		return automatonPane;
	}
	
	private void switchToWireWorld(Pane automatonPane){
		Pane wireWorldPane = new FlowPane();
		wireWorldPane.setPrefSize(AUTOMATON_WIDTH, AUTOMATON_HEIGHT);
		wireWorldPane.getStyleClass().clear();
		wireWorldPane.getStyleClass().add("WireWorldContainer");
		HBox controlers = setWireWorldControl();
		automatonPane.getChildren().add(wireWorldPane);
		automatonPane.getChildren().add(controlers);
//		return gameOfLife;
		
	}


	private HBox setWireWorldControl(){
		HBox controlers = new HBox();
		controlers.setMinSize(MENU_WIDTH, MENU_HEIGHT);
		Button btnStart = new Button();

		if(GameOfLife.isPlaying()){
	        btnStart.setText("PAUSE");
		}
		else{
	        btnStart.setText("PLAY");	
		}
        btnStart.setPrefSize(100, 20);
//        btnStart.setTranslateX(-((BOARD_SIZE_PIX)/2-6*BORDER_SIZE_X));
        btnStart.setTranslateY(AUTOMATON_HEIGHT-50);
        btnStart.getStyleClass().clear();
		btnStart.getStyleClass().add("button");
		
		

//		File file = new File("css/Roboto/Roboto-Black.ttf");
//		URL url;
//		try {
//			url = file.toURI().toURL();
//			btnStart.setFont(Font.loadFont(url.toExternalForm(), 20));	
//		} 
//		catch (MalformedURLException e) {
//			e.printStackTrace();
//		}
        btnStart.setOnAction(new EventHandler<ActionEvent>() {
        	
            @Override
            public void handle(ActionEvent event) {
            	if(GameOfLife.isPlaying()){
            		animation.stop();
            		btnStart.setText("PLAY");
            		WireWorld.setIsPlaying(false);
            	}
            	else{
                	btnStart.setText("PAUSE");
                	animation.start();
                	WireWorld.setIsPlaying(true);
            	}
            }
        });
        
        controlers.getChildren().add(btnStart);
		return controlers;
 
	}
	
	
	
	private Pane setElementaryCellAutomaton() {
		Pane automatonPane = new AnchorPane();
//		CellStateFactory gameOfLifeStateFactory = new GeneralStateFactory(new GameOfLife(new UniformStateFactory(BinaryState.DEAD), new MoorNeighborhood(1)));
		CellStateFactory elementaryCellAutomatonStateFactory = new UniformStateFactory(BinaryState.DEAD);
		CellNeighborhood elementaryCellAutomatonCellNeighborhood = new MoorNeighborhood(1);
//		System.out.println("cell state factory game : "+gameOfLifeStateFactory);
		Automaton game = new ElementaryCellAutomaton(elementaryCellAutomatonStateFactory, elementaryCellAutomatonCellNeighborhood);
//		Automaton game = new GameOfLife(gameOfLifeStateFactory, gameOfLifeCellNeighborhood);
		
//		ArrayList<CellCoordinates> inputCoords = new ArrayList<CellCoordinates>();
		
		animation = new AutomatonAnimation(automatonPane, game);

		switchToElementaryCellAutomaton(automatonPane);
		EventHandler<MouseEvent> mousePressedHandler = new EventHandler<MouseEvent>(){
			@Override
		     public void handle(MouseEvent me) {
				int x = (int)me.getSceneX();
		     	int y = (int)me.getSceneY();
		     	
		     	try {
					CellCoordinates cellCoords = PointPosition.setCellCoordinates1D(x, y);
					
//					inputCoords.add(cellCoords);
//					System.out.println(inputCoords);
					animation.changePressedCellState(cellCoords);

				} catch (InvalidCellCoordinatesInstanceException e) {
					e.printStackTrace();
				}
		     	
		     }
		};
		
		automatonPane.addEventHandler(MouseEvent.MOUSE_PRESSED, mousePressedHandler);
//		animation.start();
		HBox controlers = setElementaryCellAutomatonControl();
//		automatonPane.getChildren().add(gameOfLifePane);
		automatonPane.getChildren().add(controlers);
//		Pane gameOfLife = switchToGameOfLife(automatonPane);
		ElementaryCellAutomaton.setIsPlaying(false);
		
		return automatonPane;
	}
	
	private void switchToElementaryCellAutomaton(Pane automatonPane){
		Pane elementaryCellAutomatonPane = new FlowPane();
		elementaryCellAutomatonPane.setPrefSize(AUTOMATON_WIDTH, AUTOMATON_HEIGHT);
		elementaryCellAutomatonPane.getStyleClass().clear();
		elementaryCellAutomatonPane.getStyleClass().add("ElementaryCellAutomatonContainer");
		HBox controlers = setWireWorldControl();
		automatonPane.getChildren().add(elementaryCellAutomatonPane);
		automatonPane.getChildren().add(controlers);
//		return gameOfLife;
		
	}


	private HBox setElementaryCellAutomatonControl(){
		HBox controlers = new HBox();
		controlers.setMinSize(MENU_WIDTH, MENU_HEIGHT);
		Button btnStart = new Button();

		if(GameOfLife.isPlaying()){
	        btnStart.setText("PAUSE");
		}
		else{
	        btnStart.setText("PLAY");	
		}
        btnStart.setPrefSize(100, 20);
//        btnStart.setTranslateX(-((BOARD_SIZE_PIX)/2-6*BORDER_SIZE_X));
        btnStart.setTranslateY(AUTOMATON_HEIGHT-50);
        btnStart.getStyleClass().clear();
		btnStart.getStyleClass().add("button");
		
		

//		File file = new File("css/Roboto/Roboto-Black.ttf");
//		URL url;
//		try {
//			url = file.toURI().toURL();
//			btnStart.setFont(Font.loadFont(url.toExternalForm(), 20));	
//		} 
//		catch (MalformedURLException e) {
//			e.printStackTrace();
//		}
        btnStart.setOnAction(new EventHandler<ActionEvent>() {
        	
            @Override
            public void handle(ActionEvent event) {
            	if(ElementaryCellAutomaton.isPlaying()){
            		animation.stop();
            		btnStart.setText("PLAY");
            		ElementaryCellAutomaton.setIsPlaying(false);
            	}
            	else{
                	btnStart.setText("PAUSE");
                	animation.start();
                	ElementaryCellAutomaton.setIsPlaying(true);
            	}
            }
        });
        
        controlers.getChildren().add(btnStart);
		return controlers;
 
	}

	private Pane setMenu() {
		Pane stackPane = new StackPane();
		HBox menu = new HBox();
		menu.setPadding(new Insets(15, 12, 15, 12));
	    menu.setSpacing(10);
		menu.getStyleClass().clear();
		menu.getStyleClass().add("menuContainer");
//		menu.setMinSize(MENU_WIDTH, MENU_HEIGHT);
		
		Button gameOfLifeBtn = new Button();
        gameOfLifeBtn.setText("Game Of Life");
        gameOfLifeBtn.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
            	root.getChildren().remove(0);
        		automatonPane = setGameOfLife();
        		root.getChildren().add(automatonPane);
            }
        });
        
        Button langtonsAnt = new Button();
        langtonsAnt.setText("Langton's Ant");
        langtonsAnt.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {	
            	root.getChildren().remove(0);
        		automatonPane = setLangtonsAnt();
        		root.getChildren().add(automatonPane);

            }
        });
        
        Button wireworld = new Button();
        wireworld.setText("Wireworld");
        wireworld.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
            	root.getChildren().remove(0);
        		automatonPane = setWireWorld();
        		root.getChildren().add(automatonPane);
            }
        });
        
        Button elementaryCellAutomaton = new Button();
        elementaryCellAutomaton.setText("Elementary Cellurar Automaton");
        elementaryCellAutomaton.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
            	root.getChildren().remove(0);
        		automatonPane = setElementaryCellAutomaton();
        		root.getChildren().add(automatonPane);
            }
        });
        
        menu.getChildren().add(gameOfLifeBtn);
        menu.getChildren().add(langtonsAnt);
        menu.getChildren().add(wireworld);
        menu.getChildren().add(elementaryCellAutomaton);
        
        stackPane.getChildren().add(menu);
		return stackPane;
	}
 
    public static void main(String[] args) {
    	Automaton2Dim init = new GameOfLife();
    	
    	STAGE_WIDTH = init.getWidth()*DISTANCE_TO_NEIGHBORS+STAGE_BORDER_LEFT+STAGE_BORDER_RIGHT;
    	STAGE_HEIGHT = init.getHeight()*DISTANCE_TO_NEIGHBORS+STAGE_BORDER_TOP+STAGE_BORDER_BOTTOM+2*MENU_HEIGHT;
    	MENU_WIDTH = init.getWidth()*DISTANCE_TO_NEIGHBORS;
    	
    	AUTOMATON_WIDTH = init.getWidth()*DISTANCE_TO_NEIGHBORS;
    	AUTOMATON_HEIGHT = init.getHeight()*DISTANCE_TO_NEIGHBORS+MENU_HEIGHT;
    	
//    	AutomatonGUI gui = new AutomatonGUI();
//    	gui.automatonPane = gui.setGameOfLife();
        launch(args);
    }
}
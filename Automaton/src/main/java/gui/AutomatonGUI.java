package gui;

import java.awt.Color;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import application.Automaton;
import application.Automaton2Dim;
import application.CellStateFactory;
import application.GameOfLife;
import application.GeneralStateFactory;
import application.UniformStateFactory;
import coordinates.CellCoordinates;
import coordinates.Coords2D;
import coordinates.PointPosition;
import exceptions.InvalidCellCoordinatesInstanceException;
import exceptions.InvalidCellStateInstance;
import exceptions.NewStateIteratorHasNotNextException;
import javafx.animation.AnimationTimer;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import neighborhood.CellNeighborhood;
import neighborhood.MoorNeighborhood;
import neighborhood.VonNeumanNeighborhood;
import javafx.scene.Node;
 
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
    public static int AUTOMATON_WIDTH;
    public static int AUTOMATON_HEIGHT;
    //END//
    
    //CELL//
    public static int DISTANCE_TO_NEIGHBORS = 50;
	public static int EXTERNAL_CELL_RADIUS = 23;
	public static int INTERNAL_CELL_RADIUS = 13;
	//END//
	
	//ANIMATION//
	public final static long NANOS_PER_SECOND = 1000000000;
    public final static long IDEAL_FRAME_RATENS = (long)(1 / 60.0 * NANOS_PER_SECOND);
	public static double ANIMATION_SPEED = 0.001; //in secounds
	//END//
	
	@Override
    public void start(Stage primaryStage) {
		VBox root = new VBox();
		
		HBox menu = setMenu();
		Pane automaton = setGameOfLife();

        root.getChildren().add(menu);
        root.getChildren().add(automaton);
		
        Scene scene = setScene(root);
        
        primaryStage.setTitle("Automaton");
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

	public Scene setScene(VBox root) {
		Scene scene = new Scene(root, STAGE_WIDTH, STAGE_HEIGHT);
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
		Pane automaton = new AnchorPane();
		Pane gameOfLife = new FlowPane();
		gameOfLife.setPrefSize(AUTOMATON_WIDTH, AUTOMATON_HEIGHT);
		gameOfLife.getStyleClass().clear();
		gameOfLife.getStyleClass().add("gameOfLifeContainer");
		
		GameOfLife init = new GameOfLife();
		CellNeighborhood neighborsStrategy= new MoorNeighborhood(1);
		CellStateFactory cellStateFactory = new UniformStateFactory();
		Automaton game = init.newGameOfLife(cellStateFactory, neighborsStrategy);
		
		ArrayList<CellCoordinates> inputCoords = new ArrayList<CellCoordinates>();
		
		AutomatonAnimation animation = new AutomatonAnimation(inputCoords, game, automaton);

		
		EventHandler<MouseEvent> mouseHandler = new EventHandler<MouseEvent>(){
			@Override
		     public void handle(MouseEvent me) {
				int x = (int)me.getSceneX();
		     	int y = (int)me.getSceneY();
		     	
		     	try {
					CellCoordinates cellCoords = PointPosition.setCellCoordinates(x, y);
					inputCoords.add(cellCoords);
					animation.changePressedCellState(cellCoords);

				} catch (InvalidCellCoordinatesInstanceException e) {
					e.printStackTrace();
				}
		     	
		     }
		};
		automaton.addEventHandler(MouseEvent.MOUSE_PRESSED, mouseHandler);
//		animation.start();
		HBox controlers = setGameOfLifeControl(animation);
		automaton.getChildren().add(gameOfLife);
		automaton.getChildren().add(controlers);
		GameOfLife.setIsPlaying(false);
		return automaton;
	}


	private HBox setGameOfLifeControl(AutomatonAnimation animation){
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
	
	private void setLangtonsAnt(Pane automaton) {
		Pane langtonsAnt = new Pane();
		
		langtonsAnt.setPrefSize(AUTOMATON_WIDTH, AUTOMATON_HEIGHT);
		langtonsAnt.getStyleClass().clear();
		langtonsAnt.getStyleClass().add("langtonsAntContainer");

		automaton.getChildren().add(langtonsAnt);
	}
	
	private void setWireworld(Pane automaton) {
		Pane wireworld = new Pane();
		
		wireworld.setPrefSize(AUTOMATON_WIDTH, AUTOMATON_HEIGHT);
		wireworld.getStyleClass().clear();
		wireworld.getStyleClass().add("wireworldContainer");

		automaton.getChildren().add(wireworld);
	}

	private HBox setMenu() {
		HBox menu = new HBox();
		menu.getStyleClass().clear();
		menu.getStyleClass().add("menuContainer");
		menu.setMinSize(MENU_WIDTH, MENU_HEIGHT);
		Button gameOfLifeBtn = new Button();
        gameOfLifeBtn.setText("Game Of Life");
        gameOfLifeBtn.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
            	//setGameOfLife(automaton);
            }
        });
        
        Button langtonsAnt = new Button();
        langtonsAnt.setText("Langton's Ant");
        langtonsAnt.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {	
            	//setLangtonsAnt(automaton);
            }
        });
        
        Button wireworld = new Button();
        wireworld.setText("Wireworld");
        wireworld.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
            	//setWireworld(automaton);
            }
        });
        
        menu.getChildren().add(gameOfLifeBtn);
        menu.getChildren().add(langtonsAnt);
        menu.getChildren().add(wireworld);
		return menu;
	}
 
    public static void main(String[] args) {
    	Automaton2Dim init = new GameOfLife();
    	
    	STAGE_WIDTH = init.getWidth()*DISTANCE_TO_NEIGHBORS+STAGE_BORDER_LEFT+STAGE_BORDER_RIGHT;
    	System.out.println(STAGE_WIDTH);
    	STAGE_HEIGHT = init.getHeight()*DISTANCE_TO_NEIGHBORS+STAGE_BORDER_TOP+STAGE_BORDER_BOTTOM+2*MENU_HEIGHT;
    	System.out.println(STAGE_HEIGHT);
    	MENU_WIDTH = init.getWidth()*DISTANCE_TO_NEIGHBORS;
    	
    	AUTOMATON_WIDTH = init.getWidth()*DISTANCE_TO_NEIGHBORS;
    	AUTOMATON_HEIGHT = init.getHeight()*DISTANCE_TO_NEIGHBORS+MENU_HEIGHT;
        launch(args);
    }
}
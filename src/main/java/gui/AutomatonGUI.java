package gui;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import application.Automaton;
import application.Automaton2Dim;
import application.BoardType;
import application.ElementaryCellAutomaton;
import application.GameOfLife;
import application.LangtonsAnt;
import application.QuadLife;
import application.Structure.StructureType;
import application.WireWorld;
import coordinates.CellCoordinates;
import coordinates.PointPosition;
import exceptions.InvalidCellCoordinatesInstanceException;
import exceptions.InvalidGameInstance;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import neighborhood.CellNeighborhood;
import neighborhood.MoorNeighborhood;
import neighborhood.VonNeumanNeighborhood;
import states.AntState;
import states.BinaryState;
import states.CellStateFactory;
import states.LangtonCell;
import states.UniformStateFactory;
import states.WireElectronState;

public class AutomatonGUI extends Application {

	// STAGE//
	public static int STAGE_WIDTH;
	public static int STAGE_HEIGHT;
	public static int STAGE_BORDER_LEFT = 0;
	public static int STAGE_BORDER_RIGHT = STAGE_BORDER_LEFT;
	public static int STAGE_BORDER_TOP = STAGE_BORDER_LEFT;
	public static int STAGE_BORDER_BOTTOM = STAGE_BORDER_LEFT;
	// END//

	// MENU//
	public static int MENU_WIDTH = 600;
	public static int MENU_HEIGHT = 50;
	// END//

	// AUTOMATON//
	private Pane root;
	public Pane automatonPane;
	public static int AUTOMATON_WIDTH;
	public static int AUTOMATON_HEIGHT;
	// END//

	// CELL//
	public static int DISTANCE_TO_NEIGHBORS = 14;
	public static int EXTERNAL_CELL_RADIUS = 6;
	public static int INTERNAL_CELL_RADIUS = 5;
	// END//

	// ANIMATION//
	private AutomatonAnimation animation;
	// END//

	// STYLE//
	// private URL styleUrl;
	// END//

	@Override
	public void start(Stage primaryStage) {
		setRootPane();
		Pane menu = setMenu();
		root.getChildren().add(menu);
		Scene scene = setScene();

		primaryStage.setTitle("Automaton");
		primaryStage.setResizable(false);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	/**
	 * 
	 * PANES & BUTTONS
	 * 
	 */

	private void setRootPane() {
		File file = new File("src/main/css/Roboto/Roboto-Black.ttf");
//		try {
//			// styleUrl = file.toURI().toURL();
//		} catch (MalformedURLException e) {
//			e.printStackTrace();
//		}
		root = new VBox();
		root.getStyleClass().clear();
		root.getStyleClass().add("root");
	}

	public Scene setScene() {
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

	private Pane setSpeedSlider() {
		FlowPane speedControler = new FlowPane();
		Slider slider = new Slider();
		slider.setMin(0.5);
		slider.setMax(50);
		slider.setValue(10);
		slider.setShowTickLabels(true);
		slider.setShowTickMarks(true);
		slider.setMajorTickUnit(50);
		slider.setMinorTickCount(5);
		slider.setBlockIncrement(2);
		slider.getStyleClass().add("slider");

		slider.valueProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
				animation.setSpeed(new_val.doubleValue());
			}
		});
		Text t = new Text("Animation Speed: ");
		// t.setFont(Font.loadFont(styleUrl.toExternalForm(), 18));
		t.getStyleClass().add("btnLabel");
		speedControler.getChildren().addAll(t, slider);
		return speedControler;
	}

	private Button setExitButton() {
		Button btnExit = new Button();
		btnExit.setText("EXIT");
		btnExit.setPrefSize(100, 20);
		btnExit.getStyleClass().clear();
		btnExit.getStyleClass().add("button");
		// btnExit.setFont(Font.loadFont(styleUrl.toExternalForm(), 20));
		btnExit.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				root.getChildren().remove(0);
				Stage stage = (Stage) root.getScene().getWindow();
				animation.stop();
				start(stage);

			}
		});
		return btnExit;
	}

	private Button setPlayPauseButton() {
		Button btnStart = new Button();
		btnStart.setText("PLAY");
		btnStart.setPrefSize(100, 20);
		btnStart.getStyleClass().clear();
		btnStart.getStyleClass().add("button");
		// btnStart.setFont(Font.loadFont(styleUrl.toExternalForm(), 20));
		btnStart.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				boolean isPlaying = Automaton.isPlaying();

				if (isPlaying) {
					animation.stop();
					btnStart.setText("PLAY");
					Automaton.setIsPlaying(false);
				} else {
					btnStart.setText("PAUSE");
					animation.start();
					Automaton.setIsPlaying(true);
				}
			}
		});
		return btnStart;
	}
	
	private Button setClearButton() {
		Button btnclc = new Button();
		btnclc.setText("CLEAR");
		btnclc.setPrefSize(100, 20);
		btnclc.getStyleClass().clear();
		btnclc.getStyleClass().add("button");
		// btnclc.setFont(Font.loadFont(styleUrl.toExternalForm(), 20));
		btnclc.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				try {
					animation.clearBoard();
				} catch (InvalidGameInstance e) {
					e.printStackTrace();
				}
			}
		});
		return btnclc;
	}

	/**
	 * 
	 * GAME OF LIFE
	 * 
	 */

	private Pane setGameOfLife() {
		BorderPane borderPane = new BorderPane();
		Pane automatonPane = setGameOfLifePane();
		borderPane.setCenter(automatonPane);
		Pane controlers;
		try {
			controlers = setGameOfLifeControl();
			borderPane.setRight(controlers);

		} catch (InvalidGameInstance e) {
			e.printStackTrace();
		}
		return borderPane;
	}

	private Pane setGameOfLifePane() {
		Pane automatonPane = new AnchorPane();
		CellStateFactory gameOfLifeStateFactory = new UniformStateFactory(BinaryState.DEAD);
		CellNeighborhood gameOfLifeCellNeighborhood = new MoorNeighborhood(1);
		Automaton game = new GameOfLife(gameOfLifeStateFactory, gameOfLifeCellNeighborhood);
		animation = new AutomatonAnimation(automatonPane, game);
		EventHandler<MouseEvent> mouseHandler = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent me) {
				int x = (int) me.getSceneX();
				int y = (int) me.getSceneY();

				try {
					CellCoordinates cellCoords = PointPosition.setCellCoordinates(x, y);
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

	private Pane setGameOfLifeControl() throws InvalidGameInstance {
		FlowPane controlers = new FlowPane();
		controlers.setPadding(new Insets(5, 0, 5, 0));
		controlers.setVgap(4);
		controlers.setHgap(4);
		controlers.setPrefWrapLength(MENU_WIDTH); 
		controlers.setPadding(new Insets(15, 12, 15, 12));
		
		Button btnStart = setPlayPauseButton();
		Button btnExit = setExitButton();
		Button btnQL = setQuadLifeButton();
		Pane btnRule = setRuleButtons();
		Pane btnNeigh = setNeighborhoodButton(controlers);
		Pane btnStruct = setStructuresAndBoardTypeBox();
		Pane speedSlider = setSpeedSlider();

		controlers.getChildren().add(btnExit);
		controlers.getChildren().add(btnStart);
		controlers.getChildren().add(btnQL);
		controlers.getChildren().add(btnRule);
		controlers.getChildren().add(btnNeigh);
		controlers.getChildren().add(btnStruct);
		controlers.getChildren().add(speedSlider);
		return controlers;
	}

	private Pane setStructuresAndBoardTypeBox() {
		FlowPane structuresControler = new FlowPane();
		structuresControler.setHgap(6);
		ComboBox<StructureType> comboBox = new ComboBox<StructureType>();
		if ((animation.getCurrentGame() instanceof GameOfLife) || (animation.getCurrentGame() instanceof QuadLife)) {
			comboBox.getItems().addAll(StructureType.SIMPLE, StructureType.GLIDER, StructureType.G_GUN);
		}
		else if (animation.getCurrentGame() instanceof WireWorld){
			comboBox.getItems().addAll(StructureType.SIMPLE, StructureType.EOR_GATE);
		} else {
			comboBox.getItems().addAll(StructureType.SIMPLE);
		}
		comboBox.setValue(StructureType.SIMPLE);
		comboBox.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				StructureType struct = comboBox.getValue();
				animation.changeStructureType(struct);
			}
		});

		Text t = new Text("Structures: ");
		// t.setFont(Font.loadFont(styleUrl.toExternalForm(), 18));
		t.getStyleClass().add("btnLabel");

		CheckBox checkBox = new CheckBox("Wrap Board");
		checkBox.setPrefSize(100, 20);
		checkBox.setSelected(true);
		checkBox.getStyleClass().add("checkBox");
		// checkBox.setFont(Font.loadFont(styleUrl.toExternalForm(), 10));
		checkBox.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				if (BoardType.boardWrapped) {
					BoardType.setBoardWrapped(false);
					checkBox.setSelected(false);
				} else {
					BoardType.setBoardWrapped(true);
					checkBox.setSelected(true);
				}
			}
		});

		structuresControler.getChildren().add(t);
		structuresControler.getChildren().add(comboBox);
		structuresControler.getChildren().add(checkBox);
		
		return structuresControler;
	}

	private Pane setNeighborhoodButton(Pane controlers) {
		Pane box = new VBox();
		FlowPane neighborhoodControler = new FlowPane();
		neighborhoodControler.setHgap(6);
		RadioButton rb1 = new RadioButton("Moor Neighborhood");
		RadioButton rb2 = new RadioButton("Von Neuman Neighborhood");

		ComboBox<Integer> comboBox = new ComboBox<Integer>();
		
		comboBox.getItems().addAll(1, 2, 3, 4, 5, 6);
		comboBox.setValue(1);
		rb1.getStyleClass().add("radioButton");
		rb2.getStyleClass().add("radioButton");

		// rb1.setFont(Font.loadFont(styleUrl.toExternalForm(), 14));
		rb1.setSelected(true);
		rb1.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				int range = comboBox.getValue();
				rb2.setSelected(false);
				animation.changeNeiberhood(new MoorNeighborhood(range));
				try {
					controlers.getChildren().set(3, setRuleButtons());
				} catch (InvalidGameInstance e) {
					e.printStackTrace();
				}
			}
		});

		// rb2.setFont(Font.loadFont(styleUrl.toExternalForm(), 14));
		rb2.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				int range = comboBox.getValue();
				rb1.setSelected(false);
				animation.changeNeiberhood(new VonNeumanNeighborhood(range));
				try {
					controlers.getChildren().set(3, setRuleButtons());
				} catch (InvalidGameInstance e) {
					e.printStackTrace();
				}
			}
		});
		comboBox.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				int range = comboBox.getValue();
				if (rb1.isSelected()) {
					animation.changeNeiberhood(new MoorNeighborhood(range));
				} else {
					animation.changeNeiberhood(new VonNeumanNeighborhood(range));
				}
				try {
					controlers.getChildren().set(3, setRuleButtons());
				} catch (InvalidGameInstance e) {
					e.printStackTrace();
				}
			}
		});
		box.getChildren().addAll(rb1, rb2);
		neighborhoodControler.getChildren().addAll(box, comboBox);
		return neighborhoodControler;
	}

	private Button setQuadLifeButton() {
		Button btnQL = new Button();
		btnQL.setText("Quad Life");
		btnQL.setPrefSize(150, 20);
		btnQL.getStyleClass().clear();
		btnQL.getStyleClass().add("button");
		// btnQL.setFont(Font.loadFont(styleUrl.toExternalForm(), 20));
		btnQL.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				try {
					if (animation.getCurrentGame() instanceof GameOfLife) {
						animation.changeToQuadLife();
						btnQL.setText("Game Of Life");
					} else {
						animation.changeToGameOfLife();
						btnQL.setText("Quad Life");
					}
				} catch (InvalidGameInstance e) {
					e.printStackTrace();
				}
			}
		});
		return btnQL;
	}

	private Pane setRuleButtons() throws InvalidGameInstance {
		Pane checkBoxesPanel = new FlowPane();

		List<Integer> survivors = animation.getCurrentSurvivers();
		List<Integer> comeAlive = animation.getCurrentComeAlive();
		
		Text t = new Text("Survivors: ");
		// t.setFont(Font.loadFont(styleUrl.toExternalForm(), 18));
		t.getStyleClass().add("btnLabel");
		checkBoxesPanel.getChildren().add(t);
		
		Pane p1 = createCheckBoxes(survivors, true);
		checkBoxesPanel.getChildren().add(p1);
		
		t = new Text("Come alive: ");
		t.getStyleClass().add("btnLabel");
		// t.setFont(Font.loadFont(styleUrl.toExternalForm(), 18));
		checkBoxesPanel.getChildren().add(t);
		
		Pane p2 = createCheckBoxes(comeAlive, false);
		checkBoxesPanel.getChildren().add(p2);
		
		return checkBoxesPanel;
	}

	private Pane createCheckBoxes(List<Integer> rules, boolean survivers) {
		FlowPane checkBoxesPanel = new FlowPane();
		checkBoxesPanel.setVgap(4);
		checkBoxesPanel.setHgap(2);
		
		int numberOfButtons = animation.getCurrentNeighborhood().getNumberOfNeighbors() + 1;
		for (int i = 0; i < numberOfButtons; i++) {
			CheckBox checkBox = new CheckBox(String.valueOf(i));

			checkBox.setText(String.valueOf(i));
			checkBox.setPrefSize(40, 25);
			if (rules.contains(i)) {
				checkBox.setSelected(true);
			}
			final int it = i;
			checkBox.getStyleClass().add("checkBox");
			// checkBox.setFont(Font.loadFont(styleUrl.toExternalForm(), 10));
			checkBox.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {

					try {
						if (survivers) {
							animation.setSurvivers(it);
						} else {
							animation.setComeAlive(it);
						}
					} catch (InvalidGameInstance e) {
						e.printStackTrace();
					}
				}
			});
			checkBoxesPanel.getChildren().add(checkBox);
		}
		return checkBoxesPanel;
	}

	
	/**
	 * 
	 * LANGTON'S ANT
	 * 
	 */
	private Pane setLangtonsAnt() {
		BorderPane borderPane = new BorderPane();
		Pane automatonPane = setLangtonsAntPane();
		borderPane.setCenter(automatonPane);
		Pane controlers;
		controlers = setLangtonsAntControl();
		borderPane.setRight(controlers);
		return borderPane;
	}

	private Pane setLangtonsAntPane() {
		Pane automatonPane = new AnchorPane();
		CellStateFactory stateFactory = new UniformStateFactory(new LangtonCell(BinaryState.DEAD, 0, AntState.NONE));
		CellNeighborhood cellNeighborhood = new VonNeumanNeighborhood(1);
		Automaton game = new LangtonsAnt(stateFactory, cellNeighborhood);
		animation = new AutomatonAnimation(automatonPane, game);
		EventHandler<MouseEvent> mouseHandler = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent me) {
				int x = (int) me.getSceneX();
				int y = (int) me.getSceneY();

				try {
					CellCoordinates cellCoords = PointPosition.setCellCoordinates(x, y);
					animation.changePressedCellState(cellCoords);
				} catch (InvalidCellCoordinatesInstanceException e) {
					e.printStackTrace();
				}

			}
		};
		automatonPane.addEventHandler(MouseEvent.MOUSE_PRESSED, mouseHandler);
		LangtonsAnt.setIsPlaying(false);
		return automatonPane;
	}

	private Pane setLangtonsAntControl() {
		FlowPane controlers = new FlowPane();
		controlers.setPadding(new Insets(5, 0, 5, 0));
		controlers.setVgap(4);
		controlers.setHgap(4);
		controlers.setPrefWrapLength(MENU_WIDTH);

		controlers.setPadding(new Insets(15, 12, 15, 12));
		Button btnStart = setPlayPauseButton();
		Button btnExit = setExitButton();
		Button btnClc = setClearButton();
		Pane speedSlider = setSpeedSlider();

		controlers.getChildren().add(btnExit);
		controlers.getChildren().add(btnStart);
		controlers.getChildren().add(btnClc);
		controlers.getChildren().add(speedSlider);
		return controlers;
	}

	/**
	 * 
	 * WIRE WORLD
	 * 
	 */

	private Pane setWireWorld() {
		BorderPane borderPane = new BorderPane();
		Pane automatonPane = setWireWorldPane();
		borderPane.setCenter(automatonPane);
		Pane controlers;
		controlers = setWireWorldControl();
		borderPane.setRight(controlers);
		return borderPane;
	}

	private Pane setWireWorldPane() {
		Pane automatonPane = new AnchorPane();
		CellStateFactory stateFactory = new UniformStateFactory(WireElectronState.VOID);
		CellNeighborhood cellNeighborhood = new MoorNeighborhood(1);
		Automaton game = new WireWorld(stateFactory, cellNeighborhood);
		animation = new AutomatonAnimation(automatonPane, game);
		EventHandler<MouseEvent> mouseHandler = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent me) {
				int x = (int) me.getSceneX();
				int y = (int) me.getSceneY();
				try {
					CellCoordinates cellCoords = PointPosition.setCellCoordinates(x, y);
					animation.changePressedCellState(cellCoords);
				} catch (InvalidCellCoordinatesInstanceException e) {
					e.printStackTrace();
				}
			}
		};
		automatonPane.addEventHandler(MouseEvent.MOUSE_PRESSED, mouseHandler);
		WireWorld.setIsPlaying(false);
		return automatonPane;
	}

	private Pane setWireWorldControl() {
		FlowPane controlers = new FlowPane();
		controlers.setPadding(new Insets(5, 0, 5, 0));
		controlers.setVgap(4);
		controlers.setHgap(4);
		controlers.setPrefWrapLength(MENU_WIDTH);
		controlers.setPadding(new Insets(15, 12, 15, 12));

		Button btnStart = setPlayPauseButton();
		Button btnExit = setExitButton();
		Button btnClc = setClearButton();
		Pane btnStruct = setStructuresAndBoardTypeBox();
		Pane speedSlider = setSpeedSlider();

		controlers.getChildren().add(btnExit);
		controlers.getChildren().add(btnStart);
		controlers.getChildren().add(btnClc);
		controlers.getChildren().add(btnStruct);
		controlers.getChildren().add(speedSlider);
		return controlers;
	}

	/**
	 * 
	 * 1D AUTOMATON
	 * 
	 */

	private Pane setElementaryCellAutomaton() {
		BorderPane borderPane = new BorderPane();
		Pane automatonPane = setElementaryCellAutomatonPane();
		borderPane.setCenter(automatonPane);
		Pane controlers;
		controlers = setElementaryCellAutomatonControl();
		borderPane.setRight(controlers);
		return borderPane;
	}

	private Pane setElementaryCellAutomatonPane() {
		Pane automatonPane = new AnchorPane();
		CellStateFactory stateFactory = new UniformStateFactory(BinaryState.DEAD);
		CellNeighborhood cellNeighborhood = new MoorNeighborhood(1);
		Automaton game = new ElementaryCellAutomaton(stateFactory, cellNeighborhood);
		animation = new AutomatonAnimation(automatonPane, game);
		EventHandler<MouseEvent> mouseHandler = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent me) {
				int x = (int) me.getSceneX();
				int y = (int) me.getSceneY();
				try {
					CellCoordinates cellCoords = PointPosition.setCellCoordinates1D(x, y);
					animation.changePressedCellState(cellCoords);

				} catch (InvalidCellCoordinatesInstanceException e) {
					e.printStackTrace();
				}
			}
		};
		automatonPane.addEventHandler(MouseEvent.MOUSE_PRESSED, mouseHandler);
		ElementaryCellAutomaton.setIsPlaying(false);
		return automatonPane;
	}

	private Pane setElementaryCellAutomatonControl() {
		FlowPane controlers = new FlowPane();
		controlers.setPadding(new Insets(5, 0, 5, 0));
		controlers.setVgap(4);
		controlers.setHgap(4);
		controlers.setPrefWrapLength(MENU_WIDTH);
		controlers.setPadding(new Insets(15, 12, 15, 12));

		Button btnStart = setPlayPauseButton();
		Button btnExit = setExitButton();
		Pane rule = setRuleField();
		Pane speedSlider = setSpeedSlider();

		controlers.getChildren().add(btnExit);
		controlers.getChildren().add(btnStart);
		controlers.getChildren().add(rule);
		controlers.getChildren().add(speedSlider);
		return controlers;
	}

	private Pane setRuleField() {
		FlowPane controlers = new FlowPane();
		TextField rule = new TextField();
		rule.setText("165");
		rule.setOnKeyPressed(new EventHandler<KeyEvent>() {
			
		    @Override
		    public void handle(KeyEvent keyEvent) {
		        if (keyEvent.getCode() == KeyCode.ENTER)  {
		            String text = rule.getText();
		            if (text.matches("\\d*")) {
						int value = Integer.parseInt(text);
						if ((value > 0) && (value < 256)) {
							try {
								animation.set1DRule(value);
								rule.setText(String.valueOf(value));
							} catch (InvalidGameInstance e) {
								e.printStackTrace();
							}
						} 
		            }
		        }
		    }
		});
		
		Text t = new Text("Rule: ");
		// t.setFont(Font.loadFont(styleUrl.toExternalForm(), 18));
		t.getStyleClass().add("btnLabel");
		
		controlers.getChildren().add(t);
		controlers.getChildren().add(rule);
		return controlers;
	}

	private Pane setMenu() {
		Pane stackPane = new StackPane();
		HBox menu = new HBox();
		menu.setPadding(new Insets(15, 12, 15, 12));
		menu.setSpacing(10);
		menu.getStyleClass().clear();
		menu.getStyleClass().add("menuContainer");

		Button gameOfLifeBtn = new Button();
		gameOfLifeBtn.setText("Game Of Life");
		gameOfLifeBtn.setPrefSize(200, 20);
		gameOfLifeBtn.getStyleClass().clear();
		gameOfLifeBtn.getStyleClass().add("button");
		// gameOfLifeBtn.setFont(Font.loadFont(styleUrl.toExternalForm(), 16));
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
		langtonsAnt.setPrefSize(200, 20);
		langtonsAnt.getStyleClass().clear();
		langtonsAnt.getStyleClass().add("button");
		// langtonsAnt.setFont(Font.loadFont(styleUrl.toExternalForm(), 16));
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
		wireworld.setPrefSize(200, 20);
		wireworld.getStyleClass().clear();
		wireworld.getStyleClass().add("button");
		// wireworld.setFont(Font.loadFont(styleUrl.toExternalForm(), 16));
		wireworld.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				root.getChildren().remove(0);
				automatonPane = setWireWorld();
				root.getChildren().add(automatonPane);
			}
		});

		Button elementaryCellAutomaton = new Button();
		elementaryCellAutomaton.setText("1D Automaton");
		elementaryCellAutomaton.setPrefSize(200, 20);
		elementaryCellAutomaton.getStyleClass().clear();
		elementaryCellAutomaton.getStyleClass().add("button");
		// elementaryCellAutomaton.setFont(Font.loadFont(styleUrl.toExternalForm(), 16));
		elementaryCellAutomaton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				root.getChildren().remove(0);
				automatonPane = setElementaryCellAutomaton();
				root.getChildren().add(automatonPane);
			}
		});

		Button quit = new Button();
		quit.setText("EXIT");
		quit.setPrefSize(200, 20);
		quit.getStyleClass().clear();
		quit.getStyleClass().add("button");
		// quit.setFont(Font.loadFont(styleUrl.toExternalForm(), 16));
		quit.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				root.getChildren().remove(0);
				Stage stage = (Stage) root.getScene().getWindow();
				stage.close();

			}
		});

		menu.getChildren().add(gameOfLifeBtn);
		menu.getChildren().add(langtonsAnt);
		menu.getChildren().add(wireworld);
		menu.getChildren().add(elementaryCellAutomaton);
		menu.getChildren().add(quit);
		stackPane.getChildren().add(menu);
		return stackPane;
	}

	public static void main(String[] args) {
		Automaton2Dim init = new GameOfLife();
		MENU_WIDTH = 400;
		STAGE_WIDTH = init.getWidth() * DISTANCE_TO_NEIGHBORS + STAGE_BORDER_LEFT + STAGE_BORDER_RIGHT + MENU_WIDTH;
		STAGE_HEIGHT = init.getHeight() * DISTANCE_TO_NEIGHBORS;
		AUTOMATON_WIDTH = init.getWidth() * DISTANCE_TO_NEIGHBORS;
		AUTOMATON_HEIGHT = init.getHeight() * DISTANCE_TO_NEIGHBORS + MENU_HEIGHT;
		launch(args);
	}
}
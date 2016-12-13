package gui;

import java.util.List;

import application.Automaton;
import application.ElementaryCellAutomaton;
import application.GameOfLife;
import application.LangtonsAnt;
import application.QuadLife;
import application.Structure.StructureType;
import application.WireWorld;
import coordinates.CellCoordinates;
import exceptions.InvalidCellCoordinatesInstanceException;
import exceptions.InvalidCellStateInstance;
import exceptions.InvalidGameInstance;
import exceptions.InvalidStructureTypeException;
import exceptions.NewStateIteratorHasNotNextException;
import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.layout.Pane;
import neighborhood.CellNeighborhood;
import neighborhood.MoorNeighborhood;
import neighborhood.VonNeumanNeighborhood;
import states.AntState;
import states.BinaryState;
import states.CellStateFactory;
import states.LangtonCell;
import states.UniformStateFactory;
import states.WireElectronState;

public class AutomatonAnimation extends AnimationTimer {
	private final long NANOS_PER_SECOND = 1000000000;
	private final long IDEAL_FRAME_RATENS = (long) (1 / 60.0 * NANOS_PER_SECOND);
	private final double FPS = 1; // in secounds
	private double animationSpeed;
	private long updateGraphicsEvery;

	private Pane automatonPane;
	private Automaton innerGame;

	private int maxNumberOfPaneChildrens;
	private int numberOfPaneChildrens;
	private int currentChildren;

	public AutomatonAnimation(Pane automaton, Automaton game) {
		this.automatonPane = automaton;
		this.innerGame = game;
		animationSpeed = 1;
		updateGraphicsEvery = (long) (FPS * NANOS_PER_SECOND / animationSpeed);
		setPaneParameters();
		drawCells();
	}

	private void setPaneParameters() {
		if (innerGame instanceof ElementaryCellAutomaton) {
			int height = ((ElementaryCellAutomaton) innerGame).getWidth();// this will be square
			maxNumberOfPaneChildrens = height + automatonPane.getChildren().size();
		} else {
			maxNumberOfPaneChildrens = automatonPane.getChildren().size() + 1;
		}
		numberOfPaneChildrens = automatonPane.getChildren().size();
		currentChildren = 0;

		for (int i = numberOfPaneChildrens; i < maxNumberOfPaneChildrens; i++) {
			Group g = new Group();
			automatonPane.getChildren().add(g);
		}
	}

	private long lastTime = IDEAL_FRAME_RATENS;

	@Override
	public void handle(long currentNanoTime) {
		updateGraphicsEvery = (long) (FPS * NANOS_PER_SECOND / animationSpeed);
		long nanosElapsed = currentNanoTime - lastTime;
		if (nanosElapsed < updateGraphicsEvery) {
			return;
		} else {
			lastTime = currentNanoTime;
			try {
				updateAndDrawCells();
			} catch (InvalidCellStateInstance e) {
				e.printStackTrace();
			} catch (NewStateIteratorHasNotNextException e) {
				e.printStackTrace();
			} catch (InvalidCellCoordinatesInstanceException e) {
				e.printStackTrace();
			} catch (InvalidGameInstance e) {
				e.printStackTrace();
			}
		}
	}

	private void updateAndDrawCells() throws InvalidCellStateInstance, NewStateIteratorHasNotNextException,
			InvalidCellCoordinatesInstanceException, InvalidGameInstance {
		updateState();
		drawCells();
	}

	private void drawCells() {
		currentChildren = (currentChildren + 1) % maxNumberOfPaneChildrens + numberOfPaneChildrens;
		Group g = innerGame.getCellsGroup();
		automatonPane.getChildren().set(currentChildren, g);
	}

	private void updateState() throws InvalidCellStateInstance, NewStateIteratorHasNotNextException,
			InvalidCellCoordinatesInstanceException, InvalidGameInstance {
		innerGame = innerGame.nextState();
	}

	public void changePressedCellState(CellCoordinates cellCoords) {
		if (cellCoords != null) {
			try {
				innerGame.insertStructure(innerGame.getStructure(cellCoords));
			} catch (InvalidGameInstance e) {
				e.printStackTrace();
			} catch (InvalidCellStateInstance e) {
				e.printStackTrace();
			} catch (InvalidStructureTypeException e) {
				e.printStackTrace();
			}
			drawCells();
		}
	}

	public List<Integer> getCurrentSurvivers() throws InvalidGameInstance {
		if (innerGame instanceof GameOfLife) {
			return ((GameOfLife) innerGame).getSurvivors();
		} else if (innerGame instanceof QuadLife) {
			return ((QuadLife) innerGame).getSurvivors();
		}
		throw new InvalidGameInstance("The game should be Game Of Life or Quad Life.");
	}

	public List<Integer> getCurrentComeAlive() throws InvalidGameInstance {
		if (innerGame instanceof GameOfLife) {
			return ((GameOfLife) innerGame).getComeAlive();
		} else if (innerGame instanceof QuadLife) {
			return ((QuadLife) innerGame).getComeAlive();
		}
		throw new InvalidGameInstance("The game should be Game Of Life or Quad Life.");
	}

	public void setSurvivers(int i) throws InvalidGameInstance {
		if (innerGame instanceof GameOfLife) {
			((GameOfLife) innerGame).changeSurvivor(i);
		} else if (innerGame instanceof QuadLife) {
			((QuadLife) innerGame).changeSurvivor(i);
		} else {
			throw new InvalidGameInstance("The game should be Game Of Life or Quad Life.");
		}
	}

	public void setComeAlive(int i) throws InvalidGameInstance {
		if (innerGame instanceof GameOfLife) {
			((GameOfLife) innerGame).changeComeAlive(i);
		} else if (innerGame instanceof QuadLife) {
			((QuadLife) innerGame).changeComeAlive(i);
		} else {
			throw new InvalidGameInstance("The game should be Game Of Life or Quad Life.");
		}
	}

	public void changeToQuadLife() throws InvalidGameInstance {
		if (innerGame instanceof GameOfLife) {
			Automaton quadLife = new QuadLife();
			quadLife.setNeighborhoodAndstateFactory(innerGame.getNeighborsStrategy(), innerGame.getStateFactory());
			quadLife.fillTheMap(innerGame.getStateFactory());
			quadLife.setStructure(((GameOfLife) innerGame).getStructureType());
			((QuadLife) quadLife).setSurvivors(((GameOfLife) innerGame).getSurvivors());
			((QuadLife) quadLife).setComeAlive(((GameOfLife) innerGame).getComeAlive());
			innerGame = quadLife;
			drawCells();
		} else {
			throw new InvalidGameInstance("The game should be Game Of Life.");
		}
	}

	public void changeToGameOfLife() throws InvalidGameInstance {
		if (innerGame instanceof QuadLife) {
			Automaton gameOfLife = new GameOfLife();
			gameOfLife.setNeighborhoodAndstateFactory(innerGame.getNeighborsStrategy(), innerGame.getStateFactory());
			gameOfLife.fillTheMap(innerGame.getStateFactory());
			gameOfLife.setStructure(((QuadLife) innerGame).getStructureType());
			((GameOfLife) gameOfLife).setSurvivors(((QuadLife) innerGame).getSurvivors());
			((GameOfLife) gameOfLife).setComeAlive(((QuadLife) innerGame).getComeAlive());
			innerGame = gameOfLife;
			try {
				updateAndDrawCells();
			} catch (InvalidCellStateInstance e) {
				e.printStackTrace();
			} catch (NewStateIteratorHasNotNextException e) {
				e.printStackTrace();
			} catch (InvalidCellCoordinatesInstanceException e) {
				e.printStackTrace();
			}
		} else {
			throw new InvalidGameInstance("The game should be Game Of Life.");
		}
	}

	public Automaton getCurrentGame() {
		return innerGame;
	}

	public void changeNeiberhood(CellNeighborhood neighborsStrategy) {
		innerGame.setNeighborhood(neighborsStrategy);
	}

	public CellNeighborhood getCurrentNeighborhood() {
		return innerGame.getNeighborsStrategy();
	}

	public void changeStructureType(StructureType struct) {
		innerGame.setStructure(struct);
	}

	public void setSpeed(double speed) {
		animationSpeed = speed;
	}

	public void set1DRule(int value) throws InvalidGameInstance {
		if (innerGame instanceof ElementaryCellAutomaton) {
			((ElementaryCellAutomaton) innerGame).setRule(value);
		} else {
			throw new InvalidGameInstance("The game should be Automaton 1D.");
		}
	}

	public void clearBoard() throws InvalidGameInstance {
		if (innerGame instanceof GameOfLife) {
			CellStateFactory gameOfLifeStateFactory = new UniformStateFactory(BinaryState.DEAD);
			CellNeighborhood gameOfLifeCellNeighborhood = new MoorNeighborhood(1);
			innerGame = new GameOfLife(gameOfLifeStateFactory, gameOfLifeCellNeighborhood);
		} else if (innerGame instanceof QuadLife) {
			CellStateFactory gameOfLifeStateFactory = new UniformStateFactory(BinaryState.DEAD);
			CellNeighborhood gameOfLifeCellNeighborhood = new MoorNeighborhood(1);
			innerGame = new GameOfLife(gameOfLifeStateFactory, gameOfLifeCellNeighborhood);
		} else if (innerGame instanceof LangtonsAnt) {
			CellStateFactory stateFactory = new UniformStateFactory(new LangtonCell(BinaryState.DEAD, 0, AntState.NONE));
			CellNeighborhood cellNeighborhood = new VonNeumanNeighborhood(1);
			innerGame = new LangtonsAnt(stateFactory, cellNeighborhood);
		} else if (innerGame instanceof WireWorld) {
			CellStateFactory stateFactory = new UniformStateFactory(WireElectronState.VOID);
			CellNeighborhood cellNeighborhood = new MoorNeighborhood(1);
			innerGame = new WireWorld(stateFactory, cellNeighborhood);
		} else if (innerGame instanceof ElementaryCellAutomaton) {
			CellStateFactory stateFactory = new UniformStateFactory(BinaryState.DEAD);
			CellNeighborhood cellNeighborhood = new MoorNeighborhood(1);
			innerGame = new ElementaryCellAutomaton(stateFactory, cellNeighborhood);
		} 
		else{
			throw new InvalidGameInstance("Invalid game instance.");
		}

	}

}

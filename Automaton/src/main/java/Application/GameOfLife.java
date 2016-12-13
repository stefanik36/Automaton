package application;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

import application.Structure.StructureType;
import cells.Cell;
import coordinates.CellCoordinates;
import coordinates.Coords1D;
import coordinates.Coords2D;
import exceptions.CoordinatesOutOfBoardException;
import exceptions.InvalidCellCoordinatesInstanceException;
import exceptions.InvalidCellStateFactoryException;
import exceptions.InvalidCellStateInstance;
import exceptions.InvalidGameInstance;
import exceptions.InvalidStructureTypeException;
import exceptions.UndefiniedInstanceOfCellException;
import gui.AutomatonGUI;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import neighborhood.CellNeighborhood;
import states.BinaryState;
import states.CellState;
import states.CellStateFactory;

public class GameOfLife extends Automaton2Dim {

	// ADDED//
	private List<Integer> survivors;
	private List<Integer> comeAlive;
	private Structure structureType;

	public List<Integer> getComeAlive() {
		return comeAlive;
	}

	public void setComeAlive(List<Integer> comeAlive) {
		this.comeAlive = comeAlive;
	}

	public List<Integer> getSurvivors() {
		return survivors;
	}

	public void setSurvivors(List<Integer> survivors) {
		this.survivors = survivors;
	}

	@Override
	public void setStructure(StructureType struct) {
		structureType = new Structure();
		structureType.setSType(struct);
	}

	public GameOfLife() {

	}

	public GameOfLife(CellStateFactory cellStateFactory, CellNeighborhood cellNeighboorhood) {
		setNeighborhoodAndstateFactory(cellNeighboorhood, cellStateFactory);
		fillTheMap(cellStateFactory);
		structureType = new Structure();
		structureType.setSType(StructureType.SIMPLE);
		Integer[] survivorsA = { 2, 3 };
		Integer[] comeAliveA = { 3 };
		survivors = new ArrayList<Integer>(Arrays.asList(survivorsA));
		comeAlive = new ArrayList<Integer>(Arrays.asList(comeAliveA));
	}
	// END//

	@Override
	protected Automaton newInstance(CellStateFactory stateFactory, CellNeighborhood neighborsStrategy) {

		Automaton game = new GameOfLife();
		game.setNeighborhoodAndstateFactory(neighborsStrategy, stateFactory);
		game.fillTheMap(stateFactory);
		game.setStructureType(structureType);
		((GameOfLife) game).setSurvivors(survivors);
		((GameOfLife) game).setComeAlive(comeAlive);
		return game;
	}

	@Override
	protected CellState nextCellState(CellState currentState, Set<Cell> neighborsStates)
			throws InvalidCellStateInstance {

		if (currentState instanceof BinaryState) {
			BinaryState state = (BinaryState) currentState;
			if (state.equals(BinaryState.ALIVE)) {
				return getNewState(neighborsStates, survivors);
			} else {
				return getNewState(neighborsStates, comeAlive);
			}
		} else {
			throw new InvalidCellStateInstance("Instance of cell state is invalid.");
		}

	}

	// ADDED//
	@Override
	public void fillTheMap(CellStateFactory cellStateFactory) {
		CellCoordinates coords = initialCoordinates();
		try {
			while (hasNextCoordinates(coords)) {
				coords = nextCoordinates(coords);
				putCell(coords, cellStateFactory.initialState(coords));
			}
		} catch (CoordinatesOutOfBoardException e) {
			e.printStackTrace();
		} catch (UndefiniedInstanceOfCellException e) {
			e.printStackTrace();
		} catch (InvalidCellStateFactoryException e) {
			e.printStackTrace();
		} catch (InvalidGameInstance e) {
			e.printStackTrace();
		} catch (InvalidCellCoordinatesInstanceException e) {
			e.printStackTrace();
		}
	}

	private CellState getNewState(Set<Cell> neighborsStates, List<Integer> criteria) {
		int numberOfAliveNeighbors = 0;
		for (Cell c : neighborsStates) {
			if (c.state.equals(BinaryState.ALIVE)) {
				numberOfAliveNeighbors++;
			}
		}
		if (criteria.contains(new Integer(numberOfAliveNeighbors))) {
			return BinaryState.ALIVE;
		}
		return BinaryState.DEAD;
	}

	@Override
	public Group getCellShape(Cell c) throws InvalidCellCoordinatesInstanceException {
		Group cellShape = new Group();
		if (c.coords instanceof Coords2D) {
			Coords2D c2D = (Coords2D) c.coords;
			BinaryState bState = (BinaryState) c.state;
			int x = c2D.getX() * AutomatonGUI.DISTANCE_TO_NEIGHBORS - AutomatonGUI.DISTANCE_TO_NEIGHBORS / 2;
			int y = c2D.getY() * AutomatonGUI.DISTANCE_TO_NEIGHBORS - AutomatonGUI.DISTANCE_TO_NEIGHBORS / 2;
			Circle eCircle = new Circle(x, y, AutomatonGUI.EXTERNAL_CELL_RADIUS, Color.BLACK);
			cellShape.getChildren().add(eCircle);
			if (bState.equals(BinaryState.DEAD)) {
				Circle iCircle = new Circle(x, y, AutomatonGUI.INTERNAL_CELL_RADIUS, Color.WHITE);
				cellShape.getChildren().add(iCircle);
			}
			return cellShape;
		} else if (c.coords instanceof Coords1D) {
			Coords1D c1D = (Coords1D) c.coords;
			BinaryState bState = (BinaryState) c.state;
			int x = c1D.getX() * AutomatonGUI.DISTANCE_TO_NEIGHBORS - AutomatonGUI.DISTANCE_TO_NEIGHBORS / 2;
			Circle eCircle = new Circle(x, AutomatonGUI.DISTANCE_TO_NEIGHBORS / 2, AutomatonGUI.EXTERNAL_CELL_RADIUS,
					Color.BLACK);
			cellShape.getChildren().add(eCircle);
			if (bState.equals(BinaryState.DEAD)) {
				Circle iCircle = new Circle(x, AutomatonGUI.DISTANCE_TO_NEIGHBORS / 2,
						AutomatonGUI.INTERNAL_CELL_RADIUS, Color.WHITE);
				cellShape.getChildren().add(iCircle);
			}
			return cellShape;
		} else {
			throw new InvalidCellCoordinatesInstanceException("Unknown cell coordinates instance.");
		}
	}

	@Override
	public Map<? extends CellCoordinates, ? extends CellState> getStructure(CellCoordinates cellCoords)
			throws InvalidStructureTypeException, InvalidGameInstance {
		return structureType.getStructure(cellCoords, this);
	}

	@Override
	public StructureType getStructureType() {
		return structureType.sType;
	}

	@Override
	public void setStructureType(Structure structureType) {
		this.structureType = structureType;
	}

	public void changeSurvivor(int i) {
		if (survivors.contains(new Integer(i)))
			survivors.remove(new Integer(i));
		else
			survivors.add(i);
	}

	public void changeComeAlive(int i) {
		if (comeAlive.contains(new Integer(i)))
			comeAlive.remove(new Integer(i));
		else
			comeAlive.add(i);
	}
	// END//
}

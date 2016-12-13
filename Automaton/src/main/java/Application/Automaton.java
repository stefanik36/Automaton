package application;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import application.Structure.StructureType;
import cells.Cell;
import coordinates.CellCoordinates;
import exceptions.CoordinatesOutOfBoardException;
import exceptions.InvalidCellCoordinatesInstanceException;
import exceptions.InvalidCellStateFactoryException;
import exceptions.InvalidCellStateInstance;
import exceptions.InvalidGameInstance;
import exceptions.InvalidStructureTypeException;
import exceptions.NewStateIteratorHasNotNextException;
import exceptions.UndefiniedInstanceOfCellException;
import javafx.scene.Group;
import neighborhood.CellNeighborhood;
import states.CellState;
import states.CellStateFactory;

public abstract class Automaton {

	// ADDED//
	public int neighborhood_range = 1;
	private static boolean isPlaying;
	// END//

	private Map<CellCoordinates, CellState> cells = new HashMap<CellCoordinates, CellState>();
	private CellNeighborhood neighborsStrategy;
	private CellStateFactory stateFactory;

	// ADDED//
	public static boolean isPlaying() {
		return isPlaying;
	}

	public static void setIsPlaying(boolean b) {
		isPlaying = b;
	}

	public void setNeighborhoodAndstateFactory(CellNeighborhood neighborsStrategy, CellStateFactory stateFactory) {
		this.stateFactory = stateFactory;
		setNeighborhood(neighborsStrategy);
	}

	public void setNeighborhood(CellNeighborhood neighborsStrategy) {
		this.neighborsStrategy = neighborsStrategy;
	}

	public CellStateFactory getStateFactory() {
		return stateFactory;
	}

	public CellNeighborhood getNeighborsStrategy() {
		return neighborsStrategy;
	}
	// END//

	public class CellIterator implements Iterator<Cell> {

		private CellCoordinates currentCoords;
		// ADDED//
		Iterator<Map.Entry<CellCoordinates, CellState>> it = cells.entrySet().iterator();
		// END//

		public boolean hasNext() {
			if (it.hasNext())
				return true;
			return false;
		}

		public Cell next() {
			Map.Entry<CellCoordinates, CellState> pair = it.next();
			CellState state = (CellState) pair.getValue();
			currentCoords = (CellCoordinates) pair.getKey();
			return new Cell(currentCoords, state);
		}

		public void setState(CellState newState) throws InvalidCellCoordinatesInstanceException, InvalidGameInstance {
			setCellState(currentCoords, newState);
		}

		public void reset() {
			it = cells.entrySet().iterator();
		}
	}

	// ADDED//
	public Set<CellCoordinates> cellNeighbors(CellCoordinates coords) {
		return neighborsStrategy.cellNeighborhood(coords);
	}

	public void setCellState(CellCoordinates coords, CellState newState)
			throws InvalidCellCoordinatesInstanceException, InvalidGameInstance {
		cells.put(coords, newState);
	}

	protected void putCell(CellCoordinates coords, CellState newState)
			throws InvalidCellStateFactoryException, InvalidGameInstance, InvalidCellCoordinatesInstanceException { // ---------------------
																													// add
																													// cell
																													// from
																													// game
																													// of
																													// life
																													// ------------//
		cells.put(coords, newState);
	}
	// END//

	public Automaton nextState() throws InvalidCellStateInstance, NewStateIteratorHasNotNextException,
			InvalidCellCoordinatesInstanceException, InvalidGameInstance {
		Automaton automaton = newInstance(stateFactory, neighborsStrategy);

		CellIterator newStateIterator = automaton.cellIterator();
		CellIterator thisIterator = cellIterator();

		while (thisIterator.hasNext()) {
			Cell c = thisIterator.next();
			if (newStateIterator.hasNext()) {
				newStateIterator.next();
			} else {
				throw new NewStateIteratorHasNotNextException("Iterator has not next cell.");
			}
			Set<CellCoordinates> neighbors = cellNeighbors(c.coords);
			Set<Cell> mappedNeighbors = mapCoordinates(neighbors);
			CellState newState = nextCellState(c.state, mappedNeighbors);
			newStateIterator.setState(newState);
		}

		return automaton;
	}

	public void insertStructure(Map<? extends CellCoordinates, ? extends CellState> structure)
			throws InvalidGameInstance, InvalidCellStateInstance {
		for (Entry<? extends CellCoordinates, ? extends CellState> pair : structure.entrySet()) {
			CellCoordinates coords = pair.getKey();
			CellState state = cells.get(coords);
			if (state == null) {
				break;
			}
			state = state.getOppositeState();
			cells.put(coords, state);
		}
	}

	public CellIterator cellIterator() {
		return new CellIterator();
	}

	private Set<Cell> mapCoordinates(Set<CellCoordinates> coords) {
		Set<Cell> cellSet = new HashSet<Cell>();

		for (CellCoordinates coord : coords) {
			CellState state = cells.get(coord);
			Cell c = new Cell(coord, state);
			cellSet.add(c);
		}
		return cellSet;
	}

	protected abstract Automaton newInstance(CellStateFactory cellStateFactory, CellNeighborhood cellNeighboorhood);

	protected abstract boolean hasNextCoordinates(CellCoordinates coords) throws UndefiniedInstanceOfCellException;// ----------added
																													// exception
																													// -----------//

	protected abstract CellCoordinates initialCoordinates();

	protected abstract CellCoordinates nextCoordinates(CellCoordinates coords)
			throws UndefiniedInstanceOfCellException, CoordinatesOutOfBoardException;// -----------added
																						// exception
																						// ------//

	protected abstract CellState nextCellState(CellState currentState, Set<Cell> neighborsStates)
			throws InvalidCellStateInstance;// -----------added exception
											// ------//

	// ADDED//
	public abstract Map<? extends CellCoordinates, ? extends CellState> getStructure(CellCoordinates cellCoords)
			throws InvalidStructureTypeException, InvalidGameInstance;

	public abstract StructureType getStructureType();

	public abstract void setStructureType(Structure structureType);

	public abstract void fillTheMap(CellStateFactory cellStateFactory);

	public abstract Group getCellShape(Cell c) throws InvalidCellCoordinatesInstanceException;

	public Group getCellsGroup() {
		Group cellsGroup = new Group();
		CellIterator thisIterator = cellIterator();
		while (thisIterator.hasNext()) {
			Cell c = thisIterator.next();
			try {
				cellsGroup.getChildren().add(getCellShape(c));
			} catch (InvalidCellCoordinatesInstanceException e) {
				e.printStackTrace();
			}
		}
		return cellsGroup;
	}

	public Map<CellCoordinates, CellState> GetActualCellStates() {
		return cells;
	}

	public abstract void setStructure(StructureType struct);
	// END//
}

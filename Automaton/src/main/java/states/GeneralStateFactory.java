package states;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import application.Automaton;
import application.GameOfLife;
import application.LangtonsAnt;
import application.WireWorld;
import cells.Cell;
import coordinates.CellCoordinates;
import exceptions.InvalidCellCoordinatesInstanceException;
import exceptions.InvalidGameInstance;

public class GeneralStateFactory implements CellStateFactory {

	private Map<CellCoordinates, CellState> states = new HashMap<CellCoordinates, CellState>();
	private Automaton game;

	// ADDED//
	public GeneralStateFactory(Automaton game) {
		this.game = game;
	}
	// END//
	
	@Override
	public CellState initialState(CellCoordinates coords) {
		if (game instanceof GameOfLife) {
			if (states.get(coords) == null) {
				BinaryState bState = BinaryState.DEAD;
				states.put(coords, bState);
				return bState;
			}
			CellState state = states.get(coords);
			return state;
		} else if (game instanceof LangtonsAnt) {
			if (states.get(coords) == null) {
				return new LangtonCell(BinaryState.DEAD, 0, AntState.NONE);
			}
			LangtonCell lCell = (LangtonCell) states.get(coords);
			return new LangtonCell(lCell.cellState, lCell.antId, lCell.antState);
		} else if (game instanceof WireWorld) {
			if (states.get(coords) == null) {
				return WireElectronState.VOID;
			}
			CellState state = states.get(coords);
			return state;
		} else {
			try {
				throw new InvalidGameInstance("Invalid game instance. (GeneralStateFactory)");
			} catch (InvalidGameInstance e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	// ADDED//
	public Cell changeCellState(CellCoordinates cellCoords, CellState newState)
			throws InvalidCellCoordinatesInstanceException, InvalidGameInstance {
		if (game instanceof GameOfLife) {

			for (Entry<CellCoordinates, CellState> pair : states.entrySet()) {
				if (pair.getKey().equals(cellCoords)) {
					if (newState == null) {
						if (pair.getValue().equals(BinaryState.ALIVE)) {
							pair.setValue(BinaryState.DEAD);
							return new Cell(cellCoords, BinaryState.DEAD);
						} else {
							pair.setValue(BinaryState.ALIVE);
							return new Cell(cellCoords, BinaryState.ALIVE);
						}
					} else {
						pair.setValue(newState);
					}
				}
			}
		} else if (game instanceof LangtonsAnt) {

		} else {
			throw new InvalidGameInstance("Invalid game instance (GeneralStateFacory)");
		}
		return null;
	}

	public void setStates(Map<CellCoordinates, CellState> states) {
		this.states = states;
	}
	//END//
}

package states;

import coordinates.CellCoordinates;

public interface CellStateFactory {
	
	public abstract CellState initialState(CellCoordinates coords);

}

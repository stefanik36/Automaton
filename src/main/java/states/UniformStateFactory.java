package states;

import coordinates.CellCoordinates;

public class UniformStateFactory implements CellStateFactory {

	private CellState state;

	// ADDED//
	public UniformStateFactory(CellState cellState) {
		this.state = cellState;
	}
	// END//
	
	@Override
	public CellState initialState(CellCoordinates coords) {
		return state;
	}

}
package cells;

import coordinates.CellCoordinates;
import states.CellState;

public class Cell {

	
	public CellState state;
	public CellCoordinates coords;
	
	public Cell(CellCoordinates coords, CellState state){
		this.state = state;
		this.coords = coords;
	}
	
	@Override
	public int hashCode(){
		final int prime = 31;
		int result = 1;
		result = prime * result + ((state == null) ? 0 : state.hashCode());
		result = prime * result + ((coords == null) ? 0 : coords.hashCode());
		return result;
	}
	
	
	//ADDED//
	@Override
	public String toString(){
		return "coords: "+coords+" state: "+state;
	}
	//END//
}

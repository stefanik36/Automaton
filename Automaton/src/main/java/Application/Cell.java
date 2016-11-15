package Application;

public class Cell {

	
	public CellState state;
	public CellCoordinates coords;
	
	public Cell(CellState state, CellCoordinates coords){
		this.state = state;
		this.coords = coords;
	}
}

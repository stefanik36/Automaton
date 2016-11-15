package Application;

public interface CellStateFactory {
	
	public abstract CellState initialState(CellCoordinates coords);

}

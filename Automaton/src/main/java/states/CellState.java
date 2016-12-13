package states;

public interface CellState {

	//ADDED//
	@Override
	public boolean equals(Object obj);

	public CellState getOppositeState();
	//END//
	
}

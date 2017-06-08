package states;

public enum BinaryState implements CellState {
	DEAD, ALIVE;

	@Override
	public CellState getOppositeState() {
		CellState state;
		if (this == BinaryState.ALIVE) {
			state = BinaryState.DEAD;
		} else {
			state = BinaryState.ALIVE;
		}
		return state;
	}
}

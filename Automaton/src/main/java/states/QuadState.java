package states;

public enum QuadState implements CellState {
	DEAD, RED, YELLOW, BLUE, GREEN;

	@Override
	public CellState getOppositeState() {
		CellState state;
		if (this == QuadState.RED) {
			state = QuadState.YELLOW;
		} else if (this == QuadState.YELLOW) {
			state = QuadState.BLUE;
		} else if (this == QuadState.BLUE) {
			state = QuadState.GREEN;
		} else if (this == QuadState.GREEN) {
			state = QuadState.DEAD;
		} else {
			state = QuadState.RED;
		}
		return state;
	}
}

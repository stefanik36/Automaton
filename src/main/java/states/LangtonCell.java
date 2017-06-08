package states;

import application.LangtonsAnt;

public class LangtonCell implements CellState {

	public BinaryState cellState;
	public int antId;
	public AntState antState;

	// ADDED//
	public LangtonCell(BinaryState dead, int antId, AntState none) {
		this.cellState = dead;
		this.antId = antId;
		this.antState = none;
	}

	@Override
	public CellState getOppositeState() {
		AntState newAntState;
		int newAntId;
		if (antState == AntState.NONE) {
			newAntState = AntState.NORTH;
			newAntId = LangtonsAnt.ants.size() + 1;

		} else {
			newAntState = AntState.NONE;
			newAntId = 0;
		}
		LangtonCell newState = new LangtonCell(cellState, newAntId, newAntState);
		LangtonsAnt.ants.add(this);
		return newState;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + antId;
		result = prime * result + ((antState == null) ? 0 : antState.hashCode());
		result = prime * result + ((cellState == null) ? 0 : cellState.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LangtonCell other = (LangtonCell) obj;
		if (antId != other.antId)
			return false;
		if (antState != other.antState)
			return false;
		if (cellState != other.cellState)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Langton Cell: " + cellState + " antId: " + antId + " " + antState;
	}
	//END//
}

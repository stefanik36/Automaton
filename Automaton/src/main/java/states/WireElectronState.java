package states;

public enum WireElectronState implements CellState{
	
	VOID, WIRE, ELECTRON_HEAD, ELECTRON_TAIL;

	@Override
	public CellState getOppositeState() {
		CellState state;
		if(this == WireElectronState.VOID){
			state = WireElectronState.WIRE;
		}
		else if (this == WireElectronState.WIRE){
			state = WireElectronState.ELECTRON_HEAD;
		}
		else{
			state = WireElectronState.VOID;
		}
		return state;
	}
	

}

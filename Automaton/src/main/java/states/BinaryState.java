package states;

import exceptions.InvalidCellStateInstance;

//public class BinaryState implements CellState {

	//ADDED//
//	public BState state;
//	public BinaryState(BState state) {
//		this.state = state;
//	}
//	
//	@Override
//	public boolean equals(Object obj){
//		
//		if((obj instanceof states.BinaryState) || (obj instanceof BinaryState.BState) || (obj instanceof BState) || (obj instanceof BinaryState)){
//			BinaryState newState = (BinaryState) obj;
//			if(newState.state.toString().equals(state.toString())){
//				return true;
//			}
//			return false;
//		}
//		try {
//			
//			throw new InvalidCellStateInstance("Equals cannot be executed on this parameter.");
//		} catch (InvalidCellStateInstance e) {
//			e.printStackTrace();
//		}
//		return false;
//	}
//	@Override
//	public String toString(){
//		if(state.equals(BState.ALIVE)){
//			return "BinaryState: ALIVE";
//		}
//		else{
//			return "BinaryState: DEAD";
//		}
//		
//	}
	//END//
	
	public enum BinaryState implements CellState{
		DEAD, ALIVE; 
		
		@Override
		public CellState getOppositeState(){
			CellState state;
			if(this == BinaryState.ALIVE){
				state = BinaryState.DEAD;
			}
			else{
				state = BinaryState.ALIVE;
			}
			return state;
		}
	}

//}

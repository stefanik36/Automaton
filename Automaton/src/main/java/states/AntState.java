package states;

import exceptions.InvalidCellStateInstance;

//public class AntState {
	
	//ADDED//
//	public AState state;
//	public AntState(AState state) {
//		this.state = state;
//	}
	
//	@Override
//	public boolean equals(Object obj){
//		
//		if((obj instanceof states.AntState) || (obj instanceof AntState.AState) || (obj instanceof AState) || (obj instanceof AntState)){
//			AntState newState = (AntState) obj;
//			if(newState.state.toString().equals(state.toString())){
//				return true;
//			}
//			return false;
//		}
//		try {
//			
//			throw new InvalidCellStateInstance("Equals cannot be executed on this parameter. (AntState)");
//		} catch (InvalidCellStateInstance e) {
//			e.printStackTrace();
//		}
//		return false;
//	}
//	
//	@Override
//	public String toString(){
//		
//		if(state.equals(AState.NORTH)){
//			return "AntState: NORTH";
//		}
//		else if(state.equals(AState.SOUTH)){
//			return "AntState: SOUTH";
//		}
//		else if(state.equals(AState.EAST)){
//			return "AntState: EAST";
//		}
//		else if(state.equals(AState.WEST)){
//			return "AntState: WEST";
//		}
//		else{
//			return "AntState: NONE";
//		}
//	}
//	@Override
//	public int hashCode(){
//		final int prime = 31;
//		int result = 1;
//		result = prime * result +  state.hashCode();
//		return result;
//	}
	//END//
	public enum AntState{
		NONE, NORTH, SOUTH, EAST, WEST;
	}
//}

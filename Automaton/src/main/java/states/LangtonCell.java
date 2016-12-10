package states;

import application.LangtonsAnt;

public class LangtonCell implements CellState{

	public BinaryState cellState;
	public int antId;
	public AntState antState;
	
	
	
	//ADDED//
	public LangtonCell(BinaryState dead, int antId, AntState none){
		this.cellState = dead;
		this.antId = antId;
		this.antState = none;
	}
	@Override
	public CellState getOppositeState(){
		
		if(antState == AntState.NONE){
			antState = AntState.NORTH;
			antId = LangtonsAnt.ants.size()+1;
			System.out.println("ant: "+antId);
			LangtonsAnt.ants.add(this);
		}
		else{
			antState = AntState.NONE;
			antId = 0;
			LangtonsAnt.ants.remove(this);
		}
		LangtonCell state = new LangtonCell(cellState, antId, antState);
		return state;
	}

	
	@Override
	public String toString(){
		return "Langton Cell: "+cellState+" antId: "+antId+" "+antState;
	}
	@Override
	public int hashCode(){
		final int prime = 31;
		int result = 1;
		result = prime * result +  cellState.hashCode();
		result = prime * result +  Integer.valueOf(antId).hashCode();
		result = prime * result +  antState.hashCode();
		return result;
	}
	//END//
	
}

package Application;

import java.util.Map;

public class UniformStateFactory implements CellStateFactory{

	private Map<CellCoordinates, CellState> states;
	
	@Override
	public CellState initialState(CellCoordinates coords) {
		// TODO Auto-generated method stub
		return null;
	}
	
	//ADDED//
	public void fillTheMap(){
		for(;;){
			
		}
	}
	//END//
	

}
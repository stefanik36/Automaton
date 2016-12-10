package states;

import coordinates.CellCoordinates;

public class UniformStateFactory implements CellStateFactory{

	private CellState state;
	//ADDED//
	public UniformStateFactory(CellState cellState){
		this.state = cellState;
	}
	//END//
	@Override
	public CellState initialState(CellCoordinates coords) {
		return state;
	}
	
	//ADDED//
//	public void fillTheMap(){
//		Automaton game = new GameOfLife();  //-------------------------- just GAME OF LIFE ---------------------//
//		CellCoordinates initCoords = game.initialCoordinates();
//		try {
//			while(game.hasNextCoordinates(initCoords)){
//				game.
//				initialState(coords)
//			}
//		} catch (UndefiniedInstanceOfCellException e) {
//			
//			e.printStackTrace();
//		}
//	}
	//END//
	

}
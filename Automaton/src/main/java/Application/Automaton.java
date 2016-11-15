package Application;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;


import Exceptions.CoordinatesOutOfBoardException;
import Exceptions.UndefiniedInstanceOfCellException;

public abstract class Automaton {
	
	//ADDED//
	public int neighborhood_range = 1;
	public static final int DISTANCE_TO_NEIGHBORS = 10;
	//END//

	private Map<CellCoordinates, CellState> cells = new HashMap<CellCoordinates, CellState> ();
	private CellNeighborhood neighborsStrategy = new VonNeumanNeighborhood(neighborhood_range);//----------------added range parameter -----------//
	private CellStateFactory stateFactory = new GeneralStateFactory();
	
	
	
	public class CellIterator implements Iterator<Cell> {    //-------------------INNER CLASS -----------------------//
		
		private CellCoordinates currentState;
		
		//ADDED//
		private Set set = cells.entrySet();
		private Iterator i = set.iterator();
		//END//
		
		public boolean hasNext(){
			
			if(i.hasNext()){
				return true;
			}
			return false;
		}
		public Cell next(){
			
			return new Cell(null, null);
		}
		public void setState(CellState cellState){
			
		}
	}
	//ADDED//
	public Set<CellCoordinates> cellNeighbors(CellCoordinates coords){
		return neighborsStrategy.cellNeighborhood(coords);
		
	}
	//END//
	
	public Automaton nextState(){
		Automaton automaton = newInstance(stateFactory, neighborsStrategy);
		GameOfLife gameOfLife = (GameOfLife) automaton;
		
		CellIterator newStateIterator = cellIterator();
		
		while(newStateIterator.hasNext()){
			Cell cell= newStateIterator.next();
			Set<CellCoordinates> neighbors = cellNeighbors(cell.coords);
			
			
			
		}
		
		return null;
	}
	public void insertStructure(Map<? extends CellCoordinates, ? extends CellState> structure){
		
	}
	
	public CellIterator cellIterator(){
		return new CellIterator();
	}
	

	protected abstract Automaton newInstance(CellStateFactory cellStateFactory, CellNeighborhood cellNeighboorhood);
	
	protected abstract boolean hasNextCoordinates(CellCoordinates coords) 
			throws UndefiniedInstanceOfCellException;//----------added exception -----------//
	
	protected abstract CellCoordinates initialCoordinates(CellCoordinates coords);
	
	protected abstract CellCoordinates nextCoordinates(CellCoordinates coords) 
			throws UndefiniedInstanceOfCellException, CoordinatesOutOfBoardException;//-----------added exception ------//
	
	protected abstract CellState nextCellState(CellState currentState, Set<Cell> neighborsStates);
	
	
	private Set<Cell> mapCoordinates(Set<CellCoordinates> coords ){
		Set<Cell> cells = new HashSet<Cell>();
		
		for(CellCoordinates coord : coords){
			cells.add(new Cell(null, coord));
		}
		return null;
	}
}

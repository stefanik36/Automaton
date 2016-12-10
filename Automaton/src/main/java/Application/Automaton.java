package application;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import cells.Cell;
import coordinates.CellCoordinates;
import coordinates.Coords2D;

import java.util.Random;
import java.util.Set;

import exceptions.CoordinatesOutOfBoardException;
import exceptions.InvalidCellCoordinatesInstanceException;
import exceptions.InvalidCellStateFactoryException;
import exceptions.InvalidCellStateInstance;
import exceptions.InvalidGameInstance;
import exceptions.InvalidStructureTypeException;
import exceptions.NewStateIteratorHasNotNextException;
import exceptions.UndefiniedInstanceOfCellException;
import gui.AutomatonGUI;
import javafx.scene.Group;
import neighborhood.CellNeighborhood;
import states.BinaryState;
import states.CellState;
import states.CellStateFactory;
import states.GeneralStateFactory;
import states.LangtonCell;

public abstract class Automaton {
	
//	static void main(String[] args){
//		AutomatonGUI gameGUI = new AutomatonGUI();
//		
//	}
	//ADDED//
	public int neighborhood_range = 1;
	private static boolean isPlaying;
	public static boolean isPlaying(){
		return isPlaying;
	}

	public static void setIsPlaying(boolean b) {
		isPlaying = b;
	}
	//END//

	private Map<CellCoordinates, CellState> cells = new HashMap<CellCoordinates, CellState> (); 
	private CellNeighborhood neighborsStrategy;// = new VonNeumanNeighborhood(neighborhood_range);//----------------added range parameter -----------//
	private CellStateFactory stateFactory;
	
	//ADDED//
	public void setNeighborhoodAndstateFactory(CellNeighborhood neighborsStrategy, CellStateFactory stateFactory){
		this.neighborsStrategy = neighborsStrategy;
		this.stateFactory = stateFactory;
	}
//	public void importCellsFromStateFactory(CellStateFactory stateFactory){    // ------------------------------------ TO DELETE --------------------------- //
//		Automaton newState = newInstance(stateFactory, neighborsStrategy);
//		CellIterator cellIterator = newState.cellIterator();
//		System.out.println("import size: "+cells.size()+" import: "+this.hashCode());
//		cells.clear();
//		while(cellIterator.hasNext()){
//			Cell c = cellIterator.next();
//			System.out.println("c: "+c);			
//			cells.put(c.coords, c.state);	
//		}
//	}
	public CellNeighborhood getNeighborsStrategy(){
		return neighborsStrategy;
	}
	//END//
	
	public class CellIterator implements Iterator<Cell> {    //-------------------INNER CLASS -----------------------//
		
		//private CellCoordinates currentState; //DELETED
		//ADDED//
		private CellCoordinates currentCoords;
		Iterator<Map.Entry<CellCoordinates, CellState>> it = cells.entrySet().iterator();
		//END//
		
		public boolean hasNext(){
			if(it.hasNext())
				return true;
			return false;
		}
		
		public Cell next(){
			Map.Entry<CellCoordinates, CellState> pair = it.next();
			CellState state = (CellState)pair.getValue();
			currentCoords = (CellCoordinates)pair.getKey();
			//System.out.println("currentCoords: "+currentCoords+" state: "+state);
			return new Cell(currentCoords, state);
		}
		
		public void setState(CellState newState) throws InvalidCellCoordinatesInstanceException, InvalidGameInstance{
//			System.out.println("newState2: "+newState);
			setCellState(currentCoords, newState); // ------------------- abstract in Automaton ------------------- //
		}
		public void reset(){
			it = cells.entrySet().iterator();
		}
	}
	
	//ADDED//
	public Set<CellCoordinates> cellNeighbors(CellCoordinates coords){
		return neighborsStrategy.cellNeighborhood(coords);
	}
	public void setCellState(CellCoordinates coords, CellState newState) throws InvalidCellCoordinatesInstanceException, InvalidGameInstance{
//		System.out.println("put cell: "+coords+" "+newState);
		cells.put(coords, newState);
//		if(stateFactory instanceof GeneralStateFactory){
//			((GeneralStateFactory) stateFactory).changeCellState(coords, newState);
//		}
		
	}
	protected void putCell(CellCoordinates coords, CellState newState) throws InvalidCellStateFactoryException, InvalidGameInstance, InvalidCellCoordinatesInstanceException{            // --------------------- add cell from game of life ------------//
		cells.put(coords, newState);
	}
	//END//
	
	public Automaton nextState() throws InvalidCellStateInstance, NewStateIteratorHasNotNextException, InvalidCellCoordinatesInstanceException, InvalidGameInstance{
//		System.out.println("newstate");
		Automaton automaton = newInstance(stateFactory, neighborsStrategy);
		
		CellIterator newStateIterator = automaton.cellIterator();	
		CellIterator thisIterator = cellIterator();
		
		while(thisIterator.hasNext()){
			Cell c = thisIterator.next();
//			System.out.println("cecll: "+c);
			if(newStateIterator.hasNext()){
				newStateIterator.next();
			}
			else{
				throw new NewStateIteratorHasNotNextException("Iterator has not next cell.");
			}
			Set<CellCoordinates> neighbors = cellNeighbors(c.coords);
			Set<Cell> mappedNeighbors = mapCoordinates(neighbors);
			CellState newState = nextCellState(c.state, mappedNeighbors);
			newStateIterator.setState(newState);		
		}
		
		return automaton;
	}
	public void insertStructure(Map<? extends CellCoordinates, ? extends CellState> structure) throws InvalidGameInstance, InvalidCellStateInstance{
		for (Entry<? extends CellCoordinates, ? extends CellState> pair : structure.entrySet()) {
			CellCoordinates coords = pair.getKey();
			CellState state = cells.get(coords);
			if(state == null){
//				System.out.println("null");
				break;
//				return;
			}
			state = state.getOppositeState();
			/*
			if(this instanceof GameOfLife){
//				if(state instanceof BinaryState){
					state = ((BinaryState) state).getOppositeState();
//				}
//				else{
//					throw new InvalidCellStateInstance("invalid state in GameOfLife");
//				}
			}
//			else if (this instanceof LangtonsAnt){
//				
//			}
			else if(this instanceof LangtonsAnt){
				LangtonCell lCell = (LangtonCell) state;
				state = lCell.getOppositeState();
			}
			else if(this instanceof WireWorld){
				LangtonCell lCell = (LangtonCell) state;
				state = lCell.getOppositeState();
			}
			else{
				throw new InvalidGameInstance("Invalid game instance in AutomatonAnimation.");
			}*/
			cells.put(pair.getKey(), state);
		}
	}
	
	public CellIterator cellIterator(){
		return new CellIterator();
	}
	
	private Set<Cell> mapCoordinates(Set<CellCoordinates> coords ){
		Set<Cell> cellSet = new HashSet<Cell>();		

		for(CellCoordinates coord : coords){
			CellState state = cells.get(coord);
			Cell c = new Cell(coord, state);
			//System.out.println("coords: "+coord+" state: "+state);
			cellSet.add(c);
		}
		return cellSet;
	}

	protected abstract Automaton newInstance(CellStateFactory cellStateFactory, CellNeighborhood cellNeighboorhood);
	
	protected abstract boolean hasNextCoordinates(CellCoordinates coords) 
			throws UndefiniedInstanceOfCellException;//----------added exception -----------//
	
	protected abstract CellCoordinates initialCoordinates();
	
	protected abstract CellCoordinates nextCoordinates(CellCoordinates coords) 
			throws UndefiniedInstanceOfCellException, CoordinatesOutOfBoardException;//-----------added exception ------//
	
	protected abstract CellState nextCellState(CellState currentState, Set<Cell> neighborsStates) 
			throws InvalidCellStateInstance;//-----------added exception ------//
	
	//ADDED//
	public abstract Map<? extends CellCoordinates, ? extends CellState> getStructure(CellCoordinates cellCoords) throws InvalidStructureTypeException;
	public abstract Structure getStructureType();
	public abstract void setStructureType(Structure structureType);
	protected abstract void fillTheMap(CellStateFactory cellStateFactory);
	public abstract Group getCellShape(Cell c) throws InvalidCellCoordinatesInstanceException;
	
	public Group getCellsGroup(){
//		System.out.println("size: "+cells.size()+" draw: "+this.hashCode());
//		System.out.println("cell state factory: "+stateFactory);
		Group cellsGroup = new Group();
		CellIterator thisIterator = cellIterator();
		while(thisIterator.hasNext()){
			Cell c = thisIterator.next();
//			System.out.println("new draw: "+c);
			try {
				cellsGroup.getChildren().add(getCellShape(c));
			} catch (InvalidCellCoordinatesInstanceException e) {
				e.printStackTrace();
			}
		}
//		thisIterator.reset();
		return cellsGroup;
	}
	public Map<CellCoordinates, CellState> GetActualCellStates(){
		return cells;
	}
	//END//
	
	//ADDED FOR TEST//
	public Map<CellCoordinates, CellState> getCells(){
		return cells;
	}
	//END//


	
	
}

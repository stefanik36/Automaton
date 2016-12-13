package states;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import application.Automaton;
import application.Automaton.CellIterator;
import application.GameOfLife;
import application.LangtonsAnt;
import application.WireWorld;
import cells.Cell;
import coordinates.CellCoordinates;
import coordinates.Coords1D;
import coordinates.Coords2D;
import exceptions.InvalidCellCoordinatesInstanceException;
import exceptions.InvalidCellStateInstance;
import exceptions.InvalidGameInstance;

public class GeneralStateFactory implements CellStateFactory{

	private Map<CellCoordinates, CellState> states = new HashMap<CellCoordinates, CellState>();
	private Automaton game;
	//ADDED//
	public GeneralStateFactory(Automaton game){
		this.game = game;
	}
	
	//END//
	@Override
	public CellState initialState(CellCoordinates coords) {
//		System.out.println("NULL");
		if(game instanceof GameOfLife){
//			CellIterator cellIterator = game.cellIterator();
//			/*getStates()*/states.clear();
//			
//			while(cellIterator.hasNext()){
//				Cell c = cellIterator.next();
//				/*getStates()*/states.put(c.coords, BinaryState.DEAD);
//			}
//			System.out.println("gameOfLIfe");
			if(states.get(coords) == null){
//				System.out.println("null");
				BinaryState bState = BinaryState.DEAD;
				states.put(coords, bState);
				return bState;
			}
			CellState state = states.get(coords);
			return state;
		}
		else if(game instanceof LangtonsAnt){
			if(states.get(coords) == null){
				return new LangtonCell(BinaryState.DEAD, 0, AntState.NONE);
			}
			LangtonCell lCell = (LangtonCell)states.get(coords);
			return new LangtonCell(lCell.cellState, lCell.antId, lCell.antState);
		}
		else if(game instanceof WireWorld){
			if(states.get(coords) == null){
				return WireElectronState.VOID;
			}
			CellState state = states.get(coords);
			return state;
		}
		else{
			try {
				throw new InvalidGameInstance("Invalid game instance. (GeneralStateFactory)");
			} catch (InvalidGameInstance e) {
				e.printStackTrace();
			}
		}
		return null;
		
	}

	//ADDED//
	public Cell changeCellState(CellCoordinates cellCoords, CellState newState) throws InvalidCellCoordinatesInstanceException, InvalidGameInstance{
		if(game instanceof GameOfLife){
			
			for (Entry<CellCoordinates, CellState> pair : /*getStates()*/states.entrySet()) {
//				System.out.println("cC: "+cellCoords+"  key: "+pair.getKey() );
				if(pair.getKey().equals(cellCoords)){
//					System.out.println("expected coords ");
					if(newState == null){
						if(pair.getValue().equals(BinaryState.ALIVE)){
							pair.setValue(BinaryState.DEAD);
							return new Cell(cellCoords, BinaryState.DEAD);
						}
						else{
							pair.setValue(BinaryState.ALIVE);
							return new Cell(cellCoords, BinaryState.ALIVE);
						}
					}
					else{
						pair.setValue(newState);
					}
				}
			}
		}
		else if (game instanceof LangtonsAnt){
			
		}
		else{
			throw new InvalidGameInstance("Invalid game instance (GeneralStateFacory)");
		}
		return null;
	}
	
//	public Cell changeCellState(Automaton game, CellCoordinates Cellcoords) throws InvalidCellCoordinatesInstanceException{
//		if(game instanceof GameOfLife){
//			CellIterator cellIterator = game.cellIterator();
//			Cell newCell=null;
//			CellState state;
//			while(cellIterator.hasNext()){
//				Cell c = cellIterator.next();
//				if(c.coords.equals(Cellcoords)){
//					if(c.state == BinaryState.ALIVE){
//						state = BinaryState.DEAD;
//			     	}
//			     	else{
//			     		state = BinaryState.ALIVE;
//			     	}
//					newCell = new Cell(c.coords, state);
//					states.put(c.coords, state);	
//				}
//				else{
//					states.put(c.coords, c.state);	
//				}
//			}
//			return newCell;
//		}
//		else if (game instanceof LangtonsAnt){
////			System.out.println("ant");
//			CellIterator cellIterator = game.cellIterator();
//			Cell newCell=null;
//			CellState state;
//			int lastAntId = getLastAntId();
//			
//			while(cellIterator.hasNext()){
//				Cell c = cellIterator.next();
//				if(c.coords.equals(Cellcoords)){
////					System.out.println(c.state);
//					if(c.state instanceof LangtonCell){
////						System.out.println("ant");
//						LangtonCell langCell = (LangtonCell) c.state;
//						if(langCell.antState == AState.NONE){
////							System.out.println("ant none");
//							state = new LangtonCell(BinaryState.ALIVE, lastAntId+1, AState.NORTH);
//						}
//						else{
//							
//							state = new LangtonCell(BinaryState.DEAD, lastAntId+1, AState.NONE);
//						}
//						newCell = new Cell(c.coords, state);
//						states.put(c.coords, state);	
//			     	}
//			     	else{
//			     		try {
//							throw new InvalidCellStateInstance("Instance of cell state should be LangtonCell.");
//						} catch (InvalidCellStateInstance e) {
//							e.printStackTrace();
//						}
//			     	}
//				}
//				else{
//					states.put(c.coords, c.state);	
//				}
//			}
//			return newCell;
//		}
//		else{
//     		try {
//				throw new InvalidCellStateInstance("Game instance is undefinied.");
//			} catch (InvalidCellStateInstance e) {
//				e.printStackTrace();
//			}
//     	}
//		return null;
//	}
	


	private int getLastAntId() {
		CellIterator cellIterator = game.cellIterator();
		int lastAntId=0;
		while(cellIterator.hasNext()){
			Cell c = cellIterator.next();
			LangtonCell langCell = (LangtonCell) c.state;
			if(langCell.antId>lastAntId){
				lastAntId = langCell.antId;
			}
//			System.out.println(langCell.antId);
		}
		System.out.println("last ant id: "+lastAntId);
		return lastAntId;
	}
	//END//

//	public Map<CellCoordinates, CellState> /*getStates()*/states {
//		return states;
//	}

	public void setStates(Map<CellCoordinates, CellState> states) {
		this.states = states;
	}

}

package application;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import application.Structure.StructureType;
import cells.Cell;
import coordinates.CellCoordinates;
import coordinates.Coords1D;
import coordinates.Coords2D;
import exceptions.CoordinatesOutOfBoardException;
import exceptions.InvalidCellCoordinatesInstanceException;
import exceptions.InvalidCellStateFactoryException;
import exceptions.InvalidCellStateInstance;
import exceptions.InvalidGameInstance;
import exceptions.InvalidStructureTypeException;
import exceptions.UndefiniedInstanceOfCellException;
import gui.AutomatonGUI;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import neighborhood.CellNeighborhood;
import neighborhood.MoorNeighborhood;
import states.BinaryState;
import states.CellState;
import states.CellStateFactory;
import states.GeneralStateFactory;
import states.UniformStateFactory;

import java.util.Arrays;
import java.util.HashMap;
public class GameOfLife extends Automaton2Dim{

	//ADDED//
	private Integer[] survivorsA  = {2, 3};
	private Integer[] comeAliveA  = {3};
	public List<Integer> survivors = new ArrayList<Integer>(Arrays.asList(survivorsA));
	public List<Integer> comeAlive = new ArrayList<Integer>(Arrays.asList(comeAliveA));
	
	private Structure structureType;
	//END//
	
	
	
	public GameOfLife(){
		
	}
	

	public GameOfLife(CellStateFactory cellStateFactory, CellNeighborhood cellNeighboorhood){
		setNeighborhoodAndstateFactory(cellNeighboorhood, cellStateFactory);
		fillTheMap(cellStateFactory);
		structureType = new Structure();
		structureType.setSType(StructureType.GLIDER);
	}
	
	


	//ADDED FOR TESTS//
	public Automaton newGameOfLife(CellStateFactory cellStateFactory, CellNeighborhood cellNeighboorhood){
		Automaton game = new GameOfLife(cellStateFactory, cellNeighboorhood); 
//		game.fillTheMap(cellStateFactory);
		return game;
	}
	public CellState getNextCellState(CellState currentState, Set<Cell> neighborsStates) throws InvalidCellStateInstance{
		 return nextCellState(currentState, neighborsStates);
	}
	//END//
	
	@Override
	protected Automaton newInstance(CellStateFactory stateFactory, CellNeighborhood neighborsStrategy) {

		Automaton game = new GameOfLife(); 
		game.setNeighborhoodAndstateFactory(neighborsStrategy, stateFactory);
		game.fillTheMap(stateFactory);
		game.setStructureType(structureType);
		//System.out.println(cellStateFactory);
//		game.setNeighborhoodAndstateFactory(cellNeighboorhood, cellStateFactory);
//		Map<CellCoordinates, CellState> structure = fillTheMap(cellStateFactory);
//		game.insertStructure(structure);
		return game;		
	}

	@Override
	protected CellState nextCellState(CellState currentState, Set<Cell> neighborsStates) throws InvalidCellStateInstance {
		
		if(currentState instanceof BinaryState){
			BinaryState state = (BinaryState)currentState;
			if(state.equals(BinaryState.ALIVE)){
				return getNewState(neighborsStates, survivors);
			}
			else{
				return getNewState(neighborsStates, comeAlive);
			}
		}
		else{
			throw new InvalidCellStateInstance("Instance of cell state is invalid.");
		}

	}
	
	//ADDED//
	@Override
	public void fillTheMap(CellStateFactory cellStateFactory){
//		System.out.println("fill the map");
		CellCoordinates coords = initialCoordinates();
		try {
			while(hasNextCoordinates(coords)){		
				coords = nextCoordinates(coords);
				putCell(coords, cellStateFactory.initialState(coords)); 
			}
		}catch (CoordinatesOutOfBoardException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UndefiniedInstanceOfCellException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidCellStateFactoryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidGameInstance e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidCellCoordinatesInstanceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
//	@Override
//	protected Map<CellCoordinates, CellState> fillTheMap(CellStateFactory cellStateFactory){
//		CellCoordinates coords = initialCoordinates();
//		Map<CellCoordinates, CellState> structure = new HashMap<CellCoordinates, CellState>();
//		try {
//			while(hasNextCoordinates(coords)){		
//				coords = nextCoordinates(coords);
//				structure.put(coords, cellStateFactory.initialState(coords));
//			}
//		} catch (UndefiniedInstanceOfCellException e) {
//			e.printStackTrace();
//		} catch (CoordinatesOutOfBoardException e) {
//			e.printStackTrace();
//		}
//		return structure;
//	}
	
	private CellState getNewState(Set<Cell> neighborsStates, List<Integer> criteria){
		int numberOfAliveNeighbors=0;
		for(Cell c : neighborsStates){
			if(c.state.equals(BinaryState.ALIVE)){
				numberOfAliveNeighbors++;
			}
		}
		for(int value : criteria){
			if(numberOfAliveNeighbors==value){
				return BinaryState.ALIVE;
			}
		}
		return BinaryState.DEAD;
	}
	
	@Override
	public Group getCellShape(Cell c) throws InvalidCellCoordinatesInstanceException {
		Group cellShape = new Group();
		if(c.coords instanceof Coords2D){
			Coords2D c2D = (Coords2D)c.coords;
			BinaryState bState = (BinaryState)c.state;
			int x = c2D.getX()*AutomatonGUI.DISTANCE_TO_NEIGHBORS-AutomatonGUI.DISTANCE_TO_NEIGHBORS/2;
			int y = c2D.getY()*AutomatonGUI.DISTANCE_TO_NEIGHBORS-AutomatonGUI.DISTANCE_TO_NEIGHBORS/2;
			Circle eCircle = new Circle(x, y,AutomatonGUI.EXTERNAL_CELL_RADIUS, Color.BLACK);
			cellShape.getChildren().add(eCircle);
//			System.out.println(bState.equals(BState.ALIVE));
			if(bState.equals(BinaryState.DEAD)){
				Circle iCircle = new Circle(x, y, AutomatonGUI.INTERNAL_CELL_RADIUS, Color.WHITE);
				cellShape.getChildren().add(iCircle);
			}
			return cellShape;
		}
		else if(c.coords instanceof Coords1D){
			Coords1D c1D = (Coords1D)c.coords;
			BinaryState bState = (BinaryState)c.state;
			int x = c1D.getX()*AutomatonGUI.DISTANCE_TO_NEIGHBORS-AutomatonGUI.DISTANCE_TO_NEIGHBORS/2;
			Circle eCircle = new Circle(x, AutomatonGUI.DISTANCE_TO_NEIGHBORS/2,AutomatonGUI.EXTERNAL_CELL_RADIUS, Color.BLACK);
			cellShape.getChildren().add(eCircle);
			if(bState.equals(BinaryState.DEAD)){
				Circle iCircle = new Circle(x, AutomatonGUI.DISTANCE_TO_NEIGHBORS/2,AutomatonGUI.INTERNAL_CELL_RADIUS, Color.WHITE);
				cellShape.getChildren().add(iCircle);
			}
			return cellShape;
		}
		else{
			throw new InvalidCellCoordinatesInstanceException("Unknown cell coordinates instance.");
		}
	}





	@Override
	public Map<? extends CellCoordinates, ? extends CellState> getStructure(CellCoordinates cellCoords) throws InvalidStructureTypeException {
		return structureType.getStructure(cellCoords, this);
	}


	@Override
	public Structure getStructureType() {
		return structureType;
	}


	@Override
	public void setStructureType(Structure structureType) {
		this.structureType = structureType;
	}

	//END//
}

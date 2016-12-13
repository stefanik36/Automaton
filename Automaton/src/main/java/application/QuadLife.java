package application;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

import application.Structure.StructureType;
import cells.Cell;
import coordinates.CellCoordinates;
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
import states.BinaryState;
import states.CellState;
import states.CellStateFactory;
import states.QuadState;
public class QuadLife extends Automaton2Dim{

	//ADDED//
	private List<Integer> survivors;
	private List<Integer> comeAlive;
	private Structure structureType;
	
	public List<Integer> getComeAlive() {
		return comeAlive;
	}

	public void setComeAlive(List<Integer> comeAlive) {
		this.comeAlive = comeAlive;
	}

	public List<Integer> getSurvivors() {
		return survivors;
	}

	public void setSurvivors(List<Integer> survivors) {
		this.survivors = survivors;
	}

	@Override
	public void setStructure(StructureType struct) {
		structureType = new Structure();
		structureType.setSType(struct);
	}
	//END//
	
	public CellState getNextCellState(CellState currentState, Set<Cell> neighborsStates) throws InvalidCellStateInstance{
		 return nextCellState(currentState, neighborsStates);
	}
	
	public QuadLife(){
		
	}
	
	public QuadLife(CellStateFactory cellStateFactory, CellNeighborhood cellNeighboorhood){
		setNeighborhoodAndstateFactory(cellNeighboorhood, cellStateFactory);
		fillTheMap(cellStateFactory);
		structureType = new Structure();
		structureType.setSType(StructureType.GLIDER);
		Integer[] survivorsA  = {2, 3};
		Integer[] comeAliveA  = {3};
		survivors = new ArrayList<Integer>(Arrays.asList(survivorsA));
		comeAlive = new ArrayList<Integer>(Arrays.asList(comeAliveA));
	}
	
	@Override
	protected Automaton newInstance(CellStateFactory stateFactory, CellNeighborhood neighborsStrategy) {

		Automaton game = new QuadLife(); 
		game.setNeighborhoodAndstateFactory(neighborsStrategy, stateFactory);
		game.fillTheMap(stateFactory);
		game.setStructureType(structureType);
		((QuadLife)game).setSurvivors(survivors);
		((QuadLife)game).setComeAlive(comeAlive);
		return game;		
	}

	@Override
	protected CellState nextCellState(CellState currentState, Set<Cell> neighborsStates) throws InvalidCellStateInstance {
		currentState = castFromBSToQuadState(currentState);
		if(currentState instanceof QuadState){
			QuadState state = (QuadState)currentState;
			if(!state.equals(QuadState.DEAD)){
				return getNewState(neighborsStates, survivors, state);
			}
			else{
				return getNewState(neighborsStates, comeAlive, state);
			}
		}
		else{
			throw new InvalidCellStateInstance("Instance of cell state is invalid.");
		}

	}
	
	//ADDED//
	@Override
	public void fillTheMap(CellStateFactory cellStateFactory){
		CellCoordinates coords = initialCoordinates();
		try {
			while(hasNextCoordinates(coords)){		
				coords = nextCoordinates(coords);
				putCell(coords, cellStateFactory.initialState(coords)); 
			}
		}catch (CoordinatesOutOfBoardException e) {
			e.printStackTrace();
		} catch (UndefiniedInstanceOfCellException e) {
			e.printStackTrace();
		} catch (InvalidCellStateFactoryException e) {
			e.printStackTrace();
		} catch (InvalidGameInstance e) {
			e.printStackTrace();
		} catch (InvalidCellCoordinatesInstanceException e) {
			e.printStackTrace();
		} 
	}

	
	private CellState getNewState(Set<Cell> neighborsStates, List<Integer> criteria, QuadState state){
		
		Map<QuadState,Integer> map = new TreeMap<QuadState,Integer>();

		map.put(QuadState.RED, 0);
		map.put(QuadState.YELLOW, 0);
		map.put(QuadState.BLUE, 0);
		map.put(QuadState.GREEN, 0);
		
		
		for(Cell c : neighborsStates){
			c.state = castFromBSToQuadState(c.state);
			if(c.state.equals(QuadState.RED)){
				map.put(QuadState.RED, map.get(QuadState.RED)+1);
				}
			else if(c.state.equals(QuadState.YELLOW)){
				map.put(QuadState.YELLOW, map.get(QuadState.YELLOW)+1);
			}
			else if(c.state.equals(QuadState.BLUE)){
				map.put(QuadState.BLUE, map.get(QuadState.BLUE)+1);
			}
			else if(c.state.equals(QuadState.GREEN)){
				map.put(QuadState.GREEN, map.get(QuadState.GREEN)+1);
			}
		}
		int numberOfAliveNeighbors = map.get(QuadState.RED)+ map.get(QuadState.YELLOW)+ map.get(QuadState.BLUE)+map.get(QuadState.GREEN);
		
		if(criteria.contains(numberOfAliveNeighbors)){		
			if(!state.equals(QuadState.DEAD)){
				return state;
			}
			List<Integer> numberOfcolor = new ArrayList<Integer>();
			List<QuadState> typeOfcolor= new ArrayList<QuadState>();
			for (Entry<QuadState, Integer> entry  : entriesSortedByValues(map)) {
				typeOfcolor.add(entry.getKey());
			    numberOfcolor.add(entry.getValue());
			}
			if(numberOfcolor.get(3)>numberOfcolor.get(2)){
				return typeOfcolor.get(3);
			}
			else{
				return typeOfcolor.get(1);
			}
		}
		return QuadState.DEAD;
	}
	
	static <K,V extends Comparable<? super V>> SortedSet<Map.Entry<K,V>> entriesSortedByValues(Map<K,V> map) {
	    SortedSet<Map.Entry<K,V>> sortedEntries = new TreeSet<Map.Entry<K,V>>(
	        new Comparator<Map.Entry<K,V>>() {
	            @Override public int compare(Map.Entry<K,V> e1, Map.Entry<K,V> e2) {
	                int res = e1.getValue().compareTo(e2.getValue());
	                return res != 0 ? res : 1;
	            }
	        }
	    );
	    sortedEntries.addAll(map.entrySet());
	    return sortedEntries;
	}
	
	@Override
	public Group getCellShape(Cell c) throws InvalidCellCoordinatesInstanceException {
		Group cellShape = new Group();
		if(c.coords instanceof Coords2D){
			Coords2D c2D = (Coords2D)c.coords;
			int x = c2D.getX()*AutomatonGUI.DISTANCE_TO_NEIGHBORS-AutomatonGUI.DISTANCE_TO_NEIGHBORS/2;
			int y = c2D.getY()*AutomatonGUI.DISTANCE_TO_NEIGHBORS-AutomatonGUI.DISTANCE_TO_NEIGHBORS/2;

			c.state = castFromBSToQuadState(c.state);
			
			QuadState qState = (QuadState)c.state;
			if(qState.equals(QuadState.RED)){
				Circle eCircle = new Circle(x, y,AutomatonGUI.EXTERNAL_CELL_RADIUS, Color.RED);
				cellShape.getChildren().add(eCircle);
			}
			else if(qState.equals(QuadState.YELLOW)){
				Circle eCircle = new Circle(x, y,AutomatonGUI.EXTERNAL_CELL_RADIUS, Color.YELLOW);
				cellShape.getChildren().add(eCircle);
			}
			else if(qState.equals(QuadState.BLUE)){
				Circle eCircle = new Circle(x, y,AutomatonGUI.EXTERNAL_CELL_RADIUS, Color.BLUE);
				cellShape.getChildren().add(eCircle);
			}
			else if(qState.equals(QuadState.GREEN)){
				Circle eCircle = new Circle(x, y,AutomatonGUI.EXTERNAL_CELL_RADIUS, Color.GREEN);
				cellShape.getChildren().add(eCircle);
			}
			else {
				Circle eCircle = new Circle(x, y,AutomatonGUI.EXTERNAL_CELL_RADIUS, Color.BLACK);
				cellShape.getChildren().add(eCircle);
				Circle iCircle = new Circle(x, y, AutomatonGUI.INTERNAL_CELL_RADIUS, Color.WHITE);
				cellShape.getChildren().add(iCircle);
			}
			return cellShape;
		}
		else{
			throw new InvalidCellCoordinatesInstanceException("Unknown cell coordinates instance.");
		}
	}


	private CellState castFromBSToQuadState(CellState tmpState) {
		if(tmpState instanceof BinaryState){
			if(((BinaryState)tmpState).equals(BinaryState.DEAD)){
				tmpState = QuadState.DEAD;
			}
			else{
				tmpState = QuadState.RED;
			}
		}
		return tmpState;
	}

	@Override
	public Map<? extends CellCoordinates, ? extends CellState> getStructure(CellCoordinates cellCoords) throws InvalidStructureTypeException, InvalidGameInstance {
		return structureType.getStructure(cellCoords, this);
	}

	@Override
	public StructureType getStructureType() {
		return structureType.sType;
	}

	@Override
	public void setStructureType(Structure structureType) {
		this.structureType = structureType;
	}

	public void changeSurvivor(int i) {
		if(survivors.contains(new Integer(i))){
			survivors.remove(new Integer(i));
		}
		else{
			survivors.add(i);
		}
	}
	public void changeComeAlive(int i) {
		if(comeAlive.contains(new Integer(i))){
			comeAlive.remove(new Integer(i));
		}
		else{
			comeAlive.add(i);
		}
	}
	//END//
}

package application;

import java.util.Map;
import java.util.Set;

import application.Structure.StructureType;
import cells.Cell;
import coordinates.CellCoordinates;
import coordinates.Coords1D;
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

public class ElementaryCellAutomaton extends Automaton1Dim{
	
	private int[] rule = new int[8];
	public int generation; 
	
	private Structure structureType;
	public ElementaryCellAutomaton() {
		
	}
	public ElementaryCellAutomaton(CellStateFactory cellStateFactory, CellNeighborhood cellNeighboorhood) {
		setNeighborhoodAndstateFactory(cellNeighboorhood, cellStateFactory);
		fillTheMap(cellStateFactory);
		structureType = new Structure();
		structureType.setSType(StructureType.SIMPLE);
		setRule(165); //default rule is 165
	}
	
	public void setRule(int value){
		int tmp = value;
		for(int it=rule.length-1;it>=0;it--){
			rule[it]=tmp%2;
			tmp= tmp/2;
		}
	}

	private void setRule(int[] oldRule) {
		for(int it=0;it<rule.length;it++){
			rule[it] = oldRule[it];
		}
		
	}
	@Override
	public void setStructure(StructureType struct) {
		structureType = new Structure();
		structureType.setSType(struct);
	}
	
	@Override
	protected Automaton newInstance(CellStateFactory cellStateFactory, CellNeighborhood cellNeighboorhood) {
		Automaton game = new ElementaryCellAutomaton(); 
		game.setNeighborhoodAndstateFactory(cellNeighboorhood, cellStateFactory);
		game.fillTheMap(cellStateFactory);
		game.setStructureType(structureType);
		if(game instanceof ElementaryCellAutomaton){
			
			ElementaryCellAutomaton ecaGame = ((ElementaryCellAutomaton)game);
			int gen = this.generation+1;
			ecaGame.setRule(rule);
			if(gen<ecaGame.getWidth()){
				ecaGame.generation = gen;
				return ecaGame;
			}
		}
		
		return game;
	}

	@Override
	protected CellState nextCellState(CellState currentState, Set<Cell> neighborsStates)throws InvalidCellStateInstance {
		if(currentState instanceof BinaryState){
			BinaryState state = (BinaryState)currentState;
			
			Coords1D[] cellsCoordinates = new Coords1D[neighborsStates.size()];
			BinaryState[] cellsStates = new BinaryState[neighborsStates.size()];
			int it=0;
			for(Cell c : neighborsStates){
			
				if(c.coords instanceof Coords1D){
					cellsCoordinates[it] = (Coords1D)c.coords;
					cellsStates[it] = (BinaryState)c.state;
					it++;
					
				}
				else{
					try {
						throw new InvalidCellCoordinatesInstanceException("Coords should be instance of Coords1D");
					} catch (InvalidCellCoordinatesInstanceException e) {
						e.printStackTrace();
					}
				}
			}
			
			if(state == BinaryState.ALIVE){ 
				return checkNeighboors(cellsCoordinates, cellsStates, true);
			}
			else{
				return checkNeighboors(cellsCoordinates, cellsStates, false);
			}
		}
		else{
			throw new InvalidCellStateInstance("Instance of cell state is invalid.");
		}

	}
	private CellState checkNeighboors(Coords1D[] cellsCoordinates, BinaryState[] cellsStates, boolean middleIsAlive) {
		if((cellsCoordinates[0].getX()+2) == cellsCoordinates[1].getX()){
			return checkRules(cellsStates, 0, 1, middleIsAlive);
		}
		else if((cellsCoordinates[0].getX()-2) == cellsCoordinates[1].getX()){
			return checkRules(cellsStates, 1, 0, middleIsAlive);
		}
		else if(cellsCoordinates[0].getX() > cellsCoordinates[1].getX()){
			return checkRules(cellsStates, 0, 1, middleIsAlive);
		}
		else if(cellsCoordinates[0].getX() < cellsCoordinates[1].getX()){
			return checkRules(cellsStates, 1, 0, middleIsAlive);
		}
		return null;
	}
	/**
	 * 
	 * check {@link ElementaryCellAutomaton} rules.
	 * 
	 *    0   1   2   3   4   5   6   7
	 * 	{111,110,101,100,011,010,001,000};
	 * 
	 * 
	 */
	private CellState checkRules(BinaryState[] cellsStates, int left, int right, boolean positiveCenter) {
		
		if(cellsStates[left] == BinaryState.ALIVE){     		 
			if(cellsStates[right] == BinaryState.ALIVE){
				if(positiveCenter)		//111
					return checkRule(0);
				else					//101
					return checkRule(2);
			}
			else{
				if(positiveCenter)		//110
					return checkRule(1);
				else   					//100
					return checkRule(3);
			}
		}
		else{
			if(cellsStates[right] == BinaryState.ALIVE){
				if(positiveCenter)		//011
					return checkRule(4);
				else					//001
					return checkRule(6);
			}
			else{
				if(positiveCenter)		//010
					return checkRule(5);
				else					//000
					return checkRule(7);
			}
		}
	}
	private CellState checkRule(int value) {
		if(rule[value] == 0)
			return BinaryState.DEAD;
		else
			return BinaryState.ALIVE;
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

	@Override
	public void fillTheMap(CellStateFactory cellStateFactory) {
		CellCoordinates coords = initialCoordinates();
			try {
				while(hasNextCoordinates(coords)){		
					coords = nextCoordinates(coords);
					putCell(coords, cellStateFactory.initialState(coords)); 
				}
			} catch (UndefiniedInstanceOfCellException e) {
				e.printStackTrace();
			} catch (CoordinatesOutOfBoardException e) {
				e.printStackTrace();
			} catch (InvalidCellStateFactoryException e) {
				e.printStackTrace();
			} catch (InvalidGameInstance e) {
				e.printStackTrace();
			} catch (InvalidCellCoordinatesInstanceException e) {
				e.printStackTrace();
			}
	}

	@Override
	public Group getCellShape(Cell c) throws InvalidCellCoordinatesInstanceException {
		Group cellShape = new Group();
		
		if(c.coords instanceof Coords1D){
			Coords1D c1D = (Coords1D)c.coords;
			BinaryState bState = (BinaryState)c.state;
			int x = c1D.getX()*AutomatonGUI.DISTANCE_TO_NEIGHBORS-AutomatonGUI.DISTANCE_TO_NEIGHBORS/2;
			Circle eCircle = new Circle(x, AutomatonGUI.DISTANCE_TO_NEIGHBORS/2+AutomatonGUI.DISTANCE_TO_NEIGHBORS*generation,AutomatonGUI.EXTERNAL_CELL_RADIUS, Color.BLACK);
			cellShape.getChildren().add(eCircle);
			if(bState.equals(BinaryState.DEAD)){
				Circle iCircle = new Circle(x, AutomatonGUI.DISTANCE_TO_NEIGHBORS/2+AutomatonGUI.DISTANCE_TO_NEIGHBORS*generation,AutomatonGUI.INTERNAL_CELL_RADIUS, Color.WHITE);
				cellShape.getChildren().add(iCircle);
			}
			return cellShape;
		}
		else{
			throw new InvalidCellCoordinatesInstanceException("Unknown cell coordinates instance.");
		}
	}
	

}

package application;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import exceptions.UncheckedCoordinatesException;
import exceptions.UndefiniedInstanceOfCellException;
import gui.AutomatonGUI;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import neighborhood.CellNeighborhood;
import states.AntState;
//import states.AntState.AntState;
import states.BinaryState;
import states.CellState;
import states.CellStateFactory;
import states.LangtonCell;

public class LangtonsAnt extends Automaton2Dim {

	
	
	//ADDED FOR TESTS//
	public Automaton newLangtonsAnt(CellStateFactory cellStateFactory, CellNeighborhood cellNeighboorhood){
		return newInstance(cellStateFactory, cellNeighboorhood);
	}
	public CellState getNextCellState(CellState currentState, Set<Cell> neighborsStates) throws InvalidCellStateInstance{
		return nextCellState(currentState, neighborsStates);
	}
	//END//
	
	
	//ADDED//
	public static List<LangtonCell> ants = new ArrayList<LangtonCell>();
	private Structure structureType;
	public LangtonsAnt(){
		
	}
	public LangtonsAnt(CellStateFactory cellStateFactory, CellNeighborhood cellNeighboorhood){
		setNeighborhoodAndstateFactory(cellNeighboorhood, cellStateFactory);
		fillTheMap(cellStateFactory);
		structureType = new Structure();
		structureType.setSType(StructureType.SIMPLE);
	}
	//END//
		
	@Override
	protected Automaton newInstance(CellStateFactory stateFactory, CellNeighborhood neighborsStrategy) {

		Automaton game = new LangtonsAnt(); 
		game.setNeighborhoodAndstateFactory(neighborsStrategy, stateFactory);
		game.fillTheMap(stateFactory);
		game.setStructureType(structureType);
		return game;		
	}
//	@Override
//	protected Automaton newInstance(CellStateFactory cellStateFactory, CellNeighborhood cellNeighboorhood) {
//		Automaton game = new LangtonsAnt(); 
//		//System.out.println(cellStateFactory);
//		game.setNeighborhoodAndstateFactory(cellNeighboorhood, cellStateFactory);
////		Map<CellCoordinates, CellState> structure = fillTheMap(cellStateFactory);
////		game.insertStructure(structure);
//		return game;		
//	}
	
	

	@Override
	protected CellState nextCellState(CellState currentState, Set<Cell> neighborsStates) throws InvalidCellStateInstance {
		
		if(currentState instanceof LangtonCell){
			LangtonCell state = (LangtonCell)currentState;
			
			if(state.antState == AntState.NONE){
//				System.out.println("NONE ");
				CellCoordinates[] cellsCoordinates = new CellCoordinates[neighborsStates.size()];
				LangtonCell[] cellsStates = new LangtonCell[neighborsStates.size()];
				int i = 0;
				for(Cell c : neighborsStates){
					if(!(c.state instanceof LangtonCell)){
						throw new InvalidCellStateInstance("Cell state should be instance of LangtonCell");
					}
					if((c.coords instanceof Coords2D) || (c.coords instanceof Coords1D)){
						cellsCoordinates[i] = c.coords;
					}
					else{
						try {
							throw new InvalidCellCoordinatesInstanceException("Unknown cell coordinates instance.");
						} catch (InvalidCellCoordinatesInstanceException e) {
							e.printStackTrace();
						}
					}
					cellsCoordinates[i] =  c.coords;
					cellsStates[i] = (LangtonCell)c.state;
					i++;

				}
				for(int it = 0; it<cellsCoordinates.length;it++){
					if(cellsStates[it].antState == AntState.NONE){
						continue;
					}
					if(cellsStates[it].antState == AntState.NORTH){
						try {
//							System.out.println("nei: NORTH it: "+it+" getSouth: "+getSouth(cellsCoordinates));
							if(it == getSouth(cellsCoordinates)){
								if(state.cellState == BinaryState.DEAD){
									return new LangtonCell(BinaryState.ALIVE, state.antId, AntState.WEST);
								}
								else{
									return new LangtonCell(BinaryState.DEAD, state.antId, AntState.EAST);
								}
							}
						} catch (InvalidCellCoordinatesInstanceException e) {
							e.printStackTrace();
						} catch (UncheckedCoordinatesException e) {
							e.printStackTrace();
						}
					}
					if(cellsStates[it].antState.equals(AntState.EAST)){
						try {
//							System.out.println("nei: EAST it: "+it+" getWest: "+getWest(cellsCoordinates));
							if(it == getWest(cellsCoordinates)){
								if(state.cellState == BinaryState.DEAD){
									return new LangtonCell(BinaryState.ALIVE, state.antId, AntState.NORTH);
								}
								else{
									return new LangtonCell(BinaryState.DEAD, state.antId, AntState.SOUTH);
								}
							}
						} catch (InvalidCellCoordinatesInstanceException e) {
							e.printStackTrace();
						} catch (UncheckedCoordinatesException e) {
							e.printStackTrace();
						}
					}
					if(cellsStates[it].antState.equals(AntState.SOUTH)){
						
						try {
//							System.out.println("nei: SOUTH it: "+it+" getNortd: "+getNorth(cellsCoordinates));
							if(it == getNorth(cellsCoordinates)){
								if(state.cellState == BinaryState.DEAD){
									return new LangtonCell(BinaryState.ALIVE, state.antId, AntState.EAST);
								}
								else{
									return new LangtonCell(BinaryState.DEAD, state.antId, AntState.WEST);
								}
							}
						} catch (InvalidCellCoordinatesInstanceException e) {
							e.printStackTrace();
						} catch (UncheckedCoordinatesException e) {
							e.printStackTrace();
						}
					}
					if(cellsStates[it].antState == AntState.WEST){
						
						try {
//							System.out.println("nei: WEST it: "+it+" getEast: "+getEast(cellsCoordinates));
							if(it == getEast(cellsCoordinates)){
								if(state.cellState == BinaryState.DEAD){
									return new LangtonCell(BinaryState.ALIVE, state.antId, AntState.SOUTH);
								}
								else{
									return new LangtonCell(BinaryState.DEAD, state.antId, AntState.NORTH);
								}
							}
						} catch (InvalidCellCoordinatesInstanceException e) {
							e.printStackTrace();
						} catch (UncheckedCoordinatesException e) {
							e.printStackTrace();
						}
					}
				}
			}

			if(state.cellState == BinaryState.DEAD){
				return new LangtonCell(BinaryState.DEAD, 0, AntState.NONE);
			}
			else{
				return new LangtonCell(BinaryState.ALIVE, 0, AntState.NONE);
			}
		}
		else{
			throw new InvalidCellStateInstance("Instance of cell state is invalid.");
		}
	}
	
	//ADDED//
	public int getNorth(CellCoordinates[] cellsCoordinates) throws InvalidCellCoordinatesInstanceException, UncheckedCoordinatesException{
		return getDirection(cellsCoordinates, -1, false);
	}
	public int getSouth(CellCoordinates[] cellsCoordinates) throws InvalidCellCoordinatesInstanceException, UncheckedCoordinatesException{
		return getDirection(cellsCoordinates, 1, false);
	}
	public int getEast(CellCoordinates[] cellsCoordinates) throws InvalidCellCoordinatesInstanceException, UncheckedCoordinatesException{
		return getDirection(cellsCoordinates, 1, true);
	}
	public int getWest(CellCoordinates[] cellsCoordinates) throws InvalidCellCoordinatesInstanceException, UncheckedCoordinatesException{
		return getDirection(cellsCoordinates, -1, true);
	}

	private int getDirection(CellCoordinates[] cellsCoordinates, int turn, boolean horizontal) throws InvalidCellCoordinatesInstanceException, UncheckedCoordinatesException {
//		System.out.println(" turn: "+turn+" horizontal: "+horizontal);
		int[] coords = new int[cellsCoordinates.length];
		for(int i=0;i<cellsCoordinates.length;i++){
//			System.out.println("i: "+i+": "+cellsCoordinates[i]);
			if(horizontal){
				int x;
				if(cellsCoordinates[i] instanceof Coords2D){
					Coords2D c2D = (Coords2D)cellsCoordinates[i];
					x = c2D.getX();
				}
				else{
					Coords1D c1D = (Coords1D)cellsCoordinates[i];
					x = c1D.getX();
				}
				coords[i] = x;
			}
			else{
				int y;
				if(cellsCoordinates[i] instanceof Coords2D){
					Coords2D c2D = (Coords2D)cellsCoordinates[i];
					y = c2D.getY();
				}
				else{
					throw new InvalidCellCoordinatesInstanceException("For vertical direction CellCoordinates should be instance of Coords2D.");
				}
				coords[i] = y;
			}
		}
		
		
		
		int result;
		for(int j=0;j<coords.length;j++){
			result = checkCoordinates1(turn, coords[j%4], coords[(j+1)%4], coords[(j+2)%4], coords[(j+3)%4], j%4);
			if(result!=-1){
				return result;
			}
		}
		for(int j=0;j<coords.length;j++){
			result = checkCoordinates2(turn, coords[j%4], coords[(j+1)%4], coords[(j+2)%4], coords[(j+3)%4], j%4, (j+1)%4, (j+2)%4, (j+3)%4);
			if(result!=-1){
				return result;
			}
		}
		throw new UncheckedCoordinatesException("Coordinates are unchecked (LangtonsAnt getDirection())");

		/*
		int coords0=coords[0];
		int coords1=coords[1];
		int coords2=coords[2];
		int coords3=coords[3];
		int it0 = 0;
		int it1 = 1;
		int it2 = 2;
		int it3 = 3;
		
		result = checkCoordinates1(turn, coords0, coords1, coords2, coords3, it0);
		System.out.println("result1: "+result);
		if(result!=-1){
			return result;
		}
		result = checkCoordinates1(turn, coords1, coords2, coords3, coords0, it1);
		System.out.println("result2: "+result);
		if(result!=-1){
			return result;
		}
		result = checkCoordinates1(turn, coords2, coords3, coords0, coords1, it2);
		System.out.println("result3: "+result);
		if(result!=-1){
			return result;
		}
		result = checkCoordinates1(turn, coords3, coords0, coords1, coords2, it3);
		System.out.println("result4: "+result);
		if(result!=-1){
			return result;
		}
		
		result = checkCoordinates2(turn, coords0, coords1, coords2, coords3, it0, it1, it2, it3);
		System.out.println("result5: "+result);
		if(result!=-1){
			return result;
		}
		result = checkCoordinates2(turn, coords1, coords2, coords3, coords0, it1, it2, it3, it0);
		System.out.println("result6: "+result);
		if(result!=-1){
			return result;
		}
		result = checkCoordinates2(turn, coords2, coords3, coords0, coords1, it2, it3, it0, it1);
		System.out.println("result7: "+result);
		if(result!=-1){
			return result;
		}
		result = checkCoordinates2(turn, coords3, coords0, coords1, coords2, it3, it0, it1, it2);
		System.out.println("result8: "+result);
		if(result!=-1){
			return result;
		}*/

		/*if(coords1-2*turn == coords0){
			System.out.println("2a");
			return it1;	
		}
		if(coords1-2*turn == coords2){
			System.out.println("2b");
			return it1;	
		}
		if(coords1-2*turn == coords3){
			System.out.println("2c");
			return it1;	
		}
		
		if(coords2-2*turn == coords0){
			System.out.println("3a");
			return it2;	
		}
		if(coords2-2*turn == coords1){
			System.out.println("3b");
			return it2;	
		}
		if(coords2-2*turn == coords3){
			System.out.println("3c");
			return it2;	
		}
		
		if(coords3-2*turn == coords0){
			System.out.println("4a");
			return it3;	
		}
		if(coords3-2*turn == coords1){
			System.out.println("4b");
			return it3;	
		}
		if(coords3-2*turn == coords2){
			System.out.println("4c");
			return it3;	
		}*/
		

		/*for(int k = 0; k<coords.length; k++){
			for(int l = 0; l<coords.length;l++){
				
				if(((coords[k]-coords[l])>2) || ((coords[k]-coords[l])<-2)){
					continue;
				}
				else if(((coords[k]-coords[l])==2) || ((coords[k]-coords[l])==-2)){
					if(coords[k]-2*turn == coords[l]){
						System.out.println("k: "+k+" l: "+l+"ck: "+coords[k]+"-2   cl: "+coords[l]);
						return k;
					}
					else if(coords[l]-2*turn == coords[k]){
						System.out.println("k: "+k+" l: "+l+"cl: "+coords[l]+"-2  ck: "+coords[k]);
						return l;
					}
					System.out.println("UPS1");
				}
				if((coords[k]-coords[l])==0){
					
				}
				else if(((coords[k]-coords[l])==1) || ((coords[k]-coords[l])==-1)){
					System.out.println("A");
					if(coords[k]-1*turn == coords[l]){
						System.out.println("B");
						for(int m=0;m<coords.length;m++){
							if(m==l){
								System.out.println("con");
								continue;
							}
							if(coords[k]-1*turn == coords[m]){
								System.out.println("C");
								for(int n=0;n<coords.length;n++){
									System.out.println("D");
									if((n!=k) && (n!=l) && (n!=m)){
										System.out.println("k: "+k+" l: "+l+" m: "+m+"       n: "+n);
										return n;
									}
								}
							}
						}
					}
					else if(coords[l]-1*turn == coords[k]){
						for(int m=0;m<coords.length;m++){
							if(m==k){
								continue;
							}
							if(coords[l]-1*turn == coords[m]){
								for(int n=0;n<coords.length;n++){
									if((n!=k) && (n!=l) && (n!=m)){
										System.out.println("k: "+k+" l: "+l+" m: "+m+"       n: "+n);
										return n;
									}
								}
							}
						}
					}
					System.out.println("UPS2");
				}
				else{
					System.out.println("UPS!!!");
				}
				
			}
		}
		return -5;
		
		if(coords0+1*turn == coords1){
			System.out.println("5");
			if(coords0+1*turn == coords2){
				System.out.println("5a");
				return it3;
			}
			if(coords0+1*turn == coords3){
				System.out.println("5b");
				return it2;
			}
			System.out.println("5c");
			return it1;
		}
		if(coords0+1*turn == coords2){
			System.out.println("5d");
			if(coords0+1*turn == coords3){
				System.out.println("5e");
				return it1;
			}
			System.out.println("5f");
			return it2;
		}
		
		
		if(coords1+1*turn == coords2){
			System.out.println("6");
			if(coords1+1*turn == coords3){
				System.out.println("6a");
				return it0;
			}
			if(coords1+1*turn == coords0){
				System.out.println("6b");
				return it3;
			}
			System.out.println("6c");
			return it2;
		}
		if(coords1+1*turn == coords3){
			System.out.println("6d");
			if(coords1+1*turn == coords0){
				System.out.println("6e");
				return it2;
			}
			System.out.println("6f");
			return it3;
		}
		
		
		
		
		if(coords2+1*turn == coords3){
			System.out.println("7");
			if(coords2+1*turn == coords0){
				System.out.println("7a");
				return it1;
			}
			if(coords2+1*turn == coords1){
				System.out.println("7b");
				return it0;
			}
			System.out.println("7c");
			return it3;
		}
		if(coords2+1*turn == coords0){
			System.out.println("7d");
			if(coords2+1*turn == coords1){
				System.out.println("7e");
				return it3;
			}
			System.out.println("7f");
			return it0;
		}
		
		
		if(coords3+1*turn == coords0){
			System.out.println("8");
			if(coords3+1*turn == coords1){
				System.out.println("8a");
				return it2;
			}
			if(coords3+1*turn == coords2){
				System.out.println("8b");
				return it1;
			}
			System.out.println("8c");
			return it0;
		}
		if(coords3+1*turn == coords1){
			System.out.println("8d");
			if(coords3+1*turn == coords2){
				System.out.println("8e");
				return it0;
			}
			System.out.println("8f");
			return it1;
		}*/
		
		/*for(int j = 1; j<cellsCoordinates.length;j++){

			System.out.println("j: "+j+": "+cellsCoordinates[j]);
			
			if(coords[direct]-2*turn==coords[j]){
				System.out.println("1");
				return direct;
			}
			else if(coords[direct]-1*turn==coords[j]){
				System.out.println("2");
				if((coords[j]==coords[j+1]) || (coords[j]-1*turn==coords[j+1])){
					System.out.println("2a");
					return direct;
				}
				else if(coords[j]+2*turn==coords[j+1]){
					System.out.println("2b");
					return j+1;
				}
				else if((j<2) && (coords[j] == coords[j+2])){
					System.out.println("2c");
					return direct;
				}
			}
			else if(coords[direct]==coords[j]){
				System.out.println("3");
				if(coords[direct]+1*turn==coords[j+1]){
					System.out.println("3a");
					return j+1;
				}
				else if(coords[direct]-1*turn==coords[j+1]){
					System.out.println("3b");
					return j+2;
				}
				else if((j<2) && (coords[direct]-1*turn==coords[j+2])){
					System.out.println("3c");
					return j+1;
				}
			}
			else if(coords[direct]+2*turn==coords[j]){
				System.out.println("4");
				return j;
			}
			else if(coords[direct]+1*turn==coords[j]){
				System.out.println("5");
				if(coords[j]==coords[j+1]){
					System.out.println("5a");
					return j+2;
				}
				else if(coords[direct]==coords[j+1]){
					System.out.println("5b");
					return j;
				}
				else if(coords[direct]-1*turn==coords[j+1]){
					System.out.println("5c");
					return j;
				}
				else if(coords[direct]+1*turn==coords[j+1]){
					System.out.println("5d");
					return j+2;
				}
				else if((j<2) && (coords[j]==coords[j+2])){
					System.out.println("5e");
					return j+1;
				}

			}
			else{
				System.out.println("UNKNOWN");
			}
//			direct++;

		}*/
//		return direct;
	}
	private int checkCoordinates1(int turn, int coords0, int coords1, int coords2, int coords3, int it0) {
		if(coords0-2*turn == coords1){
//			System.out.println("1a");
			return it0;
		}
		if(coords0-2*turn == coords2){
//			System.out.println("1b");
			return it0;	
		}
		if(coords0-2*turn == coords3){
//			System.out.println("1c");
			return it0;
		}
		return -1;
	}
	private int checkCoordinates2(int turn, int coords0, int coords1, int coords2, int coords3, int it0, int it1, int it2, int it3) {
		if(coords0+1*turn == coords1){
//			System.out.println("5");
			if(coords0+1*turn == coords2){
//				System.out.println("5a");
				return it3;
			}
			if(coords0+1*turn == coords3){
//				System.out.println("5b");
				return it2;
			}
//			System.out.println("5c");
			return it1;
		}
		if(coords0+1*turn == coords2){
//			System.out.println("6");
			if(coords0+1*turn == coords3){
//				System.out.println("6a");
				return it1;
			}
			if(coords0+1*turn == coords1){
//				System.out.println("6b");
				return it3;
			}
//			System.out.println("6c");
			return it2;
		}
		return -1;
	}
	//END//
	
	
	//ADDED//
	@Override
	protected void fillTheMap(CellStateFactory cellStateFactory) {
//		System.out.println("instance langtonCell---------------------------------------------------------------------------------------------------" );
		CellCoordinates coords = initialCoordinates();
//		Map<CellCoordinates, CellState> structure = new HashMap<CellCoordinates, CellState>();
		try {
			while(hasNextCoordinates(coords)){		
				coords = nextCoordinates(coords);
				putCell(coords, cellStateFactory.initialState(coords));
			}
//			while(hasNextCoordinates(coords)){		
//				coords = nextCoordinates(coords);
//				structure.put(coords, cellStateFactory.initialState(coords));
//			}
		} catch (UndefiniedInstanceOfCellException e) {
			e.printStackTrace();
		} catch (CoordinatesOutOfBoardException e) {
			e.printStackTrace();
		}
		catch (InvalidCellStateFactoryException e) {
			e.printStackTrace();
		} catch (InvalidGameInstance e) {
			e.printStackTrace();
		} catch (InvalidCellCoordinatesInstanceException e) {
			e.printStackTrace();
		}
	}
	
//	@Override
//	public Set<CellCoordinates> cellNeighbors(CellCoordinates coords){
//		return new VonNeumanNeighborhood(1).cellNeighborhood(coords);
//	}
	
	@Override
	public Group getCellShape(Cell c) throws InvalidCellCoordinatesInstanceException {
		Group cellShape = new Group();
		if(c.coords instanceof Coords2D){
			Coords2D c2D = (Coords2D)c.coords;
			LangtonCell aState = (LangtonCell)c.state;
			
			int x = c2D.getX()*AutomatonGUI.DISTANCE_TO_NEIGHBORS-AutomatonGUI.DISTANCE_TO_NEIGHBORS/2;
			int y = c2D.getY()*AutomatonGUI.DISTANCE_TO_NEIGHBORS-AutomatonGUI.DISTANCE_TO_NEIGHBORS/2;
			
			Circle eCircle;
			
			if(aState.antState == AntState.NONE){
				eCircle = new Circle(x, y,AutomatonGUI.EXTERNAL_CELL_RADIUS, Color.BROWN);
				cellShape.getChildren().add(eCircle);
			}			
			if(aState.cellState == BinaryState.DEAD){
				Circle iCircle = new Circle(x, y, AutomatonGUI.INTERNAL_CELL_RADIUS, Color.LIGHTGREY);
				cellShape.getChildren().add(iCircle);
			}
			if(aState.antState != AntState.NONE){
//				System.out.println("draw: "+aState.antId);
				if(aState.antId%3 == 1){
					eCircle = new Circle(x, y,AutomatonGUI.EXTERNAL_CELL_RADIUS, Color.RED);
				}
				else if(aState.antId%3 == 2){
					eCircle = new Circle(x, y,AutomatonGUI.EXTERNAL_CELL_RADIUS, Color.BLUE);
				}
				else{
					eCircle = new Circle(x, y,AutomatonGUI.EXTERNAL_CELL_RADIUS, Color.GREEN);
				}
				cellShape.getChildren().add(eCircle);
			}
			return cellShape;
		}
		else if(c.coords instanceof Coords1D){
			Coords1D c1D = (Coords1D)c.coords;
			BinaryState bState = (BinaryState)c.state;
			int x = c1D.getX()*AutomatonGUI.DISTANCE_TO_NEIGHBORS-AutomatonGUI.DISTANCE_TO_NEIGHBORS/2;
			Circle eCircle = new Circle(x, AutomatonGUI.DISTANCE_TO_NEIGHBORS/2,AutomatonGUI.EXTERNAL_CELL_RADIUS, Color.BLACK);
			cellShape.getChildren().add(eCircle);
			if(bState == BinaryState.DEAD){
				Circle iCircle = new Circle(x, AutomatonGUI.DISTANCE_TO_NEIGHBORS/2,AutomatonGUI.INTERNAL_CELL_RADIUS, Color.WHITE);
				cellShape.getChildren().add(iCircle);
			}
			return cellShape;
		}
		else{
			throw new InvalidCellCoordinatesInstanceException("Unknown cell coordinates instance.");
		}
	}
	//END//
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
	
}

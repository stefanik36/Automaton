package application;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cells.Cell;
import coordinates.CellCoordinates;
import coordinates.Coords2D;
import exceptions.InvalidStructureTypeException;
import states.CellState;

public class Structure{

	public StructureType sType;
	
	public Structure(){
		sType = StructureType.SIMPLE;
	}
	
	public enum StructureType{
		SIMPLE, GLIDER;
	}

	public Map<? extends CellCoordinates, ? extends CellState> getStructure(CellCoordinates coords, Automaton game) throws InvalidStructureTypeException {
		Map<CellCoordinates,CellState> structure = new HashMap<CellCoordinates,CellState>();
		System.out.println("type: "+sType+" game: "+game);
		if(sType == StructureType.SIMPLE){
			structure.put(coords, null);
		}
		else if((sType == StructureType.GLIDER) && (game instanceof GameOfLife) && (coords instanceof Coords2D)){
			GameOfLife gameOfLife = (GameOfLife)game;
			Integer[] survivorsA  = {2, 3};
			Integer[] comeAliveA  = {3};
			List<Integer> survivors = new ArrayList<Integer>(Arrays.asList(survivorsA));
			List<Integer> comeAlive = new ArrayList<Integer>(Arrays.asList(comeAliveA));
			if((!Collections.disjoint(survivors, gameOfLife.survivors)) && (!Collections.disjoint(comeAlive, gameOfLife.comeAlive))){
				Coords2D c2D = (Coords2D)coords;
				int newX, newY;
				int boardHeight = gameOfLife.getHeight();
				int boardWidth = gameOfLife.getWidth();
				int range = 1;
				int distanceToNeighbor = 1;
				boolean boardWrapped = BoardType.boardWrapped;
				for(int vertical = -range; vertical<=range; vertical++){
					 
					for(int horizontal = -range; horizontal<=range;horizontal++){
						if((vertical == 0) && (horizontal==0)){
							continue;
						}
						if((vertical == 1) && (horizontal==1)){
							continue;
						}
						if((vertical == 1) && (horizontal==-1)){
							continue;
						}
						if((vertical == 0) && (horizontal==1)){
							continue;
						}
						newX = c2D.getX() + distanceToNeighbor*horizontal;
						
						if(gameOfLife.xIsOutOfBoard(newX)){
							if(!boardWrapped)
								continue;
							newX = wrapCoordinate(distanceToNeighbor, boardWidth, horizontal);				
						}
						newY = c2D.getY() + distanceToNeighbor*vertical;
						if(gameOfLife.yIsOutOfBoard(newY)){
							if(!boardWrapped)
								continue;
							newY = wrapCoordinate(distanceToNeighbor, boardHeight, vertical);			
						}
						CellCoordinates coords2D = new Coords2D(newX, newY);
						structure.put(coords2D, null);		
					}
				}	
			}
		}
		else{
			throw new InvalidStructureTypeException("Stucture type is invalid.");
		}
		return structure;
	}
	
	@Override
	public String toString() {
		return "Structure [sType=" + sType + "]";
	}

	public StructureType getsType() {
		return sType;
	}

	public void setSType(StructureType sType) {
		this.sType = sType;
	}

	private int wrapCoordinate(int distanceToNeighbor, int length, int position) {
		int coord;
		if(position>0)
			coord = distanceToNeighbor*position;
		else
			coord = length + distanceToNeighbor + distanceToNeighbor*position;
		return coord;
	}
}

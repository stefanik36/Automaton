package Application;

import java.util.Set;

import Exceptions.UndefiniedInstanceOfCellException;

import java.util.HashSet;

public class VonNeumanNeighborhood implements CellNeighborhood{

	public int range;
	
	public VonNeumanNeighborhood(int range) {
		setRange(range);
	}
	
	@Override
	public Set<CellCoordinates> cellNeighborhood(CellCoordinates cell) {
		int distanceToNeighbor = Automaton.DISTANCE_TO_NEIGHBORS;
		boolean boardWrapped = BoardType.boardWrapped;
		Set<CellCoordinates> cellNeighbors = new HashSet<CellCoordinates>();   // -------------------------------HASH SET ------------------------//
		
		if(cell instanceof Coords2D){//------------------------------------------------- instanceof ----------------------------------//
			Automaton2Dim gameOfLife = new GameOfLife();   //------------------ board dimension --------------------//
			Coords2D cell2D = (Coords2D) cell;
			int newX, newY;
			int i=0;
			int boardHeight = gameOfLife.getHeight();
			int boardWidth = gameOfLife.getWidth();
			for(int vertical = -range; vertical<=range; vertical++){
				 
				for(int horizontal = -i; horizontal<=i;horizontal++){
					if((vertical == 0) && (horizontal==0)){
						continue;
					}
					newX = cell2D.getX() + distanceToNeighbor*horizontal;
					if(gameOfLife.xIsOutOfBoard(newX)){
						if(!boardWrapped)
							continue;
						newX = wrapCoordinate(distanceToNeighbor, boardWidth, horizontal);				
					}
					newY = cell2D.getY() + distanceToNeighbor*vertical;
					if(gameOfLife.yIsOutOfBoard(newY)){
						if(!boardWrapped)
							continue;
						newY = wrapCoordinate(distanceToNeighbor, boardHeight, vertical);					
					}
					CellCoordinates coords = new Coords2D(newX, newY);
					cellNeighbors.add(coords);
					System.out.println(coords);
					
					
				}
				if(vertical>=0)
					i--;
				else
					i++;
			}
			
		}
		else if(cell instanceof Coords1D){
			Coords1D cell1D = (Coords1D) cell;
			int newX;
			for(int x = -range; x<=range; x++){
				if(x==0){
					continue;
				}
				newX = cell1D.getX() + distanceToNeighbor*x;
				CellCoordinates coords = new Coords1D(newX);
				cellNeighbors.add(coords);
			}	
			
		}
		else{
			try {
				throw new UndefiniedInstanceOfCellException("Instance of CellCoordinates undefinied.");
			} catch (UndefiniedInstanceOfCellException e) {
				e.printStackTrace();
			}
		}
		
		
		return cellNeighbors;
	}

	private int wrapCoordinate(int distanceToNeighbor, int length, int position) {
		int coord;
		if(position>0)
			coord = distanceToNeighbor/2 + distanceToNeighbor*position;
		else
			coord = length + distanceToNeighbor/2 + distanceToNeighbor*position;
		return coord;
	}
	
	public void setRange(int range){
		this.range = range;
	}

}

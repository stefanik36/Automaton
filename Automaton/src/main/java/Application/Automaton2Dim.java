package application;

import coordinates.CellCoordinates;
import coordinates.Coords2D;
import exceptions.CoordinatesOutOfBoardException;
import exceptions.UndefiniedInstanceOfCellException;

public abstract class Automaton2Dim extends Automaton {

	private int width = 30;
	private int height = 30;
	private int distanceToNeighbor = 1;
	
	//ADDED//
	private int firstCellX = distanceToNeighbor;
	private int lastCellX = width;
	private int firstCellY = distanceToNeighbor;
	private int lastCellY = height;
	//END//
	
	//ADDED FOR TESTS//
	public boolean getHasNextCoordinates(CellCoordinates coords) throws UndefiniedInstanceOfCellException{
		return hasNextCoordinates(coords);
	}
	public CellCoordinates getInitialCoordinates(){
		return initialCoordinates();
	}
	public CellCoordinates  getNextCoordinates(CellCoordinates coords) throws UndefiniedInstanceOfCellException, CoordinatesOutOfBoardException{
		return nextCoordinates(coords);
	}
	//END//
	
	@Override
	protected boolean hasNextCoordinates(CellCoordinates coords) throws UndefiniedInstanceOfCellException{
		if(coords instanceof Coords2D){
			if(xIsOutOfBoard(((Coords2D) coords).getX()+distanceToNeighbor)){
				if(yIsOutOfBoard(((Coords2D) coords).getY()+distanceToNeighbor)){
					return false;
				}
			}
			return true;
		}	
		else{
			throw new UndefiniedInstanceOfCellException("Incorrect instance of CellCoordinates.");
		}
	}

	@Override
	protected CellCoordinates initialCoordinates(){
		CellCoordinates coords = new Coords2D(firstCellX-distanceToNeighbor, firstCellY);
		return coords;	
	}
	
	@Override
	protected CellCoordinates nextCoordinates(CellCoordinates coords) throws UndefiniedInstanceOfCellException, CoordinatesOutOfBoardException{
		if(coords instanceof Coords2D){
			if(xIsOutOfBoard(((Coords2D) coords).getX()+distanceToNeighbor)){
				CellCoordinates newCoords = new Coords2D(firstCellX,((Coords2D) coords).getY()+distanceToNeighbor);
				return newCoords;	
			}
			CellCoordinates newCoords = new Coords2D(((Coords2D) coords).getX()+distanceToNeighbor,((Coords2D) coords).getY());
			return newCoords;	
		}
		//CHECK AGAIN COORDINATES// 
		/*if(coords instanceof Coords2D){
			if(xIsOutOfBoard(((Coords2D) coords).getX()+distanceToNeighbor)){
				if(yIsOutOfBoard(((Coords2D) coords).getY()+distanceToNeighbor)){
					throw new CoordinatesOutOfBoardException("Coordinate y out of board.");
				}
				else{
					CellCoordinates newCoords = new Coords2D(firstCellX,((Coords2D) coords).getY()+distanceToNeighbor);
					return newCoords;	
				}
			}
				
			CellCoordinates newCoords = new Coords2D(((Coords2D) coords).getX()+distanceToNeighbor,((Coords2D) coords).getY());
			return newCoords;
		}
		else if (coords instanceof Coords1D){
			int x = ((Coords1D) coords).getX()+distanceToNeighbor;
			if(!xIsOutOfBoard(x)){
				CellCoordinates newCoords = new Coords1D(x);
				return newCoords;	
			}
			else{
				throw new CoordinatesOutOfBoardException("Coordinate x out of board.");
			}
		}*/
		else{
			throw new UndefiniedInstanceOfCellException("Incorrect instance of CellCoordinates.");
		}
	}
	
	
	//DELETE//
//	protected CellCoordinates initialCoordinates(){ //-------------------initialCoordinates(CellCoordinates)--------------------//
//		CellCoordinates coords = new Coords2D(-5, 5);
//		return coords;
//	}
//	protected CellCoordinates nextCoordinates(Coords2D coords){
//		CellCoordinates newCoords = new Coords2D(-1, -1);
//		return newCoords;
//	}
	//END//
	
	
	//ADDED//
	public int getWidth() {
		return width;
	}
	public int getHeight() {
		return height;
	}
	public boolean xIsOutOfBoard(int x){
		if(x>lastCellX)
			return true;
		if(x<firstCellX)
			return true;
		return false;
	}
	public boolean yIsOutOfBoard(int y){
		if(y>lastCellY)
			return true;
		if(y<firstCellY)
			return true;
		return false;
	}
	//END//
	
	
}

package Application;

import Exceptions.CoordinatesOutOfBoardException;
import Exceptions.UndefiniedInstanceOfCellException;

public abstract class Automaton2Dim extends Automaton {

	private int width = 600;
	private int height = 600;
	
	
	//ADDED//
	private int firstCellX = Automaton.DISTANCE_TO_NEIGHBORS/2;
	private int lastCellX = width - Automaton.DISTANCE_TO_NEIGHBORS/2;
	private int firstCellY = Automaton.DISTANCE_TO_NEIGHBORS/2;
	private int lastCellY = height - Automaton.DISTANCE_TO_NEIGHBORS/2;
	//END//
	
	@Override
	public boolean hasNextCoordinates(CellCoordinates coords) throws UndefiniedInstanceOfCellException{
		if(coords instanceof Coords2D){
			
			if(xIsOutOfBoard(((Coords2D) coords).getX()+Automaton.DISTANCE_TO_NEIGHBORS)){
				if(yIsOutOfBoard(((Coords2D) coords).getY()+Automaton.DISTANCE_TO_NEIGHBORS)){
					return false;
				}
			}
			return true;
		}
//		else if (coords instanceof Coords1D){
//			if(xIsOutOfBoard(((Coords1D) coords).getX()+Automaton.DISTANCE_TO_NEIGHBORS))
//				return false;
//			else
//				return true;
//		}	
		else{
			throw new UndefiniedInstanceOfCellException("Incorrect instance of CellCoordinates.");
		}
	}
	
	@Override
	protected CellCoordinates initialCoordinates(CellCoordinates coords){
		CellCoordinates coords2 = new Coords2D(firstCellX-Automaton.DISTANCE_TO_NEIGHBORS, firstCellY);
		return coords2;	
	}
	
	@Override
	protected CellCoordinates nextCoordinates(CellCoordinates coords) throws UndefiniedInstanceOfCellException, CoordinatesOutOfBoardException{

		if(coords instanceof Coords2D){
			if(xIsOutOfBoard(((Coords2D) coords).getX()+Automaton.DISTANCE_TO_NEIGHBORS)){
				CellCoordinates newCoords = new Coords2D(firstCellX,((Coords2D) coords).getY()+Automaton.DISTANCE_TO_NEIGHBORS);
				return newCoords;	
			}
			CellCoordinates newCoords = new Coords2D(((Coords2D) coords).getX()+Automaton.DISTANCE_TO_NEIGHBORS,((Coords2D) coords).getY());
			return newCoords;	
		}
		//CHECK AGAIN COORDINATES// 
		/*if(coords instanceof Coords2D){
			if(xIsOutOfBoard(((Coords2D) coords).getX()+Automaton.DISTANCE_TO_NEIGHBORS)){
				if(yIsOutOfBoard(((Coords2D) coords).getY()+Automaton.DISTANCE_TO_NEIGHBORS)){
					throw new CoordinatesOutOfBoardException("Coordinate y out of board.");
				}
				else{
					CellCoordinates newCoords = new Coords2D(firstCellX,((Coords2D) coords).getY()+Automaton.DISTANCE_TO_NEIGHBORS);
					return newCoords;	
				}
			}
				
			CellCoordinates newCoords = new Coords2D(((Coords2D) coords).getX()+Automaton.DISTANCE_TO_NEIGHBORS,((Coords2D) coords).getY());
			return newCoords;
		}*/
//		else if (coords instanceof Coords1D){
//			int x = ((Coords1D) coords).getX()+Automaton.DISTANCE_TO_NEIGHBORS;
//			if(!xIsOutOfBoard(x)){
//				CellCoordinates newCoords = new Coords1D(x);
//				return newCoords;	
//			}
//			else{
//				throw new CoordinatesOutOfBoardException("Coordinate x out of board.");
//			}
//		}
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

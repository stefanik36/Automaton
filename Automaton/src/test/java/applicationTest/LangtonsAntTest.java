package applicationTest;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import application.LangtonsAnt;
import cells.Cell;
import coordinates.CellCoordinates;
import coordinates.Coords2D;
import exceptions.InvalidCellCoordinatesInstanceException;
import exceptions.InvalidCellStateInstance;
import exceptions.UncheckedCoordinatesException;
import states.AntState;
import states.BinaryState;
import states.CellState;
import states.LangtonCell;

public class LangtonsAntTest {

	@Test
	public void nextCellStateTest() {
		
		
		CellState noneDState = new  LangtonCell(BinaryState.DEAD, 1, AntState.NONE);
		CellState southDState = new  LangtonCell(BinaryState.DEAD, 1, AntState.SOUTH);
		CellState eastAntState = new  LangtonCell(BinaryState.ALIVE, 1, AntState.EAST);
		CellState westDState = new  LangtonCell(BinaryState.DEAD, 1, AntState.WEST);
		
		Set<Cell> neighborsStates = new HashSet<Cell>();
		neighborsStates.add(new Cell(new Coords2D(5, 5), southDState));
		neighborsStates.add(new Cell(new Coords2D(4, 6), noneDState));
		neighborsStates.add(new Cell(new Coords2D(6, 6), noneDState));
		neighborsStates.add(new Cell(new Coords2D(5, 7), noneDState));
		
		LangtonsAnt game = new LangtonsAnt();
		try {
			
			if(game.getNextCellState(noneDState, neighborsStates) == null){
				System.out.println("null " );
			}
			System.out.println(game.getNextCellState(noneDState, neighborsStates).toString());
			assertEquals(eastAntState.toString(), game.getNextCellState(noneDState, neighborsStates).toString());
			
		} catch (InvalidCellStateInstance e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void getDirectionTest() {
		LangtonsAnt game = new LangtonsAnt();
		CellCoordinates[] cellCoordinates = new CellCoordinates[4];
		cellCoordinates[3] = new Coords2D(5,5); 
		cellCoordinates[1] = new Coords2D(4,6); 
		cellCoordinates[2] = new Coords2D(6,6); 
		cellCoordinates[0] = new Coords2D(5,7); 
		try {
//			System.out.println("-------North: "+game.getNorth(cellCoordinates));
			assertEquals(3,game.getNorth(cellCoordinates));
			
//			System.out.println("-------South: "+game.getSouth(cellCoordinates));
			assertEquals(0,game.getSouth(cellCoordinates));
			
//			System.out.println("-------West: "+game.getWest(cellCoordinates));
			assertEquals(1,game.getWest(cellCoordinates));
			
//			System.out.println("-------East: "+game.getEast(cellCoordinates));
			assertEquals(2,game.getEast(cellCoordinates));
			
			
			
		} catch (InvalidCellCoordinatesInstanceException e) {
			e.printStackTrace();
		} catch (UncheckedCoordinatesException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	@Test
	public void langtonsAntTest() {
		
	}
}

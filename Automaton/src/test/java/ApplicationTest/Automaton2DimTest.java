package applicationTest;

import static org.junit.Assert.*;

import org.junit.Test;

import application.*;
import coordinates.CellCoordinates;
import coordinates.Coords2D;
import exceptions.CoordinatesOutOfBoardException;
import exceptions.UndefiniedInstanceOfCellException;
import neighborhood.MoorNeighborhood;
import states.BinaryState;
import states.UniformStateFactory;

public class Automaton2DimTest {
	private int distanceToNeighbor = 1;
	@Test
	public void hasNextCoordinatesTest() {
		Automaton2Dim gameOfLife = new GameOfLife(new UniformStateFactory(BinaryState.DEAD), new MoorNeighborhood(1));
		CellCoordinates initCoords =  gameOfLife.getInitialCoordinates();
		CellCoordinates coordsTL = new Coords2D(-distanceToNeighbor/2, distanceToNeighbor/2);
		CellCoordinates coordsTR = new Coords2D(gameOfLife.getWidth()-distanceToNeighbor/2, distanceToNeighbor/2);
		CellCoordinates coordsBL = new Coords2D(-distanceToNeighbor/2, gameOfLife.getHeight()-distanceToNeighbor/2);
		CellCoordinates coordsBR2 = new Coords2D(gameOfLife.getWidth()-distanceToNeighbor/2, gameOfLife.getHeight()-distanceToNeighbor/2);
				
		try {
			assertEquals(true, gameOfLife.getHasNextCoordinates(initCoords));
			assertEquals(true, gameOfLife.getHasNextCoordinates(coordsTL));
			assertEquals(true, gameOfLife.getHasNextCoordinates(coordsTR));
			assertEquals(true, gameOfLife.getHasNextCoordinates(coordsBL));
			assertEquals(false, gameOfLife.getHasNextCoordinates(coordsBR2));
		} catch (UndefiniedInstanceOfCellException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void nextCoordinatesTest() {
		Automaton2Dim gameOfLife = new GameOfLife(new UniformStateFactory(BinaryState.DEAD), new MoorNeighborhood(1));
		CellCoordinates initCoords =  gameOfLife.getInitialCoordinates();
		CellCoordinates coordsTR = new Coords2D(gameOfLife.getWidth()-distanceToNeighbor/2, distanceToNeighbor/2);
		CellCoordinates coordsBL = new Coords2D(-distanceToNeighbor/2, gameOfLife.getHeight()-distanceToNeighbor/2);
		
		try {
			CellCoordinates resultInitCoords = new Coords2D(distanceToNeighbor/2, distanceToNeighbor/2);
			assertEquals(resultInitCoords.toString(), gameOfLife.getNextCoordinates(initCoords).toString());
			
			CellCoordinates resultCoordsTR = new Coords2D(distanceToNeighbor/2, distanceToNeighbor+distanceToNeighbor/2);
			assertEquals(resultCoordsTR.toString(), gameOfLife.getNextCoordinates(coordsTR).toString());
			
			CellCoordinates resultCoordsBL = new Coords2D(distanceToNeighbor/2,gameOfLife.getHeight()-distanceToNeighbor/2);
			assertEquals(resultCoordsBL.toString(), gameOfLife.getNextCoordinates(coordsBL).toString());
		} catch (UndefiniedInstanceOfCellException e) {
			e.printStackTrace();
		} catch (CoordinatesOutOfBoardException e) {
			e.printStackTrace();
		}
	}

}

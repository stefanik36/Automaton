package application;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

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
		CellCoordinates initCoords = gameOfLife.initialCoordinates();
		CellCoordinates coordsTL = new Coords2D(-distanceToNeighbor, distanceToNeighbor);
		CellCoordinates coordsTR = new Coords2D(gameOfLife.getWidth(), distanceToNeighbor);
		CellCoordinates coordsBL = new Coords2D(-distanceToNeighbor, gameOfLife.getHeight()-distanceToNeighbor);
		CellCoordinates coordsBR2 = new Coords2D(gameOfLife.getWidth(), gameOfLife.getHeight());

		try {
			assertEquals(true, gameOfLife.hasNextCoordinates(initCoords));
			assertEquals(true, gameOfLife.hasNextCoordinates(coordsTL));
			assertEquals(true, gameOfLife.hasNextCoordinates(coordsTR));
			assertEquals(true, gameOfLife.hasNextCoordinates(coordsBL));
			assertEquals(false, gameOfLife.hasNextCoordinates(coordsBR2));
		} catch (UndefiniedInstanceOfCellException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void nextCoordinatesTest() {
		Automaton2Dim gameOfLife = new GameOfLife(new UniformStateFactory(BinaryState.DEAD), new MoorNeighborhood(1));
		CellCoordinates initCoords = gameOfLife.initialCoordinates();
		CellCoordinates coordsTR = new Coords2D(gameOfLife.getWidth(), distanceToNeighbor);
		CellCoordinates coordsBL = new Coords2D(-distanceToNeighbor, gameOfLife.getHeight() - distanceToNeighbor);

		try {
			CellCoordinates resultInitCoords = new Coords2D(distanceToNeighbor, distanceToNeighbor);
			assertEquals(resultInitCoords.toString(), gameOfLife.nextCoordinates(initCoords).toString());

			CellCoordinates resultCoordsTR = new Coords2D(distanceToNeighbor, distanceToNeighbor + distanceToNeighbor);
			assertEquals(resultCoordsTR.toString(), gameOfLife.nextCoordinates(coordsTR).toString());

			CellCoordinates resultCoordsBL = new Coords2D(distanceToNeighbor, gameOfLife.getHeight());
			assertEquals(resultCoordsBL.toString(), gameOfLife.nextCoordinates(coordsBL).toString());
		} catch (UndefiniedInstanceOfCellException e) {
			e.printStackTrace();
		} catch (CoordinatesOutOfBoardException e) {
			e.printStackTrace();
		}
	}

}

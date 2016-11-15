package ApplicationTest;

import static org.junit.Assert.*;

import org.junit.Test;

import Application.*;
import Exceptions.UndefiniedInstanceOfCellException;

public class Automaton2DimTest {

	@Test
	public void hasNextCoordinatesTest() {
		Automaton2Dim gameOfLife = new GameOfLife();
		CellCoordinates coordsTL = new Coords2D(-Automaton.DISTANCE_TO_NEIGHBORS/2, Automaton.DISTANCE_TO_NEIGHBORS/2);
		CellCoordinates coordsTR = new Coords2D(gameOfLife.getWidth()-Automaton.DISTANCE_TO_NEIGHBORS/2, Automaton.DISTANCE_TO_NEIGHBORS/2);
		CellCoordinates coordsBL = new Coords2D(-Automaton.DISTANCE_TO_NEIGHBORS/2, gameOfLife.getHeight()-Automaton.DISTANCE_TO_NEIGHBORS/2);
		CellCoordinates coordsBR2 = new Coords2D(gameOfLife.getWidth()-Automaton.DISTANCE_TO_NEIGHBORS/2, gameOfLife.getHeight()-Automaton.DISTANCE_TO_NEIGHBORS/2);
				
		try {
			assertEquals(true, gameOfLife.hasNextCoordinates(coordsTL));
			assertEquals(true, gameOfLife.hasNextCoordinates(coordsTR));
			assertEquals(true, gameOfLife.hasNextCoordinates(coordsBL));
			assertEquals(false, gameOfLife.hasNextCoordinates(coordsBR2));
		} catch (UndefiniedInstanceOfCellException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

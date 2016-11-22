package applicationTest;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import application.BinaryState;
import application.Cell;
import application.CellState;
import application.GameOfLife;
import coordinates.Coords2D;
import exceptions.InvalidCellStateInstance;

public class GameOfLifeTest {

	@Test
	public void nextCellStateTest() {
		CellState deadState = new BinaryState(BinaryState.BState.DEAD);
		CellState aliveState = new BinaryState(BinaryState.BState.ALIVE);
		Set<Cell> neighborsStates = new HashSet<Cell>();
		neighborsStates.add(new Cell(new Coords2D(15, 15), deadState));
		GameOfLife game = new GameOfLife();
		try {
			assertEquals(deadState.toString(), game.getNextCellState(deadState, neighborsStates).toString());
			assertEquals(deadState.toString(), game.getNextCellState(aliveState, neighborsStates).toString());
			neighborsStates.add(new Cell(new Coords2D(15, 15), deadState));
			neighborsStates.add(new Cell(new Coords2D(15, 15), aliveState));
			neighborsStates.add(new Cell(new Coords2D(15, 15), aliveState));
			neighborsStates.add(new Cell(new Coords2D(15, 15), aliveState));
			assertEquals(aliveState.toString(), game.getNextCellState(deadState, neighborsStates).toString());
			assertEquals(aliveState.toString(), game.getNextCellState(aliveState, neighborsStates).toString());
		} catch (InvalidCellStateInstance e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

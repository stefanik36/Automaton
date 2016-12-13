package application;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import application.GameOfLife;
import application.QuadLife;
import cells.Cell;
import coordinates.Coords2D;
import exceptions.InvalidCellStateInstance;
import neighborhood.MoorNeighborhood;
import states.BinaryState;
import states.CellState;
import states.QuadState;
import states.UniformStateFactory;

public class GameOfLifeTest {

	@Test
	public void nextCellStateTest() {
		CellState deadState = BinaryState.DEAD;
		CellState aliveState = BinaryState.ALIVE;
		Set<Cell> neighborsStates = new HashSet<Cell>();
		neighborsStates.add(new Cell(new Coords2D(15, 15), deadState));
		GameOfLife game = new GameOfLife(new UniformStateFactory(BinaryState.DEAD), new MoorNeighborhood(1));
		try {
			assertEquals(deadState.toString(), game.nextCellState(deadState, neighborsStates).toString());
			assertEquals(deadState.toString(), game.nextCellState(aliveState, neighborsStates).toString());
			neighborsStates.add(new Cell(new Coords2D(15, 15), deadState));
			neighborsStates.add(new Cell(new Coords2D(15, 15), aliveState));
			neighborsStates.add(new Cell(new Coords2D(15, 15), aliveState));
			neighborsStates.add(new Cell(new Coords2D(15, 15), aliveState));
			assertEquals(aliveState.toString(), game.nextCellState(deadState, neighborsStates).toString());
			assertEquals(aliveState.toString(), game.nextCellState(aliveState, neighborsStates).toString());
		} catch (InvalidCellStateInstance e) {
			e.printStackTrace();
		}
	}

	@Test
	public void nextCellStateQuadLifeTest() {
		CellState deadState = QuadState.DEAD;
		CellState aliveState = QuadState.RED;
		Set<Cell> neighborsStates = new HashSet<Cell>();
		neighborsStates.add(new Cell(new Coords2D(15, 15), deadState));
		QuadLife game = new QuadLife(new UniformStateFactory(QuadState.DEAD), new MoorNeighborhood(1));
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
			e.printStackTrace();
		}
	}

}

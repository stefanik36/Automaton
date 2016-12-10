package applicationTest;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import application.GameOfLife;
import cells.Cell;
import coordinates.Coords2D;
import exceptions.InvalidCellStateInstance;
import neighborhood.MoorNeighborhood;
import states.BinaryState;
import states.CellState;
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
	
	@Test
	public void constructorTest(){
		GameOfLife game = new GameOfLife(new UniformStateFactory(BinaryState.DEAD), new MoorNeighborhood(1));
		
		
	}

}

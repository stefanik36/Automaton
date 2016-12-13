package application;

import org.junit.Test;
import exceptions.InvalidCellCoordinatesInstanceException;
import exceptions.InvalidCellStateInstance;
import exceptions.InvalidGameInstance;
import exceptions.NewStateIteratorHasNotNextException;
import neighborhood.CellNeighborhood;
import neighborhood.VonNeumanNeighborhood;
import states.BinaryState;
import states.CellStateFactory;
import states.UniformStateFactory;
public class AutomatonTest {

	@Test
	public void nextStateTest() {
		CellNeighborhood cellNeighborhood = new VonNeumanNeighborhood(1);
		CellStateFactory cellStateFactory = new UniformStateFactory(BinaryState.DEAD);
		Automaton game = new GameOfLife(cellStateFactory, cellNeighborhood);
		try {
			Automaton newGame = game.nextState();
			for (int i = 0; i < 2; i++) {
				newGame = newGame.nextState();
			}

		} catch (InvalidCellStateInstance e) {
			e.printStackTrace();
		} catch (NewStateIteratorHasNotNextException e) {
			e.printStackTrace();
		} catch (InvalidCellCoordinatesInstanceException e) {
			e.printStackTrace();
		} catch (InvalidGameInstance e) {
			e.printStackTrace();
		}
	}
}

package application;

import org.junit.Test;

import exceptions.InvalidCellCoordinatesInstanceException;
import exceptions.InvalidCellStateInstance;
import exceptions.InvalidGameInstance;
import exceptions.NewStateIteratorHasNotNextException;
import neighborhood.MoorNeighborhood;
import states.BinaryState;
import states.GeneralStateFactory;
import states.UniformStateFactory;

public class EfficiencyTest {

	@Test
	public void test() {
		Automaton game = new GameOfLife(new GeneralStateFactory(new GameOfLife(new UniformStateFactory(BinaryState.DEAD), new MoorNeighborhood(1))), new MoorNeighborhood(1));
		for(int i=0; i<1000; i++){
			try {
				game = game.nextState();
				System.out.println("iteration: "+game);
			} catch (InvalidCellStateInstance | NewStateIteratorHasNotNextException
					| InvalidCellCoordinatesInstanceException | InvalidGameInstance e) {
				e.printStackTrace();
			}
		}
	}

}

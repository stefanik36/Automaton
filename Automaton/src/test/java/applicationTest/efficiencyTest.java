package applicationTest;

import static org.junit.Assert.*;

import org.junit.Test;

import application.Automaton;
import application.GameOfLife;
import exceptions.InvalidCellCoordinatesInstanceException;
import exceptions.InvalidCellStateInstance;
import exceptions.InvalidGameInstance;
import exceptions.NewStateIteratorHasNotNextException;
import neighborhood.MoorNeighborhood;
import states.BinaryState;
import states.GeneralStateFactory;
import states.UniformStateFactory;

public class efficiencyTest {

	@Test
	public void test() {
		Automaton game = new GameOfLife(new GeneralStateFactory(new GameOfLife(new UniformStateFactory(BinaryState.DEAD), new MoorNeighborhood(1))), new MoorNeighborhood(1));
		while(true){
			try {
				game = game.nextState();
				System.out.println("iteration: "+game);
			} catch (InvalidCellStateInstance | NewStateIteratorHasNotNextException
					| InvalidCellCoordinatesInstanceException | InvalidGameInstance e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}

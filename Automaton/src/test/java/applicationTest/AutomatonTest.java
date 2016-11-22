package applicationTest;

import org.junit.Test;

import application.Automaton;
import application.CellStateFactory;
import application.GameOfLife;
import application.UniformStateFactory;
import exceptions.InvalidCellStateInstance;
import exceptions.NewStateIteratorHasNotNextException;
import neighborhood.CellNeighborhood;
import neighborhood.VonNeumanNeighborhood;

public class AutomatonTest {

	@Test
	public void nextStateTest() {
		GameOfLife init = new GameOfLife();
		CellNeighborhood cellNeighborhood = new VonNeumanNeighborhood(1);
		CellStateFactory cellStateFactory = new UniformStateFactory();
		Automaton game = init.newGameOfLife(cellStateFactory, cellNeighborhood);
		try {
			Automaton newGame = game.nextState();
			for(int i = 0; i<2;i++){
//				System.out.println();
//				System.out.println("------------new instance:---------------");
				newGame = newGame.nextState();
			}
			
			
		} catch (InvalidCellStateInstance e) {
			e.printStackTrace();
		} catch (NewStateIteratorHasNotNextException e) {
			e.printStackTrace();
		}
	}
}

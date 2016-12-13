package applicationTest;

import org.junit.Test;

import application.Automaton;
import application.GameOfLife;
import exceptions.InvalidCellCoordinatesInstanceException;
import exceptions.InvalidCellStateInstance;
import exceptions.InvalidGameInstance;
import exceptions.NewStateIteratorHasNotNextException;
import neighborhood.CellNeighborhood;
import neighborhood.MoorNeighborhood;
import neighborhood.VonNeumanNeighborhood;
import states.BinaryState;
import states.CellStateFactory;
import states.UniformStateFactory;

public class AutomatonTest {

	@Test
	public void nextStateTest() {
		GameOfLife init = new GameOfLife(new UniformStateFactory(BinaryState.DEAD), new MoorNeighborhood(1));
		CellNeighborhood cellNeighborhood = new VonNeumanNeighborhood(1);
		CellStateFactory cellStateFactory = new UniformStateFactory(BinaryState.DEAD);
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
		} catch (InvalidCellCoordinatesInstanceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidGameInstance e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

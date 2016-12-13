package application;

import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import application.Structure.StructureType;
import cells.Cell;
import coordinates.CellCoordinates;

import static org.mockito.Mockito.*;

import java.util.Map;
import java.util.Set;

import static org.mockito.ArgumentMatchers.*;

import exceptions.CoordinatesOutOfBoardException;
import exceptions.InvalidCellCoordinatesInstanceException;
import exceptions.InvalidCellStateInstance;
import exceptions.InvalidGameInstance;
import exceptions.InvalidStructureTypeException;
import exceptions.NewStateIteratorHasNotNextException;
import exceptions.UndefiniedInstanceOfCellException;
import javafx.scene.Group;
import neighborhood.CellNeighborhood;
import neighborhood.VonNeumanNeighborhood;
import states.BinaryState;
import states.CellState;
import states.CellStateFactory;
import states.UniformStateFactory;
public class AutomatonTest {


	@Mock Automaton mock = new Automaton() {
		
		@Override
		public void setStructureType(Structure structureType) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void setStructure(StructureType struct) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		protected CellCoordinates nextCoordinates(CellCoordinates coords)
				throws UndefiniedInstanceOfCellException, CoordinatesOutOfBoardException {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		protected CellState nextCellState(CellState currentState, Set<Cell> neighborsStates)
				throws InvalidCellStateInstance {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		protected Automaton newInstance(CellStateFactory cellStateFactory, CellNeighborhood cellNeighboorhood) {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		protected CellCoordinates initialCoordinates() {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		protected boolean hasNextCoordinates(CellCoordinates coords) throws UndefiniedInstanceOfCellException {
			// TODO Auto-generated method stub
			return false;
		}
		
		@Override
		public StructureType getStructureType() {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public Map<? extends CellCoordinates, ? extends CellState> getStructure(CellCoordinates cellCoords)
				throws InvalidStructureTypeException, InvalidGameInstance {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public Group getCellShape(Cell c) throws InvalidCellCoordinatesInstanceException {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public void fillTheMap(CellStateFactory cellStateFactory) {
			// TODO Auto-generated method stub
			
		}
	};
	
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
			
			Mockito.verify(mock, times(4)).nextState();
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

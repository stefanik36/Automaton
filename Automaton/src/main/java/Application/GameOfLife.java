package Application;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import squares.Squares;

import java.util.Arrays;
public class GameOfLife extends Automaton2Dim{

	//ADDED//
	public Integer[] survivorsA  = {2, 3};
	public Integer[] comeAliveA  = {3};
	public List<Integer> survivors = new ArrayList<Integer>(Arrays.asList(survivorsA));
	public List<Integer> comeAlive = new ArrayList<Integer>(Arrays.asList(comeAliveA));
	//END//
	
	
	@Override
	protected Automaton newInstance(CellStateFactory cellStateFactory, CellNeighborhood cellNeighboorhood) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected CellState nextCellState(CellState currentState, Set<Cell> neighborsStates) {
		
		if(currentState instanceof BinaryState){
			BinaryState state = (BinaryState)currentState;
			if(state.equals(BinaryState.binaryState.ALIVE)){
				for(int survivor : survivors){
					if(neighborsStates.size()==survivor){
						
					}
				}
			}
			else{
				
			}
		}
		else{
			
		}
		
		for(int survivor : survivors){
			
			
			if(neighbors == dead){
				//System.out.println("dead neig: "+neighbors);
				deadIndividuals.addIndividual(specimen);
			}	
		}
		
		
		return null
	}

}

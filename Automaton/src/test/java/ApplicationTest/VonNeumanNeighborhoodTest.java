package ApplicationTest;

import static org.junit.Assert.*;

import org.junit.Test;

import Application.*;

public class VonNeumanNeighborhoodTest {

	@Test
	public void loopTestFor2D() {
		int range = 2;
		CellNeighborhood neighborsStrategy = new VonNeumanNeighborhood(range);
		CellCoordinates cellC= new Coords2D(5,45);
		int numberOfNeighbors  = 2*range*(range+1);
		
		assertEquals(numberOfNeighbors, neighborsStrategy.cellNeighborhood(cellC).size());
		System.out.println("Number of neighbors: "+numberOfNeighbors);
	}
	
	@Test
	public void loopTestFor1D() {
		int range = 2;
		CellNeighborhood neighborsStrategy = new VonNeumanNeighborhood(range);
		CellCoordinates cellC= new Coords1D(20);
		int numberOfNeighbors  = 2*range;
		
		assertEquals(numberOfNeighbors, neighborsStrategy.cellNeighborhood(cellC).size());
		System.out.println("Number of neighbors: "+numberOfNeighbors);
	}

}

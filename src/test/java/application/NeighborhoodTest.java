package application;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import coordinates.CellCoordinates;
import coordinates.Coords1D;
import coordinates.Coords2D;
import neighborhood.CellNeighborhood;
import neighborhood.MoorNeighborhood;
import neighborhood.VonNeumanNeighborhood;

public class NeighborhoodTest {

	@Test
	public void VNloopTestFor2D() {
		int range = 2;
		CellNeighborhood neighborsStrategy = new VonNeumanNeighborhood(range);
		CellCoordinates cellC= new Coords2D(5,45);
		int numberOfNeighbors  = 2*range*(range+1);
		assertEquals(numberOfNeighbors, neighborsStrategy.cellNeighborhood(cellC).size());
	}
	
	@Test
	public void VNloopTestFor1D() {
		int range = 2;
		CellNeighborhood neighborsStrategy = new VonNeumanNeighborhood(range);
		CellCoordinates cellC= new Coords1D(20);
		int numberOfNeighbors  = 2*range;
		assertEquals(numberOfNeighbors, neighborsStrategy.cellNeighborhood(cellC).size());
	}
	
	@Test
	public void MloopTestFor2D() {
		int range = 1;
		CellNeighborhood neighborsStrategy = new MoorNeighborhood(range);
		CellCoordinates cellC= new Coords2D(25,45);
		int numberOfNeighbors  = (range*2 + 1)*(range*2 + 1)-1;
		assertEquals(numberOfNeighbors, neighborsStrategy.cellNeighborhood(cellC).size());
	}
	
	@Test
	public void MEdgeLoopTestFor2D() {
		int range = 1;
		CellNeighborhood neighborsStrategy = new MoorNeighborhood(range);
		CellCoordinates cellC= new Coords2D(0,5);
		int numberOfNeighbors  = (range*2 + 1)*(range*2 + 1)-1;
		assertEquals(numberOfNeighbors, neighborsStrategy.cellNeighborhood(cellC).size());
	}
	
	@Test
	public void MloopTestFor1D() {
		int range = 2;
		CellNeighborhood neighborsStrategy = new MoorNeighborhood(range);
		CellCoordinates cellC= new Coords1D(20);
		int numberOfNeighbors  = 2*range;
		assertEquals(numberOfNeighbors, neighborsStrategy.cellNeighborhood(cellC).size());
	}

}

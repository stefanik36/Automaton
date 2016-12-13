package neighborhood;

import java.util.HashSet;
import java.util.Set;

import application.Automaton2Dim;
import application.BoardType;
import application.GameOfLife;
import coordinates.CellCoordinates;
import coordinates.Coords1D;
import coordinates.Coords2D;
import exceptions.UndefiniedInstanceOfCellException;
import states.BinaryState;
import states.UniformStateFactory;

public class VonNeumanNeighborhood implements CellNeighborhood {

	// ADDED//
	public int range;
	private int distanceToNeighbor = 1;

	public VonNeumanNeighborhood(int range) {
		setRange(range);
	}

	public void setRange(int range) {
		this.range = range;
	}

	@Override
	public int getNumberOfNeighbors() {
		return 2 * range * (range + 1);
	}
	// END//

	@Override
	public Set<CellCoordinates> cellNeighborhood(CellCoordinates cell) {
		boolean boardWrapped = BoardType.boardWrapped;
		Set<CellCoordinates> cellNeighbors = new HashSet<CellCoordinates>();
		if (cell instanceof Coords2D) {
			Automaton2Dim gameOfLife = new GameOfLife(new UniformStateFactory(BinaryState.DEAD), new MoorNeighborhood(1));
			Coords2D cell2D = (Coords2D) cell;
			int newX, newY;
			int i = 0;
			int boardHeight = gameOfLife.getHeight();
			int boardWidth = gameOfLife.getWidth();
			for (int vertical = -range; vertical <= range; vertical++) {

				for (int horizontal = -i; horizontal <= i; horizontal++) {
					if ((vertical == 0) && (horizontal == 0)) {
						continue;
					}
					newX = cell2D.getX() + distanceToNeighbor * horizontal;
					if (gameOfLife.xIsOutOfBoard(newX)) {
						if (!boardWrapped)
							continue;
						newX = wrapCoordinate(distanceToNeighbor, boardWidth, horizontal);
					}
					newY = cell2D.getY() + distanceToNeighbor * vertical;
					if (gameOfLife.yIsOutOfBoard(newY)) {
						if (!boardWrapped)
							continue;
						newY = wrapCoordinate(distanceToNeighbor, boardHeight, vertical);
					}
					CellCoordinates coords = new Coords2D(newX, newY);
					cellNeighbors.add(coords);
				}
				if (vertical >= 0)
					i--;
				else
					i++;
			}

		} else if (cell instanceof Coords1D) {
			Coords1D cell1D = (Coords1D) cell;
			int newX;
			for (int x = -range; x <= range; x++) {
				if (x == 0) {
					continue;
				}
				newX = cell1D.getX() + distanceToNeighbor * x;
				CellCoordinates coords = new Coords1D(newX);
				cellNeighbors.add(coords);
			}
		} else {
			try {
				throw new UndefiniedInstanceOfCellException("Instance of CellCoordinates undefinied.");
			} catch (UndefiniedInstanceOfCellException e) {
				e.printStackTrace();
			}
		}
		return cellNeighbors;
	}
}

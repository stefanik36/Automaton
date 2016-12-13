package application;

import coordinates.CellCoordinates;
import coordinates.Coords1D;
import exceptions.CoordinatesOutOfBoardException;
import exceptions.UndefiniedInstanceOfCellException;

public abstract class Automaton1Dim extends Automaton {

	private int width = 40;
	private int distanceToNeighbor = 1;
	private int firstCellX = distanceToNeighbor;
	private int lastCellX = width;

	@Override
	protected boolean hasNextCoordinates(CellCoordinates coords) throws UndefiniedInstanceOfCellException {
		if (coords instanceof Coords1D) {
			if (xIsOutOfBoard(((Coords1D) coords).getX() + distanceToNeighbor)) {
				return false;
			}
		} else {
			throw new UndefiniedInstanceOfCellException("Incorrect instance of CellCoordinates.");
		}
		return true;
	}

	@Override
	protected CellCoordinates initialCoordinates() {
		CellCoordinates coords = new Coords1D(firstCellX - distanceToNeighbor);
		return coords;
	}

	@Override
	protected CellCoordinates nextCoordinates(CellCoordinates coords)
			throws UndefiniedInstanceOfCellException, CoordinatesOutOfBoardException {
		return new Coords1D(((Coords1D) coords).getX() + distanceToNeighbor);
	}

	public int getWidth() {
		return width;
	}

	public boolean xIsOutOfBoard(int x) {
		if (x > lastCellX)
			return true;
		if (x < firstCellX)
			return true;
		return false;
	}

}

package coordinates;

import exceptions.InvalidCellCoordinatesInstanceException;
import gui.AutomatonGUI;

public class PointPosition {
	public static int leftEdge = 0;
	public static int rightEdge = AutomatonGUI.AUTOMATON_WIDTH;
	public static int topEdge = 0;
	public static int bottomEdge = AutomatonGUI.AUTOMATON_HEIGHT;

	public static CellCoordinates setCellCoordinates(int x, int y) throws InvalidCellCoordinatesInstanceException {
		x = (x - leftEdge);
		y = (y - topEdge);
		int posX = setCellPosition(x, true);
		int posY = setCellPosition(y, false);
		if ((posX == -1) || (posY == -1)) {
			return null;
		}
		posX = posX / AutomatonGUI.DISTANCE_TO_NEIGHBORS + 1;
		posY = posY / AutomatonGUI.DISTANCE_TO_NEIGHBORS + 1;
		return new Coords2D(posX, posY);
	}

	public static CellCoordinates setCellCoordinates1D(int x, int y) throws InvalidCellCoordinatesInstanceException {
		Coords2D c2D = (Coords2D) setCellCoordinates(x, y);
		if (c2D == null) {
			return null;
		}
		CellCoordinates c1D = new Coords1D(c2D.getX());
		return c1D;
	}

	private static int setCellPosition(int x, boolean horizontal) {
		int startEdge;
		int endEdge;
		if (horizontal) {
			startEdge = 0;
			endEdge = rightEdge;
		} else {
			startEdge = 0;
			endEdge = bottomEdge;
		}

		if (IsOnBoard(x, startEdge, endEdge)) {
			int posX = AutomatonGUI.EXTERNAL_CELL_RADIUS;
			int left = startEdge;
			int right = startEdge + 2 * AutomatonGUI.EXTERNAL_CELL_RADIUS;
			while (right <= endEdge) {
				if (x >= left) {
					if (x <= right) {
						return posX;
					}
				}
				left = left + AutomatonGUI.DISTANCE_TO_NEIGHBORS;
				right = right + AutomatonGUI.DISTANCE_TO_NEIGHBORS;
				posX = posX + AutomatonGUI.DISTANCE_TO_NEIGHBORS;
			}
		}
		return -1;
	}

	public static boolean IsOnBoard(int x, int startEdge, int endEdge) {
		if (x >= startEdge) {
			if (x <= endEdge) {
				if (!intoBreak(x, startEdge, endEdge)) {
					return true;
				}
			}
		}
		return false;
	}

	public static boolean intoBreak(int x, int startEdge, int endEdge) {
		int left = startEdge;
		int right = startEdge + 2 * AutomatonGUI.EXTERNAL_CELL_RADIUS;
		while (right <= endEdge) {
			if (x >= left) {
				if (x <= right) {
					return false;
				}
			}
			left = left + AutomatonGUI.DISTANCE_TO_NEIGHBORS;
			right = right + AutomatonGUI.DISTANCE_TO_NEIGHBORS;
		}
		return true;
	}
}

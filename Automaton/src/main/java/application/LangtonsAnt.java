package application;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import application.Structure.StructureType;
import cells.Cell;
import coordinates.CellCoordinates;
import coordinates.Coords1D;
import coordinates.Coords2D;
import exceptions.CoordinatesOutOfBoardException;
import exceptions.InvalidCellCoordinatesInstanceException;
import exceptions.InvalidCellStateFactoryException;
import exceptions.InvalidCellStateInstance;
import exceptions.InvalidGameInstance;
import exceptions.InvalidStructureTypeException;
import exceptions.UncheckedCoordinatesException;
import exceptions.UndefiniedInstanceOfCellException;
import gui.AutomatonGUI;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import neighborhood.CellNeighborhood;
import states.AntState;
import states.BinaryState;
import states.CellState;
import states.CellStateFactory;
import states.LangtonCell;

public class LangtonsAnt extends Automaton2Dim {

	// ADDED//
	public static List<LangtonCell> ants = new ArrayList<LangtonCell>();
	private Structure structureType;
	// END//

	public LangtonsAnt() {

	}

	public LangtonsAnt(CellStateFactory cellStateFactory, CellNeighborhood cellNeighboorhood) {
		setNeighborhoodAndstateFactory(cellNeighboorhood, cellStateFactory);
		fillTheMap(cellStateFactory);
		structureType = new Structure();
		structureType.setSType(StructureType.SIMPLE);
	}

	@Override
	protected Automaton newInstance(CellStateFactory stateFactory, CellNeighborhood neighborsStrategy) {

		Automaton game = new LangtonsAnt();
		game.setNeighborhoodAndstateFactory(neighborsStrategy, stateFactory);
		game.fillTheMap(stateFactory);
		game.setStructureType(structureType);
		return game;
	}

	@Override
	protected CellState nextCellState(CellState currentState, Set<Cell> neighborsStates)
			throws InvalidCellStateInstance {

		if (currentState instanceof LangtonCell) {
			LangtonCell state = (LangtonCell) currentState;
			if (state.antState == AntState.NONE) {
				CellCoordinates[] cellsCoordinates = new CellCoordinates[neighborsStates.size()];
				LangtonCell[] cellsStates = new LangtonCell[neighborsStates.size()];
				int i = 0;
				for (Cell c : neighborsStates) {
					if (!(c.state instanceof LangtonCell)) {
						throw new InvalidCellStateInstance("Cell state should be instance of LangtonCell");
					}
					if ((c.coords instanceof Coords2D) || (c.coords instanceof Coords1D)) {
						cellsCoordinates[i] = c.coords;
					} else {
						try {
							throw new InvalidCellCoordinatesInstanceException("Unknown cell coordinates instance.");
						} catch (InvalidCellCoordinatesInstanceException e) {
							e.printStackTrace();
						}
					}
					cellsCoordinates[i] = c.coords;
					cellsStates[i] = (LangtonCell) c.state;
					i++;

				}
				for (int it = 0; it < cellsCoordinates.length; it++) {
					if (cellsStates[it].antState == AntState.NONE) {
						continue;
					}
					if (cellsStates[it].antState == AntState.NORTH) {
						try {
							if (it == getSouth(cellsCoordinates)) {
								if (state.cellState == BinaryState.DEAD) {
									return new LangtonCell(BinaryState.ALIVE, state.antId, AntState.WEST);
								} else {
									return new LangtonCell(BinaryState.DEAD, state.antId, AntState.EAST);
								}
							}
						} catch (InvalidCellCoordinatesInstanceException e) {
							e.printStackTrace();
						} catch (UncheckedCoordinatesException e) {
							e.printStackTrace();
						}
					}
					if (cellsStates[it].antState.equals(AntState.EAST)) {
						try {
							if (it == getWest(cellsCoordinates)) {
								if (state.cellState == BinaryState.DEAD) {
									return new LangtonCell(BinaryState.ALIVE, state.antId, AntState.NORTH);
								} else {
									return new LangtonCell(BinaryState.DEAD, state.antId, AntState.SOUTH);
								}
							}
						} catch (InvalidCellCoordinatesInstanceException e) {
							e.printStackTrace();
						} catch (UncheckedCoordinatesException e) {
							e.printStackTrace();
						}
					}
					if (cellsStates[it].antState.equals(AntState.SOUTH)) {

						try {
							if (it == getNorth(cellsCoordinates)) {
								if (state.cellState == BinaryState.DEAD) {
									return new LangtonCell(BinaryState.ALIVE, state.antId, AntState.EAST);
								} else {
									return new LangtonCell(BinaryState.DEAD, state.antId, AntState.WEST);
								}
							}
						} catch (InvalidCellCoordinatesInstanceException e) {
							e.printStackTrace();
						} catch (UncheckedCoordinatesException e) {
							e.printStackTrace();
						}
					}
					if (cellsStates[it].antState == AntState.WEST) {

						try {
							if (it == getEast(cellsCoordinates)) {
								if (state.cellState == BinaryState.DEAD) {
									return new LangtonCell(BinaryState.ALIVE, state.antId, AntState.SOUTH);
								} else {
									return new LangtonCell(BinaryState.DEAD, state.antId, AntState.NORTH);
								}
							}
						} catch (InvalidCellCoordinatesInstanceException e) {
							e.printStackTrace();
						} catch (UncheckedCoordinatesException e) {
							e.printStackTrace();
						}
					}
				}
			}

			if (state.cellState == BinaryState.DEAD) {
				return new LangtonCell(BinaryState.DEAD, 0, AntState.NONE);
			} else {
				return new LangtonCell(BinaryState.ALIVE, 0, AntState.NONE);
			}
		} else {
			throw new InvalidCellStateInstance("Instance of cell state is invalid.");
		}
	}

	// ADDED//
	public int getNorth(CellCoordinates[] cellsCoordinates)
			throws InvalidCellCoordinatesInstanceException, UncheckedCoordinatesException {
		return getDirection(cellsCoordinates, -1, false);
	}

	public int getSouth(CellCoordinates[] cellsCoordinates)
			throws InvalidCellCoordinatesInstanceException, UncheckedCoordinatesException {
		return getDirection(cellsCoordinates, 1, false);
	}

	public int getEast(CellCoordinates[] cellsCoordinates)
			throws InvalidCellCoordinatesInstanceException, UncheckedCoordinatesException {
		return getDirection(cellsCoordinates, 1, true);
	}

	public int getWest(CellCoordinates[] cellsCoordinates)
			throws InvalidCellCoordinatesInstanceException, UncheckedCoordinatesException {
		return getDirection(cellsCoordinates, -1, true);
	}

	private int getDirection(CellCoordinates[] cellsCoordinates, int turn, boolean horizontal)
			throws InvalidCellCoordinatesInstanceException, UncheckedCoordinatesException {
		int[] coords = new int[cellsCoordinates.length];
		for (int i = 0; i < cellsCoordinates.length; i++) {
			if (horizontal) {
				int x;
				if (cellsCoordinates[i] instanceof Coords2D) {
					Coords2D c2D = (Coords2D) cellsCoordinates[i];
					x = c2D.getX();
				} else {
					Coords1D c1D = (Coords1D) cellsCoordinates[i];
					x = c1D.getX();
				}
				coords[i] = x;
			} else {
				int y;
				if (cellsCoordinates[i] instanceof Coords2D) {
					Coords2D c2D = (Coords2D) cellsCoordinates[i];
					y = c2D.getY();
				} else {
					throw new InvalidCellCoordinatesInstanceException(
							"For vertical direction CellCoordinates should be instance of Coords2D.");
				}
				coords[i] = y;
			}
		}

		int result;
		for (int j = 0; j < coords.length; j++) {
			result = checkCoordinates1(turn, coords[j % 4], coords[(j + 1) % 4], coords[(j + 2) % 4],
					coords[(j + 3) % 4], j % 4);
			if (result != -1) {
				return result;
			}
		}
		for (int j = 0; j < coords.length; j++) {
			result = checkCoordinates2(turn, coords[j % 4], coords[(j + 1) % 4], coords[(j + 2) % 4],
					coords[(j + 3) % 4], j % 4, (j + 1) % 4, (j + 2) % 4, (j + 3) % 4);
			if (result != -1) {
				return result;
			}
		}
		throw new UncheckedCoordinatesException("Coordinates are unchecked (LangtonsAnt getDirection())");
	}

	private int checkCoordinates1(int turn, int coords0, int coords1, int coords2, int coords3, int it0) {
		if (coords0 - 2 * turn == coords1)
			return it0;
		if (coords0 - 2 * turn == coords2)
			return it0;
		if (coords0 - 2 * turn == coords3)
			return it0;
		return -1;
	}

	private int checkCoordinates2(int turn, int coords0, int coords1, int coords2, int coords3, int it0, int it1,
			int it2, int it3) {
		if (coords0 + 1 * turn == coords1) {
			if (coords0 + 1 * turn == coords2)
				return it3;
			if (coords0 + 1 * turn == coords3)
				return it2;
			return it1;
		}
		if (coords0 + 1 * turn == coords2) {
			if (coords0 + 1 * turn == coords3)
				return it1;
			if (coords0 + 1 * turn == coords1)
				return it3;
			return it2;
		}
		return -1;
	}

	@Override
	public void fillTheMap(CellStateFactory cellStateFactory) {
		CellCoordinates coords = initialCoordinates();
		try {
			while (hasNextCoordinates(coords)) {
				coords = nextCoordinates(coords);
				putCell(coords, cellStateFactory.initialState(coords));
			}
		} catch (UndefiniedInstanceOfCellException e) {
			e.printStackTrace();
		} catch (CoordinatesOutOfBoardException e) {
			e.printStackTrace();
		} catch (InvalidCellStateFactoryException e) {
			e.printStackTrace();
		} catch (InvalidGameInstance e) {
			e.printStackTrace();
		} catch (InvalidCellCoordinatesInstanceException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Group getCellShape(Cell c) throws InvalidCellCoordinatesInstanceException {
		Group cellShape = new Group();
		if (c.coords instanceof Coords2D) {
			Coords2D c2D = (Coords2D) c.coords;
			LangtonCell aState = (LangtonCell) c.state;

			int x = c2D.getX() * AutomatonGUI.DISTANCE_TO_NEIGHBORS - AutomatonGUI.DISTANCE_TO_NEIGHBORS / 2;
			int y = c2D.getY() * AutomatonGUI.DISTANCE_TO_NEIGHBORS - AutomatonGUI.DISTANCE_TO_NEIGHBORS / 2;

			Circle eCircle;

			if (aState.antState == AntState.NONE) {
				eCircle = new Circle(x, y, AutomatonGUI.EXTERNAL_CELL_RADIUS, Color.BROWN);
				cellShape.getChildren().add(eCircle);
			}
			if (aState.cellState == BinaryState.DEAD) {
				Circle iCircle = new Circle(x, y, AutomatonGUI.INTERNAL_CELL_RADIUS, Color.LIGHTGREY);
				cellShape.getChildren().add(iCircle);
			}
			if (aState.antState != AntState.NONE) {
				eCircle = new Circle(x, y, AutomatonGUI.EXTERNAL_CELL_RADIUS, Color.RED);
				cellShape.getChildren().add(eCircle);
			}
			return cellShape;
		} else if (c.coords instanceof Coords1D) {
			Coords1D c1D = (Coords1D) c.coords;
			BinaryState bState = (BinaryState) c.state;
			int x = c1D.getX() * AutomatonGUI.DISTANCE_TO_NEIGHBORS - AutomatonGUI.DISTANCE_TO_NEIGHBORS / 2;
			Circle eCircle = new Circle(x, AutomatonGUI.DISTANCE_TO_NEIGHBORS / 2, AutomatonGUI.EXTERNAL_CELL_RADIUS,
					Color.BLACK);
			cellShape.getChildren().add(eCircle);
			if (bState == BinaryState.DEAD) {
				Circle iCircle = new Circle(x, AutomatonGUI.DISTANCE_TO_NEIGHBORS / 2,
						AutomatonGUI.INTERNAL_CELL_RADIUS, Color.WHITE);
				cellShape.getChildren().add(iCircle);
			}
			return cellShape;
		} else {
			throw new InvalidCellCoordinatesInstanceException("Unknown cell coordinates instance.");
		}
	}

	@Override
	public Map<? extends CellCoordinates, ? extends CellState> getStructure(CellCoordinates cellCoords)
			throws InvalidStructureTypeException, InvalidGameInstance {
		return structureType.getStructure(cellCoords, this);
	}

	@Override
	public StructureType getStructureType() {
		return structureType.sType;
	}

	@Override
	public void setStructureType(Structure structureType) {
		this.structureType = structureType;
	}

	@Override
	public void setStructure(StructureType struct) {
		try {
			throw new InvalidGameInstance("The game should be Game Of Life or Quad Life.");
		} catch (InvalidGameInstance e) {
			e.printStackTrace();
		}
	}
	// END//
}

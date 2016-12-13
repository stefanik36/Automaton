package application;

import java.util.ArrayList;
import java.util.Arrays;
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
import exceptions.UndefiniedInstanceOfCellException;
import gui.AutomatonGUI;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import neighborhood.CellNeighborhood;
import states.BinaryState;
import states.CellState;
import states.CellStateFactory;
import states.WireElectronState;

public class WireWorld extends Automaton2Dim {

	// ADDED//
	private Integer[] getHeadsA = { 1, 2 };
	public List<Integer> getHeads = new ArrayList<Integer>(Arrays.asList(getHeadsA));
	private Structure structureType;

	@Override
	public void setStructure(StructureType struct) {
		structureType = new Structure();
		structureType.setSType(struct);
	}

	public WireWorld() {

	}

	public WireWorld(CellStateFactory cellStateFactory, CellNeighborhood cellNeighboorhood) {
		setNeighborhoodAndstateFactory(cellNeighboorhood, cellStateFactory);
		fillTheMap(cellStateFactory);
		structureType = new Structure();
		structureType.setSType(StructureType.SIMPLE);
	}
	// END//

	@Override
	protected Automaton newInstance(CellStateFactory cellStateFactory, CellNeighborhood cellNeighboorhood) {
		Automaton game = new WireWorld();
		game.setNeighborhoodAndstateFactory(cellNeighboorhood, cellStateFactory);
		game.fillTheMap(cellStateFactory);
		game.setStructureType(structureType);
		return game;
	}

	@Override
	protected CellState nextCellState(CellState currentState, Set<Cell> neighborsStates)
			throws InvalidCellStateInstance {
		WireElectronState state;
		if (currentState instanceof WireElectronState) {
			state = (WireElectronState) currentState;
		} else {
			throw new InvalidCellStateInstance("State instance should be WireElectronState.");
		}

		if (state == WireElectronState.ELECTRON_HEAD) {
			return WireElectronState.ELECTRON_TAIL;
		} else if (state == WireElectronState.ELECTRON_TAIL) {
			return WireElectronState.WIRE;
		} else if (state == WireElectronState.WIRE) {
			int numberOfHeads = 0;
			for (Cell c : neighborsStates) {
				if (c.state.equals(WireElectronState.ELECTRON_HEAD)) {
					numberOfHeads++;
				}
			}
			for (int value : getHeads) {
				if (numberOfHeads == value) {
					return WireElectronState.ELECTRON_HEAD;
				}
			}
			return WireElectronState.WIRE;
		} else {
			return state;
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
			WireElectronState wireState = (WireElectronState) c.state;

			int x = c2D.getX() * AutomatonGUI.DISTANCE_TO_NEIGHBORS - AutomatonGUI.DISTANCE_TO_NEIGHBORS / 2;
			int y = c2D.getY() * AutomatonGUI.DISTANCE_TO_NEIGHBORS - AutomatonGUI.DISTANCE_TO_NEIGHBORS / 2;

			Circle eCircle;

			if (wireState == WireElectronState.WIRE) {
				eCircle = new Circle(x, y, AutomatonGUI.EXTERNAL_CELL_RADIUS, Color.YELLOW);
				cellShape.getChildren().add(eCircle);
			} else if (wireState == WireElectronState.ELECTRON_TAIL) {
				eCircle = new Circle(x, y, AutomatonGUI.EXTERNAL_CELL_RADIUS, Color.RED);
				cellShape.getChildren().add(eCircle);
			} else if (wireState == WireElectronState.ELECTRON_HEAD) {
				eCircle = new Circle(x, y, AutomatonGUI.EXTERNAL_CELL_RADIUS, Color.BLUE);
				cellShape.getChildren().add(eCircle);
			} else {
				eCircle = new Circle(x, y, AutomatonGUI.EXTERNAL_CELL_RADIUS, Color.DARKBLUE);
				cellShape.getChildren().add(eCircle);
				Circle iCircle = new Circle(x, y, AutomatonGUI.INTERNAL_CELL_RADIUS, Color.LIGHTGREY);
				cellShape.getChildren().add(iCircle);
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

}

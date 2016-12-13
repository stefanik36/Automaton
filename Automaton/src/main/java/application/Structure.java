package application;

import java.util.HashMap;
import java.util.Map;

import coordinates.CellCoordinates;
import coordinates.Coords2D;
import exceptions.InvalidGameInstance;
import exceptions.InvalidStructureTypeException;
import states.CellState;

public class Structure {

	public StructureType sType;

	public Structure() {
		sType = StructureType.SIMPLE;
	}

	public enum StructureType {
		SIMPLE, GLIDER, G_GUN, EOR_GATE;
	}

	public Map<? extends CellCoordinates, ? extends CellState> getStructure(CellCoordinates coords, Automaton game)
			throws InvalidStructureTypeException, InvalidGameInstance {
		Map<CellCoordinates, CellState> structure = new HashMap<CellCoordinates, CellState>();
		if (sType == StructureType.SIMPLE) {
			structure.put(coords, null);
		} else if ((sType == StructureType.GLIDER) && (coords instanceof Coords2D)) {
			if (game instanceof GameOfLife) {
				createGliderGOF(coords, game, structure);
			} else if (game instanceof QuadLife) {
				createGliderQL(coords, game, structure);
			} else {
				throw new InvalidGameInstance("The game should be Game Of Life or Quad Life.");
			}
		} else if ((sType == StructureType.G_GUN) && (coords instanceof Coords2D)) {
			if (game instanceof GameOfLife) {
				createGliderGunGOF(coords, game, structure);
			} else if (game instanceof QuadLife) {
				createGliderGunQL(coords, game, structure);
			} else {
				throw new InvalidGameInstance("The game should be Game Of Life or Quad Life.");
			}
		} else if ((sType == StructureType.EOR_GATE) && (coords instanceof Coords2D)) {
			if (game instanceof WireWorld) {
				createAndWW(coords, game, structure);
			} else {
				throw new InvalidGameInstance("The game should be Game Of Life or Quad Life.");
			}
		} else {
			throw new InvalidStructureTypeException("Stucture type is invalid.");
		}
		return structure;
	}
	
	private void createAndWW(CellCoordinates coords, Automaton game, Map<CellCoordinates, CellState> structure) {
		WireWorld tmpGame = (WireWorld) game;
		int boardHeight = tmpGame.getHeight();
		int boardWidth = tmpGame.getWidth();
		createOrGate(coords, structure, boardHeight, boardWidth);
	}
	
	private void createGliderGOF(CellCoordinates coords, Automaton game, Map<CellCoordinates, CellState> structure) {
		GameOfLife tmpGame = (GameOfLife) game;
		int boardHeight = tmpGame.getHeight();
		int boardWidth = tmpGame.getWidth();
		createGlider(coords, structure, boardHeight, boardWidth);
	}

	private void createGliderQL(CellCoordinates coords, Automaton game, Map<CellCoordinates, CellState> structure) {
		QuadLife tmpGame = (QuadLife) game;
		int boardHeight = tmpGame.getHeight();
		int boardWidth = tmpGame.getWidth();
		createGlider(coords, structure, boardHeight, boardWidth);
	}

	private void createGliderGunGOF(CellCoordinates coords, Automaton game, Map<CellCoordinates, CellState> structure) {
		GameOfLife tmpGame = (GameOfLife) game;
		int boardHeight = tmpGame.getHeight();
		int boardWidth = tmpGame.getWidth();
		createGliderGun(coords, structure, boardHeight, boardWidth);
	}
	
	private void createGliderGunQL(CellCoordinates coords, Automaton game, Map<CellCoordinates, CellState> structure) {
		QuadLife tmpGame = (QuadLife) game;
		int boardHeight = tmpGame.getHeight();
		int boardWidth = tmpGame.getWidth();
		createGliderGun(coords, structure, boardHeight, boardWidth);
	}

	private void createGliderGun(CellCoordinates coords, Map<CellCoordinates, CellState> structure, int boardHeight,
			int boardWidth) {
		Coords2D c2D = (Coords2D) coords;
		int rangeV = 4;
		int rangeH = 18;
		
		for (int vertical = -rangeV; vertical <= rangeV; vertical++) {

			for (int horizontal = -rangeH; horizontal <= rangeH; horizontal++) {
				if ((vertical == 1) && (horizontal == 0)) {
					putSingleCoords(structure, boardWidth, boardHeight, c2D, vertical, horizontal);
				}
				else if ((vertical == 0) && (horizontal == -1)) {
					putSingleCoords(structure, boardWidth, boardHeight, c2D, vertical, horizontal);
				}
				else if ((vertical == 1) && (horizontal == -1)) {
					putSingleCoords(structure, boardWidth, boardHeight, c2D, vertical, horizontal);
				}
				else if ((vertical == 2) && (horizontal == -1)) {
					putSingleCoords(structure, boardWidth, boardHeight, c2D, vertical, horizontal);
				}
				else if ((vertical == -1) && (horizontal == -2)) {
					putSingleCoords(structure, boardWidth, boardHeight, c2D, vertical, horizontal);
				}
				else if ((vertical == 3) && (horizontal == -2)) {
					putSingleCoords(structure, boardWidth, boardHeight, c2D, vertical, horizontal);
				}
				else if ((vertical == 1) && (horizontal == -3)) {
					putSingleCoords(structure, boardWidth, boardHeight, c2D, vertical, horizontal);
				}
				else if ((vertical == -2) && (horizontal == -4)) {
					putSingleCoords(structure, boardWidth, boardHeight, c2D, vertical, horizontal);
				}
				else if ((vertical == 4) && (horizontal == -4)) {
					putSingleCoords(structure, boardWidth, boardHeight, c2D, vertical, horizontal);
				}
				else if ((vertical == -2) && (horizontal == -5)) {
					putSingleCoords(structure, boardWidth, boardHeight, c2D, vertical, horizontal);
				}
				else if ((vertical == 4) && (horizontal == -5)) {
					putSingleCoords(structure, boardWidth, boardHeight, c2D, vertical, horizontal);
				}
				else if ((vertical == -1) && (horizontal == -6)) {
					putSingleCoords(structure, boardWidth, boardHeight, c2D, vertical, horizontal);
				}
				else if ((vertical == 3) && (horizontal == -6)) {
					putSingleCoords(structure, boardWidth, boardHeight, c2D, vertical, horizontal);
				}
				else if ((vertical == 1) && (horizontal == -7)) {
					putSingleCoords(structure, boardWidth, boardHeight, c2D, vertical, horizontal);
				}
				else if ((vertical == 0) && (horizontal == -7)) {
					putSingleCoords(structure, boardWidth, boardHeight, c2D, vertical, horizontal);
				}
				else if ((vertical == 2) && (horizontal == -7)) {
					putSingleCoords(structure, boardWidth, boardHeight, c2D, vertical, horizontal);
				}
				else if ((vertical == 0) && (horizontal == -16)) {
					putSingleCoords(structure, boardWidth, boardHeight, c2D, vertical, horizontal);
				}
				else if ((vertical == 1) && (horizontal == -16)) {
					putSingleCoords(structure, boardWidth, boardHeight, c2D, vertical, horizontal);
				}
				else if ((vertical == 0) && (horizontal == -17)) {
					putSingleCoords(structure, boardWidth, boardHeight, c2D, vertical, horizontal);
				}
				else if ((vertical == 1) && (horizontal == -17)) {
					putSingleCoords(structure, boardWidth, boardHeight, c2D, vertical, horizontal);
				}
				else if ((vertical == 0) && (horizontal == 3)) {
					putSingleCoords(structure, boardWidth, boardHeight, c2D, vertical, horizontal);
				}
				else if ((vertical == -1) && (horizontal == 3)) {
					putSingleCoords(structure, boardWidth, boardHeight, c2D, vertical, horizontal);
				}
				else if ((vertical == -2) && (horizontal == 3)) {
					putSingleCoords(structure, boardWidth, boardHeight, c2D, vertical, horizontal);
				}
				else if ((vertical == 0) && (horizontal == 4)) {
					putSingleCoords(structure, boardWidth, boardHeight, c2D, vertical, horizontal);
				}
				else if ((vertical == -1) && (horizontal == 4)) {
					putSingleCoords(structure, boardWidth, boardHeight, c2D, vertical, horizontal);
				}
				else if ((vertical == -2) && (horizontal == 4)) {
					putSingleCoords(structure, boardWidth, boardHeight, c2D, vertical, horizontal);
				}
				else if ((vertical == 1) && (horizontal == 5)) {
					putSingleCoords(structure, boardWidth, boardHeight, c2D, vertical, horizontal);
				}
				else if ((vertical == -3) && (horizontal == 5)) {
					putSingleCoords(structure, boardWidth, boardHeight, c2D, vertical, horizontal);
				}
				else if ((vertical == 1) && (horizontal == 7)) {
					putSingleCoords(structure, boardWidth, boardHeight, c2D, vertical, horizontal);
				}
				else if ((vertical == -3) && (horizontal == 7)) {
					putSingleCoords(structure, boardWidth, boardHeight, c2D, vertical, horizontal);
				}
				else if ((vertical == 2) && (horizontal == 7)) {
					putSingleCoords(structure, boardWidth, boardHeight, c2D, vertical, horizontal);
				}
				else if ((vertical == -4) && (horizontal == 7)) {
					putSingleCoords(structure, boardWidth, boardHeight, c2D, vertical, horizontal);
				}
				else if ((vertical == -1) && (horizontal == 17)) {
					putSingleCoords(structure, boardWidth, boardHeight, c2D, vertical, horizontal);
				}
				else if ((vertical == -2) && (horizontal == 17)) {
					putSingleCoords(structure, boardWidth, boardHeight, c2D, vertical, horizontal);
				}
				else if ((vertical == -1) && (horizontal == 18)) {
					putSingleCoords(structure, boardWidth, boardHeight, c2D, vertical, horizontal);
				}
				else if ((vertical == -2) && (horizontal == 18)) {
					putSingleCoords(structure, boardWidth, boardHeight, c2D, vertical, horizontal);
				}
				continue;
			}
		}
	}
	
	private void createGlider(CellCoordinates coords, Map<CellCoordinates, CellState> structure, int boardHeight, int boardWidth) {
		Coords2D c2D = (Coords2D) coords;
		int range = 1;
		for (int vertical = -range; vertical <= range; vertical++) {

			for (int horizontal = -range; horizontal <= range; horizontal++) {
				if ((vertical == 0) && (horizontal == 0)) {
					continue;
				}
				if ((vertical == 1) && (horizontal == 1)) {
					continue;
				}
				if ((vertical == 1) && (horizontal == -1)) {
					continue;
				}
				if ((vertical == 0) && (horizontal == 1)) {
					continue;
				}
				putSingleCoords(structure, boardWidth, boardHeight, c2D, vertical, horizontal);
			}
		}
	}
	private void createOrGate(CellCoordinates coords, Map<CellCoordinates, CellState> structure, int boardHeight, int boardWidth) {
		Coords2D c2D = (Coords2D) coords;
		int rangeH = 2;
		int rangeV = 3;
		for (int vertical = -rangeV; vertical <= rangeV; vertical++) {

			for (int horizontal = -rangeH; horizontal <= rangeH; horizontal++) {
				if ((vertical == 0) && (horizontal == 0)) {
					continue;
				}
				else if ((vertical == -2) && (horizontal == 0)) {
					continue;
				}
				else if ((vertical == -3) && (horizontal == 0)) {
					continue;
				}
				else if ((vertical == 2) && (horizontal == 0)) {
					continue;
				}
				else if ((vertical == 3) && (horizontal == 0)) {
					continue;
				}
				else if ((vertical == 0) && (horizontal == -1)) {
					continue;
				}
				else if ((vertical == -3) && (horizontal == -1)) {
					continue;
				}
				else if ((vertical == 3) && (horizontal == -1)) {
					continue;
				}
				else if ((vertical == -2) && (horizontal == -2)) {
					continue;
				}
				else if ((vertical == 2) && (horizontal == -2)) {
					continue;
				}
				else if ((vertical == -3) && (horizontal == 1)) {
					continue;
				}
				else if ((vertical == 3) && (horizontal == 1)) {
					continue;
				}
				else if ((vertical == -2) && (horizontal == 1)) {
					continue;
				}
				else if ((vertical == 2) && (horizontal == 1)) {
					continue;
				}
				else if ((vertical == -3) && (horizontal == 2)) {
					continue;
				}
				else if ((vertical == 3) && (horizontal == 2)) {
					continue;
				}
				else if ((vertical == -2) && (horizontal == 2)) {
					continue;
				}
				else if ((vertical == 2) && (horizontal == 2)) {
					continue;
				}
				else if ((vertical == -1) && (horizontal == 2)) {
					continue;
				}
				else if ((vertical == 1) && (horizontal == 2)) {
					continue;
				}
				putSingleCoords(structure, boardWidth, boardHeight, c2D, vertical, horizontal);
			}
		}
	}
	
	
	private void putSingleCoords(Map<CellCoordinates, CellState> structure, int boardWidth, int boardHeight, Coords2D c2D, int vertical,	int horizontal) {
		int newX;
		int newY;
		int distanceToNeighbor =1;
		boolean boardWrapped = BoardType.boardWrapped;
		newX = c2D.getX() + distanceToNeighbor * horizontal;

		if ((newX<0)||(newX>boardWidth)) {
			if (!boardWrapped){
				return;
			}
			newX = wrapCoordinates(newX, boardWidth);
		}
		newY = c2D.getY() + distanceToNeighbor * vertical;
		if ((newY<0)||(newY>boardHeight)) {
			if (!boardWrapped){
				return;
			}
			newY = wrapCoordinates(newY, boardHeight);
		}
		CellCoordinates coords2D = new Coords2D(newX, newY);
		structure.put(coords2D, null);
	}

	private int wrapCoordinates(int coord, int boardSize) {
		if(coord>boardSize){
			coord = coord-boardSize;
		}
		else{
			coord = boardSize+coord;
		}
		return coord;
	}

	@Override
	public String toString() {
		return "Structure [sType=" + sType + "]";
	}

	public StructureType getsType() {
		return sType;
	}

	public void setSType(StructureType sType) {
		this.sType = sType;
	}
}

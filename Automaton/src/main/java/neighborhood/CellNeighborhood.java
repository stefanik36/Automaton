package neighborhood;

import java.util.Set;

import coordinates.CellCoordinates;

public interface CellNeighborhood {

	public abstract Set<CellCoordinates> cellNeighborhood(CellCoordinates cell);
	
	//ADDED//
	default int wrapCoordinate(int distanceToNeighbor, int length, int position) {
		int coord;
		if(position>0)
			coord = distanceToNeighbor*position;
		else
			coord = length + distanceToNeighbor + distanceToNeighbor*position;
		return coord;
	}
	//END//
}

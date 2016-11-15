package Application;

import java.util.Set;

public interface CellNeighborhood {

	public abstract Set<CellCoordinates> cellNeighborhood(CellCoordinates cell);
}

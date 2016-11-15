package Application;

public class Coords1D implements CellCoordinates{

	public int x;
	
	public Coords1D(int x){
		this.x = x;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	
	@Override
	public String toString() {
		return "x: "+x;
	}
}

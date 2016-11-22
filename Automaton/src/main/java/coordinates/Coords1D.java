package coordinates;

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
	public boolean equals(Object obj){
		Coords2D coords = (Coords2D) obj;
		if(this.x == coords.x){
			return true;
		}
		return false;
	}
	@Override
	public int hashCode(){
		final int prime = 31;
		int result = 1;
		result = prime * result +  Integer.valueOf(x).hashCode();
		return result;
	}

	
	@Override
	public String toString() {
		return "x: "+x;
	}
}

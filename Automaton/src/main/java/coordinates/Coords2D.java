package coordinates;

public class Coords2D implements CellCoordinates{

	public int x;
	public int y;
	
	//ADDED//
	public Coords2D(int x, int y){
		this.x = x;
		this.y = y;
	}
	@Override
	public boolean equals(Object obj){
		Coords2D coords = (Coords2D) obj;
		if(this.x == coords.x){
			if(this.y == coords.y){
				return true;
			}
		}
		return false;
	}

	@Override
	public int hashCode(){
		final int prime = 31;
		int result = 1;
		result = prime * result +  Integer.valueOf(x).hashCode();
		result = prime * result +  Integer.valueOf(y).hashCode();
		result = result*x;
		return result;
	}
	
	public int getY() {
		return y;
	}
	public int getX() {
		return x;
	}
	
	@Override
	public String toString() {
		return "x: "+x+" y: "+y;
	}
	//END//
}

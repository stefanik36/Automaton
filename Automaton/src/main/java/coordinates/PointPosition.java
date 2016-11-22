package coordinates;

import exceptions.InvalidCellCoordinatesInstanceException;
import gui.AutomatonGUI;

public class PointPosition {
	public static int leftEdge = 0;
	public static int rightEdge = AutomatonGUI.AUTOMATON_WIDTH;
	public static int topEdge = AutomatonGUI.MENU_HEIGHT;
	public static int bottomEdge = AutomatonGUI.AUTOMATON_HEIGHT+AutomatonGUI.MENU_HEIGHT;
	
	public static CellCoordinates setCellCoordinates(int x, int y) throws InvalidCellCoordinatesInstanceException{
		CellCoordinates result;
		x = (x-leftEdge);
		y = (y-topEdge);
		int posX = setCellPosition(x, true);
		int posY = setCellPosition(y, false);
			
//		System.out.println("before/: "+posX+" "+posY);
		posX = posX/AutomatonGUI.DISTANCE_TO_NEIGHBORS +1;
		posY = posY/AutomatonGUI.DISTANCE_TO_NEIGHBORS +1;
//		System.out.println("after: "+posX+" "+posY);
		result = new Coords2D(posX, posY);

		return result;
		
	}
	
	
	
	
//	public static CellCoordinates setCellCoordinates(CellCoordinates coords) throws InvalidCellCoordinatesInstanceException{
//		CellCoordinates result;
//		if(coords instanceof Coords2D){
//			Coords2D c2D = (Coords2D)coords;
//			int x = (c2D.getX()-leftEdge);
//			int y = (c2D.getY()-topEdge);
//			System.out.println("before: "+x+" "+y);
//			int posX = setCellPosition(x, true);
//			int posY = setCellPosition(y, false);
//			
//			System.out.println("before/: "+posX+" "+posY);
//			posX = posX/AutomatonGUI.DISTANCE_TO_NEIGHBORS +1;
//			posY = posY/AutomatonGUI.DISTANCE_TO_NEIGHBORS +1;
//			System.out.println("after: "+posX+" "+posY);
//			result = new Coords2D(posX, posY);
//		}
//		else if(coords instanceof Coords1D){
//			Coords1D c1D = (Coords1D)coords;
//			int posX = setCellPosition(c1D.getX(), true);
//			result = new Coords1D(posX);
//		}
//		else{
//			throw new InvalidCellCoordinatesInstanceException("Unknown instance of cell coordinates."); 
//		}
//		return result;
//		
//	}
	
	private static int setCellPosition(int x, boolean horizontal) {
		int startEdge;
		int endEdge;
		if(horizontal){
			startEdge = 0;
			endEdge = rightEdge;
		}
		else{
			startEdge = 0;
			endEdge = bottomEdge;
		}
		
		if(IsOnBoard(x,startEdge,endEdge)){
			
    		int posX = AutomatonGUI.EXTERNAL_CELL_RADIUS;
    		
    		int left=startEdge;
	   		int right=startEdge+2*AutomatonGUI.EXTERNAL_CELL_RADIUS;
	   		while(right<=endEdge){
	   			if(x>=left){
	   				if(x<=right){
	   					System.out.println(posX);
	   					return posX;
	   				}
	   			}
	   			left = left + AutomatonGUI.DISTANCE_TO_NEIGHBORS;
	   			right = right + AutomatonGUI.DISTANCE_TO_NEIGHBORS;
	   			posX = posX + AutomatonGUI.DISTANCE_TO_NEIGHBORS;
	   		}
	   	}
		return -AutomatonGUI.DISTANCE_TO_NEIGHBORS;		
	}
	 
	 
	 public static boolean IsOnBoard(int x, int startEdge, int endEdge){
		 if(x>=startEdge){
			 if(x<=endEdge){
				 if(!intoBreak(x, startEdge, endEdge)){
					 return true;
				 }
			 }
		 }
		 return false;		 
	 }
	 public static boolean intoBreak(int x, int startEdge, int endEdge){
		 int left=startEdge;
		 int right=startEdge+2*AutomatonGUI.EXTERNAL_CELL_RADIUS;
		 while(right<=endEdge){
			 if(x>=left){
				 if(x<=right){
					 return false;
				 }
			 }
			 left = left + AutomatonGUI.DISTANCE_TO_NEIGHBORS;
			 right = right + AutomatonGUI.DISTANCE_TO_NEIGHBORS;
		 }
		 return true;
	 }
}

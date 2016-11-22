package gui;

import java.util.ArrayList;

import application.Automaton;
import application.BinaryState;
import application.CellState;
import application.CellStateFactory;
import application.GeneralStateFactory;
import application.UniformStateFactory;
import coordinates.CellCoordinates;
import coordinates.Coords1D;
import coordinates.Coords2D;
import exceptions.InvalidCellCoordinatesInstanceException;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;


public class EventHandlers implements EventHandler<MouseEvent>{
	private ArrayList<CellCoordinates> inputCoords;
	private AutomatonAnimation animation;
	public int leftEdge = 0;
	public int rightEdge = AutomatonGUI.AUTOMATON_WIDTH;
	public int topEdge = AutomatonGUI.MENU_HEIGHT;
	public int bottomEdge = AutomatonGUI.AUTOMATON_HEIGHT+AutomatonGUI.MENU_HEIGHT;
	
	public EventHandlers(ArrayList<CellCoordinates> inputCoords, AutomatonAnimation animation) {
		this.inputCoords = inputCoords;
		this.animation = animation;
	}
	@Override
     public void handle(MouseEvent me) {
		int x = (int)me.getSceneX();
     	int y = (int)me.getSceneY();
     	
     	try {
			CellCoordinates cellCoords = setCellCoordinates(new Coords2D(x, y));
			inputCoords.add(cellCoords);

		} catch (InvalidCellCoordinatesInstanceException e) {
			e.printStackTrace();
		}
     }
	
	private CellCoordinates setCellCoordinates(CellCoordinates coords) throws InvalidCellCoordinatesInstanceException{
		CellCoordinates result;
		if(coords instanceof Coords2D){
			Coords2D c2D = (Coords2D)coords;
			int x = (c2D.getX()-leftEdge);
			int y = (c2D.getY()-topEdge);
			System.out.println("before: "+x+" "+y);
			int posX = setCellPosition(x, true);
			int posY = setCellPosition(y, false);
			
			System.out.println("before/: "+posX+" "+posY);
			posX = posX/AutomatonGUI.DISTANCE_TO_NEIGHBORS +1;
			posY = posY/AutomatonGUI.DISTANCE_TO_NEIGHBORS +1;
			System.out.println("after: "+posX+" "+posY);
			result = new Coords2D(posX, posY);
		}
		else if(coords instanceof Coords1D){
			Coords1D c1D = (Coords1D)coords;
			int posX = setCellPosition(c1D.getX(), true);
			result = new Coords1D(posX);
		}
		else{
			throw new InvalidCellCoordinatesInstanceException("Unknown instance of cell coordinates."); 
		}
//		System.out.println("result: "+result);
		return result;
		
	}
	
	private int setCellPosition(int x, boolean horizontal) {
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
	 
	 
	 public boolean IsOnBoard(int x, int startEdge, int endEdge){
		 if(x>=startEdge){
			 if(x<=endEdge){
				 if(!intoBreak(x, startEdge, endEdge)){
					 return true;
				 }
			 }
		 }
		 return false;		 
	 }
	 public boolean intoBreak(int x, int startEdge, int endEdge){
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
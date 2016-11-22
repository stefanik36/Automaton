package gui;

import java.util.ArrayList;
import java.util.function.Consumer;

import application.Automaton;
import application.Cell;
import application.GameOfLife;
import application.GeneralStateFactory;
import coordinates.CellCoordinates;
import coordinates.Coords2D;
import exceptions.InvalidCellCoordinatesInstanceException;
import exceptions.InvalidCellStateInstance;
import exceptions.NewStateIteratorHasNotNextException;
import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.animation.Timeline;

public class AutomatonAnimation extends AnimationTimer{
	private final long startNanoTime = System.nanoTime();
	private static int loopIteration = 0;
	private int it =0;
	private int fps =50;
	private int sec =0;
	
	private Pane automaton;
	private Automaton innerGame;

//	ArrayList<CellCoordinates> inputCoords;
	
	private GameOfLife init = new GameOfLife();
	
	public AutomatonAnimation(ArrayList<CellCoordinates> inputCoords, Automaton game, Pane automaton) {
		this.innerGame = game;
		this.automaton = automaton;
//		this.inputCoords = inputCoords;
	}

	
//	@Override
//	public void start(){
//		setIsPlaying(true);
//		super.start();
//	}
//	@Override
//	public void stop(){
//		setIsPlaying(false);
//		super.stop();
//	}
	
	private final long updateGraphicsEvery = (long)(AutomatonGUI.ANIMATION_SPEED * AutomatonGUI.NANOS_PER_SECOND);

//    private final Consumer<Long> doEveryUpdate;
	
    private long lastTime = AutomatonGUI.IDEAL_FRAME_RATENS;
    
	@Override
	public void handle(long currentNanoTime) {
//		double t = (currentNanoTime - startNanoTime) / 1000000000.0; 
		
		long nanosElapsed = currentNanoTime - lastTime;
//		System.out.println("nanoElapsed: "+nanosElapsed);
        if (nanosElapsed < updateGraphicsEvery) {
            return;
        } else {
            lastTime = currentNanoTime;
			try {
				updateAndDrawCells();
			} catch (InvalidCellStateInstance e) {
				e.printStackTrace();
			} catch (NewStateIteratorHasNotNextException e) {
				e.printStackTrace();
			}
        }
			
			
			
//		if(loopIteration<t*AutomatonGUI.ANIMATION_SPEED ){
//			System.out.println("loopIteration: "+loopIteration);
//			try {
//				updateAndDrawCells();
//			} catch (InvalidCellStateInstance | NewStateIteratorHasNotNextException e) {
//				e.printStackTrace();
//			}
//			loopIteration++;
//    	}	
		
		
		
//		if(t>sec && t<=sec+1){
//			it++;
//		}
//		if(t>sec+1){
//			fps=it;
//			sec++;
//			it=0;
//		}
//		
//		loopIteration++;
//		if(loopIteration>fps){// 1/s
//			
//			try {
//				updateAndDrawCells();
//			} catch (InvalidCellStateInstance | NewStateIteratorHasNotNextException e) {
//				e.printStackTrace();
//			}
//			loopIteration=0;
//		}
	}


	private void updateAndDrawCells() throws InvalidCellStateInstance, NewStateIteratorHasNotNextException {
		drawCells();
		updateState();
	}
	private void drawCells(){
		automaton.getChildren().add(innerGame.getCellsGroup());
	}
	private void updateState() throws InvalidCellStateInstance, NewStateIteratorHasNotNextException{
		innerGame = innerGame.nextState();
	}
	

	
	public void changePressedCellState(CellCoordinates cellCoords) {
//		if(inputCoords.size()>0){
//			GeneralStateFactory generalStateFactory = new GeneralStateFactory();	 
//			try {
//				
//				Cell c = generalStateFactory.changeCellState(innerGame, inputCoords.get(0));
//				
//				innerGame = init.newGameOfLife(generalStateFactory, innerGame.getNeighborsStrategy());
//				if(c != null){
//					automaton.getChildren().add(innerGame.getCellShape(c));
//				}
//			} catch (InvalidCellCoordinatesInstanceException e) {
//				e.printStackTrace();
//			}
//			inputCoords.clear();
//		}
		GeneralStateFactory generalStateFactory = new GeneralStateFactory();	
		Cell c;
		try {
			c = generalStateFactory.changeCellState(innerGame, cellCoords);
			innerGame = init.newGameOfLife(generalStateFactory, innerGame.getNeighborsStrategy());
			if(c != null){
				automaton.getChildren().add(innerGame.getCellShape(c));
			}
		} catch (InvalidCellCoordinatesInstanceException e) {
			e.printStackTrace();
		}
		
		
	}

	


}

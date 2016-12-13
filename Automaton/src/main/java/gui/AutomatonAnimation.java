package gui;

import application.Automaton;
import application.ElementaryCellAutomaton;
import coordinates.CellCoordinates;
import exceptions.InvalidCellCoordinatesInstanceException;
import exceptions.InvalidCellStateInstance;
import exceptions.InvalidGameInstance;
import exceptions.InvalidStructureTypeException;
import exceptions.NewStateIteratorHasNotNextException;
import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.layout.Pane;

public class AutomatonAnimation extends AnimationTimer{
	public final long NANOS_PER_SECOND = 1000000000;
    public final long IDEAL_FRAME_RATENS = (long)(1 / 60.0 * NANOS_PER_SECOND);
	public final double FPS = 1; //in secounds
	
//	private final long startNanoTime = System.nanoTime();

	
	private Pane automatonPane;
	private Automaton innerGame;
//	ArrayList<CellCoordinates> inputCoords;
	
	private int maxNumberOfPaneChildrens;
	private int numberOfPaneChildrens;
	private int currentChildren;
	
	public AutomatonAnimation(Pane automaton, Automaton game) {
		this.automatonPane = automaton;
		this.innerGame = game;
		setPaneParameters();
		drawCells();
			
	}


	private void setPaneParameters() {
		if(innerGame instanceof ElementaryCellAutomaton){
			int height = ((ElementaryCellAutomaton) innerGame).getWidth();//this will be square
			maxNumberOfPaneChildrens = height+automatonPane.getChildren().size();
		}	
		else{
			maxNumberOfPaneChildrens = automatonPane.getChildren().size()+1;	
		}
		numberOfPaneChildrens = automatonPane.getChildren().size();
		
		currentChildren = 0;

		for(int i = numberOfPaneChildrens; i<maxNumberOfPaneChildrens; i++){
			Group g = new Group();
			automatonPane.getChildren().add(g);
		}
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
	
	private final long updateGraphicsEvery = (long)(FPS * NANOS_PER_SECOND/AutomatonGUI.animation_speed);

//    private final Consumer<Long> doEveryUpdate;
	
    private long lastTime = IDEAL_FRAME_RATENS;
    
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
			} catch (InvalidCellCoordinatesInstanceException e) {
				e.printStackTrace();
			} catch (InvalidGameInstance e) {
				e.printStackTrace();
			}
        }
	}


	private void updateAndDrawCells() throws InvalidCellStateInstance, NewStateIteratorHasNotNextException, InvalidCellCoordinatesInstanceException, InvalidGameInstance {
		
		
		updateState();
		drawCells();
	}
	private void drawCells(){
		currentChildren = (currentChildren+1)%maxNumberOfPaneChildrens+numberOfPaneChildrens;
		System.out.println("cr: "+currentChildren+" max "+maxNumberOfPaneChildrens);
		
		
		Group g = innerGame.getCellsGroup();
		System.out.println("g: "+g);
//		automatonPane.getChildren().add(g);
		automatonPane.getChildren().set(currentChildren, g);
		
//		System.out.println("draw cells");
//		Group g = innerGame.getCellsGroup();
//		int maxPaneSize = automatonPane.getChildren().size(); 
//		
//		if(innerGame instanceof ElementaryCellAutomaton){
//			int height = ((ElementaryCellAutomaton) innerGame).getWidth();
//						
//			
//			if(automatonPane.getChildren().size()>height){
//				System.out.println("height: "+height+" bsize: "+automatonPane.getChildren().size());
//				//automatonPane.getChildren().remove(maxPaneSize+5, height-1);
//				System.out.println("asize: "+automatonPane.getChildren().size());
//			}
//			automatonPane.getChildren().add(g);
//		}
//		else{
//			automatonPane.getChildren().set(maxPaneSize-1, g);
//		}
//		System.out.println("size: "+automatonPane.getChildren().size());
//		

//		System.out.println("contains  g "+automatonPane.getChildren().size());

		
	}
	private void updateState() throws InvalidCellStateInstance, NewStateIteratorHasNotNextException, InvalidCellCoordinatesInstanceException, InvalidGameInstance{
//		System.out.println("next state");
		innerGame = innerGame.nextState();
		
	}
	

	
	public void changePressedCellState(CellCoordinates cellCoords) {
		if(cellCoords != null){
			try {
				innerGame.insertStructure(innerGame.getStructure(cellCoords));
			} catch (InvalidGameInstance e) {
				e.printStackTrace();
			} catch (InvalidCellStateInstance e) {
				e.printStackTrace();
			} catch (InvalidStructureTypeException e) {
				e.printStackTrace();
			}
			drawCells();
		}

		
		
	}


	


}

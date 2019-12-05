package mines;

import java.io.IOException;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class mineFx extends Application{
	
	private GridPane mainGrid;
	private Mines game;
	private Controller cont;
	private myButton[][] buttonMap;
	private HBox root;
	private int Cheight,Cwidth;
	
	public static void main(String args[]) {
		launch(args);
	}
	
	
	@Override
	public void start(Stage stage) {
		try {
    	    FXMLLoader loader = new FXMLLoader();
    	    loader.setLocation(getClass().getResource("MinesFxml.fxml"));
    	    root = loader.load();
    	    cont = loader.getController();
    	} catch (IOException e) {
    	    e.printStackTrace();
    	    return;
    	}
		
        Scene scene= new Scene(root);
        stage.setTitle("mine");
		stage.setScene(scene);
		stage.show();
		cont.getResetButton().setOnAction(new resetAction());;
		
	}
	
	public GridPane setGrid(int height,int width) {
		GridPane mainGrid = new GridPane();
		mainGrid.setMaxHeight(Double.MAX_VALUE);
		mainGrid.setMaxWidth(Double.MAX_VALUE);
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				myButton button = new myButton(i,j);
				buttonMap[i][j] = button;
				button.setOnAction(new GridButtonHandler());
				mainGrid.add(button, j, i);
				
			}
		}
		return mainGrid;
	}
	
	class myButton extends Button{
		int i,j;
		public myButton(int i,int j) {
			super();
			this.i = i;
			this.j = j;
			this.setMaxHeight(Double.MAX_VALUE);
			this.setMaxWidth(Double.MAX_VALUE);
			this.setPadding(new Insets(1));
			this.setPrefSize(40, 40);
			this.setText(".");
		}
	}
	
	class FxStSquare implements StrategySquare {
		@Override
		public void handle(int i, int j) {
			buttonMap[i][j].setText(game.get(i, j));
		}
		
	}
	
	
	class GridButtonHandler implements EventHandler<ActionEvent>{
		@Override	//button pressed on my grid
		public void handle(ActionEvent event) {
			myButton b =(myButton)event.getSource();
			if(game.open(b.i, b.j) == false) {	//only false in pressing button can be bomb
				game.setShowAll(true);
				showAll(); 
			}
			
		}
	}
	
	class resetAction implements EventHandler<ActionEvent>{
		@Override
		public void handle(ActionEvent event) {
			int mine,height,width;
			mine = Integer.valueOf(cont.getMineTextField().getText());
			height = Integer.valueOf(cont.getHeightTextField().getText());
			width = Integer.valueOf(cont.getWidthTextField().getText());
			if(height == 0 || width == 0) {
				System.out.println("zero in one of the dimensions\n");
				return;
			}
			Cheight = height;
			Cwidth = width;
			if(game != null) {
				root.getChildren().remove(1);
			}
			game = new Mines(height,width,mine);
			game.setFunc(new FxStSquare());
			buttonMap = new myButton[height][width];
			mainGrid = setGrid(height,width);
			root.getChildren().add(mainGrid);
			mainGrid.getScene().getWindow().sizeToScene();
		}
	}
	
	private void showAll() {
		String sGame = game.toString();
		int nS = 0;	//number of \n
		for (int i = 0; i < Cheight; i++) {
			for (int j = 0; j < Cwidth; j++) {
				buttonMap[i][j].setText(String.valueOf(sGame.charAt(i*Cwidth +j + nS)));
			}
			nS++;
		}
	}
}

package ex10;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class First extends Application {
	
    @Override
    public void start(Stage stage) {
    	AnchorPane anc;
    	controller cont;
    	try {
    	    FXMLLoader loader = new FXMLLoader();
    	    loader.setLocation(getClass().getResource("fxcontroller.fxml"));
    	    anc = loader.load();
    	    cont = loader.getController();
    	} catch (IOException e) {
    	    e.printStackTrace();
    	    return;
    	}
    	Scene scene= new Scene(anc);
        stage.setScene(scene);
        stage.show();
        
       Label label = cont.getLabel();
       //label.setText("No button in main");
    }
    
    public static void main(String[] args) {
        launch(args);
    }

}


package ex10;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class controller {

    @FXML
    private Button button1;

    @FXML
    private Button button2;

    @FXML
    private Label label;
    
    @FXML
    void press(ActionEvent event) {
    	Button b =(Button)event.getSource();
    	label.setText(((b == button1) ? "One" : "Two"));
    }
    
    public Label getLabel() {
    	return label;
    }
    
}

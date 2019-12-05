package mines;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class Controller {

    @FXML
    private Button resetButton;

    @FXML
    private TextField mineTextField;

    @FXML
    private TextField heightTextField;

    @FXML
    private TextField widthTextField;

    public TextField getMineTextField(){
    	return mineTextField;
    }
    public TextField getHeightTextField(){
    	return heightTextField;
    }
    public TextField getWidthTextField(){
    	return widthTextField;
    }
    
    public Button getResetButton() {
    	return resetButton;
    }
}

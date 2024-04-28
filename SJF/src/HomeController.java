import java.io.IOException;

import Heplers.Functions;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.Alert.AlertType;

public class HomeController {

    private Stage stage;
    private Scene scene;
    private Parent root;
    
    @FXML
    private Label NoOfProcess;

    @FXML
    private Button addBtn;

    @FXML
    private TextField arrivalField;

    @FXML
    private TextField burstField;

    @FXML
    private Button nextBtn;

    @FXML
    private Label title;

    public void addProcesss(ActionEvent e)
    {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error!");
        
        try {
            if(Functions.readPositiveOrZero(Double.parseDouble(arrivalField.getText())) != -1)
            {
                if(Functions.readPositive(Double.parseDouble(burstField.getText())) != -1)
                {
                    ProcessManagement.addProcess(Double.parseDouble(burstField.getText()) , Double.parseDouble(arrivalField.getText()));
                    NoOfProcess.setText(String.valueOf(ProcessManagement.getAllProcesses().size()));
                    System.out.println("Added");
                    arrivalField.clear();
                    burstField.clear();

                    for(Process process : ProcessManagement.getAllProcesses())
                    {
                        System.out.println("Process " + process.getID() + " , Arrival = " + process.getArrivalTime() + " , Burst = " + process.getBurstTime());
                    }
                }
                else{
                    alert.setContentText("Burst time should be more than zero");
                    if(alert.showAndWait().get() == ButtonType.OK);
                }
                
            }
            else{
                alert.setContentText("Arrival time should be positive");
                if(alert.showAndWait().get() == ButtonType.OK);
            }
    } catch (Exception exception) {
        alert.setContentText("Please Fill all the fields");
        if(alert.showAndWait().get() == ButtonType.OK);
    }
    }

    public void switchToScene(ActionEvent e) throws IOException
    {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error!");

        if(!ProcessManagement.getAllProcesses().isEmpty())
        {
            ProcessManagement.serve();
            ProcessManagement.Calculation();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("./CalculationScene.fxml"));
            root = loader.load();
            stage = (Stage)((Node)e.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        else
        {
            alert.setContentText("Add process first!");
            if(alert.showAndWait().get() == ButtonType.OK);
        }
        
    }
}


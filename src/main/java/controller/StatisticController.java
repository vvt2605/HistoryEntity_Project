package controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class StatisticController {

	public StatisticController() {
		// TODO Auto-generated constructor stub
	}
	@FXML
	private Button backStatistic;
	@FXML
	public void btnBackHover(MouseEvent event) {
	 	backStatistic.setCursor(Cursor.HAND);

        backStatistic.setStyle("-fx-background-radius: 8px; -fx-background-color: #f2f6fe; -fx-opacity: 1; -fx-font-size: 18px;");
	
	
}	@FXML
	public void btnBackWithoutHover(MouseEvent event) {
    backStatistic.setStyle("-fx-background-radius: 8px; -fx-background-color: #ffffff; -fx-opacity: 0.7; -fx-font-size: 18px;");
	}
	public void backHome(ActionEvent event) throws IOException {
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/home.fxml"));
		Parent detailParent = loader.load();
		Scene scene = new Scene(detailParent);
	
		stage.setScene(scene);
	}

}

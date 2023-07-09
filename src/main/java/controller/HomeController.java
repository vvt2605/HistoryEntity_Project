package controller;

import java.io.IOException;

import constant.Constant;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class HomeController {

    @FXML
    public  Button btnHome;
    @FXML
    public Button btnCharacter;
    @FXML
    public Button btnDynasty;
    @FXML
    public Button btnEvent;
    @FXML
    public Button btnFestival;
    @FXML
    public Button btnRelic;
    @FXML 
    public Button btnSearch;

    //hover và khi thoat hover
    public void initialize() {
    	
    	handleExitHover(btnCharacter);
    	handleExitHover(btnDynasty);
    	handleExitHover(btnEvent);
    	handleExitHover(btnRelic);
    	handleExitHover(btnFestival);
    	btnHome.setStyle("-fx-opacity: 1; -fx-background-color: #2262C6;-fx-background-radius: 8px;");	
	
    	
    }
//thoát hover
    public void handleExitHover (Button button) {
    	button.setOnMouseExited(new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent event) {
				// TODO Auto-generated method stub
				button.setStyle("-fx-opacity: 0.7; -fx-background-color: #2262C6;-fx-background-radius: 8px;");	
			}
		});
    	
    }
    public void btnHover(MouseEvent event) {
    	if (event.getSource()== btnCharacter) {
        	btnCharacter.setStyle("-fx-cursor: hand;-fx-opacity: 1; -fx-background-color: #2262C6;-fx-background-radius: 8px; ");

    	}
    	else if (event.getSource()== btnDynasty) {
        	btnDynasty.setStyle("-fx-cursor: hand;-fx-opacity: 1; -fx-background-color: #2262C6;-fx-background-radius: 8px; ");

    	}
    	else if (event.getSource()== btnEvent) {
        	btnEvent.setStyle("-fx-cursor: hand;-fx-opacity: 1; -fx-background-color: #2262C6;-fx-background-radius: 8px; ");

    	}
    	else if (event.getSource()== btnFestival) {
        	btnFestival.setStyle("-fx-cursor: hand;-fx-opacity: 1; -fx-background-color: #2262C6;-fx-background-radius: 8px; ");

    	}
    	else if (event.getSource()== btnRelic) {
        	btnRelic.setStyle("-fx-cursor: hand;-fx-opacity: 1; -fx-background-color: #2262C6;-fx-background-radius: 8px; ");

    	}
    	else if (event.getSource()== btnSearch) {
    		btnSearch.setStyle("-fx-cursor: hand;-fx-opacity: 1; -fx-background-color: #2262C6;-fx-background-radius: 8px; ");

    	}

    }
    // khi nhấn các button entity
    public void pressBtnEntity(ActionEvent event) throws IOException {
    	
    	if (event.getSource()== btnCharacter) {
    		actionPress(event, "Danh sách các nhân vật lịch sử", Constant.CHARACTER_ENTITY);
    		
    	}
    	else if (event.getSource()== btnDynasty) {
    		actionPress(event, "Danh sách các triều đại lịch sử", Constant.DYNASTY_ENTITY);

    	}
    	else if (event.getSource()== btnEvent) {
    		actionPress(event, "Danh sách các sự kiện lịch sử", Constant.EVENT_ENTITY);

    	}
    	else if (event.getSource()== btnFestival) {
    		actionPress(event, "Danh sách các Lễ hội", Constant.FESTIVAL_ENTITY);

    	}
    	else if (event.getSource()== btnRelic) {
    		actionPress(event, "Danh sách các danh lam thắng cảnh", Constant.RELIC_PLACE_ENTITY);

    	}
    	
    	
    }
  
    public void actionPress(ActionEvent event, String value, String type) throws IOException {
    	Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/listEntity.fxml"));
		Parent detailParent = loader.load();
		Scene scene = new Scene(detailParent);
		listEntityController listEntityController = loader.getController();
		listEntityController.setTitleList(value);
		listEntityController.createList(type);
		
		stage.setScene(scene);
    }
    
    public void pressSearchBtn(ActionEvent event) throws IOException {
    	Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/search.fxml"));
		Parent detailParent = loader.load();
		Scene scene = new Scene(detailParent);
		stage.setScene(scene);
    }

    
}

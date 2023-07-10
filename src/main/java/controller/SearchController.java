package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import constant.Constant;
import dataParser.loadFileJson;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.BaseEntity;

import java.net.URL;

public class SearchController implements Initializable {
	@FXML 
	private Button backBtnSearch;
	@FXML
	private ComboBox<String> listTypeSearch;
	@FXML 
	private Button searchBtnSubmit;
	@FXML 
	private VBox listEntitySearch ;
	@FXML
	private TextField inputSearch;
	
	@FXML 
	private ScrollPane scrollPaneSearch;
	private String typeInput="Tất cả";

    ObservableList<String> list = FXCollections.observableArrayList("Tất cả","Nhân vật lịch sử", "Sự kiện và triều đại lịch sử", "Lễ hội văn hóa ", "Danh lam thắng cảnh");
    //tạo list combobox
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    	// hiển thị trong combobox
    	listTypeSearch.setItems(list);
    	// xử lí sự kiện cho button search
    	searchBtnSubmit.setOnAction(event-> handleSearchButtonAction());
    	
    	// xử lí sự kiện nhất enter trong textField 
    	inputSearch.setOnAction(event-> handleSearchButtonAction());
        
    }
    // khi nhấn enter hoặc button search
    @FXML
    public void handleSearchButtonAction() {
    	scrollPaneSearch.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
    	String searchTerm = inputSearch.getText();
    	String typeCombobox= listTypeSearch.getValue();
    	if (typeCombobox == "Nhân vật lịch sử" ) {
    		typeInput = Constant.CHARACTER_ENTITY;
    	}
    	else if (typeCombobox == "Sự kiện và triều đại lịch sử" ) {
    		typeInput = Constant.DYNASTY_ENTITY;
    	}
    	
    	else if (typeCombobox == "Lễ hội văn hóa " ) {
    		typeInput = Constant.FESTIVAL_ENTITY;
    	}
    	else if (typeCombobox ==  "Danh lam thắng cảnh" ) {
    		typeInput = Constant.RELIC_PLACE_ENTITY;
    	}
    	else if (typeCombobox ==  "Tất cả" ) {
    		typeInput = typeCombobox;
    	}
    	
    	
    	
    	
    		System.out.println(searchTerm);
        	listEntitySearch.getChildren().clear();
        loadFileJson load = new loadFileJson();
        List<BaseEntity> listEntity = load.getAllEntityIdsOfType(typeInput);
        List<BaseEntity> entities = load.getEntityByNameWithoutAccentsAndType(searchTerm, listEntity);
        System.out.println(entities);
        	if (entities.isEmpty()) {
        		System.out.println(entities);
        		Label label = new Label("Không có dữ liệu");
        		label.setStyle(" -fx-font-size: 18px;");
        		VBox.setMargin(label, new Insets(16, 16, 16, 16));
    	        listEntitySearch.setAlignment(Pos.CENTER);

        		listEntitySearch.getChildren().add(label);
        	} 
        	else {
        		 for (int i = 0; i < entities.size(); i++) {
        		    	int index = i + 1;
        		        Button button = new Button(index + "." +entities.get(i).getName());
        		        button.setLayoutY(10);
        		        button.setPrefWidth(800);
        		        button.setPrefHeight(40);
        		        button.setStyle("-fx-background-radius: 16px; -fx-margin-left: 12px; -fx-margin-right: 12px; -fx-background-color: #ffffff; -fx-opacity: 1; -fx-font-size: 18px;");
        		        button.setCursor(Cursor.HAND);
        		        handleExitHover(button);
        		        String id = entities.get(i).getId();
        		        
        			        button.setOnAction((ActionEvent eventAction)-> {
        			        	try {
        			        		changeDetailView(eventAction, id);
        						} catch (IOException e) {
        							// TODO Auto-generated catch block
        							e.printStackTrace();
        						}
        			        });
        		         
        		        
        		        button.prefWidthProperty().bind(listEntitySearch.widthProperty());
        		        VBox.setMargin(button, new Insets(4, 4, 4, 4));
        		        listEntitySearch.getChildren().add(button);
        		    }
        		
        	}
    	
    	
    	
	    
	   
    }
    

	//chuyển tới giao diện chi tiết
		public void changeDetailView(ActionEvent event, String id) throws IOException {
			Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/DetailEntityView.fxml"));
			Parent detailParent = loader.load();			
			Scene scene = new Scene(detailParent);
			DetailEntityController detailController = loader.getController();
			detailController.detailView(id);
			stage.setScene(scene);
			
		}
		//hover các thực thể 
		public void handleExitHover (Button button) {
			button.setOnMouseEntered(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
					// TODO Auto-generated method stub
		            button.setStyle("-fx-background-radius: 16px; -fx-margin-left: 12px; -fx-margin-right: 12px;-fx-background-color: linear-gradient(to right, #84fab0 0%, #8fd3f4 51%, #84fab0 100%); -fx-opacity: 1; -fx-font-size: 18px;");
				
				}
			});
	    	button.setOnMouseExited(new EventHandler<MouseEvent>(){
				@Override
				public void handle(MouseEvent event) {
					
		            button.setStyle("-fx-background-radius: 16px; -fx-margin-left: 12px; -fx-margin-right: 12px; -fx-background-color: #ffffff; -fx-opacity: 1; -fx-font-size: 18px;");
				}
			});
	    	
	    }
   
   
	//hover và thoát khỏi hover
	@FXML
	public void btnBackHover(MouseEvent event) {
	 	backBtnSearch.setCursor(Cursor.HAND);
        backBtnSearch.setStyle("-fx-background-radius: 8px; -fx-background-color: linear-gradient(to right, #84fab0 0%, #8fd3f4 51%, #84fab0 100%); -fx-opacity: 1; -fx-font-size: 18px;");
	}
	@FXML
	public void btnBackWithoutHover(MouseEvent event) {
    backBtnSearch.setStyle("-fx-background-radius: 8px; -fx-background-color: #ffffff; -fx-opacity: 0.7; -fx-font-size: 18px;");
	}
	// back home
	public void backAction(ActionEvent event) throws IOException {
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/home.fxml"));
		Parent detailParent = loader.load();
		Scene scene = new Scene(detailParent);

		stage.setScene(scene);
	}
	
	//hover và thoát hover của button search
	@FXML
	public void btnSearchHover(MouseEvent event) {
	    searchBtnSubmit.setCursor(Cursor.HAND);
	    searchBtnSubmit.setStyle("-fx-background-radius: 16px; -fx-background-color: #00FF00; -fx-opacity: 1;");
	}

	@FXML
	public void btnSearchWithoutHover(MouseEvent event) {
	    searchBtnSubmit.setStyle("-fx-background-radius: 16px; -fx-background-color: #16bc00; -fx-opacity: 0.8;");
	}
	

	

}

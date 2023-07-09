package controller;


import java.io.IOException;
import java.util.Map;

import constant.Constant;
import dataParser.loadFileJson;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.BaseEntity;
import model.PreviousList;


public class ControllerMain {
	
	
	@FXML
	public Button button1 ;
	@FXML
	public Button button2 ;
	@FXML
	public Button button3 ;
	@FXML
	public Button button4 ;
	@FXML
	public Button button5 ;
	
	@FXML
	public VBox listButtons ;


	public String BtnPreList;
	// sự kiện khi nhấn vào các nút entity
	public void showListEntity(ActionEvent event) {
		 loadFileJson load = new loadFileJson();
		 Map<String, BaseEntity> entities = load.getAllEntities();
		if (event.getSource()== button1) {
			createListEntity(Constant.CHARACTER_ENTITY);
			 PreviousList preBtnList = new PreviousList(Constant.CHARACTER_ENTITY);
			 BtnPreList =  preBtnList.getPreList();

		}
		else if (event.getSource() == button2) {
			createListEntity(Constant.DYNASTY_ENTITY);
			PreviousList preBtnList = new PreviousList(Constant.DYNASTY_ENTITY);
			BtnPreList =  preBtnList.getPreList();
        } else if (event.getSource() == button3) {
        	createListEntity( Constant.EVENT_ENTITY);
        	PreviousList preBtnList = new PreviousList(Constant.EVENT_ENTITY);
        	BtnPreList =  preBtnList.getPreList();
        } else if (event.getSource() == button4) {
        	createListEntity( Constant.RELIC_PLACE_ENTITY);
        	PreviousList preBtnList = new PreviousList(Constant.RELIC_PLACE_ENTITY);
        	BtnPreList =  preBtnList.getPreList();
        } else if (event.getSource() == button5) {
        	createListEntity( Constant.FESTIVAL_ENTITY);
        	PreviousList preBtnList = new PreviousList(Constant.FESTIVAL_ENTITY);
        	BtnPreList =  preBtnList.getPreList();
        }
	}

	
	// tạo danh sách tên các thực thể
	public void createListEntity(Map<String,BaseEntity> entities, String type) {

		listButtons.getChildren().clear();

        
		for (BaseEntity entity : entities.values()) {
			if (entity.getType()== type) {
				Button button = new Button(entity.getName());
	            button.setLayoutY(10);
	            button.setCursor(Cursor.HAND);
	            button.setOnAction(new EventHandler<ActionEvent>() {
					
					@Override
					public void handle(ActionEvent event) {
						try {
				            changeDetailView(event);
				        } catch (IOException e) {
				            e.printStackTrace();
				        }
					}
				});
	            button.prefWidthProperty().bind(listButtons.widthProperty());
	            VBox.setMargin(button, new Insets(4,4,4,4));
	            listButtons.getChildren().add(button);
			}
            
        }
	}
	public void createListEntity( String type) {
		loadFileJson load = new loadFileJson();
		 Map<String, BaseEntity> entities = load.getAllEntities();
		listButtons.getChildren().clear();

        
		for (BaseEntity entity : entities.values()) {
			if (entity.getType()== type) {
				Button button = new Button(entity.getName());
	            button.setLayoutY(10);
	            button.setCursor(Cursor.HAND);
	            button.setOnAction(new EventHandler<ActionEvent>() {
					
					@Override
					public void handle(ActionEvent event) {
						try {
				            changeDetailView(event);
				        } catch (IOException e) {
				            e.printStackTrace();
				        }
					}
				});
	            button.prefWidthProperty().bind(listButtons.widthProperty());
	            VBox.setMargin(button, new Insets(4,4,4,4));
	            listButtons.getChildren().add(button);
			}
            
        }
	}
	//chuyển tới giao diện chi tiết
	public void changeDetailView(ActionEvent event) throws IOException {
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/DetailEntityView.fxml"));
		Parent detailParent = loader.load();
		
		Scene scene = new Scene(detailParent);
		DetailEntityController detailController = loader.getController();
		stage.setScene(scene);
		
	}
	
	//trả về VBox trong main.fxml
	public VBox getListButtonsVbox() {
		return listButtons;
	}
	
	
	

	public static void main(String[] args) { 
		loadFileJson load = new loadFileJson();
		int i = 1;
		Map<String, BaseEntity> entities = load.getAllEntities();
		for (BaseEntity entity : entities.values()) {
		    System.out.println(i + entity.getName());
		i++;    
		}

		System.out.println("load data successfully!");


	}
	


}

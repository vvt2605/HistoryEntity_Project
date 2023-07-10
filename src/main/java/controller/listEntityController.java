package controller;


import java.io.IOException;

import java.util.List;
import java.util.Map;

import dataParser.loadFileJson;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.BaseEntity;

public class listEntityController {
	@FXML
	public Button btnBack;

	@FXML
	public VBox listButtons;
	
	@FXML 
	public Label titleList;
	
	private static  String prevous = new String();
	
	public void setPrevous(String prevous) {
		this.prevous = prevous;
	}
	
	public void setTitleList(String title) {
		titleList.setText(title);
	}

	//tạo danh sách các thực thể
	/* public void createListEntity(String type ) {
	    btnBack.setStyle("-fx-background-radius: 8px; -fx-background-color: #ffffff; -fx-opacity: 0.7;-fx-font-size: 18px;");
		
		loadFileJson load = new loadFileJson();
		 Map<String, BaseEntity> entities = load.getAllEntities();
		listButtons.getChildren().clear();

        
		for (BaseEntity entity : entities.values()) {
			if (entity.getType()== type) {
				Button button = new Button(entity.getName());
	            button.setLayoutY(10);
	            button.setPrefWidth(800);
	            button.setPrefHeight(40);
	            button.setStyle("-fx-margin-left: 12px; -fx-margin-right: 12px; -fx-background-color: #ffffff; -fx-opacity: 1; -fx-font-size: 18px;");
	            button.setCursor(Cursor.HAND);
	            handleExitHover(button);
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
	
	public void createList(String type) {
	    btnBack.setStyle("-fx-background-radius: 8px; -fx-background-color: #ffffff; -fx-opacity: 0.7;-fx-font-size: 18px;");
	    loadFileJson load = new loadFileJson();
	    List<BaseEntity> entities = load.getAllEntityIdsOfType(type);
	    
	    for (int i = 0; i < entities.size(); i++) {
	        Button button = new Button(i + "." +entities.get(i).getName());
	        button.setLayoutY(10);
	        button.setPrefWidth(800);
	        button.setPrefHeight(40);
	        button.setStyle("-fx-margin-left: 12px; -fx-margin-right: 12px; -fx-background-color: #ffffff; -fx-opacity: 1; -fx-font-size: 18px;");
	        button.setCursor(Cursor.HAND);
	        handleExitHover(button);
	        
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
	        VBox.setMargin(button, new Insets(4, 4, 4, 4));
	        listButtons.getChildren().add(button);
	    }
	}
*/
	public void createList(String type) {
	    btnBack.setStyle("-fx-background-radius: 8px; -fx-background-color: #ffffff; -fx-opacity: 0.7;-fx-font-size: 18px;");
	    loadFileJson load = new loadFileJson();
	    List<BaseEntity> entities = load.getAllEntityIdsOfType(type);
       // System.out.println(entities);

	    for (int i = 0; i < entities.size(); i++) {
	    	int index = i + 1;
	        Button button = new Button(index + ".  " +entities.get(i).getName());
	        button.setLayoutY(10);
	        button.setPrefWidth(400);
	        button.setPrefHeight(40);
	        button.setStyle("-fx-background-radius: 16px; -fx-margin-left: 12px; -fx-margin-right: 12px; -fx-background-color: #ffffff; -fx-opacity: 1; -fx-font-size: 18px;");
	        button.setCursor(Cursor.HAND);
	        handleExitHover(button);
	        String id = entities.get(i).getId();
	        button.setOnAction(event-> {
	        	try {
					changeDetailView(event, id);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        });
	        //set tỉ lệ button so vs box chứa nó và set center
	        button.prefWidthProperty().bind(listButtons.widthProperty().divide(2));
	        button.setAlignment(Pos.BASELINE_LEFT);
	        
	        VBox.setMargin(button, new Insets(4, 4, 4, 4));
	        listButtons.getChildren().add(button);
	        listButtons.setAlignment(Pos.CENTER);
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
		
		public void backHome(ActionEvent event) throws IOException {
			Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/home.fxml"));
			Parent detailParent = loader.load();
			Scene scene = new Scene(detailParent);

			stage.setScene(scene);
		}

		//hover và khi thoát hover
		public void handleExitHover (Button button) {
			button.setOnMouseEntered(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
					// TODO Auto-generated method stub
		            button.setStyle("-fx-background-radius: 16px; -fx-margin-left: 12px; -fx-margin-right: 12px; -fx-background-color: linear-gradient(to right, #84fab0 0%, #8fd3f4 51%, #84fab0 100%); -fx-opacity: 1; -fx-font-size: 18px;");
				
				}
			});
	    	button.setOnMouseExited(new EventHandler<MouseEvent>(){
				@Override
				public void handle(MouseEvent event) {
					
		            button.setStyle("-fx-background-radius: 16px; -fx-margin-left: 12px; -fx-margin-right: 12px; -fx-background-color: #ffffff; -fx-opacity: 1; -fx-font-size: 18px;");
				}
			});
	    	
	    }
		@FXML
		public void btnBackHover(MouseEvent event) {
			 	btnBack.setCursor(Cursor.HAND);

	            btnBack.setStyle("-fx-background-radius: 8px; -fx-background-color: linear-gradient(to right, #84fab0 0%, #8fd3f4 51%, #84fab0 100%); -fx-opacity: 1; -fx-font-size: 18px;");
			
	    	
		}
		@FXML
		public void btnBackWithoutHover(MouseEvent event) {
            btnBack.setStyle("-fx-background-radius: 8px; -fx-background-color: #ffffff; -fx-opacity: 0.7; -fx-font-size: 18px;");
			}
}

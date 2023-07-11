package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import constant.Constant;
import dataParser.loadFileJson;
import javafx.event.ActionEvent;
import javafx.event.Event;
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
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import model.BaseEntity;

public class DetailEntityController {
	private static List<String> previousPage = new ArrayList<>();
	private static String idPreviousPage;
	@FXML
	private Label typeDetaiLabel;
	@FXML
	private TextFlow descriptionDetaiLabel;
	@FXML
	private Button btnBack;
	@FXML 
	private VBox VboxAddInfor;
	@FXML 
	private VBox VboxRelated;
	@FXML 
	private Label titleDetail;
	
	private String idString;
	@FXML 
	public void initial() {
	    btnBack.setStyle("-fx-background-radius: 8px; -fx-background-color: #ffffff; -fx-opacity: 0.7;-fx-font-size: 18px;");
	}
	// lưu trang trước đó 
	public void setPreviousPage(String string) {
		System.out.println("Trước"+previousPage);
		previousPage.add(string);
		System.out.println("Sau "+ previousPage);
		
	}
	//lưu id cho trường hợp từ id chuyển vào 
	public void setId(String string) {
		idPreviousPage = string;
	}
	
	public void detailView(String id ) {
		 VboxAddInfor.setPrefHeight(VBox.USE_COMPUTED_SIZE);
		 VboxRelated.setPrefHeight(VBox.USE_COMPUTED_SIZE);
		idString = id;
			loadFileJson load = new loadFileJson();
			BaseEntity entity = load.getEntityById(id);
			if (entity.getType()== Constant.CHARACTER_ENTITY ) {
				typeDetaiLabel.setText("Thông tin chi tiết về nhân vật lịch sử" );

			} 
			else if (entity.getType()== Constant.DYNASTY_ENTITY ) {
				typeDetaiLabel.setText("Thông tin chi tiết về triều đại lịch sử" );

			} 
			else if (entity.getType()== Constant.FESTIVAL_ENTITY ) {
				typeDetaiLabel.setText("Thông tin chi tiết về lễ hội văn hóa" );

			} 
			else if (entity.getType()== Constant.EVENT_ENTITY ) {
				typeDetaiLabel.setText("Thông tin chi tiết về sự kiện lịch sử" );

			} 
			else if (entity.getType()== Constant.RELIC_PLACE_ENTITY ) {
				typeDetaiLabel.setText("Thông tin chi tiết về danh lam thắng cảnh" );
			} 
			titleDetail.setText("Mô tả chi tiết về " + entity.getName());
			 Text text = new Text(entity.getDescription() );
			text.setFont(Font.font("System", 16));
			descriptionDetaiLabel.getChildren().add(text);
			
			Map<String, String> addInfor = new HashMap<>();
			addInfor = entity.getAdditionalInfo();
			for (Map.Entry<String, String> entry : addInfor.entrySet()) {
				String key = entry.getKey();
				String value = entry.getValue();
				Text textInfor = new Text(key + ": " + value);
				textInfor.setFont(Font.font("System", 16));
				TextFlow infor = new TextFlow(textInfor);
				VBox.setMargin(infor, new Insets(4,4,4,4));
				VboxAddInfor.getChildren().add(infor);
			}
			List<String> relatedId = new ArrayList<>();
			relatedId = entity.getRelatedEntityIds();
			for (int i = 0 ; i< relatedId.size();i++) {
				String relateIdBtn = relatedId.get(i);
				BaseEntity btnRelated =  load.getEntityById(relatedId.get(i));
				Button button = new Button(btnRelated.getName());
		        button.setStyle("-fx-background-radius: 8px; -fx-background-color: #ebe9e4; -fx-opacity: 1; -fx-font-size: 16px;");

				 button.setOnAction(new EventHandler<ActionEvent>() {
						
						@Override
						public void handle(ActionEvent event) {
							try {
								relatedBtnAction(event,relateIdBtn,entity.getId() );
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					});
				 button.setOnMouseEntered(new EventHandler<Event>() {

					@Override
					public void handle(Event event) {
						// TODO Auto-generated method stub
						button.setCursor(Cursor.HAND);
						button.setStyle("-fx-background-color: linear-gradient(to right, #84fab0 0%, #8fd3f4 51%, #84fab0 100%); -fx-background-radius: 8px; -fx-opacity: 1; -fx-font-size: 16px;");
					}
				 }
				 );
				 button.setOnMouseExited(new EventHandler<Event>() {

					@Override
					public void handle(Event event) {
						// TODO Auto-generated method stub
						button.setStyle("-fx-background-radius: 8px; -fx-background-color: #ebe9e4; -fx-opacity: 1; -fx-font-size: 16px;");
					}
				});
				VBox.setMargin(button, new Insets(4,4,4,4));
				button.prefWidthProperty().bind(VboxRelated.widthProperty().divide(1.5));
		        VboxRelated.setAlignment(Pos.CENTER);
				VboxRelated.getChildren().add(button);
			}	
	}
	// action of btn related
	public void relatedBtnAction(ActionEvent event, String id, String previousId) throws IOException {
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/DetailEntityView.fxml"));
		Parent detailParent = loader.load();			
		Scene scene = new Scene(detailParent);
		DetailEntityController detailController = loader.getController();
		detailController.setPreviousPage(id);
		//System.out.println(id);
		detailController.detailView(id);
		stage.setScene(scene);
	}
	
    //hover and without hover
    public void btnBackHover(MouseEvent event) {
	 	btnBack.setCursor(Cursor.HAND);
        btnBack.setStyle("-fx-background-radius: 8px; -fx-background-color: linear-gradient(to right, #84fab0 0%, #8fd3f4 51%, #84fab0 100%); -fx-opacity: 1; -fx-font-size: 18px;");
    	}
    public void btnBackWithoutHover(MouseEvent event) {
    btnBack.setStyle("-fx-background-radius: 8px; -fx-background-color: #ffffff; -fx-opacity: 0.7; -fx-font-size: 18px;");
	}
    
    //back action    
    public void backAction(ActionEvent event) throws IOException {
    	Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		FXMLLoader loader = new FXMLLoader();
		 if (previousPage.get(previousPage.size()-1)=="list" || previousPage.get(previousPage.size()-1) == "search" ) {
			 if (previousPage.get(previousPage.size()-1)=="list" ) {
				 previousPage.remove(previousPage.size()-1);
				 	loader.setLocation(getClass().getResource("/listEntity.fxml"));
					Parent detailParent = loader.load();
					Scene scene = new Scene(detailParent);
					
					loadFileJson load = new loadFileJson();
					BaseEntity entity = load.getEntityById(idString);
					
					listEntityController listEntityController = loader.getController();
					listEntityController.createList(entity.getType());
					String type = entity.getType();
					listEntityController.createList(type);
					if (type == Constant.CHARACTER_ENTITY) {
						listEntityController.setTitleList("Danh sách các nhân vật lịch sử");

					} 
					else if (type == Constant.DYNASTY_ENTITY) {
						listEntityController.setTitleList("Danh sách các triều đại lịch sử");

					}
					 else if (type == Constant.EVENT_ENTITY) {
							listEntityController.setTitleList("Danh sách các sự kiện lịch sử");

						}
					 else if (type == Constant.FESTIVAL_ENTITY) {
							listEntityController.setTitleList("Danh sách các Lễ hội");

						}
					 else if (type == Constant.RELIC_PLACE_ENTITY) {
							listEntityController.setTitleList("Danh sách các danh lam thắng cảnh");

						}
					stage.setScene(scene);
			 }
			 if (previousPage.get(previousPage.size()-1) == "search") {
				 previousPage.remove(previousPage.size()-1);
					loader.setLocation(getClass().getResource("/search.fxml"));
					Parent detailParent = loader.load();
					Scene scene = new Scene(detailParent);
					stage.setScene(scene);
			 }
		 }
		  
		 else   {
			  if (previousPage.get(previousPage.size()-2)=="list" ||previousPage.get(previousPage.size()-2)=="search" ) {
				  Stage stage1 = (Stage) ((Node) event.getSource()).getScene().getWindow();
					FXMLLoader loader1 = new FXMLLoader();
					loader1.setLocation(getClass().getResource("/DetailEntityView.fxml"));
					Parent detailParent = loader1.load();			
					Scene scene = new Scene(detailParent);
					DetailEntityController detailController = loader1.getController();
					//detailController.setPreviousPage("relatedId");
					System.out.println("Back trước "+previousPage);

					

					System.out.println("Back "+previousPage);
					detailController.detailView(idPreviousPage);
					previousPage.remove(previousPage.size()-1);
					stage1.setScene(scene);
			  }
			  else {
				  Stage stage1 = (Stage) ((Node) event.getSource()).getScene().getWindow();
					FXMLLoader loader1 = new FXMLLoader();
					loader1.setLocation(getClass().getResource("/DetailEntityView.fxml"));
					Parent detailParent = loader1.load();			
					Scene scene = new Scene(detailParent);
					DetailEntityController detailController = loader1.getController();
					//detailController.setPreviousPage("relatedId");
					System.out.println("Back trước "+previousPage);

					
					previousPage.remove(previousPage.size()-1);

					System.out.println("Back "+previousPage);
					detailController.detailView(previousPage.get(previousPage.size()-1));
					stage1.setScene(scene);
			  }
			 
		 }
		
    }
    

    

}

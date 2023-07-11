package controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import dataParser.Count;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class StatisticController  implements Initializable{

	public StatisticController() {
		// TODO Auto-generated constructor stub
	}
	@FXML private Button backStatistic; 
	@FXML private Label totalCharacter;
	@FXML private Label totalEvent;
	@FXML private Label totalFestival;
	@FXML private Label totalRelic;
	@FXML private Label characterWiki;
	@FXML private Label eventWiki;
	@FXML private Label festivalWiki;
	@FXML private Label relicWiki;
	@FXML private Label characterNKS;
	@FXML private Label eventNKS;
	@FXML private Label festivalNKS;
	@FXML private Label relicNKS;
	@FXML private Label totalLink;
	@FXML private Label attributeCharacter;
	@FXML private Label attributeDynasty;
	@FXML private Label attributeFestival;
	@FXML private Label attributeRelic;
	@FXML private Label linkedEachEntity;
	@FXML private Label totalLinked;
	
	
	@FXML
	public void initialize() throws FileNotFoundException {
	    try {
	        Count count = new Count();
	        JSONObject object = count.readCount();
	        totalCharacter.setText("Tổng số nhân vật lịch sử: "+ object.get("Total character:").toString());
	        totalEvent.setText("Tổng số sự kiện và triều đại lịch sử: "+object.get("Total Dynasty and Event:").toString());
	        totalFestival.setText("Tổng số lễ hội: "+object.get("Total Festival:").toString());
	        totalRelic.setText("Tổng số danh lam thắng cảnh:" + object.get("Total Relic :").toString());
	        characterWiki.setText("Tổng số nhân vật lịch sử: "+object.get("CharacterEntity_Wiki").toString());
	        eventWiki.setText("Tổng số sự kiện và triều đại lịch sử: "+object.get("DynastyEntity_Wiki").toString());
	        festivalWiki.setText("Tổng số lễ hội: "+object.get("FestivalEntity_Wiki").toString());
	        relicWiki.setText("Tổng số danh làm thắng cảnh: "+object.get("RelicPlaceEntity_Wiki").toString());
	        characterNKS.setText("Tổng số nhân vật lịch sử: "+object.get("CharacterEntity_NguoiKeSu").toString());
	        eventNKS.setText("Tổng số sự kiện và triều đại lịch sử: "+object.get("DynastyEntity_NguoiKeSu").toString());
	        festivalNKS.setText("Tổng số lễ hội: "+object.get("FestivalEntity_NguoiKeSu").toString());
	        relicNKS.setText("Tổng số danh làm thắng cảnh: "+object.get("RelicPlaceEntity_NguoiKeSu").toString());
	        totalLink.setText("Nguồn dữ liệu được tạo nên từ "+ object.get("Total Links").toString()+" links");
	        attributeCharacter.setText("Số thuộc tính trên mỗi nhân vật lịch sử "+ object.get("attributeCharacter").toString());
	        attributeDynasty.setText("Số thuộc tính trên mỗi sự kiện và triều đại lịch sử "+ object.get("attributeDynasty").toString());
	        attributeFestival.setText("Số thuộc tính trên mỗi lễ hội "+ object.get("attributeFestival").toString());
	        attributeRelic.setText("Số thuộc tính trên mỗi danh lam thắng cảnh "+ object.get("attributeRelic").toString());
	        linkedEachEntity.setText("Số liên kết trên mỗi thực thể "+ object.get("linkedEachEntity").toString());
	        totalLinked.setText("Tổng số liên kết giữa các thực thể "+ object.get("totalLinked").toString());


	    } catch (IOException | ParseException e) {
	        e.printStackTrace();
	    }
	}


	
	@FXML
	public void btnBackHover(MouseEvent event) {
	 	backStatistic.setCursor(Cursor.HAND);

        backStatistic.setStyle("-fx-background-radius: 8px; -fx-background-color: linear-gradient(to right, #84fab0 0%, #8fd3f4 51%, #84fab0 100%); -fx-opacity: 1; -fx-font-size: 18px;");
	
	
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



	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}

}

package crawler;

import java.util.HashMap;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.io.FileWriter;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;


import constant.Constant;
import model.CharacterEntity;

public class CharacterWiki extends AbstractCrawler {
	@SuppressWarnings("unchecked")
	public void start() throws Exception {
		String url = "https://vi.wikipedia.org/wiki/Anh_hùng_dân_tộc_Việt_Nam";
		String rootURL = "https://vi.wikipedia.org";
		crawler = new LinkedList<>();

		System.out.println("Crawler: CharacterWikiCrawler");
		try {
			Document document = Wiki.getDocument(url);
			if (document == null)
				return;

			Element table = document.selectFirst(".wikitable");
			if (table == null)
				return;

			List<String> tableHeaders = Wiki.getTableHeaders(table);
			List<List<Element>> tableElements = Wiki.getTableElements(table);
			int i =0 ;

			for (List<Element> currentRowElements : tableElements) {

				String characterName = currentRowElements.get(1).text();
				String linkToDetailedPost = rootURL + currentRowElements.get(1).selectFirst("a").attr("href");
				String characterDescription = Wiki.getDescription(linkToDetailedPost);

				Map<String, String> characterAdditionalInfo = new HashMap<String, String>();
				for (int j = 2; j < 6; j++) {
					characterAdditionalInfo.put(tableHeaders.get(j), currentRowElements.get(j).text());
				}

				crawler.add(new CharacterEntity(characterName, characterAdditionalInfo, characterDescription, rootURL));
				System.out.println("+1 Character from Wikipedia" + characterName);
				System.out.println(i+1);
				i++;
			}
			System.out.println("Tổng số nhân vật lịch sử " + i);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
		}

		JSONArray jsonArray = new JSONArray();
		for (Object object : crawler) {
			CharacterEntity character = (CharacterEntity) object;
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("Type", character.getType());
			jsonObject.put("ID", character.getId());
			jsonObject.put("Name", character.getName());
			jsonObject.put("AdditionalInfo", character.getAdditionalInfo());
			jsonObject.put("Description", character.getDescription());
			jsonObject.put("RootURL", character.getRootURL());
			jsonObject.put("Related object", character.getRelatedEntityIds());
			jsonArray.add(jsonObject);
		}

		// Write the JSON data to a file
		try (FileWriter fileWriter = new FileWriter(Constant.JSON_PATH)) {
			fileWriter.write(jsonArray.toJSONString());
			System.out.println("Crawled data written to JSON file: " + Constant.JSON_PATH);
		} catch (IOException e) {
			System.out.println("Error writing JSON file: " + e.getMessage());
		}
	}

	// test
	public static void main(String[] args) throws Exception {
		Character crawler = new Character();
		try {
			crawler.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

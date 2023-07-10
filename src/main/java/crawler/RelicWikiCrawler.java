package crawler;

import java.io.FileWriter;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import constant.Constant;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import model.RelicPlaceEntity;

public class RelicWikiCrawler extends AbstractCrawler {
    @SuppressWarnings("unchecked")
	public void start() throws Exception {
        LinkedList<Object> crawler = new LinkedList<>();
        String rootURL = "https://vi.wikipedia.org";
        String url = "https://vi.wikipedia.org/wiki/Danh_sách_Di_tích_quốc_gia_Việt_Nam";
        Document doc = Wiki.getDocument(url); // connection to wikipedia
        Elements tables = doc.select(".wikitable"); // select tables

        if (tables.size() == 0)
            return;

        for (Element table : tables) {
            List<String> table_headers = Wiki.getTableHeaders(table);
            List<List<Element>> table_datas = Wiki.getTableElements(table);

            int headerIndex = table_headers.get(0).equals("TT") ? 1 : 0;
            int cols = table_datas.get(0).size();

            for (List<Element> cell : table_datas) {
                String relicName = cell.get(headerIndex) == null ? "" : cell.get(headerIndex).text();
                if (relicName.length() < 5)
                    continue;

                String relicDescription;
                Element firstAtag = cell.get(headerIndex).selectFirst("a");

                relicDescription = firstAtag == null ? "Chưa có thông tin chi tiết"
                        : Wiki.getEntityDetails(rootURL + firstAtag.attr("href"));

                Map<String, String> relicAdditionalInfo = new HashMap<String, String>();
                for (int i = 1 + headerIndex; i < cols; i++) {
                    if (cell.get(i) == null || cell.get(i).text().equals(""))
                        continue;
                    relicAdditionalInfo.put(table_headers.get(i),
                            Wiki.getComplexInnerTextOfElement(cell.get(i)));
                }
                crawler.add(new RelicPlaceEntity(relicName, relicAdditionalInfo, relicDescription, rootURL));
                System.out.println("+1 Relic from Wikipedia: " + relicName);
            }
        }

        // Convert the crawled data to JSON
        JSONArray jsonArray = new JSONArray();
        for (Object object : crawler) {
        	RelicPlaceEntity relic = (RelicPlaceEntity) object;
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("Type", relic.getType());
            jsonObject.put("Name", relic.getName());
            jsonObject.put("AdditionalInfo", relic.getAdditionalInfo());
            jsonObject.put("Description", relic.getDescription());
            jsonObject.put("RootURL", relic.getRootURL());
            jsonObject.put("Related object", relic.getRelatedEntityIds());
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
        RelicWikiCrawler crawler = new RelicWikiCrawler();
        try {
            crawler.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

package crawler;

import java.util.HashMap;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.io.FileWriter;
import java.io.IOException;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import constant.Constant;
import model.BaseEntity;
import model.DynastyEntity;

import org.apache.commons.lang3.RandomStringUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class DynastyWiki extends AbstractCrawler {
    @SuppressWarnings("unchecked")
	public void start() {
        String url = "https://vi.wikipedia.org/wiki/Các_cuộc_chiến_tranh_Việt_Nam_tham_gia";
        String rootURL = "https://vi.wikipedia.org";
        LinkedList<Object> crawler = new LinkedList<>();

        System.out.println("Crawler: Dynasty Wiki Crawler");

        Document document = Wiki.getDocument(url);
        if (document == null) {
            System.out.println("End Dynasty Wiki Crawler");
            return;
        }

        Element content = document.selectFirst("#mw-content-text .mw-parser-output");
        if (content == null) {
            System.out.println("End Dynasty Wiki Crawler");
            return;
        }

        Map<Element, List<Element>> dynasty__tables = new HashMap<Element, List<Element>>();

        Element prev_h2 = null;
        Element prev_h3 = null;
        boolean has_h3 = false;

        for (Element child : content.children()) {
            String type = child.tagName();

            switch (type) {
                case "p":
                case "ul":
                case "figure":
                case "div":
                    break;
                case "h2":
                    prev_h2 = child;
                    has_h3 = false;
                    break;
                case "h3":
                    prev_h3 = child;
                    has_h3 = true;
                    break;
                case "table":
                    if (child.hasClass("wikitable")) {
                        Element dynasty = has_h3 ? prev_h3 : prev_h2;
                        if (dynasty__tables.get(dynasty) == null)
                            dynasty__tables.put(dynasty, new LinkedList<Element>());
                        dynasty__tables.get(dynasty).add(child);
                    }
            }
        }

        for (Map.Entry<Element, List<Element>> entry : dynasty__tables.entrySet()) {
            Element dynasty = entry.getKey();
            dynasty.selectFirst(".mw-editsection").remove();
            String dynastyName = dynasty.text();
            boolean isThisDynastyValid = false;

            Elements aTags = dynasty.select("a");
            // Nếu có đường dẫn tới bài viết chi tiết.
            if (aTags.size() != 0) {
                String linkToDetailedPost = rootURL + aTags.first().attr("href");

                Map<String, String> dynastyAdditionalInfo = Wiki.getAdditionalInfo(linkToDetailedPost);
                String dynastyDescription = Wiki.getDescription(linkToDetailedPost);

                isThisDynastyValid = (dynastyDescription != "Chưa có thông tin.")
                        || (dynastyAdditionalInfo.size() != 0);

                if (isThisDynastyValid) {
                    DynastyEntity dynastyEntity = new DynastyEntity(dynastyName, dynastyAdditionalInfo, dynastyDescription, rootURL);
                    dynastyEntity.setId(generateRandomId());
                    crawler.add(dynastyEntity);
                    System.out.println("+1 Dynasty from Wikipedia");
                }
            }
        }

        // Tạo mối liên kết giữa các đối tượng DynastyEntity
        for (Object object : crawler) {
            DynastyEntity currentDynasty = (DynastyEntity) object;
            
            for (Object otherObject : crawler) {
                if (otherObject != object) {
                    DynastyEntity otherDynasty = (DynastyEntity) otherObject;
                    
                    if (currentDynasty.isContainInEntity(otherDynasty.getName())) {
                        currentDynasty.addRelatedEntity(otherDynasty.getId());
                        otherDynasty.addRelatedEntity(currentDynasty.getId());
                    }
                }
            }
        }

        // Convert the crawled data to JSON
        JSONArray jsonArray = new JSONArray();
        for (Object object : crawler) {
            DynastyEntity dynasty = (DynastyEntity) object;
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("ID", dynasty.getId());
            jsonObject.put("Type", dynasty.getType());
            jsonObject.put("Name", dynasty.getName());
            jsonObject.put("AdditionalInfo", dynasty.getAdditionalInfo());
            jsonObject.put("Description", dynasty.getDescription());
            jsonObject.put("RootURL", dynasty.getRootURL());
            jsonObject.put("Related object", dynasty.getRelatedEntityIds());
            
            JSONArray relatedArray = new JSONArray();
            for (String entityId : dynasty.getRelatedEntityIds()) {
                relatedArray.add(entityId);
            }
            jsonObject.put("Related object", relatedArray);
            
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
    private String generateRandomId() {
        return RandomStringUtils.random(32, true, true);
    }
    // test
    public static void main(String[] args) throws Exception {
        DynastyWiki crawler = new DynastyWiki();
        try {
            crawler.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

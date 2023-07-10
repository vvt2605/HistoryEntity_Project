package crawler;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import org.apache.commons.lang3.RandomStringUtils;
import model.DynastyEntity;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import constant.Constant;

public class Dynasty extends AbstractCrawler {

    @SuppressWarnings("unchecked")
	@Override
    public void start() throws Exception {
        int MAX_PAGE = 160;
        int STEP_PAGE = 5;
        LinkedList<Object> crawler = new LinkedList<>();
        String ROOT_URL = "https://nguoikesu.com";

        for (int i = 0; i <= MAX_PAGE; i += STEP_PAGE) {
            try {
                System.out.println(i);
                String url = ROOT_URL + "/dong-lich-su?start=" + Integer.toString(i);
                Document doc = Jsoup.connect(url).timeout(5000).get();
                for (Element sup : doc.select("sup"))
                    sup.remove();
                Elements eles = doc.select("div.blog-items").select("h2").select("a[href^=/dong-lich-su/]");
                for (org.jsoup.nodes.Element ele : eles) {
                    // Entity Element definition here
                    String eName = ele.text();
                    HashMap<String, String> addInfo = new HashMap<String, String>();

                    String infoUrl = ROOT_URL + ele.attr("href");
                    Document infoDoc = Jsoup.connect(infoUrl).timeout(5000).get();

                    String cssSelector = "#content > div.com-content-category-blog.blog > div.category-desc.clearfix";
                    Element element = infoDoc.selectFirst(cssSelector);
                    if (element != null) {
                        String key = element.text();
                        // Assign the key to addInfo here
                        addInfo.put(key, "");
                    } else {
                        System.out.println("Không tìm thấy phần tử phù hợp.");
                    }

                    Element content = infoDoc.selectFirst("div.com-content-article__body");

                    StringBuilder description = new StringBuilder();
                    String whitespace = "    ";

                    Elements childs = content.children();
                    HashSet<Element> notValidTags = new HashSet<Element>();
                    for (Element child : childs) {
                        String type = child.tagName();
                        switch (type) {
                            case "figure":
                            case "table":
                                if (child.hasClass("cquote")) {
                                    break;
                                }
                            case "div":
                                notValidTags.add(child);
                                break;
                        }
                    }

                    for (Element child : notValidTags) {
                        child.remove();
                    }
                    int index_h2 = 0;
                    int index_h3 = 1;

                    // Add the actual content that needs to be displayed
                    childs = content.children();
                    int nChilds = childs.size();
                    for (int i2 = 0; i2 < nChilds; i2++) {
                        Element child = childs.get(i2);
                        if (child.text().length() == 0) {
                            continue;
                        }

                        String type = child.tagName();

                        switch (type) {
                            case "d1":
                            case "p":
                                description.append(whitespace).append(child.text()).append("\n\n");
                                break;
                            case "table":
                                if (child.hasClass("cquote")) {
                                    description.append(whitespace).append(whitespace).append(child.text()).append("\n\n");
                                }
                                break;
                            // Large Heading
                            case "h2":
                                if (i2 == nChilds - 1 || childs.get(i2 + 1).tagName() == "h2")
                                    break;
                                index_h2++;

                                description.append(index_h2).append(".").append(" ").append(child.text()).append("\n\n");
                                index_h3 = 1;
                                break;
                            // Small Heading
                            case "h3":
                                description.append(index_h2).append(".").append(index_h3).append(".").append(" ").append(child.text()).append("\n\n");
                                index_h3++;
                                break;
                            // List
                            case "ul":
                                if (child.classNames().size() == 0) {
                                    for (Element e : child.select("li")) {
                                        description.append(whitespace).append(whitespace).append("+ ").append(e.text()).append("\n\n");
                                    }
                                }
                                break;
                        }
                    }
                    DynastyEntity dynasty = new DynastyEntity(eName, addInfo, description.toString(), ROOT_URL);
                    dynasty.setId(generateRandomId());
                    crawler.add(dynasty);
                    System.out.println("Crawl completed successfully!" + eName);
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        }

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
        Dynasty crawler = new Dynasty();
        try {
            crawler.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

package crawler;
import java.util.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import constant.Constant;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class JSONRelatedObjectUpdater {
    @SuppressWarnings({ "unchecked" })
	public static void main(String[] args) {
        try {
            FileReader fileReader = new FileReader("D:\\Eclipse\\Java_oop-master\\json\\data.json");
            JSONParser jsonParser = new JSONParser();
            JSONArray jsonArray = (JSONArray) jsonParser.parse(fileReader);
            fileReader.close();
            Map<String, JSONObject> entityMap = new HashMap<>();
            for (Object object : jsonArray) {
                JSONObject entity = (JSONObject) object;
                String name = (String) entity.get("Name");
                entityMap.put(name, entity);
            }

            ListIterator<Object> iterator = jsonArray.listIterator();
            while (iterator.hasNext()) {
                JSONObject entity = (JSONObject) iterator.next();
                String currentName = (String) entity.get("Name");

                @SuppressWarnings("unchecked")
				List<String> keys = new ArrayList<>(entity.keySet());
                for (String key : keys) {
                    if (entity.get(key) instanceof String) {
                        String value = (String) entity.get(key);

                        for (String relatedName : entityMap.keySet()) {
                            if (value.contains(relatedName) && !currentName.equals(relatedName)) {
                                
                                String relatedId = (String) entityMap.get(relatedName).get("ID");

                                JSONArray relatedArray = (JSONArray) entity.getOrDefault("Related object", new JSONArray());
                                relatedArray.add(relatedId);
                                entity.put("Related object", relatedArray);

                                JSONArray relatedArray2 = (JSONArray) entityMap.get(relatedName).getOrDefault("Related object", new JSONArray());
                                relatedArray2.add((String) entity.get("ID"));
                                entityMap.get(relatedName).put("Related object", relatedArray2);
                            }
                        }
                    }
                }
            }

            // Ghi dữ liệu đã cập nhật vào tệp JSON
            FileWriter fileWriter = new FileWriter(Constant.JSON_PATH1);
            fileWriter.write(jsonArray.toJSONString());
            fileWriter.close();

            System.out.println("Cập nhật thành công!");

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }
}

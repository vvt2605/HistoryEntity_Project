package dataParser;

import java.io.FileReader;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import model.BaseEntity;

public class loadFileJson {
    private Map<String, BaseEntity> allEntities;

    public loadFileJson() {
        loadFromFileJson();
    }

    public Map<String, BaseEntity> getAllEntities() {
        return allEntities;
    }

    // danh sách thực thể theo loại
    public List<BaseEntity> getAllEntityIdsOfType(String type) {
    	if (type == "Tất cả") {
    		List<BaseEntity> list = new LinkedList<BaseEntity>();
            for (Map.Entry<String, BaseEntity> entry : allEntities.entrySet()) {
               
                list.add(entry.getValue());
            }
            Collections.sort(list, new Comparator<BaseEntity>() {
                @Override
                public int compare(BaseEntity s1, BaseEntity s2) {
                    return s1.getName().compareToIgnoreCase(s2.getName());
                }
            });
            return list;
    	}
        List<BaseEntity> list = new LinkedList<BaseEntity>();
        for (Map.Entry<String, BaseEntity> entry : allEntities.entrySet()) {
            if (entry.getValue().getType().equals(type)) {
                list.add(entry.getValue());
            }
        }
        Collections.sort(list, new Comparator<BaseEntity>() {
            @Override
            public int compare(BaseEntity s1, BaseEntity s2) {
                return s1.getName().compareToIgnoreCase(s2.getName());
            }
        });
        return list;
    }

    // thực thể theo ID
    public BaseEntity getEntityById(String id) {
        return allEntities.get(id);
    }

    // chuyển có dấu về không dấu
    public static String convertSearchString(String name) {
    	String[] accentChars = {"à", "á", "ạ", "ả", "ã", "â", "ầ", "ấ", "ậ", "ẩ", "ẫ", "ă", "ằ", "ắ", "ặ", "ẳ", "ẵ", "è", "é", "ẹ", "ẻ", "ẽ", "ê", "ề", "ế", "ệ", "ể", "ễ", "ì", "í", "ị", "ỉ", "ĩ", "ò", "ó", "ọ", "ỏ", "õ", "ô", "ồ", "ố", "ộ", "ổ", "ỗ", "ơ", "ờ", "ớ", "ợ", "ở", "ỡ", "ù", "ú", "ụ", "ủ", "ũ", "ư", "ừ", "ứ", "ự", "ử", "ữ", "ỳ", "ý", "ỵ", "ỷ", "ỹ", "đ", "À", "Á", "Ạ", "Ả", "Ã", "Â", "Ầ", "Ấ", "Ậ", "Ẩ", "Ẫ", "Ă", "Ằ", "Ắ", "Ặ", "Ẳ", "Ẵ", "È", "É", "Ẹ", "Ẻ", "Ẽ", "Ê", "Ề", "Ế", "Ệ", "Ể", "Ễ", "Ì", "Í", "Ị", "Ỉ", "Ĩ", "Ò", "Ó", "Ọ", "Ỏ", "Õ", "Ô", "Ồ", "Ố", "Ộ", "Ổ", "Ỗ", "Ơ", "Ờ", "Ớ", "Ợ", "Ở", "Ỡ", "Ù", "Ú", "Ụ", "Ủ", "Ũ", "Ư", "Ừ", "Ứ", "Ự", "Ử", "Ữ", "Ỳ", "Ý", "Ỵ", "Ỷ", "Ỹ", "Đ"};

        String[] nonAccentChars = {"a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "e", "e", "e", "e", "e", "e", "e", "e", "e", "e", "e", "i", "i", "i", "i", "i", "o", "o", "o", "o", "o", "o", "o", "o", "o", "o", "o", "o", "o", "o", "o", "o", "o", "u", "u", "u", "u", "u", "u", "u", "u", "u", "u", "u", "y", "y", "y", "y", "y", "d", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "E", "E", "E", "E", "E", "E", "E", "E", "E", "E", "E", "I", "I", "I", "I", "I", "O", "O", "O", "O", "O", "O", "O", "O", "O", "O", "O", "O", "O", "O", "O", "O", "O", "O", "U", "U", "U", "U", "U", "U", "U", "U", "U", "U", "U", "Y", "Y", "Y", "Y", "Y", "D"};

        StringBuilder stringBuilder = new StringBuilder(name);
        for (int i = 0; i < stringBuilder.length(); i++) {
            for (int j = 0; j < accentChars.length; j++) {
                if (stringBuilder.charAt(i) == accentChars[j].charAt(0)) {
                    stringBuilder.setCharAt(i, nonAccentChars[j].charAt(0));
                    break;
                }
            }
        }

        
    	//System.out.println("load success");
    	//System.out.println(stringBuilder.toString());
    	return stringBuilder.toString();
    }

    // thực thể theo tên
    public List<BaseEntity> getEntityByNameWithoutAccents(String textSearch) {
        List<BaseEntity> list = new LinkedList<BaseEntity>();
        for (Map.Entry<String, BaseEntity> entry : allEntities.entrySet()) {
            if (convertSearchString(entry.getValue().getName()).toLowerCase()
                    .contains(convertSearchString(textSearch).toLowerCase())) {
                list.add(entry.getValue());
            }
        }
        Collections.sort(list, new Comparator<BaseEntity>() {
            @Override
            public int compare(BaseEntity s1, BaseEntity s2) {
                return s1.getName().compareToIgnoreCase(s2.getName());
            }
        });
        return list;
    }
    // thực thể theo tên không dấu và theo loại
    public List<BaseEntity> getEntityByNameWithoutAccentsAndType(String textSearch, List<BaseEntity> listEntity) {
    	List<BaseEntity> list = new LinkedList<BaseEntity>();
    	for (int i = 0 ; i < listEntity.size();i++) {
    		if (convertSearchString(listEntity.get(i).getName()).toLowerCase().contains(convertSearchString(textSearch).toLowerCase())) {
                list.add(listEntity.get(i));
            }
    		
    	}
    	
        Collections.sort(list, new Comparator<BaseEntity>() {
            @Override
            public int compare(BaseEntity s1, BaseEntity s2) {
                return s1.getName().compareToIgnoreCase(s2.getName());
            }
        });
        return list;
    }

    public void loadFromFileJson() {
        // TODO Auto-generated constructor stub
        allEntities = new HashMap<String, BaseEntity>();

        JSONParser parser = new JSONParser();
        try {
            JSONObject jsonObject = (JSONObject) parser.parse(new FileReader("C:\\Workspace\\Java\\HistoryEntity13\\src\\main\\resources\\data.json"));
            JSONArray entities = (JSONArray) jsonObject.get("Entities");
            @SuppressWarnings("unchecked")
            Iterator<JSONObject> iterator = ((List<JSONObject>) entities).iterator();
            while (iterator.hasNext()) {
                JSONObject jObject = iterator.next();
                BaseEntity e = parseDataToEntities.toEntity(jObject);
                allEntities.put(e.getId(), e);
            }
            //System.out.println("load data successfully!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

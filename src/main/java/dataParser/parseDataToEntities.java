package dataParser;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import constant.Constant;
import model.BaseEntity;
import model.CharacterEntity;
import model.DynastyEntity;
import model.EventEntity;
import model.FestivalEntity;
import model.RelicPlaceEntity;





public class parseDataToEntities {
	
	//tạo đối tuợng thực thể 
	public static BaseEntity toEntity(JSONObject object) {
		String id = getStringFromJson(object, Constant.ENTITY_ID);
		String name = getStringFromJson(object, Constant.ENTITY_NAME);
		String description = getStringFromJson(object, Constant.ENTITY_DESCRIPTION);
		String type = getStringFromJson(object, Constant.ENTITY_TYPE);
		String rootUrl = getStringFromJson(object, Constant.ENTITY_ROOT_URL);

		 HashMap<String, String> additionalInfo = parseAdditionalInfor(object);

		    List<String> relatedEntityIds = parseRelatedEntityIds(object);

		    BaseEntity entity = createEntityByType(type);
		    entity.setId(id);
		    entity.setName(name);
		    entity.setDescription(description);
		    entity.setAdditionalInfo(additionalInfo);
		    entity.setRelatedEntityIds(relatedEntityIds);
		    entity.setRootURL(rootUrl);

	       // System.out.println(entity.getName());
		    
		    return entity;
	}
	
	// hàm đọc string từ json
	private static String getStringFromJson(JSONObject object, String key) {
		Object value = object.get(key);
		if (value != null && value instanceof String) {
			return (String) value;
		}
		return null;
	}
	// nhận đối tuwognj json và trả về addtionalInfor từ đối tượng đó
	private static HashMap<String, String> parseAdditionalInfor(JSONObject object){
		HashMap< String, String> additionalInfor = new HashMap<>();
		JSONObject addInfor = (JSONObject) object.get(Constant.ENTITY_ADDITIONAL_INFOR);
		 if (addInfor != null) {
		        for (Object keyObj : addInfor.keySet()) {
		            if (keyObj instanceof String) {
		                String key = (String) keyObj;
		                String value = getStringFromJson(addInfor, key);
		                if (value != null) {
		                	additionalInfor.put(key, value);
		                }
		            }
		        }
		    }
		    return additionalInfor;
		
	}
	
	// nhận đối tương json và trả về danh sách relatedEntityIds
	private static List<String> parseRelatedEntityIds(JSONObject obj) {
	    List<String> relatedEntityIds = new LinkedList<>();
	    JSONArray relatedList = (JSONArray) obj.get(Constant.ENTITY_RELATED_ENTITY_IDS);
	    if (relatedList != null) {
	        for (Object relatedIdObj : relatedList) {
	            if (relatedIdObj instanceof String) {
	                String relatedId = (String) relatedIdObj;
	                relatedEntityIds.add(relatedId);
	            }
	        }
	    }
	    return relatedEntityIds;
	}
	// tạo đối tượng từ type của entity
	
	private static BaseEntity createEntityByType(String type) {
	    switch (type) {
	    case Constant.CHARACTER_ENTITY:
            return new CharacterEntity();
        case Constant.DYNASTY_ENTITY:
            return new DynastyEntity();
        case Constant.EVENT_ENTITY:
            return new EventEntity();
        case Constant.FESTIVAL_ENTITY:
            return new FestivalEntity();
        case Constant.RELIC_PLACE_ENTITY:
            return new RelicPlaceEntity();
        default:
	        return new BaseEntity();
	    }
	}
	
	

}

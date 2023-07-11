
package dataParser;

import org.json.simple.JSONArray;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import constant.Constant;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
public class Count {
	public JSONObject readCount() throws FileNotFoundException, IOException, ParseException {
		JSONParser parser = new JSONParser();
			
	    JSONObject jsonObject = (JSONObject) parser.parse(new FileReader(Constant.JSON_PATH2));
			
		
		return jsonObject;
	}
    @SuppressWarnings("unchecked")
	public static void main(String[] args) {
        try {
            FileReader fileReader = new FileReader(Constant.JSON_PATH1);
            JSONParser jsonParser = new JSONParser();
            JSONArray jsonArray = (JSONArray) jsonParser.parse(fileReader);
            fileReader.close();

            int character = 0;
            int dynasty = 0;
            int relic = 0;
            int festival = 0;  
            
            int characterCountWiki = 0;
            int dynastyCountWiki = 0;
            int relicPlaceCountWiki = 0;
            int festivalCountWiki = 0;
            int linkCountWiki = 0;

            int characterCountNguoiKeSu = 0;
            int dynastyCountNguoiKeSu = 0;
            int relicPlaceCountNguoiKeSu = 0;
            int festivalCountNguoiKeSu = 0;
            int linkCountNguoiKeSu = 0;
            
            int totalLinked = 0;
            int totalAttributeCharacter  = 0;
            int totalAttributeDynasty = 0;
            int totalAttributeFestival = 0;
            int totalAttributeRelic = 0;
            int totalAttribute=0;
            int totalEntities = 0;
            

            for (Object object : jsonArray) {
                JSONObject entity = (JSONObject) object;
                String type = (String) entity.get("Type");
                String rootURL = (String) entity.get("RootURL");
                List<String> listRelated = (List<String>) entity.get("Related object");
                if (listRelated != null) {
                    totalLinked = totalLinked + listRelated.size();
                }
                
                totalAttribute = totalAttribute + entity.keySet().size();
                JSONObject adddInfor = (JSONObject) entity.get("AdditionalInfo");
                totalAttribute= totalAttribute + adddInfor.keySet().size() - 1;
                
                switch (type) {
                    case "CharacterEntity":
                    	totalAttributeCharacter = totalAttributeCharacter + entity.keySet().size();
                         JSONObject adddInfor1 = (JSONObject) entity.get("AdditionalInfo");
                         totalAttributeCharacter= totalAttributeCharacter + adddInfor1.keySet().size() - 1;
                        if (rootURL.equals("https://vi.wikipedia.org")) {
                            characterCountWiki++;
                        } else if (rootURL.equals("https://nguoikesu.com")) {
                            characterCountNguoiKeSu++;
                        }
                        break;
                    case "DynastyEntity":
                    	totalAttributeDynasty = totalAttributeDynasty + entity.keySet().size();
                        JSONObject adddInfor2 = (JSONObject) entity.get("AdditionalInfo");
                        totalAttributeDynasty= totalAttributeDynasty + adddInfor2.keySet().size() - 1;
                        if (rootURL.equals("https://vi.wikipedia.org")) {
                            dynastyCountWiki++;
                        } else if (rootURL.equals("https://nguoikesu.com")) {
                            dynastyCountNguoiKeSu++;
                        }
                        break;
                    case "RelicPlaceEntity":
                    	totalAttributeRelic = totalAttributeRelic + entity.keySet().size();
                        JSONObject adddInfor3 = (JSONObject) entity.get("AdditionalInfo");
                        totalAttributeRelic= totalAttributeRelic + adddInfor3.keySet().size() - 1;
                        if (rootURL.equals("https://vi.wikipedia.org")) {
                            relicPlaceCountWiki++;
                        } else if (rootURL.equals("https://nguoikesu.com")) {
                            relicPlaceCountNguoiKeSu++;
                        }
                        break;
                    case "FestivalEntity":
                    	totalAttributeFestival = totalAttributeFestival + entity.keySet().size();
                        JSONObject adddInfor4 = (JSONObject) entity.get("AdditionalInfo");
                        totalAttributeFestival= totalAttributeFestival + adddInfor4.keySet().size() - 1;
                        if (rootURL.equals("https://vi.wikipedia.org")) {
                            festivalCountWiki++;
                        } else if (rootURL.equals("https://nguoikesu.com")) {
                            festivalCountNguoiKeSu++;
                        }
                        break;
                }

                JSONArray relatedArray = (JSONArray) entity.get("Related object");
                if (relatedArray != null) {
                    if (rootURL.equals("https://vi.wikipedia.org")) {
                        linkCountWiki += relatedArray.size();
                    } else if (rootURL.equals("https://nguoikesu.com")) {
                        linkCountNguoiKeSu += relatedArray.size();
                    }
                }
            }
int totalCount=(linkCountWiki+linkCountNguoiKeSu)/2;
            JSONObject countsObject = new JSONObject();
            countsObject.put("CharacterEntity_Wiki", characterCountWiki);
            countsObject.put("DynastyEntity_Wiki", dynastyCountWiki);
            countsObject.put("RelicPlaceEntity_Wiki", relicPlaceCountWiki);
            countsObject.put("FestivalEntity_Wiki", festivalCountWiki);

            countsObject.put("CharacterEntity_NguoiKeSu", characterCountNguoiKeSu);
            countsObject.put("DynastyEntity_NguoiKeSu", dynastyCountNguoiKeSu);
            countsObject.put("RelicPlaceEntity_NguoiKeSu", relicPlaceCountNguoiKeSu);
            countsObject.put("FestivalEntity_NguoiKeSu", festivalCountNguoiKeSu);
            countsObject.put("Total Links", totalCount);
            
            countsObject.put("totalLinked", totalLinked);
            countsObject.put("totalAttribute", totalAttribute);

            character = characterCountWiki + characterCountNguoiKeSu;
            dynasty = dynastyCountWiki + dynastyCountNguoiKeSu;
            relic = relicPlaceCountWiki + relicPlaceCountNguoiKeSu;
            festival = festivalCountWiki + festivalCountNguoiKeSu;
            countsObject.put((Object)"Total character:", (Object)character);
            countsObject.put((Object)"Total Dynasty and Event:", (Object)dynasty);
            countsObject.put((Object)"Total Relic :", (Object)relic);
            countsObject.put((Object)"Total Festival:", (Object)festival);
              
            totalEntities = character + dynasty + relic +festival;
            countsObject.put("totalEntities", totalEntities);
            
            countsObject.put("attributeCharacter", Math.round((totalAttributeCharacter/character)*10)/10.0);
            countsObject.put("attributeDynasty", Math.round((totalAttributeDynasty/dynasty)*10)/10.0);
            countsObject.put("attributeFestival", Math.round((totalAttributeFestival/festival)*10)/10.0);
            countsObject.put("attributeRelic", Math.round((totalAttributeRelic/relic)*10)/10.0);
            countsObject.put("linkedEachEntity", Math.round((totalLinked/totalEntities)*10)/10.0);
            
            FileWriter fileWriter = new FileWriter(Constant.JSON_PATH2);
            fileWriter.write(countsObject.toJSONString());
            fileWriter.close();

            System.out.println("Cập nhật thành công!");

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }
}

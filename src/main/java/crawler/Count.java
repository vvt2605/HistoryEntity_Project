package crawler;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import constant.Constant;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
public class Count {
    @SuppressWarnings("unchecked")
	public static void main(String[] args) {
        try {
            FileReader fileReader = new FileReader(Constant.JSON_PATH1);
            JSONParser jsonParser = new JSONParser();
            JSONArray jsonArray = (JSONArray) jsonParser.parse(fileReader);
            fileReader.close();

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

            for (Object object : jsonArray) {
                JSONObject entity = (JSONObject) object;
                String type = (String) entity.get("Type");
                String rootURL = (String) entity.get("RootURL");

                switch (type) {
                    case "CharacterEntity":
                        if (rootURL.equals("https://vi.wikipedia.org")) {
                            characterCountWiki++;
                        } else if (rootURL.equals("https://nguoikesu.com")) {
                            characterCountNguoiKeSu++;
                        }
                        break;
                    case "DynastyEntity":
                        if (rootURL.equals("https://vi.wikipedia.org")) {
                            dynastyCountWiki++;
                        } else if (rootURL.equals("https://nguoikesu.com")) {
                            dynastyCountNguoiKeSu++;
                        }
                        break;
                    case "RelicPlaceEntity":
                        if (rootURL.equals("https://vi.wikipedia.org")) {
                            relicPlaceCountWiki++;
                        } else if (rootURL.equals("https://nguoikesu.com")) {
                            relicPlaceCountNguoiKeSu++;
                        }
                        break;
                    case "FestivalEntity":
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

            FileWriter fileWriter = new FileWriter(Constant.JSON_PATH2);
            fileWriter.write(countsObject.toJSONString());
            fileWriter.close();

            System.out.println("Cập nhật thành công!");

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }
}

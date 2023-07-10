package crawler;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import constant.Constant;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class JSonMerge {
    public static void mergeJSONFiles(String outputPath, String... inputPaths) {
        JSONArray mergedArray = new JSONArray();

        for (String inputPath : inputPaths) {
            try {
                String jsonData = new String(Files.readAllBytes(Paths.get(inputPath)));
                JSONArray jsonArray = (JSONArray) new JSONParser().parse(jsonData);
                mergeEntities(jsonArray, mergedArray);
            } catch (IOException e) {
                System.out.println("Error reading JSON file: " + e.getMessage());
            } catch (ParseException e) {
                System.out.println("Error parsing JSON file: " + e.getMessage());
            }
        }

        try (FileWriter fileWriter = new FileWriter(outputPath)) {
            fileWriter.write(mergedArray.toJSONString());
            System.out.println("Merged data written to JSON file: " + outputPath);
        } catch (IOException e) {
            System.out.println("Error writing JSON file: " + e.getMessage());
        }
    }

    private static void mergeEntities(JSONArray sourceArray, JSONArray targetArray) {
        for (Object sourceObj : sourceArray) {
            JSONObject sourceEntity = (JSONObject) sourceObj;
            boolean isDuplicate = false;

            for (Object targetObj : targetArray) {
                JSONObject targetEntity = (JSONObject) targetObj;
                if (isSameEntity(sourceEntity, targetEntity)) {
                    mergeEntity(sourceEntity, targetEntity);
                    isDuplicate = true;
                    break;
                }
            }

            if (!isDuplicate) {
                targetArray.add(sourceEntity);
            }
        }
    }

    private static boolean isSameEntity(JSONObject sourceEntity, JSONObject targetEntity) {
        String sourceName = (String) sourceEntity.get(Constant.ENTITY_NAME);
        String targetName = (String) targetEntity.get(Constant.ENTITY_NAME);

        if (sourceName != null && sourceName.equals(targetName)) {
            return true;
        }

        return false;
    }

    @SuppressWarnings("unchecked")
	private static void mergeEntity(JSONObject sourceEntity, JSONObject targetEntity) {
        JSONArray sourceRelated = (JSONArray) sourceEntity.get(Constant.ENTITY_RELATED_ENTITY_IDS);
        JSONArray targetRelated = (JSONArray) targetEntity.get(Constant.ENTITY_RELATED_ENTITY_IDS);
        if (sourceRelated != null) {

        for (Object relatedObj : sourceRelated) {
            String relatedId = (String) relatedObj;
            if (!targetRelated.contains(relatedId)) {
                targetRelated.add(relatedId);
            }
        }
        }
        String sourceDescription = (String) sourceEntity.get(Constant.ENTITY_DESCRIPTION);
        String targetDescription = (String) targetEntity.get(Constant.ENTITY_DESCRIPTION);

        if (sourceDescription != null && (targetDescription == null || sourceDescription.length() > targetDescription.length())) {
            targetEntity.put(Constant.ENTITY_DESCRIPTION, sourceDescription);
        }

        JSONObject sourceAdditionalInfo = (JSONObject) sourceEntity.get(Constant.ENTITY_ADDITIONAL_INFO);
        JSONObject targetAdditionalInfo = (JSONObject) targetEntity.get(Constant.ENTITY_ADDITIONAL_INFO);

        if (sourceAdditionalInfo != null && (targetAdditionalInfo == null || targetAdditionalInfo.isEmpty())) {
            targetEntity.put(Constant.ENTITY_ADDITIONAL_INFO, sourceAdditionalInfo);
        }
    }

    public static void main(String[] args) {
        String outputPath = Constant.JSON_PATH;
       String inputPath1 = Constant.JSON_PATH1;
        String inputPath2 = Constant.JSON_PATH1;
        


        mergeJSONFiles(outputPath, inputPath1, inputPath2);
    }
}

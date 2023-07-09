package controller;



import org.json.simple.JSONArray;


import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.util.ArrayList;

public class ReadFileJson {
	public static ArrayList<String> characters = new ArrayList<String>();
	public static ArrayList<String> dynasties = new ArrayList<String>();
	public static ArrayList<String> events = new ArrayList<String>();
	public static ArrayList<String> relicplaces = new ArrayList<String>();
	public static ArrayList<String> festivals = new ArrayList<String>();
	public static ArrayList<String> des = new ArrayList<String>();
	
	
	public ArrayList<String> getCharacters() {
	        return characters;
	    }
	public ArrayList<String> getDynastry() {
        return dynasties;
    }
	public ArrayList<String> getEvent() {
        return events;
    }
	public ArrayList<String> getRelicPlace() {
        return relicplaces;
    }
	public ArrayList<String> getFestival() {
        return festivals;
    }
	public String getDes() {
		return des.get(2);
	}
	
	public ReadFileJson() {

		JSONParser parser = new JSONParser();
    
    try {
    	JSONObject jsonObject = (JSONObject) parser.parse(new FileReader(	"C:\\Workspace\\Java\\HistoryEntity13\\src\\main\\resources\\data.json"));
    	JSONArray entities =  (JSONArray) jsonObject.get("Entities");
    	for (int i = 0 ; i < entities.size() ; i++) {
    		JSONObject entity = (JSONObject) entities.get(i);
        	String type = (String) entity.get("Type");
        	if (type.equalsIgnoreCase("CharacterEntity") == true) {
	        	//System.out.println("Type: "+ type);
	        	String name = (String) entity.get("Name");
	        	characters.add(name);
	        	String desString = (String) entity.get("Description");
	        	des.add(desString);
        	}
        	else if (type.equalsIgnoreCase("DynastyEntity") == true) {
        		String name = (String) entity.get("Name");
        		dynasties.add(name);
			}
        	else if (type.equalsIgnoreCase("EventEntity") == true) {
        		String name = (String) entity.get("Name");
        		events.add(name);
			}
        	else if (type.equalsIgnoreCase("RelicPlaceEntity") == true) {
        		String name = (String) entity.get("Name");
        		relicplaces.add(name);
			}
        	else if (type.equalsIgnoreCase("FestivalEntity") == true) {
        		String name = (String) entity.get("Name");
        		festivals.add(name);
			}


    	}
    	//System.out.println("Thanh kiểm tra :  ");
    	//System.out.println(characters);
    	for (int i = 0 ; i < 5;i++) {
    		//System.out.println("Thanh : " + characters.get(i));
    	}

    	
    } catch (Exception e) {
        e.printStackTrace();
        System.out.println("ERROR");
    		} 
    
		
}

	
	 public static void main(String[] args) { 
	    	
	    		JSONParser parser = new JSONParser();
	        
	        try {
	        	JSONObject jsonObject = (JSONObject) parser.parse(new FileReader(	"C:\\Workspace\\Java\\HistoryEntity13\\src\\main\\resources\\data.json"));
	        	JSONArray entities =  (JSONArray) jsonObject.get("Entities");
	        	for (int i = 0 ; i < entities.size() ; i++) {
	        		JSONObject entity = (JSONObject) entities.get(i);
		        	String type = (String) entity.get("Type");
		        	if (type.equalsIgnoreCase("CharacterEntity") == true) {
			        	//System.out.println("Type: "+ type);
			        	String name = (String) entity.get("Name");
			        	characters.add(name);
		        	}

	        	}
	        	System.out.println("Thanh kiểm tra :  ");
	        	//System.out.println(characters);
	        	for (int i = 0 ; i < 5;i++) {
	        		//System.out.println("Thanh : " + characters.get(i));
	        	}

	        	
	        } catch (Exception e) {
	            e.printStackTrace();
	            System.out.println("ERROR");
	    } 
	        }
	
	    

}


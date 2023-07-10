package crawler;

import java.util.LinkedList;

import java.util.HashMap;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import model.RelicPlaceEntity;

public class RelicnguoikesuCrawler extends AbstractCrawler {

	@Override
	public void start() throws Exception {
		// TODO Auto-generated method stub
		int MAX_PAGE = 20;
		int STEP_PAGE = 10;
		LinkedList<Object> crawler = new LinkedList<>();
		String ROOT_URL = "https://nguoikesu.com"; // root url
		for (int i=0; i<=MAX_PAGE; i+=STEP_PAGE) {
			try {
				String url = ROOT_URL +"/di-tich-lich-su?start=" + Integer.toString(i);
				Document doc = Jsoup.connect(url).get(); // connect to server
				for (Element sup: doc.select("sup")) // remove all notes
					sup.remove();
				Elements elements = doc.select("li.list-group-item").select("h3").select("a[href^=/dia-danh/]");
				// select all elements that have class "li.list-group-item", "h3" and have hyper reference "/dia-danh/"
				for (Element element: elements) {
					String relicName = element.text();
					HashMap<String, String> relicAdditionalInfo = new HashMap<String, String>();
					
			    	String relic_info_url = ROOT_URL + element.attr("href"); 
			    	Document data = Jsoup.connect(relic_info_url).get(); // connect to each relic in the document
			    	Elements infoEles = data.select("div.infobox"); // select all elements that contain class "div.infobox"
			    	
//			    	System.out.println(relics_info.text());
			    	
			    	if(infoEles != null) {
				    	if(infoEles.select("tr").select("th").hasAttr("scope")) 
				    		for(Element infoEle:infoEles.select("tr").select("th[scope]")) 
								relicAdditionalInfo.put(infoEle.text(), infoEle.parent().select("td").text());					    
				    	else 
				    		for(Element infoEle:infoEles.select("tr")) 
				    			if(infoEle.select("td").size() >0) 
				    				relicAdditionalInfo.put(infoEle.select("td").first().text(), infoEle.select("td").last().text());		
					}
			    	
			    	
			    	Element content = data.selectFirst("div.com-content-article__body");	    	
				    String relicDescription = Wiki.getDescription(content, false);
				    
					crawler.add(new RelicPlaceEntity(relicName, relicAdditionalInfo, relicDescription, ROOT_URL));
					System.out.println("+1 Relic from nguoikesu:");
//					System.out.println(crawler.get(crawler.size()-1).toString());
				}
			} catch (Exception e) {
				System.out.println(e);  
			}
		}
	}
//	test
	public static void main(String[] args) throws Exception {
		RelicnguoikesuCrawler crawler = new RelicnguoikesuCrawler();
		try {
			crawler.start();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
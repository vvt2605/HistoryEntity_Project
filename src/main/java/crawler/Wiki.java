package crawler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Wiki {
//	get document from wikipedia by the given url
	public static Document getDocument(String url) {
		Document doc = null;
		try {
            doc = Jsoup.connect(url).get();
            Elements sups = doc.select("sup");
    		for (Element sup: sups) sup.remove(); // remove all notes    		
        } catch (Exception e) {
            e.printStackTrace();
        }
		return doc;
	}
//	get the headers of the given table
//	Returns a list of the header contents of a Wikipedia table.
	public static List<String> getTableHeaders(Element table) {
		if (table == null) 
			return null;
		
		Elements headers = table.select("table tbody > tr th");
		int numHeaders = headers.size();
		
		List<String> listHeaderTexts = new ArrayList<String>(numHeaders);
		
		for (Element header: headers) {
			listHeaderTexts.add(header.text());
		}
		
		return listHeaderTexts;
	}
//	get the contents of the given table.
//	Returns a two-dimensional list of the elements of a Wikipedia table, excluding the header row.
	public static List<List<Element>> getTableElements(Element table){
		if (table == null) return null;

		Elements data = table.select("table tbody > tr");
		int rows = data.size()-1;
		int cols = data.get(0).select("th").size();
		
//		Initialize the 2-D list as the table data to be returned.
		List<List<Element>> tableData = new ArrayList<List<Element>>(rows);
		for (int i = 0; i < rows; i++) {
			ArrayList<Element> temp = new ArrayList<Element>(cols);
			for (int j = 0; j < cols; j++) 
				temp.add(null);
			tableData.add(new ArrayList<Element>(temp));
		}
		
		boolean[][] mark = new boolean[rows + 1][cols]; // two-dimensional array marking the elements taken.

		for (int i=1; i<=rows; i++){
			Elements cell = data.get(i).select("td"); //select the elements that contain the "td" tag in the i-th row.
			
			int num_ele = 0;
			for (int j=0; j<cols; j++){
				if (mark[i][j]) {
					continue; // if element is already taken then skip.
				}
				
				if (num_ele >= cell.size()) continue;
				
				Element ele = cell.get(num_ele);
				
				num_ele++;
				//	Multiple row handle
				String rowspan = ele.attr("rowspan");
				if (rowspan.length()==0)
					tableData.get(i-1).set(j,ele);
				else {
					int k = Integer.parseInt(rowspan);
					for (int l=0; l<k; l++) {
						tableData.get(i+l-1).set(j,ele);
						mark[i+l][j] = true;						
					}
				}
				//	Multiple columns handle
				String colspan = ele.attr("colspan");
				if (colspan.length() != 0) {
				    int k = Integer.parseInt(colspan);
				    for (int l = 0; l < k; l++) {
				        int columnIndex = j + l;
				        if (columnIndex < tableData.get(i).size()) {
				            tableData.get(i).set(columnIndex, ele);
				            mark[i][columnIndex] = true;
				        } else {
				            // Xử lý lỗi hoặc thông báo vượt quá kích thước danh sách/mảng
				        }
				    }
				}
		}
		} 
        return tableData;
		
	}
//	Reformat an innerText of an element
	public static String getComplexInnerTextOfElement(Element element) {
		if (element == null) return "";
		for (Element brTag: element.select("br")) {
			brTag.before(", ");
		}
		for (Element liTag: element.select("li")) {
			liTag.before(", ");
		}
		return element.text();
	}
//	Get more details of entity from Wikipedia's URL
	
	public static String getEntityDetails(String url) {
		String defaultDesciprt = "Chưa có thông tin chi tiết";
		
		Document doc = getDocument(url);
		if (doc == null) return defaultDesciprt;
		
		Element infor = doc.selectFirst("#mw-content-text .mw-parser-output");
		if (infor == null || infor.text().equals("")) return defaultDesciprt;
		
		return getDescription(infor, true);
	}
//	--------------------------------------------------------
	public static String getDescription(Element infor, boolean n) {
		
		StringBuilder description = new StringBuilder();
		Elements children = infor.children();
		HashSet<Element> invalidTags = new HashSet<Element>(); // store the invalid tags
		
		for (Element child: children) {
            String type = child.tagName();
            
            switch (type) {
            case "figure":
            	// type of figure
            case "table":
            	// type of table
            	if (child.hasClass("cquote")) // quote character sayings
            		break;
            case "div":
            	// a div tag
            	invalidTags.add(child);
            }
        }
		
		for (Element child: invalidTags) 
			child.remove();	// remove the invalid tags has found
		
		children = infor.children();
		int h2_index = 0, h3_index = 1, numChildren = children.size();
		
		for (int j = 0; j < numChildren; j++) {
			Element child = children.get(j);
			String type = child.tagName();
			String childText = child.text();
			
			if (childText.equals("")) continue;
			
			switch (type) {
			case "d1":
			case "p":
				description.append(" " + childText+"\n\n");
				break;
			case "table":
				if (child.hasClass("cquote")) // quote character sayings
            		description.append("  "+childText+"\n\n");
				break;
			case "h2":
				if (j==numChildren-1 || children.get(j+1).tagName()=="h2") break;
				h2_index++;
				if (n)
					child = child.selectFirst(".mw-headline");
				description.append(h2_index+ ". "+ child.text()+ "\n\n");
				h3_index=1;
				break;
			case "h3":
				if (n)
					child = child.selectFirst(".mw-headline");
				description.append(h2_index+ ". " + h3_index + child.text()+ "\n\n");
				h3_index++;
				break;
			case "ul":
				if (child.classNames().size() == 0) {
					for (Element e: child.select("li")) {
						description.append(" + " + e.text() + "\n\n");
					}
				}
				break;
			}
		}
		return description.toString();
	}
//	-----------------------------------------------------------------
	public static Map<String, String> getAdditionalInfo(String url) {
		Map<String, String> additionalInfo = new HashMap<String, String>();
		
		Document doc = getDocument(url);
		if (doc == null) return additionalInfo;
		
		Element infoBox = doc.selectFirst(".infobox");
		if (infoBox == null) return additionalInfo;
		
		Elements trs = infoBox.select("tbody > tr");
		for (Element tr: trs) {
			if (tr.hasClass("mergedrow")) continue;
			if (tr.selectFirst("td") == null || tr.selectFirst("td").text().length() == 0) continue;
			if (tr.selectFirst("th") == null || tr.selectFirst("th").text().length() == 0 || tr.selectFirst("table") != null) continue;
			
			for (Element brTag: tr.select("br")) brTag.before(",");
			additionalInfo.put(tr.selectFirst("th").text(), tr.selectFirst("td").text());
		}
		return additionalInfo;
	}
	public static String getDescription(String linkToDetailedPost) {
		Document doc = getDocument(linkToDetailedPost);
		if (doc == null)
			return "Chưa có thông tin chi tiết";

		Element content = doc.selectFirst("#mw-content-text .mw-parser-output");
		if (content == null)
			return "Chưa có thông tin chi tiết";

		StringBuilder description = new StringBuilder();
		Elements children = content.children();
		HashSet<Element> invalidTags = new HashSet<Element>(); // store the invalid tags

		for (Element child : children) {
			String type = child.tagName();

			switch (type) {
			case "figure":
				// type of figure
			case "table":
				// type of table
				if (child.hasClass("cquote")) // quote character sayings
					break;
			case "div":
				// a div tag
				invalidTags.add(child);
			}
		}

		for (Element child : invalidTags)
			child.remove(); // remove the invalid tags has found

		children = content.children();
		int h2_index = 0, h3_index = 1, numChildren = children.size();

		for (int j = 0; j < numChildren; j++) {
			Element child = children.get(j);
			String type = child.tagName();
			String childText = child.text();

			if (childText.equals(""))
				continue;

			switch (type) {
			case "h2":
				if (j == numChildren - 1 || children.get(j + 1).tagName() == "h2")
					break;
				h2_index++;
				description.append(h2_index + ". " + childText + "\n\n");
				h3_index = 1;
				break;
			case "h3":
				description.append(h2_index + ". " + h3_index + childText + "\n\n");
				h3_index++;
				break;
			case "p":
				description.append(" " + childText + "\n\n");
				break;
			case "ul":
				if (child.classNames().size() == 0) {
					for (Element e : child.select("li")) {
						description.append(" + " + e.text() + "\n\n");
					}
				}
				break;
			}
		}

		return description.toString();
	}
	}

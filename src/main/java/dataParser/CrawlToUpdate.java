package dataParser;

import java.util.ArrayList;
import java.util.List;

import crawler.AbstractCrawler;
import crawler.RelicWikiCrawler;
import crawler.RelicnguoikesuCrawler;
import crawler.DynastyWiki;
import crawler.Dynasty;
import crawler.CharacterWiki;
import crawler.FestivalWikiCrawler;
import crawler.Character;

public class CrawlToUpdate {
	public static List<AbstractCrawler> crawlers = new ArrayList<>();

	

	public void addCrawlers() {
		if (!crawlers.isEmpty()) {
			return;
		}
		crawlers.add(new Character());
		crawlers.add(new RelicnguoikesuCrawler());
		crawlers.add(new RelicWikiCrawler());
		crawlers.add(new CharacterWiki());
		crawlers.add(new FestivalWikiCrawler());
		crawlers.add(new DynastyWiki());
		crawlers.add(new Dynasty());
	}
	
	public void crawlData() {
		if (!crawlers.isEmpty()) {
			return;
		}
		crawlers.add(new Character());
		crawlers.add(new RelicnguoikesuCrawler());
		//crawlers.add(new RelicWikiCrawler());
		//crawlers.add(new CharacterWiki());
		//crawlers.add(new FestivalWikiCrawler());
		//crawlers.add(new DynastyWiki());
		//crawlers.add(new Dynasty());
		 
		// crawlers.add(new Count());
		// crawlers.add(new JSonMerge());

		for (AbstractCrawler cr : crawlers) {
			try {
				cr.start();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		// Khởi tạo lại dữ liệu cho ứng dụng
		loadFileJson load = new loadFileJson();
		load.loadFromFileJson();
	}
	public static void main(String[] args) throws Exception {
		List<AbstractCrawler> crawlers = new ArrayList<>();

		if (!crawlers.isEmpty()) {
			return;
		}
		crawlers.add(new Character());
		crawlers.add(new RelicnguoikesuCrawler());
		crawlers.add(new RelicWikiCrawler());
		crawlers.add(new CharacterWiki());
		crawlers.add(new FestivalWikiCrawler());
		crawlers.add(new DynastyWiki());
		crawlers.add(new Dynasty());
		 
		// crawlers.add(new Count());
		// crawlers.add(new JSonMerge());

		for (AbstractCrawler cr : crawlers) {
			try {
				cr.start();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		// Khởi tạo lại dữ liệu cho ứng dụng
		loadFileJson load = new loadFileJson();
		load.loadFromFileJson();
	}

}

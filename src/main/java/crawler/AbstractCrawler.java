package crawler;
import java.util.List;

import model.BaseEntity;


public abstract class AbstractCrawler {
	public abstract void start() throws Exception;
	public List<BaseEntity> crawler;
	public List<BaseEntity> getCrawledData(){
		return this.crawler;
	};
}
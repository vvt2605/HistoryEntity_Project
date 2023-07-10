package model;

import java.util.HashMap;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;


public class BaseEntity {

	protected String id;
	protected String name;
	protected String description;
	protected Map<String, String> addInfor;
	protected List<String> relatedEntityIds;
	protected String rootUrl;
	protected String type;
	
	// khởi tạo đối tượng cho lớp BaseEntity
	
	public BaseEntity() {
		this.id = UUID.randomUUID().toString();
		this.addInfor = new HashMap<String, String>();
		this.relatedEntityIds = new LinkedList<String>();
		this.description = "Chưa có thông tin";
		this.type = "Không xác định";
	}
	public BaseEntity(String name, Map<String, String> addInfor) {
		// Gọi constructor mặc định để khởi tạo các thuộc tính khác
		this();
		this.name = name;
		this.addInfor = addInfor;
	
	}
	public BaseEntity(String name, Map<String, String> addInfor, String description  ) {
		this();
		this.name = name;
		this.addInfor = addInfor;
		this.description = description;
		
	}
	public boolean isContainInEntity(String text) {
		return this.getName().contains(text) || this.getAdditionalInfo().values().toString().contains(text) || this.getDescription().contains(text);  
	}
	
	public void addRelatedEntity(String entityId) {
		if(!this.relatedEntityIds.contains(entityId))
			this.relatedEntityIds.add(entityId);
	}
	
	
	//SETTER AND GETTER
	//getter and setter
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getDescription() {
			return description;
		}
		public void setDescription(String description) {
			this.description = description;
		}
		public Map<String, String> getAdditionalInfo() {
			return addInfor;
		}
		public void setAdditionalInfo(Map<String, String> additionalInfo) {
			this.addInfor = additionalInfo;
		}
		public List<String> getRelatedEntityIds() {
			return relatedEntityIds;
		}
		public void setRelatedEntityIds(List<String> relatedEntityIds) {
			this.relatedEntityIds = relatedEntityIds;
		}
		public String getRootURL() {
			return rootUrl;
		}
		public void setRootURL(String rootURL) {
			this.rootUrl = rootURL;
		}
		public String getType() {
			return "BaseEntity";
		}



}

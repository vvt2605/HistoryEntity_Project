package model;

import java.util.Map;

import constant.Constant;

public class DynastyEntity extends BaseEntity {
	public DynastyEntity() {
		super();
	}
	public DynastyEntity(String name, Map<String, String> additionalInfo, String description ) {
		super(name, additionalInfo, description);
	}
	public DynastyEntity(String name, Map<String, String> additionalInfo, String description, String url ) {
		super(name, additionalInfo, description);
		this.setRootURL(url);
	}
	
	@Override
	public String getType() {
		return Constant.DYNASTY_ENTITY;
	}
}

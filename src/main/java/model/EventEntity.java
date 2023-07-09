package model;

import java.util.Map;

import constant.Constant;

public class EventEntity extends BaseEntity {
	public EventEntity() {
		super();
	}
	public EventEntity(String name, Map<String, String> additionalInfo, String description ) {
		super(name, additionalInfo, description);
	}
	public EventEntity(String name, Map<String, String> additionalInfo, String description, String url ) {
		super(name, additionalInfo, description);
		this.setRootURL(url);
	}
	@Override
	public String getType() {
		return Constant.EVENT_ENTITY;
	}
}

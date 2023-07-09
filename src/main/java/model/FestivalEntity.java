package model;

import java.util.Map;

import constant.Constant;

public class FestivalEntity extends BaseEntity {
	public FestivalEntity() {
		super();
	}
	public FestivalEntity(String name, Map<String, String> additionalInfo, String description ) {
		super(name, additionalInfo, description);
	}
	public FestivalEntity(String name, Map<String, String> additionalInfo, String description, String url ) {
		super(name, additionalInfo, description);
		setRootURL(url);
	}
	@Override
	public String getType() {
		return Constant.FESTIVAL_ENTITY;
	}
}

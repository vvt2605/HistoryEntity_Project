package model;

import java.util.Map;

import constant.Constant;

public class RelicPlaceEntity extends BaseEntity{
	public RelicPlaceEntity() {
		super();
	}
	public RelicPlaceEntity(String name, Map<String, String> additionalInfo, String description ) {
		super(name, additionalInfo, description);
	}
	public RelicPlaceEntity(String name, Map<String, String> additionalInfo, String description, String url ) {
		super(name, additionalInfo, description);
		setRootURL(url);
	}
	@Override
	public String getType() {
		return Constant.RELIC_PLACE_ENTITY;
	}
}

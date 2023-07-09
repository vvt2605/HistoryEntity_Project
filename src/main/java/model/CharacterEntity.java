package model;

import java.util.Map;

import constant.Constant;

public class CharacterEntity extends BaseEntity {
	public CharacterEntity() {
		super();
	}
	public CharacterEntity(String name, Map<String, String> additionalInfo, String description) {
		super(name, additionalInfo, description);
	}
	public CharacterEntity(String name, Map<String, String> additionalInfo, String description, String url) {
		super(name, additionalInfo, description);
		this.setRootURL(url);
	}
	
	@Override
	public String getType() {
		return Constant.CHARACTER_ENTITY;
	}
	
}

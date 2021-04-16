package net.supercraftalex.liquido.modules;

import java.util.ArrayList;
import java.util.List;

public class Config {
	
	private String name;
	private Object v;
	
	private boolean isConfigMode = false;
	private ConfigMode mode;
	
	public Config(String n, Object o) {
		this.v = o;
		this.name = n;
	}
	
	public Config(String n, Boolean isConfigmode, ConfigMode c) {
		this.v = c;
		this.isConfigMode = true;
		this.mode = c;
		this.name = n;
	}
	
	public Object getValue() {
		return this.v;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setValue(Object o) {
		this.v = o;
	}
	
	public ConfigMode getConfigMode() {
		return this.mode;
	}
	
}
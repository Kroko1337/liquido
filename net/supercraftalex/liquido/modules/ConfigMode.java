package net.supercraftalex.liquido.modules;

import java.util.ArrayList;
import java.util.List;

public class ConfigMode {
	
	private String value;
	private List<String> aviable = new ArrayList<String>();
	
	public void addMode(String val) {
		this.aviable.add(val);
	}
	
	public void setValue(String val) {
		if(this.aviable.contains(val)) {
			this.value = val;
		}
	}
	
	public String getValue() {
		if(this.value == null || this.value == "") {
			return this.aviable.get(0);
		} 
		return this.value;
	}
	
	public List<String> getAviable() {
		return this.aviable;
	}
	
}

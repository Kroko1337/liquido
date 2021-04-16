package net.supercraftalex.liquido.config;

import java.util.ArrayList;
import java.util.List;
import net.supercraftalex.liquido.config.configs.*;

public class ClientConfigManager {
	
	private List<ClientConfig> configs = new ArrayList<ClientConfig>();
	public ClientConfig selected;
	
	public ClientConfigManager() {
		addConfigs();
	}
	
	public void addConfigs() {
		configs.clear();
		
		configs.add(new Config_Default());
		
		selected = configs.get(configs.size()-1);
	}
	
	public void loadConfig() {
		selected.load();
	}
	
	public List<ClientConfig> get() {
		return configs;
	}
	
	public ClientConfig getConfigByName(String n) {
		for(ClientConfig c : configs) {
			if(c.name.equalsIgnoreCase(n)) {
				return c;
			}
		}
		return null;
	}
	
}
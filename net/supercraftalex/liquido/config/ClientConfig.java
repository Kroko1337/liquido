package net.supercraftalex.liquido.config;

import java.util.ArrayList;
import java.util.List;

import net.supercraftalex.liquido.ErrorManager;
import net.supercraftalex.liquido.Liquido;
import net.supercraftalex.liquido.modules.Config;
import net.supercraftalex.liquido.modules.Module;

public class ClientConfig {
	
	public String name;
	public String author;
	public List<ModuleConfig> mods = new ArrayList<ModuleConfig>();
	public boolean fileConfig = false;
	
	public ClientConfig(String configname,String configauthor) {
		name = configname;
		author = configauthor;
		for(Module m : Liquido.INSTANCE.moduleManager.getModules()) {
			mods.add(new ModuleConfig(m.getName()));
		}
	}
	public ClientConfig(String configname,String configauthor, boolean iscustom) {
		name = configname;
		author = configauthor;
		for(Module m : Liquido.INSTANCE.moduleManager.getModules()) {
			mods.add(new ModuleConfig(m.getName()));
		}
		fileConfig = iscustom;
	}
	public void save() {
		for(ModuleConfig c : mods) {
			c.save();
		}
	}
	public void load() {
		for(ModuleConfig m : mods) {
			for(Config c : m.get()) {
				try {
					Liquido.INSTANCE.moduleManager.getModuleByName(m.name).getConfigByName(c.getName()).setValue(c.getValue());
				} catch (Exception e) {
					ErrorManager.addException(e);
				}
			}
		}
	}
	public void addModule(String m) {
		mods.add(new ModuleConfig(m));
	}
	
	public class ModuleConfig {
		public String name;
		public List<Config> configs = new ArrayList<Config>();
		public ModuleConfig(String modulename) {
			name = modulename;
			configs = Liquido.INSTANCE.moduleManager.getModuleByName(name).getConfigs();
		}
		public void save() {
			configs = Liquido.INSTANCE.moduleManager.getModuleByName(name).getConfigs();
		}
		public List<Config> get() {
			return this.configs;
		}
	}
	
}
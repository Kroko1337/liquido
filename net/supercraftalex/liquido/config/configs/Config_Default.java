package net.supercraftalex.liquido.config.configs;

import net.supercraftalex.liquido.Liquido;
import net.supercraftalex.liquido.config.ClientConfig;

public class Config_Default extends ClientConfig {

	public Config_Default() {
		super("default", "alex");
		
		
		mods.add(new ModuleConfig("speed"));
		
		Liquido.INSTANCE.moduleManager.getModuleByName("speed").getConfigByName("mode").getConfigMode().setValue("NCP");
		
		this.save();
	}

}

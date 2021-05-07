package net.supercraftalex.liquido.config.configs;

import net.supercraftalex.liquido.Liquido;
import net.supercraftalex.liquido.config.ClientConfig;

public class Config_Mineplex extends ClientConfig {

	public Config_Mineplex() {
		super("mineplex", "alex");
		
		mods.add(new ModuleConfig("speed"));
		mods.add(new ModuleConfig("longjump"));
		mods.add(new ModuleConfig("criticals"));
		mods.add(new ModuleConfig("killaura"));
		
		Liquido.INSTANCE.moduleManager.getModuleByName("speed").getConfigByName("mode").getConfigMode().setValue("Mineplex");
		Liquido.INSTANCE.moduleManager.getModuleByName("longjump").getConfigByName("mode").getConfigMode().setValue("Mineplex");
		Liquido.INSTANCE.moduleManager.getModuleByName("criticals").getConfigByName("mode").getConfigMode().setValue("Jump");
		Liquido.INSTANCE.moduleManager.getModuleByName("killaura").getConfigByName("delay").setValue(new Integer(2));
		Liquido.INSTANCE.moduleManager.getModuleByName("killaura").getConfigByName("range").setValue(new Integer(4));
		Liquido.INSTANCE.moduleManager.getModuleByName("killaura").getConfigByName("autoblock").setValue(new Boolean(false));
		Liquido.INSTANCE.moduleManager.getModuleByName("killaura").getConfigByName("aiming").setValue(new Boolean(true));
		Liquido.INSTANCE.moduleManager.getModuleByName("killaura").getConfigByName("silent").setValue(new Boolean(true));
		
		this.save();
	}

}

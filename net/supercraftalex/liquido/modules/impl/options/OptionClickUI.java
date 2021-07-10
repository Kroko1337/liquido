package net.supercraftalex.liquido.modules.impl.options;

import org.lwjgl.input.Keyboard;

import net.supercraftalex.liquido.modules.Category;
import net.supercraftalex.liquido.modules.Config;
import net.supercraftalex.liquido.modules.ConfigMode;
import net.supercraftalex.liquido.modules.Module;

public class OptionClickUI extends Module {

	public OptionClickUI() {
		super("clickui", "ClickUI", Keyboard.KEY_RSHIFT, Category.OPTIONS);
		
		addConfig(new Config("Style", true, new ConfigMode()));
		getConfigByName("Style").getConfigMode().addMode("New");
		getConfigByName("Style").getConfigMode().addMode("JellyLike");
		
		addConfig(new Config("Rainbow", new Boolean(true)));
		
		addConfig(new Config("R", new Integer(1)));
		getConfigByName("R").isDouble = true;
		getConfigByName("R").doubleMax = 255;
		getConfigByName("R").doubleMin = 0;
		getConfigByName("R").doubleValue = 50;
		
		addConfig(new Config("G", new Integer(1)));
		getConfigByName("G").isDouble = true;
		getConfigByName("G").doubleMax = 255;
		getConfigByName("G").doubleMin = 0;
		getConfigByName("G").doubleValue = 168;
		
		addConfig(new Config("B", new Integer(1)));
		getConfigByName("B").isDouble = true;
		getConfigByName("B").doubleMax = 255;
		getConfigByName("B").doubleMin = 0;
		getConfigByName("B").doubleValue = 82;
	}
	
	@Override
	public void onDisable() {
		super.onDisable();
	}
	
	@Override
	public void onEnable() {
		super.onEnable();
	}
}

package net.supercraftalex.liquido.modules.impl.Combat;

import com.darkmagician6.eventapi.EventManager;
import com.darkmagician6.eventapi.EventTarget;

import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.supercraftalex.liquido.events.EventUpdate;
import net.supercraftalex.liquido.modules.Category;
import net.supercraftalex.liquido.modules.Config;
import net.supercraftalex.liquido.modules.ConfigMode;
import net.supercraftalex.liquido.modules.Module;
import net.supercraftalex.liquido.utils.TimeHelper;

public class AutoClicker extends Module {

	public AutoClicker() {
		super("autoclicker", "AutoClicker", 0, Category.COMBAT);
		addConfig(new Config("delay", new Integer(4)));
		getConfigByName("delay").isDouble = true;
		getConfigByName("delay").doubleMax = 10;
		getConfigByName("delay").doubleMin = 0;
		getConfigByName("delay").doubleValue = 4;
	}

	TimeHelper t = new TimeHelper();
	TimeHelper t2 = new TimeHelper();
	
	@EventTarget
	public void onUpdate(EventUpdate event) {	
		final int delay = (int) getConfigByName("delay").doubleValue;
		if(t.hasReached(delay * 100)) {
			
			mc.clickMouse();
			
			t.reset();
		}
	} 
	
	@Override
	public void onDisable() {
		EventManager.unregister(this);
		t.reset();
		t2.reset();
	}
	
	@Override
	public void onEnable() {
		EventManager.register(this);
		t.reset();
		t2.reset();
	}
	
}
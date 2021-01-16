package net.supercraftalex.liquido.modules.impl.Player;

import org.lwjgl.input.Keyboard;

import com.darkmagician6.eventapi.EventManager;
import com.darkmagician6.eventapi.EventTarget;

import net.supercraftalex.liquido.Booleans;
import net.supercraftalex.liquido.events.EventUpdate;
import net.supercraftalex.liquido.modules.Category;
import net.supercraftalex.liquido.modules.Module;
import net.supercraftalex.liquido.utils.Util;

public class AutoEat extends Module {

	public AutoEat() {
		super("autoeat", "AutoEat", Keyboard.KEY_NONE, Category.PLAYER);
	}

	@EventTarget
	public void onUpdate(EventUpdate event) {
		if(!Booleans.hacking_enabled) {return;}
		int i = Util.firstFoodIndex(inventory().mainInventory);
		if(player().canEat(false) && i != -1)
		{
			inventory().currentItem = i-27;
		}
	}
	
	@Override
	public void onDisable() {
		EventManager.unregister(this);
	}
	
	@Override
	public void onEnable() {
		EventManager.register(this);
	}
	
}
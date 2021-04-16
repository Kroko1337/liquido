package net.supercraftalex.liquido.modules.impl.Movement;

import org.lwjgl.input.Keyboard;

import com.darkmagician6.eventapi.EventManager;
import com.darkmagician6.eventapi.EventTarget;

import net.supercraftalex.liquido.events.EventUpdate;
import net.supercraftalex.liquido.modules.Category;
import net.supercraftalex.liquido.modules.Module;

public class Sprint extends Module {

	public Sprint() {
		super("sprint", "Sprint", Keyboard.KEY_NONE, Category.MOVEMENT);
	}
	
	@EventTarget
	public void onUpdate(EventUpdate event) {
		if((mc.thePlayer != null) && 
		(mc.thePlayer.getFoodStats().getFoodLevel() > 6) && 
		(!mc.gameSettings.keyBindSneak.pressed) && 
		(mc.gameSettings.keyBindForward.pressed) || 
		(mc.gameSettings.keyBindLeft.pressed) ||
		(mc.gameSettings.keyBindRight.pressed) ||
		(mc.gameSettings.keyBindBack.pressed)) {
			mc.thePlayer.setSprinting(true);
		}
	}
	
	@Override
	public void onEnable() {
		EventManager.register(this);
	}
	
	@Override
	public void onDisable() {
		EventManager.unregister(this);
		mc.thePlayer.setSprinting(false);
	}
	
}

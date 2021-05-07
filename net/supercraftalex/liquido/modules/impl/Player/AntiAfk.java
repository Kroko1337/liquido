package net.supercraftalex.liquido.modules.impl.Player;

import org.lwjgl.input.Keyboard;

import com.darkmagician6.eventapi.EventManager;
import com.darkmagician6.eventapi.EventTarget;

import net.supercraftalex.liquido.Booleans;
import net.supercraftalex.liquido.events.EventUpdate;
import net.supercraftalex.liquido.modules.Category;
import net.supercraftalex.liquido.modules.Config;
import net.supercraftalex.liquido.modules.Module;
import net.supercraftalex.liquido.utils.TimeHelper;

public class AntiAfk extends Module{

	public AntiAfk() {
		super("antiafk", "AntiAFK", Keyboard.KEY_NONE, Category.PLAYER);
	}
	
	int tickCount = 1;
	int afkCount = 1;
	TimeHelper timer = new TimeHelper();
	TimeHelper timer2 = new TimeHelper();
	
	@EventTarget
	public void onUpdate(EventUpdate event) {
		if(!Booleans.hacking_enabled) {return;}
		mc.gameSettings.keyBindForward.pressed = true;
		if(timer.hasReached(500)){
			mc.thePlayer.rotationYaw = mc.thePlayer.rotationYaw -= 90;
			timer.reset();
		}
	}
	
	@Override
	public void onEnable(){
		EventManager.register(this);
	}
	
	@Override
	public void onDisable() {
		EventManager.unregister(this);
		mc.gameSettings.keyBindForward.pressed = false;
	}
}
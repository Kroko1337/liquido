package net.supercraftalex.liquido.modules.impl.Player;

import org.lwjgl.input.Keyboard;

import com.darkmagician6.eventapi.EventManager;
import com.darkmagician6.eventapi.EventTarget;

import net.supercraftalex.liquido.Booleans;
import net.supercraftalex.liquido.events.EventUpdate;
import net.supercraftalex.liquido.modules.Category;
import net.supercraftalex.liquido.modules.Module;

public class AutoRespawn extends Module {

	public AutoRespawn() {
		super("autorespawn", "AutoRespawn", Keyboard.KEY_NONE, Category.PLAYER);
	}
	
	@EventTarget
	public void onUpdate(EventUpdate event) {
		if(!Booleans.hacking_enabled) {return;}
		if(mc.thePlayer.isDead){
			mc.thePlayer.respawnPlayer();
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


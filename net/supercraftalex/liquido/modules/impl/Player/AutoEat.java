package net.supercraftalex.liquido.modules.impl.Player;

import org.lwjgl.input.Keyboard;

import com.darkmagician6.eventapi.EventManager;
import com.darkmagician6.eventapi.EventTarget;

import net.minecraft.client.entity.EntityPlayerSP;
import net.supercraftalex.liquido.Booleans;
import net.supercraftalex.liquido.events.EventUpdate;
import net.supercraftalex.liquido.modules.Category;
import net.supercraftalex.liquido.modules.Module;
import net.supercraftalex.liquido.utils.PlayerUtils;

public class AutoEat extends Module {

	public AutoEat() {
		super("autoeat", "AutoEat", Keyboard.KEY_NONE, Category.PLAYER);
	}
	
	private EntityPlayerSP p = mc.thePlayer;

	@EventTarget
	public void onUpdate(EventUpdate event) {
		if(!Booleans.hacking_enabled) {return;}
		int i = PlayerUtils.firstFoodIndex(mc.thePlayer.inventory.mainInventory);
		if(p.canEat(false) && i != -1)
		{
			mc.thePlayer.inventory.currentItem = i-27;
			p.setEating(true);
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
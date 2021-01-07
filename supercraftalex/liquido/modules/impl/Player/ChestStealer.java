package net.supercraftalex.liquido.modules.impl.Player;

import org.lwjgl.input.Keyboard;

import com.darkmagician6.eventapi.EventManager;
import com.darkmagician6.eventapi.EventTarget;

import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.Slot;
import net.supercraftalex.liquido.Booleans;
import net.supercraftalex.liquido.events.EventUpdate;
import net.supercraftalex.liquido.modules.Category;
import net.supercraftalex.liquido.modules.Module;
import net.supercraftalex.liquido.utils.Timer;

public class ChestStealer extends Module{

	public ChestStealer() {
		super("cheststealer", "ChestStealer", Keyboard.KEY_U, Category.PLAYER);
	}
	
	Timer timer = new Timer();
	public static double delay = 100;

	@EventTarget
	public void onUpdate(EventUpdate event) {
		if(!Booleans.hacking_enabled) {return;}
		if(mc.thePlayer.openContainer != null){
			if(mc.thePlayer.openContainer instanceof ContainerChest){
				ContainerChest chest = (ContainerChest) mc.thePlayer.openContainer;
				int i;
				for(i = 0; i<chest.numRows*9;i++){
					if(mc.thePlayer.openContainer == null){
						break;
					}
					Slot slot =(Slot)chest.inventorySlots.get(i);
					if(slot.getStack() == null)
						continue;
					if(!timer.check((float)(delay == 0 ? 1 : delay))){
						return;
					}
					mc.playerController.windowClick(chest.windowId, i, 0, 0, mc.thePlayer);
				}
			}
		}
	}
	
	@Override
	public void onEnable() {
		EventManager.register(this);
	}
	
	@Override
	public void onDisable() {
		EventManager.unregister(this);
	}
	
}
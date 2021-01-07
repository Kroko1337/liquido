package net.supercraftalex.liquido.modules.impl.Render;

import org.lwjgl.input.Keyboard;

import com.darkmagician6.eventapi.EventManager;
import com.darkmagician6.eventapi.EventTarget;

import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.supercraftalex.liquido.events.EventUpdate;
import net.supercraftalex.liquido.modules.Category;
import net.supercraftalex.liquido.modules.Module;

public class Fullbright extends Module {

	public Fullbright() {
		super("fullbright", "Fullbright", Keyboard.KEY_B, Category.RENDER);
	}
	
	@EventTarget
	public void onUpdate(EventUpdate event) {
		mc.gameSettings.gammaSetting = 99.0F;
		if((mc.thePlayer != null) && (mc.theWorld != null)) {
			mc.thePlayer.addPotionEffect(new PotionEffect(Potion.nightVision.id,2125));
		}
	}

	@Override
	public void onDisable() {
		EventManager.unregister(this);
		if((mc.thePlayer != null) && (mc.theWorld != null)) {
			mc.thePlayer.removePotionEffect(Potion.nightVision.id);
			mc.gameSettings.gammaSetting = 1.0F;
		}
		super.onDisable();
	}
	
	@Override
	public void onEnable() {
		EventManager.register(this);
		if((mc.thePlayer != null) && (mc.theWorld != null)) {
			mc.thePlayer.addPotionEffect(new PotionEffect(Potion.nightVision.id,2125));
			mc.gameSettings.gammaSetting = 99.0F;
		}
		super.onEnable();
	}
}

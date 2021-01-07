package net.supercraftalex.liquido.modules.impl.Movement;

import org.lwjgl.input.Keyboard;

import com.darkmagician6.eventapi.EventManager;
import com.darkmagician6.eventapi.EventTarget;

import net.minecraft.util.MathHelper;
import net.supercraftalex.liquido.Booleans;
import net.supercraftalex.liquido.events.EventUpdate;
import net.supercraftalex.liquido.modules.Category;
import net.supercraftalex.liquido.modules.Module;

public class Speed extends Module {

	public Speed() {
		super("speed", "Speed", Keyboard.KEY_V, Category.MOVEMENT);
	}
	
	@EventTarget
	public void onUpdate(EventUpdate event) {
		if(!Booleans.hacking_enabled) {return;}
		Double speed = Booleans.SpeedSpeed;
		if(mc.gameSettings.keyBindForward.isKeyDown() && mc.thePlayer.onGround){
			float yaw = mc.thePlayer.rotationYaw * 0.017453292F;
			mc.thePlayer.motionX -= MathHelper.sin(yaw) * (speed/5);
			mc.thePlayer.motionZ += MathHelper.cos(yaw) * (speed/5);
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

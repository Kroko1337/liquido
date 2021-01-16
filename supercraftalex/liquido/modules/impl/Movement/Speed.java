package net.supercraftalex.liquido.modules.impl.Movement;

import org.lwjgl.input.Keyboard;

import com.darkmagician6.eventapi.EventManager;
import com.darkmagician6.eventapi.EventTarget;

import net.minecraft.util.MathHelper;
import net.supercraftalex.liquido.Booleans;
import net.supercraftalex.liquido.events.EventUpdate;
import net.supercraftalex.liquido.modules.Category;
import net.supercraftalex.liquido.modules.Config;
import net.supercraftalex.liquido.modules.ConfigMode;
import net.supercraftalex.liquido.modules.Module;

public class Speed extends Module {

	public Speed() {
		super("speed", "Speed", Keyboard.KEY_V, Category.MOVEMENT);
		addConfig(new Config("speed", new Integer(3)));
		addConfig(new Config("bhop_height", new Integer(1)));
		
		addConfig(new Config("mode", true, new ConfigMode()));
		getConfigByName("mode").getConfigMode().addMode("vanilla");
		getConfigByName("mode").getConfigMode().addMode("bhop");
	}
	
	@EventTarget
	public void onUpdate(EventUpdate event) {
		if(!Booleans.hacking_enabled) {return;}
		double speed = new Integer(getConfigByName("speed").getValue().toString()) / 5;
		double bhop_height  = new Integer(getConfigByName("bhop_height").getValue().toString())/6;
		
		if(getConfigByName("mode").getConfigMode().getValue() == "bhop") {
			if(mc.thePlayer.onGround && mc.thePlayer.moveForward > 0 && !mc.thePlayer.isInWater() && !mc.thePlayer.isInLava()){
				mc.thePlayer.motionY = bhop_height;
			}
		}
		else if(getConfigByName("mode").getConfigMode().getValue() == "vanilla") {
			if(mc.gameSettings.keyBindForward.isKeyDown() && mc.thePlayer.onGround){
				float yaw = mc.thePlayer.rotationYaw * 0.017453292F;
				mc.thePlayer.motionX -= MathHelper.sin(yaw) * (speed/5);
				mc.thePlayer.motionZ += MathHelper.cos(yaw) * (speed/5);
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
		mc.thePlayer.setSprinting(false);
	}
	
}

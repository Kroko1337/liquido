package net.supercraftalex.liquido.modules.impl.Render;

import org.lwjgl.input.Keyboard;

import com.darkmagician6.eventapi.EventManager;
import com.darkmagician6.eventapi.EventTarget;

import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.entity.EntityPlayerSP;
import net.supercraftalex.liquido.Booleans;
import net.supercraftalex.liquido.events.EventUpdate;
import net.supercraftalex.liquido.modules.Category;
import net.supercraftalex.liquido.modules.Module;
import net.supercraftalex.liquido.modules.impl.Movement.Fly;

public class FreeCam extends Module{

	public FreeCam() {
		super("freecam", "FreeCam", Keyboard.KEY_NONE, Category.RENDER);
	}
	
	private EntityPlayerSP p = mc.thePlayer;
	double oldX;
	double oldY;
	double oldZ;
	EntityOtherPlayerMP fakePlayer;
	
	public double speed = 4.0;
	
	@EventTarget
	public void onUpdate(EventUpdate event) {
		if(!Booleans.hacking_enabled) {return;}
		mc.thePlayer.motionX = 0;
		mc.thePlayer.motionY = 0;
		mc.thePlayer.motionZ = 0;
		mc.thePlayer.jumpMovementFactor = (float) speed / 2;
		
		if(mc.gameSettings.keyBindJump.pressed){
			mc.thePlayer.motionY += speed / 5;
		}
		if(mc.gameSettings.keyBindSneak.pressed){
			mc.thePlayer.motionY -= speed / 5;
		}
	}
	
	@Override
	public void onEnable() {
		EventManager.register(this);
		fakePlayer = new EntityOtherPlayerMP(mc.theWorld, mc.thePlayer.getGameProfile());
		oldX = mc.thePlayer.posX;
		oldY = mc.thePlayer.posY;
		oldZ = mc.thePlayer.posZ;
		fakePlayer.setEntityId(-1882);
		fakePlayer.clonePlayer(mc.thePlayer, true);
		fakePlayer.copyLocationAndAnglesFrom(mc.thePlayer);
		fakePlayer.rotationYawHead = mc.thePlayer.rotationYawHead;
		mc.theWorld.addEntityToWorld(fakePlayer.getEntityId(), fakePlayer);
	}
	
	@Override
	public void onDisable() {
		EventManager.unregister(this);
		mc.thePlayer.setLocationAndAngles(oldX, oldY, oldZ, mc.thePlayer.rotationYaw, mc.thePlayer.rotationPitch);
		mc.theWorld.removeEntityFromWorld(fakePlayer.getEntityId());
	}
}
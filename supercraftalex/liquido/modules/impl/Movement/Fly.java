package net.supercraftalex.liquido.modules.impl.Movement;

import org.lwjgl.input.Keyboard;

import com.darkmagician6.eventapi.EventManager;
import com.darkmagician6.eventapi.EventTarget;

import net.minecraft.network.play.client.C03PacketPlayer;
import net.supercraftalex.liquido.Booleans;
import net.supercraftalex.liquido.Liquido;
import net.supercraftalex.liquido.events.EventUpdate;
import net.supercraftalex.liquido.modules.Category;
import net.supercraftalex.liquido.modules.Module;

public class Fly extends Module{

	public Fly() {
		super("fly", "Flight", Keyboard.KEY_F, Category.MOVEMENT);
	}
	
	public static double flySpeed = (20.0F / 25);

	boolean wasFlying;
	boolean canFly;
	
	@EventTarget
	public void onUpdate() {
		if(!Booleans.hacking_enabled) {return;}
		flySpeed = Booleans.FlySpeed;
		mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer(true));
		mc.thePlayer.motionY = 0;
		mc.thePlayer.isAirBorne = false;
		if(mc.gameSettings.keyBindJump.isKeyDown()){
			mc.thePlayer.motionY += (flySpeed / 25);
		}
		if(mc.gameSettings.keyBindSneak.isKeyDown()){
			mc.thePlayer.motionY -= (flySpeed / 25);
		}
		mc.thePlayer.capabilities.setFlySpeed((float) (flySpeed/75));
		mc.thePlayer.jumpMovementFactor = ((float) (flySpeed/75));
	}
	
	@Override
	public void onEnable(){
		EventManager.register(this);
		if(mc.inGameHasFocus){
			if(mc.thePlayer.capabilities.isFlying){
				wasFlying = true;
			}else{
				wasFlying = false;
			}
			if(mc.thePlayer.capabilities.allowFlying){
				canFly = true;
			}else{
				canFly = false;
			}
			mc.thePlayer.capabilities.setFlySpeed((float) (flySpeed/100));
		}
	}
	
	@Override
	public void onDisable() {
		EventManager.unregister(this);
		if(mc.inGameHasFocus){
			if(wasFlying){
				if(!mc.thePlayer.isAirBorne){
					mc.thePlayer.motionY = 0.05;
				}
				mc.thePlayer.capabilities.isFlying = true;
			}else{
				mc.thePlayer.capabilities.isFlying = false;
			}
			if(canFly){
				mc.thePlayer.capabilities.allowFlying = true;
			}else{
				mc.thePlayer.capabilities.allowFlying = false;
			}
			mc.thePlayer.capabilities.setFlySpeed(0.05F);
		}
	}
}
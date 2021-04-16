package net.supercraftalex.liquido.modules.impl.Movement;

import org.lwjgl.input.Keyboard;

import com.darkmagician6.eventapi.EventManager;
import com.darkmagician6.eventapi.EventTarget;

import net.minecraft.client.Minecraft;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.Vec3;
import net.supercraftalex.liquido.Booleans;
import net.supercraftalex.liquido.Liquido;
import net.supercraftalex.liquido.commands.Command;
import net.supercraftalex.liquido.events.EventUpdate;
import net.supercraftalex.liquido.modules.Category;
import net.supercraftalex.liquido.modules.Config;
import net.supercraftalex.liquido.modules.ConfigMode;
import net.supercraftalex.liquido.modules.Module;
import net.supercraftalex.liquido.utils.PlayerUtils;

public class Fly extends Module{

	public Fly() {
		super("fly", "Flight", Keyboard.KEY_F, Category.MOVEMENT, true);
		addConfig(new Config("speed", new Integer(2)));
		addConfig(new Config("mode", true, new ConfigMode()));
		getConfigByName("mode").getConfigMode().addMode("Vanilla");
		getConfigByName("mode").getConfigMode().addMode("SpeedVanilla");
		getConfigByName("mode").getConfigMode().addMode("NCP");
		getConfigByName("mode").getConfigMode().addMode("MCCentral");
		getConfigByName("mode").getConfigMode().addMode("StopPort");
		getConfigByName("mode").getConfigMode().addMode("Boat");
	}
	
	public static double flySpeed = 2.0;

	boolean wasFlying;
	boolean canFly;
	int loop = 0;
    boolean ride;
	
	@EventTarget
	public void onUpdate(EventUpdate event) {
		if(!Booleans.hacking_enabled) {return;}
		flySpeed = new Integer(getConfigByName("speed").getValue().toString());
		if(getConfigByName("mode").getConfigMode().getValue().equalsIgnoreCase("Vanilla")) {
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
		if(getConfigByName("mode").getConfigMode().getValue().equalsIgnoreCase("StopPort")) {
			mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer(true));
			mc.thePlayer.motionY = -0.1;
			mc.thePlayer.isAirBorne = false;
			//mc.thePlayer.capabilities.setFlySpeed((float) (0.053));
			//mc.thePlayer.jumpMovementFactor = ((float) (0.053));
			mc.thePlayer.rotationYaw = 0;
			mc.thePlayer.setPosition(mc.thePlayer.posX, mc.thePlayer.posY-0.05, mc.thePlayer.posZ+0.5);
			if(mc.thePlayer.onGround) {
				toggled = false;
				this.onDisable();
			}
		}
		if(getConfigByName("mode").getConfigMode().getValue().equalsIgnoreCase("SpeedVanilla")) {
			mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer(true));
			mc.thePlayer.motionY = 0;
			mc.thePlayer.isAirBorne = false;
			if(mc.gameSettings.keyBindJump.isKeyDown()){
				mc.thePlayer.motionY += (flySpeed / 25);
			}
			if(mc.gameSettings.keyBindSneak.isKeyDown()){
				mc.thePlayer.motionY -= (flySpeed / 25);
			}
			mc.thePlayer.capabilities.setFlySpeed((float) (flySpeed));
			mc.thePlayer.jumpMovementFactor = ((float) (flySpeed));
		}
		if(getConfigByName("mode").getConfigMode().getValue().equalsIgnoreCase("NCP")) {
			mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer(true));
			mc.thePlayer.motionY = 0;
			if(loop >= 10) {
				System.out.println("AirStep...");
				loop = 0;
				PlayerUtils.damagePlayer(0.5);
			}
			mc.gameSettings.keyBindForward.pressed = true;
			loop++;
		}
		if(getConfigByName("mode").getConfigMode().getValue().equalsIgnoreCase("MCCentral")) {
			mc.thePlayer.motionX = 0;
			mc.thePlayer.motionY = 0;
			mc.thePlayer.motionZ = 0;
			mc.thePlayer.jumpMovementFactor = (float) 10.0 / 75;
			
			if(mc.gameSettings.keyBindJump.pressed){
				mc.thePlayer.motionY += Fly.flySpeed / 25;
			}
			if(mc.gameSettings.keyBindSneak.pressed){
				mc.thePlayer.motionY -= Fly.flySpeed / 25;
			}
		}
		if(getConfigByName("mode").getConfigMode().getValue().equalsIgnoreCase("Boat")) {
	           if (mc.thePlayer.isRiding()) {
	                this.ride = true;
	            }
	            if (this.ride) {
	                final Vec3 lookVec = mc.thePlayer.getLookVec();
	                mc.thePlayer.motionZ = lookVec.zCoord * 8;
	                mc.thePlayer.motionX = lookVec.xCoord * 8;
	                mc.thePlayer.motionY = 1.0;
	                this.ride = false;
	            }
		}
	}
	
	@Override
	public void onEnable(){
		EventManager.register(this);
		if(mc.thePlayer.onGround && getConfigByName("mode").getConfigMode().getValue().equalsIgnoreCase("StopPort")) {
			Command.messageWithPrefix("§f[fly] §aJump a bit in the void an then enable! (Only 1 or 2 blocks in the void! Then fly away!) Dont forget that you need to start from an heigher position! The fly goes down!");
			toggled = false;
			this.onDisable();
		}
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
		this.ride = false;
	}
	
	@Override
	public void onDisable() {
		EventManager.unregister(this);
		mc.gameSettings.keyBindForward.pressed = false;
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
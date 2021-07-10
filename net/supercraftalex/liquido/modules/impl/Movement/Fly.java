package net.supercraftalex.liquido.modules.impl.Movement;

import org.lwjgl.input.Keyboard;

import com.darkmagician6.eventapi.EventManager;
import com.darkmagician6.eventapi.EventTarget;

import net.minecraft.client.Minecraft;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
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
import net.supercraftalex.liquido.utils.TimeHelper;

public class Fly extends Module{

	public Fly() {
		super("fly", "Flight", Keyboard.KEY_F, Category.MOVEMENT, true);
		
		addConfig(new Config("speed", new Integer(2)));
		getConfigByName("speed").isDouble = true;
		getConfigByName("speed").doubleMax = 10;
		getConfigByName("speed").doubleMin = 1;
		getConfigByName("speed").doubleValue = 2;
		
		addConfig(new Config("mode", true, new ConfigMode()));
		getConfigByName("mode").getConfigMode().addMode("Vanilla");
		getConfigByName("mode").getConfigMode().addMode("SpeedVanilla");
		getConfigByName("mode").getConfigMode().addMode("NCP");
		getConfigByName("mode").getConfigMode().addMode("MCCentral");
		getConfigByName("mode").getConfigMode().addMode("StopPort");
		getConfigByName("mode").getConfigMode().addMode("Motion");
		getConfigByName("mode").getConfigMode().addMode("Damage");
		getConfigByName("mode").getConfigMode().addMode("Test");
		
		addConfig(new Config("test", new Integer(1)));
		getConfigByName("test").isDouble = true;
		getConfigByName("test").doubleMax = 3; //ANZAHL TEST MODI
		getConfigByName("test").doubleMin = 1;
		getConfigByName("test").doubleValue = 1;
	}
	
	public static double flySpeed = 2.0;

	boolean wasFlying;
	boolean canFly;
	int loop = 0;
    boolean ride;
    
    int startY = 0;
    
    TimeHelper th = new TimeHelper();
	
    int t1loop = 0;
    boolean t1mode = true;

	private double speed;
    
	@EventTarget
	public void onUpdate(EventUpdate event) {
		if(!Booleans.hacking_enabled) {return;}
		flySpeed = getConfigByName("speed").doubleValue;
		
		if(getConfigByName("mode").getConfigMode().getValue().equalsIgnoreCase("test")) {
			int mode = (int) Math.round(getConfigByName("test").doubleValue);
			if(mode == 1) {
				mc.timer.timerSpeed = (float) 0.2;
				mc.thePlayer.isAirBorne = false;
				float yaw = mc.thePlayer.rotationYaw * 0.017453292F;
				mc.thePlayer.motionX = MathHelper.sin(yaw) * (t1loop / 15);
				mc.thePlayer.motionZ = MathHelper.cos(yaw) * (t1loop / 15);
				mc.thePlayer.motionY = 0;
				mc.thePlayer.jumpMovementFactor = 0;
				if(mc.thePlayer.onGround) {
					onDisable();
					this.toggled = false;
				}
				
				if(t1mode) {t1loop++;} else {t1loop-= 2;}
				if(t1loop <= 0 || t1loop >= 50) {
					t1mode =! t1mode;
				}
			}
			if(mode == 2) {
				mc.timer.timerSpeed = (float) 0.2;
				mc.thePlayer.isAirBorne = false;
				mc.gameSettings.keyBindJump.pressed = false;
				mc.gameSettings.keyBindSneak.pressed = false;
				float yaw = mc.thePlayer.rotationYaw * 0.017453292F;
				mc.thePlayer.motionX = MathHelper.sin(yaw) * (t1loop / 15);
				mc.thePlayer.motionZ = MathHelper.cos(yaw) * (t1loop / 15);
				if(t1mode) {
					mc.thePlayer.motionY = 0.5;
				} else {
					mc.thePlayer.motionY = -0.5;
				}
				mc.thePlayer.jumpMovementFactor = 0;
				if(mc.thePlayer.onGround) {
					onDisable();
					this.toggled = false;
				}
				
				if(t1mode) {t1loop++;} else {t1loop-= 2;}
				if(t1loop <= 0 || t1loop >= 50) {
					t1mode =! t1mode;
				}
			}
			if(mode == 3) {
                if (this.th.hasReached(190)) {
                    mc.timer.timerSpeed = 1.35111F;
                    this.th.reset();
                } else {
                    mc.timer.timerSpeed = 0.34111F;
                    this.speed = 1.5D;
                    double d2 = mc.thePlayer.movementInput.moveForward;
                    double d3 = mc.thePlayer.movementInput.moveStrafe;
                    float f2 = mc.thePlayer.rotationYaw;
                    if ((d2 == 0.0D) && (d3 == 0.0D)) {
                        mc.thePlayer.motionX = 0.0D;
                        mc.thePlayer.motionZ = 0.0D;
                    } else {
                        if (d2 != 0.0D) {
                            if (d3 > 0.0D) {
                                f2 += (d2 > 0.0D ? -45 : 45);
                            } else if (d3 < 0.0D) {
                                f2 += (d2 > 0.0D ? 45 : -45);
                            }
                            d3 = 0.0D;
                            if (d2 > 0.0D) {
                                d2 = 1.0D;
                            } else if (d2 < 0.0D) {
                                d2 = -1.0D;
                            }
                        }
                        mc.thePlayer.setPosition(mc.thePlayer.posX + d2 * this.speed * Math.cos(Math.toRadians(f2 + 90.0F)) + d3 * this.speed * Math.sin(Math.toRadians(f2 + 90.0F)), mc.thePlayer.posY, mc.thePlayer.posZ + d2 * this.speed * Math.sin(Math.toRadians(f2 + 90.0F)) - d3 * this.speed * Math.cos(Math.toRadians(f2 + 90.0F)));
                    }
                }
                mc.thePlayer.motionY = -0.07;
				mc.thePlayer.jumpMovementFactor = 0;
				if(mc.thePlayer.onGround) {
					onDisable();
					this.toggled = false;
				}
				
			}
		}
		if(getConfigByName("mode").getConfigMode().getValue().equalsIgnoreCase("Vanilla")) {
			mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer(false));
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
			if(th.hasReached(350)) {
				mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer(false));
				mc.thePlayer.motionY = -0.1;
				mc.thePlayer.isAirBorne = false;
				float yaw = mc.thePlayer.rotationYaw * 0.017453292F;
				mc.thePlayer.setPosition(mc.thePlayer.posX - MathHelper.sin(yaw) * 2, mc.thePlayer.posY-0.7, mc.thePlayer.posZ + MathHelper.cos(yaw) * 2);
				if(mc.thePlayer.onGround) {
					toggled = false;
					this.onDisable();
				}
				th.reset();
			}
			else {
				mc.thePlayer.motionY = 0;
			}
		}
		if(getConfigByName("mode").getConfigMode().getValue().equalsIgnoreCase("SpeedVanilla")) {
			mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer(false));
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
			mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer(false));
			mc.thePlayer.motionY = 0;
			if(loop >= 10) {
				loop = 0;
				PlayerUtils.damagePlayer(0.5);
			}
			mc.gameSettings.keyBindForward.pressed = true;
			loop++;
		}
		if(getConfigByName("mode").getConfigMode().getValue().equalsIgnoreCase("Motion")) {
			mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer(false));
			mc.thePlayer.isAirBorne = false;
			//mc.thePlayer.capabilities.setFlySpeed((float) (flySpeed/75));
			float yaw = mc.thePlayer.rotationYaw * 0.017453292F;
			mc.thePlayer.motionX = MathHelper.sin(yaw) * 0.5;
			mc.thePlayer.motionZ = MathHelper.cos(yaw) * 0.5;
			mc.thePlayer.motionY = 0;
			mc.gameSettings.keyBindForward.pressed = false;
			mc.gameSettings.keyBindBack.pressed = false;
			mc.thePlayer.jumpMovementFactor = 0;
			if(mc.thePlayer.onGround) {
				onDisable();
				this.toggled = false;
			}
		}
		if(getConfigByName("mode").getConfigMode().getValue().equalsIgnoreCase("Damage")) {
			//mc.thePlayer.motionY = 0;
			if(loop >= 5) {
				loop = 0;
				if(mc.thePlayer.posY < startY) {
					PlayerUtils.damagePlayer(0.5);
				}
			}
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
	}
	
	@Override
	public void onEnable(){
		th.reset();
		t1loop = 1;
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
		this.startY = (int) mc.thePlayer.posY;
	}
	
	@Override
	public void onDisable() {
		th.reset();
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
		mc.timer.timerSpeed = 1;
	}
}
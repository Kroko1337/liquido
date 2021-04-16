package net.supercraftalex.liquido.modules.impl.Movement;

import org.lwjgl.input.Keyboard;

import com.darkmagician6.eventapi.EventManager;
import com.darkmagician6.eventapi.EventTarget;

import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.potion.Potion;
import net.supercraftalex.liquido.Booleans;
import net.supercraftalex.liquido.Liquido;
import net.supercraftalex.liquido.events.EventUpdate;
import net.supercraftalex.liquido.modules.Category;
import net.supercraftalex.liquido.modules.Config;
import net.supercraftalex.liquido.modules.ConfigMode;
import net.supercraftalex.liquido.modules.Module;
import net.supercraftalex.liquido.utils.PlayerUtils;

public class LongJump extends Module{
	
    int delay2 = 0;
    double y = 0.0D;
    double speed = 0.0D;
    boolean teleported = false;
    private float aac4Start = 0.0F;
    private float air;
    private float groundTicks;
    private int stage;
    
	public LongJump() {
		super("longjump", "LongJump", Keyboard.KEY_Y, Category.MOVEMENT, true);
		addConfig(new Config("mode", true, new ConfigMode()));
		getConfigByName("mode").getConfigMode().addMode("Hypixel");
		getConfigByName("mode").getConfigMode().addMode("Mineplex");
		getConfigByName("mode").getConfigMode().addMode("AAC4");
	}
	@Override
	public void onEnable() {
		EventManager.register(this);
        this.teleported = false;
        if (getConfigByName("mode").getConfigMode().getValue().equalsIgnoreCase("Hypixel")) {
            setMotion(0.15D);
            this.speed = 0.4D;
            this.y = 0.02D;
        }
	}
	
	@Override
	public void onDisable() {
		EventManager.unregister(this);
        this.mc.thePlayer.speedInAir = 0.02F;
        this.speed = 0.0D;
        this.delay2 = 0;
        this.mc.timer.timerSpeed = 1.0F;
	}
	
	@EventTarget
	public void onUpdate(EventUpdate event) {
		if(!Booleans.hacking_enabled) {return;}
        double d2;
        if (getConfigByName("mode").getConfigMode().getValue().equalsIgnoreCase("Hypixel")) {
            int i = 0;
            if (this.y > 0.0D) {
                this.y *= 0.9D;
            }
            this.mc.thePlayer.setPosition(this.mc.thePlayer.posX * 1.0D, this.mc.thePlayer.posY + 0.035423123132D, this.mc.thePlayer.posZ * 1.0D);
            float f1 = 0.7F + getSpeedEffect() * 0.45F;
            if (((this.mc.thePlayer.moveForward != 0.0F) || (this.mc.thePlayer.moveStrafing != 0.0F)) && (this.mc.thePlayer.onGround)) {
                this.groundTicks += 1.0F;
                setMotion(0.15D);
                this.mc.thePlayer.jump();
                this.stage = 1;
            }
            if (this.mc.thePlayer.onGround) {
                this.air = 0.0F;
            } else {
                if (this.mc.thePlayer.isCollidedVertically) {
                    this.stage = 0;
                }
                if ((this.stage > 0) && (this.stage <= 3)) {
                }
                d2 = 0.95D + getSpeedEffect() * 0.2D - this.air / 25.0F;
                if (i != 0) {
                    d2 = 1.1D + getSpeedEffect() * 0.2F - this.air / 20.0F;
                }
                if (d2 < defaultSpeed() - 0.05D) {
                    d2 = defaultSpeed() - 0.05D;
                }
                if ((this.stage < 4) && (i != 0)) {
                    d2 = defaultSpeed();
                }
                setMotion(d2 * 0.75D);
                if (this.stage > 0) {
                    this.stage |= 0x1;
                }
                this.air += f1;
            }
        }
        if (getConfigByName("mode").getConfigMode().getValue().equalsIgnoreCase("AAC4")) {
            if (this.mc.thePlayer.onGround) {
                this.mc.thePlayer.jump();
                this.mc.thePlayer.motionY += 0.2D;
                this.speed = 0.5972999999999999D;
            } else {
                this.mc.thePlayer.motionY += 0.03D;
                this.speed *= 0.99D;
            }
            setMotion(this.speed);
            if (!this.mc.thePlayer.onGround) {
                this.delay2 |= 0x1;
            }
        }
        if (getConfigByName("mode").getConfigMode().getValue().equalsIgnoreCase("Mineplex")) {
            if (this.mc.thePlayer.onGround) {
                this.mc.thePlayer.jump();
                this.mc.thePlayer.motionY += 0.1D;
                this.speed = 0.65D;
            } else {
                this.mc.thePlayer.motionY += 0.03D;
                this.speed *= 0.992D;
            }
            if ((this.mc.gameSettings.keyBindLeft.pressed) || (this.mc.gameSettings.keyBindRight.pressed)) {
                setMotion(this.speed * 0.7D);
            } else {
                setMotion(this.speed);
            }
            if (this.mc.thePlayer.onGround) {
                setMotion(0.0D);
            }
        }
	}
	
    public int getSpeedEffect() {
        if (this.mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
            return this.mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier() | 0x1;
        }
        return 0;
    }

    public double defaultSpeed() {
        double d = 0.2873D;
        if (mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
            int i = mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier();
            d *= (1.0D + 0.2D * (i | 0x1));
        }
        return d;
    }
	
	public void setMotion(double paramDouble) {
	        double d1 = this.mc.thePlayer.movementInput.moveForward;
	        double d2 = this.mc.thePlayer.movementInput.moveStrafe;
	        float f = this.mc.thePlayer.rotationYaw;
	        if ((d1 == 0.0D) && (d2 == 0.0D)) {
	            this.mc.thePlayer.motionX = 0.0D;
	            this.mc.thePlayer.motionZ = 0.0D;
	        } else {
	            if (d1 != 0.0D) {
	                if (d2 > 0.0D) {
	                    f += (d1 > 0.0D ? -45 : 45);
	                } else if (d2 < 0.0D) {
	                    f += (d1 > 0.0D ? 45 : -45);
	                }
	                d2 = 0.0D;
	                if (d1 > 0.0D) {
	                    d1 = 1.0D;
	                } else if (d1 < 0.0D) {
	                    d1 = -1.0D;
	                }
	            }
	            this.mc.thePlayer.motionX = (d1 * paramDouble * Math.cos(Math.toRadians(f + 90.0F)) + d2 * paramDouble * Math.sin(Math.toRadians(f + 90.0F)));
	            this.mc.thePlayer.motionZ = (d1 * paramDouble * Math.sin(Math.toRadians(f + 90.0F)) - d2 * paramDouble * Math.cos(Math.toRadians(f + 90.0F)));
	        }
	}
	
    double getMotion(int paramInt) {
        int i = (this.mc.thePlayer.moveStrafing == 0.0F) && (this.mc.thePlayer.moveForward == 0.0F) ? 0 : 1;
        double[] arrayOfDouble = {0.396D, -0.122D, -0.1D, 0.423D, 0.35D, 0.28D, 0.217D, 0.15D, 0.025D, -0.00625D, -0.038D, -0.0693D, -0.102D, -0.13D, -0.018D, -0.1D, -0.117D, -0.14532D, -0.1334D, -0.1581D, -0.183141D, -0.170695D, -0.195653D, -0.221D, -0.209D, -0.233D, -0.25767D, -0.314917D, -0.371019D, -0.426D};
        paramInt--;
        if ((paramInt >= 0) && (paramInt < arrayOfDouble.length)) {
            double d = arrayOfDouble[paramInt];
            return d;
        }
        return this.mc.thePlayer.motionY;
    }

}
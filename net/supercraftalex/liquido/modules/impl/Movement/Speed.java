package net.supercraftalex.liquido.modules.impl.Movement;

import org.lwjgl.input.Keyboard;

import com.darkmagician6.eventapi.EventManager;
import com.darkmagician6.eventapi.EventTarget;

import net.minecraft.client.Minecraft;
import net.minecraft.potion.Potion;
import net.minecraft.util.MathHelper;
import net.supercraftalex.liquido.Booleans;
import net.supercraftalex.liquido.events.EventUpdate;
import net.supercraftalex.liquido.modules.Category;
import net.supercraftalex.liquido.modules.Config;
import net.supercraftalex.liquido.modules.ConfigMode;
import net.supercraftalex.liquido.modules.Module;
import net.supercraftalex.liquido.utils.MotionUtils;
import net.supercraftalex.liquido.utils.MovementUtil;
import net.supercraftalex.liquido.utils.TimeHelper;

public class Speed extends Module {
	
    static float SmoothYaw = 0.0F;
    static float SmoothYaw2 = 0.0F;
    int delay2 = 0;
    int delay3 = 0;
    boolean down = true;
    boolean down2 = true;
    double speed = 0.0D;
    TimeHelper delay = new TimeHelper();
    TimeHelper delay4 = new TimeHelper();
    boolean legitHop = true;
    float yaw = 0.0F;
    static Minecraft mce = Minecraft.getMinecraft();
	
	public Speed() {
		super("speed", "Speed", Keyboard.KEY_V, Category.MOVEMENT, true);
		addConfig(new Config("speed", new Integer(4)));
		addConfig(new Config("height", new Integer(1)));
		addConfig(new Config("length", new Integer(1)));
		
		addConfig(new Config("mode", true, new ConfigMode()));
		getConfigByName("mode").getConfigMode().addMode("Hypixel");
		getConfigByName("mode").getConfigMode().addMode("vanilla");
		getConfigByName("mode").getConfigMode().addMode("bhop-test");
		getConfigByName("mode").getConfigMode().addMode("Phase");
		getConfigByName("mode").getConfigMode().addMode("NCP");
		getConfigByName("mode").getConfigMode().addMode("Reflex");
		getConfigByName("mode").getConfigMode().addMode("Mineplex");
		getConfigByName("mode").getConfigMode().addMode("CubeCraft");
		getConfigByName("mode").getConfigMode().addMode("AAC3.2.2");
	}
	
	int phaseloop = -1;
	
	@EventTarget
	public void onUpdate(EventUpdate event) {
		if(!Booleans.hacking_enabled) {return;}
		double speed = (double)new Integer(getConfigByName("speed").getValue().toString()) / 5;
		double bhop_height  = (double)new Integer(getConfigByName("height").getValue().toString())/6;
		int phase_length  = new Integer(getConfigByName("length").getValue().toString());
		phaseloop += phase_length;
		
		if(getConfigByName("mode").getConfigMode().getValue() == "bhop-test") {
			if(mc.thePlayer.onGround && mc.thePlayer.moveForward > 0 && !mc.thePlayer.isInWater() && !mc.thePlayer.isInLava()){
				mc.thePlayer.motionY = bhop_height;
				float yaw = mc.thePlayer.rotationYaw * 0.017453292F;
				mc.thePlayer.motionX -= MathHelper.sin(yaw) * (speed/5);
				mc.thePlayer.motionZ += MathHelper.cos(yaw) * (speed/5);
			}
		}
		else if(getConfigByName("mode").getConfigMode().getValue() == "vanilla") {
			if(mc.gameSettings.keyBindForward.isKeyDown() && mc.thePlayer.onGround){
				float yaw = mc.thePlayer.rotationYaw * 0.017453292F;
				mc.thePlayer.motionX -= MathHelper.sin(yaw) * (speed);
				mc.thePlayer.motionZ += MathHelper.cos(yaw) * (speed);
			}	
		} 
		else if(getConfigByName("mode").getConfigMode().getValue() == "Phase") {
			if(mc.gameSettings.keyBindForward.isKeyDown() && mc.thePlayer.onGround){
				if(phaseloop <= 40) {
					float yaw = mc.thePlayer.rotationYaw * 0.017453292F;
					mc.thePlayer.motionX -= MathHelper.sin(yaw) * (speed/5);
					mc.thePlayer.motionZ += MathHelper.cos(yaw) * (speed/5);
				}
				if(phaseloop > 70) {
					phaseloop = 0;
				}
				System.out.println(speed);
			}	
		} 
		else if(getConfigByName("mode").getConfigMode().getValue() == "NCP") {
            if (MovementUtil.isMoving()) {
                if (mc.thePlayer.onGround && (!mc.gameSettings.keyBindJump.pressed)) {
                    mc.thePlayer.jump();
                    mc.timer.timerSpeed = 1.05F;
                    mc.thePlayer.motionX *= 1.03999;
                    mc.thePlayer.motionZ *= 1.03999;
                    mc.thePlayer.moveStrafing *= 2;
                    return;
                }
                MovementUtil.strafe();
                return;
            }
            mc.timer.timerSpeed = 1F;
		}
        if (getConfigByName("mode").getConfigMode().getValue().equalsIgnoreCase("AAC3.2.2")) {
            AACFlag();
        }
        if (getConfigByName("mode").getConfigMode().getValue().equalsIgnoreCase("Mineplex")) {
        	if(mc.thePlayer.onGround) {
                mc.timer.timerSpeed = 1;
                mc.thePlayer.jump();
                mc.thePlayer.motionY *= 0.9f;
                MovementUtil.setSpeed(MovementUtil.defaultSpeed() * 1.4);
            }
            else if(!mc.thePlayer.isCollidedHorizontally && mc.thePlayer.isAirBorne) {
                mc.timer.timerSpeed = 1.3f;
                MovementUtil.setSpeed(MovementUtil.getSpeed());
                //MoveUtils.setSpeed(MoveUtils.defaultSpeed() * 0.98);
                //mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer());
            }
        }
        this.yaw = ((float) (this.yaw + 1.5D));
        this.yaw = (float) (this.yaw < mc.thePlayer.rotationYaw ? 0.62D : (float) (this.yaw - 1.5D));
        if (getConfigByName("mode").getConfigMode().getValue().equalsIgnoreCase("Hypixel")) {
            if ((mc.thePlayer.movementInput.moveForward != 0.0F) || (mc.thePlayer.movementInput.moveStrafe != 0.0F)) {
                if (mc.thePlayer.onGround) {
                    mc.timer.timerSpeed = 1.0F;
                    mc.thePlayer.jump();
                    float f1 = (float) (MotionUtils.getMotion() < 0.5600000023841858D ? MotionUtils.getMotion() * 1.0449999570846558D : 0.5600000023841858D);
                    if ((mc.thePlayer.onGround) && (mc.thePlayer.isPotionActive(Potion.moveSpeed))) {
                        f1 *= (1.0F + 0.13F * (0x1 | mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier()));
                    }
                    setMotion(f1);
                } else {
                    setMotion(MotionUtils.getMotion());
                }
            } else {
                mc.thePlayer.motionX = (mc.thePlayer.motionZ = 0.0D);
            }
        }
        if (getConfigByName("mode").getConfigMode().getValue().equalsIgnoreCase("Reflex")) {
            mc.timer.timerSpeed = 5.0F;
            setMotion(1.0D);
        }
        try {
            if (getConfigByName("mode").getConfigMode().getValue().equalsIgnoreCase("CubeCraft")) {
                if (this.delay.hasReached(190)) {
                    mc.timer.timerSpeed = 1.35111F;
                    this.delay.reset();
                } else {
                    mc.timer.timerSpeed = 0.34111F;
                    this.speed = 3.5D;
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
                mc.thePlayer.motionY = -22.0D;
            }
        } catch (Exception localException) {
        	
        }
	}
	
	@Override
	public void onEnable() {
		EventManager.register(this);
        this.yaw = mc.thePlayer.rotationYaw;
        this.speed = 0.0D;
        this.delay.reset();
        this.delay4.reset();
        this.down2 = true;
        this.down = true;
        this.delay2 = 0;
        this.delay3 = 0;
        this.legitHop = true;
        if (getConfigByName("mode").getConfigMode().getValue().equalsIgnoreCase("Mineplex")) {
            this.speed = -2.0D;
        }
	}
	
	@Override
	public void onDisable() {
		EventManager.unregister(this);
		mc.thePlayer.setSprinting(false);
        mc.thePlayer.speedInAir = 0.02F;
        mc.timer.timerSpeed = 1.0F;
        if (getConfigByName("mode").getConfigMode().getValue().equalsIgnoreCase("CubeCraft")) {
            setMotion(0.2D);
            mc.thePlayer.capabilities.isFlying = false;
            mc.thePlayer.capabilities.allowFlying = false;
        }
        if (getConfigByName("mode").getConfigMode().getValue().equalsIgnoreCase("Mineplex")) {
            this.speed = -2.0D;
        }
        mc.timer.timerSpeed = 1.0F;
	}
	
    public static double getDirection() {
        float f1 = 0.5F;
        float f2 = mce.thePlayer.rotationYaw;
        if (mce.gameSettings.keyBindLeft.pressed) {
            f2 = mce.thePlayer.rotationYaw - SmoothYaw * f1;
        }
        if (mce.gameSettings.keyBindRight.pressed) {
            f2 = mce.thePlayer.rotationYaw + SmoothYaw2 * f1;
        }
        if (mce.thePlayer.moveForward < 0.0F) {
            f2 += 180.0F;
        }
        float f3 = 1.0F;
        if (mce.thePlayer.moveForward < 0.0F) {
            f3 = -0.5F;
        } else if (mce.thePlayer.moveForward > 0.0F) {
            f3 = 0.5F;
        }
        if (mce.thePlayer.moveStrafing > 0.0F) {
            f2 -= 90.0F * f3;
        }
        if (mce.thePlayer.moveStrafing < 0.0F) {
            f2 += 90.0F * f3;
        }
        return Math.toRadians(f2);
    }
    
    public void setMotion(double paramDouble) {
        double d1 = mc.thePlayer.movementInput.moveForward;
        double d2 = mc.thePlayer.movementInput.moveStrafe;
        float f = mc.thePlayer.rotationYaw;
        if ((d1 == 0.0D) && (d2 == 0.0D)) {
            mc.thePlayer.motionX = 0.0D;
            mc.thePlayer.motionZ = 0.0D;
        } else {
            if (d1 != 0.0D) {
                int i = 20;
                if (d2 > 0.0D) {
                    f += (d1 > 0.0D ? -35 : 35);
                } else if (d2 < 0.0D) {
                    f += (d1 > 0.0D ? 35 : -35);
                }
                d2 = 0.0D;
                if (d1 > 0.0D) {
                    d1 = 1.0D;
                } else if (d1 < 0.0D) {
                    d1 = -1.0D;
                }
            }
            mc.thePlayer.motionX = (d1 * paramDouble * Math.cos(Math.toRadians(f + 90.0F)) + d2 * paramDouble * Math.sin(Math.toRadians(f + 90.0F)));
            mc.thePlayer.motionZ = (d1 * paramDouble * Math.sin(Math.toRadians(f + 90.0F)) - d2 * paramDouble * Math.cos(Math.toRadians(f + 90.0F)));
        }
    }
    
    void AACFlag() {
        double d1 = 2.0D;
        if (mc.thePlayer.moveForward != 0.0F) {
            double d2 = Math.sqrt(mc.thePlayer.motionX * mc.thePlayer.motionX + mc.thePlayer.motionZ * mc.thePlayer.motionZ);
            double d3 = -Math.sin(getDirection()) * d2 * 1.0D;
            double d4 = Math.cos(getDirection()) * d2 * 1.0D;
            mc.thePlayer.motionX -= mc.thePlayer.motionX - d3;
            mc.thePlayer.motionZ -= mc.thePlayer.motionZ - d4;
            if (mc.gameSettings.keyBindLeft.pressed) {
                if (SmoothYaw < 90.0F) {
                    if (SmoothYaw < 20.0F) {
                        SmoothYaw = (float) (SmoothYaw + 1.2D * d1);
                    }
                    if (SmoothYaw < 30.0F) {
                        SmoothYaw = (float) (SmoothYaw + 2.0D * d1);
                    }
                    if (SmoothYaw < 45.0F) {
                        SmoothYaw = (float) (SmoothYaw + 2.5D * d1);
                    }
                }
            } else {
                SmoothYaw = 0.0F;
            }
            if (mc.gameSettings.keyBindRight.pressed) {
                if (SmoothYaw2 < 90.0F) {
                    if (SmoothYaw2 < 20.0F) {
                        SmoothYaw2 = (float) (SmoothYaw2 + 1.2D * d1);
                    }
                    if (SmoothYaw2 < 30.0F) {
                        SmoothYaw2 = (float) (SmoothYaw2 + 2.0D * d1);
                    }
                    if (SmoothYaw2 < 45.0F) {
                        SmoothYaw2 = (float) (SmoothYaw2 + 2.5D * d1);
                    }
                }
            } else {
                SmoothYaw2 = 0.0F;
            }
            if (this.legitHop) {
                if (mc.thePlayer.onGround) {
                    mc.thePlayer.jump();
                    mc.thePlayer.onGround = false;
                    this.legitHop = false;
                }
                return;
            }
            if (mc.thePlayer.fallDistance > 0.1D) {
                mc.thePlayer.motionX *= 1.0099999904632568D;
                mc.thePlayer.motionZ *= 1.0099999904632568D;
            }
            if (mc.thePlayer.fallDistance > 0.4D) {
                mc.thePlayer.motionX *= 1.0099999904632568D;
                mc.thePlayer.motionZ *= 1.0099999904632568D;
            }
            if (mc.thePlayer.onGround) {
                mc.thePlayer.onGround = false;
                double d5 = -Math.sin(getDirection()) * 0.375D;
                double d6 = Math.cos(getDirection()) * 0.375D;
                mc.thePlayer.motionX = d5;
                mc.thePlayer.motionZ = d6;
                mc.thePlayer.jump();
                mc.thePlayer.motionY = 0.4D;
                if ((!mc.thePlayer.onGround) && (mc.thePlayer.motionY > 0.0D)) {
                    mc.thePlayer.motionY -= 0.014999999664723873D;
                }
            } else {
                mc.thePlayer.speedInAir = 0.0211F;
            }
        } else {
            mc.thePlayer.motionX = 0.0D;
            mc.thePlayer.motionZ = 0.0D;
            this.legitHop = true;
        }
    }
    
}

/*
OLDSPEED:
	public Speed() {
		super("speed", "Speed", Keyboard.KEY_V, Category.MOVEMENT);
		addConfig(new Config("speed", new Integer(3)));
		addConfig(new Config("bhop_height", new Integer(1)));
		
		addConfig(new Config("mode", true, new ConfigMode()));
		getConfigByName("mode").getConfigMode().addMode("vanilla");
		getConfigByName("mode").getConfigMode().addMode("bhop-test");
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
*/
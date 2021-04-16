package net.supercraftalex.liquido.modules.impl.Combat;

import org.lwjgl.input.Keyboard;

import com.darkmagician6.eventapi.EventManager;
import com.darkmagician6.eventapi.EventTarget;

import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.util.Vec3;
import net.supercraftalex.liquido.Booleans;
import net.supercraftalex.liquido.events.EventUpdate;
import net.supercraftalex.liquido.modules.Category;
import net.supercraftalex.liquido.modules.Config;
import net.supercraftalex.liquido.modules.ConfigMode;
import net.supercraftalex.liquido.modules.Module;
import net.supercraftalex.liquido.utils.OnlineRotation;
import net.supercraftalex.liquido.utils.PacketUtils;
import net.supercraftalex.liquido.utils.TimeHelper;

public class Velocity extends Module {
	
    private boolean playerAttacking = false;
    private boolean ignoreVelocity = false;
    private float velocityDirection = 0.0F;
    private Vec3 velocityStart = null;
    private S12PacketEntityVelocity velocityPacket = null;
    private TimeHelper flagless = new TimeHelper();
	
	public Velocity() {
		super("velocity", "Velocity", Keyboard.KEY_NONE, Category.COMBAT);
		addConfig(new Config("mode", true ,new ConfigMode()));
		getConfigByName("mode").getConfigMode().addMode("AAC4Flag");
		getConfigByName("mode").getConfigMode().addMode("AAC4");
	}
	
    public void setMotion(double paramDouble) {
        double d1 = this.mc.thePlayer.movementInput.moveForward;
        double d2 = this.mc.thePlayer.movementInput.moveStrafe;
        float f = OnlineRotation.getOnlineYaw();
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
	
	@EventTarget
	public void onUpdate(EventUpdate event) {
		if(!Booleans.hacking_enabled) {return;}
        if ((getConfigByName("mode").getConfigMode().getValue().equals("AAC4Flag")) && ((this.mc.thePlayer.hurtTime == 3) || (this.mc.thePlayer.hurtTime == 4))) {
            setMotion(0.05D);
        }
        if (getConfigByName("mode").getConfigMode().getValue().equals("AAC4")) {
            if (!this.flagless.hasReached(500)) {
                return;
            }
            if (this.mc.thePlayer.hurtTime == 10) {
                this.velocityStart = new Vec3(this.mc.thePlayer.motionX, 0.0D, this.mc.thePlayer.motionZ);
            }
            if ((this.mc.thePlayer.onGround) && (this.velocityPacket != null)) {
                this.mc.thePlayer.sendQueue.handleEntityVelocity(this.velocityPacket);
                this.velocityPacket = null;
            }
            if (!this.ignoreVelocity) {
                handleAAC4Velocity();
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

    private void handleAAC4Velocity() {
        if (!this.mc.thePlayer.isSprinting()) {
            return;
        }
        int i = (this.mc.thePlayer.isBlocking()) && (this.mc.thePlayer.getCurrentEquippedItem() != null) && ((this.mc.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemSword)) ? 1 : 0;
        if ((this.mc.thePlayer.hurtTime != 0) && (i == 0) && (this.mc.thePlayer.onGround)) {
            this.mc.thePlayer.jump();
        } else if ((i != 0) && (this.playerAttacking)) {
            this.playerAttacking = false;
            this.mc.thePlayer.motionX *= 0.9D;
            this.mc.thePlayer.motionZ *= 0.9D;
        }
    }
}

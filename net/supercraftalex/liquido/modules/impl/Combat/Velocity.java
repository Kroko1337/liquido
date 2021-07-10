package net.supercraftalex.liquido.modules.impl.Combat;

import org.lwjgl.input.Keyboard;

import com.darkmagician6.eventapi.EventManager;
import com.darkmagician6.eventapi.EventTarget;

import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.util.BlockPos;
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
	
	public Velocity() {
		super("velocity", "Velocity", Keyboard.KEY_NONE, Category.COMBAT);
		
		addConfig(new Config("xz", new Integer(0)));
		getConfigByName("xz").isDouble = true;
		getConfigByName("xz").doubleMax = 10;
		getConfigByName("xz").doubleMin = 0;
		getConfigByName("xz").doubleValue = 0;
		
		addConfig(new Config("mode", true, new ConfigMode()));
		getConfigByName("mode").getConfigMode().addMode("Normal");
		getConfigByName("mode").getConfigMode().addMode("Pull");
		getConfigByName("mode").getConfigMode().addMode("Jump");
	}
	
	public S12PacketEntityVelocity RunVelocity(S12PacketEntityVelocity packet, Entity e) {
		if(this.toggled) {
			if(e instanceof EntityPlayer) {
				if(e.isEntityEqual(mc.thePlayer)) {
					final int vel = (int) getConfigByName("xz").doubleValue;
					if(getConfigByName("mode").getConfigMode().getValue().equalsIgnoreCase("normal")) {
						packet.motionX *= vel/10;
						packet.motionZ *= vel/10;
					}
					if(getConfigByName("mode").getConfigMode().getValue().equalsIgnoreCase("pull")) {
						packet.motionX *= -1;
						packet.motionZ *= -1;
					}
					if(getConfigByName("mode").getConfigMode().getValue().equalsIgnoreCase("jump")) {
						if(mc.thePlayer.onGround || getDistanceToGround() <= 1.5) {
							mc.thePlayer.jump();
						}
					}
				}
			}
		}
		return packet;
	}
	
    public double getDistanceToGround() {
        double d = 0.0D;
        for (int i = 0; i < 256; i++) {
            BlockPos localBlockPos = new BlockPos(this.mc.thePlayer.posX, this.mc.thePlayer.posY - i, this.mc.thePlayer.posZ);
            if ((this.mc.theWorld.getBlockState(localBlockPos).getBlock() != Blocks.air) && (this.mc.theWorld.getBlockState(localBlockPos).getBlock() != Blocks.grass) && (this.mc.theWorld.getBlockState(localBlockPos).getBlock() != Blocks.tallgrass) && (this.mc.theWorld.getBlockState(localBlockPos).getBlock() != Blocks.red_flower) && (this.mc.theWorld.getBlockState(localBlockPos).getBlock() != Blocks.yellow_flower)) {
                d = this.mc.thePlayer.posY - localBlockPos.getY();
                return d - 1.0D;
            }
        }
        return 256.0D;
    }
	
	
}

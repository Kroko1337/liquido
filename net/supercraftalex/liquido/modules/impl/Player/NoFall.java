package net.supercraftalex.liquido.modules.impl.Player;

import org.lwjgl.input.Keyboard;

import com.darkmagician6.eventapi.EventManager;
import com.darkmagician6.eventapi.EventTarget;

import net.minecraft.client.gui.GuiGameOver;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.BlockPos;
import net.supercraftalex.liquido.Booleans;
import net.supercraftalex.liquido.events.EventUpdate;
import net.supercraftalex.liquido.modules.Category;
import net.supercraftalex.liquido.modules.Config;
import net.supercraftalex.liquido.modules.ConfigMode;
import net.supercraftalex.liquido.modules.Module;
import net.supercraftalex.liquido.utils.PacketUtils;

public class NoFall extends Module {

	public NoFall() {
		super("nofall", "NoFallDamage", Keyboard.KEY_NONE, Category.PLAYER, true);
		addConfig(new Config("mode", true, new ConfigMode()));
		getConfigByName("mode").getConfigMode().addMode("Vanilla");
		getConfigByName("mode").getConfigMode().addMode("Timer");
		getConfigByName("mode").getConfigMode().addMode("YPort");
		getConfigByName("mode").getConfigMode().addMode("Motion");
	}
	
	@EventTarget
	public void onUpdate(EventUpdate event) {
		if(!Booleans.hacking_enabled) {return;}
        if (this.mc.thePlayer.fallDistance > 3.1D) {
        	if(getConfigByName("mode").getConfigMode().getValue().equalsIgnoreCase("Vanilla")) {
        		this.mc.getNetHandler().getNetworkManager().sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY, this.mc.thePlayer.posZ, true));
        	}
        }
        if(getConfigByName("mode").getConfigMode().getValue().equalsIgnoreCase("Timer") && getDistanceToGround() > 2) {
        	PacketUtils.sendPacket(new C03PacketPlayer());
        	mc.timer.timerSpeed = 2;
        	p.posY = p.posY - getDistanceToGround();
        	PacketUtils.sendPacket(new C03PacketPlayer());
    	}
        if(getConfigByName("mode").getConfigMode().getValue().equalsIgnoreCase("Timer") && getDistanceToGround() <= 2) {
        	mc.timer.timerSpeed = 1;
    	}
        if(getConfigByName("mode").getConfigMode().getValue().equalsIgnoreCase("Motion") && getDistanceToGround() >= 5) {
        	mc.thePlayer.motionY = -100;
    	}
		if(getConfigByName("mode").getConfigMode().getValue().equalsIgnoreCase("YPort") && getDistanceToGround() >= 5) {
        	mc.thePlayer.setPosition(mc.thePlayer.posX,mc.thePlayer.posY-getDistanceToGround()+2,mc.thePlayer.posZ);
    	}
        if(mc.currentScreen instanceof GuiGameOver) { 
        	mc.timer.timerSpeed = 1;
        }
	}
	
	@Override
	public void onDisable() {
		EventManager.unregister(this);
		mc.timer.timerSpeed = 1;
	}
	
	@Override
	public void onEnable() {
		EventManager.register(this);
		mc.timer.timerSpeed = 1;
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


package net.supercraftalex.liquido.modules.impl.Movement;

import org.lwjgl.input.Keyboard;

import com.darkmagician6.eventapi.EventManager;
import com.darkmagician6.eventapi.EventTarget;

import net.minecraft.init.Blocks;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.BlockPos;
import net.supercraftalex.liquido.Booleans;
import net.supercraftalex.liquido.Liquido;
import net.supercraftalex.liquido.events.EventUpdate;
import net.supercraftalex.liquido.modules.Category;
import net.supercraftalex.liquido.modules.Config;
import net.supercraftalex.liquido.modules.Module;
import net.supercraftalex.liquido.utils.PacketUtils;

public class Jesus extends Module{

	public Jesus() {
		super("jesus", "Jesus", Keyboard.KEY_NONE, Category.MOVEMENT);
		addConfig(new Config("legit", new Boolean(false)));
	}
	
	public static boolean legit = false;
	
	@Override
	public void onEnable() {
		EventManager.register(this);
	}
	
	@Override
	public void onDisable() {
		EventManager.unregister(this);
		mc.gameSettings.keyBindJump.pressed = false;
	}
	
	@EventTarget
	public void onUpdate(EventUpdate event) {
		if(!Booleans.hacking_enabled) {return;}
		legit = new Boolean(getConfigByName("legit").getValue().toString());
		PacketUtils.sendPacket(new C03PacketPlayer(true));
		if(legit){
			if(mc.thePlayer.isInWater()){
				mc.gameSettings.keyBindJump.pressed = true;
			}else{
				mc.gameSettings.keyBindJump.pressed = false;
			}
		}else{
			if(mc.theWorld.getBlockState(new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - 0.1, mc.thePlayer.posZ)).getBlock() == Blocks.water){
				mc.thePlayer.motionY = 0;
				mc.thePlayer.onGround = true;
			}
			if(mc.thePlayer.isInWater() || mc.thePlayer.isInLava()){
				mc.thePlayer.motionY = 0.1;
				mc.thePlayer.onGround = true;
			}
		}
	}

}
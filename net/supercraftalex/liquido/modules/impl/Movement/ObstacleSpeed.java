package net.supercraftalex.liquido.modules.impl.Movement;

import org.lwjgl.input.Keyboard;

import com.darkmagician6.eventapi.EventManager;
import com.darkmagician6.eventapi.EventTarget;

import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.BlockPos;
import net.supercraftalex.liquido.Booleans;
import net.supercraftalex.liquido.events.EventUpdate;
import net.supercraftalex.liquido.modules.Category;
import net.supercraftalex.liquido.modules.Module;
import net.supercraftalex.liquido.utils.MovementUtil;

public class ObstacleSpeed extends Module {

	public ObstacleSpeed() {
		super("obstaclespeed", "ObstacleSpeed", Keyboard.KEY_NONE, Category.MOVEMENT);
	}
	
	@EventTarget
	public void onUpdate(EventUpdate e) {
		if(!Booleans.hacking_enabled) {return;}
		if(!mc.theWorld.isAirBlock(new BlockPos(p.posX, p.posY+2, p.posZ))) {
			mc.gameSettings.keyBindSprint.pressed = true;
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
	}
	
	@Override
	public void onDisable() {
		EventManager.unregister(this);
		mc.gameSettings.keyBindSprint.pressed = false;
	}
	
	@Override
	public void onEnable() {
		EventManager.register(this);
	}
	
}

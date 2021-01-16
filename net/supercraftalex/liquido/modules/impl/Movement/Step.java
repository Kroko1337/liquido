package net.supercraftalex.liquido.modules.impl.Movement;

import org.lwjgl.input.Keyboard;

import com.darkmagician6.eventapi.EventManager;
import com.darkmagician6.eventapi.EventTarget;

import net.minecraft.network.play.client.C03PacketPlayer;
import net.supercraftalex.liquido.Booleans;
import net.supercraftalex.liquido.events.EventUpdate;
import net.supercraftalex.liquido.modules.Category;
import net.supercraftalex.liquido.modules.Module;
import net.supercraftalex.liquido.utils.TimeHelper;

public class Step extends Module {

	public Step() {
		super("step", "Step", Keyboard.KEY_X, Category.MOVEMENT);
	}
	
	TimeHelper time1 = new TimeHelper();
	
	@EventTarget
	public void onUpdate(EventUpdate e) {
		if(!Booleans.hacking_enabled) {return;}
		if((mc.thePlayer.isCollidedHorizontally) && ((this.mc.gameSettings.keyBindForward.isPressed()) || (this.mc.gameSettings.keyBindBack.isPressed()) || (this.mc.gameSettings.keyBindLeft.isPressed()) || (this.mc.gameSettings.keyBindRight.isPressed())) && (mc.thePlayer.onGround) && (!this.mc.thePlayer.isOnLadder())) {
			if(this.time1.isDelayComplete(57.0F)) {
				mc.thePlayer.stepHeight = 1.2F;
				mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX,mc.thePlayer.posY + 0.42D,mc.thePlayer.posZ,mc.thePlayer.onGround));
				mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX,mc.thePlayer.posY + 0.753D,mc.thePlayer.posZ,mc.thePlayer.onGround));
				time1.reset();
			}
		} 
		else {
			
			mc.timer.timerSpeed = 1f;
			mc.thePlayer.stepHeight = 0.5f;
		
		}
	}
	
	@Override
	public void onDisable() {
		EventManager.unregister(this);
	}
	
	@Override
	public void onEnable() {
		EventManager.register(this);
	}
	
}

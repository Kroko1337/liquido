package net.supercraftalex.liquido.modules.impl.Combat;

import org.lwjgl.input.Keyboard;

import com.darkmagician6.eventapi.EventManager;
import com.darkmagician6.eventapi.EventTarget;

import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.supercraftalex.liquido.Booleans;
import net.supercraftalex.liquido.Liquido;
import net.supercraftalex.liquido.events.EventSentPacket;
import net.supercraftalex.liquido.events.EventUpdate;
import net.supercraftalex.liquido.modules.Category;
import net.supercraftalex.liquido.modules.Config;
import net.supercraftalex.liquido.modules.ConfigMode;
import net.supercraftalex.liquido.modules.Module;

public class Criticals extends Module {

	public Criticals() {
		super("criticals", "Criticals", Keyboard.KEY_NONE, Category.COMBAT);
		addConfig(new Config("mode", true, new ConfigMode()));
		getConfigByName("mode").getConfigMode().addMode("Jump");
		getConfigByName("mode").getConfigMode().addMode("Motion");
		getConfigByName("mode").getConfigMode().addMode("Port");
		getConfigByName("mode").getConfigMode().addMode("Silent");
	}

	@EventTarget
	public void onUpdate(EventSentPacket event) {
		if(!Booleans.hacking_enabled) {return;}
		
		if(event.getPacket() instanceof C02PacketUseEntity) {
			C02PacketUseEntity packet = (C02PacketUseEntity) event.getPacket();
			if(packet.entityId == Liquido.INSTANCE.target.getEntityId()) {
				if(packet.action == C02PacketUseEntity.Action.ATTACK) {
					if(mc.thePlayer.onGround) {
						if(getConfigByName("mode").getConfigMode().getValue().equalsIgnoreCase("Jump")) {
							mc.thePlayer.jump();
						} 
						if (getConfigByName("mode").getConfigMode().getValue().equalsIgnoreCase("Motion")) {
							mc.thePlayer.motionY = 0.2;
						}
						if(getConfigByName("mode").getConfigMode().getValue().equalsIgnoreCase("Port")) {
							mc.thePlayer.setPosition(mc.thePlayer.posX, mc.thePlayer.posY + 0.5, mc.thePlayer.posZ);
						}
						if(getConfigByName("mode").getConfigMode().getValue().equalsIgnoreCase("Silent")) {
							C03PacketPlayer newpacket = new C03PacketPlayer(true);
							newpacket.y = mc.thePlayer.posY + 0.5;
							newpacket.y = mc.thePlayer.posX;
							newpacket.y = mc.thePlayer.posZ;
							newpacket.yaw = mc.thePlayer.rotationYaw;
							newpacket.pitch = mc.thePlayer.rotationPitch;
							mc.myNetworkManager.sendPacket(newpacket);
						}
					}
				}
			}
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
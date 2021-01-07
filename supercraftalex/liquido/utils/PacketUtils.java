package net.supercraftalex.liquido.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;

public class PacketUtils {

	public static void sendPacket(Packet packetIn) {
		Minecraft.getMinecraft().thePlayer.sendQueue.addToSendQueue(packetIn);
	}
	public static NetworkManager getNetworkManager(){
		return NetworkPacket.networkManager;
	}
}
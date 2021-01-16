package net.supercraftalex.liquido.utils;

import com.mojang.authlib.GameProfile;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.NetworkManager;

public class NetworkPacket extends NetHandlerPlayClient{

	public static NetworkManager networkManager;
	
	public NetworkPacket(Minecraft mcIn, GuiScreen p_i46300_2_,
			NetworkManager networkManagerIn, GameProfile profileIn) {
		super(mcIn, p_i46300_2_, networkManagerIn, profileIn);
		NetworkPacket.networkManager = netManager;
	}

}
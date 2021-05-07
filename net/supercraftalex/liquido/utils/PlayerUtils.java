package net.supercraftalex.liquido.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import net.supercraftalex.liquido.ErrorManager;
import net.supercraftalex.liquido.Liquido;

public class PlayerUtils {
	
	public static boolean aacdamage = false;
	public static double aacdamagevalue;
	private static double lastX = 0.0;
	private static double lastY = 0.0;
	private static double lastZ = 0.0;
	
	public static void damagePlayer(double value) {
		aacdamage = true;
		aacdamagevalue = value + 2.85D;
		Minecraft.getMinecraft().thePlayer.moveForward++;
		Minecraft.getMinecraft().thePlayer.moveForward--;
		Minecraft.getMinecraft().thePlayer.moveStrafing--;
		Minecraft.getMinecraft().thePlayer.moveStrafing++;
		Minecraft.getMinecraft().thePlayer.jump();
	}
	
	public static double getLastX() {return lastX;}
	public static double getLastY() {return lastY;}
	public static double getLastZ() {return lastZ;}
	public static void setLastX(double n) {lastX = n;}
	public static void setLastY(double n) {lastY = n;}
	public static void setLastZ(double n) {lastZ = n;}
	
	public static int firstFoodIndex(ItemStack[] inv)
	{
		for(int i = 27; i < 36; i++)
			if(inv[i].getItem() instanceof ItemFood)
				return i - 27;
		return -1;
	}
	
	public static void messageWithoutPrefix(String msg) {
		Object chat = new ChatComponentText(msg);
		if(msg != null) {
			try {
				Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessage((IChatComponent)chat);
			} catch (Exception e) {ErrorManager.addException(e);}
		}
	}
	
	public static void messageWithPrefix(String msg) {
		messageWithoutPrefix(Liquido.INSTANCE.PREFIX+"§c"+msg);
	}
	
}

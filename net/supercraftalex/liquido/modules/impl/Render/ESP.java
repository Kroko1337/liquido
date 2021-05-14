package net.supercraftalex.liquido.modules.impl.Render;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

import com.darkmagician6.eventapi.EventManager;
import com.darkmagician6.eventapi.EventTarget;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.supercraftalex.liquido.Booleans;
import net.supercraftalex.liquido.commands.Command;
import net.supercraftalex.liquido.events.EventUpdate;
import net.supercraftalex.liquido.modules.Category;
import net.supercraftalex.liquido.modules.Module;
import net.supercraftalex.liquido.utils.RenderUtil;

public class ESP extends Module {

	public ESP() {
		super("esp", "ESP", Keyboard.KEY_NONE, Category.RENDER);
	}
	
	@EventTarget
	public void onUpdate(EventUpdate event) {
		if(!Booleans.hacking_enabled) {return;}
		for(Object theObject : mc.theWorld.loadedEntityList) {
			if(!(theObject instanceof EntityLivingBase))
				continue;
			
			EntityLivingBase entity = (EntityLivingBase) theObject;
			
			if(entity != mc.thePlayer) {
				
				Thread t = new Thread(() -> {
					//Display.initContext();
					Minecraft.getMinecraft();
					Minecraft.getMinecraft().running = false;
					int x = (int) (entity.posX - Minecraft.getMinecraft().getRenderManager().renderPosX);
					int y = (int) (entity.posY - Minecraft.getMinecraft().getRenderManager().renderPosY);
					
					mc.currentScreen.drawRect(y, x, y+2, x+2, Color.BLACK.getRGB());
					Minecraft.getMinecraft();
				});
				t.start();
				
			}
			continue;
			
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
package net.supercraftalex.liquido.modules.impl.Render;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Keyboard;
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
import net.supercraftalex.liquido.commands.Command;
import net.supercraftalex.liquido.events.EventUpdate;
import net.supercraftalex.liquido.modules.Category;
import net.supercraftalex.liquido.modules.Module;
import net.supercraftalex.liquido.utils.RenderUtil;

public class ESP extends Module {

	public ESP() {
		super("esp", "ESP", Keyboard.KEY_NONE, Category.RENDER);
	}
	
	public void render() {
		if(!this.toggled) {
			return;
		}
		for(Object theObject : mc.theWorld.loadedEntityList) {
			if(!(theObject instanceof EntityLivingBase))
				continue;
			
			EntityLivingBase entity = (EntityLivingBase) theObject;
			
			if(entity != mc.thePlayer)
				player(entity);
			continue;
			
		}
	}
	
	public void player(EntityLivingBase entity) {
		float red = 0.5F;
		float green = 0.5F;
		float blue = 1F;
		
		double xPos = (entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * mc.timer.renderPartialTicks) - mc.getRenderManager().renderPosX;
		double yPos = (entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * mc.timer.renderPartialTicks) - mc.getRenderManager().renderPosY;
		double zPos = (entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * mc.timer.renderPartialTicks) - mc.getRenderManager().renderPosZ;
		
		render(red, green, blue, xPos, yPos, zPos, entity.width, entity.height);
	}
	
	
	public void render(float red, float green, float blue, double x, double y, double z, float width, float height) {
		RenderUtil.drawEntityESP(x, y, z, width, height, red, green, blue, 0.45F, 0F, 0F, 0F, 1F, 1F);
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
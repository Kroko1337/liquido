package net.supercraftalex.liquido.modules.impl.Render;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Keyboard;

import com.darkmagician6.eventapi.EventManager;
import com.darkmagician6.eventapi.EventTarget;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
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
		List<Entity> entitys = new ArrayList<Entity>();
		entitys = mc.theWorld.loadedEntityList;
		for(Entity e : entitys) {
			if(mc.thePlayer.canEntityBeSeen(e) && e instanceof EntityLivingBase) {
				int x = RenderUtil.getScreenCoords(e.posX, e.posY, e.posZ)[1];
				int y = RenderUtil.getScreenCoords(e.posX, e.posY, e.posZ)[0];
				int length = 500;
				mc.currentScreen.drawRect(x, y, x+2, y+length, Color.green.getRGB());
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
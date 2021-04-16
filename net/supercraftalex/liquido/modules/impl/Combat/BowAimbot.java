package net.supercraftalex.liquido.modules.impl.Combat;

import java.util.List;

import org.lwjgl.input.Keyboard;

import com.darkmagician6.eventapi.EventManager;
import com.darkmagician6.eventapi.EventTarget;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.MathHelper;
import net.supercraftalex.liquido.Booleans;
import net.supercraftalex.liquido.events.EventUpdate;
import net.supercraftalex.liquido.modules.Category;
import net.supercraftalex.liquido.modules.Module;

public class BowAimbot extends Module{
	
	public BowAimbot() {
		super("bowaimbot", "BowAimBot", Keyboard.KEY_NONE, Category.COMBAT);
	}
	
	@EventTarget
	public void onUpdate(EventUpdate event) {
		if(!Booleans.hacking_enabled) {return;}
		List list = mc.theWorld.playerEntities;
		
		for(int k = 0; k < list.size(); k++){
			if(((EntityPlayer) list.get(k)).getName() == mc.thePlayer.getName()){
				continue;
			}
			
			EntityPlayer entPlayer = (EntityPlayer) list.get(k);
			
			if(mc.thePlayer.getDistanceToEntity(entPlayer) > mc.thePlayer.getDistanceToEntity((Entity) list.get(k))){
				entPlayer = (EntityPlayer) list.get(k);
			}
			
			float f = mc.thePlayer.getDistanceToEntity(entPlayer);
			if ( f < 100F && mc.thePlayer.canEntityBeSeen(entPlayer)){
				this.faceEntity(entPlayer);
			}
		}
	}
	
	public synchronized void faceEntity(Entity entity) {
		final float[] rotations = getRotationsNeeded(entity);

		if (rotations != null) {
			mc.thePlayer.rotationYaw = rotations[0];
			mc.thePlayer.rotationPitch = rotations[1] + 1.0F;// 14
		}
	}

	public float[] getRotationsNeeded(Entity entity) {
		if (entity == null) {
			return null;
		}

		final double diffX = entity.posX - mc.thePlayer.posX;
		final double diffZ = entity.posZ - mc.thePlayer.posZ;
		double diffY;

		if (entity instanceof EntityPlayer) {
			final EntityPlayer entityLivingBase = (EntityPlayer) entity;
			diffY = entityLivingBase.posY + entityLivingBase.getEyeHeight() - (mc.thePlayer.posY + mc.thePlayer.getEyeHeight());
		} else if(entity instanceof EntityMob){
			final EntityMob entityLivingBase = (EntityMob) entity;
			diffY = (entity.boundingBox.minY + entity.boundingBox.maxY) / 2.0D - (mc.thePlayer.posY + mc.thePlayer.getEyeHeight());
		}else{
			final EntityLivingBase entityLivingBase = (EntityLivingBase) entity;
			diffY = (entity.boundingBox.minY + entity.boundingBox.maxY) / 2.0D - (mc.thePlayer.posY + mc.thePlayer.getEyeHeight());
		}

		final double dist = MathHelper.sqrt_double(diffX * diffX + diffZ * diffZ);
		final float yaw = (float) (Math.atan2(diffZ, diffX) * 180.0D / Math.PI) - 90.0F;
		final float pitch = (float) -(Math.atan2(diffY, dist) * 180.0D / Math.PI);
		return new float[] {
			mc.thePlayer.rotationYaw + MathHelper.wrapAngleTo180_float(yaw - mc.thePlayer.rotationYaw), mc.thePlayer.rotationPitch + MathHelper.wrapAngleTo180_float(pitch - mc.thePlayer.rotationPitch)
		};
	}
	
	@Override
	public void onEnable() {
		EventManager.register(this);
	}
	
	@Override
	public void onDisable() {
		EventManager.unregister(this);
	}
	
}
package net.supercraftalex.liquido.modules.impl.Combat;

import java.util.List;

import org.lwjgl.input.Keyboard;

import com.darkmagician6.eventapi.EventManager;
import com.darkmagician6.eventapi.EventTarget;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import net.supercraftalex.liquido.Booleans;
import net.supercraftalex.liquido.events.EventUpdate;
import net.supercraftalex.liquido.modules.Category;
import net.supercraftalex.liquido.modules.Config;
import net.supercraftalex.liquido.modules.Module;

public class KillAura extends Module{

	public KillAura() {
		super("killaura", "KillAura", Keyboard.KEY_R, Category.COMBAT);
		addConfig(new Config("delay", new Integer(4)));
	}
	
	@EventTarget
	public void onUpdate(EventUpdate event) {
		if(!Booleans.hacking_enabled) {return;}
		killaura();
	}

	int delay;

	private void killaura() {
		List list = mc.theWorld.playerEntities;
		delay++;

		for (int k = 0; k < list.size(); k++) {
			if (((EntityPlayer) list.get(k)).getName() == mc.thePlayer.getName()) {
				continue;
			}

			EntityPlayer entityplayer = (EntityPlayer) list.get(1);

			if (mc.thePlayer.getDistanceToEntity(entityplayer) > mc.thePlayer.getDistanceToEntity((Entity) list.get(k))) {
				entityplayer = (EntityPlayer) list.get(k);
			}

			float f = mc.thePlayer.getDistanceToEntity(entityplayer);

			if (f < 4F && mc.thePlayer.canEntityBeSeen(entityplayer) && delay > new Integer(getConfigByName("delay").getValue().toString()) && !entityplayer.isInvisible() && !entityplayer.isSpectator()) {
				faceEntity(entityplayer);
				mc.playerController.attackEntity(mc.thePlayer, entityplayer);
				delay = 0;
				continue;
			}
		}
	}
	
	@Override
	public void onEnable() {
		EventManager.register(this);
	}
	
	@Override
	public void onDisable() {
		EventManager.unregister(this);
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
	
}
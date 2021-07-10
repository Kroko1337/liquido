package net.supercraftalex.liquido.modules.impl.Combat;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.lwjgl.input.Keyboard;

import com.darkmagician6.eventapi.EventManager;
import com.darkmagician6.eventapi.EventTarget;
import com.google.common.collect.Ordering;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiPlayerTabOverlay;
import net.minecraft.client.gui.PlayerComparator;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.item.EntityExpBottle;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityPainting;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityFishHook;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C07PacketPlayerDigging.Action;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.supercraftalex.liquido.Booleans;
import net.supercraftalex.liquido.ErrorManager;
import net.supercraftalex.liquido.Liquido;
import net.supercraftalex.liquido.events.EventSentPacket;
import net.supercraftalex.liquido.events.EventUpdate;
import net.supercraftalex.liquido.modules.Category;
import net.supercraftalex.liquido.modules.Config;
import net.supercraftalex.liquido.modules.Module;
import net.supercraftalex.liquido.utils.MovementUtil;

public class KillAura extends Module{
	
	public static boolean targetany = false;
	public static List<EntityPlayer> bots = new ArrayList<EntityPlayer>();
	
	public KillAura() {
		super("killaura", "KillAura", Keyboard.KEY_R, Category.COMBAT);
		
		addConfig(new Config("delay", new Integer(1)));
		getConfigByName("delay").isDouble = true;
		getConfigByName("delay").doubleMax = 10;
		getConfigByName("delay").doubleMin = 0;
		getConfigByName("delay").doubleValue = 2;
		
		addConfig(new Config("autoblock", new Boolean(false)));
		addConfig(new Config("aiming", new Boolean(true)));
		addConfig(new Config("Teams", new Boolean(false)));
		
		addConfig(new Config("range", new Integer(4)));
		getConfigByName("range").isDouble = true;
		getConfigByName("range").doubleMax = 5;
		getConfigByName("range").doubleMin = 3;
		getConfigByName("range").doubleValue = 3.4;
		
		addConfig(new Config("TrougthWalls", new Boolean(false)));
		addConfig(new Config("silent", new Boolean(false)));
		addConfig(new Config("LegitAttack", new Boolean(false)));
		addConfig(new Config("antibot", new Boolean(true)));
		addConfig(new Config("antibot+", new Boolean(true)));
		addConfig(new Config("antibot++", new Boolean(true)));
		addConfig(new Config("AOGC-ab", new Boolean(false)));
	}
	
	@EventTarget
	public void onUpdate(EventUpdate event) {
		if(!Booleans.hacking_enabled) {return;}
		killaura();
	}
	
	@EventTarget
	public void onUpdate(EventSentPacket event) {
		if(new Boolean(getConfigByName("silent").getValue().toString())) {
			if(event.getPacket() instanceof C03PacketPlayer) {
				C03PacketPlayer e = (C03PacketPlayer)event.getPacket();
				final float[] rotations = getRotationsNeeded(Liquido.INSTANCE.target);
				e.yaw = rotations[0];
				e.pitch = rotations[1];
			}
		}
	}

	int delay;

	private void killaura() {
		List list = mc.theWorld.loadedEntityList;
		delay++;

		for (int k = 0; k < list.size(); k++) {
			if (((Entity) list.get(k)).getName() == mc.thePlayer.getName()) {
				continue;
			}

			Entity entityplayer = (Entity) list.get(1);

			if (mc.thePlayer.getDistanceToEntity(entityplayer) > mc.thePlayer.getDistanceToEntity((Entity) list.get(k))) {
				entityplayer = (Entity) list.get(k);
			}

			float f = mc.thePlayer.getDistanceToEntity(entityplayer);
			
			int upperBound = (int)getConfigByName("delay").doubleValue;
			int lowerBound = (int)getConfigByName("delay").doubleValue/2;
			int number = lowerBound + (int)(Math.random() * ((upperBound - lowerBound) + 1));
			if (f <= getConfigByName("range").doubleValue && delay >= number  && entityplayer.ticksExisted > 50 && !entityplayer.isInvisible()) {
				if(!(mc.thePlayer.canEntityBeSeen(entityplayer)) && !new Boolean(getConfigByName("TrougthWalls").getValue().toString())) {continue;}
				if(entityplayer instanceof EntityItem || entityplayer instanceof EntityFishHook || entityplayer instanceof EntityArrow || entityplayer instanceof EntityPainting || entityplayer instanceof EntityExpBottle || entityplayer instanceof EntityXPOrb) {continue;}
				if(entityplayer instanceof EntityPlayer) {if(!isNotBot((EntityPlayer)entityplayer)) {continue;}}
				
				if(new Boolean(getConfigByName("autoblock").getValue().toString()) && mc.gameSettings.keyBindUseItem.pressed) {
		            this.mc.gameSettings.keyBindUseItem.pressed = false;
				}
				Boolean silent = new Boolean(getConfigByName("silent").getValue().toString());
				if(new Boolean(getConfigByName("aiming").getValue().toString())) {
					if(!silent) {
						faceEntity(entityplayer);
					}
				}
				
				Liquido.INSTANCE.target = entityplayer;
				targetany = true;
				
				Minecraft.getMinecraft().thePlayer.swingItem();
				if(entityplayer != null) {
					if(!new Boolean(getConfigByName("LegitAttack").getValue().toString())) {
						Minecraft.getMinecraft().playerController.attackEntity(Minecraft.getMinecraft().thePlayer, entityplayer);
					} else {
						Minecraft.getMinecraft().clickMouse();
					}
				}
				
				if(new Boolean(getConfigByName("autoblock").getValue().toString())) {
					this.mc.gameSettings.keyBindUseItem.pressed = true;
				}
				
				delay = 0;
			}
			else if (f > new Integer(getConfigByName("range").getValue().toString())){
				this.mc.gameSettings.keyBindUseItem.pressed = false;
			}
		}
		targetany = false;
	}
	
	@Override
	public void onEnable() {
		bots.clear();
		EventManager.register(this);
	}
	
	public boolean isTeam(EntityLivingBase e) {
		if(new Boolean(getConfigByName("Teams").getValue().toString())) {
			if(e instanceof EntityPlayer) {
				EntityPlayer pt = (EntityPlayer)e;
				if(pt.getTeam().isSameTeam(p.getTeam())) {
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean isNotBot(EntityPlayer p) {
		if(new Boolean(getConfigByName("antibot").getValue().toString())) {
			
			for(EntityPlayer pl : bots) {
				if(pl == p) {
					return false;
				}
			}
			
		}
		if(new Boolean(getConfigByName("antibot+").getValue().toString())) {
			
			if(p.getTotalArmorValue() == 0) {
				return false;
			}
			
		}
		if(new Boolean(getConfigByName("antibot++").getValue().toString())) {
			
	        if (mc.isSingleplayer()) {
	            return true;
	        }
	        final Iterator<NetworkPlayerInfo> iterator = mc.thePlayer.sendQueue.getPlayerInfoMap().iterator();
	        while (iterator.hasNext()) {
	            if (iterator.next().getGameProfile().getId().equals(p.getUniqueID())) {
	                return true;
	            }
	        }
	        return false;
			
		}
		if(new Boolean(getConfigByName("AOGC-ab").getValue().toString())) {
			if(p.posY - mc.thePlayer.posY >= 2) {
				return false;
			}
		}
		if(p.isDead || p.isInvisible()) {
			return false;
		}
		if(!p.isUser()) {
			return false;
		}
		return true;
	}
	
	@Override
	public void onDisable() {
		mc.gameSettings.keyBindUseItem.pressed = false;
		bots.clear();
		EventManager.unregister(this);
	}
	
	public synchronized void faceEntity(Entity entity) {
		final float[] rotations = getRotationsNeeded(entity);

		if (rotations != null) {
			Minecraft.getMinecraft().thePlayer.rotationYaw = rotations[0];
			Minecraft.getMinecraft().thePlayer.rotationPitch = rotations[1] + 1.0F;// 14
		}
	}
	
	int rotloop = 0;
	double rotX = 0;
	double rotZ = 0;
	double aimX = 0;
	double aimY = 0;
	double aimZ = 0;
	
	@SuppressWarnings("unused")
	public float[] getRotationsNeeded(Entity entity) {
		aimX = entity.posX;
		aimY = entity.posY;
		aimZ = entity.posZ;
		if (entity == null) {
			return null;
		}
		
		rotX = entity.posX - mc.thePlayer.posX;
		rotZ = entity.posZ - mc.thePlayer.posZ;
		
		final double diffX = rotX;
		final double diffZ = rotZ;
		double diffY;

		if (entity instanceof EntityPlayer) {
			final EntityPlayer entityLivingBase = (EntityPlayer) entity;
			diffY = entityLivingBase.posY + entityLivingBase.getEyeHeight() - (Minecraft.getMinecraft().thePlayer.posY + mc.thePlayer.getEyeHeight());
		} else if(entity instanceof EntityMob){
			final EntityMob entityLivingBase = (EntityMob) entity;
			diffY = (entity.boundingBox.minY + entity.boundingBox.maxY) / 2.0D - (Minecraft.getMinecraft().thePlayer.posY + mc.thePlayer.getEyeHeight());
		}else{
			final EntityLivingBase entityLivingBase = (EntityLivingBase) entity;
			diffY = (entity.boundingBox.minY + entity.boundingBox.maxY) / 2.0D - (Minecraft.getMinecraft().thePlayer.posY + mc.thePlayer.getEyeHeight());
		}

		final double dist = MathHelper.sqrt_double(diffX * diffX + diffZ * diffZ);
		final float yaw = (float) (Math.atan2(diffZ, diffX) * 180.0D / Math.PI) - 90.0F;
		final float pitch = (float) -(Math.atan2(diffY, dist) * 180.0D / Math.PI);
		
		return new float[] {
			mc.thePlayer.rotationYaw + MathHelper.wrapAngleTo180_float(yaw - Minecraft.getMinecraft().thePlayer.rotationYaw), Minecraft.getMinecraft().thePlayer.rotationPitch + MathHelper.wrapAngleTo180_float(pitch - Minecraft.getMinecraft().thePlayer.rotationPitch)
		};
	}
	
}
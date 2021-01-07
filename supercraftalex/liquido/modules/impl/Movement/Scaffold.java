package net.supercraftalex.liquido.modules.impl.Movement;

import java.util.ArrayDeque;
import java.util.Queue;

import org.lwjgl.input.Keyboard;

import com.darkmagician6.eventapi.EventManager;
import com.darkmagician6.eventapi.EventTarget;

import net.minecraft.block.Block;
import net.minecraft.block.BlockCarpet;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockLadder;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.BlockSkull;
import net.minecraft.block.BlockSnow;
import net.minecraft.block.material.Material;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.supercraftalex.liquido.Booleans;
import net.supercraftalex.liquido.events.EventUpdate;
import net.supercraftalex.liquido.modules.Category;
import net.supercraftalex.liquido.modules.Module;
import net.supercraftalex.liquido.utils.PacketUtils;
import net.supercraftalex.liquido.utils.TimeHelper;

public class Scaffold extends Module {
	
	private Queue<EntityPlayerSP> action = new ArrayDeque();
	public double before;
	public boolean look;
	private float lastYaw;
	private float lastPitch;
	
	public Scaffold() {
		super("scaffold", "ScaffoldWalk", Keyboard.KEY_Z, Category.MOVEMENT);
		before = 0.0D;
		look = true;
	}
	
	public static class BlockData {
		public BlockPos pos;
		public EnumFacing face;
		
		public BlockData(BlockPos pos, EnumFacing face) {
			this.pos = pos;
			this.face = face;
		}
	}
	
	private BlockData blockData = null;
	private TimeHelper time = new TimeHelper();
	float[] f;
	
	@EventTarget
	public void onUpdate(EventUpdate event) {
		if(!Booleans.hacking_enabled) {return;}
		final int delay = Booleans.scaffold_delay;
		if((mc.thePlayer.getHeldItem() != null) && (!mc.thePlayer.isSneaking()) && ((mc.thePlayer.getHeldItem().getItem() instanceof ItemBlock))) {
			
		}
	}
	
	@Override
	public void onEnable() {
		EventManager.register(this);
		blockData = null;
	}
	
	@Override
	public void onDisable() {
		EventManager.unregister(this);
		mc.gameSettings.keyBindJump.pressed = false;
	}
	
}
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
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.supercraftalex.liquido.Booleans;
import net.supercraftalex.liquido.events.EventUpdate;
import net.supercraftalex.liquido.modules.Category;
import net.supercraftalex.liquido.modules.Config;
import net.supercraftalex.liquido.modules.ConfigMode;
import net.supercraftalex.liquido.modules.Module;
import net.supercraftalex.liquido.utils.MotionUtils;
import net.supercraftalex.liquido.utils.OnlineRotation;
import net.supercraftalex.liquido.utils.PacketUtils;
import net.supercraftalex.liquido.utils.PlayerUtils;
import net.supercraftalex.liquido.utils.TimeHelper;

public class Scaffold2 extends Module {
	
	public Scaffold2() {
		super("scaffold2", "ScaffoldWalk2", Keyboard.KEY_Z, Category.MOVEMENT);
		addConfig(new Config("mode", true, new ConfigMode()));
		getConfigByName("mode").getConfigMode().addMode("Normal");
		addConfig(new Config("tower", new Boolean(false)));
		addConfig(new Config("tower-type", true, new ConfigMode()));
		getConfigByName("tower-type").getConfigMode().addMode("Timer");
		getConfigByName("tower-type").getConfigMode().addMode("Motion");
		addConfig(new Config("tower-timer", new Integer(1)));
		getConfigByName("tower-timer").isDouble = true;
		getConfigByName("tower-timer").doubleMax = 10;
		getConfigByName("tower-timer").doubleMin = 1;
		getConfigByName("tower-timer").doubleValue = 1.5;
		addConfig(new Config("jump", new Boolean(false)));
	}
	
	private EntityPlayerSP p = mc.thePlayer;
	Boolean tower = false;
    double lastX = 0;
    double lastZ = 0;
    private boolean wasSprinting = false;
	
	@EventTarget
	public void onUpdate(EventUpdate event) {
		if(mc.thePlayer.posX - lastX >= 7.0D && new Boolean(getConfigByName("jump").getValue().toString()) && p.onGround) {
			mc.thePlayer.setSneaking(true);
			mc.thePlayer.jump();
			mc.thePlayer.setSneaking(false);
			lastX = mc.thePlayer.posX;
		}
		if(mc.thePlayer.posZ - lastZ >= 7.0D && new Boolean(getConfigByName("jump").getValue().toString()) && p.onGround) {
			mc.thePlayer.setSneaking(true);
			mc.thePlayer.jump();
			mc.thePlayer.setSneaking(false);
			lastZ = mc.thePlayer.posZ;
		}
		
		int slot = 10;
		for (int i = 0; i < 9; i++) {
			try {
				if(mc.thePlayer.inventory.getStackInSlot(i).getItem() instanceof ItemBlock && mc.thePlayer.inventory.getStackInSlot(i).stackSize != 0) {
					mc.thePlayer.inventory.currentItem = i;
				}
			} catch (Exception e) {
				
			}
		}
		
		
		tower = new Boolean(getConfigByName("tower").getValue().toString());
		BlockPos pb = null;
		pb = new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ);
		if(mc.gameSettings.keyBindJump.pressed && getDistanceToGround() <= 1 &&
				!mc.gameSettings.keyBindForward.pressed &&
				!mc.gameSettings.keyBindBack.pressed &&
				!mc.gameSettings.keyBindRight.pressed &&
				!mc.gameSettings.keyBindLeft.pressed &&
				tower
				) {
			if(getConfigByName("tower-type").getConfigMode().getValue().equalsIgnoreCase("timer"))
				mc.timer.timerSpeed = (float) getConfigByName("tower-timer").doubleValue;
			if(getConfigByName("tower-type").getConfigMode().getValue().equalsIgnoreCase("motion"))
				mc.thePlayer.motionY = 0.255;
		}
		else {
			mc.timer.timerSpeed = 1;
		}
		if(valid(pb.add(0, -2, 0)))
			place(pb.add(0, -1, 0), EnumFacing.UP);
		else if(valid(pb.add(-1, -1, 0)))
			place(pb.add(0, -1, 0), EnumFacing.EAST);
		else if(valid(pb.add(1, -1, 0)))
			place(pb.add(0, -1, 0), EnumFacing.WEST);
		else if(valid(pb.add(0, -1, -1)))
			place(pb.add(0, -1, 0), EnumFacing.SOUTH);
		else if(valid(pb.add(0, -1, 1)))
			place(pb.add(0, -1, 0), EnumFacing.NORTH);
		else if(valid(pb.add(1, -1, 1))) {
			if(valid(pb.add(0, -1, 1)))
				place(pb.add(0, -1, 1), EnumFacing.NORTH);;
		}else if(valid(pb.add(-1, -1, 1))) {
			if(valid(pb.add(-1, -1, 0)))
				place(pb.add(0, -1, 1), EnumFacing.WEST);
		}else if(valid(pb.add(-1, -1, -1))) {
			if(valid(pb.add(0, -1, -1)))
				place(pb.add(0, -1, 1), EnumFacing.SOUTH);
		}else if(valid(pb.add(1, -1, -1))) {
			if(valid(pb.add(1, -1, 0)))
				place(pb.add(1, -1, 0), EnumFacing.EAST);
		}
	}
	
	@Override
	public void onEnable() {
		EventManager.register(this);
		lastX = 0;
	    lastZ = 0;
	    wasSprinting = false;
	}
	
	@Override
	public void onDisable() {
		EventManager.unregister(this);
		mc.timer.timerSpeed = 1;
	}
	
	int loop = 0;
	
    public double getDistanceToGround() {
        double d = 0.0D;
        for (int i = 0; i < 256; i++) {
            BlockPos localBlockPos = new BlockPos(this.mc.thePlayer.posX, this.mc.thePlayer.posY - i, this.mc.thePlayer.posZ);
            if ((this.mc.theWorld.getBlockState(localBlockPos).getBlock() != Blocks.air) && (this.mc.theWorld.getBlockState(localBlockPos).getBlock() != Blocks.grass) && (this.mc.theWorld.getBlockState(localBlockPos).getBlock() != Blocks.tallgrass) && (this.mc.theWorld.getBlockState(localBlockPos).getBlock() != Blocks.red_flower) && (this.mc.theWorld.getBlockState(localBlockPos).getBlock() != Blocks.yellow_flower)) {
                d = this.mc.thePlayer.posY - localBlockPos.getY();
                return d - 1.0D;
            }
        }
        return 256.0D;
    }
	
	void place(BlockPos pos, EnumFacing f)
	{
		if(f == EnumFacing.UP)
			pos = pos.add(0, -1, 0);
		else if(f == EnumFacing.NORTH)
			pos = pos.add(0, 0, 1);
		else if(f == EnumFacing.EAST)
			pos = pos.add(-1, 0, 0);
		else if(f == EnumFacing.SOUTH)
			pos = pos.add(0, 0, -1);
		else if(f == EnumFacing.WEST)
			pos = pos.add(1, 0, 0);
		
		
		
		if(mc.thePlayer.getHeldItem() != null && mc.thePlayer.getHeldItem().getItem() instanceof ItemBlock)
		{
			if(!mc.thePlayer.isSwingInProgress) {
				mc.thePlayer.swingItem();
			}
			mc.playerController.onPlayerRightClick(mc.thePlayer, mc.theWorld, mc.thePlayer.getHeldItem(), pos, f, new Vec3(0.5, 0.5, 0.5));
			double x = pos.getX() + 0.25 - mc.thePlayer.posX;
			double z = pos.getZ() + 0.25 - mc.thePlayer.posZ;
			double y = pos.getY() + 0.25 - mc.thePlayer.posY - p.getEyeHeight();
			double distance = MathHelper.sqrt_double(x * x + z * z);
			float yaw = (float)(Math.atan2(z, x) * 180 / Math.PI - 90);
			float pitch = (float)-(Math.atan2(y, distance) * 180 / Math.PI);
			PacketUtils.sendPacket(new C03PacketPlayer.C06PacketPlayerPosLook(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, yaw, pitch, mc.thePlayer.onGround));
		}
	}

	boolean valid(BlockPos p)
	{
		Block b =mc.theWorld.getBlockState(p).getBlock();
		return !(b instanceof BlockLiquid) && b.getMaterial() != Material.air;
	}
	
}

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
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
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

public class Scaffold extends Module {
	
    public static boolean noJump = false;
    BlockPos lastPlacePose;
    BlockPos StartPos;
    int StartY = 0;
    int walktower1 = 0;
    boolean walktower = false;
    int downstate = 0;
    double speed = 0.0D;
    int state = 0;
    float Yaw = 0.0F;
    BlockData data;
    double lastX = 0;
    double lastZ = 0;
    
	public Scaffold() {
		super("scaffold", "ScaffoldWalk", Keyboard.KEY_NONE, Category.MOVEMENT);
		addConfig(new Config("mode", true, new ConfigMode()));
		getConfigByName("mode").getConfigMode().addMode("Motion");
		getConfigByName("mode").getConfigMode().addMode("Cubecraft");
		getConfigByName("mode").getConfigMode().addMode("Slow");
		getConfigByName("mode").getConfigMode().addMode("AAC");
		addConfig(new Config("NoSwing", new Boolean(true)));
		addConfig(new Config("Legit", new Boolean(false)));
		addConfig(new Config("Sprint", new Boolean(true)));
	}
	
	@EventTarget
	public void onUpdate(EventUpdate event) {
		if(!Booleans.hacking_enabled) {return;}
		float yaw = mc.thePlayer.rotationYaw;
		
		if(mc.gameSettings.keyBindJump.pressed && 
				getDistanceToGround() <= 2 &&
				!mc.gameSettings.keyBindForward.pressed &&
				!mc.gameSettings.keyBindBack.pressed &&
				!mc.gameSettings.keyBindRight.pressed &&
				!mc.gameSettings.keyBindLeft.pressed
				) {
			mc.thePlayer.rotationPitch = 90.0F;
		} else {
			mc.thePlayer.rotationPitch = 85.0F;
		}
		
		if(mc.thePlayer.posX - lastX >= 7.0D) {
			mc.thePlayer.setSneaking(true);
			mc.thePlayer.jump();
			mc.thePlayer.setSneaking(false);
			lastX = mc.thePlayer.posX;
		}
		if(mc.thePlayer.posZ - lastZ >= 7.0D) {
			mc.thePlayer.setSneaking(true);
			mc.thePlayer.jump();
			mc.thePlayer.setSneaking(false);
			lastZ = mc.thePlayer.posZ;
		}
		//PRE
        Object localObject1 = null;
        int i = 0;
        Object localObject2;
        
        if(!mc.thePlayer.isSwingInProgress) {
            mc.thePlayer.swingItem();
        }
        
        localObject1 = this.mc.thePlayer.getItemInUse();
        i = this.mc.thePlayer.inventory.currentItem;
        
        //sprinting
        this.mc.gameSettings.keyBindSprint.pressed = new Boolean(getConfigByName("Sprint").getValue().toString());
        this.mc.thePlayer.setSprinting(new Boolean(getConfigByName("Sprint").getValue().toString()));
        
        localObject2 = this.mc.thePlayer.getPosition().add(0, 0, 0);
        Object localObject3 = this.mc.theWorld.getBlockState((BlockPos) localObject2).getBlock();
        BlockPos localBlockPos1 = new BlockPos(this.mc.thePlayer.posX, this.mc.thePlayer.posY - 1.0D, this.mc.thePlayer.posZ);
        if (this.downstate > 1) {
            localBlockPos1 = new BlockPos(this.mc.thePlayer.posX, this.mc.thePlayer.posY - 2.0D, this.mc.thePlayer.posZ);
        }
        BlockPos localBlockPos2 = new BlockPos(this.mc.thePlayer.posX, this.mc.thePlayer.posY - 1.0D, this.mc.thePlayer.posZ);
        float f = this.mc.thePlayer.rotationYaw - 180.0F;
        
        //antiair
        if (this.mc.theWorld.getBlockState(localBlockPos2.add(-1, 0, 0)).getBlock() != Blocks.air) {
            f = 90.0F;
        }
        if (this.mc.theWorld.getBlockState(localBlockPos2.add(1, 0, 0)).getBlock() != Blocks.air) {
            f = -90.0F;
        }
        if (this.mc.theWorld.getBlockState(localBlockPos2.add(0, 0, -1)).getBlock() != Blocks.air) {
            f = -180.0F;
        }
        if (this.mc.theWorld.getBlockState(localBlockPos2.add(0, 0, 1)).getBlock() != Blocks.air) {
            f = this.mc.thePlayer.rotationYaw - 180.0F;
        }
        if (this.mc.theWorld.getBlockState(localBlockPos2.add(0, -1, 0)).getBlock() != Blocks.air) {
            f = this.mc.thePlayer.rotationYaw - 180.0F;
        }
        
        double d3;
        double d4;
        try {
            double d1 = this.data.pos.getX() + 0.5D - this.mc.thePlayer.posX + this.data.face.getFrontOffsetX() / 2.0D;
            d3 = this.data.pos.getZ() + 0.5D - this.mc.thePlayer.posZ + this.data.face.getFrontOffsetZ() / 2.0D;
            d4 = this.mc.thePlayer.posY + this.mc.thePlayer.getEyeHeight() - this.renderY;
            if (f < 0.0F) {
                f += 360.0F;
            }
        } catch (Exception e) {}
        f = (float) (f + Math.random());
        if (Math.abs(this.Yaw - f) < 0.01D) {
            this.Yaw = f;
        } else {
            for (int k = 0; k < 40; k++) {
                this.Yaw = ((float) (this.Yaw + (f - this.Yaw) / 16.0D));
            }
        }
        if (this.walktower) {
            if ((getConfigByName("mode").getConfigMode().getValue().equalsIgnoreCase("slow")) || (getConfigByName("mode").getConfigMode().getValue().equalsIgnoreCase("cubecraft"))) {
                this.walktower1 |= 0x1;
                try {
                    if (this.walktower1 == 1) {
                        this.mc.thePlayer.motionY += 0.5D;
                    }
                    if (this.walktower1 <= 3) {
                    }
                    this.mc.thePlayer.motionY = -0.2D;
                    this.walktower1 = 0;
                } catch (Exception localException2) {
                }
            } else if (getConfigByName("mode").getConfigMode().getValue().equalsIgnoreCase("Motion")) {
                if ((isOnGround(0.76D)) && (!isOnGround(0.75D)) && (this.mc.thePlayer.motionY > 0.23D) && (this.mc.thePlayer.motionY < 0.25D)) {
                    this.mc.thePlayer.motionY = (Math.round(this.mc.thePlayer.posY) - this.mc.thePlayer.posY);
                }
                if (isOnGround(1.0E-4D)) {
                    this.mc.thePlayer.motionY = 0.42D;
                    this.mc.thePlayer.motionX *= 0.9D;
                    this.mc.thePlayer.motionZ *= 0.9D;
                } else if ((this.mc.thePlayer.posY >= Math.round(this.mc.thePlayer.posY) - 1.0E-4D) && (this.mc.thePlayer.posY <= Math.round(this.mc.thePlayer.posY) + 1.0E-4D)) {
                    this.mc.thePlayer.motionY = 0.0D;
                }
            } else if(getConfigByName("mode").getConfigMode().getValue().equalsIgnoreCase("AAC")) {
            	p.setSneaking(true);
                if ((isOnGround(0.76D)) && (!isOnGround(0.75D)) && (this.mc.thePlayer.motionY > 0.23D) && (this.mc.thePlayer.motionY < 0.25D)) {
                    this.mc.thePlayer.motionY = (Math.round(this.mc.thePlayer.posY) - this.mc.thePlayer.posY);
                }
                if (isOnGround(1.0E-4D)) {
                    this.mc.thePlayer.motionY = 0.42D;
                    this.mc.thePlayer.motionX *= 0.9D;
                    this.mc.thePlayer.motionZ *= 0.9D;
                } else if ((this.mc.thePlayer.posY >= Math.round(this.mc.thePlayer.posY) - 1.0E-4D) && (this.mc.thePlayer.posY <= Math.round(this.mc.thePlayer.posY) + 1.0E-4D)) {
                    this.mc.thePlayer.motionY = 0.0D;
                }
                p.setSneaking(false);
            }
            this.walktower = false;
        } else {
            this.walktower1 = 0;
            if (this.mc.theWorld.getBlockState(localBlockPos2).getBlock() != Blocks.air) {
                return;
            }
            if (this.mc.theWorld.getBlockState(localBlockPos2.add(-1, 0, 0)).getBlock() != Blocks.air) {
                f = 90.0F;
            }
            if (this.mc.theWorld.getBlockState(localBlockPos2.add(1, 0, 0)).getBlock() != Blocks.air) {
                f = -90.0F;
            }
            if (this.mc.theWorld.getBlockState(localBlockPos2.add(0, 0, -1)).getBlock() != Blocks.air) {
                f = -180.0F;
            }
            if (this.mc.theWorld.getBlockState(localBlockPos2.add(0, 0, 1)).getBlock() != Blocks.air) {
                f = this.mc.thePlayer.rotationYaw - 180.0F;
            }
            if (this.mc.theWorld.getBlockState(localBlockPos2.add(0, -1, 0)).getBlock() != Blocks.air) {
                f = this.mc.thePlayer.rotationYaw - 180.0F;
            }
            try {
                double d2 = this.data.pos.getX() + 0.5D - this.mc.thePlayer.posX + this.data.face.getFrontOffsetX() / 2.0D;
                d3 = this.data.pos.getZ() + 0.5D - this.mc.thePlayer.posZ + this.data.face.getFrontOffsetZ() / 2.0D;
                d4 = this.mc.thePlayer.posY + this.mc.thePlayer.getEyeHeight() - this.renderY;
                if (f < 0.0F) {
                    f += 360.0F;
                }
                p.rotationYaw = f;
            } catch (Exception localException3) {
            }
            if (Math.abs(this.Yaw - f) < 0.01D) {
                this.Yaw = f;
            } else {
                for (int m = 0; m < 32; m++) {
                    this.Yaw = ((float) (this.Yaw + (f - this.Yaw) / 22.0D));
                }
            }
        }
        noJump = false;
        C09PacketHeldItemChange localC09PacketHeldItemChange = new C09PacketHeldItemChange(i);
        this.mc.thePlayer.sendQueue.getNetworkManager().sendPacket(localC09PacketHeldItemChange);
        BlockPos localBlockPos3 = null;
        Vec3 localVec3 = null;
        EnumFacing localEnumFacing = null;
        if (this.mc.theWorld.getBlockState(localBlockPos2).getBlock() != Blocks.air) {
            return;
        }
        if (new Boolean(getConfigByName("Legit").getValue().toString())) {
            localEnumFacing = this.mc.objectMouseOver.sideHit;
            localVec3 = this.mc.objectMouseOver.hitVec;
            localBlockPos3 = this.mc.objectMouseOver.getBlockPos();
        } else {
            this.data = getBlockData(localBlockPos1);
            if (this.downstate != 1) {
                localEnumFacing = this.data.face;
            }
            localVec3 = this.mc.objectMouseOver.hitVec;
            localBlockPos3 = this.mc.objectMouseOver.getBlockPos();
        }
        if ((this.mc.objectMouseOver.getBlockPos() == null) || (this.mc.theWorld.getBlockState(this.mc.objectMouseOver.getBlockPos()).getBlock() == Blocks.air)) {
            return;
        }
        if ((this.mc.theWorld.getBlockState(localBlockPos3).getBlock().getMaterial() != Material.air) && (this.mc.playerController.onPlayerRightClick(this.mc.thePlayer, this.mc.theWorld, (ItemStack) localObject1, localBlockPos3, localEnumFacing, localVec3))) {
            if (this.downstate > 1) {
                this.downstate = 0;
            }
            if (new Boolean(getConfigByName("NoSwing").getValue().toString())) {
                this.mc.thePlayer.sendQueue.addToSendQueue(new C0APacketAnimation());
            } else {
                this.mc.thePlayer.swingItem();
            }
        }

		//POST
        this.mc.gameSettings.keyBindSprint.pressed = new Boolean(getConfigByName("Sprint").getValue().toString());
        this.mc.thePlayer.setSprinting(new Boolean(getConfigByName("Sprint").getValue().toString()));
        
	}
	
	@Override
	public void onEnable() {
		PlayerUtils.messageWithPrefix("Please look on the block!");
		EventManager.register(this);
        this.StartY = ((int) this.mc.thePlayer.posY);
        this.lastX = mc.thePlayer.posX;
        this.lastZ = mc.thePlayer.posZ;
        this.StartPos = this.mc.thePlayer.getPosition();
        this.mc.gameSettings.keyBindForward.isKeyDown();
	}
	
	@Override
	public void onDisable() {
		EventManager.unregister(this);
        this.mc.timer.timerSpeed = 1.0F;
        C09PacketHeldItemChange localC09PacketHeldItemChange = new C09PacketHeldItemChange(this.mc.thePlayer.inventory.currentItem);
        this.mc.thePlayer.sendQueue.getNetworkManager().sendPacket(localC09PacketHeldItemChange);
	}
	
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

    public boolean isOnGround(double paramDouble) {
        return !this.mc.theWorld.getCollidingBoundingBoxes(this.mc.thePlayer, this.mc.thePlayer.getEntityBoundingBox().offset(0.0D, -paramDouble, 0.0D)).isEmpty();
    }

    public boolean isAirBlock(Block paramBlock) {
        if (paramBlock.getMaterial().isReplaceable()) {
            return (!(paramBlock instanceof BlockSnow)) || (paramBlock.getBlockBoundsMaxY() <= 0.125D);
        }
        return false;
    }
    
    public void setMotion(double paramDouble) {
        double d1 = this.mc.thePlayer.movementInput.moveForward;
        double d2 = this.mc.thePlayer.movementInput.moveStrafe;
        float f = this.mc.thePlayer.rotationYaw;
        if ((d1 == 0.0D) && (d2 == 0.0D)) {
            this.mc.thePlayer.motionX = 0.0D;
            this.mc.thePlayer.motionZ = 0.0D;
        } else {
            if (d1 != 0.0D) {
                if (d2 > 0.0D) {
                    f += (d1 > 0.0D ? -45 : 45);
                } else if (d2 < 0.0D) {
                    f += (d1 > 0.0D ? 45 : -45);
                }
                d2 = 0.0D;
                if (d1 > 0.0D) {
                    d1 = 1.0D;
                } else if (d1 < 0.0D) {
                    d1 = -1.0D;
                }
            }
            this.mc.thePlayer.motionX = (d1 * paramDouble * Math.cos(Math.toRadians(f + 90.0F)) + d2 * paramDouble * Math.sin(Math.toRadians(f + 90.0F)));
            this.mc.thePlayer.motionZ = (d1 * paramDouble * Math.sin(Math.toRadians(f + 90.0F)) - d2 * paramDouble * Math.cos(Math.toRadians(f + 90.0F)));
        }
    }
    
    private BlockData getBlockData(BlockPos paramBlockPos) {
        if (isPosSolid(paramBlockPos.add(0, -1, 0))) {
            return new BlockData(paramBlockPos.add(0, -1, 0), EnumFacing.UP);
        }
        if (isPosSolid(paramBlockPos.add(-1, 0, 0))) {
            return new BlockData(paramBlockPos.add(-1, 0, 0), EnumFacing.EAST);
        }
        if (isPosSolid(paramBlockPos.add(1, 0, 0))) {
            return new BlockData(paramBlockPos.add(1, 0, 0), EnumFacing.WEST);
        }
        if (isPosSolid(paramBlockPos.add(0, 0, 1))) {
            return new BlockData(paramBlockPos.add(0, 0, 1), EnumFacing.NORTH);
        }
        if (isPosSolid(paramBlockPos.add(0, 0, -1))) {
            return new BlockData(paramBlockPos.add(0, 0, -1), EnumFacing.SOUTH);
        }
        BlockPos localBlockPos1 = paramBlockPos.add(-1, 0, 0);
        if (isPosSolid(localBlockPos1.add(0, -1, 0))) {
            return new BlockData(localBlockPos1.add(0, -1, 0), EnumFacing.UP);
        }
        if (isPosSolid(localBlockPos1.add(-1, 0, 0))) {
            return new BlockData(localBlockPos1.add(-1, 0, 0), EnumFacing.EAST);
        }
        if (isPosSolid(localBlockPos1.add(1, 0, 0))) {
            return new BlockData(localBlockPos1.add(1, 0, 0), EnumFacing.WEST);
        }
        if (isPosSolid(localBlockPos1.add(0, 0, 1))) {
            return new BlockData(localBlockPos1.add(0, 0, 1), EnumFacing.NORTH);
        }
        if (isPosSolid(localBlockPos1.add(0, 0, -1))) {
            return new BlockData(localBlockPos1.add(0, 0, -1), EnumFacing.SOUTH);
        }
        BlockPos localBlockPos2 = paramBlockPos.add(1, 0, 0);
        if (isPosSolid(localBlockPos2.add(0, -1, 0))) {
            return new BlockData(localBlockPos2.add(0, -1, 0), EnumFacing.UP);
        }
        if (isPosSolid(localBlockPos2.add(-1, 0, 0))) {
            return new BlockData(localBlockPos2.add(-1, 0, 0), EnumFacing.EAST);
        }
        if (isPosSolid(localBlockPos2.add(1, 0, 0))) {
            return new BlockData(localBlockPos2.add(1, 0, 0), EnumFacing.WEST);
        }
        if (isPosSolid(localBlockPos2.add(0, 0, 1))) {
            return new BlockData(localBlockPos2.add(0, 0, 1), EnumFacing.NORTH);
        }
        if (isPosSolid(localBlockPos2.add(0, 0, -1))) {
            return new BlockData(localBlockPos2.add(0, 0, -1), EnumFacing.SOUTH);
        }
        BlockPos localBlockPos3 = paramBlockPos.add(0, 0, 1);
        if (isPosSolid(localBlockPos3.add(0, -1, 0))) {
            return new BlockData(localBlockPos3.add(0, -1, 0), EnumFacing.UP);
        }
        if (isPosSolid(localBlockPos3.add(-1, 0, 0))) {
            return new BlockData(localBlockPos3.add(-1, 0, 0), EnumFacing.EAST);
        }
        if (isPosSolid(localBlockPos3.add(1, 0, 0))) {
            return new BlockData(localBlockPos3.add(1, 0, 0), EnumFacing.WEST);
        }
        if (isPosSolid(localBlockPos3.add(0, 0, 1))) {
            return new BlockData(localBlockPos3.add(0, 0, 1), EnumFacing.NORTH);
        }
        if (isPosSolid(localBlockPos3.add(0, 0, -1))) {
            return new BlockData(localBlockPos3.add(0, 0, -1), EnumFacing.SOUTH);
        }
        BlockPos localBlockPos4 = paramBlockPos.add(0, 0, -1);
        if (isPosSolid(localBlockPos4.add(0, -1, 0))) {
            return new BlockData(localBlockPos4.add(0, -1, 0), EnumFacing.UP);
        }
        if (isPosSolid(localBlockPos4.add(-1, 0, 0))) {
            return new BlockData(localBlockPos4.add(-1, 0, 0), EnumFacing.EAST);
        }
        if (isPosSolid(localBlockPos4.add(1, 0, 0))) {
            return new BlockData(localBlockPos4.add(1, 0, 0), EnumFacing.WEST);
        }
        if (isPosSolid(localBlockPos4.add(0, 0, 1))) {
            return new BlockData(localBlockPos4.add(0, 0, 1), EnumFacing.NORTH);
        }
        if (isPosSolid(localBlockPos4.add(0, 0, -1))) {
            return new BlockData(localBlockPos4.add(0, 0, -1), EnumFacing.SOUTH);
        }
        BlockPos localBlockPos5 = paramBlockPos.add(-2, 0, 0);
        if (isPosSolid(localBlockPos1.add(0, -1, 0))) {
            return new BlockData(localBlockPos1.add(0, -1, 0), EnumFacing.UP);
        }
        if (isPosSolid(localBlockPos1.add(-1, 0, 0))) {
            return new BlockData(localBlockPos1.add(-1, 0, 0), EnumFacing.EAST);
        }
        if (isPosSolid(localBlockPos1.add(1, 0, 0))) {
            return new BlockData(localBlockPos1.add(1, 0, 0), EnumFacing.WEST);
        }
        if (isPosSolid(localBlockPos1.add(0, 0, 1))) {
            return new BlockData(localBlockPos1.add(0, 0, 1), EnumFacing.NORTH);
        }
        if (isPosSolid(localBlockPos1.add(0, 0, -1))) {
            return new BlockData(localBlockPos1.add(0, 0, -1), EnumFacing.SOUTH);
        }
        BlockPos localBlockPos6 = paramBlockPos.add(2, 0, 0);
        if (isPosSolid(localBlockPos2.add(0, -1, 0))) {
            return new BlockData(localBlockPos2.add(0, -1, 0), EnumFacing.UP);
        }
        if (isPosSolid(localBlockPos2.add(-1, 0, 0))) {
            return new BlockData(localBlockPos2.add(-1, 0, 0), EnumFacing.EAST);
        }
        if (isPosSolid(localBlockPos2.add(1, 0, 0))) {
            return new BlockData(localBlockPos2.add(1, 0, 0), EnumFacing.WEST);
        }
        if (isPosSolid(localBlockPos2.add(0, 0, 1))) {
            return new BlockData(localBlockPos2.add(0, 0, 1), EnumFacing.NORTH);
        }
        if (isPosSolid(localBlockPos2.add(0, 0, -1))) {
            return new BlockData(localBlockPos2.add(0, 0, -1), EnumFacing.SOUTH);
        }
        BlockPos localBlockPos7 = paramBlockPos.add(0, 0, 2);
        if (isPosSolid(localBlockPos3.add(0, -1, 0))) {
            return new BlockData(localBlockPos3.add(0, -1, 0), EnumFacing.UP);
        }
        if (isPosSolid(localBlockPos3.add(-1, 0, 0))) {
            return new BlockData(localBlockPos3.add(-1, 0, 0), EnumFacing.EAST);
        }
        if (isPosSolid(localBlockPos3.add(1, 0, 0))) {
            return new BlockData(localBlockPos3.add(1, 0, 0), EnumFacing.WEST);
        }
        if (isPosSolid(localBlockPos3.add(0, 0, 1))) {
            return new BlockData(localBlockPos3.add(0, 0, 1), EnumFacing.NORTH);
        }
        if (isPosSolid(localBlockPos3.add(0, 0, -1))) {
            return new BlockData(localBlockPos3.add(0, 0, -1), EnumFacing.SOUTH);
        }
        BlockPos localBlockPos8 = paramBlockPos.add(0, 0, -2);
        if (isPosSolid(localBlockPos4.add(0, -1, 0))) {
            return new BlockData(localBlockPos4.add(0, -1, 0), EnumFacing.UP);
        }
        if (isPosSolid(localBlockPos4.add(-1, 0, 0))) {
            return new BlockData(localBlockPos4.add(-1, 0, 0), EnumFacing.EAST);
        }
        if (isPosSolid(localBlockPos4.add(1, 0, 0))) {
            return new BlockData(localBlockPos4.add(1, 0, 0), EnumFacing.WEST);
        }
        if (isPosSolid(localBlockPos4.add(0, 0, 1))) {
            return new BlockData(localBlockPos4.add(0, 0, 1), EnumFacing.NORTH);
        }
        if (isPosSolid(localBlockPos4.add(0, 0, -1))) {
            return new BlockData(localBlockPos4.add(0, 0, -1), EnumFacing.SOUTH);
        }
        BlockPos localBlockPos9 = paramBlockPos.add(0, -1, 0);
        if (isPosSolid(localBlockPos9.add(0, -1, 0))) {
            return new BlockData(localBlockPos9.add(0, -1, 0), EnumFacing.UP);
        }
        if (isPosSolid(localBlockPos9.add(-1, 0, 0))) {
            return new BlockData(localBlockPos9.add(-1, 0, 0), EnumFacing.EAST);
        }
        if (isPosSolid(localBlockPos9.add(1, 0, 0))) {
            return new BlockData(localBlockPos9.add(1, 0, 0), EnumFacing.WEST);
        }
        if (isPosSolid(localBlockPos9.add(0, 0, 1))) {
            return new BlockData(localBlockPos9.add(0, 0, 1), EnumFacing.NORTH);
        }
        if (isPosSolid(localBlockPos9.add(0, 0, -1))) {
            return new BlockData(localBlockPos9.add(0, 0, -1), EnumFacing.SOUTH);
        }
        BlockPos localBlockPos10 = paramBlockPos.add(0, -2, 0);
        if (isPosSolid(localBlockPos10.add(0, -1, 0))) {
            return new BlockData(localBlockPos10.add(0, -1, 0), EnumFacing.UP);
        }
        if (isPosSolid(localBlockPos10.add(-1, 0, 0))) {
            return new BlockData(localBlockPos10.add(-1, 0, 0), EnumFacing.EAST);
        }
        if (isPosSolid(localBlockPos10.add(1, 0, 0))) {
            return new BlockData(localBlockPos10.add(1, 0, 0), EnumFacing.WEST);
        }
        if (isPosSolid(localBlockPos10.add(0, 0, 1))) {
            return new BlockData(localBlockPos10.add(0, 0, 1), EnumFacing.NORTH);
        }
        if (isPosSolid(localBlockPos10.add(0, 0, -1))) {
            return new BlockData(localBlockPos10.add(0, 0, -1), EnumFacing.SOUTH);
        }
        BlockPos localBlockPos11 = localBlockPos9.add(1, 0, 0);
        if (isPosSolid(localBlockPos11.add(0, -1, 0))) {
            return new BlockData(localBlockPos11.add(0, -1, 0), EnumFacing.UP);
        }
        if (isPosSolid(localBlockPos11.add(-1, 0, 0))) {
            return new BlockData(localBlockPos11.add(-1, 0, 0), EnumFacing.EAST);
        }
        if (isPosSolid(localBlockPos11.add(1, 0, 0))) {
            return new BlockData(localBlockPos11.add(1, 0, 0), EnumFacing.WEST);
        }
        if (isPosSolid(localBlockPos11.add(0, 0, 1))) {
            return new BlockData(localBlockPos11.add(0, 0, 1), EnumFacing.NORTH);
        }
        if (isPosSolid(localBlockPos11.add(0, 0, -1))) {
            return new BlockData(localBlockPos11.add(0, 0, -1), EnumFacing.SOUTH);
        }
        BlockPos localBlockPos12 = localBlockPos9.add(-1, 0, 0);
        if (isPosSolid(localBlockPos12.add(0, -1, 0))) {
            return new BlockData(localBlockPos12.add(0, -1, 0), EnumFacing.UP);
        }
        if (isPosSolid(localBlockPos12.add(-1, 0, 0))) {
            return new BlockData(localBlockPos12.add(-1, 0, 0), EnumFacing.EAST);
        }
        if (isPosSolid(localBlockPos12.add(1, 0, 0))) {
            return new BlockData(localBlockPos12.add(1, 0, 0), EnumFacing.WEST);
        }
        if (isPosSolid(localBlockPos12.add(0, 0, 1))) {
            return new BlockData(localBlockPos12.add(0, 0, 1), EnumFacing.NORTH);
        }
        if (isPosSolid(localBlockPos12.add(0, 0, -1))) {
            return new BlockData(localBlockPos12.add(0, 0, -1), EnumFacing.SOUTH);
        }
        BlockPos localBlockPos13 = localBlockPos9.add(0, 0, 1);
        if (isPosSolid(localBlockPos13.add(0, -1, 0))) {
            return new BlockData(localBlockPos13.add(0, -1, 0), EnumFacing.UP);
        }
        if (isPosSolid(localBlockPos13.add(-1, 0, 0))) {
            return new BlockData(localBlockPos13.add(-1, 0, 0), EnumFacing.EAST);
        }
        if (isPosSolid(localBlockPos13.add(1, 0, 0))) {
            return new BlockData(localBlockPos13.add(1, 0, 0), EnumFacing.WEST);
        }
        if (isPosSolid(localBlockPos13.add(0, 0, 1))) {
            return new BlockData(localBlockPos13.add(0, 0, 1), EnumFacing.NORTH);
        }
        if (isPosSolid(localBlockPos13.add(0, 0, -1))) {
            return new BlockData(localBlockPos13.add(0, 0, -1), EnumFacing.SOUTH);
        }
        BlockPos localBlockPos14 = localBlockPos9.add(0, 0, -1);
        if (isPosSolid(localBlockPos14.add(0, -1, 0))) {
            return new BlockData(localBlockPos14.add(0, -1, 0), EnumFacing.UP);
        }
        if (isPosSolid(localBlockPos14.add(-1, 0, 0))) {
            return new BlockData(localBlockPos14.add(-1, 0, 0), EnumFacing.EAST);
        }
        if (isPosSolid(localBlockPos14.add(1, 0, 0))) {
            return new BlockData(localBlockPos14.add(1, 0, 0), EnumFacing.WEST);
        }
        if (isPosSolid(localBlockPos14.add(0, 0, 1))) {
            return new BlockData(localBlockPos14.add(0, 0, 1), EnumFacing.NORTH);
        }
        if (isPosSolid(localBlockPos14.add(0, 0, -1))) {
            return new BlockData(localBlockPos14.add(0, 0, -1), EnumFacing.SOUTH);
        }
        return null;
    }
    
    private boolean isPosSolid(BlockPos paramBlockPos) {
        Block localBlock = this.mc.theWorld.getBlockState(paramBlockPos).getBlock();
        return ((localBlock.getMaterial().isSolid()) || (!localBlock.isTranslucent()) || ((localBlock instanceof BlockLadder)) || ((localBlock instanceof BlockCarpet)) || ((localBlock instanceof BlockSnow)) || ((localBlock instanceof BlockSkull))) && (!localBlock.getMaterial().isLiquid()) && (!(localBlock instanceof BlockContainer));
    }
    
	public static class BlockData {
		public BlockPos pos;
		public EnumFacing face;
		
		public BlockData(BlockPos pos, EnumFacing face) {
			this.pos = pos;
			this.face = face;
		}
	}
	
}

/*OLD
	private Queue<EntityPlayerSP> action = new ArrayDeque();
	public double before;
	public boolean look;
	private float lastYaw;
	private float lastPitch;
	
	public Scaffold() {
		super("scaffold", "ScaffoldWalk", Keyboard.KEY_Z, Category.MOVEMENT);
		before = 0.0D;
		look = true;
		addConfig(new Config("delay", new Integer(1)));
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
		final int delay = new Integer(getConfigByName("delay").getValue().toString());
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
*/
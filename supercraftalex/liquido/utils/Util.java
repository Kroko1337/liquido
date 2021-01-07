package net.supercraftalex.liquido.utils;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemSoup;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagDouble;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C07PacketPlayerDigging.Action;
import net.minecraft.network.play.client.C10PacketCreativeInventoryAction;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatAllowedCharacters;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class Util {
	
	static Minecraft mc;

	public static BufferedImage scale(BufferedImage src, int w, int h) {
		BufferedImage i = new BufferedImage(w, h, 1); // type_int_rgb
	    for (int x = 0; x < w; x++)
	        for (int y = 0; y < h; y++)
	            i.setRGB(x, y, src.getRGB(x * src.getWidth() / w, y * src.getHeight() / h));
	    return i;
	}

	public static void swapSlots(int slot1, int slot2, int windowId) {
		mc.playerController.windowClick(windowId, slot1, 0, 1, mc.thePlayer);
		mc.playerController.windowClick(windowId, slot2, 0, 1, mc.thePlayer);
	}

	/**
	 * Sends the packet to break the block instantly.
	 * @param block The block to break.
	 */
	public static void breakBlock(BlockPos block) {
		mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(Action.START_DESTROY_BLOCK, block, EnumFacing.UP));
		mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(Action.STOP_DESTROY_BLOCK, block, EnumFacing.UP));
	}

	public static void downloadFile(final String url, final String file) throws IOException {
		final BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
		final FileOutputStream out = new FileOutputStream(file);
		
		final byte[] bfr = new byte[4096];
		int count = 0;

		while ((count = in.read(bfr, 0, 4096)) != -1)
			out.write(bfr, 0, count);
		
		out.close();
		in.close();
	}

	public static void cheatArmorStand(String msg, double x, double y, double z, int slot) {
		ItemStack itm = new ItemStack(Items.armor_stand);
	    
	    NBTTagCompound base = new NBTTagCompound();
	    NBTTagCompound entityTag = new NBTTagCompound();
	    
	    entityTag.setInteger("Invisible", 1);
	    entityTag.setString("CustomName", msg);
	    entityTag.setInteger("CustomNameVisible", 1);
	    entityTag.setInteger("NoGravity", 1);
	    
	    NBTTagList position = new NBTTagList();
	    
	    position.appendTag(new NBTTagDouble(x));
	    position.appendTag(new NBTTagDouble(y));
	    position.appendTag(new NBTTagDouble(z));
	    
	    entityTag.setTag("Pos", position);
	    
	    base.setTag("EntityTag", entityTag);
	    itm.setTagCompound(base);
	    
	    itm.setStackDisplayName(msg);
	    
	    cheatItem(itm, slot);
	  }

	public static NBTTagList newEffects() {
		return new NBTTagList();
	}

	public static NBTTagList addEffect(NBTTagList effects, int effect, int amplifier, int duration) {
		NBTTagCompound eff = new NBTTagCompound();
	    eff.setInteger("Amplifier", amplifier);
	    eff.setInteger("Duration", duration);
	    eff.setInteger("Id", effect);
	    effects.appendTag(eff);
		return effects;
	}

	public static ItemStack getCustomPotion(NBTTagList effects, String name) {
		ItemStack i = new ItemStack(Items.potionitem);
		i.setItemDamage(16384);
		
		i.setTagInfo("CustomPotionEffects", effects);
		i.setStackDisplayName(name);
		
		return i;
	}

	public static void cheatItem(ItemStack itm, int slot) {
		mc.thePlayer.sendQueue.addToSendQueue(new C10PacketCreativeInventoryAction(slot, itm));
	}

	public static void sendChat(String msg) {
		mc.thePlayer.sendChatMessage(msg);
	}

	public static boolean isWater(Block b)
	{
		int id = Block.getIdFromBlock(b);
		return id == 8 || id == 9;
	}

	static void applyRotations(float[] rotations) {
		mc.thePlayer.rotationYaw = rotations[0];
		mc.thePlayer.rotationPitch = rotations[1] + 8.1f;
	}

	public static BlockPos[] getBlocksAround(EntityPlayer pl, int r, boolean mbv) {
		final long px = (long)pl.posX;
		final long py = (long)pl.posY;
		final long pz = (long)pl.posZ;
		final long ex = px + r;
		final long ey = py + r;
		final long ez = pz + r;
		final long sx = px - r;
		final long sy = py - r;
		final long sz = pz - r;
		List<BlockPos> b = new ArrayList<BlockPos>();
		for(long x = sx; x < ex; x++)
			for(long y = sy; y < ey; y++)
				for(long z = sz; z < ez; z++) {
					BlockPos pos = new BlockPos(x, y, z);
					if(!mc.theWorld.isAirBlock(pos) && (!mbv || isBlockVisible(pos)))
						b.add(pos);
				}
		return b.toArray(new BlockPos[b.size()]);
	}

	static boolean isBlockVisible(BlockPos pos) {
		World w = mc.theWorld;
		EntityPlayerSP p = mc.thePlayer;
		int i = pos.getX();
		int j = pos.getY();
		int k = pos.getZ();
		double d = p.posX;
		double e = p.posY + p.getEyeHeight();
		double f = p.posZ;
		boolean b = w.isAirBlock(new BlockPos(i - 1, j, k));
		boolean c = w.isAirBlock(new BlockPos(i + 1, j, k));
		boolean g = w.isAirBlock(new BlockPos(i, j, k - 1));
		boolean h = w.isAirBlock(new BlockPos(i, j, k + 1));
		boolean l = w.isAirBlock(new BlockPos(i, j + 1, k));
		boolean m = w.isAirBlock(new BlockPos(i, j - 1, k));
		if((l && e > j) || (m && e < j) || (b && d < i) || (c && d > i) || (g && f < k) || (h && f > k)) return true;
		else return false;
	}

	public static int firstFoodIndex(ItemStack[] inv)
	{
		for(int i = 27; i < 36; i++)
			if(inv[i].getItem() instanceof ItemFood)
				return i - 27;
		return -1;
	}

	public static int firstSoupIndex(ItemStack[] inv)
	{
		for(int i = 27; i < 36; i++)
			if(inv[i].getItem() instanceof ItemSoup)
				return i - 27;
		return -1;
	}

	public static String enc64(byte[] b)
	{
		return Base64.getEncoder().encodeToString(b);
	}

	public static byte[] dec64(String s)
	{
		return Base64.getDecoder().decode(s);
	}

	public static String chatFilter(String s) {
		StringBuilder b = new StringBuilder();
		for(char c : s.toCharArray())
			if(ChatAllowedCharacters.isAllowedCharacter(c))
				b.append(c);
		return b.toString();
	}

	public static int getKeyId(String key) {
		int keyId = Keyboard.getKeyIndex(key);
		if(keyId == Keyboard.KEY_NONE) keyId = Keyboard.getKeyIndex(key.toUpperCase());
		if(keyId == Keyboard.KEY_NONE) keyId = Keyboard.getKeyIndex(key.toLowerCase());
		return keyId;
	}
	
}
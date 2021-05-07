package net.supercraftalex.liquido.modules.impl.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import com.darkmagician6.eventapi.EventManager;
import com.darkmagician6.eventapi.EventTarget;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiPlayerTabOverlay;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagList;
import net.supercraftalex.liquido.events.EventUpdate;
import net.supercraftalex.liquido.modules.Category;
import net.supercraftalex.liquido.modules.Module;
import net.supercraftalex.liquido.utils.TimeHelper;

public class AutoArmor extends Module {

    public AutoArmor() {
        super("autoarmor", "AutoArmor", 0, Category.PLAYER);
    }
    
    @Override
    public void onEnable() {
    	EventManager.register(this);
    }
    
    @Override
    public void onDisable() {
    	EventManager.unregister(this);
    }
    
    public static boolean isSomeArmorNeeded() {
    	return true;
    }
    
    private static int getArmorSlot(int paramInt) {
    	int num = paramInt;
    	if(num == 0) {
    		return 103;
    	}
    	if(num == 0) {
    		return 102;
    	}
    	if(num == 0) {
    		return 101;
    	}
    	else {
    		return 100;
    	}
    }
    
    @EventTarget
    public void onUpdate(EventUpdate event) {
		if(mc.currentScreen instanceof GuiInventory) {
			InventoryPlayer inventory = mc.thePlayer.inventory;
			
			//WURST CODE!
			
			// store slots and values of best armor pieces
			int[] bestArmorSlots = new int[4];
			int[] bestArmorValues = new int[4];
			
			// initialize with currently equipped armor
			for(int type = 0; type < 4; type++)
			{
				bestArmorSlots[type] = -1;
				
				ItemStack stack = inventory.armorItemInSlot(type);
				if(stack == null
					|| !(stack.getItem() instanceof ItemArmor))
					continue;
				
				ItemArmor item = (ItemArmor)stack.getItem();
				bestArmorValues[type] = getArmorValue(item, stack);
			}
			
			// search inventory for better armor
			for(int slot = 0; slot < 36; slot++)
			{
				ItemStack stack = inventory.getStackInSlot(slot);
				
				if(stack == null
					|| !(stack.getItem() instanceof ItemArmor))
					continue;
				
				ItemArmor item = (ItemArmor)stack.getItem();
				int armorType = getArmorType(item);
				int armorValue = getArmorValue(item, stack);
				
				if(armorValue > bestArmorValues[armorType])
				{
					bestArmorSlots[armorType] = slot;
					bestArmorValues[armorType] = armorValue;
				}
			}
			// equip better armor in random order
			ArrayList<Integer> types = new ArrayList<>(Arrays.asList(0, 1, 2, 3));
			Collections.shuffle(types);
			for(int type : types)
			{
				// check if better armor was found
				int slot = bestArmorSlots[type];
				if(slot == -1)
					continue;
				// check if armor can be swapped
				// needs 1 free slot where it can put the old armor
				ItemStack oldArmor = inventory.armorItemInSlot(type);
				if(oldArmor != null
					&& inventory.getFirstEmptyStack() == -1)
					continue;
				
				if(slot < 9)
					slot += 36;
				// swap armor
				click(slot, getArmorSlot(type));
				
				break;
			}
		}
    }
    
	private int getArmorValue(ItemArmor item, ItemStack stack)
	{
        if (stack == null) {
            return 0;
        }
        if ((stack.getItem() instanceof ItemArmor)) {
            ItemArmor localItemArmor = (ItemArmor) stack.getItem();
            int f = localItemArmor.damageReduceAmount;
            if (stack.getEnchantmentTagList() != null) {
                f += 0.5F;
            }
            f += getEnchantmentLevel(stack, 0) * 0.75F;
            f += getEnchantmentLevel(stack, 1) * 0.35F;
            f += getEnchantmentLevel(stack, 2) * 0.35F;
            f += getEnchantmentLevel(stack, 3) * 0.35F;
            f += getEnchantmentLevel(stack, 4) * 0.35F;
            f += getEnchantmentLevel(stack, 5) * 0.35F;
            f += getEnchantmentLevel(stack, 6) * 0.35F;
            f += getEnchantmentLevel(stack, 7) * 0.35F;
            return f;
        }
        return 0;
	}

	private int getArmorType(ItemArmor i) {
		return i.armorType;
	}
	
    public static int getEnchantmentLevel(ItemStack paramItemStack, int paramInt) {
        try {
            NBTTagList localNBTTagList = paramItemStack.getEnchantmentTagList();
            for (int i = 0; i < localNBTTagList.tagCount(); i++) {
                String str = localNBTTagList.getStringTagAt(i);
                int j = Integer.parseInt(str.split(",")[1].split(":")[1].split("s")[0]);
                int k = Integer.parseInt(str.split(",")[0].split(":")[1].split("s")[0]);
                if (j == paramInt) {
                    return k;
                }
            }
        } catch (Exception localException) {
        }
        return 0;
    }
	
    private void click(int paramInt1, int paramInt2) {
    	Container localContainer = this.mc.thePlayer.openContainer;
    	System.out.println("Click: " + paramInt1 + " | " + paramInt2);
    	if(paramInt1 < 9) {
    		drop(paramInt1);
    		
        	this.mc.playerController.windowClick(localContainer.windowId, paramInt1, 0, 1, this.mc.thePlayer);
        	this.mc.playerController.windowClick(localContainer.windowId, paramInt2, 1, 4, this.mc.thePlayer);
    	} else {
    		drop(paramInt1);
    		
        	this.mc.playerController.windowClick(localContainer.windowId, paramInt1, 0, 1, this.mc.thePlayer);
        	this.mc.playerController.windowClick(localContainer.windowId, paramInt2, 1, 4, this.mc.thePlayer);
    	}
    }
    
    private void drop(int slot) {
        mc.thePlayer.inventoryContainer.slotClick(slot, 0, 4, mc.thePlayer);
        mc.playerController.windowClick(mc.thePlayer.inventoryContainer.windowId, slot, 1, 4, mc.thePlayer);
    }
    
}





package net.supercraftalex.liquido.modules.impl.Player;

import java.io.File;
import java.util.Iterator;

import org.lwjgl.input.Keyboard;

import com.darkmagician6.eventapi.EventManager;
import com.darkmagician6.eventapi.EventTarget;

import net.minecraft.block.BlockSapling;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.*;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.play.client.C0DPacketCloseWindow;
import net.minecraft.network.play.client.C16PacketClientStatus;
import net.supercraftalex.liquido.Booleans;
import net.supercraftalex.liquido.ErrorManager;
import net.supercraftalex.liquido.Liquido;
import net.supercraftalex.liquido.events.EventUpdate;
import net.supercraftalex.liquido.modules.Category;
import net.supercraftalex.liquido.modules.Config;
import net.supercraftalex.liquido.modules.Module;
import net.supercraftalex.liquido.utils.ItemSortPattern;
import net.supercraftalex.liquido.utils.TimeHelper;

public class InventorySorter extends Module {
	
    public static boolean action = false;
    public static boolean openInv = false;
    private static Container inv = null;
    private static boolean changedByPlayer = false;
    private boolean sortInv = true;
    private int startDelay = 0;
    private int minDelay = 0;
    private int maxDelay = 0;
    private int currentDelay = 0;
    private TimeHelper delayHelper = new TimeHelper();
    private TimeHelper startDelayHelper = new TimeHelper();
    private ItemSortPattern itemSort;
    private File itemSortFile;
    private boolean canclose = false;
    private boolean canopen = false;
    
    private static float helmetValency = 0.0F;
    private static float chestValency = 0.0F;
    private static float legginsValency = 0.0F;
    private static float bootsValency = 0.0F;
    private int state = 0;
    
	public InventorySorter() {
		super("invsorter", "InvSorter", Keyboard.KEY_I, Category.PLAYER);
		addConfig(new Config("NoOpenInv", new Boolean(false)));
		addConfig(new Config("SortInv", new Boolean(true)));
		addConfig(new Config("StartDelay", new Integer(100)));
		addConfig(new Config("MinDelay", new Integer(100)));
		addConfig(new Config("MaxDelay", new Integer(100)));
		
        this.itemSortFile = new File(Liquido.INSTANCE.direcionary, "InvSorter.txt");
        
        loadItemSort();
	}
	
	@EventTarget
	public void onUpdate(EventUpdate event) {
		if(!Booleans.hacking_enabled) {return;}
        Boolean NoOpenInv = false;
        Boolean settingSortInv = true;
        int settingStartDelay = 100;
        int MinDelay = 100;
        int MaxDelay = 100;
		try {
	        NoOpenInv = new Boolean(getConfigByName("NoOpenInv").toString());
	        settingSortInv = new Boolean(getConfigByName("SortInv").toString());
	        settingStartDelay = new Integer(getConfigByName("StartDelay").toString());
	        MinDelay = new Integer(getConfigByName("MinDelay").toString());
	        MaxDelay = new Integer(getConfigByName("MaxDelay").toString());
		} catch (Exception e) {
			ErrorManager.addException(e);
		}
        
        if (this.mc.thePlayer.ticksExisted < 13) {
            this.canopen = true;
        }
        if (this.itemSort == null) {
            loadItemSort();
        }
        if ((this.mc.currentScreen == null) && (!NoOpenInv)) {
            this.startDelayHelper.reset();
        }
        if (!this.startDelayHelper.hasReached(this.startDelay)) {
            return;
        }
        if (this.delayHelper.hasReached(this.currentDelay)) {
            this.delayHelper.reset();
            this.currentDelay = ((int) (this.minDelay + (this.maxDelay - this.minDelay) * Math.random()));
        } else {
            return;
        }
        if (this.mc.currentScreen instanceof GuiInventory) {
        	inv = this.mc.thePlayer.openContainer;
            openInv = true;
            
            for (int i = 0; i < inv.inventoryItemStacks.size(); i++) {
                Slot localSlot = inv.getSlot(i);
                if ((localSlot != null) && (localSlot.getHasStack())) {
                    ItemStack localItemStack = localSlot.getStack();
                    if (shouldDrop(localItemStack, localSlot)) {
                    	if(!isArmorSlot(i)) {
                            windowClick(inv.windowId, i, 1, 4);
                    	}
                        return;
                    }
                }
            }
            if(this.sortInv) {
                sortInv();
            } else {
                openInv = false;
            }
        }
	}
	
	@Override
	public void onEnable() {
        this.canopen = true;
        this.delayHelper.reset();
        this.startDelayHelper.reset();
        action = false;
        changedByPlayer = false;
        openInv = false;
		EventManager.register(this);
        helmetValency = 0.0F;
        chestValency = 0.0F;
        legginsValency = 0.0F;
        bootsValency = 0.0F;
        this.state = 0;
	}
	
	@Override
	public void onDisable() {
		EventManager.unregister(this);
	}
	
    public static boolean shouldDrop(ItemStack paramItemStack, Slot paramSlot) {
        if (inv == null) {
            return false;
        }
        Item localItem = paramItemStack.getItem();
        if((localItem instanceof ItemArmor)) {
        	if(isArmorNeeded(paramItemStack)) {
        		return false;
        	}
        	else {
            	return true;
        	}
        }
        if ((localItem instanceof ItemPotion)) {
            return false;
        }
        if ((localItem instanceof ItemBed)) {
            return false;
        }
        if (localItem.getUnlocalizedName().equalsIgnoreCase("item.compass")) {
            return false;
        }
        if((localItem.getUnlocalizedName().equalsIgnoreCase("item.rotten_flesh"))) {
        	return true;
        }
        if((localItem.getUnlocalizedName().equalsIgnoreCase("item.poison_potato"))) {
        	return true;
        }
        if((localItem.getUnlocalizedName().equalsIgnoreCase("item.sapling"))) {
        	return true;
        }
        return !isItemNeeded(paramItemStack, paramSlot);
    }
    
    private void click(int paramInt1, int paramInt2) {
        Container localContainer = this.mc.thePlayer.openContainer;
        if (this.state == 0) {
            this.mc.playerController.windowClick(localContainer.windowId, paramInt1, 0, 4, this.mc.thePlayer);
            this.state = 1;
        } else if (this.state == 1) {
            this.mc.playerController.windowClick(localContainer.windowId, paramInt2, 0, 1, this.mc.thePlayer);
            this.state = 0;
        }
    }
    
    private static boolean isItemNeeded(ItemStack paramItemStack, Slot paramSlot) {
        if (paramItemStack == null) {
            return true;
        }
        Item localItem = paramItemStack.getItem();
        if ((((localItem instanceof Item)) && (Item.getIdFromItem(localItem) == 339)) || ((localItem instanceof ItemRedstone)) || ((localItem instanceof ItemReed)) || ((localItem instanceof ItemBed))) {
            return false;
        }
        if (((localItem instanceof ItemSword)) && (getSwordValency(paramItemStack) < getBestSword())) {
            return false;
        }
        if (((localItem instanceof ItemSword)) && (getSwordValency(paramItemStack) >= getBestSword())) {
            return true;
        }
        if ((isItemDouble(localItem, paramSlot)) && (Item.getIdFromItem(localItem) != 438)) {
            return false;
        }
        if ((localItem instanceof ItemSword)) {
            return true;
        }
        if ((localItem instanceof ItemBlock)) {
            return (Item.getIdFromItem(localItem) != 130) && (Item.getIdFromItem(localItem) != 54) && (Item.getIdFromItem(localItem) != 146);
        }
        if ((localItem instanceof ItemEnderPearl)) {
            return true;
        }
        if ((localItem instanceof ItemPotion)) {
            return true;
        }
        if ((localItem instanceof ItemPickaxe)) {
            return true;
        }
        if ((localItem instanceof ItemAxe)) {
            return true;
        }
        if ((localItem instanceof ItemEgg)) {
            return true;
        }
        if ((localItem instanceof ItemSnowball)) {
            return true;
        }
        if ((localItem instanceof ItemBow)) {
            return true;
        }
        if (localItem == Items.arrow) {
            return true;
        }
        if ((localItem instanceof ItemSpade)) {
            return true;
        }
        if ((localItem instanceof ItemAppleGold)) {
            return true;
        }
        if ((localItem instanceof ItemFood)) {
            return true;
        }
        if ((localItem instanceof ItemArmor)) {
            return true;
        }
        return (localItem instanceof ItemBucket);
    }

    private static ItemStack getBestItemOfSort(Item paramItem) {
        ItemStack localItemStack = null;
        float f1 = 0.0F;
        for (int i = 0; i < inv.inventoryItemStacks.size(); i++) {
            Slot localSlot = inv.getSlot(i);
            if ((localSlot != null) && (localSlot.getHasStack()) && (!isArmorSlot(i)) && (paramItem.getClass().isInstance(localSlot.getStack().getItem()))) {
                ItemTool localItemTool = (ItemTool) localSlot.getStack().getItem();
                float f2 = getEnchantmentLevel(localSlot.getStack(), 32);
                if ((localItemTool.getToolMaterial() == Item.ToolMaterial.WOOD) || (localItemTool.getToolMaterial() == Item.ToolMaterial.GOLD)) {
                    f2 += 1.0F;
                } else if (localItemTool.getToolMaterial() == Item.ToolMaterial.STONE) {
                    f2 += 2.0F;
                } else if (localItemTool.getToolMaterial() == Item.ToolMaterial.IRON) {
                    f2 += 3.0F;
                } else {
                    f2 += 4.0F;
                }
                if (localSlot.getStack().getEnchantmentTagList() != null) {
                    f2 += 1.0F;
                }
                if (f2 > f1) {
                    f1 = f2;
                    localItemStack = localSlot.getStack();
                }
            }
        }
        return localItemStack;
    }

    public static void windowClick(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
        action = true;
        Minecraft.getMinecraft().playerController.windowClick(paramInt1, paramInt2, paramInt3, paramInt4, Minecraft.getMinecraft().thePlayer);
        action = false;
    }

    private static float getBestSword() {
        if (inv == null) {
            return 0.0F;
        }
        float f1 = 0.0F;
        float f2 = 0.0F;
        for (int i = 0; i < inv.inventoryItemStacks.size(); i++) {
            Slot localSlot = inv.getSlot(i);
            if ((localSlot != null) && (localSlot.getHasStack()) && (!isArmorSlot(i)) && ((localSlot.getStack().getItem() instanceof ItemSword))) {
                ItemSword localItemSword = (ItemSword) localSlot.getStack().getItem();
                float f3 = getSwordValency(localSlot.getStack());
                if (f3 > f1) {
                    f1 = f3;
                    f2 = 0.0F;
                } else if (f3 == f1) {
                    f2 = 0.1F;
                }
            }
        }
        return f1 + f2;
    }

    private static float getSwordValency(ItemStack paramItemStack) {
        if ((paramItemStack == null) || (!(paramItemStack.getItem() instanceof ItemSword))) {
            return 0.0F;
        }
        ItemSword localItemSword = (ItemSword) paramItemStack.getItem();
        NBTTagList localNBTTagList = paramItemStack.getEnchantmentTagList();
        if (localNBTTagList != null) {
            for (int i = 0; i < localNBTTagList.tagCount(); i++) {
                String str = localNBTTagList.getStringTagAt(i);
            }
        }
        float f = localItemSword.getDamageVsEntity();
        f += getEnchantmentLevel(paramItemStack, 16) * 1.25F;
        f += getEnchantmentLevel(paramItemStack, 20) * 0.75F;
        f += getEnchantmentLevel(paramItemStack, 19) * 0.5F;
        if (localNBTTagList != null) {
            f += 0.5F;
        }
        return f;
    }

    private static boolean isItemDouble(Item paramItem, Slot paramSlot) {
        if (inv == null) {
            return false;
        }
        if ((paramItem instanceof ItemTool)) {
            return paramSlot.getStack() != getBestItemOfSort(paramItem);
        }
        if (paramItem.getItemStackLimit() != 1) {
            return false;
        }
        for (int i = 0; i < inv.inventoryItemStacks.size(); i++) {
            Slot localSlot = inv.getSlot(i);
            if ((localSlot != null) && (localSlot.getHasStack()) && (!isArmorSlot(i)) && (localSlot.getStack().getItem() == paramItem) && (localSlot != paramSlot)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isChangedByPlayer() {
        return changedByPlayer;
    }

    public static void setChangedByPlayer(boolean paramBoolean) {
        if (!action) {
            changedByPlayer = paramBoolean;
        }
    }

    private static boolean isArmorSlot(int paramInt) {
        return (paramInt - 5 >= 0) && (paramInt - 5 <= 3);
    }
    
    private void sendClose() {
        this.canclose = false;
        this.canopen = true;
        this.mc.thePlayer.sendQueue.addToSendQueue(new C0DPacketCloseWindow(this.mc.thePlayer.inventoryContainer.windowId));
    }

    private void sendOpen() {
        this.canclose = true;
        this.canopen = false;
        this.mc.thePlayer.sendQueue.addToSendQueue(new C16PacketClientStatus(C16PacketClientStatus.EnumState.OPEN_INVENTORY_ACHIEVEMENT));
    }
    
    private void sortInv() {
        this.itemSort = getDefaultLoadOut();
        this.itemSort.cleanup();
        for (int i = 0; i < this.itemSort.getHotbarItems().length; i++) {
            ItemSortPattern.ItemType localItemType = this.itemSort.getItem(i);
            if (localItemType != null) {
                int j = getItemForType(localItemType);
                if ((j > 0) && (localItemType != this.itemSort.getType(inv.getSlot(i | 0x24).getStack()))) {
                    windowClick(inv.windowId, j, i, 2);
                    return;
                }
            }
        }
        openInv = false;
    }

    private int getItemForType(ItemSortPattern.ItemType paramItemType) {
        if (paramItemType == null) {
            return -1;
        }
        for (int i = 0; i < inv.inventoryItemStacks.size(); i++) {
            Slot localSlot = inv.getSlot(i);
            if ((localSlot != null) && (localSlot.getHasStack()) && (!isArmorSlot(i))) {
                ItemStack localItemStack = localSlot.getStack();
                if (this.itemSort.getType(localItemStack) == paramItemType) {
                    return i;
                }
            }
        }
        return -1;
    }

    private boolean isItemUseless(Item paramItem) {
        if ((paramItem instanceof ItemAnvilBlock)) {
            return true;
        }
        if ((paramItem instanceof ItemArmorStand)) {
            return true;
        }
        if ((paramItem instanceof ItemBed)) {
            return true;
        }
        if ((paramItem instanceof ItemBoat)) {
            return true;
        }
        if ((paramItem instanceof ItemBanner)) {
            return true;
        }
        if ((paramItem instanceof ItemBook)) {
            return true;
        }
        if ((paramItem instanceof ItemCarrotOnAStick)) {
            return true;
        }
        if ((paramItem instanceof ItemCoal)) {
            return true;
        }
        if ((paramItem instanceof ItemColored)) {
            return true;
        }
        if ((paramItem instanceof ItemDoor)) {
            return true;
        }
        if ((paramItem instanceof ItemDye)) {
            return true;
        }
        if ((paramItem instanceof ItemCloth)) {
            return true;
        }
        if ((paramItem instanceof ItemLeaves)) {
            return true;
        }
        if ((paramItem instanceof ItemGlassBottle)) {
            return true;
        }
        if ((paramItem instanceof ItemMap)) {
            return true;
        }
        if ((paramItem instanceof ItemMinecart)) {
            return true;
        }
        if ((paramItem instanceof ItemNameTag)) {
            return true;
        }
        if ((paramItem instanceof ItemSaddle)) {
            return true;
        }
        return (paramItem instanceof ItemSkull);
    }

    private ItemSortPattern getCurrentLoadOut() {
        ItemSortPattern localItemSortPattern = new ItemSortPattern();
        for (int i = 0; i < localItemSortPattern.getHotbarItems().length; i++) {
            localItemSortPattern.setItem(inv.getSlot(i | 0x24).getStack(), i);
        }
        return localItemSortPattern;
    }

    private void loadItemSort() {
            this.itemSort = new ItemSortPattern();
            this.itemSort.setItem(ItemSortPattern.ItemType.SWORD, 0);
            this.itemSort.setItem(ItemSortPattern.ItemType.BOW, 1);
            this.itemSort.setItem(ItemSortPattern.ItemType.GOLDENAPPLE, 2);
            this.itemSort.setItem(ItemSortPattern.ItemType.ENDERPEARL, 3);
            this.itemSort.setItem(ItemSortPattern.ItemType.BLOCK, 4);
            this.itemSort.setItem(ItemSortPattern.ItemType.PICKAXE, 5);
            this.itemSort.setItem(ItemSortPattern.ItemType.FOOD, 6);
    }

    private ItemSortPattern getDefaultLoadOut() {
        ItemSortPattern localItemSortPattern = new ItemSortPattern();
        this.itemSort = new ItemSortPattern();
        localItemSortPattern.setItem(ItemSortPattern.ItemType.SWORD, 0);
        localItemSortPattern.setItem(ItemSortPattern.ItemType.GOLDENAPPLE, 1);
        localItemSortPattern.setItem(ItemSortPattern.ItemType.BOW, 2);
        localItemSortPattern.setItem(ItemSortPattern.ItemType.ENDERPEARL, 3);
        localItemSortPattern.setItem(ItemSortPattern.ItemType.BLOCK, 4);
        localItemSortPattern.setItem(ItemSortPattern.ItemType.PICKAXE, 5);
        localItemSortPattern.setItem(ItemSortPattern.ItemType.FOOD, 6);
        return localItemSortPattern;
    }

    public void saveInvSetup() {
        if (this.itemSort == null) {
            return;
        }
    }

    public ItemSortPattern getItemSort() {
        return this.itemSort;
    }

    public void setItemSort(ItemSortPattern paramItemSortPattern) {
        this.itemSort = paramItemSortPattern;
    }
    
    public static boolean isArmorNeeded(ItemStack paramItemStack) {
        if ((paramItemStack == null) || (!(paramItemStack.getItem() instanceof ItemArmor))) {
            return false;
        }
        ItemArmor localItemArmor = (ItemArmor) paramItemStack.getItem();
        int i = localItemArmor.armorType;
        if (i == 0) {
            return getArmorValency(paramItemStack) > helmetValency;
        }
        if (i == 1) {
            return getArmorValency(paramItemStack) > chestValency;
        }
        if (i == 2) {
            return getArmorValency(paramItemStack) > legginsValency;
        }
        if (i == 3) {
            return getArmorValency(paramItemStack) > bootsValency;
        }
        return false;
    }
    
    public boolean isSomeArmorNeeded() {
        if (!isEnabled()) {
            return false;
        }
        int i = getBestArmorOfSort(0);
        if (i != getArmorSlot(0)) {
            return true;
        }
        int j = getBestArmorOfSort(1);
        if (j != getArmorSlot(1)) {
            return true;
        }
        int k = getBestArmorOfSort(2);
        if (k != getArmorSlot(2)) {
            return true;
        }
        int m = getBestArmorOfSort(3);
        return m != getArmorSlot(3);
    }

    private static int getArmorSlot(int paramInt) {
        return paramInt | 0x5;
    }
    
    public static int getBestArmorOfSort(int paramInt) {
        int i = getArmorSlot(paramInt);
        float f1 = 0.0F;
        Container localContainer = Minecraft.getMinecraft().thePlayer.openContainer;
        for (int j = 0; j < localContainer.inventoryItemStacks.size(); j++) {
            Slot localSlot = localContainer.getSlot(j);
            if ((localSlot != null) && (localSlot.getHasStack()) && ((localSlot.getStack().getItem() instanceof ItemArmor))) {
                ItemArmor localItemArmor = (ItemArmor) localSlot.getStack().getItem();
                if (localItemArmor.armorType == paramInt) {
                    float f2 = getEnchantmentLevel(localSlot.getStack(), 0) * 0.75F + getEnchantmentLevel(localSlot.getStack(), 1) * 0.25F + getEnchantmentLevel(localSlot.getStack(), 2) * 0.25F + getEnchantmentLevel(localSlot.getStack(), 4) * 0.25F + getEnchantmentLevel(localSlot.getStack(), 7) * 0.5F;
                    f2 += localItemArmor.damageReduceAmount;
                    if (localSlot.getStack().getEnchantmentTagList() != null) {
                        f2 += 0.5F;
                    }
                    if (f2 > f1) {
                        f1 = f2;
                        i = j;
                    }
                }
            }
        }
        return i;
    }

    private static float getArmorValency(ItemStack paramItemStack) {
        if (paramItemStack == null) {
            return 0.0F;
        }
        if ((paramItemStack.getItem() instanceof ItemArmor)) {
            ItemArmor localItemArmor = (ItemArmor) paramItemStack.getItem();
            float f = localItemArmor.damageReduceAmount;
            if (paramItemStack.getEnchantmentTagList() != null) {
                f += 0.5F;
            }
            f += getEnchantmentLevel(paramItemStack, 0) * 0.75F;
            f += getEnchantmentLevel(paramItemStack, 1) * 0.35F;
            f += getEnchantmentLevel(paramItemStack, 2) * 0.35F;
            f += getEnchantmentLevel(paramItemStack, 3) * 0.35F;
            f += getEnchantmentLevel(paramItemStack, 4) * 0.35F;
            f += getEnchantmentLevel(paramItemStack, 5) * 0.35F;
            f += getEnchantmentLevel(paramItemStack, 6) * 0.35F;
            f += getEnchantmentLevel(paramItemStack, 7) * 0.35F;
            return f;
        }
        return 0.0F;
    }
    
    public static float getEnchantmentLevel(ItemStack paramItemStack, int paramInt) {
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
        return 0.0F;
    }
    
}

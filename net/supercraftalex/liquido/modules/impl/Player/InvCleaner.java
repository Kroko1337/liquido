package net.supercraftalex.liquido.modules.impl.Player;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.*;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.play.client.C0DPacketCloseWindow;
import net.minecraft.network.play.client.C16PacketClientStatus;
import net.minecraft.network.play.client.C16PacketClientStatus.EnumState;
import net.supercraftalex.liquido.events.EventUpdate;
import net.supercraftalex.liquido.modules.Category;
import net.supercraftalex.liquido.modules.Module;
import net.supercraftalex.liquido.utils.ItemSortPattern;
import net.supercraftalex.liquido.utils.TimeHelper;

import java.io.File;
import java.util.List;

import org.lwjgl.input.Keyboard;

import com.darkmagician6.eventapi.EventManager;
import com.darkmagician6.eventapi.EventTarget;

public class InvCleaner extends Module {

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

    public InvCleaner() {
        super("invcleaner", "InvCleaner", Keyboard.KEY_U, Category.PLAYER);
    }

    public static boolean shouldDrop(ItemStack paramItemStack, Slot paramSlot) {
        if (inv == null) {
            return false;
        }
        Item localItem = paramItemStack.getItem();
        if ((localItem instanceof ItemPotion)) {
            return false;
        }
        if ((localItem instanceof ItemBed)) {
            return false;
        }
        if ((localItem instanceof ItemEnchantedBook)) {
            return false;
        }
        if ((localItem instanceof ItemBook)) {
            return false;
        }
        if ((localItem instanceof ItemArmor)) {
            return false;
        }
        if (localItem.getUnlocalizedName().equalsIgnoreCase("item.compass")) {
            return false;
        }
        return !isItemNeeded(paramItemStack, paramSlot);
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

    private static float getEnchantmentLevel(ItemStack paramItemStack, int paramInt) {
        try {
            NBTTagList localNBTTagList = paramItemStack.getEnchantmentTagList();
            if (localNBTTagList == null) {
                return 0.0F;
            }
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

    public void setup() {
        this.openInv = false;
        this.sortInv = true;
        this.startDelay = 100;
        this.minDelay = 100;
        this.maxDelay = 200;
        loadItemSort();
    }

    @Override
    public void onEnable() {
    	EventManager.register(this);
        this.canopen = true;
        this.delayHelper.reset();
        this.startDelayHelper.reset();
        action = false;
        changedByPlayer = false;
        openInv = false;
    }
    @Override
    public void onDisable() {
    	EventManager.unregister(this);
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
    
    @EventTarget
    public void onUpdate(EventUpdate event) {
        this.canopen = true;
        if (this.itemSort == null) {
            loadItemSort();
        }
        if ((this.mc.currentScreen == null) && (!this.openInv)) {
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
        if (((this.mc.currentScreen instanceof GuiInventory)) || (this.mc.currentScreen == null) || ((this.mc.currentScreen instanceof GuiInventory))) {
            inv = this.mc.thePlayer.openContainer;
            openInv = true;
            for (int i = 0; i < inv.inventoryItemStacks.size(); i++) {
                Slot localSlot = inv.getSlot(i);
                if ((localSlot != null) && (localSlot.getHasStack()) && (!isArmorSlot(i)) && (!AutoArmor.isSomeArmorNeeded())) {
                    if (((this.openInv) && (this.mc.currentScreen == null)) || (((this.mc.currentScreen instanceof GuiInventory)) && (openInv))) {
                    }
                    ItemStack localItemStack = localSlot.getStack();
                    if (shouldDrop(localItemStack, localSlot)) {
                        windowClick(inv.windowId, i, 1, 4);
                        return;
                    }
                }
            }
            if (this.sortInv) {
                sortInv();
            } else {
                openInv = false;
            }
        }
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
        if ((paramItem instanceof ItemEnchantedBook)) {
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
        this.itemSort.setItem(ItemSortPattern.ItemType.GOLDENAPPLE, 1);
        this.itemSort.setItem(ItemSortPattern.ItemType.BOW, 2);
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
}





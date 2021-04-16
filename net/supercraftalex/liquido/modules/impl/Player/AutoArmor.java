package net.supercraftalex.liquido.modules.impl.Player;

import com.darkmagician6.eventapi.EventManager;
import com.darkmagician6.eventapi.EventTarget;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiInventory;
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

    private static boolean enabled = false;
    private static float helmetValency = 0.0F;
    private static float chestValency = 0.0F;
    private static float legginsValency = 0.0F;
    private static float bootsValency = 0.0F;
    private int state = 0;
    private int startDelay = 0;
    private int minDelay = 0;
    private int maxDelay = 0;
    private boolean open = false;
    private int currentDelay = 0;
    private TimeHelper delayHelper = new TimeHelper();
    private TimeHelper startDelayHelper = new TimeHelper();
	private boolean noinv = false;

    public AutoArmor() {
        super("autoarmor", "AutoArmor", 0, Category.PLAYER);
        this.startDelay = 100;
        this.minDelay = 100;
        this.maxDelay = 120;
        this.noinv  = false;
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

    public static boolean isSomeArmorNeeded() {
        if (!enabled) {
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
    
    @Override
    public void onEnable() {
    	EventManager.register(this);
        helmetValency = 0.0F;
        chestValency = 0.0F;
        legginsValency = 0.0F;
        bootsValency = 0.0F;
        this.delayHelper.reset();
        this.startDelayHelper.reset();
        this.state = 0;
        enabled = true;
    }
    
    @Override
    public void onDisable() {
    	EventManager.unregister(this);
        enabled = false;
    }
    
    @EventTarget
    public void onUpdate(EventUpdate event) {
        if (this.mc.currentScreen == null) {
            this.startDelayHelper.reset();
        }
        if ((!this.startDelayHelper.hasReached(this.startDelay)) && (!this.noinv)) {
            return;
        }
        if ((!this.open) && (isSomeArmorNeeded()) && (this.noinv)) {
            this.open = true;
        }
        if ((!isSomeArmorNeeded()) && (this.open) && (this.noinv)) {
            this.open = false;
        }
        if (this.delayHelper.hasReached(this.currentDelay)) {
            this.delayHelper.reset();
            this.currentDelay = ((int) (this.minDelay + (this.maxDelay - this.minDelay) * Math.random()));
        } else {
            return;
        }
        if (((this.mc.currentScreen instanceof GuiInventory)) || ((this.noinv) && (isSomeArmorNeeded()) && (this.mc.currentScreen == null))) {
            Container localContainer = Minecraft.getMinecraft().thePlayer.openContainer;
            int i = getBestArmorOfSort(0);
            if (i != getArmorSlot(0)) {
                click(getArmorSlot(0), i);
                helmetValency = getArmorValency(localContainer.getSlot(getBestArmorOfSort(0)).getStack());
                return;
            }
            int j = getBestArmorOfSort(1);
            if (j != getArmorSlot(1)) {
                click(getArmorSlot(1), j);
                chestValency = getArmorValency(localContainer.getSlot(getBestArmorOfSort(1)).getStack());
                return;
            }
            int k = getBestArmorOfSort(2);
            if (k != getArmorSlot(2)) {
                click(getArmorSlot(2), k);
                legginsValency = getArmorValency(localContainer.getSlot(getBestArmorOfSort(2)).getStack());
                return;
            }
            int m = getBestArmorOfSort(3);
            if (m != getArmorSlot(3)) {
                click(getArmorSlot(3), m);
                bootsValency = getArmorValency(localContainer.getSlot(getBestArmorOfSort(3)).getStack());
                return;
            }
        }
    }

    private void click(int paramInt1, int paramInt2) {
        Container localContainer = this.mc.thePlayer.openContainer;
        InvCleaner.action = true;
        this.mc.playerController.windowClick(localContainer.windowId, paramInt2, 0, 4, this.mc.thePlayer);
        this.mc.playerController.windowClick(localContainer.windowId, paramInt1, 1, 4, this.mc.thePlayer);
        InvCleaner.action = false;
    }

    public void onPlayerSpawn() {
        helmetValency = 0.0F;
        chestValency = 0.0F;
        legginsValency = 0.0F;
        bootsValency = 0.0F;
    }
}





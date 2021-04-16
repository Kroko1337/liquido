package net.supercraftalex.liquido.utils;

import net.minecraft.item.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class ItemSortPattern {

    private ItemType[] hotbarItems = new ItemType[9];

    public static ItemSortPattern fromArray(List<String> paramList) {
        if ((paramList == null) || (paramList.size() == 0)) {
            return null;
        }
        ItemSortPattern localItemSortPattern = new ItemSortPattern();
        for (int i = 0; i < localItemSortPattern.hotbarItems.length; i++) {
            try {
                String str = (String) paramList.get(i);
                localItemSortPattern.setItem(ItemType.setItemType(str.split(":")[1]), Integer.parseInt(str.split(":")[0]));
            } catch (Exception localException) {
                localException.printStackTrace();
            }
        }
        return localItemSortPattern;
    }

    public void setItem(ItemType paramItemType, int paramInt) {
        if ((paramInt < 0) || (paramInt > 8)) {
            return;
        }
        this.hotbarItems[paramInt] = paramItemType;
    }

    public void setItem(ItemStack paramItemStack, int paramInt) {
        if ((paramInt < 0) || (paramInt > 8)) {
            return;
        }
        setItem(getType(paramItemStack), paramInt);
    }

    public ItemType getItem(int paramInt) {
        if ((paramInt < 0) || (paramInt > 8)) {
            return null;
        }
        return this.hotbarItems[paramInt];
    }

    public void changeBy(ItemSortPattern paramItemSortPattern) {
        removeItems(paramItemSortPattern.getAllItemsAsList());
        for (int i = 0; i < paramItemSortPattern.getHotbarItems().length; i++) {
            ItemType localItemType = paramItemSortPattern.getItem(i);
            if (localItemType != null) {
                setItem(localItemType, i);
            }
        }
    }

    public void cleanup() {
        ArrayList localArrayList = new ArrayList();
        for (int i = 0; i < this.hotbarItems.length; i++) {
            ItemType localItemType = this.hotbarItems[i];
            if (localItemType != null) {
                if (localArrayList.contains(localItemType)) {
                    this.hotbarItems[i] = null;
                } else {
                    localArrayList.add(localItemType);
                }
            }
        }
    }

    public void removeItems(List<ItemType> paramList) {
        Iterator localIterator = paramList.iterator();
        while (localIterator.hasNext()) {
            ItemType localItemType = (ItemType) localIterator.next();
            removeItem(localItemType);
        }
    }

    public void removeItem(ItemType paramItemType) {
        for (int i = 0; i < this.hotbarItems.length; i++) {
            if (this.hotbarItems[i] == paramItemType) {
                this.hotbarItems[i] = null;
            }
        }
    }

    public List<ItemType> getAllItemsAsList() {
        ArrayList localArrayList = new ArrayList();
        ItemType[] arrayOfItemType;
        int j = (arrayOfItemType = this.hotbarItems).length;
        for (int i = 0; i < j; i++) {
            ItemType localItemType = arrayOfItemType[i];
            localArrayList.add(localItemType);
        }
        if (localArrayList.size() == 0) {
            return null;
        }
        return localArrayList;
    }

    public List<String> toArray() {
        ArrayList localArrayList = new ArrayList();
        for (int i = 0; i < this.hotbarItems.length; i++) {
            if (getItem(i) == null) {
                localArrayList.add(i + ":null");
            } else {
                localArrayList.add(i + ":" + getItem(i).toString());
            }
        }
        return localArrayList;
    }

    public ItemType getType(ItemStack paramItemStack) {
        if (paramItemStack == null) {
            return null;
        }
        return getType(paramItemStack.getItem());
    }

    public ItemType getType(Item paramItem) {
        if (paramItem == null) {
            return null;
        }
        if ((paramItem instanceof ItemAxe)) {
            return ItemType.AXE;
        }
        if ((paramItem instanceof ItemBow)) {
            return ItemType.BOW;
        }
        if ((paramItem instanceof ItemSpade)) {
            return ItemType.SHOVEL;
        }
        if ((paramItem instanceof ItemPickaxe)) {
            return ItemType.PICKAXE;
        }
        if ((paramItem instanceof ItemSword)) {
            return ItemType.SWORD;
        }
        if ((paramItem instanceof ItemAppleGold)) {
            return ItemType.GOLDENAPPLE;
        }
        if ((paramItem instanceof ItemFood)) {
            return ItemType.FOOD;
        }
        if ((paramItem instanceof ItemEnderPearl)) {
            return ItemType.ENDERPEARL;
        }
        if ((paramItem instanceof ItemBlock)) {
            return ItemType.BLOCK;
        }
        if (paramItem.getClass().getSuperclass() == ItemBlock.class) {
            return ItemType.BLOCK;
        }
        return ItemType.NONE;
    }

    public ItemType[] getHotbarItems() {
        return this.hotbarItems;
    }

    public void setHotbarItems(ItemType[] paramArrayOfItemType) {
        this.hotbarItems = paramArrayOfItemType;
    }

    public static enum ItemType {
        AXE("axe"), BOW("bow"), SHOVEL("shovel"), PICKAXE("pickaxe"), SWORD("sword"), BLOCK("block"), GOLDENAPPLE("goldenApple"), ENDERPEARL("enderpearl"), FOOD("food"), NONE("none");

        private static HashMap<String, ItemType> hashMap;

        static {
            hashMap = new HashMap();
            ItemType[] arrayOfItemType;
            int j = (arrayOfItemType = values()).length;
            for (int i = 0; i < j; i++) {
                ItemType localItemType = arrayOfItemType[i];
                hashMap.put(localItemType.toString(), localItemType);
            }
        }

        private String itemType;

        private ItemType(String paramString1) {
            this.itemType = paramString1;
        }

        public static ItemType setItemType(String paramString) {
            ItemType localItemType = (ItemType) hashMap.get(paramString);
            if (localItemType == null) {
                localItemType = NONE;
            }
            return localItemType;
        }

        public String toString() {
            return this.itemType;
        }
    }
}





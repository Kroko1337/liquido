package net.supercraftalex.liquido.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;

public class InventorySort {
	
	public static void sort(EntityPlayerSP p,int slot) {
		int p0;
		if(!(slot == 0)) {
			p0 = GetItemPrior.getPriority(p.inventory.getStackInSlot(slot-1));
		}
		else {
			p0 = 1;
		}
		int p1 = GetItemPrior.getPriority(p.inventory.getStackInSlot(slot));
		int p2 = GetItemPrior.getPriority(p.inventory.getStackInSlot(slot+1));
		
		if(p0 > p1) {
			
		}
		
		//next slot
		if(slot == 35) {
			return;
		} 
		else {
			sort(p,slot+1);
		}
	}

}

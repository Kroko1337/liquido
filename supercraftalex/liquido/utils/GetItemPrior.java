package net.supercraftalex.liquido.utils;

import net.minecraft.item.ItemStack;

public class GetItemPrior {
	
	public static int getPriority(ItemStack i) {
		int prio = 0;
		
		if(i.isItemEnchanted()) {
			prio = prio + 2;
		}
		if(i.isItemDamaged()) {
			prio--;
		}
		
		if(i.getItem().getMaxDamage() >= 4) {
			prio++;
		}
		else if(i.getItem().getMaxDamage() >= 6) {
			prio++;
		}
		else {
			prio = prio - 3;
		}
		
		if(!i.getItem().isDamageable()) {
			prio++;
		}
		
		return prio;
	}
	
}

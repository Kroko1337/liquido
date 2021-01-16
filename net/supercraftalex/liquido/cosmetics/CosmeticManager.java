package net.supercraftalex.liquido.cosmetics;

import java.util.ArrayList;
import java.util.List;

import net.supercraftalex.liquido.cosmetics.capes.Cape;
import net.supercraftalex.liquido.cosmetics.effects.CreeperOverlay;
import net.supercraftalex.liquido.cosmetics.effects.WitherOverlay;

public class CosmeticManager {
	
	public List<Cosmetic> cosmetics = new ArrayList<Cosmetic>();
	
	public CosmeticManager() {
		cosmetics.add(new Cape());
		cosmetics.add(new WitherOverlay());
		cosmetics.add(new CreeperOverlay());
		
		for(Cosmetic c : cosmetics) {
			c.toggle();
		}
	}
	
	private void addCosmetic(Cosmetic c) {
		cosmetics.add(c);
	}
	
	public List<Cosmetic> getCosmetics() {
		return cosmetics;
	}
	
	public Cosmetic getCosmeticByName(String name) {
		for(Cosmetic c : cosmetics) {
			if(c.getName().equalsIgnoreCase(name) || c.getDisplayName().equalsIgnoreCase(name)) {
				return c;
			}
		}
		return null;
	}
	
}

package net.supercraftalex.liquido.cosmetics;

import net.minecraft.client.renderer.entity.RenderPlayer;

public class Cosmetic {
	
	private String name;
	private String displayname;
	private boolean toggled = false;
	
	public Cosmetic(String name, String displayname) {
		this.name = name;
		this.displayname = displayname;
	}
	
	public void toggle() {
		this.toggled = !this.toggled;
	}
	
	protected boolean isToggled() {
		return this.toggled;
	}
	
	protected String getName() {
		return this.name;
	} 
	
	protected String getDisplayName() {
		return this.displayname;
	} 
	
}

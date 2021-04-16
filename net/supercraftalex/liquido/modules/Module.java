package net.supercraftalex.liquido.modules;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.IChatComponent;
import net.supercraftalex.liquido.Liquido;

public class Module {
	
	private List<Config> configs = new ArrayList<Config>();
	private int id;
    public double renderY = 8.0D;
    public double renderX;
    public double renderAlpha = 50.0D;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDisplayname() {
		return displayname;
	}

	public void setDisplayname(String displayname) {
		this.displayname = displayname;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public int getKeyBind() {
		return keyBind;
	}

	public void setKeyBind(int keyBind) {
		this.keyBind = keyBind;
	}
	
	public int getId() {
		return this.id;
	}
	
	public void setId(int n_id) {
		this.id = n_id;
	}
	
	public boolean isCategory(Category category) {
		return  this.category == category;
	}

	private String name;
	private String displayname;
	private Category category;
	public boolean toggled;
	public boolean visible;
	public boolean canSetback = false;
	private int keyBind;
	public static boolean colormode = false;
	public Minecraft mc = Minecraft.getMinecraft();
	public EntityPlayerSP p = mc.thePlayer;
	
	public Module(String name, String displayname, int keyBind,Category category) {
		
		this.name = name;
		this.displayname = displayname;
		this.category = category;
		this.keyBind = keyBind;
		this.visible =true;
		
	}
	public Module(String name, String displayname, int keyBind,Category category,Boolean cs) {
		
		this.canSetback = cs;
		this.name = name;
		this.displayname = displayname;
		this.category = category;
		this.keyBind = keyBind;
		this.visible =true;
		
	}
	
	public boolean canSetback() {
		return canSetback;
	}
	
	public boolean isEnabled() {
		return toggled;
	}
	
	public void toggle() {
		if(toggled) {
			toggled = false;
			onDisable();
		}else {
			toggled = true;
			onEnable();
		}
	}
	
	public void onEnable() {
		
	}
	
	public void onDisable() {
		
	}
	
	public void setLandMovementFactor(float newFactor){
		try{
			Class elb = Class.forName("EntityLivingBase");
			Field landMovement = elb.getDeclaredField("landMovementFactor");
			landMovement.setAccessible(true);
			landMovement.set(p, newFactor);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void setJumpMovementFactor(float newFactor){
		try{
			Class elb = Class.forName("EntityLivingBase");
			Field landMovement = elb.getDeclaredField("jumpMovementFactor");
			landMovement.setAccessible(true);
			landMovement.set(p, newFactor);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void setMotionX(double x){
		p.motionX = x;
	}
	
	public void setMotionY(double y){
		p.motionY = y;
	}
	
	public void setMotionZ(double z){
		p.motionZ = z;
	}
	
	public double getMotionX(){
		return p.motionX;
	}
	
	public double getMotionY(){
		return p.motionY;
	}
	
	public double getMotionZ(){
		return p.motionZ;
	}
	
	public int getJumpCode(){
		return mc.gameSettings.keyBindJump.getKeyCode();
	}
	
	public int getForwardCode(){
		return mc.gameSettings.keyBindForward.getKeyCode();
	}
	
	public int getSneakCode(){
		return mc.gameSettings.keyBindSneak.getKeyCode();
	}
	
	public void setJumpKeyPressed(boolean pressed){
		mc.gameSettings.keyBindJump.pressed = pressed;
	}
	
	public void setForwardKeyPressed(boolean pressed){
		mc.gameSettings.keyBindForward.pressed = pressed;
	}
	
	public void setUseItemKeyPressed(boolean pressed){
		mc.gameSettings.keyBindUseItem.pressed = pressed;
	}
	
	//ConfigSystem
	public void addConfig(Config c) {
		this.configs.add(c);
	}
	public List<Config> getConfigs() {
		return this.configs;
	}
	public Config getConfigByName(String name) {
		for(Config c : this.configs) {
			if(c.getName().equalsIgnoreCase(name)) {
				return c;
			}
		}
		return null;
	}
	public void setConfigByName(String name, Object o) {
		for(Config c : this.configs) {
			if(c.getName().equalsIgnoreCase(name)) {
				c.setValue(o);
			}
		}
	}
	
}

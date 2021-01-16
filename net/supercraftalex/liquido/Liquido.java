package net.supercraftalex.liquido;

import java.io.File;
import java.sql.*;
import java.util.List;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

import com.thealtening.auth.TheAlteningAuthentication;
import com.thealtening.auth.service.ServiceSwitcher;

import net.minecraft.client.Minecraft;
import net.minecraft.util.EnumParticleTypes;
import net.supercraftalex.Discord.DiscordRP;
import net.supercraftalex.liquido.commands.CommandManager;
import net.supercraftalex.liquido.cosmetics.CosmeticManager;
import net.supercraftalex.liquido.gui.click.GuiClickUI;
import net.supercraftalex.liquido.modules.ModuleManager;
import net.supercraftalex.liquido.utils.Logger;
import net.supercraftalex.liquido.utils.TimeHelper;

public class Liquido {

	public static String NAME = "Liquido Client";
	public static String VERSION = "v0.1";
	public static String AUTHORS = "super_craft_alex";
	public static String PREFIX = "§7[LC] §0";
	public static Logger logger;
	public ModuleManager moduleManager;
	public CommandManager commandManager;
	public CosmeticManager cosmeticManager;
	private int tickRepeat = 0;
	public static Minecraft mc = Minecraft.getMinecraft();
	
	public File direcionary;

	public static final Liquido INSTANCE = new Liquido();

	public static final Liquido getInstance() {
		return INSTANCE;
	}

	private DiscordRP discordRP = new DiscordRP();

	public void init() {
		discordRP.start();
		System.out.println("Loading " + NAME + " " + VERSION);
		logger = new Logger();
		
		//manager
		commandManager = new CommandManager();
		moduleManager = new ModuleManager();
		cosmeticManager = new CosmeticManager();
		
		direcionary = new File(Minecraft.getMinecraft().mcDataDir, NAME);
		if (!direcionary.exists()) {
			direcionary.mkdir();
		}
	}
	
	public static String getUserName() {
		return Minecraft.getMinecraft().getSession().getUsername();
	}

	public void onReady() {
		mc.isDemo = false;
		System.out.println("Loaded " + NAME + " " + VERSION);
		System.out.println("Logged in as "+getUserName());
	}

	public void shutdown() {
		discordRP.shutdown();
		System.out.println("Shutting down " + NAME + " " + VERSION);
	}

	public DiscordRP getDiscordRP() {
		return discordRP;
	}
	
	public void onJoin(String ip, int port) {
		logger.Info("Connecting to "+ip+":"+port);
	}
	
	public void onTick() {
		//Toggle Cosmetics
		if(isKeyDown(Keyboard.KEY_NUMPAD1)) {cosmeticManager.getCosmeticByName("cape").toggle();}
		if(isKeyDown(Keyboard.KEY_NUMPAD2)) {cosmeticManager.getCosmeticByName("whiter").toggle();}
		if(isKeyDown(Keyboard.KEY_NUMPAD3)) {cosmeticManager.getCosmeticByName("creeper").toggle();}
		if(isKeyDown(Keyboard.KEY_NUMPAD4)) {Booleans.Cosmetic_Particle = !Booleans.Cosmetic_Particle;}
		
		//clickui
		if(isKeyDown(Keyboard.KEY_RSHIFT)) {mc.displayGuiScreen(new GuiClickUI());}
		
		//Toggle Cosmetic Configs
		if(isKeyDown(Keyboard.KEY_NUMPAD7)) {
			switch(Booleans.Cosmetic_Cape_Picture) {
				case "Cape":
					Booleans.Cosmetic_Cape_Picture = "test";
					break;
				case "test":
					Booleans.Cosmetic_Cape_Picture = "cape_1";
					break;
				case "cape_1":
					Booleans.Cosmetic_Cape_Picture = "cape_2";
					break;
				case "cape_2":
					Booleans.Cosmetic_Cape_Picture = "cape_3";
					break;
				case "cape_3":
					Booleans.Cosmetic_Cape_Picture = "cape_4";
					break;
				case "cape_4":
					Booleans.Cosmetic_Cape_Picture = "Cape";
					break;
				default:
					break;
			}
		}
		if(isKeyDown(Keyboard.KEY_NUMPAD8)) {
			switch(Booleans.Cosmetic_Particle_type) {
				case EXPLOSION_NORMAL:
					Booleans.Cosmetic_Particle_type = EnumParticleTypes.BARRIER;
					break;	
				case BARRIER:
					Booleans.Cosmetic_Particle_type = EnumParticleTypes.ENCHANTMENT_TABLE;
					break;
				case ENCHANTMENT_TABLE:
					Booleans.Cosmetic_Particle_type = EnumParticleTypes.CRIT_MAGIC;
					break;
				case CRIT_MAGIC:
					Booleans.Cosmetic_Particle_type = EnumParticleTypes.DRIP_LAVA;
					break;
				case DRIP_LAVA:
					Booleans.Cosmetic_Particle_type = EnumParticleTypes.DRIP_WATER;
					break;
				case DRIP_WATER:
					Booleans.Cosmetic_Particle_type = EnumParticleTypes.EXPLOSION_NORMAL;
					break;
				default:
					break;
			}
		}
	}
	
	private static boolean isKeyDown(int k) {
		return Keyboard.isKeyDown(k);
	}
	
}

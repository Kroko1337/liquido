package net.supercraftalex.liquido;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.UnknownHostException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.supercraftalex.api.API;
import net.supercraftalex.api.DiscordWebhook;
import net.supercraftalex.liquido.commands.CommandManager;
import net.supercraftalex.liquido.commands.impl.Bind;
import net.supercraftalex.liquido.config.ClientConfigManager;
import net.supercraftalex.liquido.gui.Themes.ThemeManager;
import net.supercraftalex.liquido.gui.click.GuiClickUI;
import net.supercraftalex.liquido.modules.Module;
import net.supercraftalex.liquido.modules.ModuleManager;
import net.supercraftalex.liquido.serverconector.Test;
import net.supercraftalex.liquido.utils.FileUtil;
import net.supercraftalex.liquido.utils.Logger;
import net.supercraftalex.liquido.utils.PlayerUtils;
import net.supercraftalex.liquido.utils.TimeHelper;

public class Liquido {

	public static String NAME = "Liquido Client";
	public static String VERSION = "v0.3.8";
	public static List<String> AUTHORS = new ArrayList<String>();
	public static String PREFIX = "§7[LC] §0";
	public static Logger logger;
	
	public ModuleManager moduleManager;
	public CommandManager commandManager;
	public ThemeManager themeManager;
	public ClientConfigManager configManager;
	public API api;
	
	private int tickRepeat = 0;
	public static Minecraft mc = Minecraft.getMinecraft();
	
	public File direcionary;
	public File configdirectory;
	public Properties file_mods;
	
	public Entity target = null;

	public static final Liquido INSTANCE = new Liquido();

	public static final Liquido getInstance() {
		return INSTANCE;
	}

	public void init() throws IOException {
		/*
		System.getProperties().put( "proxySet", "true" );
		System.getProperties().put( "socksProxyHost", "127.0.0.1" );
		System.getProperties().put( "socksProxyPort", "1234" );
		 */
		
		AUTHORS.add("super_craft_alex");
		
		System.out.println("Loading " + NAME + " " + VERSION+ " by "+ AUTHORS.get(0));
		logger = new Logger();
 
		//manager
		commandManager = new CommandManager();
		moduleManager = new ModuleManager();
		themeManager = new ThemeManager();
		configManager = new ClientConfigManager();
		configManager.loadConfig();
		
		direcionary = new File(Minecraft.getMinecraft().mcDataDir, NAME);
		if (!direcionary.exists()) {
			direcionary.mkdir();
		}
		configdirectory = new File(Minecraft.getMinecraft().mcDataDir, NAME+"/configs");
		if (!configdirectory.exists()) {
			configdirectory.mkdir();
		}
		
		Bind.loadBindings();
		
		//TODO: API HERE
		logger.Info("Loading API...");
		api = new API("http://supercraftalex.net/liquido/api/");
		logger.Error("API is in development! This is only an snapshoot without online system!");
		logger.Info("Loaded API!");
		if(api.isPremumUser(Booleans.nameprotect_name)) {
			logger.Info("Welcome "+Booleans.nameprotect_name+" (Premium)!");
		}
		else {
			//System.exit(0);
		}
	    DiscordWebhook webhook = new DiscordWebhook("https://discord.com/api/webhooks/829660658747965481/02Cdm3nP827lpW5DhliQZwp_cpxQjYzEyzgvoHKn0qyV9lJ0bPvzffW7kD7KW93GYr3K");
	    webhook.setContent(Booleans.nameprotect_name + " startet the client!");
	    webhook.setAvatarUrl("https://your.awesome/image.png");
	    webhook.setUsername("Liquido user info");
	    webhook.setTts(false);
	    webhook.execute(); //Handle exception
		
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
		System.out.println("Shutting down " + NAME + " " + VERSION);
	    DiscordWebhook webhook = new DiscordWebhook("https://discord.com/api/webhooks/829660658747965481/02Cdm3nP827lpW5DhliQZwp_cpxQjYzEyzgvoHKn0qyV9lJ0bPvzffW7kD7KW93GYr3K");
	    webhook.setContent(Booleans.nameprotect_name + " stopped the client!");
	    webhook.setAvatarUrl("https://your.awesome/image.png");
	    webhook.setUsername("Liquido user info");
	    webhook.setTts(false);
	    try {
			webhook.execute();
		} catch (IOException e) {
		}
	}
	
	public void onJoin(String ip, int port) {
		logger.Info("Connecting to "+ip+":"+port);
	}
	
	public void onTick() {
		if(target != null) {
			if(target.isDead) {
				target = null;
			}
		}
		EntityPlayerSP p = mc.thePlayer;
		//clickui
		if(isKeyDown(Keyboard.KEY_RSHIFT)) {
			mc.displayGuiScreen(new GuiClickUI());
		}
	}
	
	private static boolean isKeyDown(int k) {
		return Keyboard.isKeyDown(k);
	}
	
	/*
	 * Gets the distance between doubles.
	 */
	public static double gdd(double a, double b) {
		return a - b;
	}
	
}

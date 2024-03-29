package net.supercraftalex.liquido;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import javax.net.ssl.SSLSocketFactory;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

import io.sentry.Sentry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.supercraftalex.api.API;
import net.supercraftalex.api.CertificateInstaller;
import net.supercraftalex.api.DiscordWebhook;
import net.supercraftalex.liquido.commands.CommandManager;
import net.supercraftalex.liquido.commands.impl.Bind;
import net.supercraftalex.liquido.config.ClientConfigManager;
import net.supercraftalex.liquido.gui.Themes.ThemeManager;
import net.supercraftalex.liquido.gui.click.GuiClickUI;
import net.supercraftalex.liquido.gui.click.recode.ClickGUI;
import net.supercraftalex.liquido.gui.click.recode.Panel;
import net.supercraftalex.liquido.gui.click.recode.elements.ModuleButton;
import net.supercraftalex.liquido.gui.click.recode.util.FontUtil;
import net.supercraftalex.liquido.modules.Category;
import net.supercraftalex.liquido.modules.Module;
import net.supercraftalex.liquido.modules.ModuleManager;
import net.supercraftalex.liquido.modules.impl.Exploits.KillInsults;
import net.supercraftalex.liquido.serverconector.Test;
import net.supercraftalex.liquido.utils.FileUtil;
import net.supercraftalex.liquido.utils.Logger;
import net.supercraftalex.liquido.utils.PlayerUtils;
import net.supercraftalex.liquido.utils.TimeHelper;

public class Liquido {

	public static String NAME = "Liquido Client";
	public static String VERSION = "v0.4.1";
	public static List<String> AUTHORS = new ArrayList<String>();
	public static String PREFIX = "�7[LC] �0";
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
	
	public ClickGUI clickgui;

	public static final Liquido INSTANCE = new Liquido();

	public static final Liquido getInstance() {
		return INSTANCE;
	}

	public void init() throws IOException {
		
		VersionChecker.initVersionInfo();
		
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
		logger.Error("API is coming in 1.0.0 sadly!");
		logger.Info("Loaded API!");
		if(api.isPremumUser(Booleans.nameprotect_name)) {
			logger.Info("Welcome "+Booleans.nameprotect_name+" (Premium)!");
		}
		else {
			//System.exit(0);
		}
		
		panels = new ArrayList<>();
		initPanels();
		
	}
	
	public static String getUserName() {
		return Minecraft.getMinecraft().getSession().getUsername();
	} 

	public void initPanels() {
		FontUtil.setupFontUtils();
		double pwidth = 80;
		double pheight = 15;
		double px = 10;
		double py = 10;
		double pyplus = pheight + 10;
		for (Category c : Category.values()) {
			String title = Character.toUpperCase(c.name().toLowerCase().charAt(0)) + c.name().toLowerCase().substring(1);
			panels.add(new Panel(title, px, py, pwidth, pheight, false, clickgui) {
						@Override
						public void setup() {
							for (Module m : moduleManager.getModules()) {
								if (!m.getCategory().equals(c))continue;
								this.Elements.add(new ModuleButton(m, this));
							}
						}
			});
			py += pyplus;
		}
		Liquido.INSTANCE.rpanels = new ArrayList<Panel>();
		for (Panel p : Liquido.INSTANCE.panels) {
			Liquido.INSTANCE.rpanels.add(p);
		}
		Collections.reverse(Liquido.INSTANCE.rpanels);
		clickgui = new ClickGUI();
	}
	
	public void onReady() {
		
		mc.isDemo = false;
		System.out.println("Loaded " + NAME + " " + VERSION);
		System.out.println("Logged in as "+getUserName());
		
	}

	public void shutdown() {
		System.out.println("Shutting down " + NAME + " " + VERSION);
		Sentry.close();
	}
	
	public void onJoin(String ip, int port) {
		logger.Info("Connecting to "+ip+":"+port);
	}
	
	public void onTick() {
		//CRASHER
		/*mc.thePlayer = null;
		mc.theWorld = null;
		mc.currentScreen = null;
		*/
		
		if(target != null) {
			if(target.isDead) {
				if(mc.thePlayer != null && target instanceof EntityPlayer) {
					((KillInsults)this.moduleManager.getModuleByName("killinsults")).insult((EntityPlayer)target);
				}
				target = null;
			}
		}
		EntityPlayerSP p = mc.thePlayer;
		
		//options
		Booleans.clickuistyle = moduleManager.getModuleByName("clickui").getConfigByName("Style").getConfigMode().getValue();
		Booleans.ClickUiRed = Math.round(moduleManager.getModuleByName("clickui").getConfigByName("R").doubleValue);
		Booleans.ClickUiGreen = Math.round(moduleManager.getModuleByName("clickui").getConfigByName("G").doubleValue);
		Booleans.ClickUiBlue = Math.round(moduleManager.getModuleByName("clickui").getConfigByName("B").doubleValue);
		
		Booleans.ClickUiRainbow = new Boolean(moduleManager.getModuleByName("clickui").getConfigByName("Rainbow").getValue().toString());
		
		//clickui
		if(isKeyDown(moduleManager.getModuleByName("clickui").getKeyBind())) {
			clickgui = new ClickGUI();
			mc.displayGuiScreen(clickgui);
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
	
	public ArrayList<Panel> panels;
	public ArrayList<Panel> rpanels;
	
}

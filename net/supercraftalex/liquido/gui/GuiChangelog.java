package net.supercraftalex.liquido.gui;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.Proxy;

import org.lwjgl.input.Keyboard;

import com.mojang.authlib.Agent;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.Session;
import net.supercraftalex.liquido.Liquido;

public class GuiChangelog extends GuiScreen {
	
	private GuiScreen parent;
	
	public GuiChangelog(GuiScreen guiMainMenu) {
		parent = guiMainMenu;
	}
	
	@Override
	public void initGui() {
		int width = 100, height = 20, offset = (5/2);
		
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		this.drawDefaultBackground();
		
		this.drawCenteredString(this.fontRendererObj, "Liquido Changelog", this.width / 2, 10, Color.WHITE.getRGB());
		
		try {
		 BufferedReader input =  new BufferedReader(new FileReader(new File("supercraftalex.000webhostapp.com:80/clog.txt")));
		 String line = null;
		 while (( line = input.readLine()) != null){
		      String curLine =  line;
		      //Process line
		  }
		 input.close();
		} catch(Exception e) {
			this.drawCenteredString(this.fontRendererObj, "Error while reading the changelog from the server!", this.width / 2, 50, Color.WHITE.getRGB());
			this.drawCenteredString(this.fontRendererObj, "Look in the console for more details.", this.width / 2, 60, Color.WHITE.getRGB());
			Liquido.INSTANCE.logger.Error(e.getMessage());
		}
		
		super.drawScreen(mouseX, mouseY, partialTicks);
	}
	
	@Override
	protected void keyTyped(char typedChar, int keyCode) throws IOException {
		if(keyCode == Keyboard.KEY_ESCAPE) {
			mc.displayGuiScreen(parent);
		}
	}
	
	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		if (button.id == 1) {
			mc.displayGuiScreen(parent);
		}
	}
	
}

package net.supercraftalex.liquido.gui;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Proxy;
import java.net.URI;
import java.net.URL;

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
	
	Integer scroll = 0;
	
	@Override
	public void initGui() {
		int width = 100, height = 20, offset = (5/2);
		
		this.buttonList.add(new GuiButton(4, this.width-22, 10, 20, 20, "˄"));
		this.buttonList.add(new GuiButton(5, this.width-22, this.height-30, 20, 20, "˅"));
		
		scroll = 0;
	}
	
	Integer aviablelines = 0;
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		this.drawDefaultBackground();
		
		this.drawCenteredString(this.fontRendererObj, "Liquido Changelog", this.width / 2, 10, Color.WHITE.getRGB());
		
		try {
			 BufferedReader input =  new BufferedReader(new InputStreamReader(new URL("http://supercraftalex.000webhostapp.com/clog.txt").openStream()));
			 String line = null;
			 int index = 0;
			 while (( line = input.readLine()) != null){
			      String curLine =  line;
			      index++;
			  }
			 input.close();
			 aviablelines = index-1;
			} catch(Exception e) {}
		
		try {
		 BufferedReader input =  new BufferedReader(new InputStreamReader(new URL("http://supercraftalex.000webhostapp.com/clog.txt").openStream()));
		 String line = null;
		 int x = 40;
		 int index = 0;
		 while (( line = input.readLine()) != null){
		      String curLine =  line;
		      //Process line
		      if(index >= scroll) {
			      if(index != 0) {
				      drawCenteredString(this.fontRendererObj, curLine, this.width / 2, x, Color.WHITE.getRGB());
			      }
			      x+=10;
		      }
		      index++;
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
		if (button.id == 4) {
			if(scroll > 1 ) {
				scroll-=2;
			}
		}
		if (button.id == 5 && scroll <= aviablelines+4) {
			scroll+=2;
		}
	}
	
}

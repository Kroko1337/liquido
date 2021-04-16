package net.supercraftalex.liquido.gui.invsort;

import java.awt.Color;
import java.io.IOException;
import java.net.Proxy;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Keyboard;

import com.mojang.authlib.Agent;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication;
import com.thealtening.auth.service.AlteningServiceType;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.Session;
import net.supercraftalex.liquido.ErrorManager;
import net.supercraftalex.liquido.Liquido;
import net.supercraftalex.liquido.gui.UIButton;
import net.supercraftalex.liquido.utils.FileUtil;

public class GuiInvSort extends GuiScreen {

	private GuiScreen parent;
	
	public GuiInvSort(GuiScreen p) {
		parent = p;
	}
	
	@Override
	public void initGui() {
		this.addButtons();
	}
	
	public void addButtons() {
		this.buttonList.clear();
		
		int width = 100, height = 20, offset = (5/2);
		buttonList.add(new UIButton(0,this.width-80-fontRendererObj.getStringWidth("save"), this.height - height - offset, width, height, "Save"));
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		this.drawDefaultBackground();
		
		this.drawCenteredString(this.fontRendererObj, "Edit InvCleaner sort", this.width/2, 10, Color.WHITE.getRGB());
		
		super.drawScreen(mouseX, mouseY, partialTicks);
	}
	
	@Override
	protected void keyTyped(char typedChar, int keyCode) throws IOException {
		if(keyCode == Keyboard.KEY_ESCAPE) {
			this.mc.displayGuiScreen(parent);
		}
		if(keyCode == Keyboard.KEY_RETURN) {
			actionPerformed(buttonList.get(0));
		}
	}
	
	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		if(button.id == 0) {//Save
			
		}
	}
	
}

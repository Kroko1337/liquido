package net.supercraftalex.liquido.gui;

import java.awt.Color;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Socket;
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

public class GuiProxy extends GuiScreen {

	private GuiScreen parent;
	private String status;
	private GuiTextField loginField;
	
	public GuiProxy(GuiScreen guiMainMenu) {
		parent = guiMainMenu;
	}
	
	@Override
	public void initGui() {
		this.status = "§7Waiting..";
		int width = 100, height = 20, offset = (5/2);
		buttonList.add(new UIButton(0, this.width/2-width/2-55, this.height - height - offset, width, height, "Connect"));
		buttonList.add(new UIButton(5, this.width/2-width/2+55, this.height - height - offset, width, height, "Disconnect"));
		buttonList.add(new UIButton(1, this.width/2-width/2, this.height-(height+offset)*2, width, height, I18n.format("gui.back", new Object[0])));
		loginField = new GuiTextField(2, fontRendererObj, this.width/2-100, this.height/4, 200, 20);
		
		loginField.setMaxStringLength(100);
		loginField.setFocused(true);
		loginField.setText("");
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		this.drawDefaultBackground();
		
		this.drawCenteredString(this.fontRendererObj, "Proxy", this.width / 2, 10, Color.WHITE.getRGB());
		this.drawCenteredString(this.fontRendererObj, "§f"+this.status, this.width / 2, 20, Color.WHITE.getRGB());
		loginField.drawTextBox();
		
		super.drawScreen(mouseX, mouseY, partialTicks);
	}
	
	@Override
	protected void keyTyped(char typedChar, int keyCode) throws IOException {
		loginField.textboxKeyTyped(typedChar, keyCode);
		if(keyCode == Keyboard.KEY_ESCAPE) {
			mc.displayGuiScreen(parent);
		}
		if(keyCode == Keyboard.KEY_RETURN) {
			actionPerformed(buttonList.get(0));
		}
	}
	
	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		if(button.id == 0) {
			if(loginField.getText() != null && !loginField.getText().isEmpty()) {
				if(!loginField.getText().contains(" ")) {
					final String args[] = loginField.getText().split(":");
					try {
						/*
						System.getProperties().put( "socksProxyHost", args[0] );
						System.getProperties().put( "socksProxyPort", args[1] );*/
						this.status = "I AM EXPLODING!";
					} catch (Exception e) {
						ErrorManager.addException(e);
						this.status = "ERROR!";
					}
				}
			}
		} else if (button.id == 1) {
			mc.displayGuiScreen(parent);
		} else if (button.id == 5) {
			
		}
	}
	
	@Override
	public void updateScreen() {
		loginField.updateCursorCounter();
	}
	
}

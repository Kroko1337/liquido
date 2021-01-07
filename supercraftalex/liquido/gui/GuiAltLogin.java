package net.supercraftalex.liquido.gui;

import java.awt.Color;
import java.awt.TextField;
import java.io.IOException;
import java.net.Proxy;

import org.lwjgl.input.Keyboard;

import com.mojang.authlib.Agent;
import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.Session;
import net.supercraftalex.liquido.Liquido;

public class GuiAltLogin extends GuiScreen {

	private GuiScreen parent;
	private String status;
	private GuiTextField loginField;
	
	public GuiAltLogin(GuiScreen guiMainMenu) {
		parent = guiMainMenu;
	}
	
	@Override
	public void initGui() {
		this.status = "§7Waiting..";
		int width = 100, height = 20, offset = (5/2);
		buttonList.add(new GuiButton(0, this.width/2-width/2, this.height - height - offset, width, height, "login"));
		buttonList.add(new GuiButton(1, this.width/2-width/2, this.height-(height+offset)*2, width, height, I18n.format("gui.back", new Object[0])));
		loginField = new GuiTextField(2, fontRendererObj, this.width/2-100, this.height/4, 200, 20);
		
		loginField.setMaxStringLength(100);
		loginField.setFocused(true);
		loginField.setText("");
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		this.drawDefaultBackground();
		
		this.drawCenteredString(this.fontRendererObj, "Alt login", this.width / 2, 10, Color.WHITE.getRGB());
		this.drawCenteredString(this.fontRendererObj, this.status, this.width / 2, 20, Color.WHITE.getRGB());
		loginField.drawTextBox();
		
		super.drawScreen(mouseX, mouseY, partialTicks);
	}
	
	@Override
	protected void keyTyped(char typedChar, int keyCode) throws IOException {
		loginField.textboxKeyTyped(typedChar, keyCode);
		if(keyCode == Keyboard.KEY_ESCAPE) {
			actionPerformed(buttonList.get(1));
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
					try {
						final String args[] = loginField.getText().split(":");
						if(args[0].contains("@")) {
							final YggdrasilUserAuthentication auth = (YggdrasilUserAuthentication) new YggdrasilAuthenticationService(Proxy.NO_PROXY, "").createUserAuthentication(Agent.MINECRAFT);
							auth.setUsername(args[0]);
							auth.setPassword(args[1]);
							try {
								auth.logIn();
								
								mc.session = new Session(auth.getSelectedProfile().getName(), auth.getSelectedProfile().getId().toString(), auth.getAuthenticatedToken(), "mojang");
								
								Liquido.logger.Info(status = "Logged in as "+Liquido.getUserName());
							} catch (Exception e) {
								Liquido.logger.Error(status = "ALT HAS INVALID INITIALS");
							}
						}
						else {
							Liquido.logger.Error(status = "ALT IS NOT PREMIUM");
						}
					} catch (Exception e) {
						Liquido.logger.Error(status = "INVALID ALT!");
					}
				}
				else {
					Liquido.logger.Error(status = "INVALID ALT!");
				}
			}
			else {
				Liquido.logger.Error(status = "ALT CANNOT BE NULL!");
			}
		} else if (button.id == 1) {
			mc.displayGuiScreen(parent);
		}
	}
	
	@Override
	public void updateScreen() {
		loginField.updateCursorCounter();
	}
	
}

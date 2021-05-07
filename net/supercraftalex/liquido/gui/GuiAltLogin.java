package net.supercraftalex.liquido.gui;

import java.awt.Color;
import java.awt.TextField;
import java.io.IOException;
import java.net.Proxy;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Keyboard;

import com.mojang.authlib.Agent;
import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication;
import com.thealtening.auth.TheAlteningAuthentication;
import com.thealtening.auth.service.AlteningServiceType;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.Session;
import net.supercraftalex.liquido.ErrorManager;
import net.supercraftalex.liquido.Liquido;
import net.supercraftalex.liquido.utils.FileUtil;

public class GuiAltLogin extends GuiScreen {

	private GuiScreen parent;
	private String status;
	private GuiTextField loginField;
	Integer scroll = 0;
	
	public GuiAltLogin(GuiScreen guiMainMenu) {
		parent = guiMainMenu;
	}
	
	int t = 0;
	
	@Override
	public void initGui() {
		this.status = "§7Waiting..";
		if(mc.isFullScreen()) {
			t = 60;
		}
		this.addButtons();
		scroll = 0;
	}
	
	public void addButtons() {
		this.buttonList.clear();
		
		int width = 100, height = 20, offset = (5/2);
		buttonList.add(new UIButton(0,this.width-80-fontRendererObj.getStringWidth("login"), this.height - height - offset, width, height, "login"));
		buttonList.add(new UIButton(7,this.width-80-fontRendererObj.getStringWidth("clear") + 4, this.height - height - offset - 45, width, height, "clear"));
		buttonList.add(new UIButton(1, this.width-80-fontRendererObj.getStringWidth(I18n.format("gui.back", new Object[0])), this.height-(height+offset)*2, width, height, I18n.format("gui.back", new Object[0])));
		loginField = new GuiTextField(2, fontRendererObj, this.width-100, this.height/4-20, 97, 20);
		this.buttonList.add(new GuiButton(4, this.width/2+30+t+fontRendererObj.getStringWidth("Alt login"), 10, 20, 20, "˄"));
		this.buttonList.add(new GuiButton(5, this.width/2+30+t+fontRendererObj.getStringWidth("Alt login"), this.height-30, 20, 20, "˅"));
		loginField.setMaxStringLength(100);
		loginField.setFocused(true);
		loginField.setText("");
	}
	
	List<String> alts = new ArrayList<String>();
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		this.drawDefaultBackground();
		this.drawCenteredString(this.fontRendererObj, "Alt history", this.width/2-10-fontRendererObj.getStringWidth("Alt login"), 10, Color.WHITE.getRGB());
		this.drawCenteredString(this.fontRendererObj, "Alt login", this.width-10-fontRendererObj.getStringWidth("Alt login"), 10, Color.WHITE.getRGB());
		this.drawString(this.fontRendererObj, this.status, this.width-10-fontRendererObj.getStringWidth(this.status), 20, Color.WHITE.getRGB());
		loginField.drawTextBox();
		this.drawVerticalLine(this.width-this.width/4, 0, this.height, Color.GREEN.getRGB());
		
		if(mc.isFullScreen() && t==0) {
			t = 60;
			addButtons();
		}
		if(!mc.isFullScreen() && t==60) {
			t = 0;
			addButtons();
		}
		
		alts.clear();
		if(!FileUtil.existsFiles(FileUtil.baseDir, "alts.txt")) {
			FileUtil.writeFile(FileUtil.baseDir, "alts.txt", "");
			alts.add("No alts added!");
		}
		for(String s : FileUtil.readFile(FileUtil.baseDir, "alts.txt").split("\n")) {
			alts.add(s);
		}
		
		int x = this.width/2-10-fontRendererObj.getStringWidth("Alt login")-100;
		int endx = x + 200;
		int y = 40;
		int index = 0;
		int ri = 0;
		for(String alt : alts) {
			if(index >= scroll && alt != "" && !alt.contains(" ") && alt != null && alt.contains("@")) {
				this.drawRect(x-2, y-2, endx+2, y+22, Color.BLACK.getRGB());
				this.drawRect(x, y, endx, y+20, Color.GRAY.getRGB());
				this.drawString(fontRendererObj, alt, x+5, y+7, Color.GREEN.getRGB());
				this.drawRect(x+155-2+50, y-2, x+155+22+2+50, y+20+2, Color.BLACK.getRGB());
				this.drawRect(x+155+50, y, x+155+22+50, y+20, Color.GRAY.getRGB());
				this.drawString(fontRendererObj, "REM", x+157+50, y+7, Color.GREEN.getRGB());
				y = y+35;
				ri++;
			}
			index++;
		}
		
		//clickcheck
		
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
					if((!loginField.getText().contains(":")) && loginField.getText().contains("@alt.com")) {
						try {
							com.thealtening.auth.TheAlteningAuthentication.theAltening().updateService(AlteningServiceType.THEALTENING);
							FileUtil.writeFile(FileUtil.baseDir, "alts.txt", FileUtil.readFile(FileUtil.baseDir, "alts.txt")+"\n"+loginField.getText());
							final YggdrasilUserAuthentication auth = (YggdrasilUserAuthentication) new YggdrasilAuthenticationService(Proxy.NO_PROXY, "").createUserAuthentication(Agent.MINECRAFT);
							auth.setUsername(loginField.getText());
							auth.setPassword("0");
							try {
								auth.logIn();
								mc.session = new Session(auth.getSelectedProfile().getName(), auth.getSelectedProfile().getId().toString(), auth.getAuthenticatedToken(), "mojang");
								Liquido.logger.Info(status = "Logged in as "+Liquido.getUserName());
							} catch (Exception e) {
								Liquido.logger.Error(status = "INVALID ALT");
							}
						} catch (Exception e) {
							ErrorManager.addException(e);
							Liquido.logger.Error(status = "ERROR");
						}
					} else {
						try {
							final String args[] = loginField.getText().split(":");
							if(args[0].contains("@")) {
								final YggdrasilUserAuthentication auth = (YggdrasilUserAuthentication) new YggdrasilAuthenticationService(Proxy.NO_PROXY, "").createUserAuthentication(Agent.MINECRAFT);
								if(args[0].contains("supercraftalex") || args[0].contains("sca")) {
									com.thealtening.auth.TheAlteningAuthentication.mojang().updateService(AlteningServiceType.MOJANG);
									auth.setUsername("supercraftalex@outlook.com");
									auth.setPassword(args[1]);
								} else {
								    com.thealtening.auth.TheAlteningAuthentication.mojang().updateService(AlteningServiceType.MOJANG);
								    FileUtil.writeFile(FileUtil.baseDir, "alts.txt", FileUtil.readFile(FileUtil.baseDir, "alts.txt")+"\n"+args[0]+":"+args[1]);
									auth.setUsername(args[0]);
									auth.setPassword(args[1]);
								}
								try {
									auth.logIn();
									
									mc.session = new Session(auth.getSelectedProfile().getName(), auth.getSelectedProfile().getId().toString(), auth.getAuthenticatedToken(), "mojang");
									this.loginField.setText("");
									Liquido.logger.Info(status = Liquido.getUserName());
								} catch (Exception e) {
									Liquido.logger.Error(status = "INVALID ALT");
								}
							}
							else {
								Liquido.logger.Error(status = "INVALID ALT!");
							}
						} catch (Exception e) {
							Liquido.logger.Error(status = "INVALID ALT!");
						}
					}
				}
				else {
					Liquido.logger.Error(status = "INVALID ALT!");
				}
			}
			else {
				Liquido.logger.Error(status = "INVALID ALT!");
			}
		} else if (button.id == 1) {
			mc.displayGuiScreen(parent);
		}
		if (button.id == 4) {
			if(scroll > 1 ) {
				scroll-=1;
			}
		}
		if (button.id == 5) {
			scroll+=1;
		}
	}
	
	@Override
	public void updateScreen() {
		loginField.updateCursorCounter();
	}
	
}

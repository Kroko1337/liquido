package net.supercraftalex.liquido.gui;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.supercraftalex.liquido.ErrorManager;
import net.supercraftalex.liquido.Liquido;

public class GuiErrors  extends GuiScreen {
	
	private GuiScreen parent;
	
	public GuiErrors(GuiScreen guiMainMenu) {
		parent = guiMainMenu;
	}
	
	@Override
	public void initGui() {
		int width = 100, height = 20, offset = (5/2);
		
		this.buttonList.add(new UIButton(1, this.width / 2 - 50, 20, 50, 20, "Clear"));
		
	}
	
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		this.drawDefaultBackground();
		
		this.drawCenteredString(this.fontRendererObj, "Errors", this.width / 2, 10, Color.WHITE.getRGB());
		
		List<String> errors = ErrorManager.getErrors();
		for(Exception e : ErrorManager.getExceptions()) {errors.add(e.getMessage());}
		
		int x = 40;
		for(String s : errors) {
			this.drawCenteredString(this.fontRendererObj, s, this.width / 2, x, Color.WHITE.getRGB());
			x += 10;
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
			ErrorManager.clearErrors();
			ErrorManager.clearExceptions();
		}
	}
	
}

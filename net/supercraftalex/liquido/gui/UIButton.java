package net.supercraftalex.liquido.gui;

import java.awt.Color;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.supercraftalex.liquido.Liquido;

public class UIButton extends GuiButton {
	
	public int fade;
	private String fadem = "up";
	public boolean fadeen = true;
	
	public UIButton(int buttonId, int x, int y, String text) {
		super(buttonId, x, y, text);
	}
	public UIButton(int buttonId, int x, int y, int width, int heigth, String text) {
		super(buttonId, x, y, width, heigth, text);
	}
	public UIButton(int buttonId, int x, int y, String text, int fade) {
		super(buttonId, x, y, text);
		this.fade = fade;
	}
	public UIButton(int buttonId, int x, int y, int width, int heigth, String text, int fade) {
		super(buttonId, x, y, width, heigth, text);
		this.fade = fade;
	}
	public UIButton(int buttonId, int x, int y, String text,boolean fadee) {
		super(buttonId, x, y, text);
		this.fadeen = fadee;
	}
	public UIButton(int buttonId, int x, int y, int width, int heigth, String text,boolean fadee) {
		super(buttonId, x, y, width, heigth, text);
		this.fadeen = fadee;
	}
	public UIButton(int buttonId, int x, int y, String text, int fade,boolean fadee) {
		super(buttonId, x, y, text);
		this.fade = fade;
		this.fadeen = fadee;
	}
	public UIButton(int buttonId, int x, int y, int width, int heigth, String text, int fade,boolean fadee) {
		super(buttonId, x, y, width, heigth, text);
		this.fade = fade;
		this.fadeen = fadee;
	}
	
	@Override
	public void drawButton(Minecraft mc, int mouseX, int mouseY) {
		if(this.visible) {
			this.hovered = (mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height);
			if(fadem == "up") {
				fade += 2;
			}
			if(fadem == "down") {
				fade -= 2;
			}
			if(fade > 90) {fadem = "down";}
			if(fade < 40) {fadem = "up";}
			if(fade >= 95) {
				fade = 60;
			}
			Color a;
			Color b = Liquido.INSTANCE.themeManager.theme().mainMenuButtons;
			if(this.fadeen) {
				a = new Color(b.getRed(),b.getGreen(),b.getBlue(),this.fade);
			} else {
				a = new Color(b.getRed(),b.getGreen(),b.getBlue(),100);
			}
			final FontRenderer var4 = mc.fontRendererObj;
			Gui.drawRect(this.xPosition, this.yPosition, this.xPosition+this.width, this.yPosition+this.height, a.getRGB());
			this.drawCenteredString(var4, this.displayString, this.xPosition+this.width/2, this.yPosition+(this.height/2)-3, 0xA1E9AE);
		}
	}
	
}

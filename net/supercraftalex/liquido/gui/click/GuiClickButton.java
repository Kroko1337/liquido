package net.supercraftalex.liquido.gui.click;

import java.awt.Color;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.supercraftalex.liquido.Booleans;
import net.supercraftalex.liquido.Liquido;
import net.supercraftalex.liquido.utils.ColorUtils;

public class GuiClickButton extends GuiButton {
	
	public GuiClickButton(int buttonId, int x, int y, String text) {
		super(buttonId, x, y, text);
	}
	public GuiClickButton(int buttonId, int x, int y, int width, int heigth, String text) {
		super(buttonId, x, y, width, heigth, text);
	}
	
	@Override
	public void drawButton(Minecraft mc, int mouseX, int mouseY) {
		if(this.visible && Booleans.clickui_newmode) {
			this.hovered = (mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height);
			final Color a =  Liquido.INSTANCE.themeManager.theme().clickUiButtons;
			final FontRenderer var4 = mc.fontRendererObj;
			
			Gui.drawRect(this.xPosition-1, this.yPosition-1, this.xPosition+this.width+1, this.yPosition+this.height+1, ColorUtils.rainbowEffect(20L, 1.0F).getRGB());
			Gui.drawRect(this.xPosition, this.yPosition, this.xPosition+this.width, this.yPosition+this.height, Color.BLACK.getRGB());
			
			this.drawCenteredString(var4, this.displayString, this.xPosition+this.width/2, this.yPosition+(this.height/2)-3, 0xA1E9AE);
			/*
			Gui.drawRect(this.xPosition, this.yPosition, this.xPosition+this.width, this.yPosition+this.height, Color.BLACK.getRGB());
			Gui.drawRect(this.xPosition+1, this.yPosition+1, this.xPosition+this.width-1, this.yPosition+this.height-1, a.getRGB());
			this.drawCenteredString(var4, this.displayString, this.xPosition+this.width/2, this.yPosition+(this.height/2)-3, 0xA1E9AE);
			*/
		} else if(this.visible && !Booleans.clickui_newmode) {
            FontRenderer fontrenderer = mc.fontRendererObj;
            mc.getTextureManager().bindTexture(buttonTextures);
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            this.hovered = mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;
            int i = this.getHoverState(this.hovered);
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
            GlStateManager.blendFunc(770, 771);
            this.drawTexturedModalRect(this.xPosition, this.yPosition, 0, 46 + i * 20, this.width / 2, this.height);
            this.drawTexturedModalRect(this.xPosition + this.width / 2, this.yPosition, 200 - this.width / 2, 46 + i * 20, this.width / 2, this.height);
            this.mouseDragged(mc, mouseX, mouseY);
            int j = 14737632;

            if (!this.enabled)
            {
                j = 10526880;
            }
            else if (this.hovered)
            {
                j = 16777120;
            }

            this.drawCenteredString(fontrenderer, this.displayString, this.xPosition + this.width / 2, this.yPosition + (this.height - 8) / 2, j);
		}
	}
	
}
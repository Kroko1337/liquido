package net.supercraftalex.liquido.gui.click.recode.elements.menu;

import java.awt.Color;

import net.minecraft.client.gui.Gui;
import net.supercraftalex.liquido.Booleans;
import net.supercraftalex.liquido.Liquido;
import net.supercraftalex.liquido.config.ClientConfig.ModuleConfig;
import net.supercraftalex.liquido.gui.click.recode.elements.Element;
import net.supercraftalex.liquido.gui.click.recode.elements.ModuleButton;
import net.supercraftalex.liquido.gui.click.recode.util.ColorUtil;
import net.supercraftalex.liquido.gui.click.recode.util.FontUtil;
import net.supercraftalex.liquido.modules.Config;
import net.supercraftalex.liquido.utils.ColorUtils;

/**
 *  Made by HeroCode
 *  it's free to use
 *  but you have to credit me
 *
 *  @author HeroCode
 */
public class ElementCheckBox extends Element {
	/*
	 * Konstrukor
	 */
	
	public ElementCheckBox(ModuleButton iparent, Config iset) {
		parent = iparent;
		set = iset;
		super.setup();
	}

	/*
	 * Rendern des Elements 
	 */
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		Color temp = ColorUtil.getClickGUIColor();
		int color = new Color(temp.getRed(), temp.getGreen(), temp.getBlue(), 200).getRGB();
		
		/*
		 * Die Box und Umrandung rendern
		 */
		Gui.drawRect((int)x, (int)y, (int)(x + width), (int)(y + height), 0xff1a1a1a);
		
		if(new Boolean(set.getValue().toString())) {
			Gui.drawRect((int)(x + 1), (int)(y + 2), (int)(x + 12), (int)(y + 13), color);
		}
		else {
			Gui.drawRect((int)(x + 1), (int)(y + 2), (int)(x + 12), (int)(y + 13), 0x7d7d7d);
		}
//0x636060
		/*
		 * Titel und Checkbox rendern.
		 */
		FontUtil.drawString(setstrg, x + width - FontUtil.getStringWidth(setstrg), y + FontUtil.getFontHeight() / 2 - 0.5, 0xffffffff);
		if (isCheckHovered(mouseX, mouseY))
			Gui.drawRect((int)(x + 1),(int)(y + 2), (int)(x + 12), (int)(y + 13), 0x55111111);
	}

	/*
	 * 'true' oder 'false' bedeutet hat der Nutzer damit interagiert und
	 * sollen alle anderen Versuche der Interaktion abgebrochen werden?
	 */
	public boolean mouseClicked(int mouseX, int mouseY, int mouseButton) {
		if (mouseButton == 0 && isCheckHovered(mouseX, mouseY)) {
			Liquido.INSTANCE.moduleManager.getModuleByName(parent.mod.getName()).getConfigByName(setstrg).setValue(!(new Boolean(set.getValue().toString())));
			return true;
		}
		
		return super.mouseClicked(mouseX, mouseY, mouseButton);
	}

	/*
	 * Einfacher HoverCheck, benötigt damit die Value geändert werden kann
	 */
	public boolean isCheckHovered(int mouseX, int mouseY) {
		return mouseX >= x + 1 && mouseX <= x + 12 && mouseY >= y + 2 && mouseY <= y + 13;
	}
}

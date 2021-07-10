package net.supercraftalex.liquido.gui.click.recode.elements.menu;

import java.awt.Color;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.supercraftalex.liquido.Liquido;
import net.supercraftalex.liquido.gui.click.recode.elements.Element;
import net.supercraftalex.liquido.gui.click.recode.elements.ModuleButton;
import net.supercraftalex.liquido.gui.click.recode.util.ColorUtil;
import net.supercraftalex.liquido.gui.click.recode.util.FontUtil;
import net.supercraftalex.liquido.modules.Config;
import net.supercraftalex.liquido.modules.ConfigMode;

/**
 *  Made by HeroCode
 *  it's free to use
 *  but you have to credit me
 *
 *  @author HeroCode
 */
public class ElementComboBox extends Element {
	/*
	 * Konstrukor
	 */
	ConfigMode cm;
	
	public ElementComboBox(ModuleButton iparent, Config iset) {
		parent = iparent;
		set = iset;
		cm = iset.getConfigMode();
		super.setup();
	}

	/*
	 * Rendern des Elements
	 */
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		Color temp = ColorUtil.getClickGUIColor();
		int color = new Color(temp.getRed(), temp.getGreen(), temp.getBlue(), 150).getRGB();
		
		/*
		 * Die Box und Umrandung rendern
		 */
		Gui.drawRect(x, y, x + width, y + height, 0xff1a1a1a);

		FontUtil.drawTotalCenteredString(setstrg, x + width / 2, y + 15/2, 0xffffffff);
		int clr1 = color;
		int clr2 = temp.getRGB();

		Gui.drawRect(x, y + 14, x + width, y + 15, 0x77000000);
		if (comboextended) {
			Gui.drawRect(x, y + 15, x + width, y + height, 0xaa121212);
			double ay = y + 15;
			for (String sld : cm.getAviable()) {
				String elementtitle = sld.substring(0, 1).toUpperCase() + sld.substring(1, sld.length());
				FontUtil.drawCenteredString(elementtitle, x + width / 2, ay + 2, 0xffffffff);
				
				/*
				 * Ist das Element ausgewählt, wenn ja dann markiere
				 * das Element in der ComboBox
				 */
				if (sld.equalsIgnoreCase(cm.getValue())) {
					Gui.drawRect(x, ay, x + 1.5, ay + FontUtil.getFontHeight() + 2, clr1);
				}
				/*
				 * Wie bei mouseClicked 'is hovered', wenn ja dann markiere
				 * das Element in der ComboBox
				 */
				if (mouseX >= x && mouseX <= x + width && mouseY >= ay && mouseY < ay + FontUtil.getFontHeight() + 2) {
					Gui.drawRect(x + width - 1.2, ay, x + width, ay + FontUtil.getFontHeight() + 2, clr2);
				}
				ay += FontUtil.getFontHeight() + 2;
			}
		}
	}

	/*
	 * 'true' oder 'false' bedeutet hat der Nutzer damit interagiert und
	 * sollen alle anderen Versuche der Interaktion abgebrochen werden?
	 */
	public boolean mouseClicked(int mouseX, int mouseY, int mouseButton) {
		if (mouseButton == 0) {
			if (isButtonHovered(mouseX, mouseY)) {
				comboextended = !comboextended;
				return true;
			}
			
			/*
			 * Also wenn die Box ausgefahren ist, dann wird für jede mögliche Options
			 * überprüft, ob die Maus auf diese zeigt, wenn ja dann global jeder weitere 
			 * call an mouseClicked gestoppt und die Values werden aktualisiert
			 */
			if (!comboextended)return false;
			double ay = y + 15;
			for (String slcd : cm.getAviable()) {
				if (mouseX >= x && mouseX <= x + width && mouseY >= ay && mouseY <= ay + FontUtil.getFontHeight() + 2) {	
					Liquido.INSTANCE.moduleManager.getModuleByName(parent.mod.getName()).getConfigByName(setstrg).getConfigMode().setValue(slcd);
					Liquido.INSTANCE.moduleManager.getModuleByName(parent.mod.getName()).getConfigByName(setstrg).getConfigMode().getValue();
					return true;
				}
				ay += FontUtil.getFontHeight() + 2;
			}
		}

		return super.mouseClicked(mouseX, mouseY, mouseButton);
	}

	/*
	 * Einfacher HoverCheck, benötigt damit die Combobox geöffnet und geschlossen werden kann
	 */
	public boolean isButtonHovered(int mouseX, int mouseY) {
		return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + 15;
	}
}

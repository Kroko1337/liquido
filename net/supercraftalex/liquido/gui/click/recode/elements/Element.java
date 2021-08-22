package net.supercraftalex.liquido.gui.click.recode.elements;

import net.supercraftalex.liquido.Liquido;
import net.supercraftalex.liquido.gui.click.recode.ClickGUI;
import net.supercraftalex.liquido.gui.click.recode.util.FontUtil;
import net.supercraftalex.liquido.modules.Config;

/**
 *  Made by HeroCode
 *  it's free to use
 *  but you have to credit me
 *
 *  @author HeroCode
 */
public class Element {
	public ClickGUI clickgui;
	public ModuleButton parent;
	public Config set;
	public double offset;
	public double x;
	public double y;
	public double width;
	public double height;
	
	public String setstrg;
	
	public boolean comboextended;
	
	public void setup(){
		clickgui = Liquido.INSTANCE.clickgui;
	}
	
	public void update(){
		/*
		 * Richtig positionieren! Offset wird von ClickGUI aus bestimmt, sodass
		 * nichts ineinander gerendert wird
		 */
		x = parent.x + parent.width + 2;
		y = parent.y + offset;
		width = parent.width + 10;
		height = 15;
		
		/*
		 * Title der Box bestimmen und falls n�tig die Breite der CheckBox
		 * erweitern
		 */
		String sname = set.getName();
		if(set.getValue() instanceof Boolean){
			setstrg = sname.substring(0, 1).toUpperCase() + sname.substring(1, sname.length());
			double textx = x + width - FontUtil.getStringWidth(setstrg);
			if (textx < x + 13) {
				width += (x + 13) - textx + 1;
			}
		}else if(set.isConfigMode){
			height = comboextended ? set.getConfigMode().getAviable().size() * (FontUtil.getFontHeight() + 2) + 15 : 15;
			
			setstrg = sname.substring(0, 1).toUpperCase() + sname.substring(1, sname.length());
			int longest = FontUtil.getStringWidth(setstrg);
			for (String s : set.getConfigMode().getAviable()) {
				int temp = FontUtil.getStringWidth(s);
				if (temp > longest) {
					longest = temp;
				}
			}
			double textx = x + width - longest;
			if (textx < x) {
				width += x - textx + 1;
			}
		}else if(set.isDouble){
			setstrg = sname.substring(0, 1).toUpperCase() + sname.substring(1, sname.length());
			String displayval = "" + Math.round(set.doubleValue * 100D)/ 100D;
			String displaymax = "" + Math.round(set.doubleMax * 100D)/ 100D;
			double textx = x + width - FontUtil.getStringWidth(setstrg) - FontUtil.getStringWidth(displaymax) - 4;
			if (textx < x) {
				width += x - textx + 1;
			}
		}
	}

	public void drawScreen(int mouseX, int mouseY, float partialTicks) {}
	
	public boolean mouseClicked(int mouseX, int mouseY, int mouseButton) {
		return isHovered(mouseX, mouseY);
	}

	public void mouseReleased(int mouseX, int mouseY, int state) {}
	
	public boolean isHovered(int mouseX, int mouseY) 
	{
		
		return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
	}
}
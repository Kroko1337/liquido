package net.supercraftalex.liquido.gui.click.recode.util;

import java.awt.Color;

import net.supercraftalex.liquido.Booleans;
import net.supercraftalex.liquido.utils.ColorUtils;

/**
 *  Made by HeroCode
 *  it's free to use
 *  but you have to credit me
 *
 *  @author HeroCode
 */
public class ColorUtil {
	
	public static Color getClickGUIColor(){
		if(Booleans.ClickUiRainbow) {
			return ColorUtils.rainbowEffect(200000000L, 1.0F);
		}
		return new Color((int)Booleans.ClickUiRed, (int)Booleans.ClickUiGreen, (int)Booleans.ClickUiBlue);
	}
}

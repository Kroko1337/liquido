package net.supercraftalex.liquido.utils;

import java.awt.Color;
import java.util.Random;

public class ColorUtils {
	
	public static Color rainbowEffect(long offset, float fade) {
		float hue = (float)(System.nanoTime()+offset) / 1.0E10F % 1.0F;
		long color = Long.parseLong(Integer.toHexString(Integer.valueOf(Color.HSBtoRGB(hue, 1.0F, 1.0F)).intValue()),16);
		Color c = new Color((int)color);
		return new Color(c.getRed() / 255.0F * fade, c.getGreen() / 255.0F * fade, c.getBlue() / 255.0F * fade, c.getAlpha() / 255.0F);
	}
	
	public static Color randomColor() {
		Random rand = new Random();
	    // Java 'Color' class takes 3 floats, from 0 to 1.
	    float r = rand.nextFloat();
	    float g = rand.nextFloat();
	    float b = rand.nextFloat();
	    return new Color(r, g, b);
	}
	
	public static Color editColor(Color c,float rn,float gn,float bn) {
	    float r = c.getRed()+rn;
	    float g = c.getGreen()+gn;
	    float b = c.getBlue()+bn;
	    return new Color(r, g, b);
	}
	
}

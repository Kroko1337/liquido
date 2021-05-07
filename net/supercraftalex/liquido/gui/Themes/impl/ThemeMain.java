package net.supercraftalex.liquido.gui.Themes.impl;

import java.awt.Color;

import net.supercraftalex.liquido.gui.Themes.UITheme;

public class ThemeMain extends UITheme{
	
	static Color m = new Color(Color.GRAY.getRed(),Color.GRAY.getGreen(),Color.GRAY.getBlue(),60);
	static Color m2 = new Color(Color.GRAY.getRed(),Color.GRAY.getGreen(),Color.GRAY.getBlue(),80);
	static Color m3 = new Color(Color.GRAY.getRed(),Color.GRAY.getGreen(),Color.GRAY.getBlue(),100);
	static Color m4 = Color.BLUE;
	static Color m5 = Color.BLACK;

	public ThemeMain() {
		super("Main", "Alex", m, m2, m3, m4, m5, Color.WHITE, Color.WHITE, Color.GRAY, Color.LIGHT_GRAY, m5);
		this.particlesEnabled = true;
	}

}

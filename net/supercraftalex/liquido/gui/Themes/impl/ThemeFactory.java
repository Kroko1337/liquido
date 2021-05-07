package net.supercraftalex.liquido.gui.Themes.impl;

import java.awt.Color;

import net.supercraftalex.liquido.gui.Themes.UITheme;

public class ThemeFactory  extends UITheme{
	
	static Color m = new Color(Color.GRAY.getRed(),Color.GRAY.getGreen(),Color.GRAY.getBlue(),60);
	static Color m2 = new Color(Color.GRAY.getRed(),Color.GRAY.getGreen(),Color.GRAY.getBlue(),80);
	static Color m3 = new Color(Color.GRAY.getRed(),Color.GRAY.getGreen(),Color.GRAY.getBlue(),100);
	static Color m4 = Color.BLUE;
	static Color m5 = Color.BLACK;

	public ThemeFactory() {
		super("Factory", "Alex", m, m2, m3, m4, m5, Color.WHITE, Color.WHITE, Color.GRAY, Color.LIGHT_GRAY, m5);
		this.particlesEnabled = false;
		this.MainMenuShaderEnabled = true;
		this.MainMenuShader = "good.fsh";
		this.ShaderSpeed = 2000f;
	}

}

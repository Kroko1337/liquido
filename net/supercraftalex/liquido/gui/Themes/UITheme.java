package net.supercraftalex.liquido.gui.Themes;

import java.awt.Color;

public class UITheme {
	
	public String name;
	public String author;
	public Color main;
	public Color main2;
	public Color main3;
	public Color main4;
	public Color main5;
	public Color title;
	public Color mainMenuParticles;
	public Color mainMenuButtons;
	public Color clickUiButtons;
	public Color clickUiTextColor;
	public boolean MainMenuShaderEnabled = false;
	public String MainMenuShader = "good.fsh";
	public boolean particlesEnabled = false;
	public boolean showLegitButton = false;
	public float ShaderSpeed = 5000f;
	
	public UITheme(String name, String author, Color m, Color m2, Color m3, Color m4, Color m5, Color title, Color mainMenuParticles, Color mainMenuButtons, Color clickUiButtons, Color clickUiTextColor) {
		this.author = author;
		this.name = name;
		this.main = m;
		this.main2 = m2;
		this.main3 = m3;
		this.main4 = m4;
		this.main5 = m5;
		this.title = title;
		this.mainMenuParticles = mainMenuParticles;
		this.mainMenuButtons = mainMenuButtons;
		this.clickUiButtons = clickUiButtons;
		this.clickUiTextColor = clickUiTextColor;
	}
	
}
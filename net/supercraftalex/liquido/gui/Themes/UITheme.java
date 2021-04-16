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
	
	/** Creates an UITheme
 	* @param name The theme name
 	* @param author The author of the Theme
	* @param m1 The main colors (Default theme: Grey, Blue..)
	* @param title The color of the title in the main menu
	* @param clickUiTextColor The color of the Strings in the ClickUI
	* @param clickUiButtons The color of the buttons in the second clickUI mode
	* @param mainMenuParticles The color of the particles in main menu
	* @param mainMenuButtons The color of the Buttons in main menu and co
	*/
	public UITheme(String name, String author; Color m, Color m2, Color m3, Color m4, Color m5, Color title, Color mainMenuParticles, Color mainMenuButtons, Color clickUiButtons, Color clickUiTextColor) {
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

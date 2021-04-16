package net.supercraftalex.liquido.gui.Themes;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import net.supercraftalex.liquido.gui.Themes.impl.*;

public class ThemeManager {
	
	private List<UITheme> themes = new ArrayList<UITheme>();
	private UITheme selected;
	
	public ThemeManager() {
		themes.add(new ThemeMain());
		
		selected = themes.get(0);
	}
	public UITheme theme() {
		return selected;
	}
	
}

package net.supercraftalex.liquido.gui.Themes;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import net.supercraftalex.liquido.gui.Themes.impl.*;

public class ThemeManager {
	
	private List<UITheme> themes = new ArrayList<UITheme>();
	private UITheme selected;
	
	public ThemeManager() {
		themes.add(new ThemeFactory());
		themes.add(new ThemeMain());
		themes.add(new ThemeMountains());
		
		selected = themes.get(1);
	}
	public UITheme theme() {
		return selected;
	}
	public boolean select(String s) {
		for(UITheme t : themes) {
			if(t.name.equalsIgnoreCase(s)) {
				this.selected = t;
				return true;
			}
		}
		return false;
	}
	public List<UITheme> getThemes() {
		return this.themes;
	}
	
}

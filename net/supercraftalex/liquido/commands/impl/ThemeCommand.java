package net.supercraftalex.liquido.commands.impl;

import net.supercraftalex.liquido.ErrorManager;
import net.supercraftalex.liquido.Liquido;
import net.supercraftalex.liquido.commands.Command;
import net.supercraftalex.liquido.gui.Themes.UITheme;

public class ThemeCommand extends Command {
	
	public ThemeCommand() {
		super("theme", "Change theme");
	}

	@Override
	public void execute(String[] args) {
		if(args.length < 1) {
			messageWithoutPrefix("&f--THEME LIST--");
			for(UITheme t : Liquido.INSTANCE.themeManager.getThemes()) {
				messageWithoutPrefix("--"+t.name+"--");
			}
		}
		if(args.length == 1) {
			if(Liquido.INSTANCE.themeManager.select(args[0])) {
				messageWithPrefix("Theme "+args[0]+" loaded!");
			}
			else {
				messageWithPrefix("Theme not found!");
			}
		}
	}
	
}

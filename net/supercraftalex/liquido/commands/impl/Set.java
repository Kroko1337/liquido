package net.supercraftalex.liquido.commands.impl;

import net.supercraftalex.liquido.Booleans;
import net.supercraftalex.liquido.ErrorManager;
import net.supercraftalex.liquido.Liquido;
import net.supercraftalex.liquido.commands.Command;

public class Set extends Command {
	
	public Set() {
		super("set", "Sets a value");
	}

	@Override
	public void execute(String[] args) {
		if(args.length < 3) {return;}
		try {
			if(Liquido.INSTANCE.moduleManager.getModuleByName(args[0]).getConfigByName(args[1]).getValue() instanceof Boolean) {
				if(args[2] == "true") {
					try {
						Liquido.INSTANCE.moduleManager.getModuleByName(args[0]).getConfigByName(args[1]).setValue(new Boolean(true));
					} catch (Exception e) {ErrorManager.addException(e);}
				}
				if(args[2] == "false") {
					try {
						Liquido.INSTANCE.moduleManager.getModuleByName(args[0]).getConfigByName(args[1]).setValue(new Boolean(false));
					} catch (Exception e) {ErrorManager.addException(e);}
				}
			}
			else {
				try {
					Liquido.INSTANCE.moduleManager.getModuleByName(args[0]).getConfigByName(args[1]).setValue(Double.parseDouble(args[2]));
				} catch (Exception e) {ErrorManager.addException(e);}
			}
		} catch (Exception e) {
			ErrorManager.addException(e);
		}
		messageWithPrefix(" Set "+args[1]+" from module "+args[0]+" to "+args[2]);
	}
	
}

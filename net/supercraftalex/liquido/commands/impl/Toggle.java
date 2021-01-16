package net.supercraftalex.liquido.commands.impl;

import net.supercraftalex.liquido.Liquido;
import net.supercraftalex.liquido.commands.Command;
import net.supercraftalex.liquido.modules.Module;

public class Toggle extends Command {

	public Toggle() {
		super("toggle", "Toggles a module!");
	}

	@Override
	public void execute(String[] args) {
		if(args.length != 1) {
			messageWithPrefix(" §7toggle §2<Module §f");
			return;
		}
		String module = args[0];
		if(module.equalsIgnoreCase("usual")) {
			for(Module m : Liquido.INSTANCE.moduleManager.getModules()) {
				if(m.isEnabled()) {
					
				}
			}
			messageWithPrefix("Enabled toggle setup");
		}
		try {
			Liquido.INSTANCE.moduleManager.getModuleByName(module).toggle();
			messageWithPrefix(" §f"+Liquido.INSTANCE.moduleManager.getModuleByName(module).getDisplayname() + " §fwas "+(Liquido.INSTANCE.moduleManager.getModuleByName(module).isEnabled() ? "§aenabled" : "§cdisabled"));
		} catch (Exception e) {
			messageWithPrefix("§cModule not found!");
		}
	}

}

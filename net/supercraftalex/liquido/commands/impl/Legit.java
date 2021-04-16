package net.supercraftalex.liquido.commands.impl;

import net.supercraftalex.liquido.Booleans;
import net.supercraftalex.liquido.Liquido;
import net.supercraftalex.liquido.commands.Command;
import net.supercraftalex.liquido.modules.Module;

public class Legit extends Command {

	public Legit() {
		super("legit", "Toggles the cheat mode!");
	}

	@Override
	public void execute(String[] args) {
		Booleans.hacking_enabled = !Booleans.hacking_enabled;
		messageWithPrefix(" Hacking mode is now set to §a" + Booleans.hacking_enabled);
	}

}

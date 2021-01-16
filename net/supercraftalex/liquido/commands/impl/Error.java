package net.supercraftalex.liquido.commands.impl;

import net.supercraftalex.liquido.ErrorManager;
import net.supercraftalex.liquido.Liquido;
import net.supercraftalex.liquido.commands.Command;

public class Error extends Command {
	
	public Error() {
		super("error", "Get´s the errors.");
	}

	@Override
	public void execute(String[] args) {
		messageWithPrefix("§f "+ErrorManager.getLatestError());
		messageWithPrefix("§f "+ErrorManager.getLatestException().getMessage());
	}
	
}
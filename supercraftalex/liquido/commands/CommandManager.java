package net.supercraftalex.liquido.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.supercraftalex.liquido.Liquido;
import net.supercraftalex.liquido.commands.impl.*;

public class CommandManager {
	
	private List<Command> commands = new ArrayList();
	
	public static String Command_Prefix = ".";
	
	public CommandManager() {
		addCommand(new Toggle());
		addCommand(new Legit());
		addCommand(new Set());
	}
	
	public void addCommand(Command cmd) {
		this.commands.add(cmd);
		Liquido.INSTANCE.logger.Loading("Commands: " + cmd.getName());
	}
	
	public boolean execute(String text) {
		if(!text.startsWith(Command_Prefix)) {
			return false;
		}
		text = text.substring(1);
		
		String[] arguments = text.split(" ");
		
		for(Command cmd : this.commands) {
			if(cmd.getName().equalsIgnoreCase(arguments[0])) {
				String[] args = (String[]) Arrays.copyOfRange(arguments, 1, arguments.length);
				cmd.execute(args);
				return true;
			}
		}
		
		return false;
	}
	
}

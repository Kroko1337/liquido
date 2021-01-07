package net.supercraftalex.liquido.commands.impl;

import net.supercraftalex.liquido.Booleans;
import net.supercraftalex.liquido.commands.Command;

public class Set extends Command {
	
	public Set() {
		super("set", "Sets a value");
	}

	@Override
	public void execute(String[] args) {
		if(args.length < 2) {return;}
		switch(args[0]) {
			case "list":
				messageWithoutPrefix("flyspeed, speed, bhopheight, auradelay");
				break;
			case "flyspeed":
				Booleans.FlySpeed = Double.parseDouble(args[1]);
				break;
			case "speed":
				Booleans.SpeedSpeed = Double.parseDouble(args[1]);
				break;
			case "bhopheight":
				Booleans.BhopHeight = Double.parseDouble(args[1]);
				break;
			case "auradelay":
				Booleans.KillAura_delay = Integer.parseInt(args[1]);
				break;
			default:
				break;
		}
	}
	
}

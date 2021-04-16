package net.supercraftalex.liquido.commands.impl;

import java.util.Properties;

import org.lwjgl.input.Keyboard;

import net.supercraftalex.liquido.Booleans;
import net.supercraftalex.liquido.ErrorManager;
import net.supercraftalex.liquido.Liquido;
import net.supercraftalex.liquido.commands.Command;
import net.supercraftalex.liquido.modules.Module;
import net.supercraftalex.liquido.utils.FileUtil;

public class Bind extends Command {

	public Bind() {
		super("bind", "Binds a module to a key.");
	}

	@Override
	public void execute(String[] args) {
		if(args.length == 2) {
			int nk = Keyboard.KEY_NONE;
			if(args[1].equalsIgnoreCase("null") || args[1].equalsIgnoreCase("none")) {nk = Keyboard.KEY_NONE;}
			if(args[1].equalsIgnoreCase("a")) {nk = Keyboard.KEY_A;}
			if(args[1].equalsIgnoreCase("b")) {nk = Keyboard.KEY_B;}
			if(args[1].equalsIgnoreCase("c")) {nk = Keyboard.KEY_C;}
			if(args[1].equalsIgnoreCase("d")) {nk = Keyboard.KEY_D;}
			if(args[1].equalsIgnoreCase("e")) {nk = Keyboard.KEY_E;}
			if(args[1].equalsIgnoreCase("f")) {nk = Keyboard.KEY_F;}
			if(args[1].equalsIgnoreCase("g")) {nk = Keyboard.KEY_G;}
			if(args[1].equalsIgnoreCase("h")) {nk = Keyboard.KEY_H;}
			if(args[1].equalsIgnoreCase("i")) {nk = Keyboard.KEY_I;}
			if(args[1].equalsIgnoreCase("j")) {nk = Keyboard.KEY_J;}
			if(args[1].equalsIgnoreCase("k")) {nk = Keyboard.KEY_K;}
			if(args[1].equalsIgnoreCase("l")) {nk = Keyboard.KEY_L;}
			if(args[1].equalsIgnoreCase("m")) {nk = Keyboard.KEY_M;}
			if(args[1].equalsIgnoreCase("n")) {nk = Keyboard.KEY_N;}
			if(args[1].equalsIgnoreCase("o")) {nk = Keyboard.KEY_O;}
			if(args[1].equalsIgnoreCase("p")) {nk = Keyboard.KEY_P;}
			if(args[1].equalsIgnoreCase("q")) {nk = Keyboard.KEY_Q;}
			if(args[1].equalsIgnoreCase("r")) {nk = Keyboard.KEY_R;}
			if(args[1].equalsIgnoreCase("s")) {nk = Keyboard.KEY_S;}
			if(args[1].equalsIgnoreCase("t")) {nk = Keyboard.KEY_T;}
			if(args[1].equalsIgnoreCase("u")) {nk = Keyboard.KEY_U;}
			if(args[1].equalsIgnoreCase("v")) {nk = Keyboard.KEY_V;}
			if(args[1].equalsIgnoreCase("w")) {nk = Keyboard.KEY_W;}
			if(args[1].equalsIgnoreCase("x")) {nk = Keyboard.KEY_X;}
			if(args[1].equalsIgnoreCase("y")) {nk = Keyboard.KEY_Y;}
			if(args[1].equalsIgnoreCase("z")) {nk = Keyboard.KEY_Z;}
			if(Liquido.INSTANCE.moduleManager.getModuleByName(args[0]) == null) {
				messageWithPrefix("§cModule not found!");
			}
			else {
				Liquido.INSTANCE.moduleManager.getModuleByName(args[0]).setKeyBind(nk);
				saveBindings();
				this.messageWithPrefix("§aBounded Module §f" + args[0] + "§a to key §f" + args[1] + "§a!");
			}
		} else {
			this.messageWithPrefix("§cWrong usage!");
			this.messageWithPrefix("§cUsage: .bind [module] [key | null]!");
		}
	}
	
	public static void loadBindings() {
		if(!FileUtil.existsFiles(FileUtil.baseDir, "binds.cfg")) {saveBindings();}
		Properties p = FileUtil.readPropertyFile(FileUtil.baseDir, "binds.cfg");
		if(p.getProperty("version") != Liquido.INSTANCE.VERSION) {
			System.out.println("Bind file version dont match the running Liqudio Version! This can be a problem!");
			ErrorManager.addError("Bind file version dont match the running Liqudio Version!");
		}
		for(Module m : Liquido.INSTANCE.moduleManager.getModules()) {
			try {
				m.setKeyBind(Integer.parseInt(p.getProperty(m.getName()+".bind")));
			} catch (Exception e) {
				ErrorManager.addException(e);
			}
		}
	}
	private static void saveBindings() {
		String text = "version="+Liquido.INSTANCE.VERSION;
		for(Module m : Liquido.INSTANCE.moduleManager.getModules()) {
			text = text + "\n" + m.getName() + ".bind=" + m.getKeyBind();
		}
		FileUtil.writeFile(FileUtil.baseDir, "binds.cfg", text);
	}

}
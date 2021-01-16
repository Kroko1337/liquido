package net.supercraftalex.liquido.gui.extra;

import javax.swing.WindowConstants;

import net.supercraftalex.liquido.ErrorManager;
import net.supercraftalex.liquido.modules.Module;

public class GuiExtraMain {
	
	public static void initGUI() {
		GuiExtraOmain frame = new GuiExtraOmain();
		frame.setVisible(true);
		frame.setTitle("LiqudioClient > Module configs");
		frame.setResizable(false);
	} 
	
	public static void initConfigGUI(Module m) {
		try {
			new GuiExtraOconfig(m).setVisible(true);
		} catch(Exception err) {
			ErrorManager.addException(err);
		}
	}
	
}

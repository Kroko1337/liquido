package net.supercraftalex.liquido.modules;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.renderer.ChestRenderer;
import net.supercraftalex.liquido.Liquido;
import net.supercraftalex.liquido.modules.impl.Combat.Aimbot;
import net.supercraftalex.liquido.modules.impl.Combat.BowAimbot;
import net.supercraftalex.liquido.modules.impl.Combat.KillAura;
import net.supercraftalex.liquido.modules.impl.Exploits.AntiFire;
import net.supercraftalex.liquido.modules.impl.Movement.*;
import net.supercraftalex.liquido.modules.impl.Player.AutoEat;
import net.supercraftalex.liquido.modules.impl.Player.AutoRespawn;
import net.supercraftalex.liquido.modules.impl.Player.ChestStealer;
import net.supercraftalex.liquido.modules.impl.Player.InventorySorter;
import net.supercraftalex.liquido.modules.impl.Player.NoFall;
import net.supercraftalex.liquido.modules.impl.Render.*;

public class ModuleManager {
	
	public List<Module> modules = new ArrayList<Module>();
	
	public ModuleManager() {
		
		addModule(new Step());
		addModule(new Sprint());
		addModule(new Fullbright());
		addModule(new InventorySorter());
		addModule(new Fly());
		addModule(new NoFall());
		addModule(new FreeCam());
		addModule(new AutoRespawn());
		addModule(new Speed());
		addModule(new AntiFire());
		addModule(new Aimbot());
		addModule(new ChestStealer());
		addModule(new KillAura());
		addModule(new Jesus());
		addModule(new BowAimbot());
		addModule(new Scaffold());
		addModule(new AutoEat());
		
		int loop = 1;
		for(Module m : modules) {
			m.setId(loop);
			loop++;
		}
		
		Liquido.logger.Info("Loaded " + modules.size() + " Modules!");
		
	}
	
	public void addModule(Module module) {
		
		this.modules.add(module);
		Liquido.logger.Loading("Module: "+module.getDisplayname() +"("+module.getName()+")");
		
	}
	
	public List<Module> getModules() {
		return modules;
	}
	
	public Module getModuleByName(String moduleName) {
		for(Module mod : modules) {
			if((mod.getName().trim().equalsIgnoreCase(moduleName)) || (mod.toString().trim().equalsIgnoreCase(moduleName.trim()))) {
				return mod;
			}
		}
		return null;
	}
	
	public Module getModule(Class <? extends Module> clazz ) {
		for(Module m : modules) {
			if(m.getClass() == clazz) {
				return m;
			}
		}
		return null;
	}
	
}

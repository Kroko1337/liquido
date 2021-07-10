package net.supercraftalex.liquido.modules;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ChestRenderer;
import net.supercraftalex.liquido.Liquido;
import net.supercraftalex.liquido.modules.impl.TestModule;
import net.supercraftalex.liquido.modules.impl.Combat.*;
import net.supercraftalex.liquido.modules.impl.Exploits.*;
import net.supercraftalex.liquido.modules.impl.Movement.*;
import net.supercraftalex.liquido.modules.impl.Player.*;
import net.supercraftalex.liquido.modules.impl.Render.*;
import net.supercraftalex.liquido.modules.impl.options.OptionClickUI;

public class ModuleManager {
	
	public List<Module> modules = new ArrayList<Module>();
	
	private void addModules() {
		addModule(new Step());
		addModule(new Sprint());
		addModule(new Fullbright());
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
		addModule(new Scaffold2());
		addModule(new AutoEat());
		addModule(new FastPlace());
		addModule(new AntiAfk());
		addModule(new InstantBreak());
		addModule(new Velocity());
		addModule(new AntiVoid());
		addModule(new Spammer());
		addModule(new KillInsults());
		addModule(new AutoArmor());
		addModule(new InvCleaner());
		addModule(new TestModule());
		addModule(new BackBug());
		addModule(new ESP());
		addModule(new BackTrack());
		addModule(new LongJump());
		addModule(new ObstacleSpeed());
		addModule(new TargetStrafe());
		addModule(new Criticals());
		addModule(new AutoClicker());
		
		//Options
		addModule(new OptionClickUI());
		
		//Exploits
		addModule(new NumberChat());
	}
	
	public ModuleManager() {
		
		addModules();
		
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
	
	public void reSetup() {
		modules.clear();
		addModules();
		
		int loop = 1;
		for(Module m : modules) {
			m.setId(loop);
			m.toggled = false;
			m.onDisable();
			loop++;
		}
		Minecraft.getMinecraft().refreshResources();
		Liquido.logger.Info("Loaded " + modules.size() + " Modules!");
	}
	
}

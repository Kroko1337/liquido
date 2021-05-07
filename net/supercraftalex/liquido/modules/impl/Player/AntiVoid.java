package net.supercraftalex.liquido.modules.impl.Player;

import org.lwjgl.input.Keyboard;

import com.darkmagician6.eventapi.EventManager;
import com.darkmagician6.eventapi.EventTarget;

import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.supercraftalex.liquido.Booleans;
import net.supercraftalex.liquido.Liquido;
import net.supercraftalex.liquido.events.EventUpdate;
import net.supercraftalex.liquido.modules.Category;
import net.supercraftalex.liquido.modules.Config;
import net.supercraftalex.liquido.modules.ConfigMode;
import net.supercraftalex.liquido.modules.Module;

public class AntiVoid extends Module {
	
	int not_fly_length = 0;
	boolean AntiModeSwitch = false;
	
	public AntiVoid() {
		super("antivoid", "AntiVoid", Keyboard.KEY_NONE, Category.PLAYER);
		addConfig(new Config("mode", true, new ConfigMode()));
		getConfigByName("mode").getConfigMode().addMode("anti");
		getConfigByName("mode").getConfigMode().addMode("slow");
	}

	@EventTarget
	public void onUpdate(EventUpdate event) {
		if(!Booleans.hacking_enabled) {return;}
		if(getConfigByName("mode").getConfigMode().getValue().equalsIgnoreCase("slow")) {
			if(getDistanceToGround() <= 20) {
				not_fly_length = 0;
				if(Liquido.INSTANCE.moduleManager.getModuleByName("fly").toggled) {
					Liquido.INSTANCE.moduleManager.getModuleByName("fly").toggle();
				}
			}
			if(not_fly_length >= 10) {
				not_fly_length=0;
				Liquido.INSTANCE.moduleManager.getModuleByName("fly").toggle();
			}
			if(not_fly_length == 2) {
					Liquido.INSTANCE.moduleManager.getModuleByName("fly").toggle();
			}
			not_fly_length++;
		}
		if(getConfigByName("mode").getConfigMode().getValue().equalsIgnoreCase("anti")) {
			if(getDistanceToGround() == 256) {
				if(AntiModeSwitch == false) {
					mc.thePlayer.motionY = -0.3;
				}
				else {
					mc.thePlayer.motionY = 0.3;
				}
				AntiModeSwitch = !AntiModeSwitch;
			}
		}
	}
	
	@Override
	public void onDisable() {
		EventManager.unregister(this);
		if(getConfigByName("mode").getConfigMode().getValue().equalsIgnoreCase("slow")) {
			if(Liquido.INSTANCE.moduleManager.getModuleByName("fly").toggled) {
				Liquido.INSTANCE.moduleManager.getModuleByName("fly").toggle();
			}
		}
	}
	
	@Override
	public void onEnable() {
		not_fly_length = 0;
		AntiModeSwitch = false;
		if(getConfigByName("mode").getConfigMode().getValue().equalsIgnoreCase("slow")) {
			if(Liquido.INSTANCE.moduleManager.getModuleByName("fly").toggled) {}
			else {
				Liquido.INSTANCE.moduleManager.getModuleByName("fly").toggle();
			}
		}
		EventManager.register(this);
	}
	
    public double getDistanceToGround() {
        double d = 0.0D;
        for (int i = 0; i < 256; i++) {
            BlockPos localBlockPos = new BlockPos(this.mc.thePlayer.posX, this.mc.thePlayer.posY - i, this.mc.thePlayer.posZ);
            if ((this.mc.theWorld.getBlockState(localBlockPos).getBlock() != Blocks.air) && (this.mc.theWorld.getBlockState(localBlockPos).getBlock() != Blocks.grass) && (this.mc.theWorld.getBlockState(localBlockPos).getBlock() != Blocks.tallgrass) && (this.mc.theWorld.getBlockState(localBlockPos).getBlock() != Blocks.red_flower) && (this.mc.theWorld.getBlockState(localBlockPos).getBlock() != Blocks.yellow_flower)) {
                d = this.mc.thePlayer.posY - localBlockPos.getY();
                return d - 1.0D;
            }
        }
        return 256.0D;
    }
	
}
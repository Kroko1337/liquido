package net.supercraftalex.liquido.modules.impl.Combat;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.supercraftalex.liquido.Liquido;
import net.supercraftalex.liquido.commands.Command;
import net.supercraftalex.liquido.events.EventUpdate;
import net.supercraftalex.liquido.modules.Category;
import net.supercraftalex.liquido.modules.Config;
import net.supercraftalex.liquido.modules.ConfigMode;
import net.supercraftalex.liquido.modules.Module;
import net.supercraftalex.liquido.utils.MovementUtil;
import net.supercraftalex.liquido.utils.OnlineRotation;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import com.darkmagician6.eventapi.EventManager;
import com.darkmagician6.eventapi.EventTarget;

import java.awt.*;

public class TargetStrafe extends Module {

    public TargetStrafe() {
        super("strafe", "TargetStrafe", 0, Category.COMBAT);
		addConfig(new Config("mode", true, new ConfigMode()));
		getConfigByName("mode").getConfigMode().addMode("Better");
		getConfigByName("mode").getConfigMode().addMode("Skid");
    }
    
    Boolean BetterFinis = false;
    
    @EventTarget
    public void onUpdate(EventUpdate event) {
    	if(getConfigByName("mode").getConfigMode().getValue().equalsIgnoreCase("skid")) {
            final boolean keyDown = Keyboard.isKeyDown(mc.gameSettings.keyBindRight.getKeyCode());
            final boolean keyDown2 = Keyboard.isKeyDown(mc.gameSettings.keyBindLeft.getKeyCode());
            if (Liquido.INSTANCE.target != null && !keyDown && !keyDown2 && mc.thePlayer.getDistanceToEntity(Liquido.INSTANCE.target)<=new Integer(Liquido.INSTANCE.moduleManager.getModuleByName("killaura").getConfigByName("range").getValue().toString())) {
                float n = OnlineRotation.getYaw((Entity)Liquido.INSTANCE.target, (Entity)mc.thePlayer) + 180.0f;
                if (OnlineRotation.calculateRotationDiff(n + 0.15f, (float)(MathHelper.wrapAngleTo180_double((double)Liquido.INSTANCE.target.rotationYaw) + 180.0))[0] > OnlineRotation.calculateRotationDiff(n - 0.15f, (float)(MathHelper.wrapAngleTo180_double((double)Liquido.INSTANCE.target.rotationYaw) + 180.0))[0]) {
                	mc.gameSettings.keyBindRight.pressed = true;
                	mc.gameSettings.keyBindLeft.pressed = false;
                }
                else {
                	mc.gameSettings.keyBindRight.pressed = false;
                	mc.gameSettings.keyBindLeft.pressed = true;
                }
            }
            else {
            	mc.gameSettings.keyBindRight.pressed = keyDown;
            	mc.gameSettings.keyBindLeft.pressed = keyDown2;
            }
    	}
    	if(getConfigByName("mode").getConfigMode().getValue().equalsIgnoreCase("better")) {
            if (Liquido.INSTANCE.target != null && mc.thePlayer.getDistanceToEntity(Liquido.INSTANCE.target)<=2) {
            	mc.gameSettings.keyBindLeft.pressed = true;
            	mc.gameSettings.keyBindForward.pressed = true;
            	mc.gameSettings.keyBindJump.pressed = true;
            	
            	BetterFinis = true;
            }
            else {
            	if(BetterFinis == true) {
            		BetterFinis = false;
                	mc.gameSettings.keyBindLeft.pressed = false;
                	mc.gameSettings.keyBindForward.pressed = false;
                	mc.gameSettings.keyBindJump.pressed = false;
            	}
            }
    	}
    }

    @Override
    public void onEnable() {
    	EventManager.register(this);
    	if(getConfigByName("mode").getConfigMode().getValue().equalsIgnoreCase("skid")) { 
    		Command.messageWithoutPrefix("§aTargetStrafe §c|§f Skid mode is skidded from a shit client named Koks.");
    	}
    }
    
    @Override
    public void onDisable() {
    	EventManager.unregister(this);
    	mc.gameSettings.keyBindLeft.pressed = false;
    	mc.gameSettings.keyBindForward.pressed = false;
    	mc.gameSettings.keyBindJump.pressed = false;
    }
}

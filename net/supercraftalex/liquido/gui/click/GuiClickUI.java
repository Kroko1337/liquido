package net.supercraftalex.liquido.gui.click;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiOptions;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiShareToLan;
import net.minecraft.client.gui.achievement.GuiAchievements;
import net.minecraft.client.gui.achievement.GuiStats;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import net.supercraftalex.liquido.Liquido;
import net.supercraftalex.liquido.gui.extra.GuiExtraMain;
import net.supercraftalex.liquido.modules.Category;
import net.supercraftalex.liquido.modules.Module;

public class GuiClickUI extends GuiScreen
{
	
	GuiScreen parent;
	Boolean hasParent;
	
	public GuiClickUI(GuiScreen p) {
		parent = p;
		hasParent = true;
	}
	
	public GuiClickUI() {
		hasParent = false;
	}
	
	@Override
    public void initGui()
    {
        this.buttonList.clear();
        
        List<Category> categorys = new ArrayList<Category>();
        categorys.add(Category.COMBAT);
        categorys.add(Category.EXPLOITS);
        categorys.add(Category.MOVEMENT);
        categorys.add(Category.PLAYER);
        categorys.add(Category.RENDER);
        int x = 10;
        int y = 30;
        for (Category c : categorys) {
        	this.buttonList.add(new GuiButton(0, x, 10, 50, 20, c.name()));
        	y= 30;
        	for(Module m : Liquido.INSTANCE.moduleManager.getModules()) {
        		if(m.getCategory() == c) {
            		this.buttonList.add(new GuiButton(m.getId(), x-15, y, 80, 20, m.getDisplayname()));
            		y = y + 20;
        		}
        	}
        	x = x + 90;
        }
    }
    
	@Override
	protected void keyTyped(char typedChar, int keyCode) throws IOException {
		if(keyCode == Keyboard.KEY_ESCAPE) {
			if(hasParent) {
				mc.displayGuiScreen(parent);
			}
			else {
                mc.displayGuiScreen((GuiScreen)null);
                mc.setIngameFocus();
			}
		}
	}
	
	@Override
    protected void actionPerformed(GuiButton button) throws IOException
    {
    	for(Module m : Liquido.INSTANCE.moduleManager.getModules()) {
    		if(button.id == m.getId()) {
    			if(mc.thePlayer != null) {
    				Object chat = new ChatComponentText("§c[§aClickUI§c] §fToggled " + m.getDisplayname());
    				this.mc.ingameGUI.getChatGUI().printChatMessage((IChatComponent)chat);
    			}
    			m.toggle();
    		}
    	}
    }
	
	@Override
    public void updateScreen()
    {
        super.updateScreen();
    }
	
	@Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
}

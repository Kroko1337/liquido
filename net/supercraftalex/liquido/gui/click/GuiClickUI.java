package net.supercraftalex.liquido.gui.click;

import java.awt.Color;
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
import net.supercraftalex.liquido.Booleans;
import net.supercraftalex.liquido.Liquido;
import net.supercraftalex.liquido.gui.extra.GuiExtraMain;
import net.supercraftalex.liquido.gui.invsort.GuiInvSort;
import net.supercraftalex.liquido.modules.Category;
import net.supercraftalex.liquido.modules.Config;
import net.supercraftalex.liquido.modules.ConfigMode;
import net.supercraftalex.liquido.modules.Module;

public class GuiClickUI extends GuiScreen
{
	
	GuiScreen parent;
	Boolean hasParent;
	
	private Boolean Combat = true;
	private Boolean Exploits = true;
	private Boolean Movement = true;
	private Boolean Player = true;
	private Boolean Render = true;
	private Boolean Options = true;
	
	private Boolean config_enabled = false;
	private Module config_Module = null;
	
	public GuiClickUI(GuiScreen p) {
		parent = p;
		hasParent = true;
	}
	
	public GuiClickUI() {
		hasParent = false;
	}
	
	public List<Category> categorys = new ArrayList<Category>();
	
	@Override
    public void initGui()
    {
		
        this.buttonList.clear();
        
        categorys.add(Category.COMBAT); //0
        categorys.add(Category.EXPLOITS); //1
        categorys.add(Category.MOVEMENT); //2
        categorys.add(Category.PLAYER); //3
        categorys.add(Category.RENDER); //4
        categorys.add(Category.OPTIONS); //5
        
        //addButtons();
		animationStage = 0;
        openAnimations();
        
    }
	
	@Override
    public boolean doesGuiPauseGame()
    {
        return false;
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
	
	double animationStage = 0;
	public void openAnimations() {
		animationStage = 0.1D;
	}
	
	@Override
    protected void actionPerformed(GuiButton button) throws IOException
    {
		
    	for(Module m : Liquido.INSTANCE.moduleManager.getModules()) {
    		if(button.id == 100+m.getId()) {
    			if(mc.thePlayer != null) {
    				Object chat = new ChatComponentText("§c[§aClickUI§c] §fToggled " + m.getDisplayname());
    				this.mc.ingameGUI.getChatGUI().printChatMessage((IChatComponent)chat);
    			}
    			m.toggle();
    		}
    	}
    	
    	if(button.id == 0) {
    		Combat =! Combat;
    		addButtons();
    		return;
    	}
    	if(button.id == 1) {
    		Exploits =! Exploits;
    		addButtons();
    		return;
    	}
    	if(button.id == 2) {
    		Movement =! Movement;
    		addButtons();
    		return;
    	}
    	if(button.id == 3) {
    		Player =! Player;
    		addButtons();
    		return;
    	}
    	if(button.id == 4) {
    		Render =! Render;
    		addButtons();
    		return;
    	}
    	if(button.id == 5) {
    		Options =! Options;
    		addButtons();
    		return;
    	}
    	if(button.id == 55555551) {
    		Booleans.clickui_newmode =! Booleans.clickui_newmode;
    	}
    	if(button.id == 999999) {
    		this.mc.displayGuiScreen(new GuiInvSort(parent));
    	}
    	
    	for(Module m : Liquido.INSTANCE.moduleManager.getModules()) {
    		if(button.id == 1000+m.getId()) {
    			config_enabled =! config_enabled;
    			if(config_enabled) {
        			config_Module = m;
    			}
    			else {
    				config_Module = null;
    			}
    			this.buttonList.clear();
    			this.updateScreen();
    			addButtons();
    		}
    	}
    	
    	for(Module m : Liquido.INSTANCE.moduleManager.getModules()) {
    		int confindex = 0;
    		for(Config c : m.getConfigs()) {
        		if(button.id == 10000+m.getId()+(confindex*100)) {
        			if(c.getValue() instanceof ConfigMode) {
        				ConfigMode cm = c.getConfigMode();
        				Boolean geht = false;
        				List<String> nunaviable = new ArrayList<String>();
        				int loop = 1;
        				for(String s : cm.getAviable()) {
        					if(geht) {
        						nunaviable.add(s);
        					}
        					if(cm.getValue() == s) {
        						geht = true;
        					}
        					loop++;
        				}
        				if(nunaviable.size() == 0) {
        					nunaviable.add(cm.getAviable().get(0));
        				}
        				cm.setValue(nunaviable.get(0));
        			}
        			
        			this.buttonList.clear();
        			this.updateScreen();
        			addButtons();
        		}
        		confindex++;
    		}
    	}
    	
    	for(Module m : Liquido.INSTANCE.moduleManager.getModules()) {
    		int confindex = 0;
    		for(Config c : m.getConfigs()) {
        		if(button.id == 20000+m.getId()+(confindex*100)) {
        			if(c.getValue() instanceof Boolean) {
        				if(new Boolean(c.getValue().toString()) == true) {
        					c.setValue(new Boolean(false));
        				}
        				else if(new Boolean(c.getValue().toString()) == false) {
        					c.setValue(new Boolean(true));
        				}
            			this.buttonList.clear();
            			this.updateScreen();
            			addButtons();
        			}
        			
        			this.buttonList.clear();
        			this.updateScreen();
        			addButtons();
        		}
        		confindex++;
    		}
    	}
    	
    	for(Module m : Liquido.INSTANCE.moduleManager.getModules()) {
    		int confindex = 0;
    		for(Config c : m.getConfigs()) {
        		if(button.id == 31000+m.getId()+(confindex*100)) {
        			if(c.getValue() instanceof Integer) {
            			c.setValue(new Integer(c.getValue().toString())+1);
        			}
        			
        			this.buttonList.clear();
        			this.updateScreen();
        			addButtons();
        		}
        		if(button.id == 32000+m.getId()+(confindex*100)) {
        			if(c.getValue() instanceof Integer) {
        				if(new Integer(c.getValue().toString()) <= 0) {}
        				else {
            				c.setValue(new Integer(c.getValue().toString())-1);
        				}
        			}
        			
        			this.buttonList.clear();
        			this.updateScreen();
        			addButtons();
        		}
        		confindex++;
    		}
    	}
    	
    	if(button.id == 501) {
    		Booleans.isSlideAnimation =! Booleans.isSlideAnimation;
    	}
    	
    	/*
    	 10000 = ConfigMode
    	 20000 = Boolean
    	 30000 = integer
    	  31000 = +
    	  32000 = -
    	 */
    	
    }
	
	@Override
    public void updateScreen()
    {
        super.updateScreen();
		addButtons();
    }
	
	@Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
		if(animationStage <= 3.4) {
			animationStage += 0.05D;
			addButtons();
		}
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
	
	public void addButtons() {
        this.buttonList.clear();
        int x = 20;
        int y = 30;
        int loop = 0;
        if(animationStage >= 0.2) {
        	this.buttonList.add(new GuiClickButton(55555551, this.width-mc.fontRendererObj.getStringWidth("Toggle ClickUI mode")-10, this.height-30, mc.fontRendererObj.getStringWidth("Toggle ClickUI mode")+10, 20, "Toggle ClickUI mode"));
        }
        for (Category c : categorys) {
        	if(animationStage >= 1.2) {
            	this.buttonList.add(new GuiClickButton(loop, x, 10, 50, 20, c.name()));
        	}
        	y= 30;
        	if(animationStage >= 2.2) {
        	if(c == Category.OPTIONS && Options) {
        		if(Booleans.isSlideAnimation) {
                   	this.buttonList.add(new GuiClickButton(501, x-13, y, 80, 20, "Animations"));
            	} else {
            		this.buttonList.add(new GuiClickButton(501, x-15, y, 82, 20, "Animations"));
            	}
        		y = y + 20;
        	}
        	if(c == Category.COMBAT && Combat) {
            	for(Module m : Liquido.INSTANCE.moduleManager.getModules()) {
            		if(m.getCategory() == c) {
            			if(m.toggled) {
                    		this.buttonList.add(new GuiClickButton(100+m.getId(), x-13, y, 78, 20, m.getDisplayname()));
            			} else {
            				this.buttonList.add(new GuiClickButton(100+m.getId(), x-15, y, 80, 20, m.getDisplayname()));
            			}
            			if(animationStage >= 3.2) {
                		Boolean coo = false;
                		for(Config co : m.getConfigs()) {
                			if(co.getName() != null) {
                				coo = true;
                			}
                		}
                		if(coo) {
                    		this.buttonList.add(new GuiClickButton(1000+m.getId(), x+65, y, 15, 20, "+"));
                		}
                		y = y + 20;
                		if(config_enabled) {
                			if(config_Module == m) {
                				//CONFIGS
                				int confindex = 0;
                				for(Config c_o : m.getConfigs()) {
                					if(c_o.getValue() instanceof ConfigMode) {
                						ConfigMode cm = c_o.getConfigMode();
                        				this.buttonList.add(new GuiClickButton(10000+m.getId()+(confindex*100), x-10, y, 80, 20, cm.getValue()));
                					}
                					if(c_o.getValue() instanceof Boolean) {
                						if(new Boolean(c_o.getValue().toString()) == false) {
                            				this.buttonList.add(new GuiClickButton(20000+m.getId()+(confindex*100), x-10, y, 80, 20, c_o.getName()));
                						}
                						if(new Boolean(c_o.getValue().toString()) == true) {
                							this.buttonList.add(new GuiClickButton(20000+m.getId()+(confindex*100), x-8, y, 78, 20, c_o.getName()));
                						}
                					}
                					if(c_o.getValue() instanceof Integer) {
                						this.buttonList.add(new GuiClickButton(123+m.getId()+(confindex*100), x-10, y,70, 20, c_o.getName()+": " + new Integer(c_o.getValue().toString())));
                						this.buttonList.add(new GuiClickButton(31000+m.getId()+(confindex*100), x+60, y, 10, 10, "+"));
                						this.buttonList.add(new GuiClickButton(32000+m.getId()+(confindex*100), x+60, y+10, 10, 10, "-"));
                					}
                					confindex++;
                					y = y + 20;
                				}
                        		//y = y + 20;
                			}
                		}
            			}
            		}
            	}
        	}
        	if(c == Category.EXPLOITS && Exploits) {
            	for(Module m : Liquido.INSTANCE.moduleManager.getModules()) {
            		if(m.getCategory() == c) {
            			if(m.toggled) {
                    		this.buttonList.add(new GuiClickButton(100+m.getId(), x-13, y, 78, 20, m.getDisplayname()));
            			} else {
            				this.buttonList.add(new GuiClickButton(100+m.getId(), x-15, y, 80, 20, m.getDisplayname()));
            			}
            			if(animationStage >= 3.2) {
                		Boolean coo = false;
                		for(Config co : m.getConfigs()) {
                			if(co.getName() != null) {
                				coo = true;
                			}
                		}
                		if(coo) {
                    		this.buttonList.add(new GuiClickButton(1000+m.getId(), x+65, y, 15, 20, "+"));
                		}
                		y = y + 20;
                		if(config_enabled) {
                			if(config_Module == m) {
                				//CONFIGS
                				int confindex = 0;
                				for(Config c_o : m.getConfigs()) {
                					if(c_o.getValue() instanceof ConfigMode) {
                						ConfigMode cm = c_o.getConfigMode();
                        				this.buttonList.add(new GuiClickButton(10000+m.getId()+(confindex*100), x-10, y, 80, 20, cm.getValue()));
                					}
                					if(c_o.getValue() instanceof Boolean) {
                						if(new Boolean(c_o.getValue().toString()) == false) {
                            				this.buttonList.add(new GuiClickButton(20000+m.getId()+(confindex*100), x-10, y, 80, 20, c_o.getName()));
                						}
                						if(new Boolean(c_o.getValue().toString()) == true) {
                							this.buttonList.add(new GuiClickButton(20000+m.getId()+(confindex*100), x-8, y, 78, 20, c_o.getName()));
                						}
                					}
                					if(c_o.getValue() instanceof Integer) {
                						this.buttonList.add(new GuiClickButton(123+m.getId()+(confindex*100), x-10, y,70, 20, c_o.getName()+": " + new Integer(c_o.getValue().toString())));
                						this.buttonList.add(new GuiClickButton(31000+m.getId()+(confindex*100), x+60, y, 10, 10, "+"));
                						this.buttonList.add(new GuiClickButton(32000+m.getId()+(confindex*100), x+60, y+10, 10, 10, "-"));
                					}
                					confindex++;
                					y = y + 20;
                				}
                        		//y = y + 20;
                			}
                		}
            			}
            		}
            	}
        	}
        	if(c == Category.MOVEMENT && Movement) {
            	for(Module m : Liquido.INSTANCE.moduleManager.getModules()) {
            		if(m.getCategory() == c) {
            			if(m.toggled) {
                    		this.buttonList.add(new GuiClickButton(100+m.getId(), x-13, y, 78, 20, m.getDisplayname()));
            			} else {
            				this.buttonList.add(new GuiClickButton(100+m.getId(), x-15, y, 80, 20, m.getDisplayname()));
            			}
            			if(animationStage >= 3.2) {
                		Boolean coo = false;
                		for(Config co : m.getConfigs()) {
                			if(co.getName() != null) {
                				coo = true;
                			}
                		}
                		if(coo) {
                    		this.buttonList.add(new GuiClickButton(1000+m.getId(), x+65, y, 15, 20, "+"));
                		}
                		y = y + 20;
                		if(config_enabled) {
                			if(config_Module == m) {
                				//CONFIGS
                				int confindex = 0;
                				for(Config c_o : m.getConfigs()) {
                					if(c_o.getValue() instanceof ConfigMode) {
                						ConfigMode cm = c_o.getConfigMode();
                        				this.buttonList.add(new GuiClickButton(10000+m.getId()+(confindex*100), x-10, y, 80, 20, cm.getValue()));
                					}
                					if(c_o.getValue() instanceof Boolean) {
                						if(new Boolean(c_o.getValue().toString()) == false) {
                            				this.buttonList.add(new GuiClickButton(20000+m.getId()+(confindex*100), x-10, y, 80, 20, c_o.getName()));
                						}
                						if(new Boolean(c_o.getValue().toString()) == true) {
                							this.buttonList.add(new GuiClickButton(20000+m.getId()+(confindex*100), x-8, y, 78, 20, c_o.getName()));
                						}
                					}
                					if(c_o.getValue() instanceof Integer) {
                						this.buttonList.add(new GuiClickButton(123+m.getId()+(confindex*100), x-10, y,70, 20, c_o.getName()+": " + new Integer(c_o.getValue().toString())));
                						this.buttonList.add(new GuiClickButton(31000+m.getId()+(confindex*100), x+60, y, 10, 10, "+"));
                						this.buttonList.add(new GuiClickButton(32000+m.getId()+(confindex*100), x+60, y+10, 10, 10, "-"));
                					}
                					confindex++;
                					y = y + 20;
                				}
                        		//y = y + 20;
                			}
                		}
            			}
            		}
            	}
        	}
        	if(c == Category.PLAYER && Player) {
            	for(Module m : Liquido.INSTANCE.moduleManager.getModules()) {
            		if(m.getCategory() == c) {
            			if(m.toggled) {
                    		this.buttonList.add(new GuiClickButton(100+m.getId(), x-13, y, 78, 20, m.getDisplayname()));
            			} else {
            				this.buttonList.add(new GuiClickButton(100+m.getId(), x-15, y, 80, 20, m.getDisplayname()));
            			}
            			if(animationStage >= 3.2) {
                		Boolean coo = false;
                		for(Config co : m.getConfigs()) {
                			if(co.getName() != null) {
                				coo = true;
                			}
                		}
                		if(coo) {
                    		this.buttonList.add(new GuiClickButton(1000+m.getId(), x+65, y, 15, 20, "+"));
                		}
                		if(m.getName().equalsIgnoreCase("InvCleaner")) {
                			this.buttonList.add(new GuiClickButton(999999, x+65, y, 15, 20, "C"));
                		}
                		y = y + 20;
                		if(config_enabled) {
                			if(config_Module == m) {
                				//CONFIGS
                				int confindex = 0;
                				for(Config c_o : m.getConfigs()) {
                					if(c_o.getValue() instanceof ConfigMode) {
                						ConfigMode cm = c_o.getConfigMode();
                        				this.buttonList.add(new GuiClickButton(10000+m.getId()+(confindex*100), x-10, y, 80, 20, cm.getValue()));
                					}
                					if(c_o.getValue() instanceof Boolean) {
                						if(new Boolean(c_o.getValue().toString()) == false) {
                            				this.buttonList.add(new GuiClickButton(20000+m.getId()+(confindex*100), x-10, y, 80, 20, c_o.getName()));
                						}
                						if(new Boolean(c_o.getValue().toString()) == true) {
                							this.buttonList.add(new GuiClickButton(20000+m.getId()+(confindex*100), x-8, y, 78, 20, c_o.getName()));
                						}
                					}
                					if(c_o.getValue() instanceof Integer) {
                						this.buttonList.add(new GuiClickButton(123+m.getId()+(confindex*100), x-10, y,70, 20, c_o.getName()+": " + new Integer(c_o.getValue().toString())));
                						this.buttonList.add(new GuiClickButton(31000+m.getId()+(confindex*100), x+60, y, 10, 10, "+"));
                						this.buttonList.add(new GuiClickButton(32000+m.getId()+(confindex*100), x+60, y+10, 10, 10, "-"));
                					}
                					confindex++;
                					y = y + 20;
                				}
                        		//y = y + 20;
                			}
                		}
            			}
            		}
            	}
        	}
        	if(c == Category.RENDER && Render) {
            	for(Module m : Liquido.INSTANCE.moduleManager.getModules()) {
            		if(m.getCategory() == c) {
            			if(m.toggled) {
                    		this.buttonList.add(new GuiClickButton(100+m.getId(), x-13, y, 78, 20, m.getDisplayname()));
            			} else {
            				this.buttonList.add(new GuiClickButton(100+m.getId(), x-15, y, 80, 20, m.getDisplayname()));
            			}
            			if(animationStage >= 3.2) {
                		Boolean coo = false;
                		for(Config co : m.getConfigs()) {
                			if(co.getName() != null) {
                				coo = true;
                			}
                		}
                		if(coo) {
                    		this.buttonList.add(new GuiClickButton(1000+m.getId(), x+65, y, 15, 20, "+"));
                		}
                		y = y + 20;
                		if(config_enabled) {
                			if(config_Module == m) {
                				//CONFIGS
                				int confindex = 0;
                				for(Config c_o : m.getConfigs()) {
                					if(c_o.getValue() instanceof ConfigMode) {
                						ConfigMode cm = c_o.getConfigMode();
                        				this.buttonList.add(new GuiClickButton(10000+m.getId()+(confindex*100), x-10, y, 80, 20, cm.getValue()));
                					}
                					if(c_o.getValue() instanceof Boolean) {
                						if(new Boolean(c_o.getValue().toString()) == false) {
                            				this.buttonList.add(new GuiClickButton(20000+m.getId()+(confindex*100), x-10, y, 80, 20, c_o.getName()));
                						}
                						if(new Boolean(c_o.getValue().toString()) == true) {
                							this.buttonList.add(new GuiClickButton(20000+m.getId()+(confindex*100), x-8, y, 78, 20, c_o.getName()));
                						}
                					}
                					if(c_o.getValue() instanceof Integer) {
                						this.buttonList.add(new GuiClickButton(123+m.getId()+(confindex*100), x-10, y,70, 20, c_o.getName()+": " + new Integer(c_o.getValue().toString())));
                						this.buttonList.add(new GuiClickButton(31000+m.getId()+(confindex*100), x+60, y, 10, 10, "+"));
                						this.buttonList.add(new GuiClickButton(32000+m.getId()+(confindex*100), x+60, y+10, 10, 10, "-"));
                					}
                					confindex++;
                					y = y + 20;
                				}
                        		//y = y + 20;
                			}
                		}
            			}
            		}
            	}
        	}
        	}
        	x = x + 100;
        	loop++;
        }
	}
	
}

package net.supercraftalex.liquido.commands.impl;

import java.util.Properties;

import net.supercraftalex.liquido.Liquido;
import net.supercraftalex.liquido.commands.Command;
import net.supercraftalex.liquido.config.ClientConfig;
import net.supercraftalex.liquido.config.ClientConfig.ModuleConfig;
import net.supercraftalex.liquido.modules.ConfigMode;
import net.supercraftalex.liquido.modules.Module;
import net.supercraftalex.liquido.utils.FileUtil;

public class Config extends Command {

	public Config() {
		super("config", "Manage configs! Usage: .config [load|save|list|del] [name]");
	}

	@Override
	public void execute(String[] args) {
		if(args.length == 0) {
			messageWithPrefix("§aUsage: .config [load|save|list|del] [name]");
		}
		if(args.length >= 1) {
			if(args[0].equalsIgnoreCase("list")) {
				messageWithPrefix("§fAviable Configs:");
				for(ClientConfig c : Liquido.INSTANCE.configManager.get()) {
					if(c.name == Liquido.INSTANCE.configManager.selected.name) {
						messageWithoutPrefix(" - §c"+c.name+"§f - [selected]");
					} else {
						messageWithoutPrefix(" - §c"+c.name+"§f - ");
					}
				}
				for(String c : FileUtil.configDir.list()) {
					messageWithoutPrefix(" - §a"+c.replace(".cfg", "")+"§f - ");
				}
			}
		}
		if(args.length >= 2) {
			if(args[0].equalsIgnoreCase("load")) {
				if(Liquido.INSTANCE.configManager.getConfigByName(args[1]) != null) {
					Liquido.INSTANCE.configManager.selected = Liquido.INSTANCE.configManager.getConfigByName(args[1]);
					Liquido.INSTANCE.configManager.loadConfig();
					messageWithPrefix("§aConfig ["+args[1]+"] was loaded!");
				}
				else if (FileUtil.existsFiles(FileUtil.configDir, args[1]+".cfg")) {
					Properties p = FileUtil.readPropertyFile(FileUtil.configDir, args[1]+".cfg");
					ClientConfig c = new ClientConfig(args[1], "custom");
					if(!p.getProperty("version").equalsIgnoreCase(Liquido.INSTANCE.VERSION)) {
						messageWithPrefix("§cThe config was created for an other version!");
						messageWithPrefix("§cThis can be buggy!");
					}
					System.out.println(p.getProperty("version"));
					for(Module m : Liquido.INSTANCE.moduleManager.getModules()) {
						c.addModule(m.getName());;
						for(net.supercraftalex.liquido.modules.Config co : m.getConfigs()) {
							if(co.getValue() instanceof ConfigMode) {
								Liquido.INSTANCE.moduleManager.getModuleByName(m.getName()).getConfigByName(co.getName()).getConfigMode().setValue(p.getProperty(m.getName()+"."+co.getName()+".mode"));
							} else {
								try {
									String n = p.getProperty(m.getName()+"."+co.getName());
									Object o = null;
									if(n.contains("true") || n.contains("false")) {
										o = new Boolean(n);
									} else {
										o = new Integer(Integer.parseInt(n));
									}
									Liquido.INSTANCE.moduleManager.getModuleByName(m.getName()).getConfigByName(co.getName()).setValue(o);
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						}
					}
					c.save();
					Liquido.INSTANCE.configManager.selected = c;
					Liquido.INSTANCE.configManager.loadConfig();
					messageWithPrefix("§aConfig ["+args[1]+"] was loaded!");
				}
				else {
					messageWithPrefix("§cConfig ["+args[1]+"] not found!");
				}
			}
			if(args[0].equalsIgnoreCase("save")) {
				String text = "version="+Liquido.INSTANCE.VERSION;
				for(Module m : Liquido.INSTANCE.moduleManager.getModules()) {
					for(net.supercraftalex.liquido.modules.Config c : m.getConfigs()) {
						if(c.getValue() instanceof ConfigMode) {
							text = text + "\n" + m.getName()+"."+c.getName() + ".mode" + "=" + c.getConfigMode().getValue();
						} else {
							text = text + "\n" + m.getName()+"."+c.getName() + "=" + c.getValue().toString();
						}
					}
				}
				FileUtil.writeFile(FileUtil.configDir, args[1]+".cfg", text);
				messageWithPrefix("§aConfig ["+args[1]+"] saved!");
			}
			if(args[0].equalsIgnoreCase("del") || args[0].equalsIgnoreCase("rem") || args[0].equalsIgnoreCase("delete")) {
				if(FileUtil.existsFiles(FileUtil.configDir, args[1]+".cfg")) {
					if(FileUtil.deleteFIle(FileUtil.configDir, args[1]+".cfg")) {
						messageWithPrefix("§aConfig ["+args[1]+"] got succsesful deleted!");
					} else {
						messageWithPrefix("§An File-Error occured!");
					}
				} else {
					messageWithPrefix("§cConfig ["+args[1]+"] doesn`t exist!");
				}
			}
		}
	}

}

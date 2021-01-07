package net.supercraftalex.Discord;

import net.supercraftalex.liquido.Liquido;
import net.arikia.dev.drpc.DiscordEventHandlers;
import net.arikia.dev.drpc.DiscordEventHandlers.Builder;
import net.arikia.dev.drpc.DiscordRPC;
import net.arikia.dev.drpc.DiscordRichPresence;
import net.arikia.dev.drpc.DiscordUser;
import net.arikia.dev.drpc.callbacks.ReadyCallback;

public class DiscordRP {

	private boolean running = true;
	private long created = 0;
	
	public void start() {
		
		this.created = System.currentTimeMillis();
		
		DiscordEventHandlers handlers = new DiscordEventHandlers.Builder().setReadyEventHandler(new ReadyCallback() {
			
			@Override
			public void apply(DiscordUser user) {
				System.out.println("Welcome " + user.username + "#" + user.discriminator + ".");
				update("Loading...", "");
			}
		}).build();
		
		DiscordRPC.discordInitialize("780363229829660683", handlers, true);
		
		new Thread("RPC Callback") {
			public void run() {
				while (running) {
					DiscordRPC.discordRunCallbacks();
				}	
			}
		}.start();
	}
	
	public void shutdown() {
		running = false;
		DiscordRPC.discordShutdown();
	}
	public void update(String linefirst, String linesecond) {
		DiscordRichPresence.Builder b = new DiscordRichPresence.Builder(linesecond);
		b.setBigImage("unbenannt", Liquido.NAME + " " + Liquido.VERSION + " " + "by " + Liquido.AUTHORS);
		b.setSmallImage("mc-logo", "in Minecraft 1.8.8");
		b.setDetails(linefirst);
		b.setStartTimestamps(created);
		
		DiscordRPC.discordUpdatePresence(b.build());
	}
}

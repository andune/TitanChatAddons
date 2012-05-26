package com.titankingdoms.nodinchan.titanchat.addon;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.plugin.Plugin;

import com.ensifera.animosity.craftirc.CraftIRC;
import com.titankingdoms.nodinchan.titanchat.event.MessageSendEvent;

public class CraftIRC2Hook extends Addon {
	
	private CraftIRC craftIRC;
	
	public CraftIRC2Hook() {
		super("CraftIRC2Hook");
		init(this);
	}
	
	@Override
	public void init() {
		if (plugin.getServer().getPluginManager().getPlugin("CraftIRC") != null) {
			Plugin craftIRC = plugin.getServer().getPluginManager().getPlugin("CraftIRC");
			
			if (craftIRC.getDescription().getVersion().startsWith("2")) {
				this.craftIRC = (CraftIRC) craftIRC;
				register(this);
			}
		}
	}
	
	public String colourise(String text) {
		String colourised = text.replaceAll("(&([a-f0-9A-Fk-oK-O]))", "\u00A7$2");
		return colourised;
	}
	
	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onMessageSend(MessageSendEvent event) {
		craftIRC.sendMessageToTag("<" + event.getSender().getName() + "> " + colourise(event.getMessage()), "all");
	}
}
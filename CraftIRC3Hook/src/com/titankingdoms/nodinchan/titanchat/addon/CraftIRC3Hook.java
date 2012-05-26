package com.titankingdoms.nodinchan.titanchat.addon;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.plugin.Plugin;

import com.ensifera.animosity.craftirc.CraftIRC;
import com.titankingdoms.nodinchan.titanchat.event.MessageSendEvent;

public class CraftIRC3Hook extends Addon {
	
	private CraftIRC craftIRC;
	
	private final Map<String, ChannelPoint> endpoints;
	
	public CraftIRC3Hook() {
		super("CraftIRC3Hook");
		init(this);
		this.endpoints = new HashMap<String, ChannelPoint>();
	}
	
	@Override
	public void init() {
		getConfig().options().copyHeader(true);
		getConfig().options().copyDefaults(true);
		saveConfig();
		
		if (plugin.getServer().getPluginManager().getPlugin("CraftIRC") != null) {
			Plugin craftIRC = plugin.getServer().getPluginManager().getPlugin("CraftIRC");
			
			if (craftIRC.getDescription().getVersion().startsWith("3")) {
				this.craftIRC = (CraftIRC) craftIRC;
				
				for (String tag : getConfig().getConfigurationSection("map").getKeys(false)) {
					ChannelPoint endpoint = new ChannelPoint(plugin, this.craftIRC, getConfig().getString("map." + tag));
					endpoints.put(getConfig().getString("map." + tag).toLowerCase(), endpoint);
					this.craftIRC.registerEndPoint(tag, endpoint);
				}
				
				register(this);
			}
		}
	}
	
	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onMessageSend(MessageSendEvent event) {
		ChannelPoint endpoint = endpoints.get(event.getSentFrom().getName().toLowerCase());
		
		if (endpoint != null)
			endpoint.messageOut(event.getSender(), event.getMessage());
	}
	
	@Override
	public void unload() {
		if (craftIRC != null)
			craftIRC.unregisterEndPoint("TitanChat");
	}
}
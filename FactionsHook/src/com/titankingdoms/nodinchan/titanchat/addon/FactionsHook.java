package com.titankingdoms.nodinchan.titanchat.addon;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;

import com.massivecraft.factions.P;
import com.titankingdoms.nodinchan.titanchat.events.MessageFormatEvent;
import com.titankingdoms.nodinchan.titanchat.events.MessageReceiveEvent;

public class FactionsHook extends Addon {
	
	private P factions;
	
	public FactionsHook() {
		super("FactionsHook");
		init(this);
	}
	
	@Override
	public void init() {
		if (plugin.getServer().getPluginManager().getPlugin("Factions") != null) {
			factions = (P) plugin.getServer().getPluginManager().getPlugin("Factions");
			register(this);
		}
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onMessageFormat(MessageFormatEvent event) {
		event.setFormat(event.getFormat().replace("%ftitle", factions.getPlayerTitle(event.getSender())));
	}
	
	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onMessageReceive(MessageReceiveEvent event) {
		event.setFormat(event.getFormat().replace("%faction", factions.getPlayerFactionTagRelation(event.getSender(), event.getRecipant())));
	}
}
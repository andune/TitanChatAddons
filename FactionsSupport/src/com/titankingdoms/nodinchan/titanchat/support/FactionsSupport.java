package com.titankingdoms.nodinchan.titanchat.support;

import org.bukkit.entity.Player;

import com.massivecraft.factions.P;
import com.titankingdoms.nodinchan.titanchat.TitanChat;

public class FactionsSupport extends Support {
	
	protected P factions;
	
	public FactionsSupport(TitanChat plugin) {
		super(plugin, "FactionsSupport");
	}
	
	@Override
	public void chatMade(String name, String message) {}
	
	@Override
	public String chatMade(Player player, String message) {
		String msg = message;
		
		if (factions != null) {
			String faction = factions.getPlayerFactionTag(player);
			
			msg = msg.replace("%faction", faction);
		}
		
		return msg;
	}

	@Override
	public void init() {
		if (plugin.getServer().getPluginManager().getPlugin("Factions") != null)
			factions = (P) plugin.getServer().getPluginManager().getPlugin("Factions");
	}
}
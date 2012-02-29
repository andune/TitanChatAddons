package com.titankingdoms.nodinchan.titanchat.support;

import net.sacredlabyrinth.phaed.simpleclans.SimpleClans;

import org.bukkit.entity.Player;

import com.titankingdoms.nodinchan.titanchat.TitanChat;

public class SimpleClansSupport extends Support {
	
	protected SimpleClans simpleClans;
	
	public SimpleClansSupport(TitanChat plugin) {
		super(plugin, "SimpleClansSupport");
	}
	
	@Override
	public void chatMade(String name, String message) {}
	
	@Override
	public String chatMade(Player player, String message) {
		String msg = message;
		
		if (simpleClans != null) {
			String clan = simpleClans.getClanManager().getClanByPlayerName(player.getName()).getColorTag();
			
			msg = msg.replace("%clan", clan);
		}
		
		return msg;
	}
	
	@Override
	public void init() {
		if (plugin.getServer().getPluginManager().getPlugin("SimpleClans") != null)
			simpleClans = (SimpleClans) plugin.getServer().getPluginManager().getPlugin("SimpleClans");
	}
}
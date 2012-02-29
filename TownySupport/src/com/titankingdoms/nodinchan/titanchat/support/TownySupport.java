package com.titankingdoms.nodinchan.titanchat.support;

import org.bukkit.entity.Player;

import com.palmergames.bukkit.towny.NotRegisteredException;
import com.palmergames.bukkit.towny.object.Resident;
import com.palmergames.bukkit.towny.object.TownyUniverse;
import com.titankingdoms.nodinchan.titanchat.TitanChat;

public class TownySupport extends Support {
	
	public TownySupport(TitanChat plugin) {
		super(plugin, "TownySupport");
	}
	
	@Override
	public void chatMade(String name, String message) {}

	@Override
	public String chatMade(Player player, String message) {
		String msg = message;
		
		try {
			Resident resident = TownyUniverse.getDataSource().getResident(player.getName());
			
			String town = resident.getTown().getName();
			String nation = resident.getTown().getNation().getName();
			String title = resident.getTitle();
			
			msg = msg.replace("%town", town);
			msg = msg.replace("%nation", nation);
			msg = msg.replace("%title", title);
			
		} catch (NotRegisteredException e) {}
		
		return msg;
	}

	@Override
	public void init() {}
}
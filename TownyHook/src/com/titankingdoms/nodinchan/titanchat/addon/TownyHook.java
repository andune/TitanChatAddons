package com.titankingdoms.nodinchan.titanchat.addon;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;

import com.palmergames.bukkit.towny.NotRegisteredException;
import com.palmergames.bukkit.towny.object.Nation;
import com.palmergames.bukkit.towny.object.Resident;
import com.palmergames.bukkit.towny.object.Town;
import com.palmergames.bukkit.towny.object.TownyUniverse;
import com.titankingdoms.nodinchan.titanchat.events.MessageFormatEvent;

public class TownyHook extends Addon {
	
	public TownyHook() {
		super("TownyHook");
		init(this);
	}
	
	@Override
	public void init() {
		if (plugin.getServer().getPluginManager().getPlugin("Towny") != null)
			register(this);
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onMessageFormat(MessageFormatEvent event) {
		try {
			Resident resident = TownyUniverse.getDataSource().getResident(event.getSender().getName());
			
			if (resident != null) {
				Town town = resident.getTown();
				
				event.setFormat(event.getFormat().replace("%ttitle", resident.getTitle()));
				event.setFormat(event.getFormat().replace("%tsurname", resident.getSurname()));
				
				if (town != null) {
					Nation nation = town.getNation();
					
					event.setFormat(event.getFormat().replace("%town", town.getName()));
					event.setFormat(event.getFormat().replace("%townformatted", town.getFormattedName()));
					event.setFormat(event.getFormat().replace("%towntag", town.getTag()));
					
					if (nation != null) {
						event.setFormat(event.getFormat().replace("%nation", nation.getName()));
						event.setFormat(event.getFormat().replace("%nationformatted", nation.getFormattedName()));
						event.setFormat(event.getFormat().replace("%nationtag", nation.getTag()));
					}
				}
			}
			
		} catch (NotRegisteredException e) {}
	}
}
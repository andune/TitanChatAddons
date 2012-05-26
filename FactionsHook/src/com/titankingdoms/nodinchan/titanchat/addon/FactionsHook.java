package com.titankingdoms.nodinchan.titanchat.addon;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import com.massivecraft.factions.P;
import com.titankingdoms.nodinchan.titanchat.event.MessageFormatEvent;
import com.titankingdoms.nodinchan.titanchat.event.MessageReceiveEvent;
import com.titankingdoms.nodinchan.titanchat.util.variable.Variable.IVariable;

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
			
			plugin.getVariableManager().register(new IVariable() {
				
				@Override
				public Class<? extends Event> getEvent() {
					return MessageFormatEvent.class;
				}
				
				@Override
				public String getReplacement(Player sender, Player... recipants) {
					return factions.getPlayerTitle(sender);
				}
				
				@Override
				public String getVariable() {
					return "%ftitle";
				}
				
			}, new IVariable() {
				
				@Override
				public Class<? extends Event> getEvent() {
					return MessageReceiveEvent.class;
				}
				
				@Override
				public String getReplacement(Player sender, Player... recipants) {
					return factions.getPlayerFactionTagRelation(sender, recipants[0]);
				}
				
				@Override
				public String getVariable() {
					return "%faction";
				}
			});
		}
	}
}
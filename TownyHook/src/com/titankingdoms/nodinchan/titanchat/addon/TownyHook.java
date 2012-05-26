package com.titankingdoms.nodinchan.titanchat.addon;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import com.palmergames.bukkit.towny.NotRegisteredException;
import com.palmergames.bukkit.towny.object.Nation;
import com.palmergames.bukkit.towny.object.Resident;
import com.palmergames.bukkit.towny.object.Town;
import com.palmergames.bukkit.towny.object.TownyUniverse;
import com.titankingdoms.nodinchan.titanchat.event.MessageFormatEvent;
import com.titankingdoms.nodinchan.titanchat.util.variable.Variable.IVariable;

public class TownyHook extends Addon {
	
	public TownyHook() {
		super("TownyHook");
		init(this);
	}
	
	@Override
	public void init() {
		if (plugin.getServer().getPluginManager().getPlugin("Towny") != null) {
			plugin.getVariableManager().register(new IVariable() {
				
				@Override
				public Class<? extends Event> getEvent() {
					return MessageFormatEvent.class;
				}
				
				@Override
				public String getReplacement(Player sender, Player... recipants) {
					try {
						Resident resident = TownyUniverse.getDataSource().getResident(sender.getName());
						return resident.getTitle();
						
					} catch (NotRegisteredException e) { return ""; }
				}
				
				@Override
				public String getVariable() {
					return "%ttitle";
				}
				
			}, new IVariable() {
				
				@Override
				public Class<? extends Event> getEvent() {
					return MessageFormatEvent.class;
				}
				
				@Override
				public String getReplacement(Player sender, Player... recipants) {
					try {
						Resident resident = TownyUniverse.getDataSource().getResident(sender.getName());
						return resident.getSurname();
						
					} catch (NotRegisteredException e) { return ""; }
				}
				
				@Override
				public String getVariable() {
					return "%tsurname";
				}
				
			}, new IVariable() {
				
				@Override
				public Class<? extends Event> getEvent() {
					return MessageFormatEvent.class;
				}
				
				@Override
				public String getReplacement(Player sender, Player... recipants) {
					try {
						Town town = TownyUniverse.getDataSource().getResident(sender.getName()).getTown();
						
						if (town != null)
							return town.getName();
						else
							return "";
						
					} catch (NotRegisteredException e) { return ""; }
				}
				
				@Override
				public String getVariable() {
					return "town";
				}
				
			}, new IVariable() {
				
				@Override
				public Class<? extends Event> getEvent() {
					return MessageFormatEvent.class;
				}
				
				@Override
				public String getReplacement(Player sender, Player... recipants) {
					try {
						Town town = TownyUniverse.getDataSource().getResident(sender.getName()).getTown();
						
						if (town != null)
							return town.getTag();
						else
							return "";
						
					} catch (NotRegisteredException e) { return ""; }
				}
				
				@Override
				public String getVariable() {
					return "%towntag";
				}
				
			}, new IVariable() {
				
				@Override
				public Class<? extends Event> getEvent() {
					return MessageFormatEvent.class;
				}
				
				@Override
				public String getReplacement(Player sender, Player... recipants) {
					try {
						Town town = TownyUniverse.getDataSource().getResident(sender.getName()).getTown();
						
						if (town != null)
							return town.getFormattedName();
						else
							return "";
						
					} catch (NotRegisteredException e) { return ""; }
				}
				
				@Override
				public String getVariable() {
					return "%townformatted";
				}
				
			}, new IVariable() {
				
				@Override
				public Class<? extends Event> getEvent() {
					return MessageFormatEvent.class;
				}
				
				@Override
				public String getReplacement(Player sender, Player... recipants) {
					try {
						Town town = TownyUniverse.getDataSource().getResident(sender.getName()).getTown();
						
						if (town != null) {
							Nation nation = town.getNation();
							
							if (nation != null)
								return nation.getName();
							else
								return "";
							
						} else { return ""; }
						
					} catch (NotRegisteredException e) { return ""; }
				}
				
				@Override
				public String getVariable() {
					return "%nation";
				}
				
			}, new IVariable() {
				
				@Override
				public Class<? extends Event> getEvent() {
					return MessageFormatEvent.class;
				}
				
				@Override
				public String getReplacement(Player sender, Player... recipants) {
					try {
						Town town = TownyUniverse.getDataSource().getResident(sender.getName()).getTown();
						
						if (town != null) {
							Nation nation = town.getNation();
							
							if (nation != null)
								return nation.getTag();
							else
								return "";
							
						} else { return ""; }
						
					} catch (NotRegisteredException e) { return ""; }
				}
				
				@Override
				public String getVariable() {
					return "%nationtag";
				}
				
			}, new IVariable() {
				
				@Override
				public Class<? extends Event> getEvent() {
					return MessageFormatEvent.class;
				}
				
				@Override
				public String getReplacement(Player sender, Player... recipants) {
					try {
						Town town = TownyUniverse.getDataSource().getResident(sender.getName()).getTown();
						
						if (town != null) {
							Nation nation = town.getNation();
							
							if (nation != null)
								return nation.getFormattedName();
							else
								return "";
							
						} else { return ""; }
						
					} catch (NotRegisteredException e) { return ""; }
				}
				
				@Override
				public String getVariable() {
					return "%nationformatted";
				}
			});
		}
	}
}
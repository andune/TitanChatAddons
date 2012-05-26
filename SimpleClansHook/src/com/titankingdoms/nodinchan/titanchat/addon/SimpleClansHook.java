package com.titankingdoms.nodinchan.titanchat.addon;

import net.sacredlabyrinth.phaed.simpleclans.Clan;
import net.sacredlabyrinth.phaed.simpleclans.ClanPlayer;
import net.sacredlabyrinth.phaed.simpleclans.SimpleClans;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import com.titankingdoms.nodinchan.titanchat.event.MessageFormatEvent;
import com.titankingdoms.nodinchan.titanchat.util.variable.Variable.IVariable;

public class SimpleClansHook extends Addon {
	
	private SimpleClans simpleClans;
	
	public SimpleClansHook() {
		super("SimpleClansHook");
		init(this);
	}
	
	@Override
	public void init() {
		if (plugin.getServer().getPluginManager().getPlugin("SimpleClans") != null) {
			simpleClans = (SimpleClans) plugin.getServer().getPluginManager().getPlugin("SimpleClans");
			
			plugin.getVariableManager().register(new IVariable() {

				@Override
				public Class<? extends Event> getEvent() {
					return MessageFormatEvent.class;
				}

				@Override
				public String getReplacement(Player sender, Player... recipants) {
					Clan clan = simpleClans.getClanManager().getClanByPlayerName(sender.getName());
					return (clan != null) ? clan.getColorTag() : "";
				}

				@Override
				public String getVariable() {
					return "%clan";
				}
				
			}, new IVariable() {

				@Override
				public Class<? extends Event> getEvent() {
					return MessageFormatEvent.class;
				}

				@Override
				public String getReplacement(Player sender, Player... recipants) {
					ClanPlayer clanPlayer = simpleClans.getClanManager().getClanPlayer(sender);
					return (clanPlayer != null) ? clanPlayer.getRank() : "";
				}

				@Override
				public String getVariable() {
					return "%csrank";
				}
				
			}, new IVariable() {

				@Override
				public Class<? extends Event> getEvent() {
					return MessageFormatEvent.class;
				}

				@Override
				public String getReplacement(Player sender, Player... recipants) {
					ClanPlayer clanPlayer = simpleClans.getClanManager().getClanPlayer(sender);
					return ((clanPlayer != null) ? clanPlayer.getKDR() : 0) + "";
				}

				@Override
				public String getVariable() {
					return "%cskdr";
				}
			});
		}
	}
}
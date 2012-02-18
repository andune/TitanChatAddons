package com.titankingdoms.nodinchan.titanchat.support;

import org.bukkit.entity.Player;
import org.monstercraft.irc.IRC;
import org.monstercraft.irc.util.IRCColor;
import org.monstercraft.irc.util.Variables;
import org.monstercraft.irc.wrappers.IRCChannel;

import com.titankingdoms.nodinchan.titanchat.TitanChat;

public class MonsterIRCSupport extends TCSupport {
	
	protected IRC monsterIRC;
	
	public MonsterIRCSupport(TitanChat plugin) {
		super(plugin, "MonsterIRCSupport");
	}

	@Override
	public void chatMade(String name, String message) {
		String msg = Variables.ircformat;
		msg = msg.replace("{name}", name);
		msg = msg.replace("{world}", plugin.getPlayer(name).getWorld().getName());
		msg = msg.replace("{prefix}", plugin.getPlayerPrefix(plugin.getPlayer(name)));
		msg = msg.replace("{suffix}", plugin.getPlayerSuffix(plugin.getPlayer(name)));
		msg = msg.replace("{groupPrefix}", plugin.getGroupPrefix(plugin.getPlayer(name)));
		msg = msg.replace("{groupSuffix}", plugin.getGroupSuffix(plugin.getPlayer(name)));
		msg = msg.replace("{message}", IRCColor.NORMAL + message);
		
		if (monsterIRC != null) {
			for (IRCChannel channel : Variables.channels) {
				IRC.getHandleManager().getIRCHandler().sendMessage(IRCColor.formatMCMessage(recolourize(msg)), channel.getChannel());
			}
		}
	}

	@Override
	public String chatMade(Player player, String message) {
		return message;
	}

	@Override
	public void init() {
		if (plugin.getServer().getPluginManager().getPlugin("MonsterIRC") != null)
			monsterIRC = (IRC) plugin.getServer().getPluginManager().getPlugin("MonsterIRC");
	}
	
	public String recolourize(String text) {
		String recolourized = text.replaceAll("(&([a-f0-9A-F|kK]))", "§");
		return recolourized;
	}
}
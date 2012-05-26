package com.titankingdoms.nodinchan.titanchat.addon;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.monstercraft.irc.ircplugin.IRC;
import org.monstercraft.irc.plugin.Configuration.Variables;
import org.monstercraft.irc.plugin.util.ColorUtils;
import org.monstercraft.irc.plugin.wrappers.IRCChannel;

import com.titankingdoms.nodinchan.titanchat.event.MessageSendEvent;

public class MonsterIRCHook extends Addon {
	
	public MonsterIRCHook() {
		super("MonsterIRCHook");
		init(this);
	}
	
	public String colourise(String text) {
		String colourised = text.replaceAll("(&([a-f0-9A-Fk-oK-O]))", "\u00C2\u00A7$2");
		return colourised;
	}
	
	@Override
	public void init() {
		if (plugin.getServer().getPluginManager().getPlugin("MonsterIRC") != null)
			register(this);
	}
	
	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onMessageSend(MessageSendEvent event) {
		String format = Variables.ircformat;
		format = format.replace("{name}", event.getSender().getName());
		format = format.replace("{world}", event.getSender().getWorld().getName());
		format = format.replace("{prefix)", plugin.getPermsBridge().getPlayerPrefix(event.getSender()));
		format = format.replace("{suffix}", plugin.getPermsBridge().getPlayerSuffix(event.getSender()));
		format = format.replace("{groupPrefix)", plugin.getPermsBridge().getGroupPrefix(event.getSender()));
		format = format.replace("{groupSuffix}", plugin.getPermsBridge().getGroupSuffix(event.getSender()));
		format = format.replace("{message}", ColorUtils.NORMAL.getIRCColor() + " " + event.getMessage());
		format = colourise(format);
		format = format.replace(ColorUtils.WHITE.getMinecraftColor(), ColorUtils.NORMAL.getIRCColor());
		
		for (IRCChannel channel : Variables.channels)
			IRC.sendMessage(channel, ColorUtils.formatGameMessage(format));
	}
}
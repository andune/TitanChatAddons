package com.titankingdoms.nodinchan.titanchat.addon;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;

import com.ensifera.animosity.craftirc.CraftIRC;
import com.ensifera.animosity.craftirc.EndPoint;
import com.ensifera.animosity.craftirc.RelayedMessage;
import com.titankingdoms.nodinchan.titanchat.TitanChat;
import com.titankingdoms.nodinchan.titanchat.channel.Channel;

public class ChannelPoint implements EndPoint {
	
	private final TitanChat plugin;
	
	private final CraftIRC craftIRC;
	
	private final String name;
	
	public ChannelPoint(TitanChat plugin, CraftIRC craftIRC, String name) {
		this.plugin = plugin;
		this.craftIRC = craftIRC;
		this.name = name;
	}
	
	@Override
	public boolean adminMessageIn(RelayedMessage msg) {
		return false;
	}
	
	public String colourise(String text) {
		String colourised = text.replaceAll("(&([a-f0-9A-Fk-oK-O]))", "\u00A7$2");
		return colourised;
	}
	
	@Override
	public Type getType() {
		return Type.MINECRAFT;
	}
	
	@Override
	public List<String> listDisplayUsers() {
		Channel channel = TitanChat.getInstance().getChannelManager().getChannel(name);
		List<String> users = new ArrayList<String>();
		users.addAll(channel.getParticipants());
		users.addAll(TitanChat.getInstance().getChannelManager().getFollowers(channel));
		return users;
	}
	
	@Override
	public List<String> listUsers() {
		Channel channel = TitanChat.getInstance().getChannelManager().getChannel(name);
		List<String> users = new ArrayList<String>();
		users.addAll(channel.getParticipants());
		users.addAll(TitanChat.getInstance().getChannelManager().getFollowers(channel));
		return users;
	}
	
	@Override
	public void messageIn(RelayedMessage msg) {
		TitanChat.getInstance().getChannelManager().getChannel(name).send(msg.getMessage());
	}
	
	public void messageOut(Player sender, String msg) {
		RelayedMessage message = craftIRC.newMsg(this, null, "TitanChat");
		message.setField("message", colourise(msg));
		message.setField("sender", sender.getName());
		message.setField("prefix", plugin.getPermsBridge().getPlayerPrefix(sender));
		message.setField("suffix", plugin.getPermsBridge().getPlayerSuffix(sender));
		message.post();
	}
	
	@Override
	public boolean userMessageIn(String username, RelayedMessage msg) {
		return false;
	}
}
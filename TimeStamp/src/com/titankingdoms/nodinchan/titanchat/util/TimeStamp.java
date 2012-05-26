package com.titankingdoms.nodinchan.titanchat.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import com.titankingdoms.nodinchan.titanchat.addon.Addon;
import com.titankingdoms.nodinchan.titanchat.channel.Channel;
import com.titankingdoms.nodinchan.titanchat.event.MessageFormatEvent;
import com.titankingdoms.nodinchan.titanchat.util.variable.Variable.IVariable;

public final class TimeStamp extends Addon {
	
	public TimeStamp() {
		super("TimeStamp");
		init(this);
	}
	
	@Override
	public void init() {
		getConfig().options().copyDefaults(true);
		saveConfig();
		
		plugin.getVariableManager().register(new IVariable() {
			
			@Override
			public Class<? extends Event> getEvent() {
				return MessageFormatEvent.class;
			}
			
			@Override
			public String getReplacement(Player sender, Player... recipants) {
				String format = "";
				
				Channel channel = plugin.getChannelManager().getChannel(sender);
				
				if (channel != null) {
					format = getConfig().getString("channels." + channel.getName() + ".timestamp");
					
					if (format == null)
						format = getConfig().getString("timestamp");
					
				} else { format = getConfig().getString("timestamp"); }
				
				return new SimpleDateFormat(format).format(new Date(System.currentTimeMillis()));
			}
			
			@Override
			public String getVariable() {
				return "%time";
			}
		});
	}
}
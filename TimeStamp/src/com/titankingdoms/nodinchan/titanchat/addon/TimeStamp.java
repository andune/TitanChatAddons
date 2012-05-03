package com.titankingdoms.nodinchan.titanchat.addon;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;

import com.titankingdoms.nodinchan.titanchat.channel.Channel;
import com.titankingdoms.nodinchan.titanchat.events.MessageFormatEvent;

public final class TimeStamp extends Addon {
	
	private Calendar cal;
	private SimpleDateFormat sdf;
	
	public TimeStamp() {
		super("TimeStamp");
		init(this);
	}
	
	@Override
	public void init() {
		getConfig().options().copyDefaults(true);
		saveConfig();
		register(this);
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onMessageFormat(MessageFormatEvent event) {
		Channel channel = plugin.getChannelManager().getChannel(event.getSender());
		String format = "";
		
		if (channel == null)
			format = getConfig().getString("timestamp");
		else {
			format = getConfig().getString("channels." + channel.getName() + ".timestamp");
			
			if (format == null)
				format = getConfig().getString("timestamp");
		}
		
		sdf = new SimpleDateFormat(format);
		cal = Calendar.getInstance();
		event.setFormat(event.getFormat().replace("%time", sdf.format(cal.getTime())));
	}
}
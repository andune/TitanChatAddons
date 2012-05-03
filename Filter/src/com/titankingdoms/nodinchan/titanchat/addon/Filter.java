package com.titankingdoms.nodinchan.titanchat.addon;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;

import com.titankingdoms.nodinchan.titanchat.command.FilterCommand;
import com.titankingdoms.nodinchan.titanchat.events.MessageSendEvent;
import com.titankingdoms.nodinchan.titanchat.util.WordFilter;

public final class Filter extends Addon {
	
	private String censor;
	
	private final List<WordFilter> filters;
	
	public Filter() {
		super("Filter");
		this.filters = new ArrayList<WordFilter>();
		init(this);
	}
	
	@Override
	public void init() {
		getConfig().options().copyDefaults(true);
		saveConfig();
		
		this.censor = getConfig().getString("censor");
		
		if (getConfig().getStringList("phrases") != null) {
			for (String word : getConfig().getStringList("phrases")) {
				filters.add(new WordFilter(word.toString()));
			}
		}
		
		register(this);
		register(new FilterCommand(this));
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onMessageSend(MessageSendEvent event) {
		for (WordFilter filter : filters)
			event.setMessage(filter.replaceAll(event.getMessage(), censor));
	}
}
package com.titankingdoms.nodinchan.titanchat.util.filter;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;

import com.titankingdoms.nodinchan.titanchat.addon.Addon;
import com.titankingdoms.nodinchan.titanchat.event.MessageSendEvent;

public final class Filter extends Addon {
	
	private String censor;
	
	private final List<WordFilter> filters;
	
	public Filter() {
		super("Filter");
		init(this);
		this.filters = new ArrayList<WordFilter>();
	}
	
	public void createFilters() {
		filters.clear();
		
		if (getConfig().getStringList("phrases") != null) {
			for (String word : getConfig().getStringList("phrases")) {
				filters.add(new WordFilter(word.toString()));
			}
		}
	}
	
	@Override
	public void init() {
		getConfig().options().copyDefaults(true);
		saveConfig();
		
		this.censor = getConfig().getString("censor");
		
		createFilters();
		
		register(this);
		register(new FilterCommand(this));
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onMessageSend(MessageSendEvent event) {
		for (WordFilter filter : filters)
			event.setMessage(filter.replaceAll(event.getMessage(), censor));
	}
}
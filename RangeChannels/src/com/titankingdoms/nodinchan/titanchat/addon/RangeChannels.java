package com.titankingdoms.nodinchan.titanchat.addon;

import com.titankingdoms.nodinchan.titanchat.channel.GlobalChannel;
import com.titankingdoms.nodinchan.titanchat.channel.LocalChannel;
import com.titankingdoms.nodinchan.titanchat.channel.WorldChannel;

public final class RangeChannels extends Addon {
	
	public RangeChannels() {
		super("RangeChannels");
		init(this);
	}
	
	@Override
	public void init() {
		getConfig().options().copyDefaults(true);
		saveConfig();
		
		if (getConfig().getBoolean("LocalChannel.enable"))
			register(new LocalChannel(this));
		
		if (getConfig().getBoolean("WorldChannel.enable"))
			register(new WorldChannel(this));
		
		if (getConfig().getBoolean("GlobalChannel.enable"))
			register(new GlobalChannel(this));
	}
}
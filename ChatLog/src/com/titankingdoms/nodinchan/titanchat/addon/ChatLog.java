package com.titankingdoms.nodinchan.titanchat.addon;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;

import com.titankingdoms.nodinchan.titanchat.events.MessageSendEvent;

public class ChatLog extends Addon {
	
	private BufferedWriter out;
	
	private final SimpleDateFormat sdf;
	
	public ChatLog() {
		super("ChatLog");
		init(this);
		this.sdf = new SimpleDateFormat("[dd.MM.yy HH:mm:ss]");
		
		try {
			out = new BufferedWriter(new FileWriter(new File(plugin.getDataFolder(), "chat.log"), true));
			register(this);
			
		} catch (IOException e) {}
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onMessageSend(MessageSendEvent event) {
		try {
			out.write(sdf.format(Calendar.getInstance().getTime()) + " <" + event.getSender().getName() + "> " + event.getMessage() + "\n");
			out.flush();
			
		} catch (IOException e) {}
	}
}
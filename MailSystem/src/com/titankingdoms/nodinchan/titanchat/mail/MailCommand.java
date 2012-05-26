package com.titankingdoms.nodinchan.titanchat.mail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import com.titankingdoms.nodinchan.titanchat.command.Command;
import com.titankingdoms.nodinchan.titanchat.command.CommandID;
import com.titankingdoms.nodinchan.titanchat.command.CommandInfo;

public class MailCommand extends Command {
	
	private final MailSystem system;
	
	public MailCommand(MailSystem system) {
		this.system = system;
	}
	
	/**
	 * Mail Command - Manages mail
	 */
	@CommandID(name = "Mail", triggers = "mail", requireChannel = false)
	@CommandInfo(description = "Manages mail; \"/titanchat mail help\" for more info", usage = "mail <command> <arguments>")
	public void mail(Player player, String[] args) {
		if (args.length < 1) {
			plugin.getServer().dispatchCommand(player, "titanchat mail help");
			return;
		}
		
		if (args[0].equalsIgnoreCase("check")) {
			Mailbox mailbox = system.getMailbox(player.getName());
			
			try {
				int page = Integer.parseInt(args[1]) - 1;
				int numPages = mailbox.size() / 10;
				int start = page * 10;
				int end = start + 10;
				
				if (mailbox.size() % 10 != 0 && (numPages * 10) - mailbox.size() < 0)
					numPages++;
				
				if (end > mailbox.size())
					end = mailbox.size();
				
				if (page + 1 > 0 || page + 1 <= numPages) {
					player.sendMessage(ChatColor.AQUA + "=== TitanChat Mail System (" + (page + 1) + "/" + numPages + ") ===");
					for (int mailNum = start; mailNum < end; mailNum++) {
						Mail mail = mailbox.getMail().get(mailNum);
						String id = "ID: " + (mailNum + 1);
						String title = "Title: " + mail.getTitle();
						String sender = "Sender: " + mail.getSender();
						player.sendMessage(ChatColor.AQUA + " " + id + "  " + title + "  " + sender + "  " + ((mail.isRead()) ? "Read" : "Unread"));
					}
					
				} else {
					plugin.getServer().dispatchCommand(player, "titanchat mail check 1");
				}
				
			} catch (IndexOutOfBoundsException e) {
				plugin.getServer().dispatchCommand(player, "titanchat mail check 1");
				
			} catch (NumberFormatException e) {
				plugin.sendWarning(player, "Invalid Page Number");
			}
			
		} else if (args[0].equalsIgnoreCase("delete")) {
			Mailbox mailbox = system.getMailbox(player.getName());
			
			try {
				if (args.length < 2)
					throw new IndexOutOfBoundsException();
				
				List<Mail> selected = new ArrayList<Mail>();
				
				for (String arg : Arrays.copyOfRange(args, 1, args.length))
					try {
						int mail = Integer.parseInt(arg) - 1;
						
						if (mail >= 0 && mail < mailbox.size())
							selected.add(mailbox.readMail(mail));
						
					} catch (NumberFormatException e) {}
				
				if (selected.size() >= 1) {
					mailbox.deleteMail(selected);
					plugin.sendInfo(player, "Successfully deleted mail");
					
				} else { plugin.sendWarning(player, "Failed to find mail"); }
				
			} catch (IndexOutOfBoundsException e) {
				if (mailbox.getSelection().size() < 1) {
					plugin.sendWarning(player, "Failed to find mail");
					return;
				}
				
				List<Mail> selected = new ArrayList<Mail>();
				
				for (int mail : mailbox.getSelection())
					selected.add(mailbox.readMail(mail));
				
				mailbox.deleteMail(selected);
				mailbox.getSelection().clear();
				plugin.sendInfo(player, "Successfully deleted mail");
				
			} catch (NumberFormatException e) {
				if (args[0].equalsIgnoreCase("all")) {
					mailbox.getMail().clear();
					plugin.sendInfo(player, "Successfully deleted all mail");
					
				} else {
					plugin.sendWarning(player, "Failed to find mail");
				}
			}
			
		} else if (args[0].equalsIgnoreCase("help")) {
			player.sendMessage(ChatColor.AQUA + "=== TitanChat Mail System Help ===");
			player.sendMessage(ChatColor.AQUA + "check : Checks mailbox for mail");
			player.sendMessage(ChatColor.AQUA + "delete <all/id>... : Deletes selection");
			player.sendMessage(ChatColor.AQUA + "help : Displays this help");
			player.sendMessage(ChatColor.AQUA + "read [id] : Reads the mail");
			player.sendMessage(ChatColor.AQUA + "[sel/select] [all/id]... : Selects the list of mail");
			player.sendMessage(ChatColor.AQUA + "send [target] [message] : Sends the mail to the target");
			player.sendMessage(ChatColor.AQUA + "setread <all/id>... : Sets all the mail to read");
			player.sendMessage(ChatColor.AQUA + "setunread <all/id>... : Sets all the mail to unread");
			player.sendMessage(ChatColor.AQUA + "Prepend with \"/titanchat mail \" before usage");
			
		} else if (args[0].equalsIgnoreCase("read")) {
			try {
				Mail mail = system.getMailbox(player.getName()).readMail(Integer.parseInt(args[1]) - 1);
				player.sendMessage(ChatColor.GREEN + "Sender: " + ChatColor.WHITE + mail.getSender());
				player.sendMessage(ChatColor.GREEN + "Date: " + ChatColor.WHITE + mail.getDateTime());
				player.sendMessage(ChatColor.GREEN + "Title: " + ChatColor.WHITE + mail.getTitle());
				player.sendMessage(ChatColor.GREEN + "Message: " + ChatColor.WHITE + mail.getMessage());
				mail.setRead(true);
				
			} catch (Exception e) { plugin.sendWarning(player, "Failed to find mail"); }
			
		} else if (args[0].equalsIgnoreCase("sel") || args[0].equalsIgnoreCase("select")) {
			Mailbox mailbox = system.getMailbox(player.getName());
			
			List<Integer> mailList = new ArrayList<Integer>();
			
			for (String arg : args) {
				try {
					int mail = Integer.parseInt(arg) - 1;
					
					if (mail >= 0 && mail < mailbox.size())
						mailList.add(mail);
					
				} catch (NumberFormatException e) {}
			}
			
			Collections.sort(mailList);
			
			mailbox.getSelection().clear();
			mailbox.getSelection().addAll(mailList);
			plugin.sendInfo(player, "You have selected " + mailList.size() + " mail");
			
		} else if (args[0].equalsIgnoreCase("send")) {
			if (args.length < 2) {
				plugin.sendWarning(player, "You require at least 1 word in your message");
				return;
			}
			
			Mailbox mailbox = system.getMailbox(args[1]);
			
			if (mailbox == null) {
				OfflinePlayer offline = plugin.getServer().getOfflinePlayer(args[1]);
				
				if (offline.hasPlayedBefore()) {
					mailbox = system.loadMailbox(offline.getName());
					
				} else {
					plugin.sendWarning(player, args[1] + " does not have a Mailbox");
					return;
				}
			}
			
			StringBuilder str = new StringBuilder();
			
			for (String arg : Arrays.copyOfRange(args, 1, args.length)) {
				if (str.length() > 0)
					str.append(" ");
				
				str.append(arg);
			}
			
			mailbox.receiveMail(player.getName(), str.toString());
			
			if (plugin.getPlayer(args[1]) != null) {
				Player target = plugin.getPlayer(args[0]);
				plugin.sendInfo(player, target.getDisplayName() + " is online, you can talk to him/her directly");
				plugin.sendInfo(target, "You received an email from " + player.getDisplayName());
				
				if (mailbox.getUnreadMail() > 0)
					plugin.sendInfo(target, "You have " + mailbox.getUnreadMail() + " unread mail");
			}
			
			plugin.sendInfo(player, "You successfully sent the message to " + args[1]);
			
		} else if (args[0].equalsIgnoreCase("setread")) {
			Mailbox mailbox = system.getMailbox(player.getName());
			
			try {
				if (args.length < 1)
					throw new IndexOutOfBoundsException();
				
				for (String arg : Arrays.copyOfRange(args, 1,  args.length)) {
					try {
						int mail = Integer.parseInt(arg) - 1;
						
						if (mail >= 0 && mail < mailbox.size())
							mailbox.readMail(mail).setRead(true);
						
					} catch (NumberFormatException e) {}
				}
				
				plugin.sendInfo(player, "Successfully set mail as read");
				
			} catch (IndexOutOfBoundsException e) {
				if (mailbox.getSelection().size() < 1) {
					plugin.sendWarning(player, "Failed to find mail");
					return;
				}
				
				for (int mail : mailbox.getSelection())
					mailbox.readMail(mail).setRead(true);
				
				plugin.sendInfo(player, "Successfully set selection as read");
				
			} catch (NumberFormatException e) {
				if (args[0].equalsIgnoreCase("all")) {
					for (Mail mail : mailbox.getMail())
						mail.setRead(true);
					
					plugin.sendInfo(player, "Successfully set all mail as read");
					
				} else { plugin.sendWarning(player, "Failed to find mail"); }
			}
			
		} else if (args[0].equalsIgnoreCase("setunread")) {
			Mailbox mailbox = system.getMailbox(player.getName());
			
			try {
				if (args.length < 1)
					throw new IndexOutOfBoundsException();
				
				for (String arg : Arrays.copyOfRange(args, 1,  args.length)) {
					try {
						int mail = Integer.parseInt(arg) - 1;
						
						if (mail >= 0 && mail < mailbox.size())
							mailbox.readMail(mail).setRead(false);
						
					} catch (NumberFormatException e) {}
				}
				
				plugin.sendInfo(player, "Successfully set mail as read");
				
			} catch (IndexOutOfBoundsException e) {
				if (mailbox.getSelection().size() < 1) {
					plugin.sendWarning(player, "Failed to find mail");
					return;
				}
				
				for (int mail : mailbox.getSelection())
					mailbox.readMail(mail).setRead(false);
				
				plugin.sendInfo(player, "Successfully set selection as read");
				
			} catch (NumberFormatException e) {
				if (args[0].equalsIgnoreCase("all")) {
					for (Mail mail : mailbox.getMail())
						mail.setRead(false);
					
					plugin.sendInfo(player, "Successfully set all mail as read");
					
				} else { plugin.sendWarning(player, "Failed to find mail"); }
			}
			
		} else {
			plugin.sendWarning(player, "Invalid Mail Command");
		}
	}
}
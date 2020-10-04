package me.atticuszambrana.banana.command;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

import com.thecloudyco.override.api.ManagerAPI;
import com.thecloudyco.override.common.OverrideType;

import me.atticuszambrana.banana.Core;
import me.atticuszambrana.banana.command.impl.test.OverrideCommand;
import me.atticuszambrana.banana.override.OverrideManager;
import me.atticuszambrana.banana.util.Console;
import me.atticuszambrana.banana.util.StringUtil;

public class CommandListener implements MessageCreateListener {
	
	private Map<String, Command> Commands = new HashMap<>();
	
	public CommandListener() {
		register(new OverrideCommand());
	}

	@Override
	public void onMessageCreate(MessageCreateEvent event) {
		// I actually want to block any commands from being ran, unless they are in a server.
		if(event.isPrivateMessage() || event.isGroupMessage()) {
			// Check to see if we were asking the user for a managers override
			if(OverrideManager.needsOverride(event.getMessageAuthor().asUser().get())) {
				String key = event.getMessageContent();
				
				if(key.equalsIgnoreCase("CANCEL")) {
					OverrideManager.NeedsOverride.remove(event.getMessageAuthor().asUser().get());
				}
				
				ManagerAPI mAPI = new ManagerAPI();
				try {
					if(mAPI.isAuthorized(OverrideType.DISCORD, key)) {
						OverrideManager.NeedsOverride.remove(event.getMessageAuthor().asUser().get());
						EmbedBuilder embed = new EmbedBuilder();
						embed.setColor(Color.GREEN);
						embed.setTitle("Thank you.");
						embed.setDescription("Override Accepted. You are now authorized to complete manager actions for `5 MINUTES`. Please re-run your previous command.");
						event.getChannel().sendMessage(embed);
						
						OverrideManager.Supervisors.add(event.getMessageAuthor().asUser().get());
						Console.print("Managers Override", event.getMessageAuthor().asUser().get().getName() + "(" + event.getMessageAuthor().asUser().get().getId() + ") has used an override!");
						
						new Thread() {
							public void run() {
								// 5 Minutes
								try {
									Thread.sleep(300000);
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								Console.print("Managers Override", event.getMessageAuthor().asUser().get().getName() + "(" + event.getMessageAuthor().asUser().get().getId() + ") Override has just expired!");
								OverrideManager.Supervisors.remove(event.getMessageAuthor().asUser().get());
							}
						}.start();
						
						return;
						// Done
					} else {
						// The user isnt authorized to complete this action
						EmbedBuilder embed = new EmbedBuilder();
						embed.setColor(Color.RED);
						embed.setTitle("Not Authorized");
						embed.setDescription("You are not authorized to complete this action. Please enter another override key or type `CANCEL` to cancel.");
						event.getChannel().sendMessage(embed);
						return;
					}
				} catch (Exception e) {
					EmbedBuilder embed = new EmbedBuilder();
					embed.setColor(Color.RED);
					embed.setTitle("Not Authorized");
					embed.setDescription("You are not authorized to complete this action. Please enter another override key or type `CANCEL` to cancel.");
					event.getChannel().sendMessage(embed);
					return;
				}
			}
			
			// If not then literally do nothing
		}
		
		if(!event.getMessageContent().startsWith(Core.bananaBot.getPrefix())) {
			return;
		}
		
		for(Map.Entry<String, Command> cmd : Commands.entrySet()) {
			if(event.getMessageContent().startsWith(Core.bananaBot.getPrefix() + cmd.getKey())) {
				cmd.getValue().execute(StringUtil.toArray(event.getMessageContent()), event);
			}
		}
	}
	
	private void register(Command cmd) {
		Commands.put(cmd.getCommand(), cmd);
	}
	
	public Command getCommand(String name) {
		return Commands.get(name);
	}
	
	public Map<String, Command> getCommands() {
		return Commands;
	}
}

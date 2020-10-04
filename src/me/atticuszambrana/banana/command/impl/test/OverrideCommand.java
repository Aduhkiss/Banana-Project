package me.atticuszambrana.banana.command.impl.test;

import java.awt.Color;

import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;

import me.atticuszambrana.banana.command.Command;
import me.atticuszambrana.banana.override.OverrideManager;

public class OverrideCommand extends Command {

	public OverrideCommand() {
		super("override", "Makes you a supervisor for 5 minutes", true);
	}

	@Override
	public void execute(String[] args, MessageCreateEvent event) {
		User author = event.getMessageAuthor().asUser().get();
		
		if(OverrideManager.isSupervisor(author)) {
			EmbedBuilder em = new EmbedBuilder();
			em.setColor(Color.GREEN);
			em.setDescription("You are already a supervisor. There is no need to override.");
			event.getChannel().sendMessage(em);
			return;
		} else {
			EmbedBuilder em = new EmbedBuilder();
			em.setColor(Color.RED);
			em.setTitle("MO   NOT AUTHORIZED");
			em.setDescription("Requires Managers Override - Please enter manager override key in direct message.");
			event.getChannel().sendMessage(em);
			OverrideManager.add(author);
			return;
		}
	}
	
}

package com.crimsonvalkyrie.discordbot.misc;

import com.crimsonvalkyrie.discordbot.chat.MessageFrame;
import com.crimsonvalkyrie.discordbot.main.Main;
import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import com.jagrosh.jdautilities.command.CommandListener;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.ArrayList;
import java.util.List;

public class BotCommandListener implements CommandListener
{
	private static final List<MessageFrame> frames = new ArrayList<>();

	public void add(MessageFrame panel)
	{
		frames.add(panel);
	}

	public void remove(MessageFrame panel)
	{
		if(frames.remove(panel))
		{
			Main.getLogger().debug("Successfully removed Message Panel");
		}
		else
		{
			Main.getLogger().debug("Failed to remove Message Panel");
		}
	}

	public void onCommand(CommandEvent event, Command command)
	{
	}

	public void onCompletedCommand(CommandEvent event, Command command)
	{
	}

	public void onTerminatedCommand(CommandEvent event, Command command)
	{
	}

	public void onNonCommandMessage(MessageReceivedEvent event)
	{
		Main.getLogger().debug("Processing Message");
		Message message = event.getMessage();
		TextChannel channel = message.getTextChannel();
		for(MessageFrame frame : frames)
		{
			Main.getLogger().debug("Checking");
			if(channel == frame.getMessagePanel().getTextChannel())
			{
				Main.getLogger().debug("Adding message");
				frame.addMessage(message);
				break;
			}
		}
	}
}
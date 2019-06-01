package com.crimsonvalkyrie.discordbot.commands.music;

import com.crimsonvalkyrie.discordbot.main.Bot;
import com.crimsonvalkyrie.discordbot.misc.music.GuildMusicManager;
import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import net.dv8tion.jda.core.entities.VoiceChannel;

abstract class MusicCommand extends Command
{
	boolean ignoreUserChannelRequirements;

	MusicCommand()
	{
		category = new Category("Music");
	}

	protected void execute(CommandEvent event)
	{
		GuildMusicManager musicManager = Bot.getMusicManager(event.getGuild());
		VoiceChannel userVoiceChannel = event.getMember().getVoiceState().getChannel();
		VoiceChannel botVoiceChannel = event.getSelfMember().getVoiceState().getChannel();

		boolean ignoreAllRequirements = false;
		if(!ignoreAllRequirements && musicManager == null)
		{
			botNotInChannel(event);
		}
		else if((!ignoreAllRequirements || !ignoreUserChannelRequirements) && userVoiceChannel == null)
		{
			userNotInChannel(event);
		}
		else if((!ignoreAllRequirements || !ignoreUserChannelRequirements) && userVoiceChannel != botVoiceChannel)
		{
			userDoesNotShareChannel(event);
		}
		else
		{
			requirementsMet(event, musicManager);
		}
	}

	void botNotInChannel(CommandEvent event)
	{
		event.reply("I need to be in a channel to do that");
	}

	private void userNotInChannel(CommandEvent event)
	{
		event.reply("You have to be in the same channel as the bot to do that");
	}

	private void userDoesNotShareChannel(CommandEvent event)
	{
		event.reply("You have to be in the same channel as the bot to do that");
	}

	protected abstract void requirementsMet(CommandEvent event, GuildMusicManager musicManager);
}
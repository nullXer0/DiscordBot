package com.crimsonvalkyrie.discordbot.commands.music;

import com.crimsonvalkyrie.discordbot.main.Bot;
import com.crimsonvalkyrie.discordbot.misc.music.GuildMusicManager;
import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import net.dv8tion.jda.core.entities.VoiceChannel;

public abstract class MusicCommand extends Command
{
	protected boolean ignoreUserChannelRequirements = false;
	protected final boolean ignoreAllRequirements = false;

	MusicCommand()
	{
		category = new Category("Music");
	}

	protected void execute(CommandEvent event)
	{
		GuildMusicManager musicManager = Bot.getMusicManager(event.getGuild());
		VoiceChannel userVoiceChannel = event.getMember().getVoiceState().getChannel();
		VoiceChannel botVoiceChannel = event.getSelfMember().getVoiceState().getChannel();

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

	public void botNotInChannel(CommandEvent event)
	{
		event.reply("I need to be in a channel to do that");
	}

	public void userNotInChannel(CommandEvent event)
	{
		event.reply("You have to be in the same channel as the bot to do that");
	}

	public void userDoesNotShareChannel(CommandEvent event)
	{
		event.reply("You have to be in the same channel as the bot to do that");
	}

	public abstract void requirementsMet(CommandEvent event, GuildMusicManager musicManager);
}
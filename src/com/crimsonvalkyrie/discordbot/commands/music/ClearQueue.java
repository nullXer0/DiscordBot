package com.crimsonvalkyrie.discordbot.commands.music;

import com.jagrosh.jdautilities.command.CommandEvent;
import com.crimsonvalkyrie.discordbot.misc.music.GuildMusicManager;

public class ClearQueue extends MusicCommand
{
	public ClearQueue()
	{
		name = "clearQueue";
		aliases = new String[]{"clearQ"};

		help = "Clears the current song queue excluding the currently playing song";
	}

	public void requirementsMet(CommandEvent event, GuildMusicManager musicManager)
	{
		musicManager.scheduler.clearQueue();
	}
}

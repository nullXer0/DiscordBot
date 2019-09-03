package com.crimsonvalkyrie.discordbot.commands.music;

import com.jagrosh.jdautilities.command.CommandEvent;
import com.crimsonvalkyrie.discordbot.misc.music.GuildMusicManager;

public class Next extends MusicCommand
{
	public Next()
	{
		name = "skip";
		aliases = new String[]{"next"};

		help = "Skips the current song and plays the next song in the queue";
	}

	public void requirementsMet(CommandEvent event, GuildMusicManager musicManager)
	{
		musicManager.scheduler.nextTrack();
	}
}

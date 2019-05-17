package com.crimsonvalkyrie.discordbot.commands.music;

import com.crimsonvalkyrie.discordbot.misc.music.GuildMusicManager;
import com.jagrosh.jdautilities.command.CommandEvent;

public class Stop extends MusicCommand
{
	public Stop()
	{
		name = "stop";

		help = "Removes the current song from the queue without playing the next song";
	}

	public void requirementsMet(CommandEvent event, GuildMusicManager musicManager)
	{
		musicManager.player.stopTrack();
	}
}
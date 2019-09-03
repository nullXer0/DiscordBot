package com.crimsonvalkyrie.discordbot.commands.music;

import com.crimsonvalkyrie.discordbot.misc.music.GuildMusicManager;
import com.jagrosh.jdautilities.command.CommandEvent;

public class Pause extends MusicCommand
{
	public Pause()
	{
		name = "pause";
		aliases = new String[]{"resume"};

		help = "Pauses/resumes the current song";
	}

	public void requirementsMet(CommandEvent event, GuildMusicManager musicManager)
	{
		musicManager.player.setPaused(!musicManager.player.isPaused());
	}
}
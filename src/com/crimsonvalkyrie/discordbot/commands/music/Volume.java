package com.crimsonvalkyrie.discordbot.commands.music;

import com.jagrosh.jdautilities.command.CommandEvent;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.crimsonvalkyrie.discordbot.misc.music.GuildMusicManager;

public class Volume extends MusicCommand
{
	public Volume()
	{
		name = "volume";
		arguments = "<volume>";

		help = "Sets the volume";
	}

	public void requirementsMet(CommandEvent event, GuildMusicManager musicManager)
	{
		AudioPlayer player = musicManager.player;
		int oldVolume = player.getVolume();
		try
		{
			int newVolume = Integer.valueOf(event.getArgs());
			player.setVolume(Math.min(newVolume, event.isOwner()?1000:100));
			event.reply("Changed Volume from " + oldVolume + "% to " + player.getVolume() + "%");
		}
		catch(NumberFormatException e)
		{
			event.reply("Current Volume: " + oldVolume);
		}
	}
}

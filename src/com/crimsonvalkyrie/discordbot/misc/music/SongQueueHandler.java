package com.crimsonvalkyrie.discordbot.misc.music;

import com.jagrosh.jdautilities.command.CommandEvent;
import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.crimsonvalkyrie.discordbot.main.Bot;
import com.crimsonvalkyrie.discordbot.main.Main;
import org.apache.logging.log4j.Logger;

public class SongQueueHandler implements AudioLoadResultHandler
{
	private CommandEvent event;

	private static Logger logger = Main.getLogger();

	public SongQueueHandler(CommandEvent event)
	{
		this.event = event;
	}

	public void trackLoaded(AudioTrack track)
	{
		GuildMusicManager manager = Bot.getMusicManager(event.getGuild());
		logger.info("Given link is a song");
		manager.scheduler.queue(track);
		if(manager.player.getPlayingTrack() == null)
		{
			event.reply("Playing: " + track.getInfo().title + " - " + track.getInfo().author);
		}
		else
		{
			event.reply("Queued: " + track.getInfo().title + " - " + track.getInfo().author);
		}
	}

	public void playlistLoaded(AudioPlaylist playlist)
	{
		logger.info("Given link is a playlist");
		for(AudioTrack track : playlist.getTracks())
		{
			Bot.getMusicManager(event.getGuild()).scheduler.queue(track);
		}
	}

	public void noMatches()
	{
		// Notify the user that we've got nothing
		event.reply("No matches were found");
		logger.info("No matches were found");
	}

	public void loadFailed(FriendlyException throwable)
	{
		// Notify the user that everything exploded
		event.reply("Please let <@89622569740697600> know that something broke");
		logger.error("Error when queuing song: {}", throwable.getMessage());
	}
}

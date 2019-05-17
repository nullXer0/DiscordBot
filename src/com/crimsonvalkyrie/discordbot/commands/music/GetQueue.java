package com.crimsonvalkyrie.discordbot.commands.music;

import com.jagrosh.jdautilities.command.CommandEvent;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.crimsonvalkyrie.discordbot.misc.music.GuildMusicManager;

public class GetQueue extends MusicCommand
{
	public GetQueue()
	{
		name = "listQueue";
		aliases = new String[]{"listQ"};

		help = "Lists the current playing song and any upcoming songs";

		ignoreUserChannelRequirements = true;
	}

	public void requirementsMet(CommandEvent event, GuildMusicManager musicManager)
	{
		StringBuilder message = new StringBuilder();

		if(musicManager.player.getPlayingTrack() != null)
		{
			message.append("Current Song: '").append(musicManager.player.getPlayingTrack().getInfo().title).append('\'');
		}

		Object[] queue = musicManager.scheduler.getQueueAsArray();

		if(queue.length > 0)
		{
			message.append("Upcoming Songs:\n");
		}

		for(Object track : queue)
		{
			message.append("\n'").append(((AudioTrack) track).getInfo().title).append('\'');
		}

		event.reply(message.toString());
	}
}
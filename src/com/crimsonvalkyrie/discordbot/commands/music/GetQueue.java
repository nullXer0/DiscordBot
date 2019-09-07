package com.crimsonvalkyrie.discordbot.commands.music;

import com.crimsonvalkyrie.discordbot.misc.music.GuildMusicManager;
import com.jagrosh.jdautilities.command.CommandEvent;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.MessageBuilder;

import java.awt.*;

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
		MessageBuilder messageBuilder = new MessageBuilder();
		EmbedBuilder embedBuilder = new EmbedBuilder().setColor(new Color(7212421));
		StringBuilder upcomingString = new StringBuilder();

		if(musicManager.player.getPlayingTrack() != null)
		{
			AudioTrackInfo trackInfo = musicManager.player.getPlayingTrack().getInfo();
			embedBuilder.addField("Current Song", trackInfo.title + " - " + trackInfo.author, false);
		}

		Object[] queue = musicManager.scheduler.getQueueAsArray();

		if(queue.length > 0)
		{
			for(Object track : queue)
			{
				AudioTrackInfo trackInfo = ((AudioTrack) track).getInfo();
				if(upcomingString.length() + trackInfo.title.length() + trackInfo.author.length() < 1014)
				{
					upcomingString.append("\n'").append(trackInfo.title).append(" - ").append(trackInfo.author).append('\'');
				}
				else
				{
					upcomingString.append("\n...");
					break;
				}
			}
			embedBuilder.addField("Upcoming Songs (" + queue.length + " in queue)", upcomingString.toString(), false);
		}

		messageBuilder.setEmbed(embedBuilder.build());
		event.reply(messageBuilder.build());
	}
}
package com.crimsonvalkyrie.discordbot.misc.music;

import com.crimsonvalkyrie.discordbot.main.Bot;
import com.jagrosh.jdautilities.command.CommandEvent;
import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.MessageBuilder;

import java.awt.*;
import java.io.File;

class AudioPlayerManager extends DefaultAudioPlayerManager
{
	public void multiLoadItem(final File[] songArray, final AudioLoadResultHandler resultHandler, CommandEvent event)
	{
		MessageBuilder messageBuilder = new MessageBuilder();
		EmbedBuilder embedBuilder = new EmbedBuilder().setColor(new Color(7212421));
		StringBuilder stringBuilder = new StringBuilder();
		boolean hasSongPlaying = Bot.getMusicManager(event.getGuild()).player.getPlayingTrack() != null;
		if(!hasSongPlaying)
		{
			embedBuilder.addField("Playing", songArray[0].getName(), false);
		}
		for(File file : songArray)
		{
			stringBuilder.append("\n").append(file.getName());
			loadItem(file.getAbsolutePath(), resultHandler);
		}
		embedBuilder.addField("Queued", stringBuilder.toString(), false);
	}
}
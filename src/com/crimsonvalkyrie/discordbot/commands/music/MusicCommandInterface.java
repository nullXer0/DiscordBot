package com.crimsonvalkyrie.discordbot.commands.music;

import com.jagrosh.jdautilities.command.CommandEvent;
import com.crimsonvalkyrie.discordbot.misc.music.GuildMusicManager;

public interface MusicCommandInterface
{
	void botNotInChannel(CommandEvent event);
	void userNotInChannel(CommandEvent event, GuildMusicManager musicManager);
	void userDoesNotShareChannel(CommandEvent event, GuildMusicManager musicManager);
	void requirementsMet(CommandEvent event, GuildMusicManager musicManager);
}

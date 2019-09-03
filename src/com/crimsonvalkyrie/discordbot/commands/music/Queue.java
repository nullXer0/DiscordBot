package com.crimsonvalkyrie.discordbot.commands.music;

import com.crimsonvalkyrie.discordbot.main.Bot;
import com.crimsonvalkyrie.discordbot.main.Main;
import com.crimsonvalkyrie.discordbot.misc.music.GuildMusicManager;
import com.crimsonvalkyrie.discordbot.misc.music.SongQueueHandler;
import com.jagrosh.jdautilities.command.CommandEvent;
import org.apache.logging.log4j.Logger;

public class Queue extends MusicCommand
{
	private static final Logger logger = Main.getLogger();

	public Queue()
	{
		name = "queue";
		aliases = new String[]{"q", "play"};
		arguments = "<link>";

		help = "Adds a song to the queue";
	}

	public void requirementsMet(CommandEvent event, GuildMusicManager musicManager)
	{
		String identifier = event.getArgs();

		logger.info("{} queued something\nServer: {}, Channel: {}, Song link: {}",
				event.getAuthor().getName(),
				event.getGuild().getName(),
				event.getTextChannel().getName(),
				identifier);

		Bot.getPlayerManager().loadItem(identifier, new SongQueueHandler(event));
	}
}
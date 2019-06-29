package com.crimsonvalkyrie.discordbot.commands.music;

import com.crimsonvalkyrie.discordbot.misc.music.GuildMusicManager;
import com.jagrosh.jdautilities.command.CommandEvent;
import net.dv8tion.jda.core.EmbedBuilder;

import java.awt.*;

public class SearchYT extends MusicCommand
{
	public SearchYT()
	{
		name = "search";
		arguments = "<query>";

		help = "Searches YouTube for a song to queue";
		ignoreAllRequirements = true;
	}

	protected void requirementsMet(CommandEvent event, GuildMusicManager musicManager)
	{
		event.reply("You searched the following");
		event.reply(event.getArgs());
		event.reply(new EmbedBuilder()
				.setTitle("__Results__")
				.setColor(new Color(13632027))
				.setFooter("Daily Queries Remaining: 99", null)
				.addField("Song 1", "[​Camellia (Feat. Nanahira) - Can I Friend You On Bassbook ? Lol [Bassline Yatteru ? LOL]](https://youtu.be/55AalrbALAk)", false)
				.addField("Song 2", "[Camellia ft. Nanahira - Bassline Yatteru? w | Can I Friend You On Bassbook ? Lol](https://youtu.be/jgW7w-SCnAs)", false)
				.addField("Song 3", "[[Beat Saber] Bassline Yatteru? w - 1307/1321 - Rank S (84.5%)](https://youtu.be/lZrqCJgNfD4)", false)
				.addField("Song 4", "[Camellia - Bassline Yatteru? w On Drums (First Try!) -- The8BitDrummer](https://youtu.be/y1YAkn9yCjY)", false)
				.addField("Song 5", "[Bassline Yatteru -- Beat Saber](https://youtu.be/zsl6SYOwFgU)", false)
				.build());
	}
}

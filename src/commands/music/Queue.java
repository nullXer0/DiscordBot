package commands.music;

import com.jagrosh.jdautilities.command.CommandEvent;
import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import main.Bot;
import main.Main;
import misc.music.GuildMusicManager;
import misc.music.SongQueueHandler;
import org.apache.logging.log4j.Logger;

public class Queue extends MusicCommand implements MusicCommandInterface
{
	private static Logger logger = Main.getLogger();

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
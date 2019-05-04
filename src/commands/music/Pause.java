package commands.music;

import com.jagrosh.jdautilities.command.CommandEvent;
import misc.music.GuildMusicManager;

public class Pause extends MusicCommand implements MusicCommandInterface
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
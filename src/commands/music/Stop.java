package commands.music;

import com.jagrosh.jdautilities.command.CommandEvent;
import misc.music.GuildMusicManager;

public class Stop extends MusicCommand implements MusicCommandInterface
{
	public Stop()
	{
		name = "stop";

		help = "Removes the current song from the queue without playing the next song";
	}

	public void requirementsMet(CommandEvent event, GuildMusicManager musicManager)
	{
		musicManager.player.stopTrack();
	}
}
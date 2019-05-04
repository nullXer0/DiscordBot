package commands.music;

import com.jagrosh.jdautilities.command.CommandEvent;
import main.Bot;
import misc.music.GuildMusicManager;

public class Leave extends MusicCommand
{
	public Leave()
	{
		name = "leave";
		cooldown = 10;

		help = "Removes the bot from the current voice channel";
	}

	public void requirementsMet(CommandEvent event, GuildMusicManager musicManager)
	{
		Bot.removeAudioChannel(event.getGuild());
	}
}
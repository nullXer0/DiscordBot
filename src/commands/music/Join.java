package commands.music;

import com.jagrosh.jdautilities.command.CommandEvent;
import main.Bot;
import misc.music.GuildMusicManager;

public class Join extends MusicCommand implements MusicCommandInterface
{
	public Join()
	{
		name = "join";
		cooldown = 10;

		help = "Joins the voice channel of the user that send the message";

		ignoreUserChannelRequirements = true;
	}

	@Override
	public void botNotInChannel(CommandEvent event)
	{
		Bot.addAudioChannel(event.getMember());
	}

	public void requirementsMet(CommandEvent event, GuildMusicManager musicManager)
	{
		event.reply("I am already in a voice channel. If you want to move me, please do !leave then !join, you can also drag me to the correct channel if you have the permission");
	}
}
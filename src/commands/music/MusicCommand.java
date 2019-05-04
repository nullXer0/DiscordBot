package commands.music;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import main.Bot;
import misc.music.GuildMusicManager;
import net.dv8tion.jda.core.entities.VoiceChannel;

public abstract class MusicCommand extends Command implements MusicCommandInterface
{
	protected boolean ignoreUserChannelRequirements = false;
	protected boolean ignoreAllRequirements = false;

	MusicCommand()
	{
		category = new Category("Music");
	}

	protected void execute(CommandEvent event)
	{
		GuildMusicManager musicManager = Bot.getMusicManager(event.getGuild());
		VoiceChannel userVoiceChannel = event.getMember().getVoiceState().getChannel();
		VoiceChannel botVoiceChannel = event.getSelfMember().getVoiceState().getChannel();

		if(!ignoreAllRequirements && musicManager == null)
		{
			botNotInChannel(event);
		}
		else if((!ignoreAllRequirements || !ignoreUserChannelRequirements) && userVoiceChannel == null)
		{
			userNotInChannel(event, musicManager);
		}
		else if((!ignoreAllRequirements || !ignoreUserChannelRequirements) && userVoiceChannel != botVoiceChannel)
		{
			userDoesNotShareChannel(event, musicManager);
		}
		else
		{
			requirementsMet(event, musicManager);
		}
	}

	public void botNotInChannel(CommandEvent event)
	{
		event.reply("I need to be in a channel to do that");
	}

	public void userNotInChannel(CommandEvent event, GuildMusicManager musicManager)
	{
		event.reply("You have to be in the same channel as the bot to do that");
	}

	public void userDoesNotShareChannel(CommandEvent event, GuildMusicManager musicManager)
	{
		event.reply("You have to be in the same channel as the bot to do that");
	}

	public abstract void requirementsMet(CommandEvent event, GuildMusicManager musicManager);
}
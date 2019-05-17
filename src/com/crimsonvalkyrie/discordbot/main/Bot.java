package com.crimsonvalkyrie.discordbot.main;

import com.jagrosh.jdautilities.command.CommandClientBuilder;
import com.jagrosh.jdautilities.command.CommandListener;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.crimsonvalkyrie.discordbot.misc.music.GuildMusicManager;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.*;
import net.dv8tion.jda.core.managers.AudioManager;
import org.apache.logging.log4j.Logger;

import javax.security.auth.login.LoginException;
import java.util.HashMap;

public class Bot
{
	private static JDA jda;

	private static AudioPlayerManager playerManager;
	private static HashMap<Guild, AudioManager> audioManagers = new HashMap<>();
	private static HashMap<Guild, GuildMusicManager> musicManagers = new HashMap<>();

	private static Logger logger = Main.getLogger();

	public Bot(String token, String prefix, String ownerID, CommandListener listener) throws LoginException, IllegalArgumentException, InterruptedException
	{
		CommandClientBuilder cmdClient = new CommandClientBuilder().setOwnerId(ownerID)
				.setPrefix(prefix)
				.setEmojis("✅", "⚠", "🔥");
		if(listener != null)
		{
			cmdClient.setListener(listener);
		}
		else
		{
			logger.error("No Listener Provided");
		}

		playerManager = new DefaultAudioPlayerManager();
		AudioSourceManagers.registerRemoteSources(playerManager);
		AudioSourceManagers.registerLocalSource(playerManager);

		Commands.initialize(cmdClient);

		jda = new JDABuilder(AccountType.BOT).setToken(token).addEventListener(cmdClient.build()).build().awaitReady();
	}

	static JDA getJDA()
	{
		return jda;
	}

	public static String getName()
	{
		return jda.getSelfUser().getName();
	}

	public static AudioPlayerManager getPlayerManager()
	{
		return playerManager;
	}

	public static Message sendMessage(String message, MessageChannel channel)
	{
		return channel.sendMessage(message).complete();
	}

	public static boolean isInGuildVoiceChannel(Guild guild)
	{
		return audioManagers.containsKey(guild);
	}

	public static void addAudioChannel(Member member)
	{
		Guild guild = member.getGuild();
		if(!audioManagers.containsKey(guild))
		{
			VoiceChannel channel = member.getVoiceState().getChannel();
			if(channel != null)
			{
				audioManagers.put(guild, guild.getAudioManager());
				musicManagers.put(guild, new GuildMusicManager(playerManager));
				audioManagers.get(guild).setSendingHandler(musicManagers.get(guild).getSendHandler());
				audioManagers.get(guild).openAudioConnection(channel);
				musicManagers.get(guild).player.setVolume(10);
			}
		}
	}

	public static void removeAudioChannel(Guild guild)
	{
		if(audioManagers.containsKey(guild))
		{
			audioManagers.get(guild).closeAudioConnection();
			audioManagers.remove(guild);
			musicManagers.remove(guild);
		}
	}

	public static GuildMusicManager getMusicManager(Guild guild)
	{
		return musicManagers.get(guild);
	}
}
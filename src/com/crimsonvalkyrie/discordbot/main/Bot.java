package com.crimsonvalkyrie.discordbot.main;

import com.crimsonvalkyrie.discordbot.commands.Commands;
import com.jagrosh.jdautilities.command.CommandClientBuilder;
import com.jagrosh.jdautilities.command.CommandListener;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import org.apache.logging.log4j.Logger;

import javax.security.auth.login.LoginException;

public class Bot
{
	private static JDA jda;

	private static final Logger logger = Main.getLogger();

	Bot(String token, String prefix, String ownerID, CommandListener listener) throws LoginException, IllegalArgumentException, InterruptedException
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

		Commands.initialize(cmdClient);

		jda = new JDABuilder(AccountType.BOT).setToken(token).addEventListener(cmdClient.build()).build().awaitReady();
	}

	static JDA getJDA()
	{
		return jda;
	}

	static String getName()
	{
		return jda.getSelfUser().getName();
	}

	public static Message sendMessage(String message, MessageChannel channel)
	{
		return channel.sendMessage(message).complete();
	}
}
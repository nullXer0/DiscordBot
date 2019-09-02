package com.crimsonvalkyrie.discordbot.commands;

import com.jagrosh.jdautilities.command.CommandClientBuilder;
import com.crimsonvalkyrie.discordbot.commands.owner.Shutdown;

public class Commands
{
	public static void initialize(CommandClientBuilder cmdClient)
	{
		cmdClient.addCommands(

				//Owner Commands
				new Shutdown());
	}
}
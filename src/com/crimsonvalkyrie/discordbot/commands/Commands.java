package com.crimsonvalkyrie.discordbot.commands;

import com.crimsonvalkyrie.discordbot.commands.image.E621;
import com.crimsonvalkyrie.discordbot.commands.owner.Shutdown;
import com.jagrosh.jdautilities.command.CommandClientBuilder;

public class Commands
{
	public static void initialize(CommandClientBuilder cmdClient)
	{
		cmdClient.addCommands(

				//Owner Commands
				new Shutdown(),

				//Image Commands
				new E621());
	}
}
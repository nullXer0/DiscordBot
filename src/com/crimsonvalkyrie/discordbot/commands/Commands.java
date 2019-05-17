package com.crimsonvalkyrie.discordbot.commands;

import com.jagrosh.jdautilities.command.CommandClientBuilder;
import com.crimsonvalkyrie.discordbot.commands.image.GTN;
import com.crimsonvalkyrie.discordbot.commands.image.Rem;
import com.crimsonvalkyrie.discordbot.commands.music.*;
import com.crimsonvalkyrie.discordbot.commands.owner.EvalCmd;
import com.crimsonvalkyrie.discordbot.commands.owner.Shutdown;

public class Commands
{
	public static void initialize(CommandClientBuilder cmdClient)
	{
		cmdClient.addCommands(

				//Owner Commands
				new Shutdown(),
				new EvalCmd(),

				//Image Commands
				new Rem(),
				new GTN(),

				//Music Commands
				new Join(),
				new Leave(),
				new Queue(),
				new Pause(),
				new Stop(),
				new Next(),
				new Volume(),
				new GetQueue(),
				new ClearQueue(),
				new RandomLocalSong());
	}
}
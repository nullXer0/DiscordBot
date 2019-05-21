package com.crimsonvalkyrie.discordbot.commands.image;

import com.jagrosh.jdautilities.command.CommandEvent;

public class GTN extends ImageCommand
{
	public GTN()
	{
		super();
		name = "gtn";

		help = "Posts a comic from GreenTeaNeko (NSFW)";

		type = "nsfw-gtn";
		nsfw = true;
	}

	protected void execute(CommandEvent event)
	{
		if(event.getTextChannel().isNSFW())
		{
			super.execute(event);
		}
		else
		{
			event.reply("You can only do this command in NSFW channels");
		}
	}
}
package com.crimsonvalkyrie.discordbot.commands.image;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import kong.unirest.GetRequest;
import kong.unirest.HeaderNames;
import kong.unirest.Unirest;
import org.json.JSONPointerException;

public class E621 extends Command
{
	final GetRequest request = Unirest.get("https://e621.net/post/index.json")
			.header(HeaderNames.USER_AGENT, "FurryBoy/1.0 (admin@crimsonvalkyrie.com)")
			.queryString("limit", 1);

	public E621()
	{
		name = "e621";
		arguments = "[tags]";
		guildOnly = false;


		help = "gets a single image from e621";
	}

	protected void execute(CommandEvent event)
	{
		String[] arguments = event.getArgs().split(" ");

		try
		{
			event.reply("" + request.queryString("tags", "order:random " + event.getArgs())
					.asJson().getBody().getArray().query("/0/file_url"));
		}
		catch(JSONPointerException e)
		{
			if(e.getMessage().equals("index 0 is out of bounds - the array has 0 elements"))
			{
				event.reply("No Results");
			}
		}
	}
}

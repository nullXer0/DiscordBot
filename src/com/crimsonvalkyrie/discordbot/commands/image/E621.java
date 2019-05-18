package com.crimsonvalkyrie.discordbot.commands.image;

import com.crimsonvalkyrie.discordbot.main.Main;
import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import kong.unirest.GetRequest;
import kong.unirest.HeaderNames;
import kong.unirest.Unirest;
import net.dv8tion.jda.core.entities.ChannelType;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;

public class E621 extends Command
{
	Logger logger = Main.getLogger();

	final GetRequest getRequest = Unirest.get("https://e621.net/post/index.json")
			.header(HeaderNames.USER_AGENT, "FurryBoy/1.1 (admin@crimsonvalkyrie.com)");

	public E621()
	{
		name = "e621";
		arguments = "[c=count] [tag1 tag2...]";
		guildOnly = false;
		cooldown = 2;


		help = "gets images from e621. Limit=5 per message";
	}

	protected void execute(CommandEvent event)
	{
		if(event.isFromType(ChannelType.PRIVATE) || event.getTextChannel().isNSFW())
		{
			GetRequest request;
			String[] arguments;
			String tags = "";
			int count = 1;
			JSONArray response;
			StringBuilder reply;

			logger.info("{} requested some nudes\nArguments: {}", event.getAuthor(), event.getArgs());
			arguments = event.getArgs().split("(?<=(c=\\d)) ");
			logger.info("Arguments split into {} arguments", arguments.length);

			if(arguments.length == 1 && arguments[0].startsWith("c=") && arguments[0].contains(" "))
			{
				logger.info("Invalid argument: {}", arguments[0]);
				reply = new StringBuilder("Invalid Arguments");
			}
			else
			{
				reply = new StringBuilder();

				if(arguments.length > 1)
				{
					tags = arguments[1];
				}
				if(arguments[0].contains("c="))
				{
					try
					{
						count = Integer.valueOf(arguments[0].replace("c=", ""));
						if(count > 5)
						{
							reply.append("Limit is 5, but ").append(count).append(" was requested, changing to 5");
							count = 5;
						}
					}
					catch(NumberFormatException e)
					{
						reply.append("\n").append("Invalid count given, defaulting to 1");
					}
				}
				else
				{
					tags = arguments[0];
				}

				request = getRequest.queryString("limit", count);
				response = request.queryString("tags", "order:random " + tags).asJson().getBody().getArray();

				if(response.length() > 0)
				{
					if(response.length() < count)
					{
						logger.info("Given count was higher than amount returned");
						reply.append("\n").append("Requested ").append(count).append(", but only got ").append(response.length()).append(" results");
						count = response.length();
					}
					for(int i = 0; i < count; i++)
					{
						reply.append("\n").append(response.query("/" + i + "/file_url"));
					}
				}
				else
				{
					logger.info("No results found");
					reply.append("No results");
				}
			}
			event.reply(reply.toString());
		}
		else
		{
			event.reply("This is an NSFW only command");
		}
	}
}
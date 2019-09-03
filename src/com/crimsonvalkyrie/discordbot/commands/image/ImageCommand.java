package com.crimsonvalkyrie.discordbot.commands.image;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import kong.unirest.GetRequest;
import kong.unirest.HeaderNames;
import kong.unirest.Unirest;
import net.dv8tion.jda.core.entities.ChannelType;
import org.json.JSONArray;

class ImageCommand extends Command
{
	private final GetRequest request = Unirest.get("https://rra.ram.moe/i/r").header(HeaderNames.USER_AGENT, "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:65.0) Gecko/20100101 Firefox/65.0");

	/**
	 * ?type=nsfw-gtn&nsfw=true
	 */
	String type = "";
	boolean nsfw = false;

	ImageCommand()
	{
		category = new Category("Image");
		guildOnly = false;
	}

	protected void execute(CommandEvent event)
	{
		if(nsfw && !(event.isFromType(ChannelType.PRIVATE) || event.getTextChannel().isNSFW()))
		{
			event.reply("You can only do this command in NSFW channels");
		}
		else
		{
			JSONArray response = request.queryString("type", type).queryString("nsfw", nsfw).asJson().getBody().getArray();
			event.reply("https://rra.ram.moe" + response.query("/0/path").toString());
		}
	}
}

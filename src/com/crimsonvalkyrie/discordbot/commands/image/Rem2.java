package com.crimsonvalkyrie.discordbot.commands.image;

import com.crimsonvalkyrie.discordbot.main.Main;
import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import kong.unirest.GetRequest;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;

import java.util.Random;

public class Rem2 extends Command
{
	private final Logger logger = Main.getLogger();

	private final Random random = new Random();

	public Rem2()
	{
		name = "rem2";
		guildOnly = false;
		cooldown = 2;

		category = new Category("Image");

	}

	protected void execute(CommandEvent event)
	{
		// The request to zerochan for rem images
		GetRequest request;
		// The raw response from zerochan
		HttpResponse<String> response;
		// The formatted response given from zerochan
		JsonNode formattedResponse;
		// The list of images from the request
		JSONArray imageArray;
		// The ID of the selected image
		int imageID;
		// The information about the selected image
		JSONArray imageResponse;

		logger.info("{} requested a picture of rem", event.getAuthor().getAsTag());

		// Request page in rem images in a random order at a random page
		request = Unirest.get("https://www.zerochan.net/Rem+(Re%3AZero)%2CSolo,-Not+Safe+For+Work/")
				.queryString("s", "random").queryString("json", "").queryString("p", random.nextInt(50) + 1);

		// Get response from zerochan
		response = request.asString();

		// Remove ad data inserted into the json
		formattedResponse = new JsonNode(response.getBody().replaceAll("\\n<div([\\s\\S]+)/div>", ""));

		// Get only the list of images
		imageArray = (JSONArray) formattedResponse.getArray().query("/0/items");
		try
		{
			logger.info("Images found: {}", imageArray.length());
		}
		catch(NullPointerException e)
		{
			logger.error("Error when getting rem images\nRequest link: {}\nRequest response: {}\nJSON response:\n{}", request.getUrl(), response.getStatusText(), response.getBody());
		}

		imageID = (int) imageArray.query("/" + random.nextInt(imageArray.length()) + "/id");
		logger.info("The ID is: {}", imageID);

		imageResponse = Unirest.get("https://www.zerochan.net/" + imageID).queryString("json", "").asJson().getBody().getArray();
		event.reply((String) imageResponse.query("/0/full"));
	}
}
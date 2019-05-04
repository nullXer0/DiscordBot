package commands.image;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;
import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class ImageCommand extends Command
{
	//private static final WebClient webClient = new WebClient();

	/**
	 * ?type=nsfw-gtn&nsfw=true
	 */
	protected String type = "";

	public ImageCommand()
	{
		category = new Category("Image");
	}

	@Override
	protected void execute(CommandEvent event)
	{
		try
		{
			URL url = new URL("https://rra.ram.moe/i/r" + type);
			URLConnection connection = url.openConnection();
			connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:65.0) Gecko/20100101 Firefox/65.0");

			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			JsonValue json = Json.parse(reader);
			JsonObject object = json.asObject();
			event.reply("https://rra.ram.moe" + object.get("path").asString());
			reader.close();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}

}

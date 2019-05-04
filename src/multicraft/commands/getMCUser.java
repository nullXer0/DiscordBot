package multicraft.commands;

import com.eclipsesource.json.ParseException;
import com.jagrosh.jdautilities.command.CommandEvent;
import org.json.JSONObject;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

public class getMCUser extends MCCommand
{
	private static Reader reader;

	public getMCUser()
	{
		this.name = "getMCUser";
	}

	protected void execute(CommandEvent event)
	{
		JSONObject jsonObject;
		String author = event.getAuthor().getId();
		try
		{
			reader = new FileReader("/MCUsers.json");
			jsonObject = (JSONObject) parser.parse(reader);
			if(jsonObject.get(author) == null)
			{
				event.reply("You are not registered");
			}
			else
			{
				event.reply((String) jsonObject.get(author));
			}
		}
		catch(IOException | ParseException e)
		{
			event.reply("<@89622569740697600> what did you do...");
			e.printStackTrace();
		}
	}
}

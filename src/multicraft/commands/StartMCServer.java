package multicraft.commands;

import com.eclipsesource.json.ParseException;
import com.jagrosh.jdautilities.command.CommandEvent;
import multicraft.MCCommands;
import org.json.JSONObject;

public class StartMCServer extends MCCommand
{
	public StartMCServer()
	{
		this.name = "startServer";
		this.cooldown = 60;
	}

	protected void execute(CommandEvent event)
	{
		try
		{
			if((boolean) ((JSONObject) parser.parse(MCCommands.startServer("1"))).get("success"))
			{
				event.reply("Starting Server");
			}
			else
			{
				event.reply("Failed to Start Server");
			}
		}
		catch(ParseException e)
		{
			event.reply("Failed to Send Command, <@89622569740697600> pls");
			e.printStackTrace();
		}
	}
}
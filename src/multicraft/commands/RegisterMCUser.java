package multicraft.commands;

import com.eclipsesource.json.ParseException;
import com.jagrosh.jdautilities.command.CommandEvent;
import org.json.JSONObject;

import java.io.*;


public class RegisterMCUser extends MCCommand
{
	private static Reader reader;
	private static Writer writer;

	public RegisterMCUser()
	{
		this.name = "registerMCUser";
		this.ownerCommand = true;
		this.arguments = "<dUser> <mcUser>";
	}

	@SuppressWarnings("unchecked")
	protected void execute(CommandEvent event)
	{
		String[] args = event.getArgs().split(" ");
		String userID = event.getMessage().getMentionedUsers().get(0).getId();
		JSONObject jsonObject;
		try
		{
			try
			{
				reader = new FileReader("/MCUsers.json");
				jsonObject = (JSONObject) parser.parse(reader);
				reader.close();
			}
			catch(IOException e)
			{
				jsonObject = new JSONObject();
			}
			jsonObject.put(userID, args[1]);
			writer = new FileWriter("/MCUsers.json");
			jsonObject.writeJSONString(writer);
			writer.close();
			event.reply("Registered " + args[0] + " as " + args[1]);
		}
		catch(IOException | ParseException e)
		{
			event.reply("<@89622569740697600> stop breaking things");
			e.printStackTrace();
		}
	}
}

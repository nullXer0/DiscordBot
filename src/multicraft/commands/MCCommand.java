package multicraft.commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

public class MCCommand extends Command
{
	static JSONParser parser = new JSONParser();

	MCCommand()
	{
		this.guildOnly = true;
		this.category = new Category("Minecraft");
	}

	protected void execute(CommandEvent event)
	{
	}
}
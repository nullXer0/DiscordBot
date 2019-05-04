package commands.owner;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import main.Main;

public class Shutdown extends Command
{
	public Shutdown()
	{
		name = "shutdown";
		guildOnly = false;
		ownerCommand = true;

		help = "Shuts the bot down";
	}

	protected void execute(CommandEvent event)
	{
		event.reply("fhsdhjfdsfjsddshjfsd (dies)");
		Main.shutdown();
	}

}

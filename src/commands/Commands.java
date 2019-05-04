package commands;

import com.jagrosh.jdautilities.command.CommandClientBuilder;
import commands.image.GTN;
import commands.image.Rem;
import commands.music.*;
import commands.owner.EvalCmd;
import commands.owner.Shutdown;

public class Commands
{
	public static void initialize(CommandClientBuilder cmdClient)
	{
		cmdClient.addCommands(

				//Owner Commands
				new Shutdown(),
				new EvalCmd(),

				//Image Commands
				new Rem(),
				new GTN(),

				//Music Commands
				new Join(),
				new Leave(),
				new Queue(),
				new Pause(),
				new Stop(),
				new Next(),
				new Volume(),
				new GetQueue(),
				new ClearQueue(),
				new RandomLocalSong());
	}
}
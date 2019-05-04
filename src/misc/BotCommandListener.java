package misc;

import chat.MessageFrame;
import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import com.jagrosh.jdautilities.command.CommandListener;
import main.Main;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class BotCommandListener implements CommandListener
{
	private static List<MessageFrame> frames = new ArrayList<>();

	private static Logger logger = Main.getLogger();

	public void add(MessageFrame panel)
	{
		frames.add(panel);
	}

	public void remove(MessageFrame panel)
	{
		if(frames.remove(panel))
		{
			logger.debug("Successfully removed Message Panel");
		}
		else
		{
			logger.debug("Failed to remove Message Panel");
		}
	}

	public void onCommand(CommandEvent event, Command command)
	{
	}

	public void onCompletedCommand(CommandEvent event, Command command)
	{
	}

	public void onTerminatedCommand(CommandEvent event, Command command)
	{
	}

	public void onNonCommandMessage(MessageReceivedEvent event)
	{
		logger.debug("Processing Message");
		Message message = event.getMessage();
		TextChannel channel = message.getTextChannel();
		for(MessageFrame frame : frames)
		{
			logger.debug("Checking");
			if(channel == frame.getMessagePanel().getTextChannel())
			{
				logger.debug("Adding message");
				frame.addMessage(message);
				break;
			}
		}
	}
}

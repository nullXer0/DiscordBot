package commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import main.Main;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.TextChannel;
import org.apache.logging.log4j.Logger;

import java.awt.*;
import java.time.OffsetDateTime;

public class TennoConTimer extends Command
{
	private static final Long TIME_UNTIL_TICKETS = 1551380400L;

	private static final EmbedBuilder EMBED = new EmbedBuilder()
			.setTitle("Countdown until TennoCon tickets go on sale", "https://www.warframe.com/tennocon2019")
			.setColor(new Color(7798954))
			.setTimestamp(OffsetDateTime.parse("2019-02-28T19:00:00Z"))
			.setFooter("Counting down to", null)
			.setAuthor("TennoCon Timer", null, null);

	private static Logger logger = Main.getLogger();

	TennoConTimer()
	{
		name = "tennocon";
		ownerCommand = true;
	}

	protected void execute(CommandEvent event)
	{
		new Thread(() ->
		{
			TextChannel channel = event.getTextChannel();
			Message message = channel.sendMessage(EMBED.setDescription(getRemainingTime()).build()).complete();
			if(getTotalRemainingSeconds() > 0)
			{
				logger.info("Starting TennoCon Loop");
			}
			while(getTotalRemainingSeconds() > 0)
			{
				try
				{
					Thread.sleep(60000);
				}
				catch(InterruptedException e)
				{
					e.printStackTrace();
				}
				message.editMessage(EMBED.setDescription(getRemainingTime()).build()).queue();
			}
		}).start();
	}

	private static Long getTotalRemainingSeconds()
	{
		return TIME_UNTIL_TICKETS - System.currentTimeMillis() / 1000;
	}

	private static String getRemainingTime()
	{
		long totalSeconds;
		int days, hours, minutes;

		totalSeconds = getTotalRemainingSeconds();

		days = (int) totalSeconds / 86400;
		hours = (int) totalSeconds % 86400 / 3600;
		minutes = (int) totalSeconds % 86400 % 3600 / 60;

		return (days > 0 ? days + " day" + (days > 1 ? "s, " : ", ") : "")
				+ (hours > 0 ? hours + " hour" + (hours > 1 ? "s, " : ", ") : "")
				+ minutes + " minute" + (minutes > 1 ? "s" : "");
	}
}
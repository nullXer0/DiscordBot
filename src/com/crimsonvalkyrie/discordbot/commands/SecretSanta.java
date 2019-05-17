package com.crimsonvalkyrie.discordbot.commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.*;

import java.util.List;
import java.util.Random;

public class SecretSanta extends Command
{
	private Random rando = new Random();

	public SecretSanta()
	{
		super();
		ownerCommand = true;
		name = "santa";
	}

	protected void execute(CommandEvent event)
	{
		JDA jda = event.getJDA();

		final Guild guild = jda.getGuildById(188929540968415233L);
		final TextChannel channel = guild.getTextChannelById(524389258409279508L);
		final Message message = channel.getMessageById(524389575687274511L).complete();
		final MessageReaction reaction = message.getReactions().get(0);
		final List<User> santaless = reaction.getUsers().complete();
		for(User user : reaction.getUsers().complete())
		{
			int num;

			do
			{
				num = rando.nextInt(santaless.size());
			}
			while(user == santaless.get(num));

			user.openPrivateChannel().complete().sendMessage("You are the secret santa for " + santaless.get(num).getName()).queue();

			santaless.remove(num);
		}
	}
}

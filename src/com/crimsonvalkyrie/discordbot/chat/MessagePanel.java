package com.crimsonvalkyrie.discordbot.chat;

import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.TextChannel;
import net.miginfocom.layout.AC;
import net.miginfocom.layout.LC;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;

public class MessagePanel extends JPanel
{
	private final MessageBox[] messages = new MessageBox[50];
	private final TextChannel channel;

	public MessagePanel(TextChannel channel)
	{
		AC columnConstraints = new AC().fill();
		LC layoutConstraints = new LC().bottomToTop().fillX().noVisualPadding().insets("0", "0", "0", "0").gridGap("0", "0");
		MigLayout migLayout = new MigLayout(layoutConstraints, columnConstraints);
		setLayout(migLayout);
		this.channel = channel;
	}

	public TextChannel getTextChannel()
	{
		return channel;
	}

	public void addMessage(Message message)
	{
		removeAll();
		if(messages.length - 1 >= 0) System.arraycopy(messages, 0, messages, 1, messages.length - 1);
		messages[0] = new MessageBox(message);
		for(MessageBox messageBox : messages)
		{
			if(messageBox != null)
			{
				add(messageBox, "wrap");
			}
		}
		revalidate();
	}
}
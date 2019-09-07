package com.crimsonvalkyrie.discordbot.chat;

import net.dv8tion.jda.api.entities.Message;

import javax.swing.*;

public class MessageBox extends JTextArea
{
	private Message message;

	MessageBox()
	{
		super();
		setEditable(false);
		setLineWrap(true);
		setWrapStyleWord(false);
		setFocusable(false);
	}

	MessageBox(Message message)
	{
		this();
		setMessage(message);
	}

	private void setMessage(Message message)
	{
		this.message = message;
		if(message != null)
		{
			setText("<" + message.getAuthor().getName() + ">: " + message.getContentStripped());
		}
		else
		{
			setText("");
		}
	}

	public Message getMessage()
	{
		return message;
	}
}
package com.crimsonvalkyrie.discordbot.menu;

import com.crimsonvalkyrie.discordbot.main.Main;
import net.dv8tion.jda.api.entities.TextChannel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.List;

public class ChannelMenu extends MenuBase
{
	final HashMap<MenuButton, TextChannel> channels = new HashMap<>();

	final MenuButton backButton;

	public ChannelMenu(List<TextChannel> channels)
	{
		super();
		setBackground(Color.black);
		backButton = new MenuButton(Color.lightGray);
		backButton.setText("GO BACK");
		backButton.setAlignment(SwingConstants.CENTER);
		addButton(backButton);
		for(TextChannel channel : channels)
		{
			MenuButton tempButton = new MenuButton(Color.lightGray);
			tempButton.setText(channel.getName());
			tempButton.setAlignment(SwingConstants.CENTER);
			addButton(tempButton);
			this.channels.put(tempButton, channel);
		}
	}

	protected void onClick(MouseEvent e)
	{
		if(e.getSource() == backButton)
		{
			Main.returnToMainMenu();
		}
		else
		{
			Main.OpenChat(channels.get(e.getSource()));
		}
		super.onClick(e);
	}
}
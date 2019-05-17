package com.crimsonvalkyrie.discordbot.misc;

import com.crimsonvalkyrie.discordbot.main.Main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TrayListener implements ActionListener
{
	public void actionPerformed(ActionEvent e)
	{
		if(e.getActionCommand() != null)
		{
			if(e.getActionCommand().equals("showWindow"))
			{
				Main.showMainWindow();
			}
			else if(e.getActionCommand().equals("exit"))
			{
				Main.shutdown();
			}
		}
	}
}
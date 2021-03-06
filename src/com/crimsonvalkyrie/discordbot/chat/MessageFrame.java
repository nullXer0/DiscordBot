package com.crimsonvalkyrie.discordbot.chat;

import com.crimsonvalkyrie.discordbot.main.Bot;
import net.dv8tion.jda.api.entities.Message;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MessageFrame extends JFrame implements ActionListener
{
	final JScrollPane scrollPane;
	final JScrollBar scrollBar;
	final MessagePanel messagePanel;
	final JTextField textField;

	public MessageFrame(MessagePanel mPanel)
	{
		messagePanel = mPanel;
		scrollPane = new JScrollPane(messagePanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollBar = new JScrollBar(JScrollBar.VERTICAL);
		scrollPane.setVerticalScrollBar(scrollBar);
		textField = new JTextField();
		add(scrollPane, BorderLayout.CENTER);
		add(textField, BorderLayout.PAGE_END);
		textField.addActionListener(this);
		setMinimumSize(new Dimension(800, 500));
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		pack();
	}

	public MessagePanel getMessagePanel()
	{
		return messagePanel;
	}

	public void addMessage(Message message)
	{
		messagePanel.addMessage(message);
		scrollBar.setValue(scrollBar.getMaximum());
	}

	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource() == textField && !textField.getText().equals(""))
		{
			new Thread(() ->
			{
				String message = textField.getText();
				textField.setText("");
				addMessage(Bot.sendMessage(message, messagePanel.getTextChannel()));
			}).start();
		}
	}
}
package com.crimsonvalkyrie.discordbot.chat;

import com.crimsonvalkyrie.discordbot.main.Bot;
import net.dv8tion.jda.api.entities.Message;
import net.miginfocom.layout.LC;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MessageFrame extends JFrame implements ActionListener
{
	private final JScrollBar scrollBar;
	private final MessagePanel messagePanel;
	private final JTextArea textArea;
	private final JButton sendButton;


	public MessageFrame(MessagePanel mPanel)
	{
		//Initialize layout
		LC layoutConstraints = new LC().gridGap("0", "0").insetsAll("0");
		MigLayout layout = new MigLayout(layoutConstraints);
		setLayout(layout);

		//Initialize message panel
		messagePanel = mPanel;
		scrollBar = new JScrollBar(JScrollBar.VERTICAL);
		JScrollPane messageScrollPane = new JScrollPane(messagePanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		messageScrollPane.setVerticalScrollBar(scrollBar);

		//Initialize message field and send button
		textArea = new JTextArea();
		textArea.setRows(3);
		JScrollPane sendScrollPane = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		sendButton = new JButton("Send");
		sendButton.addActionListener(this);

		//Add message panel to layout
		add(messageScrollPane, "grow, push, spanx 2, wrap");

		//Add message field and send button to layout
		add(sendScrollPane, "grow 1, hmax 64px");
		add(sendButton, "shrink");

		//Prepare frame
		setTitle(messagePanel.getTextChannel().getName());
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
		System.out.println(textArea.getHeight());
		if(e.getSource() == sendButton && !textArea.getText().replaceAll("\\s", "").equals(""))
		{
			new Thread(() ->
			{
				String message = textArea.getText();
				textArea.setText("");
				for(int i = 0; i < (message.length() / 2000) + 1; i++)
				{
					addMessage(Bot.sendMessage(message.substring(2000 * i, Math.min(2000 * (i + 1), message.length())), messagePanel.getTextChannel()));
				}
			}).start();
		}
	}
}
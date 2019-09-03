package com.crimsonvalkyrie.discordbot.menu;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

class MenuButton extends JPanel
{
	private final JLabel label;
	private BufferedImage originalImage;

	private boolean hasImage = false;

	private MenuButton()
	{
		super();
		setLayout(null);
		label = new JLabel();
		add(label);
	}

	MenuButton(Color bg)
	{
		this();
		setBackground(bg);
	}

	@SuppressWarnings("unused")
	public MenuButton(Color bg, BufferedImage image)
	{
		this(bg);
		setImage(image);
	}

	void setText(String text)
	{
		label.setText(text);
	}

	void setImage(BufferedImage image)
	{
		originalImage = image;
		label.setIcon(new ImageIcon(originalImage.getScaledInstance(label.getHeight() - 4, label.getHeight() - 4, Image.SCALE_SMOOTH)));
		hasImage = true;
	}

	public void setSize(int width, int height)
	{
		super.setSize(width, height);
		label.setLocation(2, 2);
		label.setSize(width - 4, height - 4);
		if(hasImage)
		{
			resizeImage();
		}
	}

	private void resizeImage()
	{
		if(label.getIcon().getIconHeight() != label.getHeight() - 4)
		{
			label.setIcon(new ImageIcon(originalImage.getScaledInstance(label.getHeight() - 4, label.getHeight() - 4, Image.SCALE_SMOOTH)));
		}
	}

	void setAlignment(int alignment)
	{
		label.setHorizontalAlignment(alignment);
	}

	void setLabelFont(Font font)
	{
		label.setFont(font);
	}
}
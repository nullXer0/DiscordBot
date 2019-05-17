package com.crimsonvalkyrie.discordbot.menu;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class MenuButton extends JPanel
{
	private final JLabel label;
	private BufferedImage originalImage;

	private boolean hasImage = false;

	public MenuButton()
	{
		super();
		setLayout(null);
		label = new JLabel();
		add(label);
	}

	public MenuButton(Color bg)
	{
		this();
		setBackground(bg);
	}

	public MenuButton(Color bg, BufferedImage image)
	{
		this(bg);
		setImage(image);
	}

	public void setText(String text)
	{
		label.setText(text);
	}

	public void setImage(BufferedImage image)
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

	public void resizeImage()
	{
		if(label.getIcon().getIconHeight() != label.getHeight() - 4)
		{
			label.setIcon(new ImageIcon(originalImage.getScaledInstance(label.getHeight() - 4, label.getHeight() - 4, Image.SCALE_SMOOTH)));
		}
	}

	public void setAlignment(int alignment)
	{
		label.setHorizontalAlignment(alignment);
	}

	public void setLabelFont(Font font)
	{
		label.setFont(font);
	}
}
package com.crimsonvalkyrie.discordbot.menu;

import com.crimsonvalkyrie.discordbot.main.Main;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

public class MenuBase extends JScrollPane implements ComponentListener, MouseListener
{
	private final JPanel panel = new JPanel();

	private final List<MenuButton> buttons = new ArrayList<>();

	private boolean isClick;

	private static final Logger logger = Main.getLogger();

	MenuBase()
	{
		super();
		setViewportView(panel);
		setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		panel.addComponentListener(this);
		panel.setLayout(null);
		panel.setBackground(Color.black);
	}

	void addButton(MenuButton button)
	{
		buttons.add(button);
		panel.add(button);
		button.addMouseListener(this);
	}

	public void removeButton(MenuButton button)
	{
		buttons.remove(button);
		panel.remove(button);
	}

	private int getButtonHeight()
	{
		return 4 + (panel.getWidth() / 4 - 4);
	}

	private int getTotalHeight()
	{
		return 4 + ((panel.getWidth() / 4 - 4) * buttons.size());
	}

	// TODO: figure out how to fix maximizing
	public void updatePanel(int width, int height)
	{
		logger.debug("Updating panel");
		JPanel menuPanel = (JPanel) getViewport().getView();
		setSize(width, height);
		menuPanel.setPreferredSize(new Dimension(getWidth(), getTotalHeight()));
		getVerticalScrollBar().setUnitIncrement(getButtonHeight() / 2);
		menuPanel.revalidate();
	}

	public void componentResized(ComponentEvent event)
	{
		for(int i = 0; i < buttons.size(); i++)
		{
			buttons.get(i).setSize(panel.getWidth() - 8, panel.getWidth() / 4 - 8);
			buttons.get(i).setLocation(4, 4 + ((panel.getWidth() / 4 - 4) * i));
		}
	}

	public void componentShown(ComponentEvent event)
	{
	}

	public void mouseClicked(MouseEvent e)
	{
		logger.debug("Clicked");
	}

	public void mouseExited(MouseEvent e)
	{
		isClick = false;
	}

	public void mousePressed(MouseEvent e)
	{
		isClick = true;
		((Component) e.getSource()).setSize(panel.getWidth() - 12, panel.getWidth() / 4 - 12);
		((Component) e.getSource()).setLocation(6, 6 + ((panel.getWidth() / 4 - 4) * buttons.indexOf(e.getSource())));
	}

	public void mouseReleased(MouseEvent e)
	{
		if(isClick)
		{
			onClick(e);
		}
		((Component) e.getSource()).setSize(panel.getWidth() - 8, panel.getWidth() / 4 - 8);
		((Component) e.getSource()).setLocation(4, 4 + ((panel.getWidth() / 4 - 4) * buttons.indexOf(e.getSource())));
	}

	void onClick(MouseEvent e)
	{
		isClick = false;
	}

	public void componentHidden(ComponentEvent event)
	{
	}

	public void componentMoved(ComponentEvent event)
	{
	}

	public void mouseEntered(MouseEvent e)
	{
	}
}
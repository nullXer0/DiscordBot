package misc;

import main.Main;

import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

public class PanelListener implements ComponentListener
{

	public void componentHidden(ComponentEvent e)
	{
	}

	public void componentMoved(ComponentEvent e)
	{
	}

	public void componentResized(ComponentEvent e)
	{
		Main.updateActivePane();
	}

	public void componentShown(ComponentEvent e)
	{
	}
}

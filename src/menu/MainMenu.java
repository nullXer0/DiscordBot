package menu;

import main.Main;
import net.dv8tion.jda.core.entities.Guild;
import org.apache.logging.log4j.Logger;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.List;

public class MainMenu extends MenuBase
{
	private static Font FONT = new Font("Dialog", Font.ITALIC, 24);

	HashMap<MenuButton, Guild> guilds = new HashMap<>();

	private static Logger logger = Main.getLogger();

	public MainMenu(List<Guild> guilds)
	{
		super();
		setBackground(Color.black);
		for(Guild guild : guilds)
		{
			MenuButton tempButton = new MenuButton(Color.lightGray);
			tempButton.setLabelFont(FONT);
			tempButton.setText(guild.getName());
			String iconURL = guild.getIconUrl();
			if(iconURL != null)
			{
				try
				{
					URLConnection connection = new URL(iconURL).openConnection();
					connection.setRequestProperty("User-Agent", "Chrome");
					tempButton.setImage(ImageIO.read(connection.getInputStream()));
					connection.getInputStream().close();
				}
				catch(IOException e)
				{
					e.printStackTrace();
				}
			}
			else
			{
				logger.warn("{} does not have an icon", guild.getName());
			}
			addButton(tempButton);
			this.guilds.put(tempButton, guild);
		}
	}

	protected void onClick(MouseEvent e)
	{
		Main.selectGuild(guilds.get(e.getSource()));
		super.onClick(e);
	}
}
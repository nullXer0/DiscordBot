package com.crimsonvalkyrie.discordbot.main;

import com.crimsonvalkyrie.discordbot.chat.MessageFrame;
import com.crimsonvalkyrie.discordbot.chat.MessagePanel;
import com.crimsonvalkyrie.discordbot.menu.ChannelMenu;
import com.crimsonvalkyrie.discordbot.menu.MainMenu;
import com.crimsonvalkyrie.discordbot.menu.MenuBase;
import com.crimsonvalkyrie.discordbot.misc.BotCommandListener;
import com.crimsonvalkyrie.discordbot.misc.PanelListener;
import com.crimsonvalkyrie.discordbot.misc.TrayListener;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.TextChannel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.imageio.ImageIO;
import javax.security.auth.login.LoginException;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import java.awt.AWTException;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;
import java.util.Properties;

public class Main
{
	// Frames and panels
	private static JFrame frame;
	private static JPanel panel = new JPanel();
	private static MenuBase mainMenu;

	// Tray and window icon
	private static BufferedImage icon;

	// System Tray stuff
	private static TrayListener trayListener = new TrayListener();

	// JScrollPane stuff
	private static PanelListener pListener = new PanelListener();
	private static MenuBase activePane;

	// Guilds, TextChannels and MessageFrames
	private static HashMap<String, Guild> guilds = new HashMap<>();
	private static HashMap<Guild, TextChannel> channels = new HashMap<>();
	private static HashMap<Guild, MenuBase> channelMenus = new HashMap<>();
	private static HashMap<TextChannel, MessagePanel> messagePanels = new HashMap<>();
	private static HashMap<MessagePanel, MessageFrame> frames = new HashMap<>();

	// Config file
	private static Properties config = new Properties();

	// Bot stuff
	private static String token = "";
	private static String prefix = "!";
	private static String ownerID = "";
	private static BotCommandListener listener = new BotCommandListener();

	private static boolean nogui;

	private static final Logger logger = LogManager.getLogger("ValkyrLogger");

	public static void main(String[] args)
	{
		if(args.length > 0 && args[0].equals("nogui"))
		{
			nogui = true;
		}
		new Main();
	}

	private Main()
	{
		if(!nogui)
		{
			try
			{
				icon = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("Icon.png")));
			}
			catch(IOException e)
			{
				e.printStackTrace();
			}
		}
		init();
	}

	private static void init()
	{
		initConfig();

		try
		{
			new Bot(token, prefix, ownerID, listener);
		}
		catch(LoginException | InterruptedException e)
		{
			e.printStackTrace();
		}

		if(!nogui)
		{
			initSystemTray();
			initMenu();
		}
	}

	private static void initConfig()
	{
		File configFile = new File("Config.cfg");
		try
		{
			if(!configFile.createNewFile())
			{
				// Gets the token from the config file and starts the bot
				config.load(new FileInputStream(new File("Config.cfg")));

				token = config.getProperty("token");
				if(token.equals(""))
				{
					logger.error("No Token Found, please provide the token in config file");
					System.exit(0);
				}

				ownerID = config.getProperty("ownerID");
				if(ownerID.equals(""))
				{
					logger.error("No owner ID Found, please provide the owner ID in config file");
					System.exit(0);
				}

				prefix = config.getProperty("prefix");
				if(prefix.equals(""))
				{
					logger.warn("No prefix given, defaulting to !");
					config.setProperty("prefix", "!");
					prefix = "!";
				}
			}
			else
			{
				logger.error("No config file found, generating");
				config.load(new FileInputStream(new File("Config.cfg")));
				config.setProperty("token", "");
				config.setProperty("ownerID", "");
				config.setProperty("prefix", "!");
				logger.info("Config file created, please provide token and restart program");
			}
		}
		catch(IOException e)
		{
			logger.fatal("Fatal error encountered, aborting");
			e.printStackTrace();
			System.exit(0);
		}
	}

	private static void initSystemTray()
	{
		// Setting up the System Tray Menu
		PopupMenu trayMenu = new PopupMenu("test");
		trayMenu.addActionListener(trayListener);
		MenuItem titleMenuItem = new MenuItem(Bot.getJDA().getSelfUser().getName());
		MenuItem exitMenuItem = new MenuItem("Exit");
		exitMenuItem.setActionCommand("exit");
		titleMenuItem.setEnabled(false);
		trayMenu.add(titleMenuItem);
		trayMenu.addSeparator();
		trayMenu.add(exitMenuItem);

		// Setting up the System tray icon
		try
		{
			TrayIcon trayIcon = new TrayIcon(icon, Bot.getJDA().getSelfUser().getName(), trayMenu);
			trayIcon.setActionCommand("showWindow");
			trayIcon.addActionListener(trayListener);
			trayIcon.setImageAutoSize(true);
			SystemTray.getSystemTray().add(trayIcon);
		}
		catch(AWTException exception)
		{
			exception.printStackTrace();
		}
	}

	private static void initMenu()
	{
		// Setting up JFrame and JPanel for menus
		frame = new JFrame();
		frame.setIconImage(icon);
		panel.setLayout(null);
		frame.pack();
		frame.setMinimumSize(new Dimension(400, 600));
		frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		Container contentPane = frame.getContentPane();
		contentPane.add(panel);
		panel.addComponentListener(pListener);

		// Get list of Guilds
		for(Guild guild : Bot.getJDA().getGuilds())
		{
			guilds.put(guild.getId(), guild);

			// Create Channel Menus
			channelMenus.put(guild, new ChannelMenu(guild.getTextChannels()));
			channelMenus.get(guild).setVerticalScrollBar(new JScrollBar());
			channelMenus.get(guild).setVisible(false);
			panel.add(channelMenus.get(guild));

			// Get list of TextChannels
			for(TextChannel channel : guild.getTextChannels())
			{
				channels.put(guild, channel);

				// Create MessageFrames for each TextChannel
				MessagePanel mPanel = new MessagePanel(channel);
				MessageFrame mFrame = new MessageFrame(mPanel);
				listener.add(mFrame);
				messagePanels.put(channel, mPanel);
				frames.put(mPanel, mFrame);
			}
		}

		mainMenu = new MainMenu(Bot.getJDA().getGuilds());
		mainMenu.setVerticalScrollBar(new JScrollBar());
		panel.add(mainMenu);
		activePane = mainMenu;

		frame.setTitle(Bot.getName());

		frame.pack();
		frame.setVisible(true);
		updateActivePane();
	}

	public static void selectGuild(Guild guild)
	{
		activePane.setVisible(false);
		activePane = channelMenus.get(guild);
		activePane.setVisible(true);
		updateActivePane();
	}

	public static void returnToMainMenu()
	{
		activePane.setVisible(false);
		activePane = mainMenu;
		activePane.setVisible(true);
		updateActivePane();
	}

	// Opens MessageFrame for given TextChannel
	public static void OpenChat(TextChannel channel)
	{
		frames.get(messagePanels.get(channel)).setVisible(true);
	}

	public static void showMainWindow()
	{
		frame.setVisible(true);
	}

	public static void updateActivePane()
	{
		activePane.updatePanel(panel.getWidth(), panel.getHeight());
	}

	// Why are you reading this? did you forget what the word "shutdown" means?
	public static void shutdown()
	{
		Bot.getJDA().shutdown();
		System.exit(0);
	}

	public static Logger getLogger()
	{
		return logger;
	}
}

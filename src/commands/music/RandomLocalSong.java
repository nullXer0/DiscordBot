package commands.music;

import com.jagrosh.jdautilities.command.CommandEvent;
import main.Bot;
import main.Main;
import misc.music.GuildMusicManager;
import misc.music.SongQueueHandler;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.util.Random;

public class RandomLocalSong extends MusicCommand implements MusicCommandInterface
{
	private static FileFilter directoryFilter = File::isDirectory;

	private static FilenameFilter songFilter = (dir, name) -> name.toLowerCase().endsWith(".flac")
			|| name.toLowerCase().endsWith(".mp3")
			|| name.toLowerCase().endsWith(".wav")
			|| name.toLowerCase().endsWith(".m4a");

	private static Random rand = new Random();

	private static File musicDirectory;

	private static Logger logger = Main.getLogger();

	public RandomLocalSong()
	{
		name = "randomSong";

		help = "Queues a random song from the bots local directory";

		musicDirectory = new File("music");
		if(!musicDirectory.exists() || !musicDirectory.isDirectory())
		{
			musicDirectory = new File("/music");
		}
	}

	public void requirementsMet(CommandEvent event, GuildMusicManager musicManager)
	{
		File chosenDirectory;
		File chosenSong;

		logger.info("{} Queued a random song", event.getAuthor().getName());

		chosenDirectory = getRandomDirectory(musicDirectory);
		if(chosenDirectory != null)
		{
			logger.info("Chosen directory: {}", chosenDirectory.getName());
			chosenSong = getRandomSong(chosenDirectory);
			if(chosenSong != null)
			{
				logger.info("Chosen song: {}", chosenSong.getName());
				Bot.getPlayerManager().loadItem(chosenSong.getAbsolutePath(), new SongQueueHandler(event));
			}
		}
	}

	/**
	 * Returns a random directory from the given directory
	 *
	 * @param startingDir The directory to search
	 * @return A random directory
	 */
	private static File getRandomDirectory(File startingDir)
	{
		if(startingDir.isDirectory())
		{
			File[] startingDirectoryArray = startingDir.listFiles(directoryFilter);
			if(startingDirectoryArray != null)
			{
				logger.debug("Length of startingDirectoryArray = {}", startingDirectoryArray.length);
				return startingDirectoryArray[rand.nextInt(startingDirectoryArray.length - 1)];
			}
		}
		logger.error("The music directory is not a folder!");
		return null;
	}

	/**
	 * Returns a random song file from the given directory
	 *
	 * <p><b>Valid Song File Types</b></p>
	 * FLAC, MP3, M4A, WAV
	 *
	 * @param dir The directory to search
	 * @return A random song file
	 */
	private static File getRandomSong(File dir)
	{
		File[] directoryArray = dir.listFiles(songFilter);
		if(directoryArray != null)
		{
			logger.debug("Length of chosenDirectoryArray = {}", directoryArray.length);
			return directoryArray[rand.nextInt(directoryArray.length - 1)];
		}
		else
		{
			logger.error("chosenDirectoryArray is null");
			return null;
		}
	}
}
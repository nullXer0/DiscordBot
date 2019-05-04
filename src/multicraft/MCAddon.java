package multicraft;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class MCAddon extends MCCommands
{

	private static String url = "";
	private static String user = "";
	private static String key = "";

	public MCAddon(String url, String user, String key)
	{
		MCAddon.url = url;
		MCAddon.user = user;
		MCAddon.key = key;
	}

	public static String hmacDigest(String msg, String keyString, String algo)
	{
		String digest = null;
		try
		{
			SecretKeySpec key = new SecretKeySpec((keyString).getBytes("UTF-8"), algo);
			Mac mac = Mac.getInstance(algo);
			mac.init(key);

			byte[] bytes = mac.doFinal(msg.getBytes("ASCII"));

			StringBuffer hash = new StringBuffer();
			for(int i = 0; i < bytes.length; i++)
			{
				String hex = Integer.toHexString(0xFF & bytes[i]);
				if(hex.length() == 1)
				{
					hash.append('0');
				}
				hash.append(hex);
			}
			digest = hash.toString();
		}
		catch(UnsupportedEncodingException e)
		{
		}
		catch(InvalidKeyException e)
		{
		}
		catch(NoSuchAlgorithmException e)
		{
		}
		return digest;
	}

	public static String getKey()
	{
		return key;
	}

	public static String getUser()
	{
		return user;
	}

	public static String getUrl()
	{
		return url;
	}

	public static void setIP(String ip)
	{
		MCAddon.url = ip;
	}

	public static void setUser(String user)
	{
		MCAddon.user = user;
	}

	public static void setKey(String key)
	{
		MCAddon.key = key;
	}

	public static boolean canConnect()
	{
		URL tempURL = null;
		HttpURLConnection tempConnection = null;
		System.out.println(getServerStatus("1"));
		try
		{
			System.out.println("Connecting to http://" + url);
			tempURL = new URL("http://" + url + "/api.php");
			tempConnection = (HttpURLConnection) tempURL.openConnection();
			if(tempConnection.getResponseCode() == 200)
			{
				tempConnection.disconnect();
				return true;
			}
		}
		catch(IOException e)
		{
		}
		if(tempConnection != null)
		{
			tempConnection.disconnect();
		}
		return false;
	}
}
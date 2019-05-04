package multicraft;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public abstract class MCCommands
{

	private static String call(String method, String[] variables, String[] args)
	{
		String response = "";
		String query = "";
		String keyData = "";
		String key = "";

		if(variables != null)
		{
			for(int i = 0; i < variables.length; i++)
			{
				query += "&" + encode(variables[i]) + "=" + encode(args[i]);
				keyData += variables[i] + args[i];
			}
		}
		query += "&_MulticraftAPIMethod=" + method;
		keyData += "_MulticraftAPIMethod" + method;
		query += "&_MulticraftAPIUser=" + MCAddon.getUser();
		keyData += "_MulticraftAPIUser" + MCAddon.getUser();

		key = MCAddon.hmacDigest(keyData, MCAddon.getKey(), "HmacSHA256");

		URL url;
		HttpURLConnection connection;
		try
		{
			url = new URL("http://" + MCAddon.getUrl() + "/api.php?" + query + "&_MulticraftAPIKey=" + key);
			connection = (HttpURLConnection) url.openConnection();
			InputStream input = connection.getInputStream();
			int temp;
			while((temp = input.read()) != -1)
			{
				response += (char) temp;
			}
			connection.disconnect();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		System.out.println(query);
		return response;
	}

	public static String listUsers()
	{
		return call("listUsers", null, null);
	}

	public static String listServers()
	{
		return call("listServers", null, null);
	}

	public static String getUser(String id)
	{
		return call("getUser", new String[]{"id"}, new String[]{id});
	}

	public static String getServer(String id)
	{
		return call("getServer", new String[]{"id"}, new String[]{id});
	}

	public static String getServerStatus(String id)
	{
		return call("getServerStatus", new String[]{"id"}, new String[]{id});
	}

	public static String getCurrentUser()
	{
		return call("getCurrentUser", null, null);
	}

	public static String getServerChat(String id)
	{
		return call("getServerChat", new String[]{"id"}, new String[]{id});
	}

	public static String getServerLog(String id)
	{
		return call("getServerLog", new String[]{"id"}, new String[]{id});
	}

	public static String startServer(String id)
	{
		return call("startServer", new String[]{"id"}, new String[]{id});
	}

	public static String sendConsoleCommand(String id, String command)
	{
		return call("sendConsoleCommand", new String[]{"server_id", "command"}, new String[]{id, command});
	}

	public static String encode(String text)
	{
		try
		{
			return URLEncoder.encode(text, "UTF-8");
		}
		catch(UnsupportedEncodingException e)
		{
			e.printStackTrace();
			return null;
		}
	}
}

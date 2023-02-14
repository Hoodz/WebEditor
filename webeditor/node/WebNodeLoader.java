package net.runelite.client.plugins.webeditor.node;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import net.runelite.api.coords.WorldPoint;

public class WebNodeLoader
{

	private static String nodeSource = "https://pastebin.com/raw/gaJBNsY4";

	public static void addAllWalkNodes(ArrayList<Node> nodes)
	{
		try
		{
			URL url = new URL(nodeSource);
			InputStream inputStream = url.openStream();
			if (inputStream == null)
			{
				return;
			}

			ArrayList<ArrayList<Integer>> index2d = new ArrayList<>();
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
			String line;
			while ((line = bufferedReader.readLine()) != null)
			{
				if (line.isEmpty())
				{
					continue;
				}

				//create the walk web node
				String[] values = line.split(",");
				WorldPoint worldPoint = new WorldPoint(Integer.parseInt(values[0]), Integer.parseInt(values[1]), Integer.parseInt(values[2]));
				Node node = new Node(worldPoint);
				ArrayList<Integer> currentIndexes = new ArrayList<>();
				index2d.add(currentIndexes);
				nodes.add(node);

				//no indexes added
				if (values.length == 3)
				{
					continue;
				}

				//add all connected indexes
				for (int i = 3; i < values.length; i++)
				{
					currentIndexes.add(Integer.valueOf(values[i]));
				}
			}

			for (int i = 0; i < nodes.size(); i++)
			{
				Node node = nodes.get(i);
				ArrayList<Integer> indexes = index2d.get(i);
				indexes.forEach(index -> node.addConnectedNode(nodes.get(index)));
			}

			inputStream.close();
			inputStreamReader.close();
			bufferedReader.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

}
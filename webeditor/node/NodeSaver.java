package net.runelite.client.plugins.webeditor.node;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import lombok.SneakyThrows;
import net.runelite.api.coords.WorldPoint;

public class NodeSaver
{

	private final ArrayList<Node> nodes;

	public NodeSaver(ArrayList<Node> nodes)
	{
		this.nodes = nodes;
	}

	@SneakyThrows
	public void save()
	{
		System.out.println("Saving...");
		List<String> lines = new ArrayList<>();
		nodes.forEach(node ->
		{
			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.append(node.getWorldPoint().getX()).append(",").append(node.getWorldPoint().getY()).append(",").append(node.getWorldPoint().getPlane());
			node.getConnectedNodes().forEach(connectedNode ->
			{
				for (int i = 0; i < nodes.size(); i++)
				{
					Node tempNode = nodes.get(i);
					WorldPoint worldPoint = tempNode.getWorldPoint();
					if (equalsWorldPoint(connectedNode.getWorldPoint(), worldPoint))
					{
						stringBuilder.append(",").append(i);
						break;
					}
				}
			});
			lines.add(stringBuilder.toString());
		});
		Path file = Paths.get("node data.txt");
		Files.write(file, lines, StandardCharsets.UTF_8);
		System.out.println("Saved");
	}

	private boolean equalsWorldPoint(WorldPoint worldPoint, WorldPoint worldPoint1)
	{
		return (worldPoint.getX() == worldPoint1.getX() && worldPoint.getY() == worldPoint1.getY() && worldPoint.getPlane() == worldPoint1.getPlane());
	}

}

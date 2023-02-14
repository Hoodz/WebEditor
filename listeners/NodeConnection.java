package net.runelite.client.plugins.webeditor.listeners;

import java.awt.event.KeyEvent;
import java.util.Optional;
import net.runelite.api.Client;
import net.runelite.api.coords.WorldPoint;
import net.runelite.api.worldmap.WorldMap;
import net.runelite.client.input.KeyListener;
import net.runelite.client.plugins.webeditor.WebEditor;
import net.runelite.client.plugins.webeditor.mode.EditMode;
import net.runelite.client.plugins.webeditor.node.Node;

public class NodeConnection implements KeyListener
{

	private final WebEditor webEditor;
	private final Client client;

	public NodeConnection(WebEditor webEditor)
	{
		this.webEditor = webEditor;
		this.client = webEditor.getClient();
	}

	@Override
	public void keyTyped(KeyEvent e)
	{

	}

	@Override
	public void keyPressed(KeyEvent e)
	{
		if (EditMode.getCurrentMode() != EditMode.CONNECTION)
		{
			return;
		}

		if (e.getKeyCode() != KeyEvent.VK_SPACE)
		{
			return;
		}

		Optional<Node> optionalNode = webEditor.getNodes().stream().filter(Node::isSelected).findAny();
		if (!optionalNode.isPresent())
		{
			return;
		}

		Node selectedNode = optionalNode.get();
		WorldMap ro = client.getWorldMap();
		if (!ro.getWorldMapRenderer().isLoaded())
		{
			return;
		}

		WorldPoint worldPoint = new WorldPoint(ro.getWorldMapPosition().getX(), ro.getWorldMapPosition().getY(), 0);
		Optional<Node> optionalConnectionNode = webEditor.getNodes().stream().filter(node1 -> node1.getWorldPoint().distanceTo(worldPoint) < 4).findAny();
		if (!optionalConnectionNode.isPresent())
		{
			return;
		}

		Node connectionNode = optionalConnectionNode.get();
		if (connectionNode.getConnectedNodes().contains(selectedNode))
		{
			selectedNode.deleteConnectedNode(connectionNode);
			connectionNode.deleteConnectedNode(selectedNode);
		}
		else
		{
			selectedNode.addConnectedNode(connectionNode);
			connectionNode.addConnectedNode(selectedNode);
		}

		e.consume();
	}

	@Override
	public void keyReleased(KeyEvent e)
	{

	}

}

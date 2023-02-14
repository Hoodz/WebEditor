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

public class NodeAddNew implements KeyListener
{

	private final WebEditor webEditor;
	private final Client client;

	public NodeAddNew(WebEditor webEditor)
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
		if (EditMode.getCurrentMode() != EditMode.ADD)
		{
			return;
		}

		if (e.getKeyCode() != KeyEvent.VK_SPACE)
		{
			return;
		}

		WorldMap ro = client.getWorldMap();
		if (!ro.getWorldMapRenderer().isLoaded())
		{
			return;
		}

		WorldPoint worldPoint = new WorldPoint(ro.getWorldMapPosition().getX(), ro.getWorldMapPosition().getY(), 0);
		Node node = new Node(worldPoint);
		if (webEditor.getNodes().stream().anyMatch(node1 -> node1.getWorldPoint().distanceTo(worldPoint) < 4))
		{
			return;
		}

		webEditor.getNodes().add(node);

		Optional<Node> optionalNode = webEditor.getNodes().stream().filter(Node::isSelected).findAny();
		if (optionalNode.isPresent())
		{
			Node selectedNode = optionalNode.get();
			selectedNode.addConnectedNode(node);
			node.addConnectedNode(selectedNode);
			selectedNode.setSelected(false);
		}

		node.setSelected(true);
		e.consume();
	}

	@Override
	public void keyReleased(KeyEvent e)
	{

	}

}

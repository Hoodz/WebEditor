package net.runelite.client.plugins.webeditor.listeners;

import java.awt.event.KeyEvent;
import java.util.Optional;
import net.runelite.client.input.KeyListener;
import net.runelite.client.plugins.webeditor.WebEditor;
import net.runelite.client.plugins.webeditor.mode.EditMode;
import net.runelite.client.plugins.webeditor.node.Node;

public class NodeRemove implements KeyListener
{

	private final WebEditor webEditor;

	public NodeRemove(WebEditor webEditor)
	{
		this.webEditor = webEditor;
	}

	@Override
	public void keyTyped(KeyEvent e)
	{

	}

	@Override
	public void keyPressed(KeyEvent e)
	{
		if (EditMode.getCurrentMode() != EditMode.DELETE)
		{
			return;
		}

		if (e.getKeyCode() != KeyEvent.VK_SPACE)
		{
			return;
		}

		Optional<Node> optionalNode = webEditor.getNodes().stream().filter(Node::isSelected).findAny();
		if (optionalNode.isPresent())
		{
			Node node = optionalNode.get();
			node.setSelected(false);
			for (Node connectedNode : node.getConnectedNodes())
			{
				connectedNode.deleteConnectedNode(node);
			}
			node.getConnectedNodes().clear();
			webEditor.getNodes().remove(node);
		}
		e.consume();
	}

	@Override
	public void keyReleased(KeyEvent e)
	{

	}

}

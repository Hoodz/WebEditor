package net.runelite.client.plugins.webeditor.listeners;

import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import net.runelite.client.plugins.webeditor.WebEditor;
import net.runelite.client.plugins.webeditor.node.Node;

public class MouseListener implements net.runelite.client.input.MouseListener
{

	private final ArrayList<Node> nodes;

	public MouseListener(WebEditor webEditor)
	{
		this.nodes = webEditor.getNodes();
	}

	@Override
	public MouseEvent mouseClicked(MouseEvent mouseEvent)
	{
		for (Node node : nodes)
		{
			Rectangle rectangle = node.getBox();
			if (rectangle != null && rectangle.contains(mouseEvent.getPoint()))
			{
				nodes.forEach(n -> n.setSelected(false));
				node.setSelected(!node.isSelected());
				break;
			}
		}
		return mouseEvent;
	}

	@Override
	public MouseEvent mousePressed(MouseEvent mouseEvent)
	{
		return mouseEvent;
	}

	@Override
	public MouseEvent mouseReleased(MouseEvent mouseEvent)
	{
		return mouseEvent;
	}

	@Override
	public MouseEvent mouseEntered(MouseEvent mouseEvent)
	{
		return mouseEvent;
	}

	@Override
	public MouseEvent mouseExited(MouseEvent mouseEvent)
	{
		return mouseEvent;
	}

	@Override
	public MouseEvent mouseDragged(MouseEvent mouseEvent)
	{
		return mouseEvent;
	}

	@Override
	public MouseEvent mouseMoved(MouseEvent mouseEvent)
	{
		return mouseEvent;
	}

}

package net.runelite.client.plugins.webeditor.node;

import java.awt.Color;
import java.awt.Rectangle;
import java.util.ArrayList;
import lombok.Getter;
import lombok.Setter;
import net.runelite.api.Point;
import net.runelite.api.coords.WorldPoint;

public class Node
{

	@Getter
	private final ArrayList<Node> connectedNodes;

	@Getter
	boolean selected;

	@Getter
	@Setter
	private WorldPoint worldPoint;

	@Getter
	private Rectangle box;

	public Node(WorldPoint worldPoint)
	{
		this.connectedNodes = new ArrayList<>();
		this.worldPoint = worldPoint;
		this.selected = false;
	}

	public void addConnectedNode(Node node)
	{
		connectedNodes.add(node);
	}

	public void deleteConnectedNode(Node node)
	{
		connectedNodes.remove(node);
	}

	public void setSelected(boolean newSetting)
	{
		this.selected = newSetting;
	}

	@Override
	public boolean equals(Object object)
	{
		if (object instanceof Node)
		{
			Node objNode = (Node) object;
			return getWorldPoint().getX() == objNode.getWorldPoint().getX() && getWorldPoint().getY() == objNode.getWorldPoint().getY() && getWorldPoint().getPlane() == objNode.getWorldPoint().getPlane();
		}
		return false;
	}

	public Color getColor()
	{
		return (selected) ? Color.GREEN : Color.RED;
	}

	public void setBox(Point point)
	{
		box = (point == null) ? null : new Rectangle(point.getX() - 4, point.getY() - 4, 9, 9);
	}

}
package net.runelite.client.plugins.webeditor.overlays;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;
import net.runelite.api.Client;
import net.runelite.api.Point;
import net.runelite.api.widgets.Widget;
import net.runelite.api.widgets.WidgetInfo;
import net.runelite.api.worldmap.WorldMap;
import net.runelite.client.plugins.webeditor.WebEditor;
import net.runelite.client.plugins.webeditor.node.Node;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.OverlayPriority;
import net.runelite.client.ui.overlay.worldmap.WorldMapOverlay;

public class MapNodeOverlay extends Overlay
{

	private final ArrayList<Node> nodes;
	private final Client client;
	private final WorldMapOverlay worldMapOverlay;

	public MapNodeOverlay(WebEditor webEditor)
	{
		this.nodes = webEditor.getNodes();
		this.client = webEditor.getClient();
		this.worldMapOverlay = webEditor.getWorldMapOverlay();
		this.setLayer(OverlayLayer.ALWAYS_ON_TOP);
		this.setPosition(OverlayPosition.DYNAMIC);
		this.setPriority(OverlayPriority.HIGHEST);
	}

	@Override
	public Dimension render(Graphics2D graphics2D)
	{
		//draws the connections between the nodes
		WorldMap ro = client.getWorldMap();
		Widget worldMapWidget = client.getWidget(WidgetInfo.WORLD_MAP_VIEW);

		if ((ro == null || !ro.getWorldMapRenderer().isLoaded()) || worldMapWidget == null)
		{
			return null;
		}

		if (nodes == null || nodes.isEmpty())
		{
			return null;
		}

		Rectangle worldMapRectangle = worldMapWidget.getBounds();
		if (worldMapRectangle == null)
		{
			return null;
		}

		graphics2D.setClip(worldMapRectangle);
		graphics2D.setColor(Color.WHITE);

		Node[] nodeArray = nodes.toArray(new Node[0]);
		for (Node node : nodeArray)
		{

			Point point1 = worldMapOverlay.mapWorldPointToGraphicsPoint(node.getWorldPoint());
			if (point1 == null)
			{
				node.setBox(null);
				continue;
			}

			node.setBox(point1);

			if (node.getConnectedNodes().isEmpty())
			{
				continue;
			}

			for (Node connectedNode : node.getConnectedNodes())
			{
				if (connectedNode == null)
				{
					continue;
				}

				Point point2 = worldMapOverlay.mapWorldPointToGraphicsPoint(connectedNode.getWorldPoint());
				if (point2 == null)
				{
					continue;
				}

				graphics2D.drawLine(point1.getX(), point1.getY(), point2.getX(), point2.getY());
			}
		}

		for (Node node : nodeArray)
		{
			if (node.getBox() == null)
			{
				continue;
			}

			graphics2D.setColor(node.getColor());
			graphics2D.fillOval(node.getBox().x, node.getBox().y, node.getBox().width, node.getBox().height);
			graphics2D.setColor(Color.WHITE);
			graphics2D.drawOval(node.getBox().x, node.getBox().y, node.getBox().width, node.getBox().height);
		}

		return null;
	}

}

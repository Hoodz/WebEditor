package net.runelite.client.plugins.webeditor.overlays;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import net.runelite.api.Client;
import net.runelite.api.Point;
import net.runelite.api.coords.WorldPoint;
import net.runelite.api.widgets.Widget;
import net.runelite.api.widgets.WidgetInfo;
import net.runelite.api.worldmap.WorldMap;
import net.runelite.client.plugins.webeditor.WebEditor;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.OverlayPriority;
import net.runelite.client.ui.overlay.worldmap.WorldMapOverlay;

public class CrosshairOverlay extends Overlay
{

	private final Client client;
	private final WorldMapOverlay worldMapOverlay;

	public CrosshairOverlay(WebEditor webEditor)
	{
		this.setLayer(OverlayLayer.ALWAYS_ON_TOP);
		this.setPosition(OverlayPosition.DYNAMIC);
		this.setPriority(OverlayPriority.HIGHEST);
		this.worldMapOverlay = webEditor.getWorldMapOverlay();
		this.client = webEditor.getClient();
	}

	@Override
	public Dimension render(Graphics2D graphics2D)
	{
		WorldMap ro = client.getWorldMap();
		Widget worldMapWidget = client.getWidget(WidgetInfo.WORLD_MAP_VIEW);

		if ((ro == null || !ro.getWorldMapRenderer().isLoaded()) || worldMapWidget == null)
		{
			return null;
		}

		Rectangle worldMapRectangle = worldMapWidget.getBounds();

		graphics2D.setClip(worldMapRectangle);
		graphics2D.setColor(Color.CYAN);

		WorldPoint mapCenterPoint = new WorldPoint(ro.getWorldMapPosition().getX(), ro.getWorldMapPosition().getY(), 0);
		Point middle = worldMapOverlay.mapWorldPointToGraphicsPoint(mapCenterPoint);

		if (middle == null)
		{
			return null;
		}

		graphics2D.drawLine(middle.getX(), worldMapRectangle.y, middle.getX(), worldMapRectangle.y + worldMapRectangle.height);
		graphics2D.drawLine(worldMapRectangle.x, middle.getY(), worldMapRectangle.x + worldMapRectangle.width, middle.getY());
		return null;
	}

}

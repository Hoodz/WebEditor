package net.runelite.client.plugins.webeditor.overlays;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import net.runelite.client.plugins.webeditor.mode.EditMode;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.OverlayPriority;
import net.runelite.client.ui.overlay.components.LineComponent;
import net.runelite.client.ui.overlay.components.PanelComponent;
import net.runelite.client.ui.overlay.components.TitleComponent;

public class MenuOverlay extends Overlay
{
	private final PanelComponent menuComponent;

	public MenuOverlay()
	{
		this.setLayer(OverlayLayer.ALWAYS_ON_TOP);
		this.setPosition(OverlayPosition.TOP_LEFT);
		this.setPriority(OverlayPriority.HIGHEST);
		this.setPreferredSize(new Dimension(500, 500));
		this.setResizable(true);
		this.menuComponent = new PanelComponent();
	}

	@Override
	public Dimension render(Graphics2D graphics2D)
	{
		//menu
		menuComponent.getChildren().clear();
		menuComponent.getChildren().add(TitleComponent.builder().color(Color.WHITE).text("Web Editor").build());
		menuComponent.getChildren().add(LineComponent.builder().left("").build());

		//adds all menu options, paints the current option green
		EditMode[] values = EditMode.values();
		for (EditMode editMode : values)
		{
			Color color = (editMode.isActive()) ? Color.GREEN : Color.WHITE;
			menuComponent.getChildren().add(LineComponent.builder().left(editMode.name()).leftColor(color).build());
		}

		return menuComponent.render(graphics2D);
	}

}
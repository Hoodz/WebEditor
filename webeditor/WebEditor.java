package net.runelite.client.plugins.webeditor;

import java.util.ArrayList;
import javax.inject.Inject;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.client.eventbus.EventBus;
import net.runelite.client.input.KeyManager;
import net.runelite.client.input.MouseManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.plugins.webeditor.listeners.MenuListener;
import net.runelite.client.plugins.webeditor.listeners.MouseListener;
import net.runelite.client.plugins.webeditor.listeners.NodeAddNew;
import net.runelite.client.plugins.webeditor.listeners.NodeConnection;
import net.runelite.client.plugins.webeditor.listeners.NodeRemove;
import net.runelite.client.plugins.webeditor.mode.EditMode;
import net.runelite.client.plugins.webeditor.node.Node;
import net.runelite.client.plugins.webeditor.node.NodeSaver;
import net.runelite.client.plugins.webeditor.node.WebNodeLoader;
import net.runelite.client.plugins.webeditor.overlays.CrosshairOverlay;
import net.runelite.client.plugins.webeditor.overlays.MapNodeOverlay;
import net.runelite.client.plugins.webeditor.overlays.MenuOverlay;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayManager;
import net.runelite.client.ui.overlay.worldmap.WorldMapOverlay;

@Slf4j
@PluginDescriptor(
	name = "WebEditor",
	enabledByDefault = false
)
public class WebEditor extends Plugin
{
	@Getter
	private ArrayList<Node> nodes;
	@Getter
	private NodeConnection nodeConnection;
	private NodeAddNew nodeAddNew;
	private NodeRemove nodeRemove;
	private MenuListener menuListener;
	private MouseListener mouseListener;
	private ArrayList<Overlay> overlays;

	@Inject
	private OverlayManager overlayManager;

	@Inject
	private EventBus eventBus;

	@Inject
	private KeyManager keyManager;

	@Inject
	private MouseManager mouseManager;

	@Inject
	@Getter
	private WorldMapOverlay worldMapOverlay;

	@Inject
	@Getter
	private Client client;

	@Override
	protected void startUp()
	{
		nodes = new ArrayList<>();
		overlays = new ArrayList<>();
		WebNodeLoader.addAllWalkNodes(nodes);
		eventBus.register(this);
		EditMode.ADD.setActive(true);

		//listeners
		nodeConnection = new NodeConnection(this);
		keyManager.registerKeyListener(nodeConnection);
		nodeAddNew = new NodeAddNew(this);
		keyManager.registerKeyListener(nodeAddNew);
		nodeRemove = new NodeRemove(this);
		keyManager.registerKeyListener(nodeRemove);
		menuListener = new MenuListener();
		keyManager.registerKeyListener(menuListener);
		mouseListener = new MouseListener(this);
		mouseManager.registerMouseListener(mouseListener);

		//overlays
		MapNodeOverlay mapNodeOverlay = new MapNodeOverlay(this);
		overlays.add(mapNodeOverlay);
		CrosshairOverlay crosshairOverlay = new CrosshairOverlay(this);
		overlays.add(crosshairOverlay);
		MenuOverlay menuOverlay = new MenuOverlay();
		overlays.add(menuOverlay);
		overlays.forEach(overlay -> overlayManager.add(overlay));
	}

	@Override
	protected void shutDown()
	{
		keyManager.unregisterKeyListener(nodeConnection);
		keyManager.unregisterKeyListener(nodeAddNew);
		keyManager.unregisterKeyListener(nodeRemove);
		keyManager.unregisterKeyListener(menuListener);
		mouseManager.unregisterMouseListener(mouseListener);
		overlays.forEach(overlay -> overlayManager.remove(overlay));
		eventBus.unregister(this);
		NodeSaver nodeSaver = new NodeSaver(nodes);
		nodeSaver.save();
	}

}

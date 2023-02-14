package net.runelite.client.plugins.webeditor.listeners;

import java.awt.event.KeyEvent;
import java.util.Objects;
import net.runelite.client.input.KeyListener;
import net.runelite.client.plugins.webeditor.mode.EditMode;

public class MenuListener implements KeyListener
{

	@Override
	public void keyTyped(KeyEvent e)
	{

	}

	@Override
	public void keyPressed(KeyEvent e)
	{
		if (e.getKeyCode() == KeyEvent.VK_UP)
		{
			int ordinal = Objects.requireNonNull(EditMode.getCurrentMode()).ordinal();
			if (ordinal > 0)
			{
				EditMode newMode = EditMode.getModeFromOrdinal(ordinal - 1);
				EditMode.setMode(Objects.requireNonNull(newMode));
				e.consume();
			}
			return;
		}

		if (e.getKeyCode() == KeyEvent.VK_DOWN)
		{
			int ordinal = Objects.requireNonNull(EditMode.getCurrentMode()).ordinal();
			if (ordinal < 2)
			{
				EditMode newMode = EditMode.getModeFromOrdinal(ordinal + 1);
				EditMode.setMode(Objects.requireNonNull(newMode));
				e.consume();
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e)
	{

	}

}

package net.runelite.client.plugins.webeditor.mode;

import lombok.Getter;
import lombok.Setter;

public enum EditMode
{

	ADD,
	CONNECTION,
	DELETE;

	@Getter
	@Setter
	private boolean active;

	EditMode()
	{
		this.active = false;
	}

	public static void setAllInactive()
	{
		for (EditMode edit : values())
		{
			edit.setActive(false);
		}
	}

	public static EditMode getModeFromOrdinal(int ordinal)
	{
		switch (ordinal)
		{
			case 0:
				return ADD;
			case 1:
				return CONNECTION;
			case 2:
				return DELETE;
		}
		return null;
	}

	public static void setMode(EditMode editMode)
	{
		setAllInactive();
		editMode.setActive(true);
	}

	public static EditMode getCurrentMode()
	{
		for (EditMode edit : values())
		{
			if (edit.isActive())
			{
				return edit;
			}
		}
		return null;
	}

}

package com.platformkeys;

import java.awt.event.InputEvent;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.runelite.client.util.OSType;

@Getter
@RequiredArgsConstructor
public enum KeyProfile
{
	AUTO("Auto-detect", 0),
	WINDOWS("Windows (Ctrl)", InputEvent.CTRL_DOWN_MASK),
	MAC("Mac (Cmd)", InputEvent.META_DOWN_MASK);

	private final String name;
	private final int modifierMask;

	/** Resolve AUTO to the profile matching the running OS. */
	public KeyProfile resolve()
	{
		if (this != AUTO)
		{
			return this;
		}
		return OSType.getOSType() == OSType.MacOS ? MAC : WINDOWS;
	}

	/** The "primary" shortcut modifier for this platform (Ctrl on Windows, Cmd on Mac). */
	public int primaryModifier()
	{
		return resolve() == MAC ? InputEvent.META_DOWN_MASK : InputEvent.CTRL_DOWN_MASK;
	}

	@Override
	public String toString()
	{
		return name;
	}
}

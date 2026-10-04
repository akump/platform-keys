package com.platformkeys;

import java.awt.event.KeyEvent;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum NumberKeys
{
	TOP_ROW("Number row (1-0)", KeyEvent.VK_0),
	NUMPAD("Numpad (Num 1-Num 0)", KeyEvent.VK_NUMPAD0);

	private final String name;
	private final int zeroKeyCode;

	/**
	 * Slot index for a key code, ordered as the keys are laid out: 1..9 are slots 0..8 and 0 is slot 9.
	 * Returns -1 if the key is not one of this set's number keys.
	 */
	public int slotFor(int keyCode)
	{
		int digit = keyCode - zeroKeyCode;
		if (digit < 0 || digit > 9)
		{
			return -1;
		}
		return (digit + 9) % 10;
	}

	@Override
	public String toString()
	{
		return name;
	}
}

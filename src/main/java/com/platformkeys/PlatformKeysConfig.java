package com.platformkeys;

import java.awt.event.KeyEvent;
import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;
import net.runelite.client.config.ConfigSection;
import net.runelite.client.config.Keybind;

@ConfigGroup("platformkeys")
public interface PlatformKeysConfig extends Config
{
	@ConfigSection(name = "Platform", description = "Which key profile to use", position = 0)
	String platformSection = "platform";

	@ConfigSection(name = "Bank search", description = "Bank search behaviour", position = 1)
	String searchSection = "search";

	@ConfigSection(name = "Number keys", description = "Remap number keys to F1-F10", position = 2)
	String numberSection = "numbers";

	@ConfigItem(
		keyName = "remapNumbers",
		name = "Remap to F1-F10",
		description = "Send F1-F10 when the profile's number keys 1-0 are pressed",
		section = numberSection,
		position = 0
	)
	default boolean remapNumbers()
	{
		return true;
	}

	@ConfigItem(
		keyName = "macNumberKeys",
		name = "Mac keys",
		description = "Which number keys are remapped when the Mac profile is active",
		section = numberSection,
		position = 1
	)
	default NumberKeys macNumberKeys()
	{
		return NumberKeys.TOP_ROW;
	}

	@ConfigItem(
		keyName = "windowsNumberKeys",
		name = "Windows keys",
		description = "Which number keys are remapped when the Windows profile is active",
		section = numberSection,
		position = 2
	)
	default NumberKeys windowsNumberKeys()
	{
		return NumberKeys.NUMPAD;
	}

	@ConfigItem(
		keyName = "profile",
		name = "Key profile",
		description = "Auto picks Ctrl on Windows/Linux and Cmd on Mac. Override to share a config across machines.",
		section = platformSection,
		position = 0
	)
	default KeyProfile profile()
	{
		return KeyProfile.AUTO;
	}

	@ConfigItem(
		keyName = "searchKey",
		name = "Search hotkey",
		description = "Key that opens bank search while the bank is open",
		section = searchSection,
		position = 0
	)
	default Keybind searchKey()
	{
		return new Keybind(KeyEvent.VK_F, 0);
	}

	@ConfigItem(
		keyName = "requireModifier",
		name = "Require Ctrl/Cmd",
		description = "Require the platform modifier (Ctrl on Windows, Cmd on Mac) together with the search hotkey",
		section = searchSection,
		position = 1
	)
	default boolean requireModifier()
	{
		return true;
	}

	@ConfigItem(
		keyName = "typeToSearch",
		name = "Type to search",
		description = "Start a bank search when you type a letter or digit while the bank is open",
		section = searchSection,
		position = 2
	)
	default boolean typeToSearch()
	{
		return false;
	}

	@ConfigItem(
		keyName = "escapeClosesSearch",
		name = "Esc closes search",
		description = "Pressing Escape while a bank search is active closes the search first",
		section = searchSection,
		position = 3
	)
	default boolean escapeClosesSearch()
	{
		return true;
	}
}

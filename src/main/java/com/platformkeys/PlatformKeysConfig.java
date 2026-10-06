package com.platformkeys;

import java.awt.event.KeyEvent;
import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;
import net.runelite.client.config.ConfigSection;
import net.runelite.client.config.Keybind;
import net.runelite.client.config.ModifierlessKeybind;

@ConfigGroup("platformkeys")
public interface PlatformKeysConfig extends Config
{
	@ConfigSection(name = "Platform", description = "Which key profile to use", position = 0)
	String platformSection = "platform";

	@ConfigSection(name = "Bank search", description = "Bank search behaviour", position = 1)
	String searchSection = "search";

	@ConfigSection(name = "F-key remapping", description = "Remap keys to F1-F12", position = 2)
	String remapSection = "remap";

	@ConfigSection(name = "Mac F-keys", description = "Keys that send F1-F12 when the Mac profile is active", position = 3, closedByDefault = true)
	String macSection = "macFKeys";

	@ConfigSection(name = "Windows F-keys", description = "Keys that send F1-F12 when the Windows profile is active", position = 4, closedByDefault = true)
	String windowsSection = "windowsFKeys";

	@ConfigItem(
		keyName = "remapNumbers",
		name = "Remap to F1-F12",
		description = "Send F1-F12 when the active profile's keys below are pressed",
		section = remapSection,
		position = 0
	)
	default boolean remapNumbers()
	{
		return true;
	}

	@ConfigItem(
		keyName = "macF1",
		name = "F1",
		description = "Key that sends F1 on Mac",
		section = macSection,
		position = 1
	)
	default ModifierlessKeybind macF1()
	{
		return new ModifierlessKeybind(KeyEvent.VK_1, 0);
	}

	@ConfigItem(
		keyName = "macF2",
		name = "F2",
		description = "Key that sends F2 on Mac",
		section = macSection,
		position = 2
	)
	default ModifierlessKeybind macF2()
	{
		return new ModifierlessKeybind(KeyEvent.VK_2, 0);
	}

	@ConfigItem(
		keyName = "macF3",
		name = "F3",
		description = "Key that sends F3 on Mac",
		section = macSection,
		position = 3
	)
	default ModifierlessKeybind macF3()
	{
		return new ModifierlessKeybind(KeyEvent.VK_3, 0);
	}

	@ConfigItem(
		keyName = "macF4",
		name = "F4",
		description = "Key that sends F4 on Mac",
		section = macSection,
		position = 4
	)
	default ModifierlessKeybind macF4()
	{
		return new ModifierlessKeybind(KeyEvent.VK_4, 0);
	}

	@ConfigItem(
		keyName = "macF5",
		name = "F5",
		description = "Key that sends F5 on Mac",
		section = macSection,
		position = 5
	)
	default ModifierlessKeybind macF5()
	{
		return new ModifierlessKeybind(KeyEvent.VK_5, 0);
	}

	@ConfigItem(
		keyName = "macF6",
		name = "F6",
		description = "Key that sends F6 on Mac",
		section = macSection,
		position = 6
	)
	default ModifierlessKeybind macF6()
	{
		return new ModifierlessKeybind(KeyEvent.VK_6, 0);
	}

	@ConfigItem(
		keyName = "macF7",
		name = "F7",
		description = "Key that sends F7 on Mac",
		section = macSection,
		position = 7
	)
	default ModifierlessKeybind macF7()
	{
		return new ModifierlessKeybind(KeyEvent.VK_7, 0);
	}

	@ConfigItem(
		keyName = "macF8",
		name = "F8",
		description = "Key that sends F8 on Mac",
		section = macSection,
		position = 8
	)
	default ModifierlessKeybind macF8()
	{
		return new ModifierlessKeybind(KeyEvent.VK_8, 0);
	}

	@ConfigItem(
		keyName = "macF9",
		name = "F9",
		description = "Key that sends F9 on Mac",
		section = macSection,
		position = 9
	)
	default ModifierlessKeybind macF9()
	{
		return new ModifierlessKeybind(KeyEvent.VK_9, 0);
	}

	@ConfigItem(
		keyName = "macF10",
		name = "F10",
		description = "Key that sends F10 on Mac",
		section = macSection,
		position = 10
	)
	default ModifierlessKeybind macF10()
	{
		return new ModifierlessKeybind(KeyEvent.VK_0, 0);
	}

	@ConfigItem(
		keyName = "macF11",
		name = "F11",
		description = "Key that sends F11 on Mac",
		section = macSection,
		position = 11
	)
	default ModifierlessKeybind macF11()
	{
		return new ModifierlessKeybind(KeyEvent.VK_MINUS, 0);
	}

	@ConfigItem(
		keyName = "macF12",
		name = "F12",
		description = "Key that sends F12 on Mac",
		section = macSection,
		position = 12
	)
	default ModifierlessKeybind macF12()
	{
		return new ModifierlessKeybind(KeyEvent.VK_EQUALS, 0);
	}

	@ConfigItem(
		keyName = "windowsF1",
		name = "F1",
		description = "Key that sends F1 on Windows",
		section = windowsSection,
		position = 1
	)
	default ModifierlessKeybind windowsF1()
	{
		return new ModifierlessKeybind(KeyEvent.VK_NUMPAD1, 0);
	}

	@ConfigItem(
		keyName = "windowsF2",
		name = "F2",
		description = "Key that sends F2 on Windows",
		section = windowsSection,
		position = 2
	)
	default ModifierlessKeybind windowsF2()
	{
		return new ModifierlessKeybind(KeyEvent.VK_NUMPAD2, 0);
	}

	@ConfigItem(
		keyName = "windowsF3",
		name = "F3",
		description = "Key that sends F3 on Windows",
		section = windowsSection,
		position = 3
	)
	default ModifierlessKeybind windowsF3()
	{
		return new ModifierlessKeybind(KeyEvent.VK_NUMPAD3, 0);
	}

	@ConfigItem(
		keyName = "windowsF4",
		name = "F4",
		description = "Key that sends F4 on Windows",
		section = windowsSection,
		position = 4
	)
	default ModifierlessKeybind windowsF4()
	{
		return new ModifierlessKeybind(KeyEvent.VK_NUMPAD4, 0);
	}

	@ConfigItem(
		keyName = "windowsF5",
		name = "F5",
		description = "Key that sends F5 on Windows",
		section = windowsSection,
		position = 5
	)
	default ModifierlessKeybind windowsF5()
	{
		return new ModifierlessKeybind(KeyEvent.VK_NUMPAD5, 0);
	}

	@ConfigItem(
		keyName = "windowsF6",
		name = "F6",
		description = "Key that sends F6 on Windows",
		section = windowsSection,
		position = 6
	)
	default ModifierlessKeybind windowsF6()
	{
		return new ModifierlessKeybind(KeyEvent.VK_NUMPAD6, 0);
	}

	@ConfigItem(
		keyName = "windowsF7",
		name = "F7",
		description = "Key that sends F7 on Windows",
		section = windowsSection,
		position = 7
	)
	default ModifierlessKeybind windowsF7()
	{
		return new ModifierlessKeybind(KeyEvent.VK_NUMPAD7, 0);
	}

	@ConfigItem(
		keyName = "windowsF8",
		name = "F8",
		description = "Key that sends F8 on Windows",
		section = windowsSection,
		position = 8
	)
	default ModifierlessKeybind windowsF8()
	{
		return new ModifierlessKeybind(KeyEvent.VK_NUMPAD8, 0);
	}

	@ConfigItem(
		keyName = "windowsF9",
		name = "F9",
		description = "Key that sends F9 on Windows",
		section = windowsSection,
		position = 9
	)
	default ModifierlessKeybind windowsF9()
	{
		return new ModifierlessKeybind(KeyEvent.VK_NUMPAD9, 0);
	}

	@ConfigItem(
		keyName = "windowsF10",
		name = "F10",
		description = "Key that sends F10 on Windows",
		section = windowsSection,
		position = 10
	)
	default ModifierlessKeybind windowsF10()
	{
		return new ModifierlessKeybind(KeyEvent.VK_NUMPAD0, 0);
	}

	@ConfigItem(
		keyName = "windowsF11",
		name = "F11",
		description = "Key that sends F11 on Windows",
		section = windowsSection,
		position = 11
	)
	default ModifierlessKeybind windowsF11()
	{
		return new ModifierlessKeybind(KeyEvent.VK_UNDEFINED, 0);
	}

	@ConfigItem(
		keyName = "windowsF12",
		name = "F12",
		description = "Key that sends F12 on Windows",
		section = windowsSection,
		position = 12
	)
	default ModifierlessKeybind windowsF12()
	{
		return new ModifierlessKeybind(KeyEvent.VK_UNDEFINED, 0);
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

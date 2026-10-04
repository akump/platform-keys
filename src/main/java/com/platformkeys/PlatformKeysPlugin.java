package com.platformkeys;

import com.google.inject.Provides;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.MenuAction;
import net.runelite.api.events.WidgetClosed;
import net.runelite.api.events.WidgetLoaded;
import net.runelite.api.widgets.Widget;
import net.runelite.api.gameval.InterfaceID;
import net.runelite.api.gameval.VarClientID;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.input.KeyListener;
import net.runelite.client.input.KeyManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.plugins.bank.BankSearch;

@Slf4j
@PluginDescriptor(
	name = "Platform Keys",
	description = "Bank search and key mapping profiles for Windows and Mac",
	tags = {"bank", "search", "hotkey", "mac"}
)
public class PlatformKeysPlugin extends Plugin implements KeyListener
{
	// Modifiers we care about when comparing against the configured profile
	private static final int MOD_MASK = KeyEvent.CTRL_DOWN_MASK | KeyEvent.META_DOWN_MASK
		| KeyEvent.ALT_DOWN_MASK | KeyEvent.SHIFT_DOWN_MASK;

	// Original key code -> remapped key code, for keys currently held down
	private final Map<Integer, Integer> remapped = new HashMap<>();
	private boolean swallowTyped;
	// Tracked from widget events because key events arrive on the AWT thread, where widgets can't be inspected
	private volatile boolean bankOpen;

	@Inject
	private Client client;

	@Inject
	private ClientThread clientThread;

	@Inject
	private KeyManager keyManager;

	@Inject
	private PlatformKeysConfig config;

	@Inject
	private BankSearch bankSearch;

	@Provides
	PlatformKeysConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(PlatformKeysConfig.class);
	}

	@Override
	protected void startUp()
	{
		keyManager.registerKeyListener(this);
		clientThread.invoke(() ->
		{
			Widget bank = client.getWidget(InterfaceID.Bankmain.UNIVERSE);
			bankOpen = bank != null && !bank.isHidden();
		});
	}

	@Override
	protected void shutDown()
	{
		keyManager.unregisterKeyListener(this);
		bankOpen = false;
	}

	@Subscribe
	public void onWidgetLoaded(WidgetLoaded event)
	{
		if (event.getGroupId() == InterfaceID.BANKMAIN)
		{
			bankOpen = true;
		}
	}

	@Subscribe
	public void onWidgetClosed(WidgetClosed event)
	{
		if (event.getGroupId() == InterfaceID.BANKMAIN)
		{
			bankOpen = false;
		}
	}

	private boolean searchActive()
	{
		// The chatbox input widget is shown while a bank search is being typed
		return client.getVarcIntValue(VarClientID.MESLAYERMODE) == 11;
	}

	private boolean inputDialogOpen()
	{
		return client.getVarcIntValue(VarClientID.MESLAYERMODE) != 0;
	}

	private NumberKeys numberKeys()
	{
		return config.profile().resolve() == KeyProfile.MAC ? config.macNumberKeys() : config.windowsNumberKeys();
	}

	private void toggleSearch()
	{
		clientThread.invoke(() ->
		{
			Widget searchButton = client.getWidget(InterfaceID.Bankmain.SEARCH);
			if (searchButton == null)
			{
				return;
			}
			client.menuAction(-1, searchButton.getId(), MenuAction.CC_OP, 1, -1, "Search", "");
		});
	}

	@Override
	public void keyPressed(KeyEvent e)
	{
		int code = e.getKeyCode();
		int mods = e.getModifiersEx() & MOD_MASK;

		// Number keys -> F1-F10. Skipped while a chatbox input (bank search, Withdraw-X) is open so digits still type there.
		int slot = config.remapNumbers() && mods == 0 && !inputDialogOpen() ? numberKeys().slotFor(code) : -1;
		if (slot >= 0)
		{
			int target = KeyEvent.VK_F1 + slot;
			remapped.put(code, target);
			swallowTyped = true;
			e.setKeyCode(target);
			e.setKeyChar(KeyEvent.CHAR_UNDEFINED);
			return;
		}
		swallowTyped = false;

		if (!bankOpen)
		{
			return;
		}

		int primary = config.profile().primaryModifier();

		// Hotkey: [Ctrl|Cmd]+<key>
		int wantedMods = config.requireModifier() ? primary : 0;
		if (code == config.searchKey().getKeyCode() && mods == wantedMods)
		{
			// Without a modifier the hotkey is a plain letter, which has to stay typeable in the search box
			if (wantedMods == 0 && searchActive())
			{
				return;
			}
			e.consume();
			// Clears any existing search text/filter and opens a fresh search prompt
			bankSearch.initSearch();
			return;
		}

		if (code == KeyEvent.VK_ESCAPE && config.escapeClosesSearch() && searchActive())
		{
			e.consume();
			toggleSearch();
			return;
		}

		if (config.typeToSearch() && !searchActive() && mods == 0 && Character.isLetterOrDigit(e.getKeyChar()))
		{
			// The triggering character is not replayed; the search box opens empty.
			toggleSearch();
		}
	}

	@Override
	public void keyTyped(KeyEvent e)
	{
		// Stop the digit of a remapped key from also being typed into chat
		if (swallowTyped && Character.isDigit(e.getKeyChar()))
		{
			e.consume();
		}
	}

	@Override
	public void keyReleased(KeyEvent e)
	{
		Integer target = remapped.remove(e.getKeyCode());
		if (target != null)
		{
			e.setKeyCode(target);
			e.setKeyChar(KeyEvent.CHAR_UNDEFINED);
		}
	}
}
